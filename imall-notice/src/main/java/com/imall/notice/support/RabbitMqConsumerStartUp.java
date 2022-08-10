package com.imall.notice.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 消费者模式开启
 *
 * @author zhangpengjun
 * @date 2022/7/25
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "notice.consumer", name = "enabled", havingValue = "true")
public class RabbitMqConsumerStartUp implements ApplicationRunner {

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    private void consumerStartUp() {
        rabbitListenerEndpointRegistry.getListenerContainers().forEach(c -> {
            if (!c.isRunning()) {
                c.start();
            }
        });
        log.info("imall notice all consumers are started");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        consumerStartUp();
    }
}
