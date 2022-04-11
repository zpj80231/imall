package com.imall.note.rabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producter {

    private static final String EXCHANGE_NAME = "test_zdy_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        // 从连接开一个通道
        Channel channel = connection.createChannel();
        // 声明一个topic路由交换机，交换机持久化
//        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);

        // 发送消息
        String message = "hello, quick.orange.rabbit";
        /*参数说明：
            String exchange -- 交换机名称
        	String routingKey -- 路由关键字
        	BasicProperties props -- 消息的基本属性，例如路由头等
        	byte[] body -- 消息体
        */
        for (int i = 0; i < 10000; i++) {
            message = "hello, quick.orange.rabbit" + "  -------  " + i;

            //参数：交换机名，路由键，消息持久化，消息体
            channel.basicPublish(EXCHANGE_NAME, "zpj.zdy.rabbit", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent message : '" + message + "'");
        }

        channel.close();
        connection.close();

    }
}
