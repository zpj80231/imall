package com.imall.notice.constant;

/**
 * @author zhangpengjun
 * @date 2022/6/16
 */
public class MqConstant {

    public static class Exchange {
        public static final String ThirdParty = "Topic_ThirdParty_Exchange";
        public static final String RetryFailureThirdParty = "Retry_Failure_ThirdParty_Exchange";
        public static final String DeadThirdParty = "Dead_ThirdParty_Exchange";
    }

    public static class Queue {
        public static final String RetryFailureThirdParty = "Retry_Failure_Queue";
        public static final String DeadThirdParty = "Dead_ThirdParty_Queue";
        public static final String HangupEvent = "HangupEvent_Queue";
        public static final String HangupEvent_ToolBar = "HangupEvent_ToolBar_Queue";
        public static final String BridgeEnterEvent = "BridgeEnterEvent_Queue";
        public static final String MusicOnHoldStartEvent = "MusicOnHoldStartEvent_Queue";
        public static final String MusicOnHoldStopEvent = "MusicOnHoldStopEvent_Queue";
    }

    public static class RoutingKey {
        public static final String RetryFailureThirdParty = "retry.failure.key";
        public static final String DeadThirdParty = "dead.thirdparty.key";
        public static final String HangupEvent = "HangupEvent";
        public static final String BridgeEnterEvent = "BridgeEnterEvent";
        public static final String MusicOnHoldStartEvent = "MusicOnHoldStartEvent";
        public static final String MusicOnHoldStopEvent = "MusicOnHoldStopEvent";
    }

}
