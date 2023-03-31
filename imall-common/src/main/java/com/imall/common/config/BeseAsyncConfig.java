package com.imall.common.config;

import com.imall.common.constant.ThreadPoolConstant;
import com.imall.common.support.ThreadPoolExecutorMdc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author zhangpengjun
 * @date 2023/3/31
 */
@Slf4j
@EnableAsync
@Configuration
public class BeseAsyncConfig {

    @Value("${spring.task.execution.pool.core-size:2}")
    private int corePoolSize;
    @Value("${spring.task.execution.pool.max-size:20}")
    private int maxPoolSize;
    @Value("${spring.task.execution.pool.keep-alive:3}")
    private int keepAliveSeconds;
    @Value("${spring.task.execution.pool.queue-capacity:2000}")
    private int queueCapacity;
    @Value("${spring.task.execution.thread-name-prefix:sync-exec-}")
    private String threadNamePrefix;

    @Bean(name = ThreadPoolConstant.ASYNC_DEFAULT_POOL)
    public ThreadPoolTaskExecutor asyncExecutor() {
        ThreadPoolExecutorMdc httpSendPool = new ThreadPoolExecutorMdc();
        httpSendPool.setCorePoolSize(corePoolSize);
        httpSendPool.setMaxPoolSize(maxPoolSize);
        httpSendPool.setKeepAliveSeconds(keepAliveSeconds);
        httpSendPool.setQueueCapacity(queueCapacity);
        httpSendPool.setThreadNamePrefix(threadNamePrefix);
        return httpSendPool;
    }

}
