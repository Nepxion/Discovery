package com.nepxion.discovery.plugin.strategy.service.context;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author zlliu
 * @date 2020/10/22 18:16
 */
public class RequestBodyContext {
    private static final ThreadLocal<RequestBodyContext> THREAD_LOCAL =
            ThreadLocal.withInitial(() -> new RequestBodyContext());

    /**
     * request body cache
     */
    private Map<String, Object> body = new LinkedHashMap<>();

    public static RequestBodyContext getContext() {
        return THREAD_LOCAL.get();
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }

    public Map<String, Object> get() {
        return body;
    }

    public void set(Map<String, Object> body) {
        this.body = body;
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