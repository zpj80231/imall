package com.imall.common.exception;

import com.imall.common.api.IResultCode;

/**
 * 自定义异常类
 *
 * @author zhangpengjun
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -418250791972825895L;
    private IResultCode errorResult;

    public IResultCode getErrorResult() {
        return this.errorResult;
    }

    /**
     * 自定义异常说明
     */
    public ApiException(String message) {
        super(message);
    }

    /**
     * 自定义异常状态码和异常信息
     */
    public ApiException(IResultCode errorResult) {
        super(errorResult.getMessage());
        this.errorResult = errorResult;
    }

    /**
     * 捕获Java的异常
     */
    public ApiException (Throwable cause) {
        super(cause);
    }

    /**
     * 捕获Java的异常，同时自定义此次异常说明
     */
    public ApiException (String message, Throwable cause) {
        super(message, cause);
    }

}
