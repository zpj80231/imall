package com.imall.common.support;

import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import com.imall.common.annotation.DoubleCache;
import com.imall.common.constant.DoubleCacheType;
import com.imall.common.utils.ElParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangpengjun
 * @date 2023/7/13
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DoubleCacheAspect {

    private final Cache<String, Object> cache;
    private final RedisTemplate<Object, Object> redisTemplate;

    @Pointcut("@annotation(com.imall.common.annotation.DoubleCache)")
    public void car() {
    }

    @Around("car()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        // 拼接解析springEl表达式的map
        String[] paramNames = signature.getParameterNames();
        Object[] args = point.getArgs();
        TreeMap<String, Object> treeMap = new TreeMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            treeMap.put(paramNames[i], args[i]);
        }

        DoubleCache annotation = method.getAnnotation(DoubleCache.class);
        String elResult = ElParser.parse(annotation.key(), treeMap);
        String realKey = annotation.cacheName() + ":" + elResult;

        // 强制更新
        long expireOfRedis = annotation.expireOfRedis();
        if (annotation.cacheType() == DoubleCacheType.PUT) {
            Object object = point.proceed();
            log.info("DoubleCache PUT, realKey: {}, expireOfRedis: {}", realKey, expireOfRedis);
            redisTemplate.opsForValue().set(realKey, object, expireOfRedis, TimeUnit.SECONDS);
            cache.put(realKey, object);
            return object;
        }
        // 删除
        else if (annotation.cacheType() == DoubleCacheType.DELETE) {
            log.info("DoubleCache DELETE, realKey: {}", realKey);
            redisTemplate.delete(realKey);
            cache.invalidate(realKey);
            return point.proceed();
        }

        // 读写，查询Caffeine
        Object caffeineCache = cache.getIfPresent(realKey);
        if (Objects.nonNull(caffeineCache)) {
            log.info("DoubleCache GET from caffeine, realKey: {}", realKey);
            return caffeineCache;
        }

        // 查询Redis
        Object redisCache = redisTemplate.opsForValue().get(realKey);
        if (Objects.nonNull(redisCache)) {
            log.info("DoubleCache GET from redis, realKey: {}", realKey);
            cache.put(realKey, redisCache);
            return redisCache;
        }

        log.info("DoubleCache GET from database, realKey: {}", realKey);
        Object object = point.proceed();
        if (Objects.nonNull(object)) {
            log.info("DoubleCache GET from database and SET, realKey: {}, expireOfRedis: {}, value: {}",
                    realKey, expireOfRedis, JSON.toJSONString(object));
            // 写入Redis
            redisTemplate.opsForValue().set(realKey, object, expireOfRedis, TimeUnit.SECONDS);
            // 写入Caffeine
            cache.put(realKey, object);
        }
        return object;
    }

}

