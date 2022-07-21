package com.imall.thirdparty.support;

import com.alibaba.fastjson.JSON;
import com.imall.thirdparty.annotations.DkyThirdPartyParam;
import com.imall.thirdparty.common.CommonResult;
import com.imall.thirdparty.exception.ApiCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
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
 * @author zhangpengjun
 * @date 2022/7/12
 */
@Slf4j
@RestControllerAdvice
public class ThirdPartyResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@Nonnull MethodParameter methodParameter,
                            @Nonnull Class<? extends HttpMessageConverter<?>> aClass) {
        DkyThirdPartyParam methodAnnotation = AnnotationUtils.findAnnotation(methodParameter.getMethod(), DkyThirdPartyParam.class);
        DkyThirdPartyParam classAnnotation = AnnotationUtils.findAnnotation(methodParameter.getDeclaringClass(), DkyThirdPartyParam.class);
        return methodAnnotation != null || classAnnotation != null;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, @Nonnull MethodParameter methodParameter, @Nonnull MediaType mediaType,
                                  @Nonnull Class<? extends HttpMessageConverter<?>> aClass, @Nonnull ServerHttpRequest serverHttpRequest,
                                  @Nonnull ServerHttpResponse serverHttpResponse) {
        CommonResult commonResult = CommonResult.success(ApiCode.SUCCESS, body);
        if (body == null) {
            commonResult = CommonResult.success(ApiCode.INTERNAL_ERROR, body);
        } else if (body instanceof CommonResult) {
            commonResult = (CommonResult) body;
        }
        log.info("original response body：{}", JSON.toJSONString(commonResult));
        return commonResult;
    }

}
