package com.imall.notice.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 电话挂机事件
 *
 * @author zhangpengjun
 * @date 2022/6/16
 */
@Component
@RabbitListener(queues = "${notice.consumer.queue.hangupevent}")
@Slf4j
public class HangupEventConsumer extends AbstractEventConsumer {

    @Override
    public String getEventType() {
        return "HangupEvent";
    }

    /**
     * @param msg
     * @param channel
     * @param message 消息
     * @throws Exception
     * @RabbitHandler(isDefault = true) 覆盖父类的监听，不然mq认为有两个，不知道用哪个会报错
     */
    @RabbitHandler(isDefault = true)
    @Override
    public void topicreviceMessage(String msg, Channel channel, Message message) throws Exception {
        log.info("HangupEvent: {}", msg);
    }

}
