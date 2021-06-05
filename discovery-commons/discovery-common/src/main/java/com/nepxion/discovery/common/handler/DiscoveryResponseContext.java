package com.nepxion.discovery.common.handler;

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

public class DiscoveryResponseContext {
    private static final ThreadLocal<DiscoveryResponseContext> THREAD_LOCAL = new ThreadLocal<DiscoveryResponseContext>() {
        @Override
        protected DiscoveryResponseContext initialValue() {
            return new DiscoveryResponseContext();
        }
    };

    private Exception error;

    public static DiscoveryResponseContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        THREAD_LOCAL.remove();
    }

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
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