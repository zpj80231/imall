package com.imall.common.api;

/**
 * @author zhangpengjun
 * @version 1.0
 * @InterfaceName IErrorCode
 * @description 规范 Web 中的状态码
 * @date 2020/9/24
 */
public interface IResultCode {
    /**
     * 状态码
     */
    long getCode();

    /**
     * 提示信息
     */
    String getMessage();

}
