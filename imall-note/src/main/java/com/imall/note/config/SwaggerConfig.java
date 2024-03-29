package com.imall.note.config;

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
 * @author zhangpengjun
 * @version 1.0
 * @className SwaggerConfig
 * @description 采用 knife4j 美化 swagger
 * https://knife4j.xiaominfo.com/
 * @date 2020/9/29
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.imall.note.modules.ums"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("imall")
                .description("imall API 文档")
                // swagger地址，/swagger-ui.html
                //.termsOfServiceUrl("http://localhost:8080/swagger-ui.html")
                // 美化后地址，doc.html
                .termsOfServiceUrl("http://localhost:8080/doc.html")
                // .contact("zpj80231@163.com")
                .version("1.0")
                .build();
    }
}
