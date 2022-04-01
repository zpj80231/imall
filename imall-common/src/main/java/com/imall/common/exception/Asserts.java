package com.imall.common.exception;

import com.imall.common.api.IResultCode;

/**
 * 断言处理，用于抛出各种自定义API异常
 * Created by zpj80231 on 2020/9/30.
 */
public class Asserts {

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IResultCode errorResult) {
        throw new ApiException(errorResult);
    }
}
