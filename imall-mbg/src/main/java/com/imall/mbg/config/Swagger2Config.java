package com.imall.mbg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zhangpengjun
 * @version 1.0
 * @className Swagger2Config
 * @description 采用 knife4j 美化swagger
 * @date 2020/9/29
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("IMall Common")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.imall.mbg.modules"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("IMall")
                .description("IMall API 文档")
                .termsOfServiceUrl("http://localhost:8000/doc.html")
                .contact(new Contact("IMall", "https://imall.com", "zpj80231@163.com"))
                .version("1.0")
                .build();
    }
}
