package com.nepxion.discovery.common.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class HeadersInjectorEntity implements Serializable {
    private static final long serialVersionUID = 4754115929939146799L;

    private HeadersInjectorType headersInjectorType;
    private List<String> headers;

    public HeadersInjectorEntity() {

    }

    public HeadersInjectorEntity(HeadersInjectorType headersInjectorType, List<String> headers) {
        this.headersInjectorType = headersInjectorType;
        this.headers = headers;
    }

    public HeadersInjectorType getHeadersInjectorType() {
        return headersInjectorType;
    }

    public void setHeadersInjectorType(HeadersInjectorType headersInjectorType) {
        this.headersInjectorType = headersInjectorType;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
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