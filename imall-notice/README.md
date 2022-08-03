# imall-notice

模块说明：主要提供对 WebSocket 和 Rabbit MQ 的支持，包括消息失败重试，重试失败再交给死信队列处理，死信队列处理失败后自动重试，重试失败继续回到私信队列直到被处理。

- 生产者模式：监听asterisk消息事件，产生事件发送到mq，默认开启。

- 消费者模式：监听mq队列，消费指定消息，默认开启。

支持生产者模式，消费者模式的单独开启或关闭。

```yaml
# 项目配置
notice:
  # 生产者模式
  producer-model: true
  # 消费者模式
  consumer-model: true
```


