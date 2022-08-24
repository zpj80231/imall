package com.imall.common.support;

import com.imall.common.api.CommonResult;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Nonnull;

/**
 * 自定义统一响应体
 * basePackages = "com.imall"，排除knife4j的结果返回，否则knife4j页面会报错
 *
 * @author zhangpengjun
 * @date 2022/6/28
 */
@RestControllerAdvice(basePackages = "com.imall")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@Nonnull MethodParameter methodParameter,
        @Nonnull Class<? extends HttpMessageConverter<?>> aClass) {
        // 不包装 String 类型
        return AbstractJackson2HttpMessageConverter.class.isAssignableFrom(aClass);
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, @Nonnull MethodParameter methodParameter, @Nonnull MediaType mediaType,
        @Nonnull Class<? extends HttpMessageConverter<?>> aClass, @Nonnull ServerHttpRequest serverHttpRequest,
        @Nonnull ServerHttpResponse serverHttpResponse) {
        if (body instanceof CommonResult) {
            return body;
        } else if (body == null) {
            return CommonResult.success(null);
        } else {
            return CommonResult.success(body);
        }
    }

}
