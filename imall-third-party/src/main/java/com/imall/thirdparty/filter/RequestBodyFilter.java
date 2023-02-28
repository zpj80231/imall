package com.imall.thirdparty.filter;


import com.imall.thirdparty.support.BodyReadHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author zhangpengjun
 * @date 2023/2/27
 */
@Slf4j
@Component
public class RequestBodyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            ServletRequest requestWrapper = new BodyReadHttpServletRequestWrapper((HttpServletRequest) request);
            chain.doFilter(requestWrapper, response);
            return;
        }
        chain.doFilter(request, response);
    }

}
