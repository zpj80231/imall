package com.imall.notice.config;

import com.imall.notice.constant.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author zhangpengjun
 * @date 2022/6/16
 */
@Configuration
public class RabbitMqProductConfiguration {

    /**
     * 1.声明注册 TopicExchange 模式的交换机
     */
    @Bean
    public TopicExchange imallExchange() {
        return ExchangeBuilder.topicExchange(MqConstant.Exchange.IMALL).build();
    }

    @Bean
    public TopicExchange deadImallExchange() {
        return ExchangeBuilder.topicExchange(MqConstant.Exchange.DeadIMALL).build();
    }

    /**
     * 2.声明队列
     */
    @Bean
    public Queue hangupEventQueue() {
        HashMap<String, Object> map = new HashMap<>();
        // 支持0-255个优先级，但建议0-10个优先级。数字越大优先级越高，对cpu和内存使用会增加。
        map.put("x-max-priority", 10);
        // 设置队列未被消费的消息过期时间，20秒过期
        map.put("x-message-ttl", 20 * 1000);
        return QueueBuilder.durable(MqConstant.Queue.HangupEvent)
                .deadLetterExchange(MqConstant.Exchange.DeadIMALL)
                .deadLetterRoutingKey(MqConstant.RoutingKey.HangupEvent)
                .build();
    }

    @Bean
    public Queue musicOnHoldStartEventQueue() {
        return QueueBuilder.durable(MqConstant.Queue.MusicOnHoldStartEvent)
                .deadLetterExchange(MqConstant.Exchange.DeadIMALL)
                .deadLetterRoutingKey(MqConstant.RoutingKey.MusicOnHoldStartEvent)
                .build();
    }

    /**
     * 重试失败交给死信队列处理
     */
    @Bean
    public Queue deadImallQueue() {
        return QueueBuilder.durable(MqConstant.Queue.DeadIMALL).build();
    }

    /**
     * 3.完成绑定关系 队列和交换机 的绑定
     */
    @Bean
    public Binding hangupEventBingding() {
        return BindingBuilder.bind(hangupEventQueue()).to(imallExchange()).with(MqConstant.RoutingKey.HangupEvent);
    }

    @Bean
    public Binding musicOnHoldStartEventBingding() {
        return BindingBuilder.bind(musicOnHoldStartEventQueue()).to(imallExchange()).with(MqConstant.RoutingKey.MusicOnHoldStartEvent);
    }

    @Bean
    public Binding deadThirdPartytBingding() {
        return BindingBuilder.bind(deadImallQueue()).to(deadImallExchange()).with(MqConstant.RoutingKey.DeadIMALL);
    }

}
