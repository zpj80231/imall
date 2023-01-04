package com.imall.notice.consumer;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imall.notice.service.EventSendingService;
import com.imall.notice.vo.PushInfoDTO;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象事件消费者
 *
 * @author zhangpengjun
 * @date 2022/8/16
 */
@Slf4j
public abstract class AbstractEventConsumer {

    @Autowired
    EventSendingService enSendingService;

    /**
     * topicrevice消息
     *
     * @param msg     味精
     * @param channel
     * @param message 消息
     * @throws Exception 异常
     */
    @RabbitHandler
    public void topicreviceMessage(String msg, Channel channel, Message message) throws Exception {
        String eventType = getEventType();
        String eventTypeChineseName = getEventTypeChineseName();
        String eventId = message.getMessageProperties().getHeader("eventId");
        log.info("====================  {},消息开始处理  ====================", eventTypeChineseName);
        log.info("{}, 收到消息: eventId: {}, msg: {}", eventTypeChineseName, eventId, msg);
        JSONObject event = JSON.parseObject(msg);

        // 1. 可添加的额外字段
        Map<String, String> extraFields = extraFields(event);
        if (CollUtil.isNotEmpty(extraFields)) {
            event.putAll(extraFields);
        }

        // 2. 查找推送信息
        List<PushInfoDTO> pushInfoList = getPushInfo(eventType);
        if (CollUtil.isEmpty(pushInfoList)) {
            log.error("{}, 未找到推送url, 事件类型: {}", eventTypeChineseName, eventType);
            log.error("====================  {},消息处理完毕  ====================", eventTypeChineseName);
            return;
        }

        // 3. 开始推送
        // 3.1 开始推送之前，留给子类覆盖，允许对推送内容event进行改造
        modifiedContentBeforePush(event, pushInfoList);

        // 3.2 事件发送 eventSending
        eventSending(event, pushInfoList);
        log.info("====================  {},消息处理完毕  ====================", eventTypeChineseName);
    }

    /**
     * 查找推送信息，查完后放到缓存中（在更新推送配置是删除即可）
     */
    protected List<PushInfoDTO> getPushInfo(String eventType) {
        // List<PushInfoDTO> pushInfoDTOList = pushInfoService.queryPushInfo(eventType);
        return null;
    }


    /**
     * 事件发送
     */
    protected void eventSending(JSONObject event, List<PushInfoDTO> pushInfoList) {
        for (PushInfoDTO pushInfoDTO : pushInfoList) {
            enSendingService.httpSending(event, pushInfoDTO);
        }
    }

    /**
     * 额外字段
     */
    protected Map<String, String> extraFields(JSONObject event) {
        // 从redis或其它地方获取的额外字段
        Map map = new HashMap<>();
        log.info("需要添加的额外字段（用户自定义字段）: {}", JSON.toJSONString(map));
        return map;
    }

    /**
     * 事件英文名称，不能为空
     *
     * @return
     */
    protected abstract String getEventType();

    /**
     * 事件中文名称（方便看日志），为空则取事件名
     *
     * @return
     */
    protected String getEventTypeChineseName() {
        return getEventType();
    }

    /**
     * 开始推送之前，留给子类覆盖，允许对推送内容event进行改造
     *
     * @param event
     * @param pushInfoDTOList
     */
    protected void modifiedContentBeforePush(JSONObject event, List<PushInfoDTO> pushInfoDTOList) throws Exception {

    }

}
