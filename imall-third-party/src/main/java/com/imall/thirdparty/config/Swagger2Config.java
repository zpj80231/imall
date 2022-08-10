package com.imall.thirdparty.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * @author zhangpengjun
 * @date 2022/7/20
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger", name = "show", havingValue = "true")
public class Swagger2Config implements WebMvcConfigurer {

    @Bean
    public Docket jsonPathDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("third-party")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.imall.thirdparty.modules"))
                .paths(PathSelectors.ant("/third-party/json/**"))
                .paths(uri -> {
                    return !Arrays.asList("/third-party/json/sign").stream().anyMatch(p -> uri.startsWith(p));
                })
                .build();
        return docket;
    }

    @Bean
    public Docket signPathDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("third-party test")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.imall.thirdparty.modules"))
                .paths(PathSelectors.ant("/third-party/json/sign/**"))
                .build()
                .pathMapping("/");
        return docket;
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("IMall Third Party 接口文档")
                .description("IMall 三方接口文档")
                .contact(new Contact("IMall", "", null))
                .version("1.0")
                .build();
    }

}
