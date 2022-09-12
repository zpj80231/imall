package com.imall.admin.component;

import cn.hutool.core.util.URLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 获取当前请求的所需权限
 *
 * @author zhangpengjun
 * @date 2022/9/5
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private DynamicSecurityService dynamicSecurityService;

    /**
     * 访问适用于给定安全对象的ConfigAttribute
     *
     * @param object 被保护的对象
     * @return 传入的安全对象的属性。如果没有适用的属性，则应返回一个空集合。
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 请求需要返回的匹配权限
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        // 请求url
        String url = ((FilterInvocation) object).getRequestUrl();
        String path = URLUtil.getPath(url);
        Map<String, ConfigAttribute> dataSource = dynamicSecurityService.loadDataSource();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        // 获取请求所需的全部权限
        dataSource.forEach((k, v) -> {
            if (pathMatcher.match(k, path)) {
                configAttributes.add(v);
            }
        });
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
