package com.imall.task.support;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author zhangpengjun
 * @date 2022/9/20
 */
@Slf4j
@Aspect
@Component
public class XxlJobLoggerInterceptor {

    private static final String TRACE_ID = "traceId";

    @Pointcut("@annotation(com.xxl.job.core.handler.annotation.XxlJob)")
    public void recordLog() {

    }

    @Before("recordLog()")
    public void before(JoinPoint point) {
        String traceId = UUID.randomUUID().toString().replaceAll("-", "");
        MDC.put(TRACE_ID, traceId);
    }

    @After("recordLog()")
    public void after(JoinPoint point) {
        MDC.remove(TRACE_ID);
    }

}
