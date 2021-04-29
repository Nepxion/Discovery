package com.nepxion.discovery.common.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ning Zhang
 * @version 1.0
 */

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DynamicRouteEntity implements Serializable {
    private static final long serialVersionUID = -3322655604556029821L;

    private String routeId;
    private String uri;
    private String serviceName;
    private String predicates;
    private String filters;
    private Integer orderNum;

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(final String routeId) {
        this.routeId = routeId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public String getPredicates() {
        return predicates;
    }

    public void setPredicates(final String predicates) {
        this.predicates = predicates;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(final String filters) {
        this.filters = filters;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(final Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
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