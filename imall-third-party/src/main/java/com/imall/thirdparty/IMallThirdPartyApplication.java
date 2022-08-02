package com.imall.thirdparty;

import cn.hutool.extra.spring.EnableSpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 项目启动类
 *
 * @author zhangpengjun
 * @date 2022/7/12
 */
@Slf4j
@SpringBootApplication
@EnableSpringUtil
@EnableFeignClients
public class IMallThirdPartyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(IMallThirdPartyApplication.class, args);
        Environment env = application.getEnvironment();
        String active = env.getProperty("spring.profiles.active");
        String path = env.getProperty("server.servlet.context-path");
        String port = env.getProperty("server.port");
        // 最大文件大小
        String maxFileSize = env.getProperty("spring.servlet.multipart.max-file-size");
        // 最大请求大小
        String maxRequestSize = env.getProperty("spring.servlet.multipart.max-request-size");
        log.info(
                "\n----------------------------------------------------------\n\t\t" +
                        "IMallThirdPartyApplication is running! \n\t\t" +
                        "spring-profiles-active: {} \n\t\t" +
                        "prot: {} \n\t\t" +
                        "path: {} \n\t\t" +
                        "max-file-size: {}\n\t\t" +
                        "max-request-size: {} " +
                        "\n----------------------------------------------------------",
                active, port, path, maxFileSize, maxRequestSize);
    }

}
