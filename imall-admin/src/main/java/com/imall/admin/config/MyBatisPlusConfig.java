package com.imall.admin.config;

import com.imall.common.config.BaseMyBatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhangpengjun
 * @date 2022/7/18
 */
@Import(BaseMyBatisPlusConfig.class)
@MapperScan(basePackages = {"com.imall.mbg.mapper", "com.imall.admin.service.dao"})
@Configuration
public class MyBatisPlusConfig {

}

