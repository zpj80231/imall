package com.imall.admin.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.URLUtil;
import com.imall.admin.config.IgnoreUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 动态获取系统权限，匹配当前请求的所需权限
 *
 * @author zhangpengjun
 * @date 2022/9/5
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<String, ConfigAttribute> configAttributeMap = null;
    @Autowired
    private DynamicSecurityService dynamicSecurityService;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @PostConstruct
    public void loadDataSource() {
        configAttributeMap = dynamicSecurityService.loadDataSource();
    }

    /**
     * 后台操作权限资源时，暴露此方法刷新缓存
     */
    public void clearDataSource() {
        configAttributeMap.clear();
        configAttributeMap = null;
    }

    /**
     * 访问适用于给定安全对象的ConfigAttribute
     *
     * @param object 被保护的对象
     * @return 传入的安全对象的属性。如果没有适用的属性，则应返回一个空集合。
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 请求需要返回，系统配置范围内的匹配权限
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        // 请求url
        String url = ((FilterInvocation) object).getRequestUrl();
        String path = URLUtil.getPath(url);
        AntPathMatcher pathMatcher = new AntPathMatcher();
        // 白名单，不需要保护的资源路径允许访问
        for (String ignoreUrl : ignoreUrlsConfig.getUrls()) {
            if (pathMatcher.match(ignoreUrl, path)) {
                return configAttributes;
            }
        }
        // 白名单，允许跨域请求的OPTIONS请求
        String method = ((FilterInvocation) object).getRequest().getMethod();
        if (HttpMethod.OPTIONS.toString().equals(method)) {
            return configAttributes;
        }
        // 当前系统所有权限
        configAttributeMap = CollUtil.isEmpty(configAttributeMap) ? dynamicSecurityService.loadDataSource() : configAttributeMap;
        // 判断当前请求，是否在系统权限范围内
        configAttributeMap.forEach((k, v) -> {
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
