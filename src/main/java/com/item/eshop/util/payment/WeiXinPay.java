package com.item.eshop.util.payment;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundQueryRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.item.eshop.config.Constant;
import com.item.eshop.model.Trade;
import com.item.eshop.util.JsonObject;
import com.item.eshop.util.thirdpay.WxpayUtil;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.*;

import static com.item.eshop.util.payment.MapUtils.sortMap;

@Component
public class WeiXinPay {

    @Autowired
    Constant constant;

    @Resource(name = "wxPayService")
    private WxPayService wxService;

    private static Constant staticConstant;

    private static WxPayService staticwxService;

    @PostConstruct
    public void init() {
        staticConstant = constant;
        staticwxService = wxService;
    }

    public String generate(String trade_no,BigDecimal weixin_total_fee,String user_ip){
        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setMchId(ConfigUtil.MCH_ID);
            orderRequest.setAppid(ConfigUtil.APPID);
            orderRequest.setBody("商品购买");
            orderRequest.setOutTradeNo(trade_no);
            orderRequest.setTotalFee((int)(weixin_total_fee.doubleValue()*100));//元转成分
            orderRequest.setTradeType("APP");
            orderRequest.setNotifyUrl("http://47.106.118.67:8080/trade/weixinpay");
            orderRequest.setSpbillCreateIp(user_ip);
            orderRequest.setSignType("MD5");
//            orderRequest.setTimeStart("yyyyMMddHHmmss");
//            orderRequest.setTimeExpire("yyyyMMddHHmmss");
            WxPayAppOrderResult wpo = staticwxService.createOrder(orderRequest);
            Map<String,String> result = new HashMap<>();
            result.put("appid",wpo.getAppId());
            result.put("nonce_str",wpo.getNonceStr());
            result.put("prepayid",wpo.getPrepayId());
            result.put("partnerid",wpo.getPartnerId());
            result.put("package",wpo.getPackageValue());
            result.put("timestamp",wpo.getTimeStamp());
            result.put("sign",wpo.getSign());
            return JsonObject.toJson(result);
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(WeiXinPay.class);
            logger.error("微信支付失败！订单号：{},原因:{}", trade_no, e.getMessage());
            e.printStackTrace();
            return "支付失败，请稍后重试！";
        }
    }

    public WxPayOrderNotifyResult payNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            WxPayOrderNotifyResult result = staticwxService.parseOrderNotifyResult(xmlResult);
            // 结果正确
            String orderId = result.getOutTradeNo();
            String tradeNo = result.getTransactionId();
            String totalFee = String.valueOf(BigDecimal.valueOf(result.getTotalFee() / 100));
            System.out.println(",,,,,:" + JsonObject.toJson(result));
            //自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
            WxPayNotifyResponse.success("处理成功!");
            return result;
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(WeiXinPay.class);
            logger.error("微信回调结果异常,异常原因{}", e.getMessage());
            WxPayNotifyResponse.fail(e.getMessage());
            return null;
        }
    }

    public boolean refund(Trade trade) {
        WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
        wxPayRefundRequest.setRefundFee((int)(trade.getFactAmount().doubleValue()*100));
        if(trade.getPay_trade_no()!=null) {
            wxPayRefundRequest.setTransactionId(trade.getPay_trade_no());
        }
        wxPayRefundRequest.setOutTradeNo(trade.getId());
        wxPayRefundRequest.setOutRefundNo(trade.getId());
        wxPayRefundRequest.setTotalFee((int)(trade.getFactAmount().doubleValue()*100));
        wxPayRefundRequest.setAppid(ConfigUtil.APPID);
        wxPayRefundRequest.setMchId(ConfigUtil.MCH_ID);
        wxPayRefundRequest.setSignType("MD5");
//        WxPayConfig payConfig = new WxPayConfig();
//        payConfig.setAppId(ConfigUtil.APPID);
//        payConfig.setMchId(ConfigUtil.MCH_ID);
//        payConfig.setKeyPath(ConfigUtil.KEY_PATH);
//        staticwxService.setConfig(payConfig);
        try {
            WxPayRefundResult wxPayRefundResult = staticwxService.refund(wxPayRefundRequest);
            if(wxPayRefundResult.getResultCode().equals("SUCCESS"))
                return true;
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String generate(String ip, String weixin_out_trade_no, BigDecimal weixin_total_fee){
        String noncestr = PayCommonUtil.CreateNoncestr();
        if (Strings.isNullOrEmpty(ip)) {
            try {
                InetAddress addr = InetAddress.getLocalHost();
                ip = addr.getHostAddress().toString();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        SortedMap<String, Object> parameters = prepareOrder(ip, weixin_out_trade_no, (int)(weixin_total_fee.doubleValue()*100),noncestr);
        String sign = PayCommonUtil.createSign(Charsets.UTF_8.toString(), parameters);
        System.out.println("sign:::"+sign);
        parameters.put("sign", sign);// sign签名 key
        String requestXML = PayCommonUtil.getRequestXml(parameters);// 生成xml格式字符串
        System.out.println("requestXML:::"+requestXML);
        String responseStr = HttpsUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
        try {
            SortedMap<String, Object> resultMap = XMLUtil.doXMLParse(responseStr);
            System.out.println("result:::"+ JsonObject.toJson(resultMap));
            SortedMap<String, Object> map = buildClientJson(resultMap);
            return JsonObject.toJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //预支付成功，返回给app的参数
    private SortedMap<String, Object> buildClientJson(Map<String, Object> resultMap) throws UnsupportedEncodingException {
        Map<String, Object> params = ImmutableMap.<String, Object> builder()
                .put("appid", ConfigUtil.APPID)//应用号
                .put("noncestr", PayCommonUtil.CreateNoncestr())//随机字符串
                .put("package", "Sign=WXPay")//固定的字符串，不需要改变
                .put("partnerid",  ConfigUtil.MCH_ID)//商户号
                .put("prepayid", resultMap.get("prepay_id"))//预支付微信返回的id
                .put("timestamp", new Timestamp(System.currentTimeMillis())) // 10 位时间戳
                .build();
        SortedMap<String, Object> sortMap = sortMap(params);
        sortMap.put("package", "Sign=WXPay");
        String paySign = PayCommonUtil.createSign(Charsets.UTF_8.toString(), sortMap);
        sortMap.put("sign", paySign);
        return sortMap;
    }

    //预支付参数准备
    private SortedMap<String, Object> prepareOrder(String ip, String tradeId, int price,String noncestr) {
        Map<String, Object> oparams = ImmutableMap.<String, Object> builder()
                .put("appid", ConfigUtil.APPID)//应用号
                .put("mch_id", ConfigUtil.MCH_ID)// 商户号
                .put("nonce_str", noncestr)// 16随机字符串(大小写字母加数字)
                .put("body", "靖军的店-商品")// 商品描述
                .put("out_trade_no", tradeId)// 商户订单号
                .put("total_fee", price)
                .put("spbill_create_ip", ip)// IP地址
                .put("notify_url", "http%3A%2F%2F47.106.118.67%3A8080%2Ftrade%2Fweixinpay") // 微信回调地址
                .put("trade_type", ConfigUtil.TRADE_TYPE)// 支付类型 APP
                .build();//支付金额
        return sortMap(oparams);
    }


    private String callback(String responseStr) {
        try {
            Map<String, Object> map = XMLUtil.doXMLParse(responseStr);
            if (!PayCommonUtil.checkIsSignValidFromResponseString(responseStr)) {
                System.out.println("error0:::::"+"invalid sign");
                return "invalid sign";//PayCommonUtil.setXML(WeixinConstant.FAIL, "invalid sign");
            }
            if (map.get("result_code").toString()=="FAIL") {
                return "weixin pay fail";//PayCommonUtil.setXML(WeixinConstant.FAIL, "weixin pay fail");
            }
            if (map.get("result_code").toString()=="SUCCESS") {
                // 对数据库的操作
                String outTradeNo = (String) map.get("out_trade_no");
                String transactionId = (String) map.get("transaction_id");
                String totlaFee = (String) map.get("total_fee");
                Integer totalPrice = Integer.valueOf(totlaFee) / 100;//服务器这边记录的是钱的元
                // Trade trade = tradeBiz.get(Integer.valueOf(outTradeNo));
                // trade.setTransactionId(transactionId);
                //boolean isOk = tradeBiz.paid(trade);
                // if (isOk) {
                //  return PayCommonUtil.setXML(WeixinConstant.SUCCESS, "OK");
                // } else {
                //  return PayCommonUtil
                //        .setXML(WeixinConstant.FAIL, "update bussiness outTrade fail");
                //}
            }
        } catch (Exception e) {
            return PayCommonUtil.setXML("FAIL", "weixin pay server exception");
        }
        return PayCommonUtil.setXML("FAIL", "weixin pay fail");
    }

    public String getSign(String weixin_sign){
        return weixin_sign;
    }

    public String getDetail(String weixin_detail){
        return weixin_detail;
    }


}
