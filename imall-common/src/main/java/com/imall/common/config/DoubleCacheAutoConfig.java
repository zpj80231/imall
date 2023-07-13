package com.imall.common.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.imall.common.support.DoubleCacheAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangpengjun
 * @date 2023/7/13
 */
@Import(DoubleCacheAspect.class)
@Configuration
public class DoubleCacheAutoConfig {

    @Bean
    @ConditionalOnMissingBean(Cache.class)
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                .initialCapacity(128) // 初始大小
                .maximumSize(5000) // 最大数量
                .expireAfterWrite(5, TimeUnit.MINUTES) // 过期时间
                .build();
    }

}
