package com.imall.thirdparty.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONValidator;
import com.imall.thirdparty.annotations.ThirdPartyPublicParam;
import com.imall.thirdparty.common.CommonRequest;
import com.imall.thirdparty.common.ThirdPartyPublicParamPlugin;
import com.imall.thirdparty.exception.ApiCode;
import com.imall.thirdparty.exception.ApiException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

/**
 * 自定义请求，解密验签重新定义请求
 *
 * @author zhangpengjun
 * @date 2022/7/12
 */
@Slf4j
@RestControllerAdvice
public class ThirdPartyRequestAdvice extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        ThirdPartyPublicParam methodAnnotation = AnnotationUtils.findAnnotation(methodParameter.getMethod(), ThirdPartyPublicParam.class);
        ThirdPartyPublicParam classAnnotation = AnnotationUtils.findAnnotation(methodParameter.getDeclaringClass(), ThirdPartyPublicParam.class);
        return methodAnnotation != null || classAnnotation != null;
    }

    @SneakyThrows
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter,
                                           Type targetType, Class<? extends HttpMessageConverter<?>> selectedConverterType) throws IOException {
        // 原请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("original request url：{}", request.getRequestURL().toString());
        log.info("original request url parameter：{}", request.getQueryString());
        log.info("original request header：{}", inputMessage.getHeaders());
        String body = new BufferedReader(new InputStreamReader(inputMessage.getBody())).lines().collect(Collectors.joining());
        body = body.replaceAll("    ", "");
        log.info("original request body：{}", body);
        if (!JSONValidator.from(body).validate()) {
            throw new ApiException(ApiCode.INVALID_JSON);
        }
        CommonRequest commonRequest = JSON.parseObject(body, CommonRequest.class);
        // 校验请求对象
        CommonRequest validatedCommonRequest = ThirdPartyPublicParamPlugin.validateCommonRequestParam(commonRequest);
        ThirdPartyPublicParamPlugin.validateToken(methodParameter, commonRequest);
        // 入参为CommonRequestParam<T>对象，则重新组装为解密验签后的CommonRequestParam<T>
        if (ClassUtils.isAssignable(CommonRequest.class, methodParameter.getParameterType())) {
            return new HttpInputMessageWrapper(inputMessage.getHeaders(), new ByteArrayInputStream(JSON.toJSONString(validatedCommonRequest).getBytes()));
        }
        // 入参为 T 对象，则去除公共层结构
        return new HttpInputMessageWrapper(inputMessage.getHeaders(), new ByteArrayInputStream(JSON.toJSONString(validatedCommonRequest.getData()).getBytes()));
    }
}
