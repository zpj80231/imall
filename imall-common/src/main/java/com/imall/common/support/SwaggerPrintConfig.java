package com.imall.common.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * 项目启动后输出swagger地址
 *
 * @author zhangpengjun
 * @date 2022/7/20
 */
@Component
@Slf4j
@ConditionalOnProperty(prefix = "swagger", name = "show", havingValue = "true")
public class SwaggerPrintConfig implements ApplicationListener<WebServerInitializedEvent> {

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        try {
            // 获取IP
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            // 获取端口号
            int port = event.getWebServer().getPort();
            // 获取应用名
            String applicationName = event.getApplicationContext().getApplicationName();
            // 打印 swagger2 文档地址
            log.info("swagger2 api: http://" + hostAddress + ":" + port + applicationName + "/doc.html");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
