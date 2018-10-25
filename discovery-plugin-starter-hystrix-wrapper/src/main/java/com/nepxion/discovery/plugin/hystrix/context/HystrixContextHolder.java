package com.nepxion.discovery.plugin.hystrix.context;

import javax.servlet.http.HttpServletRequest;

/**
 * 上下文保持
 */
public class HystrixContextHolder {
    private static final ThreadLocal<HystrixContextHolder> THREAD_LOCAL = new ThreadLocal<HystrixContextHolder>() {
        @Override
        protected HystrixContextHolder initialValue() {
            return new HystrixContextHolder();
        }
    };

    public static HystrixContextHolder getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        THREAD_LOCAL.remove();
    }

    private HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}