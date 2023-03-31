package com.imall.common.support;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 主要用于在 @Async 使用 Request
 * 使用 @Async 获取 Request 时，内部方法不能用 this（this是本身而不是代理对象） 调用，否则无效。
 *
 * @author zhangpengjun
 * @date 2023/3/31
 */
@Slf4j
@Aspect
@Component
public class InheritableRequestAspect {

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Async)")
    public void car() {

    }

    @Before("car()")
    public void logRequest(JoinPoint joinPoint) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(servletRequestAttributes, true);
    }


}
