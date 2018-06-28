package com.item.eshop.util;

import okhttp3.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;

public class Sms {
    private static final String ACCOUNT = "C74841886";
    private static final String AUTH = "ece8d9e0a140c73bcc05f9d07dc14d8f";
    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

    public static int send(String phone) {

        OkHttpClient client = new OkHttpClient();

//        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");

        int mobile_code = (int)((Math.random()*9+1)*100000);

        String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");

        RequestBody formBody = new FormBody.Builder()
                .add("account",ACCOUNT)
                .add("password", AUTH)
                .add("mobile", phone)
                .add("content", content)
                .build();

        Request request = new Request.Builder()
                .url(Url)
                .post(formBody)
                .build();


        try {
            Response rs =  client.newCall(request).execute();
            String SubmitResult =rs.body().string();
            //System.out.println(SubmitResult)
            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");

            System.out.println(code);
            System.out.println(msg);
            System.out.println(smsid);

            if("2".equals(code)){
                System.out.println("短信提交成功");
            }
            return mobile_code;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

}
