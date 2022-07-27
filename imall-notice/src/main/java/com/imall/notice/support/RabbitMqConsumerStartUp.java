package com.imall.notice.support;

import com.imall.notice.constant.NoticeConstant;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 消费者模式开启
 *
 * @author zhangpengjun
 * @date 2022/7/25
 */
@Component
public class RabbitMqConsumerStartUp implements ApplicationRunner {

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    private void consumerStartUp() {
        if (NoticeConstant.consumerModel) {
            rabbitListenerEndpointRegistry.getListenerContainers().forEach(c -> {
                if (!c.isRunning()) {
                    c.start();
                }
            });
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        consumerStartUp();
    }
}
