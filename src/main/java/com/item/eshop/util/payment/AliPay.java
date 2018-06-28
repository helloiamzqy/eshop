package com.item.eshop.util.payment;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.item.eshop.config.Constant;
import com.item.eshop.model.Const;
import com.item.eshop.model.Trade;
import com.item.eshop.service.TradeService;
import com.item.eshop.util.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class AliPay {
    @Autowired
    Constant constant;

    private static Constant staticConstant;

    @PostConstruct
    public void init() {
        staticConstant = constant;
    }

    public static String generate(String outtradeno,String totalAmount,String body,String subject ) {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", staticConstant.getAli_app_id(), staticConstant.getAli_app_primary_key(), "json", staticConstant.getAli_charset(), staticConstant.getAli_pay_public_key(), "RSA2");
        //        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(outtradeno);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(totalAmount);
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(staticConstant.getAli_url());
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return "支付宝订单生成失败！";
    }

    public static Map<String,String> verify(HttpServletRequest request) {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        try {
            boolean flag = AlipaySignature.rsaCheckV1(params, staticConstant.getAli_pay_common_public_key(), staticConstant.getAli_charset(),"RSA2");
            if(flag){
               return params;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static boolean refund(String out_trade_no,String ali_pay_trade_no,BigDecimal bd) {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",staticConstant.getAli_app_id(),staticConstant.getAli_app_primary_key(), "json", staticConstant.getAli_charset(), staticConstant.getAli_pay_common_public_key(), "RSA2"); //获得初始化的AlipayClient
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\""+out_trade_no+"\"," +
                "\"trade_no\":\""+ali_pay_trade_no+"\"," +
                "\"refund_amount\":"+bd+"," +
//                "\"refund_currency\":\"USD\"," +
                "\"refund_reason\":\"靖军的店-正常退款\"" +
//                "\"out_request_no\":\"HZ01RF001\"," +
//                "\"operator_id\":\"OP001\"," +
//                "\"store_id\":\"NJ_S_001\"," +
//                "\"terminal_id\":\"NJ_T_001\"," +
//                "      \"goods_detail\":[{" +
//                "        \"goods_id\":\"apple-01\"," +
//                "\"alipay_goods_id\":\"20010001\"," +
//                "\"goods_name\":\"ipad\"," +
//                "\"quantity\":1," +
//                "\"price\":2000," +
//                "\"goods_category\":\"34543238\"," +
//                "\"body\":\"特价手机\"," +
//                "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
//                "        }]" +
                "  }");
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println("退款成功");
            return true;
        } else {
            System.out.println("退款失败");
            return false;
        }
    }

    public static String check(String out_trade_no,String ali_pay_trade_no) {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",staticConstant.getAli_app_id(),staticConstant.getAli_app_primary_key(), "json", staticConstant.getAli_charset(), staticConstant.getAli_pay_common_public_key(), "RSA2"); //获得初始化的AlipayClient
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
        request.setBizContent("{" +
                "   \"out_trade_no\":"+out_trade_no+"," +
                "   \"trade_no\":"+ali_pay_trade_no+"" +
                "  }");//设置业务参数
        AlipayTradeQueryResponse response = null;//通过alipayClient调用API，获得对应的response类
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response.getBody();
//根据response中的结果继续业务逻辑处理
    }

}
