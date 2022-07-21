package com.imall.thirdparty.filter;

import com.imall.thirdparty.common.TokenSignPlugin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DkyThirdPartyParamInterceptor
 *
 * @author zhangpengjun
 * @date 2022/7/12
 */
@Component
@Slf4j
public class DkyThirdPartyParamInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 查询corp_secret
        TokenSignPlugin.setCorpSecret("123");
        // 支持不同客户不同交易请求包装
        // TokenSignPlugin.setIsPlaintextRequest(ThirdPartyConstant.plaintextRequest);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TokenSignPlugin.removeCorpSecret();
        TokenSignPlugin.removeIsPlaintextRequest();
    }
}

