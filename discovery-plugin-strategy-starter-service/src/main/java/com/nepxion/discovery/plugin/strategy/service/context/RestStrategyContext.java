package com.nepxion.discovery.plugin.strategy.service.context;

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
import org.springframework.web.context.request.ServletRequestAttributes;

public class RestStrategyContext {
    private static final ThreadLocal<RestStrategyContext> THREAD_LOCAL = new InheritableThreadLocal<RestStrategyContext>() {
        @Override
        protected RestStrategyContext initialValue() {
            return new RestStrategyContext();
        }
    };

    public static RestStrategyContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        THREAD_LOCAL.remove();
    }

    private ServletRequestAttributes requestAttributes;

    public ServletRequestAttributes getRequestAttributes() {
        return requestAttributes;
    }

    public void setRequestAttributes(ServletRequestAttributes requestAttributes) {
        this.requestAttributes = requestAttributes;
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