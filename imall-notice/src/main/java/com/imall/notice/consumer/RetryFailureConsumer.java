package com.imall.notice.consumer;

import com.imall.notice.constant.MqConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费失败记录
 *
 * @author zhangpengjun
 * @date 2022/7/25
 */
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = MqConstant.Queue.RetryFailureThirdParty, durable = "true", autoDelete = "false"),
        exchange = @Exchange(value = MqConstant.Exchange.RetryFailureThirdParty, type = ExchangeTypes.TOPIC),
        key = MqConstant.RoutingKey.RetryFailureThirdParty
))
@Slf4j
public class RetryFailureConsumer {

    @RabbitHandler
    public void retryFailure(String msg, Channel channel, Message message) {
        String eventId = message.getMessageProperties().getHeader("eventId");
        String eventClass = message.getMessageProperties().getHeader("eventClass");
        log.info("RetryFailureConsumer ==> eventId: {}, eventClass: {}, msg: {}", eventId, eventClass, msg);
        // 入库
    }

}
