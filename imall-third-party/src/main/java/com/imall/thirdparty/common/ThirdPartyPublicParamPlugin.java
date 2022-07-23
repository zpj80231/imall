package com.imall.thirdparty.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONValidator;
import com.imall.thirdparty.annotations.ThirdPartyPublicParam;
import com.imall.thirdparty.constants.RedisConstant;
import com.imall.thirdparty.constants.ThirdPartyConstant;
import com.imall.thirdparty.constants.TokenTypeEnum;
import com.imall.thirdparty.exception.ApiCode;
import com.imall.thirdparty.exception.ApiException;
import com.imall.thirdparty.utils.RedisUtil;
import com.imall.thirdparty.utils.ThirdPartyPublicParamSignUtil;
import com.imall.thirdparty.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 公共参数插件
 * 支持对token，私钥，时间戳，是否明文请求等参数的设置
 *
 * @author zhangpengjun
 * @date 2022/7/15
 */
@Slf4j
@Component
public class ThirdPartyPublicParamPlugin {

    /**
     * 私钥
     */
    private static ThreadLocal<String> corpSecret = new ThreadLocal<>();
    /**
     * 是否校验token
     */
    private static ThreadLocal<Boolean> isCheckToken = new ThreadLocal<>();
    /**
     * 是否校验时间戳
     */
    private static ThreadLocal<Boolean> isCheckTimestamp = new ThreadLocal<>();
    /**
     * 校验时间戳时差范围，单位秒，默认5分钟
     */
    private static ThreadLocal<Integer> timestampLimit = new ThreadLocal<>();
    /**
     * 是否校验签名
     */
    private static ThreadLocal<Boolean> isCheckSign = new ThreadLocal<>();
    /**
     * 是否明文请求
     */
    private static ThreadLocal<Boolean> isPlaintextRequest = new ThreadLocal<>();


    public static void setCorpSecret(String corp_secret_value) {
        corpSecret.set(corp_secret_value);
    }

    public static String getCorpSecret() {
        return corpSecret.get();
    }

    public static void removeCorpSecret() {
        corpSecret.remove();
    }

    public static void setIsPlaintextRequest(Boolean plaintextRequest) {
        isPlaintextRequest.set(plaintextRequest);
    }

    public static Boolean getIsPlaintextRequest() {
        return isPlaintextRequest.get() == null ? ThirdPartyConstant.plaintextRequest : isPlaintextRequest.get();
    }

    public static void removeIsPlaintextRequest() {
        isPlaintextRequest.remove();
    }

    public static void setIsCheckSign(Boolean checkSign) {
        isCheckSign.set(checkSign);
    }

    public static Boolean getIsCheckSign() {
        return isCheckSign.get() == null ? ThirdPartyConstant.checkSign : isCheckSign.get();
    }

    public static void removeIsCheckSign() {
        isCheckSign.remove();
    }

    public static void setTimestampLimit(Integer limit) {
        timestampLimit.set(limit);
    }

    public static Integer getTimestampLimit() {
        return timestampLimit.get() == null ? ThirdPartyConstant.timestampLimit : timestampLimit.get();
    }

    public static void removeTimestampLimit() {
        timestampLimit.remove();
    }

    public static void setIsCheckTimestamp(Boolean checkTimestamp) {
        isCheckTimestamp.set(checkTimestamp);
    }

    public static Boolean getIsCheckTimestamp() {
        return isCheckTimestamp.get() == null ? ThirdPartyConstant.checkTimestamp : isCheckTimestamp.get();
    }

    public static void removeIsCheckTimestamp() {
        isCheckTimestamp.remove();
    }

    public static void setIsCheckToken(Boolean flag) {
        isCheckToken.set(flag);
    }

    public static Boolean getIsCheckToken() {
        return isCheckToken.get() == null ? ThirdPartyConstant.checkToken : isCheckToken.get();
    }

    public static void removeIsCheckToken() {
        isCheckToken.remove();
    }

    // @PostConstruct
    // private static void init() {
    //     isCheckToken.set(ThirdPartyConstant.checkSign);
    //     isCheckTimestamp.set(ThirdPartyConstant.checkTimestamp);
    //     timestampLimit.set(ThirdPartyConstant.timestampLimit);
    //     isCheckSign.set(ThirdPartyConstant.checkSign);
    //     isPlaintextRequest.set(ThirdPartyConstant.plaintextRequest);
    // }

    /**
     * 自动校验
     *
     * @param methodParameter
     * @param commonRequest
     * @throws ApiException
     */
    public static void validateToken(MethodParameter methodParameter, CommonRequest commonRequest) throws ApiException {
        ThirdPartyPublicParam defaultAnnotation = AnnotationUtils.findAnnotation(methodParameter.getDeclaringClass(), ThirdPartyPublicParam.class);
        ThirdPartyPublicParam methodAnnotation = AnnotationUtils.findAnnotation(methodParameter.getMethod(), ThirdPartyPublicParam.class);
        if (methodAnnotation != null) {
            defaultAnnotation = methodAnnotation;
        }
        if (TokenTypeEnum.Empty == defaultAnnotation.tokenType()) {
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        String[] split = uri.split("/");
        int uriJsonIndex = 0;
        for (int i = 0; i < split.length; i++) {
            if ("json".equals(split[i])) {
                uriJsonIndex = i;
            }
        }
        String companyid = split[uriJsonIndex + 2];
        checkToken(defaultAnnotation.tokenType(), companyid, commonRequest.getToken());
    }

    /**
     * 支持手动校验token
     *
     * @param tokenTypeEnum
     * @param companyId
     * @param token
     * @throws ApiException
     */
    public static synchronized void checkToken(TokenTypeEnum tokenTypeEnum, String companyId, String token) throws ApiException {
        if (!getIsCheckToken()) {
            return;
        }
        if (!StringUtils.hasText(token) || token.length() != 32) {
            throw new ApiException(ApiCode.TOKEN_VERIFICATION_FAILED);
        }
        String key = RedisConstant.TOKEN_PREFIX + companyId + ":" + tokenTypeEnum.getType();
        String value = (String) RedisUtil.get(key);
        if (value == null) {
            throw new ApiException(ApiCode.TOKEN_EXPIRED);
        }
        if (!token.equals(value)) {
            throw new ApiException(ApiCode.TOKEN_VERIFICATION_FAILED);
        }
    }

    /**
     * 生成token
     *
     * @param tokenType
     * @param companyId
     * @return
     * @throws ApiException
     */
    public static synchronized String createToken(String tokenType, String companyId) throws ApiException {
        TokenTypeEnum tokenTypeEnum;
        try {
            tokenTypeEnum = TokenTypeEnum.valueOf(tokenType);
        } catch (IllegalArgumentException e) {
            throw new ApiException(ApiCode.REQUEST_PARAMETER_EXCEPTION.getCode(), ApiCode.REQUEST_PARAMETER_EXCEPTION.getMessage() + "tokentype未匹配");
        }
        String token = UUID.randomUUID().toString(true);
        String key = RedisConstant.TOKEN_PREFIX + companyId + ":" + tokenTypeEnum.getType();
        long endTime = DateUtil.endOfDay(new Date()).getTime();
        long startTime = DateUtil.beginOfDay(new Date()).getTime();
        long expireTime = (endTime - startTime) / 1000 + new Random().nextInt(300);
        try {
            RedisUtil.set(key, token, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new ApiException(ApiCode.TOKEN_FAILED_TO_GET, e);
        }
        log.info("createToken - key: {}, value: {}, expireTime: {}, timeUnit: {}", key, token, expireTime, TimeUnit.SECONDS);
        return token;
    }

    /**
     * 构造返回对象
     *
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static synchronized <T> CommonResult<T> buildCommonResult(Integer code, String message, T data) {
        if (data != null) {
            Boolean plaintext_request = getIsPlaintextRequest();
            CommonResult<T> commonResult;
            if (plaintext_request) {
                commonResult = new CommonResult(code, message, data);
            } else {
                String dataEncode = ThirdPartyPublicParamSignUtil.encodeData(data);
                commonResult = new CommonResult(code, message, dataEncode);
            }
            String sign = ThirdPartyPublicParamSignUtil.createResponsetSign(commonResult);
            commonResult.setSign(sign);
            return commonResult;
        }
        return new CommonResult<>(code, message, null, System.currentTimeMillis() / 1000, null);
    }

    /**
     * 校验并重构入参对象
     *
     * @param commonRequest
     * @return
     * @throws ApiException
     */
    public static synchronized CommonRequest validateCommonRequestParam(CommonRequest commonRequest) throws ApiException {
        // 校验参数
        ValidatorUtil.validateFast(commonRequest);
        // 校验时间戳，前后五分钟有效
        if (getIsCheckTimestamp()) {
            Long ts = Long.valueOf(commonRequest.getTs().toString());
            Integer limit = getTimestampLimit();
            if ((System.currentTimeMillis() / 1000 - ts) > limit || (ts - System.currentTimeMillis() / 1000) > limit) {
                log.error("ts date: {}", DateUtil.formatDateTime(new Date(ts * 1000)));
                throw new ApiException(ApiCode.TS_EXPIRED);
            }
        }

        // 解密验签
        // 验签
        if (getIsCheckSign()) {
            boolean verifyRequestSign = ThirdPartyPublicParamSignUtil.verifyRequestSign(commonRequest, commonRequest.getSign());
            if (!verifyRequestSign) {
                throw new ApiException(ApiCode.INVALID_SIGN);
            }
        }
        // 非明文请求
        if (!getIsPlaintextRequest()) {
            String decodeDataToString = null;
            try {
                String data = String.valueOf(commonRequest.getData());
                decodeDataToString = ThirdPartyPublicParamSignUtil.decodeData(data);
            } catch (Exception e) {
                throw new ApiException(ApiCode.REQUEST_PARAMETER_EXCEPTION, "data不是Base64编码字符串");
            }
            if (!JSONValidator.from(decodeDataToString).validate()) {
                log.info("decrypted data：{}", decodeDataToString);
                throw new ApiException(ApiCode.INVALID_JSON, "data无效JSON");
            }
            // 重构
            commonRequest.setData(JSON.parseObject(decodeDataToString));
            log.info("refactored request object：{}", commonRequest);
        } else { // 明文请求
            if (!JSONValidator.from(String.valueOf(commonRequest.getData())).validate()) {
                throw new ApiException(ApiCode.INVALID_JSON, "data无效JSON");
            }
        }
        return commonRequest;
    }
}
