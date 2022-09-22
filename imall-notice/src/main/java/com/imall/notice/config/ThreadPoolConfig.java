package com.imall.notice.config;

import com.imall.notice.constant.ThreadPoolConstant;
import com.imall.notice.util.ThreadPoolExecutorMdcUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author zhangpengjun
 * @date 2022/9/22
 */
@Configuration
public class ThreadPoolConfig {

    @Value("${spring.task.execution.pool.core-size}")
    private int corePoolSize;
    @Value("${spring.task.execution.pool.max-size}")
    private int maxPoolSize;
    @Value("${spring.task.execution.pool.keep-alive}")
    private int keepAliveSeconds;
    @Value("${spring.task.execution.pool.queue-capacity}")
    private int queueCapacity;

    @Bean(name = ThreadPoolConstant.HTTP_SENDING_POOL)
    public ThreadPoolTaskExecutor asyncExecutor() {
        ThreadPoolExecutorMdcUtils httpSendPool = new ThreadPoolExecutorMdcUtils();
        httpSendPool.setCorePoolSize(corePoolSize);
        httpSendPool.setMaxPoolSize(maxPoolSize);
        httpSendPool.setKeepAliveSeconds(keepAliveSeconds);
        httpSendPool.setQueueCapacity(queueCapacity);
        httpSendPool.setThreadNamePrefix("async-http-sending-");
        return httpSendPool;
    }

}
