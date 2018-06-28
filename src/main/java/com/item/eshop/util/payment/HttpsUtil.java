package com.item.eshop.util.payment;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

public class HttpsUtil {

    /**
     *
     * @param requestUrl
     * @param requestMethod
     * @param outputStr
     * @return
     * 发送https请求
     */
    public static String httpsRequest(String requestUrl,String requestMethod,String outputStr){
        StringBuffer buffer=null;
        try{
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("SSL");
            TrustManager[] tm={new TrustManager()};
            sslContext.init(null, tm, new java.security.SecureRandom());;
            SSLSocketFactory ssf=sslContext.getSocketFactory();
            URL url=new URL(requestUrl);
            HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            conn.setSSLSocketFactory(ssf);
            conn.connect();
            if(null!=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            //读取服务器端返回的内容
            InputStream is=conn.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            buffer=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }
}