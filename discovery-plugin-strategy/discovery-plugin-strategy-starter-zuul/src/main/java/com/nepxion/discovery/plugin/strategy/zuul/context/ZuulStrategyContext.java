package com.nepxion.discovery.plugin.strategy.zuul.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Hao Huang
 * @version 1.0
 */

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ZuulStrategyContext {
    private static final ThreadLocal<ZuulStrategyContext> THREAD_LOCAL = new ThreadLocal<ZuulStrategyContext>() {
        @Override
        protected ZuulStrategyContext initialValue() {
            return new ZuulStrategyContext();
        }
    };

    private HttpServletRequest request;
    private Map<String, String> headers;

    public static ZuulStrategyContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        THREAD_LOCAL.remove();
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}