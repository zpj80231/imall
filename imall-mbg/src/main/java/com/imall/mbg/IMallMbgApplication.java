package com.imall.mbg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * IMallMbgApplication
 *
 * @author zhangpengjun
 * @date 2020/9/29
 */
@SpringBootApplication(scanBasePackages = "com.imall")
public class IMallMbgApplication {
    public static void main(String[] args) {
        SpringApplication.run(IMallMbgApplication.class, args);
    }
}
