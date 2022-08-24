package com.imall.common.api;

/**
 * @author zhangpengjun
 * @version 1.0
 * @EnumName ResultCode
 * @description 常用状态码枚举类
 * @date 2020/9/24
 */
public enum ResultCode implements IResultCode {

    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    VALIDATE_FAILED(404, "参数校验失败"),
    REQUEST_TYPE_MISMATCH(418, "请求类型不匹配"),
    REQUEST_PARAMETER_EXCEPTION(419, "请求参数异常"),
    INTERNAL_ERROR(9999, "系统繁忙，请稍后重试"),
    ;

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
