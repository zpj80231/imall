package com.imall.common.api;

import lombok.Data;

/**
 * @author zhangpengjun
 * @version 1.0
 * @className CommonResult
 * @description 通用返回结果
 * @date 2020/9/24
 */
@Data
public class CommonResult<T> {
    private long code;
    private String message;
    private T data;

    private CommonResult() {

    }

    private CommonResult (long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 返回成功
     *
     * @param data
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> success(T data) {
        return result(ResultCode.SUCCESS, data);
    }

    /**
     * 返回成功，自定义结果提示信息
     *
     * @param data
     * @param message
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(),message,data);
    }

    /**
     * 返回失败
     *
     * @param
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> failed() {
        return result(ResultCode.FAILED, (T) null);
    }

    /**
     * 返回失败，自定义失败提示信息
     *
     * @param message
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(),message,null);
    }

    /**
     * 返回失败，自定义失败提示信息
     *
     * @param resultCode
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> failed(IResultCode resultCode) {
        return new CommonResult<T>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    /**
     * 返回失败，自定义失败提示信息
     *
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> failed(int code, String msg, T data) {
        return new CommonResult<T>(code, msg, data);
    }

    /**
     * 返回未指定授权
     *
     * @param
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> forbidden() {
        return result(ResultCode.FORBIDDEN, (T) null);
    }

    /**
     * 返回未指定授权，自定义提示信息
     *
     * @param message
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> forbidden(String message) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(),message,null);
    }

    /**
     * 返回参数校验失败
     *
     * @param
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> validateFailed() {
        return result(ResultCode.VALIDATE_FAILED, (T) null);
    }

    /**
     * 返回参数校验失败，自定义提示信息
     *
     * @param message
     * @return CommonResult<T>
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    private static <T> CommonResult<T> result(IResultCode resultCode, T data) {
        return new CommonResult<T>(resultCode.getCode(), resultCode.getMessage(), data);
    }

    /**
     * 暂未登录或token已经过期
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }
}
