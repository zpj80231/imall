package com.zpj80231.imall.common.api;

/**
 * @InterfaceName IErrorCode
 * @Description 规范 Web 中的状态码
 * @Version 1.0
 * @Date 2020/9/24 15:15
 * @Created by zpj80231
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
