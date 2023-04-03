package com.imall.common.support;

import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 线程池
 *
 * @author zhangpengjun
 * @date 2022/7/18
 */
public class ThreadPoolExecutorMdc extends ThreadPoolTaskExecutor {

    private static final long serialVersionUID = -8273858932464503828L;

    @Override
    public void execute(Runnable task) {
        inheritableRequest();
        super.execute(ThreadPoolMDCFilter.wrap(task, MDC.getCopyOfContextMap()));
    }

    /**
     * 提交
     *
     * @param task 任务
     * @return {@link Future}<{@link T}>
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        inheritableRequest();
        return super.submit(ThreadPoolMDCFilter.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        inheritableRequest();
        return super.submit(ThreadPoolMDCFilter.wrap(task, MDC.getCopyOfContextMap()));
    }

    /**
     * 在异步线程中，使 HttpServletRequest 可以继续在异步线程中使用
     */
    public void inheritableRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(servletRequestAttributes, true);
    }
}
