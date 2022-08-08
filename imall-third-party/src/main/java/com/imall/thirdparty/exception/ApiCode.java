package com.imall.thirdparty.exception;

/**
 * 字典码
 *
 * @author zhangpengjun
 * @date 2022/7/13
 */
public enum ApiCode {

    SUCCESS(0, "请求成功"),
    FAIL(1, "请求失败"),
    MISSING_PUBLIC_PARAMETER(2, "缺少公共参数"),
    PUBLIC_PARAMETER_EXCEEDS_LIMIT(3, "公共参数超出限制"),
    MISSING_REQUIRED_PUBLIC_PARAMETER(4, "公共参数缺少必填"),
    TS_EXPIRED(5, "ts过期;前后5分钟有效"),
    INVALID_COMPANY_ID(6, "无效企业ID"),
    TOKEN_VERIFICATION_FAILED(7, "token验证失败"),
    TOKEN_EXPIRED(8, "token过期"),
    INVALID_SIGN(9, "无效sign"),
    DATA_PARAMETER_IS_INCOMPLETE(10, "data参数不完整"),
    DATA_IS_MISSING_REQUIRED_KEY(11, "data缺少必填"),
    INVALID_AGENT(13, "无效坐席"),
    INVALID_JSON(14, "无效json"),
    NO_NUMBERS_AVAILABLE_YET(15, "暂无可用号码"),
    TOKEN_FAILED_TO_GET(16, "token获取失败"),
    INVALID_GATEWAY(17, "无效网关"),
    REQUEST_TYPE_MISMATCH(18, "请求类型不匹配"),
    REQUEST_PARAMETER_EXCEPTION(19, "请求参数异常"),
    INTERNAL_ERROR(9999, "系统繁忙，请稍后重试"),
    ;

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ApiCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
