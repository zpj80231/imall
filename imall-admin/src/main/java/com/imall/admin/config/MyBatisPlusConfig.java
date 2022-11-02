package com.imall.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhangpengjun
 * @date 2022/7/18
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.imall.mbg.mapper", "com.imall.admin.service.dao"})
public class MyBatisPlusConfig {

}
