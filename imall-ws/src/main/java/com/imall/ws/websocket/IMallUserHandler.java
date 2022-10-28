package com.imall.ws.websocket;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imall.ws.constant.AgentEventConstant;
import com.imall.ws.constant.EventConstant;
import com.imall.ws.constant.WsMessageConstant;
import com.imall.ws.util.WsSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.UUID;

/**
 * @author zhangpengjun
 * @date 2022/10/28
 */
@Slf4j
@Component
public class IMallUserHandler extends TextWebSocketHandler {

    private static final String USER_ID = WsMessageConstant.USER_ID;
    private static final String TRACE_ID = "traceId";

    /**
     * socket 建立成功事件
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String traceId = UUID.randomUUID().toString().replaceAll("-", "");
        MDC.put(TRACE_ID, traceId);
        String userId = (String) session.getAttributes().get(USER_ID);
        // 挤掉之前登录的
        if (WsSessionManager.get(userId) != null) {
            log.info("用户已经登录，挤掉。。");
            WsSessionManager.removeAndClose(userId);
            MDC.put(TRACE_ID, traceId);
        }
        WsSessionManager.add(userId, session);
        // 登录成功
        JSONObject context = getLoginParam(session);
        // context = service.login(context);
        log.info("用户登录: {}, 当前在线人数为: {}", userId, WsSessionManager.getOnlineCount());

        // 用户连接成功，可以放入redis 在线用户缓存
        // ...

        session.sendMessage(new TextMessage(context.toJSONString()));
        MDC.remove(TRACE_ID);
    }

    /**
     * 接收消息事件
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        MDC.put(TRACE_ID, UUID.randomUUID().toString().replaceAll("-", ""));
        // 获得客户端传来的消息
        JSONObject userContext = getLoginParam(session);
        JSONObject payload = JSON.parseObject(message.getPayload());
        payload.putAll(userContext);
        // 心跳，不打印
        if (!AgentEventConstant.HEART_BEAT.equals(payload.getString(EventConstant.eventType))) {
            log.info("ws request payload: {}", JSON.toJSONString(payload));
        }
        // 根据不同事件路由不同的service处理
        // payload = service.dispatchEvents(payload);
        if (!AgentEventConstant.HEART_BEAT.equals(payload.getString(EventConstant.eventType))) {
            log.info("ws response payload: {}", JSON.toJSONString(payload));
        }
        session.sendMessage(new TextMessage(JSON.toJSONString(payload)));
        MDC.remove(TRACE_ID);
    }

    /**
     * 处理来自底层 WebSocket 消息传输的错误
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        MDC.put(TRACE_ID, UUID.randomUUID().toString().replaceAll("-", ""));
        // 连接异常，用户退出
        String userId = (String) session.getAttributes().get(USER_ID);
        log.error("用户: {}, 连接异常", userId, exception);
        session.sendMessage(new TextMessage("连接异常：" + exception.getMessage()));
        JSONObject userContext = userLogout(session);
        log.info("用户: {}, 连接异常, 退出成功！", userContext.getString(USER_ID));
        MDC.remove(TRACE_ID);
    }

    /**
     * socket 断开连接时
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (StrUtil.isBlank(MDC.get(TRACE_ID))) {
            MDC.put(TRACE_ID, UUID.randomUUID().toString().replaceAll("-", ""));
        }
        // 用户退出
        JSONObject userContext = userLogout(session);
        log.info("用户: {}, 退出成功！", userContext.getString(USER_ID));
        MDC.remove(TRACE_ID);
    }

    /**
     * 用户注销
     */
    private JSONObject userLogout(WebSocketSession session) {
        JSONObject userContext = getLoginParam(session);
        // userContext = service.logout();
        WsSessionManager.removeAndClose(userContext.getString(USER_ID));
        return userContext;
    }

    /**
     * 当前信息
     */
    private JSONObject getLoginParam(WebSocketSession session) {
        JSONObject context = new JSONObject();
        context.putAll(session.getAttributes());
        return context;
    }

}
