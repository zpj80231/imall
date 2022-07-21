package com.imall.thirdparty.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhangpengjun
 * @date 2022/7/18
 */
@ConfigurationProperties(prefix = "third-party")
@Component
public class ThirdPartyConstant {

    /**
     * 是否校验token
     */
    public static Boolean checkToken = true;
    /**
     * 是否校验时间戳
     */
    public static Boolean checkTimestamp = true;
    /**
     * 校验时间戳时差范围，单位秒，默认5分钟
     */
    public static Integer timestampLimit = 300;
    /**
     * 是否校验签名
     */
    public static Boolean checkSign = true;
    /**
     * 是否明文请求
     */
    public static Boolean plaintextRequest = false;

    public void setCheckToken(Boolean checkToken) {
        ThirdPartyConstant.checkToken = checkToken;
    }

    public void setCheckTimestamp(Boolean checkTimestamp) {
        ThirdPartyConstant.checkTimestamp = checkTimestamp;
    }

    public void setTimestampLimit(Integer timestampLimit) {
        ThirdPartyConstant.timestampLimit = timestampLimit;
    }

    public void setCheckSign(Boolean checkSign) {
        ThirdPartyConstant.checkSign = checkSign;
    }

    public void setPlaintextRequest(Boolean plaintextRequest) {
        ThirdPartyConstant.plaintextRequest = plaintextRequest;
    }
}
