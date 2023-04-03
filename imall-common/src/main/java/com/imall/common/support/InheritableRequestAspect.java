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
 * 也可以在线程池包装处处理：参考 {@link ThreadPoolExecutorMdc#inheritableRequest()}，此处则是单独抽离，按需引入。
 *
 * @author zhangpengjun
 * @date 2023/3/31
 */
@Slf4j
@Aspect
@Component
public class InheritableRequestAspect {

    // 直接拦截Async注解后，在异步方法中获取request时，有时生效有时不生效，可能是个bug
    // @Pointcut("@annotation(org.springframework.scheduling.annotation.Async)")
    // 推荐：直接在 controller 拦截的话就没问题
    @Pointcut("execution(* com.imall..controller..*.*(..))")
    public void car() {

    }

    @Before("car()")
    public void logRequest(JoinPoint joinPoint) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(servletRequestAttributes, true);
    }


}
