package com.nepxion.discovery.plugin.strategy.zuul.configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextHolder extends HashMap<String, Object> {
    private static final ThreadLocal<ContextHolder> THREAD_LOCAL = new InheritableThreadLocal<ContextHolder>() {
        @Override
        protected ContextHolder initialValue() {
            try {
                return new ContextHolder();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    };
    private static ContextHolder contextHolder;

    public static ContextHolder getCurrentContext() {
        if (contextHolder != null) return contextHolder;
        ContextHolder context = THREAD_LOCAL.get();
        return context;
    }

    public void addZuulRequestHeader(String name, String value) {
        getZuulRequestHeaders().put(name.toLowerCase(), value);
    }

    public Map<String, String> getZuulRequestHeaders() {
        if (get("zuulRequestHeaders") == null) {
            HashMap<String, String> zuulRequestHeaders = new HashMap<String, String>();
            putIfAbsent("zuulRequestHeaders", zuulRequestHeaders);
        }
        return (Map<String, String>) get("zuulRequestHeaders");
    }
    public void setRequest(HttpServletRequest request) {
        put("request", request);
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) get("request");
    }


    public void setRequestQueryParams(Map<String, List<String>> qp) {
        put("requestQueryParams", qp);
    }
    public Map<String, List<String>> getRequestQueryParams() {
        return (Map<String, List<String>>) get("requestQueryParams");
    }
}
