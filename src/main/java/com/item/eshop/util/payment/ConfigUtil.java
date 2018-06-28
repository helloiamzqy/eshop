package com.item.eshop.util.payment;

public class ConfigUtil {
    /**
     * 服务号相关信息
     */
    public final static String APPID = "wx63d3930c171e366e";// 应用号
    public final static String APP_SECRECT = "3d70a7b1edc06bb95fa2af1fc10618c2";// 应用密码
    public final static String MCH_ID = "1502856731";// 商户号 xxxx 公众号商户id
    public final static String API_KEY = "Wujinjun12345678Wujinjun12345678";// API密钥
    public final static String SIGN_TYPE = "MD5";// 签名加密方式
    public final static String TRADE_TYPE = "APP";// 支付类型
    public final static String KEY_PATH = "/opt/tomcat/webapps/apiclient_cert.p12";
    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; // 微信支付统一接口(POST)
}

