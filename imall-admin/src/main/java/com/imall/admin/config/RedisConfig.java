package com.imall.admin.config;

import com.imall.common.config.BaseRedisConfig;
import com.imall.common.utils.RedisUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhangpengjun
 * @date 2022/8/24
 */
@Import({BaseRedisConfig.class, RedisUtil.class})
@Configuration
public class RedisConfig extends BaseRedisConfig {

}
