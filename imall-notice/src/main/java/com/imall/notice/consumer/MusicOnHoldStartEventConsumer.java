package com.imall.notice.consumer;

import com.alibaba.fastjson.JSONObject;
import com.imall.notice.vo.PushInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 电话响铃开始事件
 *
 * @author zhangpengjun
 * @date 2022/6/16
 */
@Component
@RabbitListener(queues = "${notice.consumer.queue.musicOnHoldStartEvent}")
@Slf4j
public class MusicOnHoldStartEventConsumer extends AbstractEventConsumer {

    @Override
    public String getEventType() {
        return "MusicOnHoldStartEvent";
    }

    @Override
    public String getEventTypeChineseName() {
        return "电话响铃开始事件";
    }

    /**
     * 添加额外字段
     *
     * @param event 事件
     * @return {@link Map}<{@link String}, {@link String}>
     */
    @Override
    protected Map<String, String> extraFields(JSONObject event) {
        return super.extraFields(event);
    }

    /**
     * 发送之前可对内容进行修改
     *
     * @param event           事件
     * @param pushInfoDTOList 推动信息dtolist
     * @throws Exception 异常
     */
    @Override
    protected void modifiedContentBeforePush(JSONObject event, List<PushInfoDTO> pushInfoDTOList) throws Exception {
        super.modifiedContentBeforePush(event, pushInfoDTOList);
    }
}
