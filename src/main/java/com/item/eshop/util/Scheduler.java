package com.item.eshop.util;

import com.item.eshop.mapper.DebtMapper;
import com.item.eshop.mapper.TradeMapper;
import com.item.eshop.model.Debt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;

//@Configuration
//@EnableScheduling
//public class Scheduler {  //更新订单状态
//    @Bean
//    public TaskScheduler taskScheduler() {
//        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
//        scheduler.setPoolSize(3);
//        scheduler.setThreadNamePrefix("spring-task-thread");
//        return scheduler;
//    }
//}

@Component
class UpdateStatusTask {
    @Autowired
    TradeMapper tradeMapper;
    @Autowired
    DebtMapper debtMapper;

    @Scheduled(cron = "0 0 3 * * ?")
    public void updatePaidTrade() {
        System.out.println("task.......");
        tradeMapper.updateTradeSuccess();
    }

    @Scheduled(cron = "0 0 3 1 * ?")
    public void updateToPay() {
        debtMapper.updateValidToPay();
    }

    @Scheduled(cron = "0 0 3 11 * ?")
    public void updateToExpire() {
        debtMapper.updateValidToExpire();
    }

    @Scheduled(cron = "0 0 12 1 * ?")
    public void pushFirstRepay() {
        String ALERT = "请及时还款，还款后才可继续使用白条，还款期限：本月10号前";
        pushAlert(ALERT);
    }

    @Scheduled(cron = "0 0 12 10 * ?")
    public void pushLastRepay() {
        String ALERT = "今天是最后还款日，若未还款，此账号以后将禁止使用白条";
        pushAlert(ALERT);
    }

    public void pushAlert(String alert){
        Integer[] ids = debtMapper.selectForRePay();
        List<String> result = new ArrayList<>();
        for (int id : ids) {
            result.add(id + "");
        }
        RepayPush.init(result, alert);
    }

//    @Scheduled(fixedRate = 360000)
//    public void push(){
//        List<String> res = new ArrayList<>();
//        res.add("2");
//        res.add("4");
//        RepayPush.init(res,"今晚国堂请吃宵夜");
//    }

}