package com.nepxion.discovery.plugin.strategy.service.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RpcStrategyContext {
    private static final ThreadLocal<RpcStrategyContext> THREAD_LOCAL = new InheritableThreadLocal<RpcStrategyContext>() {
        @Override
        protected RpcStrategyContext initialValue() {
            return new RpcStrategyContext();
        }
    };

    public static RpcStrategyContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        THREAD_LOCAL.remove();
    }

    private final Map<String, Object> attributes = new LinkedHashMap<String, Object>();

    public RpcStrategyContext add(String key, Object value) {
        attributes.put(key, value);

        return this;
    }

    public Object get(String key) {
        return attributes.get(key);
    }

    public RpcStrategyContext remove(String key) {
        attributes.remove(key);

        return this;
    }

    public RpcStrategyContext clear() {
        attributes.clear();

        return this;
    }

    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(attributes);
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