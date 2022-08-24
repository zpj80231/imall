package com.imall.admin.config;

import com.imall.common.config.BaseSwaggerConfig;
import com.imall.common.domain.SwaggerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 配置
 *
 * @author zhangpengjun
 * @date 2022/8/24
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger", name = "show", havingValue = "true")
public class Swagger2Config extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.imall.admin.controller")
                .title("imall 后台管理系统")
                .description("imall 后台管理相关接口文档")
                .contactName("zpj")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }

}
