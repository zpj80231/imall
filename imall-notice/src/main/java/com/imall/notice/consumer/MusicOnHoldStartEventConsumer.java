package com.imall.notice.consumer;

import com.imall.notice.utils.AsteriskMQGetMsgUtil;
import com.imall.notice.webSocket.WebSocketServer;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 电话响铃开始事件
 *
 * @author zhangpengjun
 * @date 2022/6/16
 */
@Component
@RabbitListener(queues = "${notice.consumer.queue.musicOnHoldStartEvent}")
@Slf4j
public class MusicOnHoldStartEventConsumer {

    @RabbitHandler
    public void topicreviceMessage(String msg, Channel channel, Message message) {
        // try {
        // 消费成功
        log.info("电话响铃开始: {}", msg);
        // 去重，过期时间10分钟，redis有说明推送过了，忽略即可
        String eventId = message.getMessageProperties().getHeader("eventId");

        // 同一个事件只推送一次，第一次正常推送后保存redis
        String callerIdNum = AsteriskMQGetMsgUtil.callerIdNum(msg);
        String webMsg = callerIdNum + "电话响铃开始, [" + eventId + "]";
        WebSocketServer.sendInfo(webMsg, callerIdNum);

        // 手动ACK
        //     channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        // } catch (Exception e) {
        //     try {
        //         // 消费失败后需要重新入队的（设置为true） false消费失败后丢弃
        //         channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        //     } catch (Exception e1) {
        //         log.error("BridgeEnterEventConsumer 消费失败后重新入队, e:{}, MqMsgVo=[{}]", e1, msg);
        //     }
        // }
    }

}
