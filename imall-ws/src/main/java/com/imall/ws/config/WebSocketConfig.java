package com.imall.ws.config;

import com.imall.ws.websocket.IMallUserHandler;
import com.imall.ws.websocket.IMallUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * websocket 配置
 *
 * @author zhangpengjun
 * @date 2022/10/28
 */
@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private IMallUserInterceptor iMallUserInterceptor;
    @Autowired
    private IMallUserHandler iMallUserHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(iMallUserHandler, "user").addInterceptors(iMallUserInterceptor).setAllowedOrigins("*");
    }
}
