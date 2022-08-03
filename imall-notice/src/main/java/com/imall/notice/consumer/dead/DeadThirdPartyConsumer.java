package com.imall.notice.consumer.dead;

import com.imall.notice.constant.MqConstant;
import com.imall.notice.utils.AsteriskMQGetMsgUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 * 死信队列
 *
 * @author zhangpengjun
 * @date 2022/6/16
 */
@Component
@RabbitListener(queues = MqConstant.Queue.DeadThirdParty)
@Slf4j
public class DeadThirdPartyConsumer {

    @RabbitHandler
    public void deadThirdPartyMessage(String msg, Channel channel, Message message) throws IOException {
        String callerIdNum = AsteriskMQGetMsgUtil.callerIdNum(msg);
        String eventId = message.getMessageProperties().getHeader("eventId");
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        log.info("DeadThirdPartyConsumer 收到事件: {}, headers: {}", msg, headers);
        try {
            int i = new Random().nextInt(10);
            log.info("i = {}", i);
            // 模拟异常
            if (i > 3) {
                int a = 123 / 0;
            }
            log.info("i = {}, 消息正常处理~", i);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 消息拒绝，重新入队
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

}
