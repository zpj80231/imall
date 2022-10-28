package com.imall.ws.websocket;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.imall.ws.constant.WsMessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhangpengjun
 * @date 2022/10/28
 */
@Slf4j
@Component
public class IMallUserInterceptor implements HandshakeInterceptor {

    private static final String TRACE_ID = "traceId";

    /**
     * 握手前，校验token等
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        MDC.put(TRACE_ID, UUID.randomUUID().toString().replaceAll("-", ""));
        // 获得请求参数
        Map<String, String> paramMap = HttpUtil.decodeParamMap(request.getURI().getQuery(), StandardCharsets.UTF_8);
        String token = paramMap.get(WsMessageConstant.TOKEN);
        String urlParamJSON = JSON.toJSONString(paramMap);
        log.info("新的连接, 开始握手, {}", urlParamJSON);

        // 放入请求域
        attributes.putAll(paramMap);

        // 校验token
        if (!"safsdf".equals(token)) {
            log.error("握手失败，token校验错误! {}", urlParamJSON);
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return false;
        }

        log.info("握手成功! {}", urlParamJSON);
        return true;
    }

    /**
     * 握手后
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        MDC.remove(TRACE_ID);
    }
}
