package com.item.eshop.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Api(value = "DebtController", description = "（后台管理系统接口）白条增删改查等访问接口")
@RestController
@RequestMapping("/debt")
public class DebtController {
    
    @Autowired
    DebtService debtService;

    @Autowired
    TradeService tradeService;

    @Autowired
    CouponService couponService;

    @Autowired
    UserService userService;

    @Autowired
    DebtRecordService debtRecordService;

    @ApiOperation(value="查找该用户的白条",notes="返回值(Debt对象)或null值")
    @ApiImplicitParams({})
    @GetMapping("/find")
    public String findByUser(HttpSession httpSession) {
        int user_id = (int)httpSession.getAttribute("user_id");
        return JsonObject.toJson(debtService.selectByPrimaryKey(user_id));
    }

    @ApiOperation(value="白条支付",notes="返回值(int):1:支付成功，0：支付失败。")
    @ApiImplicitParams({})
    @Transactional
    @GetMapping("/pay")
    public Msg pay(@RequestParam(value = "trade_no")String trade_no, @RequestParam(value = "passwd")String passwd, HttpSession httpSession) {
        int user_id = (int)httpSession.getAttribute("user_id");
        String phone = (String)httpSession.getAttribute("phone");
        Trade trade = tradeService.selectByIdAndUserId(trade_no,user_id
        );

        if(userService.login(phone,passwd)==null){
            return new Msg(0,"支付密码错误！");
        }
        if(trade!=null) {
            BigDecimal amount = trade.getFactAmount();
            Debt debt = debtService.selectByPrimaryKey(user_id);
            if(debt==null){

                return new Msg(-3,"白条无效，请开通认证白条！");
            }
            if(debt.getValid()==2){
                BigDecimal bd = debt.getCost();
                if(debt.getMax_limit().doubleValue()-bd.doubleValue()>=amount.doubleValue()){
                    debt.setCost(bd.add(amount));
                    debtService.updateByPrimaryKeySelective(debt);
                    trade.setPay_type(3);
                    trade.setStatus(2);
                    tradeService.updateByPrimaryKeySelective(trade);
                    DebtRecord debtRecord = new DebtRecord();
                    debtRecord.setId(TradeID.getDebtRecordId());
                    debtRecord.setDebt_id(user_id);
                    debtRecord.setTrade_no(trade_no);
                    debtRecord.setCost(amount);
                    debtRecord.setUsetime(Calendar.getInstance().getTime());
                    debtRecord.setStatus(1);
                    debtRecordService.insertSelective(debtRecord);
                    return new Msg(1,"白条支付成功");
                }else
                    return new Msg(-1,"白条余额不足。");
            }else if(debt.getValid()>2){
                return new Msg(-2,"上月白条未还清。");
            }else{
                return new Msg(-3,"白条无效，请开通认证白条！");
            }
        }
        return new Msg(-5,"订单不存在。");
    }

    @ApiOperation(value="偿还白条",notes="返回值支付链接")
    @ApiImplicitParams({})
    @PostMapping("/repay")
    public String repay(@RequestParam(value = "pay_type")Integer type, HttpSession httpSession) {
        int id = (int) httpSession.getAttribute("user_id");
        Debt debt = debtService.selectByPrimaryKey(id);
        double amount = debt.getCost().doubleValue();
        String trade_no = TradeID.getDebtRecordId();
        String result = null;
        Cache.setRedisWithDeadline(trade_no,id+"",3000);
        System.out.println(">>>>>>>>>>>>>check debt repay");
        if(debt!=null&&amount>0) {
            if (type == 1) { //alipay
                System.out.println(">>>>>>>>>>>>>check debt repay,1111111");
                result = AliPay.generate(trade_no,String.valueOf(amount),"白条月结","靖军的店-白条还款");
            } else if (type == 2) {  //wechatpay
                System.out.println(">>>>>>>>>>>>>check debt repay,2222222");
               result = new WeiXinPay().generate(trade_no,debt.getCost(),id+"211.97.6.117");
            }
        }
        return result;
    }

    @ApiOperation(value="退款",notes="返回值1:成功，0：失败")
    @ApiImplicitParams({})
    @PostMapping("/refund")
    public int refund(HttpSession httpSession) {
        int id = (int) httpSession.getAttribute("user_id");
        Debt debt = new Debt();
        debt.setId(id);

        //验证确认支付是否成功
        if(true) {
            debt.setCost(new BigDecimal(0.00));
            debt.setValid(2);
            debtService.updateByPrimaryKeySelective(debt);
        }
        return 0;
    }

    // ======================  admin background management system interface ( user interceptor) =============================
//    @GetMapping("/add")
//    public int add(HttpSession httpSession) {
//        int user_id = (int) httpSession.getAttribute("user_id");
//        Coupon coupon = couponService.selectByPrimaryKey(0);
//        Debt debt = new Debt();
//        debt.setId(user_id);
//        debt.setMax_limit(coupon.getTotal());
//        return debtService.insertSelective(debt);
//    }

//    @PostMapping("/delete")
//    public int delete(@RequestParam(value = "id")Integer id) {
//        return debtService.deleteByPrimaryKey(id);
//    }


    @PostMapping("/updateMax")
    public int update(@RequestParam(value = "id")Integer id,@RequestParam(value = "max",required = false)double max) {
        Debt debt = new Debt();
        debt.setId(id);
        debt.setMax_limit(new BigDecimal(max));
        return debtService.updateByPrimaryKeySelective(debt);
    }

    @PostMapping("/updateValid")
    public int update(@RequestParam(value = "id")Integer id, @RequestParam(value = "valid")Integer valid) {
        Debt debt = new Debt();
        debt.setId(id);
        debt.setValid(valid);
        return debtService.updateByPrimaryKeySelective(debt);
    }

    @PostMapping("/findById")
    public String find(@RequestParam(value = "id")Integer id) {
        return JsonObject.toJson(debtService.selectByPrimaryKey(id));
    }


    // add :  chan  2018/4/4
    @PostMapping("/findMore")
    public String findMore(@RequestParam(value = "page")Integer page,@RequestParam(value = "num")Integer num) {
        return JsonObject.toJson(debtService.selectMore(page,num));
    }

    // add:  chan 2018/4/16
    @PostMapping("/findByValid")
    public String findByValid(@RequestParam(value = "page")Integer page,@RequestParam(value = "num")Integer num,@RequestParam(value = "valid")Integer valid) {
        List<Debt> debts = null;
        if(valid==10){
            debts = debtService.selectMore(page,num);
        }else{
            debts = debtService.selectMoreByValid(valid,page,num);
        }
        String result = debtWithPhone(debts);
        return result;
    }

    // add :  chan  2018/5/10
    public String debtWithPhone(List<Debt> debtList) {
        List<Map<String,String>> result = new ArrayList<>();
        Map<String,String> map = null;
        Debt debt = null;
        String phone = "";
        for(int i=0;i<debtList.size();i++){
            map = new HashMap<>();
            debt = debtList.get(i);
            phone = userService.selectUser(debt.getId()).getPhone();
            map.put("id",debt.getId()+"");
            map.put("valid",debt.getValid()+"");
            map.put("max_limit",debt.getMax_limit()+"");
            map.put("cost",debt.getCost()+"");
            map.put("phone",phone);
            result.add(map);
        }
        return JsonObject.toJson(result);
    }


}

