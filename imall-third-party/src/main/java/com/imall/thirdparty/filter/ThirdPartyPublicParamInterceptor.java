package com.imall.thirdparty.filter;

import com.imall.thirdparty.common.ThirdPartyPublicParamPlugin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ThirdPartyPublicParamInterceptor
 *
 * @author zhangpengjun
 * @date 2022/7/12
 */
@Component
@Slf4j
public class ThirdPartyPublicParamInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 查询corp_secret
        ThirdPartyPublicParamPlugin.setCorpSecret("123");
        // 支持不同客户不同交易请求包装
        // TokenSignPlugin.setIsPlaintextRequest(ThirdPartyConstant.plaintextRequest);
        return true;
    }

}

