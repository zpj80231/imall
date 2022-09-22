package com.imall.notice.util;

import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 线程池
 *
 * @author zhangpengjun
 * @date 2022/7/18
 */
public class ThreadPoolExecutorMdcUtils extends ThreadPoolTaskExecutor {

    private static final long serialVersionUID = -8273858932464503828L;

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadPoolMDCFilter.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadPoolMDCFilter.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadPoolMDCFilter.wrap(task, MDC.getCopyOfContextMap()));
    }
}
