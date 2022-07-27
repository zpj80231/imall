package com.imall.mbg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * IMallMbgApplication
 *
 * @author by zpj80231
 * @Date 2020/9/29 20:51
 */
@SpringBootApplication(scanBasePackages = "com.imall")
public class IMallMbgApplication {
    public static void main(String[] args) {
        SpringApplication.run(IMallMbgApplication.class, args);
    }
}
