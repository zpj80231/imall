package com.imall.notice.manager.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.imall.notice.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.asteriskjava.manager.AbstractManagerEventListener;
import org.asteriskjava.manager.event.BridgeEnterEvent;
import org.asteriskjava.manager.event.CdrEvent;
import org.asteriskjava.manager.event.DialBeginEvent;
import org.asteriskjava.manager.event.DialEndEvent;
import org.asteriskjava.manager.event.HangupEvent;
import org.asteriskjava.manager.event.ManagerEvent;
import org.asteriskjava.manager.event.MusicOnHoldEvent;
import org.asteriskjava.manager.event.MusicOnHoldStartEvent;
import org.asteriskjava.manager.event.MusicOnHoldStopEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhangpengjun
 * @date 2022/6/16
 */
@Slf4j
public class ManagerEventListener extends AbstractManagerEventListener {

    private RabbitTemplate rabbitTemplate;
    private Map<String, Integer> map = new LinkedHashMap<>();

    @Override
    public void handleEvent(CdrEvent event) {
        //在生成呼叫详细记录时触发，通常在呼叫结束时。
    }

    @Override
    public void handleEvent(DialBeginEvent event) {
        // log.info("拨号开始 DialBeginEvent: {}", event);
    }

    @Override
    public void handleEvent(DialEndEvent event) {
        // log.info("拨号结束 DialEndEvent: {}", event);
    }

    @Override
    public void handleEvent(MusicOnHoldEvent event) {
        // log.info("MusicOnHoldEvent：[{}]", event);
    }

    /**
     * 接听人响铃开始
     *
     * @param event
     */
    @Override
    public void handleEvent(MusicOnHoldStartEvent event) {
        String eventId = event.getCallerIdNum() + ":"
                + event.getClass().getSimpleName() + ":"
                + event.getChannel() + ":"
                + event.getLinkedId();
        sendRabbitMq(event, eventId);
    }

    /**
     * 接听人响铃结束
     *
     * @param event
     */
    @Override
    public void handleEvent(MusicOnHoldStopEvent event) {
        String eventId = event.getCallerIdNum() + ":"
                + event.getClass().getSimpleName() + ":"
                + event.getChannel() + ":"
                + event.getLinkedId();
        sendRabbitMq(event, eventId);
    }

    /**
     * 电话接听时事件
     *
     * @param event
     */
    @Override
    public void handleEvent(BridgeEnterEvent event) {
        String eventId = event.getCallerIdNum() + ":"
                + event.getClass().getSimpleName() + ":"
                + event.getChannel() + ":"
                + event.getLinkedId();
        sendRabbitMq(event, eventId);
    }

    /**
     * 电话挂断时事件
     *
     * @param event
     */
    @Override
    public void handleEvent(HangupEvent event) {
        String eventId = event.getCallerIdNum() + ":"
                + event.getClass().getSimpleName() + ":"
                + event.getChannel() + ":"
                + event.getLinkedId();
        sendRabbitMq(event, eventId);
        log.warn(JSON.toJSONString(map));
    }

    /**
     * @param event
     * @param eventId 用作每种事件的唯一id
     */
    private void sendRabbitMq(ManagerEvent event, String eventId) {
        String strEvent = JSON.toJSONString(event);
        String routingKey = MqConstant.routingKeyPrefix + event.getClass().getSimpleName();
        if (log.isDebugEnabled()) {
            log.debug("GenerateEvent -> exchange: {}, routingKey: {}, eventId: {}, event: {}",
                    MqConstant.Exchange.IMALL, routingKey, eventId, strEvent);
        }
        rabbitTemplate.convertAndSend(MqConstant.Exchange.IMALL, routingKey, strEvent, (x) -> {
            //这个参数是用来做消息的唯一标识
            //发布消息时使用，存储在消息的headers中
            x.getMessageProperties().setHeader("eventId", (eventId));
            x.getMessageProperties().setHeader("eventClass", (event.getClass().getName()));
            // x.getMessageProperties().setExpiration("5000");
            return x;
        });
    }

    @Override
    public void onManagerEvent(ManagerEvent event) {
        String eventName = event.getClass().getSimpleName();
        if (!map.containsKey(eventName)) {
            map.put(eventName, 1);
        } else {
            map.put(eventName, map.get(eventName) + 1);
        }
        if (rabbitTemplate == null) {
            RabbitTemplate rabbitTemplate = SpringUtil.getBean(RabbitTemplate.class);
            this.rabbitTemplate = rabbitTemplate;
        }
        super.onManagerEvent(event);
    }

}

