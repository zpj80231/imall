package com.imall.notice.support;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author zhangpengjun
 * @date 2022/7/19
 */
@Component
public class TraceIdLogging extends AbstractRequestLoggingFilter {

    private static final String TRACE_ID = "traceId";

    @Override
    protected void beforeRequest(HttpServletRequest httpServletRequest, String s) {
        String traceId = UUID.randomUUID().toString().replaceAll("-", "");
        MDC.put(TRACE_ID, traceId);
    }

    @Override
    protected void afterRequest(HttpServletRequest httpServletRequest, String s) {
        MDC.remove(TRACE_ID);
    }
}

