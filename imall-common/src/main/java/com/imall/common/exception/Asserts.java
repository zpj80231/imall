package com.imall.common.exception;

import com.imall.common.api.IResultCode;

/**
 * 断言处理，用于抛出各种自定义API异常
 * @author zhangpengjun
 */
public class Asserts {

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IResultCode errorResult) {
        throw new ApiException(errorResult);
    }
}
