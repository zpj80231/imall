package com.imall.notice.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangpengjun
 * @date 2022/7/25
 */
@Component
@ConfigurationProperties(prefix = "notice")
public class NoticeConstant {
    /**
     * 生产者模式
     */
    public static Boolean producerModel = true;
    /**
     * 消费者模式
     */
    public static Boolean consumerModel = true;

    public void setProducerModel(Boolean producerModel) {
        NoticeConstant.producerModel = producerModel;
    }

    public void setConsumerModel(Boolean consumerModel) {
        NoticeConstant.consumerModel = consumerModel;
    }
}
