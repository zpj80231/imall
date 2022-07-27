package com.imall.notice.constant;

/**
 * @author zhangpengjun
 * @date 2022/6/16
 */
public class MqConstant {

    public static class Exchange {
        public static final String EVENT = "topic_event_exchange";
        public static final String RETRY_EXCHANGE = "retry_exchange";
    }

    public static class Queue {
        public static final String RETRY_FAILURE_QUEUE = "retry_failure_queue";
        public static final String HangupEvent = "HangupEvent.topic.queue";
        public static final String BridgeEnterEvent = "BridgeEnterEvent.topic.queue";
        public static final String MusicOnHoldStartEvent = "MusicOnHoldStartEvent";
        public static final String MusicOnHoldStopEvent = "MusicOnHoldStopEvent";
    }

    public static class RoutingKey {
        public static final String RETRY_FAILURE_KEY = "retry.failure.key";
        public static final String HangupEvent = "HangupEvent";
        public static final String BridgeEnterEvent = "BridgeEnterEvent";
        public static final String MusicOnHoldStartEvent = "MusicOnHoldStartEvent";
        public static final String MusicOnHoldStopEvent = "MusicOnHoldStopEvent";
    }

}
