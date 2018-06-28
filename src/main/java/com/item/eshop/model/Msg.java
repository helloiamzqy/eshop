package com.item.eshop.model;

public class Msg {
    private int code;
    private String content;
    private static final String[] NORMAL_MSG = {"失败","成功"};
    private static final String[] USER_REGISTER_MSG = {"账号密码不能为空","验证码已过期","验证码错误","账号已被注册","账号密码格式有误","注册成功"};
    private static final String[] USER_LOGIN_MSG = {"账号密码错误","登录成功"};
    private static final String[] USER_SMS_MSG = {"短信发送失败，该手机号不可用","短信发送成功"};


    public static Msg getMsg(String module,int type,int code){
        String[] temp = NORMAL_MSG;
        switch (module){
            case "user":
                if(type==0)
                    temp = USER_REGISTER_MSG;
                else if(type==1)
                    temp = USER_LOGIN_MSG;
                else if(type==2)
                    temp = USER_SMS_MSG;
                else
                    temp = NORMAL_MSG;
                break;
            case "address":
                break;
            case "coupon":
                break;
            case "debt":
                break;
            case "good":
                break;
            case "trade":
                break;
            case "shopcar":
                break;

        }
        return new Msg(code,temp[code]);
    }

    public Msg(int code,String content){
        this.code = code;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
