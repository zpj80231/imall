package com.imall.notice.consumer;

import com.imall.notice.util.AsteriskMQGetMsgUtils;
import com.imall.notice.webSocket.WebSocketServer;
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
public class HangupEventConsumer {

    @RabbitHandler
    public void hangupEventConsumerMessage(String msg, Channel channel, Message message) {
        // try {
        // 消费成功
        log.info("电话挂机: {}", msg);
        String callerIdNum = AsteriskMQGetMsgUtils.callerIdNum(msg);
        String eventId = message.getMessageProperties().getHeader("eventId");
        String webMsg = callerIdNum + "挂断电话, [" + eventId + "]";
        WebSocketServer.sendInfo(webMsg, callerIdNum);
        // 手动ACK
        // channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        // } catch (Exception e) {
        //     try {
        //         // 消费失败后需要重新入队的（设置为true） false消费失败后丢弃
        //         // channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        //     } catch (Exception e1) {
        //         log.error("HangupEventConsumer 消费失败后重新入队,MqMsgVo=[{}]", msg);
        //     }
        // }
    }

}
