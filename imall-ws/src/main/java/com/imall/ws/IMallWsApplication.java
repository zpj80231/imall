package com.imall.ws;

import cn.hutool.extra.spring.EnableSpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author zhangpengjun
 * @date 2022/10/28
 */
@SpringBootApplication
@EnableSpringUtil
@EnableAsync
public class IMallWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(IMallWsApplication.class, args);
    }

}
