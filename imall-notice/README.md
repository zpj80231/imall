# imall-notice

模块说明：主要提供对 WebSocket 和 Rabbit MQ 的支持，包括消息失败重试，重试失败再交给死信队列处理，死信队列处理失败后自动重试，重试失败继续回到私信队列直到被处理。

- 生产者模式：监听asterisk消息事件，产生事件发送到mq，默认开启。

- 消费者模式：监听mq队列，消费指定消息，默认开启。

支持生产者模式，消费者模式的单独开启或关闭。

```yaml
# 项目配置
notice:
  # 生产者模式
  producer:
    enabled: true
  # 消费者模式
  consumer:
    enabled: true
```

`@RabbitListener(queues = "${notice.consumer.queue.a}")` 监听，最好只指定需要监听的队列名，队列名在配置文件中配置。 否则通过 @RabbitListener
绑定的方式，会将声明的队列、交换机、路由等信息在mq服务器新建（没有的话）。 @RabbitListener 作为消费者最好只监听服务端已经给我们分配好的队列。

```java
// 推荐方式
@RabbitListener(queues = "${notice.consumer.queue.b}")
// 拒绝这种方式声明消费者
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = MqConstant.Queue.MusicOnHoldStartEvent, durable = "true", autoDelete = "false"),
        exchange = @Exchange(value = MqConstant.Exchange.ThirdParty, type = ExchangeTypes.TOPIC),
        key = MqConstant.RoutingKey.MusicOnHoldStartEvent
))
```

