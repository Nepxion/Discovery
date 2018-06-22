package com.nepxion.discovery.plugin.core.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DiscoveryServiceEntity implements Serializable {
    private static final long serialVersionUID = -2976565258172148792L;

    private String consumerServiceName;
    private String providerServiceName;
    private String consumerVersionValue;
    private String providerVersionValue;

    public DiscoveryServiceEntity() {

    }

    public String getConsumerServiceName() {
        return consumerServiceName;
    }

    public void setConsumerServiceName(String consumerServiceName) {
        this.consumerServiceName = consumerServiceName;
    }

    public String getProviderServiceName() {
        return providerServiceName;
    }

    public void setProviderServiceName(String providerServiceName) {
        this.providerServiceName = providerServiceName;
    }

    public String getConsumerVersionValue() {
        return consumerVersionValue;
    }

    public void setConsumerVersionValue(String consumerVersionValue) {
        this.consumerVersionValue = consumerVersionValue;
    }

    public String getProviderVersionValue() {
        return providerVersionValue;
    }

    public void setProviderVersionValue(String providerVersionValue) {
        this.providerVersionValue = providerVersionValue;
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