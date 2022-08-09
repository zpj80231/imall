package com.imall.thirdparty.config;

import com.imall.thirdparty.filter.ThirdPartyPublicParamInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfig
 *
 * @author zhangpengjun
 * @date 2022/7/12
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ThirdPartyPublicParamInterceptor thirdPartyPublicParamInterceptor;

    /**
     * 拦截器配置
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器注册对象, 指定要拦截的请求
        registry.addInterceptor(thirdPartyPublicParamInterceptor).addPathPatterns("/json/**");
    }

}
