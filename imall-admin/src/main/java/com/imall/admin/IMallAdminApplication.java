package com.imall.admin;

import com.imall.common.exception.GlobalExceptionHandler;
import com.imall.common.support.ResponseAdvice;
import com.imall.common.support.TraceIdLogging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author zhangpengjun
 * @date 2022/8/24
 */
@Import({GlobalExceptionHandler.class, ResponseAdvice.class, TraceIdLogging.class})
@SpringBootApplication
public class IMallAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(IMallAdminApplication.class, args);
    }

}
