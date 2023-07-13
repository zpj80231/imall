package com.imall.common.annotation;

import com.imall.common.constant.DoubleCacheType;

import java.lang.annotation.*;

/**
 * 双缓存，本地 + redis
 * 自定义本地缓存，注入一个 Bean {@link com.github.benmanes.caffeine.cache.Cache}，
 * 否则使用默认本地缓存（过期时间5分钟）
 *
 * @author zhangpengjun
 * @date 2023/7/13
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DoubleCache {

    String cacheName();

    String key();

    long expireOfRedis() default 30 * 60L; // 单位，秒

    DoubleCacheType cacheType() default DoubleCacheType.GET;

}
