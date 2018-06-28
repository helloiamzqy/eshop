package com.item.eshop.util;

import java.util.Calendar;
import java.util.Date;

public class TradeID {
    public static String  getID(){
        Calendar date = Calendar.getInstance();
        String ID = date.get(Calendar.YEAR)+"";
        int time;
        if((time=date.get(Calendar.MONTH)+1)<10){
            ID+="0"+time;
        }
        else
            ID+=time;
        if((time=date.get(Calendar.DAY_OF_MONTH))<10){
            ID+="0"+time;
        }
        else
            ID+=time;
        for(int i=0;i<8;i++){
            time = (int)(Math.random()*10);
            ID+=time;
        }
        return ID;
    }

    public static String getDebtRecordId() {
        Calendar date = Calendar.getInstance();
        String ID = date.get(Calendar.YEAR)+"";
        ID = ID.substring(2,4);
        int time;
        if((time=date.get(Calendar.MONTH)+1)<10){
            ID+="0"+time;
        }
        else
            ID+=time;
        if((time=date.get(Calendar.DAY_OF_MONTH))<10){
            ID+="0"+time;
        }
        else
            ID+=time;
        for(int i=0;i<6;i++){
            time = (int)(Math.random()*10);
            ID+=time;
        }
        return ID;
    }
}
