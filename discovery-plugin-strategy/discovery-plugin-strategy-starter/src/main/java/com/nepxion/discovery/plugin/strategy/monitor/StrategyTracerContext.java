package com.nepxion.discovery.plugin.strategy.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author zifeihan
 * @version 1.0
 */

import java.util.LinkedList;

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

    private LinkedList<Object> spanList = new LinkedList<Object>();

    public static StrategyTracerContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        StrategyTracerContext strategyTracerContext = THREAD_LOCAL.get();
        if (strategyTracerContext == null) {
            return;
        }

        LinkedList<Object> spanList = strategyTracerContext.getSpanList();
        if (!spanList.isEmpty()) {
            spanList.removeLast();
        }

        if (spanList.isEmpty()) {
            THREAD_LOCAL.remove();
        }
    }

    public Object getSpan() {
        if (spanList.isEmpty()) {
            return null;
        }

        return spanList.getLast();
    }

    public void setSpan(Object span) {
        spanList.addLast(span);
    }

    private LinkedList<Object> getSpanList() {
        return spanList;
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