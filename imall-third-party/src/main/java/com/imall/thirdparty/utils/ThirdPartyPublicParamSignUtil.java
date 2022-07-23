package com.imall.thirdparty.utils;

import com.alibaba.fastjson.JSON;
import com.imall.thirdparty.common.CommonRequest;
import com.imall.thirdparty.common.CommonResult;
import com.imall.thirdparty.common.ThirdPartyPublicParamPlugin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * SignUtils
 *
 * @author zhangpengjun
 * @date 2022/7/12
 */
@Slf4j
public class ThirdPartyPublicParamSignUtil {

    /**
     * 生成请求签名
     *
     * @param requestParam
     * @return
     */
    public static synchronized String createRequestSign(CommonRequest requestParam) {
        Long ts = Long.valueOf(requestParam.getTs());
        String token = requestParam.getToken();
        String data = JSON.toJSONString(requestParam.getData());
        String corp_secret = ThirdPartyPublicParamPlugin.getCorpSecret();
        String originalSign = "ts=" + ts + "&token=" + token + "&data=" + data + "&corp_secret=" + corp_secret;
        String sign = DigestUtils.md5DigestAsHex(originalSign.getBytes(StandardCharsets.UTF_8));
        if (log.isDebugEnabled()) {
            log.debug("createRequestSign originalSign: {}", originalSign);
            log.debug("createRequestSign sign: {}", sign);
        }
        return sign;
    }

    /**
     * 校验请求签名
     *
     * @param requestParam
     * @return
     */
    public static synchronized boolean verifyRequestSign(CommonRequest requestParam, String requestParamSign) {
        Long ts = Long.valueOf(requestParam.getTs());
        String token = requestParam.getToken();
        String data = JSON.toJSONString(requestParam.getData());
        String corp_secret = ThirdPartyPublicParamPlugin.getCorpSecret();
        String originalSign = "ts=" + ts + "&token=" + token + "&data=" + data + "&corp_secret=" + corp_secret;
        String sign = DigestUtils.md5DigestAsHex(originalSign.getBytes(StandardCharsets.UTF_8));
        if (log.isDebugEnabled()) {
            log.debug("verifyRequestSign corp_secret: {}", corp_secret);
            log.debug("verifyRequestSign originalSign: {}", originalSign);
            log.debug("verifyRequestSign sign: {}", sign);
            log.debug("verifyRequestSign requestParamSign: {}", requestParamSign);
        }
        return requestParamSign.equals(sign);
    }

    /**
     * 生成响应签名
     *
     * @param responseParam
     * @return
     */
    public static synchronized String createResponsetSign(CommonResult responseParam) {
        Integer code = responseParam.getCode();
        String message = responseParam.getMessage();
        Long ts = responseParam.getTs();
        String data = JSON.toJSONString(responseParam.getData());
        String corp_secret = ThirdPartyPublicParamPlugin.getCorpSecret();
        String originalSign = "code=" + code + "&message=" + message + "&ts=" + ts + "&data=" + data + "&corp_secret=" + corp_secret;
        String sign = DigestUtils.md5DigestAsHex(originalSign.getBytes(StandardCharsets.UTF_8));
        if (log.isDebugEnabled()) {
            log.debug("createResponsetSign corp_secret: {}", corp_secret);
            log.debug("createResponsetSign originalSign: {}", originalSign);
            log.debug("createResponsetSign sign: {}", sign);
        }
        return sign;
    }

    /**
     * 加密数据
     *
     * @param data
     * @return
     */
    public static synchronized String encodeData(Object data) {
        String dataEncode = Base64.getEncoder().encodeToString(JSON.toJSONString(data).getBytes(StandardCharsets.UTF_8));
        return dataEncode;
    }

    /**
     * 解密数据
     *
     * @param data
     * @return
     */
    public static String decodeData(String data) {
        byte[] decode = Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8));
        return new String(decode);
    }
}
