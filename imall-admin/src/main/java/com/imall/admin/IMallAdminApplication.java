package com.imall.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhangpengjun
 * @date 2022/8/24
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.imall"})
public class IMallAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(IMallAdminApplication.class, args);
    }

}
