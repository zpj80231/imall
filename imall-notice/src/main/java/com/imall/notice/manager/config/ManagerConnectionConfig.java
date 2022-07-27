package com.imall.notice.manager.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangpengjun
 * @date 2022/6/17
 */
@Configuration
@Slf4j
@ConfigurationProperties(prefix = "asterisk")
@Data
public class ManagerConnectionConfig {

    private String hostname;
    private int port;
    private String username;
    private String password;

    @Bean
    public ManagerConnection managerConnection() {
        ManagerConnectionFactory factory = new ManagerConnectionFactory(hostname, port, username, password);
        ManagerConnection managerConnection = factory.createManagerConnection();
        return managerConnection;
    }

}
