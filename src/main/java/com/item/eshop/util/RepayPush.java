package com.item.eshop.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepayPush {
    private static final String MASTER_SECRET = "c28a777b5dfb870d3af3c28c";
    private static final String APP_KEY = "b8ef04d0d2dd3e0cbf14c98a";
    private static final String TITLE = "白条还款-靖军的店";
    private static String ALERT = "";

    public static void init(List<String> tag,String alert){
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());

        ALERT = alert;
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_tag_alert(tag);
        org.slf4j.Logger logger = LoggerFactory.getLogger(RepayPush.class);
        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("Got result - " + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            logger.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            logger.error("Should review the error, and fix the request", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static PushPayload buildPushObject_all_tag_alert(List<String> tag) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.android(ALERT, TITLE, null))
                .build();
    }
}
