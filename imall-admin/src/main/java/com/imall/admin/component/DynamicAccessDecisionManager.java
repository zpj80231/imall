package com.imall.admin.component;

import cn.hutool.core.collection.CollUtil;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 判断请求是否具有访问权限
 *
 * @author zhangpengjun
 * @date 2022/9/5
 */
public class DynamicAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 对未被配置资源的接口（后台接口）直接放行
        if (CollUtil.isEmpty(configAttributes)) {
            return;
        }
        // 权限匹配，有权限则请求成功
        Set<ConfigAttribute> attributes = configAttributes.stream()
                .filter(auth -> authentication.getAuthorities().contains(auth.getAttribute().trim()))
                .collect(Collectors.toSet());
        if (CollUtil.isEmpty(attributes)) {
            throw new AccessDeniedException("访问失败，权限异常");
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
