package com.item.eshop.util.thirdpay;

import com.google.common.collect.Maps;
import com.item.eshop.util.JsonObject;
import com.item.eshop.util.thirdpay.util.Digests;
import com.item.eshop.util.thirdpay.util.XMLUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.JDOMException;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.*;

/**
 * @ClassName: WxpayUtil
 * @author: SuperZemo
 * @email: zemochen@gmail.com
 * @Date 6/26/15 14:27
 * @Description 微信支付工具
 */
public class WxpayUtil {

    private static final String UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private static final String ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";
    private static final String CLOSE_ORDER = "https://api.mch.weixin.qq.com/pay/closeorder";
    private static final String PAY_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    private static final String STATUS_SUCCESS = "success";
    private static final String APP_ID = "wx63d3930c171e366e";
    private static final String MCH_ID = "1502856731";    //商户号
    private static final String APP_KEY = "Wujinjun12345678Wujinjun12345678";
    private static final String ENV = "dev";    //开发环境,dev时支付金额不乘以100
    private static final String NOTIFY_URL = "http%3A%2F%2F47.106.118.67%3A8080%2Ftrade%2Fweixinpay";
    public static final String PAY_TYPE_JSAPI = "JSAPI";
    public static final String PAY_TYPE_APP = "APP";


    public static SortedMap<String, String> buildPrePayParam(String body, double price, String tradeNo, String type, String openid) {
        SortedMap<String, String> info = Maps.newTreeMap();
        //设置package订单参数
        info.put("appid", APP_ID);
        //商户号
        info.put("mch_id", MCH_ID);
        info.put("nonce_str", genNonceStr());
        //商品描述
        info.put("body", body);
        //商家订单号
        info.put("out_trade_no", tradeNo);
        //商品金额,以"分"为单位
        if (!"dev".equals(ENV)) {
            price = (price * 100);
        }
        info.put("total_fee", (int) Math.abs(price) + "");
        //订单生成的机器IP，指用户浏览器端IP
        info.put("spbill_create_ip", "196.168.3.25");
        info.put("trade_type", PAY_TYPE_APP);
        //异步回调url
        info.put("notify_url", NOTIFY_URL);

//        info.put("device_info", type);

        if (StringUtils.isNotBlank(openid)) {
            info.put("openid", openid);
        }
        /*info.put("timestamp", getTimestamp());
        //银行渠道
        //info.put("bank_type", "WX");
        //币种，1人民币   66
        info.put("fee_type", "1");
        //字符编码
        info.put("input_charset", "UTF-8");
        info.put("package", packageValue);
        info.put("traceid", traceid);*/
        return info;
    }

    /**
     * 生成预支付订单
     *
     * @return
     */
    public static String getPerPayId(String trade_no,int price) {

        String xml = getPackageSign(buildPrePayParam("shop-good",price,trade_no,"APP",""));
        System.out.println("xml:"+xml);
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(UNIFIED_ORDER);
        try {
            httpPost.setEntity(new StringEntity(xml, "UTF-8"));
            HttpResponse resp = client.execute(httpPost);
            HttpEntity entity = resp.getEntity();
            System.out.println("result:::::"+ entity.toString()+entity.getContent().toString()+entity.getContent().toString());
            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return null;
            }
            return getXMLValue(entity, "prepay_id");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证订单
     *
     * @param transactionId
     * @param outTradeNo
     * @param fee
     * @return
     */
    public static boolean verifyOrder(String transactionId, String outTradeNo, Double fee) {
        //组装请求查询订单所需参数
        SortedMap<String, String> map = Maps.newTreeMap();
        map.put("appid", APP_ID);
        map.put("mch_id", MCH_ID);
        map.put("nonce_str", genNonceStr());
        if (StringUtils.isNotBlank(transactionId)) {
            map.put("transaction_id", transactionId);
        } else {
            map.put("out_trade_no", outTradeNo);

        }
        map.put("sign", createSign(map));

        //建立连接查询
        String xmlString = buildXML(map);
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ORDER_QUERY);
        try {
            httpPost.setEntity(new StringEntity(xmlString, "UTF-8"));

            HttpResponse resp = client.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return false;
            }
            HttpEntity entity = resp.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            System.out.println("thirdPay result::"+result);
            result = result.replace("<![CDATA[", "").replace("]]>", "");
            //获取查询结果
            Map<String, String> param = XMLUtil.doXMLParse(result);
            String returnCode = param.get("return_code");
            String resultCode = param.get("result_code");
            String billNo = param.get("out_trade_no");
            //判断查询真实性，防止钓鱼攻击
            if (resultCode.equalsIgnoreCase(STATUS_SUCCESS) &&
                    returnCode.equalsIgnoreCase(STATUS_SUCCESS) && outTradeNo.equals(billNo)) {
                Double totalFee = Double.valueOf(param.get("total_fee")) / 100;
                if (totalFee.equals(fee)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 关闭订单
     * 以下情况需要调用关单接口：商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
     *
     * @param tradeNo 系统内订单号
     * @return
     */
    public static boolean closeOrder(String tradeNo) {
        SortedMap<String, String> map = Maps.newTreeMap();
        map.put("appid", APP_ID);
        //商户号
        map.put("mch_id", MCH_ID);
        map.put("nonce_str", genNonceStr());
        //商家订单号
        map.put("out_trade_no", tradeNo);
        String xml = getPackageSign(map);
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(CLOSE_ORDER);
        try {
            httpPost.setEntity(new StringEntity(xml, "UTF-8"));
            HttpResponse resp = client.execute(httpPost);
            HttpEntity entity = resp.getEntity();
            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return false;
            }
            return getXMLValue(entity, "return_code").equals("SUCCESS") ? true : false;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return
     */
    public static Boolean refund(String tradeNo, int totalFee, int refundFee) {
        SortedMap<String, String> map = Maps.newTreeMap();
        map.put("appid", APP_ID);
        //商户号
        map.put("mch_id", MCH_ID);
        map.put("nonce_str", genNonceStr());
        //商家订单号
        map.put("out_trade_no", tradeNo);
        //商户系统内部的退款单号,暂用订单号,同一个单号只能退款一次
        map.put("out_refund_no", tradeNo);
        //订单总额
        map.put("total_fee", totalFee + "");
        //退款金额
        map.put("refund_fee", refundFee + "");
        //操作员
        map.put("op_user_id", MCH_ID);
        String xml = getPackageSign(map);
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(PAY_REFUND);
        SSLContext sslContext = null;
        try {

            KeyStore ks = KeyStore.getInstance("pkcs12");
            InputStream ins = WxpayUtil.class.getResourceAsStream("/cert/apiclient_cert.p12");
            ks.load(ins, MCH_ID.toCharArray());

            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(ks, MCH_ID.toCharArray()).build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            httpPost.setEntity(new StringEntity(xml, "UTF-8"));
            HttpResponse resp = client.execute(httpPost);
            HttpEntity entity = resp.getEntity();
            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return false;
            }
            return getXMLValue(entity, "return_code").equals("SUCCESS") ? true : false;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Map<String, String> createPackageValue(String prepayid) {
        SortedMap<String, String> map = Maps.newTreeMap();
        //设置package订单参数
        map.put("appId", APP_ID);
//        map.put("device_info", deviceInfo);
        map.put("timeStamp", getTimestamp());
        map.put("nonceStr", genNonceStr());
        //JSAPI 传参形式
        map.put("package", "prepay_id=" + prepayid);
        //APP 传参形式
//        map.put("package", "Sign=WXPay");
        map.put("signType", "MD5");
        map.put("paySign", createSign(map));

        return map;
    }

    private static String getPackageSign(SortedMap<String, String> map) {

        map.put("sign", createSign(map));
        return buildXML(map);
    }

    /**
     * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     */
    private static String createSign(SortedMap<String, String> packageParams) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + APP_KEY);
        String sign = Digests.MD5(sb.toString()).toUpperCase();
        return sign;
    }

    /**
     * 根据key获取xml值
     *
     * @param result
     * @param key
     * @return
     */
    private static String getXMLValue(HttpEntity result, String key) {
        //获取查询结果
        try {
            String resource = EntityUtils.toString(result, "UTF-8");
            resource = resource.replace("<![CDATA[", "").replace("]]>", "");
            Map<String, String> param = XMLUtil.doXMLParse(resource);
            System.out.println("key:::::"+resource);
            return param.get(key);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构建xml
     *
     * @param map
     * @return
     */
    private static String buildXML(SortedMap<String, String> map) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>\n");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if ("body".equals(entry.getKey()) || "sign".equals(entry.getKey())) {
                xml.append("<" + entry.getKey() + "><![CDATA[").append(entry.getValue()).append("]]></" + entry.getKey() + ">\n");
            } else {
                xml.append("<" + entry.getKey() + ">").append(entry.getValue()).append("</" + entry.getKey() + ">\n");
            }
        }

        xml.append("</xml>");
        return xml.toString();
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static String getTimestamp() {
        return Long.toString(new Date().getTime() / 1000);
    }

    /**
     * 生成随机数
     *
     * @return
     */
    public static String genNonceStr() {
        Random random = new Random();
        return Digests.MD5(String.valueOf(random.nextInt(10000)));
    }

//    public static void main(String[] args) {
//        /*SortedMap<String, String> map = Maps.newTreeMap();
//        map = buildPrePayParam("V3阿斯顿飞", 1, "2016060512111", "JSAPI", "o4Bd1w49SWQoo_lO0vUyppyjFNfM");
//
//        String packageString = getPerPayId(map);
//        System.out.println(packageString);
//
//        closeOrder("2016060512111");
////        System.out.println(packageString);
////        String perPayId = getPerPayId(map);
////        System.out.println("perPayId:" + perPayId);
//
////        boolean result = verifyOrder(null, "G201507081413466504",0.01d);
////        System.out.println(result);
//        Map<String, String> packageValue = Maps.newHashMap();
////        packageValue = createPackageValue(packageString);
//        System.out.println("-------");
//        for (String s : packageValue.keySet()) {
//            System.out.println(s + ":" + packageValue.get(s));
//        }*/
//        refund("G201608031526051334", 5, 5);
//       /* WxpayUtil wxpay = new WxpayUtil();
//        InputStream ins = WxpayUtil.class.getResourceAsStream("/cert/apiclient_cert.p12");
//        *//*InputStream ins = null;
//        try {
//            ins = new FileInputStream("src/main/resources/cert/apiclient_cert.p12");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//*//*
//        System.out.println(ins == null);*/
//
//    }
}
