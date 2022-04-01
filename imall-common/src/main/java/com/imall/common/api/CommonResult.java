package com.imall.common.api;

import lombok.Data;

/**
 * @ClassName CommonResult
 * @Description 通用返回结果
 * @Version 1.0
 * @Date 2020/9/24 15:39
 * @Created by zpj80231
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
     * @return com.zpj80231.imall.common.api.CommonResult<T>
     */
    public static <T> CommonResult<T> success(T data) {
        return result(ResultCode.SUCCESS, data);
    }

    /**
     * 返回成功，自定义结果提示信息
     *
     * @param data
     * @param message
     * @return com.zpj80231.imall.common.api.CommonResult<T>
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(),message,data);
    }

    /**
     * 返回失败
     *
     * @param
     * @return com.zpj80231.imall.common.api.CommonResult<T>
     */
    public static <T> CommonResult<T> failed() {
        return result(ResultCode.FAILED, (T) null);
    }

    /**
     * 返回失败，自定义失败提示信息
     *
     * @param message
     * @return com.zpj80231.imall.common.api.CommonResult<T>
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(),message,null);
    }

    /**
     * 返回失败，自定义失败提示信息
     *
     * @param resultCode
     * @return com.zpj80231.imall.common.api.CommonResult<T>
     */
    public static <T> CommonResult<T> failed(IResultCode resultCode) {
        return new CommonResult<T>(resultCode.getCode(),resultCode.getMessage(),null);
    }

    /**
     * 返回未指定授权
     *
     * @param
     * @return com.zpj80231.imall.common.api.CommonResult<T>
     */
    public static <T> CommonResult<T> forbidden() {
        return result(ResultCode.FORBIDDEN, (T) null);
    }

    /**
     * 返回未指定授权，自定义提示信息
     *
     * @param message
     * @return com.zpj80231.imall.common.api.CommonResult<T>
     */
    public static <T> CommonResult<T> forbidden(String message) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(),message,null);
    }

    /**
     * 返回参数校验失败
     *
     * @param
     * @return com.zpj80231.imall.common.api.CommonResult<T>
     */
    public static <T> CommonResult<T> validateFailed() {
        return result(ResultCode.VALIDATE_FAILED, (T) null);
    }

    /**
     * 返回参数校验失败，自定义提示信息
     *
     * @param message
     * @return com.zpj80231.imall.common.api.CommonResult<T>
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(),message,null);
    }

    private static <T> CommonResult<T> result(IResultCode resultCode, T data) {
        return new CommonResult<T>(resultCode.getCode(),resultCode.getMessage(),data);
    }

}
