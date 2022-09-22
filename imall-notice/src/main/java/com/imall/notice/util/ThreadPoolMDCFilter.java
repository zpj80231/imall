package com.imall.notice.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;


/**
 * 线程池 mdc filter
 *
 * @author zhangpengjun
 * @date 2022/09/22
 */
public class ThreadPoolMDCFilter {

    private static final String TRACE_ID = "traceId";

    public static void setSleuthTraceId() {
        if (StringUtils.isNotBlank(MDC.get(TRACE_ID))) {
            MDC.put(TRACE_ID, MDC.get(TRACE_ID));
        }
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setSleuthTraceId();
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setSleuthTraceId();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

}
