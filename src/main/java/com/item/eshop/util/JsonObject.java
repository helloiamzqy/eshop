package com.item.eshop.util;

import com.google.gson.Gson;
import com.item.eshop.model.*;

public class JsonObject {       //对象数据转换JSON格式

    public static String toJson(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static Object toObject(String json,String name){
        Gson gson = new Gson();
        switch (name){
            case "address":
                return gson.fromJson(json,Address.class);

            case "coupon":
                return gson.fromJson(json,Coupon.class);

            case "debt":
                return gson.fromJson(json,Debt.class);

            case "good":
                return gson.fromJson(json,Good.class);

            case "recommend":
                return gson.fromJson(json,Recommend.class);

            case "shopcarGood":
                return gson.fromJson(json,ShopcarGood.class);

            case "trade":
                return gson.fromJson(json,Trade.class);

            case "tradeGood":
                return gson.fromJson(json,TradeGood.class);

            case "userCoupon":
                return gson.fromJson(json,UserCoupon.class);

            case "goodCategory":
                return gson.fromJson(json,GoodCategory.class);

            default:
                return gson.fromJson(json,User.class);
        }
    }

}
