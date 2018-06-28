package com.item.eshop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "constant")
public class Constant {
    private String path;

    private String url;

    private String ip;

    private String passwd;

    private String ali_app_id;

    private String ali_url;

    private String ali_app_primary_key;

    private String ali_pay_common_public_key;

    private String ali_charset;

    private String ali_pay_public_key;

    private String weixin_url;            //微信支付链接

    private String weixin_app_id;        //微信公众号ID

    private String weixin_mch_id;       //商户号

    private String weixin_body;         //商品简单描述 （应用市场上的APP名字-商品概述）

    private String weixin_trade_type;    //交易类型 APP

    private String weixin_notify_url;   //通知地址

    public String getAli_url() {
        return ali_url;
    }

    public void setAli_url(String ali_url) {
        this.ali_url = ali_url;
    }

    public String getAli_pay_common_public_key() {
        return ali_pay_common_public_key;
    }

    public void setAli_pay_common_public_key(String ali_pay_common_public_key) {
        this.ali_pay_common_public_key = ali_pay_common_public_key;
    }

    public String getWeixin_url() {
        return weixin_url;
    }

    public void setWeixin_url(String weixin_url) {
        this.weixin_url = weixin_url;
    }

    public String getWeixin_app_id() {
        return weixin_app_id;
    }

    public void setWeixin_app_id(String weixin_app_id) {
        this.weixin_app_id = weixin_app_id;
    }

    public String getWeixin_mch_id() {
        return weixin_mch_id;
    }

    public void setWeixin_mch_id(String weixin_mch_id) {
        this.weixin_mch_id = weixin_mch_id;
    }

    public String getWeixin_body() {
        return weixin_body;
    }

    public void setWeixin_body(String weixin_body) {
        this.weixin_body = weixin_body;
    }

    public String getWeixin_trade_type() {
        return weixin_trade_type;
    }

    public void setWeixin_trade_type(String weixin_trade_type) {
        this.weixin_trade_type = weixin_trade_type;
    }

    public String getWeixin_notify_url() {
        return weixin_notify_url;
    }

    public void setWeixin_notify_url(String weixin_notify_url) {
        this.weixin_notify_url = weixin_notify_url;
    }

    public String getAli_app_id() {
        return ali_app_id;
    }

    public void setAli_app_id(String ali_app_id) {
        this.ali_app_id = ali_app_id;
    }

    public String getAli_app_primary_key() {
        return ali_app_primary_key;
    }

    public void setAli_app_primary_key(String ali_app_primary_key) {
        this.ali_app_primary_key = ali_app_primary_key;
    }

    public String getAli_charset() {
        return ali_charset;
    }

    public void setAli_charset(String ali_charset) {
        this.ali_charset = ali_charset;
    }

    public String getAli_pay_public_key() {
        return ali_pay_public_key;
    }

    public void setAli_pay_public_key(String ali_pay_public_key) {
        this.ali_pay_public_key = ali_pay_public_key;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
