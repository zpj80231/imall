package com.imall.notice.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhangpengjun
 * @date 2022/6/16
 */
@Component
public class MqConstant {

    private static String systemName;
    public static String routingKeyPrefix;

    @Value("${asterisk.systemname}")
    public void setSystemName(String systemName) {
        MqConstant.systemName = systemName;
        MqConstant.routingKeyPrefix = systemName + ".imall.key.";
    }

    public static class Exchange {
        public static final String IMALL = systemName + ".imall.exchange";
        public static final String DeadIMALL = systemName + ".imall.exchange.Dead";
    }

    public static class Queue {
        public static final String DeadIMALL = systemName + ".imall.queue.Dead";
        public static final String HangupEvent = systemName + ".imall.queue.HangupEvent";
        public static final String MusicOnHoldStartEvent = systemName + ".imall.queue.MusicOnHoldStartEvent";
    }

    public static class RoutingKey {
        public static final String DeadIMALL = systemName + ".imall.key.*";
        public static final String HangupEvent = systemName + ".imall.key.HangupEvent";
        public static final String MusicOnHoldStartEvent = systemName + ".imall.key.MusicOnHoldStartEvent";
    }

}
