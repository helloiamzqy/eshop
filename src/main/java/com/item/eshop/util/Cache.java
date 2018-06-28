package com.item.eshop.util;

import com.item.eshop.config.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;

@Component
public class Cache {

    @Autowired
    private Constant constant;

    private static Constant staticConstant;

    private static int DEADLINE = 5*60;
    private static String IP ;
    private static String AUTH;

    @PostConstruct
    public void init(){
        staticConstant = constant;
    }

    public static Jedis connectRedis() {
        IP = staticConstant.getIp();
        AUTH = staticConstant.getPasswd();
        Jedis jedis = new Jedis(IP);
        jedis.auth(AUTH);
        return jedis;
    }

    //  add　：　chan  2018/4/4
    public static String getRedis(String key){
        Jedis jedis = connectRedis();
        return jedis.get(key);
    }

    //  add　：　chan  2018/4/4
    public static int setRedis(String key,String value){
        Jedis jedis = connectRedis();
        String result = jedis.setex(key,DEADLINE,value);
        if(result=="OK"){
            return 1;
        }
        return 0;
    }

    //  add　：　chan  2018/4/4
    public static int setRedisWithDeadline(String key,String value,int deadline){
        Jedis jedis = connectRedis();
        String result = jedis.setex(key,deadline,value);
        if(result=="OK"){
            return 1;
        }
        return 0;
    }

    //  add　：　chan  2018/4/4
    public static int delRedis(String key){
        Jedis jedis = connectRedis();
        return Integer.parseInt(String.valueOf(jedis.del(key)));

    }

    //  add　：　chan  2018/4/15
    public static int incrRedis(String key){
        Jedis jedis = connectRedis();
        return Integer.parseInt(String.valueOf(jedis.incr(key)));

    }

    //  add　：　chan  2018/4/15
    public static int decrRedis(String key){
        Jedis jedis = connectRedis();
        return Integer.parseInt(String.valueOf(jedis.decr(key)));

    }

    // delete :  chan  2018/4/4
//    public static int verifyPhoneAndCode(String phone,int code){
//        Jedis jedis = connectRedis();
//        String result = jedis.get(phone);
//        System.out.print("result2:"+result);
//        if(Integer.parseInt(result)==code)
//            return 1;
//        return 0;
//    }
//
//    public static int addPhoneAndCode(String phone,int code){
//        Jedis jedis = connectRedis();
//        String result = jedis.setex(phone,DEADLINE,code+"");
//        System.out.print("result:"+result);
//        if(result=="OK"){
//            return 1;
//        }
//        return 0;
//    }
//
//    public static int login(String session_id,String status){
//        Jedis jedis = connectRedis();
//        String result = jedis.setex(session_id,DEADLINE,status);
//        System.out.print("result:"+result);
//        if(result=="OK"){
//            return 1;
//        }
//        return 0;
//    }

}
