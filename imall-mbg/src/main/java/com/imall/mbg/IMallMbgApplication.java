package com.imall.mbg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author admin
 * @ClassName com.zpj80231.imall.mbg.IMallMbgApplication
 * @Version 1.0
 * @Date 2020/9/29 20:51
 * @Created by zpj80231
 */
@SpringBootApplication(scanBasePackages = "com.imall")
public class IMallMbgApplication {
    public static void main(String[] args) {
        SpringApplication.run(IMallMbgApplication.class, args);
    }
}
