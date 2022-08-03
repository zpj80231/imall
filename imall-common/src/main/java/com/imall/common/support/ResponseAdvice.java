package com.imall.common.support;

import com.imall.common.api.CommonResult;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Nonnull;

/**
 * 自定义统一响应体
 *
 * @author yangfuya
 * @date 2022/6/28
 */
@RestControllerAdvice(basePackages = "com.imall")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@Nonnull MethodParameter methodParameter,
        @Nonnull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
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
