package com.imall.notice;

import cn.hutool.extra.spring.EnableSpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * IMallNoticeApplication
 *
 * @author zhangpengjun
 * @date 2022/6/16
 */
@SpringBootApplication
@EnableSpringUtil
@EnableAsync
public class IMallNoticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(IMallNoticeApplication.class, args);
    }

}
