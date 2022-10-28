package com.imall.ws.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangpengjun
 * @date 2022/10/28
 */
@Slf4j
public class WsSessionManager {

    /**
     * 保存连接 session 的地方
     */
    private static ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

    /**
     * 用户名key值
     */
    public static final String USER_ID_PREFIX = "WEBSOCKET_USERID";

    /**
     * 添加 session
     *
     * @param key
     */
    public static void add(String key, WebSocketSession session) {
        // 添加 session
        SESSION_POOL.put(key, session);
    }

    /**
     * 删除 session,会返回删除的 session
     *
     * @param key
     * @return
     */
    public static WebSocketSession remove(String key) {
        // 删除 session
        return SESSION_POOL.remove(key);
    }

    /**
     * 删除并同步关闭连接
     *
     * @param key
     */
    public static void removeAndClose(String key) {
        WebSocketSession session = remove(key);
        if (session != null) {
            try {
                // 关闭连接
                log.info("用户退出: {}, 当前在线人数为: {}", key, getOnlineCount());
                session.close();
            } catch (IOException e) {
                log.error("用户退出: {} 异常", e);
            }
        }
    }

    /**
     * 获得此时的
     * 在线人数
     *
     * @return
     */
    public static int getOnlineCount() {
        return SESSION_POOL.keySet().size();
    }

    /**
     * 获得 session
     *
     * @param key
     * @return
     */
    public static WebSocketSession get(String key) {
        // 获得 session
        return SESSION_POOL.get(key);
    }

    /**
     * 给某个用户发送消息
     */
    public static void sendMessageToAgent(String agentid, String message) {
        if (SESSION_POOL.get(agentid) != null) {
            try {
                if (SESSION_POOL.get(agentid).isOpen()) {
                    SESSION_POOL.get(agentid).sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                log.error("给用户: {} 发送消息: {} 异常: {}", agentid, message, e);
            }
        } else {
            log.error("给用户: {} 发送消息: {} 异常: {}", agentid, message, "用户已下线");
        }
    }

    /**
     * 给所有在线用户发送消息
     */
    public static void sendMessageToAllAgents(String message) {
        for (String agentid : SESSION_POOL.keySet()) {
            try {
                if (SESSION_POOL.get(agentid).isOpen()) {
                    SESSION_POOL.get(agentid).sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                log.error("给所有用户发送消息, 给用户: {} 发送消息 {}, 发生异常: {}", agentid, message, e);
            }
        }
    }


}
