package com.imall.thirdparty.exception;

import lombok.Getter;

/**
 * 公共请求参数
 *
 * @author zhangpengjun
 * @date 2022/7/13
 */
@Getter
public class ApiException extends Exception {

    private static final long serialVersionUID = -7094081888791936121L;

    private int code;

    private Object data;

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ApiException(ApiCode apiCode) {
        this(apiCode.getCode(), apiCode.getMessage());
    }

    public ApiException(ApiCode apiCode, String message) {
        this(apiCode.getCode(), message);
    }

    public ApiException(ApiCode apiCode, Throwable throwable) {
        this(apiCode.getCode(), apiCode.getMessage(), throwable);
    }

    public ApiException(int code, String msg) {
        this(msg);
        this.code = code;
    }

    public <T> ApiException(int code, String msg, T data) {
        this(msg);
        this.code = code;
        this.data = data;
    }

    public ApiException(int code, String msg, Throwable cause) {
        this(msg, cause);
        this.code = code;
    }

}
