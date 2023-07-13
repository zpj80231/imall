package com.imall.common.config;

import com.imall.common.support.ThreadPoolExecutorMdc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author zhangpengjun
 * @date 2023/3/31
 */
@Slf4j
@EnableAsync
@Configuration
public class BeseAsyncConfig implements AsyncConfigurer {

    @Value("${spring.task.execution.pool.core-size:4}")
    private int corePoolSize;
    @Value("${spring.task.execution.pool.max-size:20}")
    private int maxPoolSize;
    @Value("${spring.task.execution.pool.keep-alive:3}")
    private int keepAliveSeconds;
    @Value("${spring.task.execution.pool.queue-capacity:2000}")
    private int queueCapacity;
    @Value("${spring.task.execution.thread-name-prefix:async-exec-}")
    private String threadNamePrefix;

    @Override
    public Executor getAsyncExecutor() {
        return createThreadPoolTaskExecutor(corePoolSize, maxPoolSize, keepAliveSeconds, queueCapacity, threadNamePrefix);
    }

    /**
     * 创建线程池执行器
     *
     * @param corePoolSize     核心池大小
     * @param maxPoolSize      最大池大小
     * @param keepAliveSeconds 维持几秒钟
     * @param queueCapacity    队列容量
     * @param threadNamePrefix 线程名称前缀
     * @return {@link ThreadPoolTaskExecutor}
     */
    public ThreadPoolTaskExecutor createThreadPoolTaskExecutor(int corePoolSize, int maxPoolSize, int keepAliveSeconds,
                                                               int queueCapacity, String threadNamePrefix) {
        ThreadPoolExecutorMdc threadPoolExecutorMdc = new ThreadPoolExecutorMdc();
        threadPoolExecutorMdc.setCorePoolSize(corePoolSize);
        threadPoolExecutorMdc.setMaxPoolSize(maxPoolSize);
        threadPoolExecutorMdc.setKeepAliveSeconds(keepAliveSeconds);
        threadPoolExecutorMdc.setQueueCapacity(queueCapacity);
        threadPoolExecutorMdc.setThreadNamePrefix(threadNamePrefix);
        threadPoolExecutorMdc.initialize();
        return threadPoolExecutorMdc;
    }

}
