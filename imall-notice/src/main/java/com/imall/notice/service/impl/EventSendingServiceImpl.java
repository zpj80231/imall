package com.imall.notice.service.impl;

import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.imall.notice.constant.ThreadPoolConstant;
import com.imall.notice.service.EventSendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author zhangpengjun
 * @date 2022/10/10
 */
@Slf4j
@Service
public class EventSendingServiceImpl implements EventSendingService {

    @Async(ThreadPoolConstant.HTTP_SENDING_POOL)
    @Override
    public void httpSending(JSONObject event, Object pushInfo) {
        HttpResponse httpResponse = null;
        String url = "http://localhost:8200/notice/receiveMessage";
        // 要发送的字段，从pushInfo里获取
        HashSet<Object> fielNames = new HashSet<>();

        try {
            String jsonString = toJSONString(event, fielNames);
            log.info("发送的数据：{}", jsonString);
            httpResponse = HttpRequest.post(url)
                    .header(Header.CONTENT_TYPE, ContentType.JSON.getValue())
                    .body(jsonString).timeout(3000).executeAsync();
        } catch (Exception e) {
            log.error("失败（网络异常） url: {}, Exception: {}", url, e);
            // todo 发送失败，重发
            return;
        }

        if (httpResponse.isOk()) {
            log.info("（成功）url: {}, http响应状态码: {}, 响应信息: {}", url, httpResponse.getStatus(), httpResponse.body());
        } else {
            log.info("（客户失败，无需再次推送）url: {}, http响应状态码: {}, 响应信息: {}", url, httpResponse.getStatus(), httpResponse.body());
        }
    }

    private String toJSONString(Object obj, Collection collection) {
        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
        if (collection != null && collection.size() > 1) {
            simplePropertyPreFilter.getIncludes().addAll(collection);
        }
        return JSON.toJSONString(obj, simplePropertyPreFilter);
    }
}
