package com.item.eshop.controller;

import cn.felord.wepay.common.exception.PayException;
import com.alipay.api.domain.TradeRecord;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.gson.Gson;
import com.item.eshop.model.*;
import com.item.eshop.service.*;
import com.item.eshop.util.Cache;
import com.item.eshop.util.JsonObject;
import com.item.eshop.util.TradeID;
import com.item.eshop.util.payment.AliPay;
import com.item.eshop.util.payment.WeiXinPay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Api(value = "RecommendController", description = "订单增删改查等访问接口")
@Controller
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    TradeService tradeService;
    @Autowired
    TradeGoodService tradeGoodService;
    @Autowired
    GoodService goodService;
    @Autowired
    DebtService debtService;
    @Autowired
    DebtRecordService debtRecordService;
    @Autowired
    UserCouponService userCouponService;

    @Resource(name = "wxPayService")
    private WxPayService wxService;

    // 2018-4-21  更新添加订单接口
    @ApiOperation(value = "添加订单", notes = "返回值（int):  1:成功，0：生成订单失败，2：购物车为空，3：商品不存在，4：生成订单失败")
    @ApiImplicitParams({@ApiImplicitParam(name = "amount", value = "总价", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "coupon_id", value = "优惠券", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "address_id", value = "地址", required = true, dataType = "Integer", paramType = "query"),
    })
    @ResponseBody
    @Transactional
    @PostMapping("/add")
    public String add(@RequestParam(value = "amount") double amount, @RequestParam(value = "coupon_id") Integer coupon_id, @RequestParam(value = "address_id") Integer address_id, HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        if (address_id == null) {
            return "5";
        }

        Trade t = new Trade();
        t.setAmount(new BigDecimal(amount));
        t.setFactAmount(new BigDecimal(amount));
        t.setAddressId(address_id);
        if(coupon_id!=0){
            t.setCouponId(coupon_id+"");
        }
        t.setUserId(user_id);
        return tradeService.insert(t);
    }

    @ResponseBody
    @PostMapping("/pay")
    public String pay(@RequestParam(value = "trade_no") String trade_no, @RequestParam(value = "pay_type") Integer pay_type, HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        Trade trade;
        if ((trade = tradeService.selectByIdAndUserId(trade_no, user_id)) == null)
            return "订单已失效，请重新下单！";
        String amount = trade.getFactAmount().doubleValue() + "";
        String subject = "靖军的店-外卖";
        String body = trade.getContent();
        if (pay_type == 1) {
            return AliPay.generate(trade_no, amount, body, subject);
        } else if (pay_type == 2) {
            return new WeiXinPay().generate(trade_no, trade.getFactAmount(), "211.97.6.117");
        }
        return "无此支付方式！";
    }

//    @ResponseBody
//    @PostMapping("/updatePay")
//    public void updatePay(@RequestParam(value = "ali_trade_no") String ali_trade_no, @RequestParam(value = "trade_no") String trade_no, HttpSession httpSession) {
//        int user_id = (int) httpSession.getAttribute("user_id");
//        Trade trade;
//        System.out.println(trade_no + "," + user_id);
//        if ((trade = tradeService.selectByIdAndUserId(trade_no, user_id)) != null) {
//            String result = AliPay.check(trade_no, ali_trade_no);
//            System.out.println(result);
//            //trade.setStatus(2);
//
//            //tradeService.updateByPrimaryKeySelective(trade);
//        }
//    }

    //  2018/4/21  取消订单删除接口
//    @ApiOperation(value="删除某id值的订单",notes="返回值（int):  1:成功，0：失败")
//    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "订单id", required = true ,dataType = "Integer",paramType = "query"),
//            @ApiImplicitParam(name = "user_id", value = "用户id", required = true ,dataType = "Integer",paramType = "query")})
//    @ResponseBody
//    @PostMapping("/delete")
//    public int delete(@RequestParam(value = "id")String id, HttpSession httpSession) {
//        int user_id = (int) httpSession.getAttribute("user_id");
//        return tradeService.deleteByPrimaryKey(id);
//    }

    // add :  chan  2018/4/7
    @ApiOperation(value = "根据用户id值查找所有订单详情", notes = "返回值(Trade对象)数组或NULL值")
    @ApiImplicitParams({@ApiImplicitParam(name = "user_id", value = "用户id值", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "num", value = "展示数量", required = true, dataType = "Integer", paramType = "query")})
    @ResponseBody
    @PostMapping("/findByUser")
    public String findByUser(@RequestParam(value = "user_id") Integer userId, @RequestParam(value = "page") Integer page, @RequestParam(value = "num") Integer num, HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        return JsonObject.toJson(tradeService.selectByUser(user_id, (page - 1) * num, num));
    }

    // add :  chan  2018-4-21
    @ApiOperation(value = "根据用户id值和订单状态查找所有订单详情", notes = "返回值(Trade对象)数组或NULL值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "订单状态", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "num", value = "展示数量", required = true, dataType = "Integer", paramType = "query")})
    @ResponseBody
    @PostMapping("/findByUserAndStatus")
    public String findByUserAndStatus(@RequestParam(value = "status") Integer status, @RequestParam(value = "page") Integer page, @RequestParam(value = "num") Integer num, HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        return JsonObject.toJson(tradeService.selectByUserAndStatus(user_id, status, (page - 1) * num, num));
    }


    // add :  chan  2018-4-21
    @ApiOperation(value = "根据用户id值和多个订单状态值数组查找所有订单详情", notes = "返回值(Trade对象)数组或NULL值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "订单状态", required = true, dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "num", value = "展示数量", required = true, dataType = "Integer", paramType = "query")
    })
    @ResponseBody
    @PostMapping("/findByStatusSet")
    public String findByUserAndStatusArray(@RequestParam(value = "status")String status, HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        System.out.println("array:::status:"+status.substring(1,status.length()-1));
        List<Trade> trades =  tradeService.selectBySet(user_id,status.substring(1,status.length()-1));
        return JsonObject.toJson(trades);
    }


    @ResponseBody       //根据id值查询查询该用户订单
    @PostMapping("/findById")
    public String findById(@RequestParam(value = "id") String id, HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        return JsonObject.toJson(tradeService.selectByIdAndUserId(id, user_id));
    }

    @ResponseBody       //取消订单
    @PostMapping("/cancel")
    public int update(@RequestParam(value = "id") String id, HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        Trade trade = tradeService.selectByPrimaryKey(id);
        if (trade == null) {
            return 0;
        }
        if (trade.getUserId() != user_id)
            return 0;
        trade.setStatus(0);
        return tradeService.updateByPrimaryKeySelective(trade);
    }

    @ResponseBody       //确认收货订单
    @PostMapping("/confirm")
    public int confirm(@RequestParam(value = "id") String id, HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        Trade trade = tradeService.selectByPrimaryKey(id);
        if (trade == null) {
            return 0;
        }
        if (trade.getUserId() != user_id)
            return 0;
        if(trade.getStatus()==3){
            trade.setStatus(4);
        }
        return tradeService.updateByPrimaryKeySelective(trade);
    }

    @ResponseBody       //用户申请退货退款
    @PostMapping("/back")
    public int back(@RequestParam(value = "id") String id, HttpSession httpSession) {
        int user_id = (int) httpSession.getAttribute("user_id");
        Trade trade = tradeService.selectByPrimaryKey(id);
        int status = trade.getStatus();
        if (trade.getUserId() != user_id) {
            return 0;
        }
        if (status == 2) {
            trade.setStatus(5);
        } else if (status == 3 || status == 4) {
            trade.setStatus(6);
        }
        return tradeService.updateByPrimaryKeySelective(trade);
    }

    // ======================  admin background management system interface ( user interceptor) =============================

    //微信支付回调，更新数据库设置status=2
    @ResponseBody
    @PostMapping("/weixinpay")
    public String parseRefundNotifyResult(HttpServletRequest request, HttpServletResponse response) throws WxPayException { //@RequestBody String xmlData
        WxPayOrderNotifyResult params = new WeiXinPay().payNotify(request, response);
        if (params.getResultCode().equals("SUCCESS")){
            String trade_no = params.getOutTradeNo();
            String wx_trade_no = params.getTransactionId();
            System.out.println(">>>>>>>>>>>>>check debt repay:"+trade_no+",length:"+trade_no.length());
            if (trade_no.length() > 13) {
                Trade trade = tradeService.selectByPrimaryKey(trade_no);
                if(trade==null)
                    return null;
                trade.setId(trade_no);
                trade.setPay_type(2);
                trade.setPay_trade_no(wx_trade_no);
                trade.setStatus(2);
                trade.setEndtime(Calendar.getInstance().getTime());
                tradeService.updateByPrimaryKeySelective(trade);
            } else {
                String uid = Cache.getRedis(trade_no);
                System.out.println(">>>>>>>>>>>>>check debt repay:steplen<=13,"+uid);
                if(uid==null)
                    return null;
                int user_id = Integer.parseInt(uid);
                Debt debt = debtService.selectByPrimaryKey(user_id);
                DebtRecord debtRecord = new DebtRecord();
                debtRecord.setId(trade_no);
                debtRecord.setOut_trade_no(wx_trade_no);
                debtRecord.setCost(debt.getCost());
                debtRecord.setUsetime(Calendar.getInstance().getTime());
                debtRecord.setDebt_id(user_id);
                debtRecord.setStatus(2);

                debt.setId(user_id);
                debt.setCost(new BigDecimal(0.00));
                debt.setValid(2);
                debtService.updateByPrimaryKeySelective(debt);
                debtRecordService.insertSelective(debtRecord);
            }
        }
        return null;//this.wxService.parseRefundNotifyResult(xmlData);
    }


    @ResponseBody       //阿里支付回调接口，调用第三方接口
    @PostMapping("/alipay")
    public boolean paid(HttpServletRequest request) {
        Map<String, String> params = AliPay.verify(request);
        String ali_trade_no = params.get("trade_no");
        String trade_no = params.get("out_trade_no");
        if (trade_no.length() > 13) {
            Trade trade = tradeService.selectByPrimaryKey(trade_no);
            if(trade==null)
                return false;
            trade.setId(trade_no);
            trade.setPay_type(1);
            trade.setPay_trade_no(ali_trade_no);
            trade.setStatus(2);
            trade.setEndtime(Calendar.getInstance().getTime());
            tradeService.updateByPrimaryKeySelective(trade);
        } else {

            String uid = Cache.getRedis(trade_no);
            if(uid==null)
                return false;
            int user_id = Integer.parseInt(Cache.getRedis(trade_no));
            Debt debt = debtService.selectByPrimaryKey(user_id);
            DebtRecord debtRecord = new DebtRecord();
            debtRecord.setId(trade_no);
            debtRecord.setOut_trade_no(ali_trade_no);
            debtRecord.setCost(debt.getCost());
            debtRecord.setUsetime(Calendar.getInstance().getTime());
            debtRecord.setDebt_id(user_id);
            debtRecord.setStatus(2);
            debt.setId(user_id);
            debt.setCost(new BigDecimal(0.00));
            debt.setValid(2);
            debtService.updateByPrimaryKeySelective(debt);
            debtRecordService.insertSelective(debtRecord);
        }
        return false;
    }

    @ResponseBody       //退款，调用第三方接口
    @Transactional
    @PostMapping("/refund")
    public int refund(@RequestParam(value = "id") String id, @RequestParam(value = "status") int toStatus) {
        Trade trade = tradeService.selectByPrimaryKey(id);
        int status = trade.getStatus();
        boolean result = false;
        if (status == 1 || status == 0 || status == 7) {
            return 0;
        }
        if (toStatus == 8) {
            trade.setStatus(8);
            tradeService.updateByPrimaryKeySelective(trade);
            return 1;
        }
        BigDecimal amount = trade.getFactAmount();
        String out_trade_no = trade.getPay_trade_no();
        if (trade.getPay_type() == 1) {
            result = AliPay.refund(id, out_trade_no, amount);
        } else if (trade.getPay_type() == 2) {
            result = new WeiXinPay().refund(trade);
        } else if (trade.getPay_type() == 3) {
            Debt debt = new Debt();
            debt.setId(trade.getUserId());
            debt.setCost(new BigDecimal(0.00));
            DebtRecord debtRecord = new DebtRecord();
            debtRecord.setId(TradeID.getDebtRecordId());
            debtRecord.setDebt_id(trade.getUserId());
            debtRecord.setTrade_no(trade.getId());
            debtRecord.setStatus(3);
            debtRecord.setCost(trade.getFactAmount());
            debtRecord.setUsetime(Calendar.getInstance().getTime());
            int result1 = debtRecordService.insertSelective(debtRecord);
            int result2 = debtService.updateByPrimaryKeySelective(debt);
            if(result1==1&&result2==1){
                result = true;
            }
        }
        if (result) {
            trade.setStatus(7);
            return tradeService.updateByPrimaryKeySelective(trade);
        }
        return 0;
    }

    @ResponseBody       //根据id值订单查询
    @PostMapping("/find")
    public String find(@RequestParam(value = "id") String id) {
        return JsonObject.toJson(tradeService.selectByPrimaryKey(id));
    }

    //管理员修改订单金额
    @ResponseBody
    @PostMapping("/updateAmount")
    public int update(@RequestParam(value = "id") String id, @RequestParam(value = "amount") double amount) {
        Trade trade = new Trade();
        trade.setId(id);
        trade.setFactAmount(new BigDecimal(amount));
        return tradeService.updateByPrimaryKeySelective(trade);
    }

    //管理员修改地址
    @ResponseBody
    @PostMapping("/updateAddress")
    public int updateAddress(@RequestParam(value = "id") String id, @RequestParam(value = "address") String address) {
        Trade trade = new Trade();
        trade.setId(id);
        trade.setAddress(address);
        return tradeService.updateByPrimaryKeySelective(trade);
    }

    //管理员修改地址和金额
    @ResponseBody
    @PostMapping("/updateTwo")
    public int updateTwo(@RequestParam(value = "id") String id, @RequestParam(value = "address") String address, @RequestParam(value = "amount") double amount) {
        Trade trade = new Trade();
        trade.setId(id);
        trade.setAddress(address);
        trade.setAmount(new BigDecimal(amount));
        return tradeService.updateByPrimaryKeySelective(trade);
    }

    //管理员修改订单状态/ 发货/结算/确认退货，取消退货
    @ResponseBody
    @PostMapping("/updateStatus")
    public int update(@RequestParam(value = "id") String id, @RequestParam(value = "status") Integer status) {
        Trade trade = new Trade();
        trade.setId(id);
        trade.setStatus(status);
        return tradeService.updateByPrimaryKeySelective(trade);
    }

    // add :  chan  2018/4/4
    @ResponseBody
    @PostMapping("/findMore")
    public String findMore(@RequestParam(value = "page") Integer page, @RequestParam(value = "num") Integer num) {
        return JsonObject.toJson(tradeService.selectMore((page - 1) * num, num));
    }

    // add :  chan  2018/4/4
    @ResponseBody
    @PostMapping("/findByType")
    public String findByType(@RequestParam(value = "status",required = false) Integer status, @RequestParam(value = "page") Integer page, @RequestParam(value = "num") Integer num) {
        if(status==null||status==10){
            return JsonObject.toJson(tradeService.selectMore((page - 1) * num, num));
        }
        List<Trade> trade = tradeService.selectByStatus(status, (page - 1) * num, num);
        return JsonObject.toJson(trade);
    }

    @ResponseBody
    @PostMapping("/findByTypes")
    public String findByTypes(@RequestParam(value = "status",required = false) Integer status,@RequestParam(value = "status2",required = false) Integer status2, @RequestParam(value = "page") Integer page, @RequestParam(value = "num") Integer num) {
        String set =""+status+","+status2+")";
        List<Trade> trade = tradeService.selectBySets(set,(page-1)*num,num);
        return JsonObject.toJson(trade);
    }
}
