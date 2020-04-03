package com.nepxion.discovery.plugin.strategy.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StrategyTracerContext {
    private static final ThreadLocal<StrategyTracerContext> THREAD_LOCAL = new ThreadLocal<StrategyTracerContext>() {
        @Override
        protected StrategyTracerContext initialValue() {
            return new StrategyTracerContext();
        }
    };

    private Object span;
    private String traceId;
    private String spanId;

    public static StrategyTracerContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        THREAD_LOCAL.remove();
    }

    public Object getSpan() {
        return span;
    }

    public void setSpan(Object span) {
        this.span = span;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
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