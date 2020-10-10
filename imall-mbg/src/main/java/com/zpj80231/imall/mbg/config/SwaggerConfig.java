package com.zpj80231.imall.mbg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName SwaggerConfig
 * @Description 采用 knife4j 美化 swagger
 *              https://knife4j.xiaominfo.com/
 * @Version 1.0
 * @Date 2020/9/29 22:59
 * @Created by zpj80231
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zpj80231.imall.mbg.modules.ums"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("imall")
                .description("imall API 文档")
                .termsOfServiceUrl("http://localhost:8999/")
                .contact("zpj80231@163.com")
                .version("1.0")
                .build();
    }
}
