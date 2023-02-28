package com.imall.thirdparty.filter;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.imall.thirdparty.common.ThirdPartyPublicParamPlugin;
import com.imall.thirdparty.constants.ThirdPartyConstant;
import com.imall.thirdparty.support.BodyReadHttpServletRequestWrapper;
import com.imall.thirdparty.utils.IpUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * ThirdPartyPublicParamInterceptor
 *
 * @author zhangpengjun
 * @date 2022/7/12
 */
@Component
@Slf4j
@Data
@ConfigurationProperties(prefix = "third-party")
public class ThirdPartyPublicParamInterceptor implements HandlerInterceptor {

    private Map<String, String> secrets;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 默认对每次请求都添加校验，防止线程池环境下ThreadLocal的重用
        ThirdPartyPublicParamPlugin.setIsCheckToken(ThirdPartyConstant.checkToken);
        ThirdPartyPublicParamPlugin.setIsCheckSign(ThirdPartyConstant.checkSign);
        ThirdPartyPublicParamPlugin.setIsCheckTimestamp(ThirdPartyConstant.checkTimestamp);
        // 白名单跳过校验
        String ipAddr = IpUtil.getIpAddr(request);
        BodyReadHttpServletRequestWrapper requestWrapper = (BodyReadHttpServletRequestWrapper) request;
        HashMap<String, Object> otherParams = new HashMap<>();
        otherParams.put("timestamp", System.currentTimeMillis() / 1000);
        if (requestWrapper.getMethod().equals(HttpMethod.GET.name()) && ArrayUtil.isEmpty(requestWrapper.getRequestBody())) {
            requestWrapper.setRequestBody(JSON.toJSONBytes(otherParams));
        }
        if (IpUtil.isPermited(ipAddr, ThirdPartyConstant.ipWhilte)) {
            log.info("original request ip: {} is in the whitelist, skip verification.", ipAddr);
            requestWrapper.addAllParameters(otherParams);
            ThirdPartyPublicParamPlugin.setIsCheckSign(false);
            ThirdPartyPublicParamPlugin.setIsCheckTimestamp(false);
        }
        // 查询corp_secret
        String companyid = ThirdPartyPublicParamPlugin.getCompanyidFromUri(request);
        if (secrets.containsKey(companyid)) {
            ThirdPartyPublicParamPlugin.setCorpSecret(secrets.get(companyid));
        }
        // 支持不同客户不同交易请求包装
        // ThirdPartyPublicParamPlugin.setIsPlaintextRequest(ThirdPartyConstant.plaintextRequest);
        return true;
    }

}

