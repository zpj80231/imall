package com.imall.common.annotation;

import com.imall.common.config.DoubleCacheAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhangpengjun
 * @date 2023/7/13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({DoubleCacheAutoConfig.class})
@Documented
public @interface EnableDoubleCacheAutoConfig {

}
