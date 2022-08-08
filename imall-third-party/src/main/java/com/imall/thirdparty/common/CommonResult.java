package com.imall.thirdparty.common;

import com.imall.thirdparty.exception.ApiCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回参数
 *
 * @author zhangpengjun
 * @date 2022/7/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {

    /**
     * 状态码 0：表示正常 其余数值表示出现错误，该值对应ExceptionCode中定义的异常码
     */
    private Integer code;

    /**
     * 信息内容
     */
    private String message;

    /**
     * 数据信息
     */
    private T data;

    /**
     * 时间戳，精度：秒 10位
     */
    private long ts;

    /**
     * 签名
     */
    private String sign;

    public static <T> CommonResult<T> success(ApiCode apiCode, T data) {
        return getResult(apiCode.getCode(), apiCode.getMessage(), data);
    }

    public static <T> CommonResult<T> success(Integer code, String message, T data) {
        return getResult(code, message, data);
    }

    public static <T> CommonResult<T> fail(ApiCode apiCode, String message) {
        return getResult(apiCode.getCode(), message, null);
    }

    public static <T> CommonResult<T> fail(Integer code, String message, T data) {
        return getResult(code, message, data);
    }

    private static <T> CommonResult<T> getResult(Integer code, String message, T data) {
        return ThirdPartyPublicParamPlugin.buildCommonResult(code, message, data);
    }
}
