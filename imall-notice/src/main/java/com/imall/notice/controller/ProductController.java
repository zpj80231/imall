package com.imall.notice.controller;

import com.imall.notice.constant.MqConstant;
import com.imall.notice.vo.MqMsgVo;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author zhangpengjun
 * @date 2022/6/16
 */
@Controller
public class ProductController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping(value = "/product/{count}/{routingKey}")
    @ResponseBody
    public String product(@PathVariable("count") Integer count, @PathVariable(value = "routingKey", required = false) String routingKey) {

        routingKey = StringUtils.hasText(routingKey) ? routingKey : "aa.topic.aa";

        for (int i = 0; i < count; i++) {
            String msg = routingKey + " 生成消息 --> " + i;
            MqMsgVo mqMsgVo = new MqMsgVo(i, msg, "176****3232", new Date());
            rabbitTemplate.convertAndSend(MqConstant.Exchange.EVENT, routingKey, mqMsgVo);
        }
        return "ok";
    }


    @GetMapping("/sendExpireMessage/{count}/{routingKey}")
    @ResponseBody
    public String sendMessage(@PathVariable("count") Integer count, @PathVariable(value = "routingKey", required = false) String routingKey) {
        for (int i = 0; i < count; i++) {
            MessageProperties messageProperties = new MessageProperties();
            // 设置过期时间,10秒過期
            // 注意：RabbitMQ只会过期淘汰队列头部的消息。如果单独给一条消息设置ttl，
            // 先入队列的消息过期时间如果设置比较长，后入队列的设置时间比较短,那么消息就不会及时的被淘汰，会导致消息的堆积问题。
            messageProperties.setExpiration("10000");


            String msgid = "zhang+" + i;
            MqMsgVo mqMsgVo = new MqMsgVo(i, msgid, "133****3232", new Date());
            rabbitTemplate.convertAndSend(MqConstant.Exchange.EVENT, routingKey, mqMsgVo, (x) -> {
                //这个参数是用来做消息的唯一标识
                //发布消息时使用，存储在消息的headers中
                x.getMessageProperties().setHeader("msg_id", (msgid));
                x.getMessageProperties().setExpiration("5000");
                return x;
            });
        }
        return "ok";
    }


}
