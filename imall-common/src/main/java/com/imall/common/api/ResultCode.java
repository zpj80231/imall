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
    VALIDATE_FAILED(404, "参数校验失败"),
    FORBIDDEN(403, "没有相关权限");

    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
