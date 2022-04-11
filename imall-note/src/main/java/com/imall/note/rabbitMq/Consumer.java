package com.imall.note.rabbitMq;

import com.rabbitmq.client.*;

import java.io.IOException;

public  class Consumer {

    private static final String QUEUE_NAME = "test_queue_topic_1";
    private static final String EXCHANGE_NAME = "test_zdy_topic";

    //消费者名称
    private String name;
    //休眠时间
    private int sleepTime;

    public Consumer(String name, int sleepTime) {
        this.name = name;
        this.sleepTime = sleepTime;
    }

    public void recv_2() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        Connection connection = factory.newConnection();

        // 打开通道
        Channel channel = connection.createChannel();

        //交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);

        // 申明要消费的队列
        //创建一个持久化 不排他的 非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*.zdy.*");

        // 这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message。换句话说，在接收到该Consumer的ack前，他它不会将新的Message分发给它。
        //mq的公平分发也用到这个
        channel.basicQos(1);

        // 创建一个回调的消费者处理类
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 接收到的消息
                String message = new String(body);
                System.out.println(name + " Received '" + message + "'");

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(name + " 完成： done ");
                    //手动ack回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        // 消费消息
        channel.basicConsume(QUEUE_NAME, false, consumer);

    }

}
