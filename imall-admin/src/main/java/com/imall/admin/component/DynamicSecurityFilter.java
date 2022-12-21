package com.imall.admin.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 动态权限过滤器
 *
 * @author zhangpengjun
 * @date 2022/9/5
 */
@Slf4j
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;
    @Autowired
    private DynamicAccessDecisionManager dynamicAccessDecisionManager;

    @PostConstruct
    public void initDynamicAccessDecisionManager() {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        invoke(new FilterInvocation(servletRequest, servletResponse, chain));
    }

    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        // 调用 AccessDecisionManager.decide 鉴权
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetadataSource;
    }
}
