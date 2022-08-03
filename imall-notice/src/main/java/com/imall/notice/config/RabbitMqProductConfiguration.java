package com.imall.notice.config;

import com.imall.notice.constant.MqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
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
    public TopicExchange thirdPartyExchange() {
        return ExchangeBuilder.topicExchange(MqConstant.Exchange.ThirdParty).build();
    }

    @Bean
    public TopicExchange retryExchange() {
        return new TopicExchange(MqConstant.Exchange.RetryFailureThirdParty, true, false);
    }

    @Bean
    public TopicExchange deadThirdPartyExchange() {
        return ExchangeBuilder.topicExchange(MqConstant.Exchange.DeadThirdParty).build();
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
                .deadLetterExchange(MqConstant.Exchange.DeadThirdParty)
                .deadLetterRoutingKey(MqConstant.RoutingKey.DeadThirdParty)
                .build();
    }

    @Bean
    public Queue deadThirdPartytQueue() {
        return QueueBuilder.durable(MqConstant.Queue.DeadThirdParty).build();
    }

    /**
     * 3.完成绑定关系   队列和交换机  的绑定
     */
    @Bean
    public Binding hangupEventBingding() {
        return BindingBuilder.bind(hangupEventQueue()).to(thirdPartyExchange()).with(MqConstant.RoutingKey.HangupEvent);
    }

    @Bean
    public Binding deadThirdPartytBingding() {
        return BindingBuilder.bind(deadThirdPartytQueue()).to(deadThirdPartyExchange()).with(MqConstant.RoutingKey.DeadThirdParty);
    }

    /**
     * 重试失败交给死信队列处理
     */
    // @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        // 需要配置交换机和绑定键
        return new RepublishMessageRecoverer(rabbitTemplate, MqConstant.Exchange.RetryFailureThirdParty, MqConstant.RoutingKey.RetryFailureThirdParty);
    }

}
