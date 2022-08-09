package com.imall.thirdparty.filter;

import com.imall.thirdparty.common.ThirdPartyPublicParamPlugin;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

