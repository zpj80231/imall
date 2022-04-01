package com.imall.common.api;

/**
 * @EnumName ResultCode
 * @Description 常用状态码枚举类
 * @Version 1.0
 * @Date 2020/9/24 15:25
 * @Created by zpj80231
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
