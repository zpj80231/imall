package com.imall.thirdparty.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.imall.thirdparty.common.CommonResult;
import com.imall.thirdparty.common.ThirdPartyPublicParamPlugin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ClassUtils;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 公共异常返回参数处理
 *
 * @author zhangpengjun
 * @date 2022/7/13
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class ThirdPartyGlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler({Throwable.class, Exception.class, RuntimeException.class})
    public ResponseEntity handleThrowableException(Throwable e, HttpServletRequest request) {
        // StrUtil.split(this.getHttpRequestInfo(request), StrUtil.CRLF).forEach(log::error);
        return this.getResponseEntity(request, e, ApiCode.INTERNAL_ERROR);
    }

    /**
     * HttpMessageNotReadableException 业务异常
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity handleBusinessException(HttpMessageNotReadableException e, HttpServletRequest request) {
        return this.getResponseEntity(request, e, ApiCode.FAIL.getCode(), "请求不能为空", null);
    }

    /**
     * ApiException 业务异常
     */
    @ExceptionHandler({ApiException.class})
    public ResponseEntity handleBusinessException(ApiException e, HttpServletRequest request) {
        return this.getResponseEntity(request, e, e.getCode(), e.getMessage(), e.getData());
    }

    /**
     * 请求类型不匹配
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        return this.getResponseEntity(request, e);
    }

    /**
     * 参数异常
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity handlerValidationException(ConstraintViolationException e,
                                                     HttpServletRequest request) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder sb = new StringBuilder();
        constraintViolations.forEach(constraintViolation -> sb.append(constraintViolation.getPropertyPath()).append(" ")
                .append(constraintViolation.getMessage()).append(" "));
        log.warn("请求参数异常 {}", sb);
        return this.getResponseEntity(request, e, ApiCode.REQUEST_PARAMETER_EXCEPTION);
    }

    /**
     * 参数异常
     */
    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity handlerIllegalStateException(IllegalStateException e, HttpServletRequest request) {
        log.warn("请求参数异常 {} ", e.getLocalizedMessage());
        return this.getResponseEntity(request, e, ApiCode.REQUEST_PARAMETER_EXCEPTION);
    }

    /**
     * 参数类型不匹配
     */

    @ExceptionHandler({TypeMismatchException.class})
    public ResponseEntity requestTypeMismatch(TypeMismatchException e, HttpServletRequest request) {
        log.warn("参数类型不匹配 参数:{} 类型应该为{}", e.getPropertyName(), e.getRequiredType());
        return this.getResponseEntity(request, e, ApiCode.REQUEST_PARAMETER_EXCEPTION);
    }

    /**
     * 参数异常
     */
    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity handlerBindException(BindException e, HttpServletRequest request) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        bindingResult.getAllErrors().stream().filter(allError -> allError instanceof FieldError)
                .map(allError -> (FieldError) allError).forEach(fieldError -> {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            sb.append(String.format("%s: %s, message:%s ", field, fieldError.getRejectedValue(), message));
        });
        log.warn("请求参数异常 {}", sb);
        return this.getResponseEntity(request, e, ApiCode.REQUEST_PARAMETER_EXCEPTION.getCode(), sb.toString(), null);
    }

    protected ResponseEntity getResponseEntity(HttpServletRequest request, Throwable e) {
        return this.getResponseEntity(request, e, ApiCode.REQUEST_TYPE_MISMATCH);
    }

    protected ResponseEntity getResponseEntity(HttpServletRequest request, Throwable e, ApiCode apiCode) {
        return this.getResponseEntity(request, e, apiCode.getCode(), apiCode.getMessage(), null);
    }

    /**
     * 构造通用返回信息
     */
    protected <T> ResponseEntity getResponseEntity(HttpServletRequest request, Throwable e, int code, String msg, T data) {
        if (oneOfByInstanceOf(e.getClass(), ApiException.class, BindException.class)) {
            String packageStackTrace = getPackageStackTrace(e, "com.imall");
            log.error("业务异常：{}", packageStackTrace);
        } else {
            log.error("系统异常：{}{}", e.getMessage(), e);
        }
        CommonResult<T> commonResult = CommonResult.fail(code, msg, data);
        ThirdPartyPublicParamPlugin.removeAll();
        return new ResponseEntity<>(commonResult, HttpStatus.OK);
    }

    /**
     * 所属类是否为指定类型之一
     */
    private static boolean oneOfByInstanceOf(Class<?> cls, Class<?>... objs) {
        for (Class<?> obj : objs) {
            if (ClassUtils.isAssignable(obj, cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 简化日志，构造指定包下的日志
     */
    private static String getPackageStackTrace(Throwable e, String packagePrefix) {
        StringBuilder sb = new StringBuilder("\n").append(e).append("\n\tcause: ").append(e.getCause());
        for (StackTraceElement traceElement : e.getStackTrace()) {
            if (traceElement.getClassName().startsWith(packagePrefix)) {
                sb.append("\n\tat ").append(traceElement);
            }
        }
        return sb.toString();
    }

    protected String getHttpRequestInfo(HttpServletRequest request) {

        StringBuilder sb = StrUtil.builder();

        // 请求URL
        String url = request.getRequestURL().toString();

        sb.append("Request URL: ").append(url).append(StrUtil.CRLF);

        // 请求方式
        String method = request.getMethod();
        sb.append("Request Method: ").append(method).append(StrUtil.CRLF);

        // header请求参数
        Map<String, List<String>> headerMaps = this.getHeaderParameter(request);
        sb.append("Request Headers: ").append(StrUtil.CRLF);
        for (Map.Entry<String, List<String>> entry : headerMaps.entrySet()) {
            sb.append("    ").append(entry.getKey()).append(": ").append(CollUtil.join(entry.getValue(), ","))
                .append(StrUtil.CRLF);
        }

        // 请求参数
        Map<String, List<String>> parameterMaps = this.getParameter(request);
        sb.append("Request Parameter: ").append(StrUtil.CRLF);
        for (Map.Entry<String, List<String>> entry : parameterMaps.entrySet()) {
            sb.append("    ").append(entry.getKey()).append(": ").append(CollUtil.join(entry.getValue(), ","))
                .append(StrUtil.CRLF);
        }

        try (ServletInputStream inputStream = request.getInputStream()) {
            // 如果使用了@RequestBody，那么这里的流其实已经关闭了，在读取就会报错了！！！！
            if (!inputStream.isFinished()) {
                sb.append("Request Body: ").append(StrUtil.CRLF);
                sb.append("    ").append(StreamUtils.copyToString(inputStream, Charsets.UTF_8)).append(StrUtil.CRLF);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return sb.toString();
    }

    /**
     * 获取请求参数
     */
    private Map<String, List<String>> getParameter(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, List<String>> parameterMaps = new HashMap<>(16);
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] parameterValues = request.getParameterValues(paramName);
            if (parameterValues != null && parameterValues.length > 0) {
                parameterMaps.put(paramName, Arrays.asList(parameterValues));
            }
        }
        return parameterMaps;
    }

    /**
     * 获取Header
     */
    private Map<String, List<String>> getHeaderParameter(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, List<String>> parameterMaps = new HashMap<>(16);
        while (headerNames.hasMoreElements()) {
            String paramName = headerNames.nextElement();
            List<String> paramValue = Lists.newArrayList();
            Enumeration<String> headers = request.getHeaders(paramName);
            while (headers.hasMoreElements()) {
                paramValue.add(headers.nextElement());
            }
            parameterMaps.put(paramName, paramValue);
        }
        return parameterMaps;
    }

}
