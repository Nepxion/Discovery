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

public class VersionEntity implements Serializable {
    private static final long serialVersionUID = 9074414583796627542L;

    private String consumerServiceName;
    private String providerServiceName;
    private List<String> consumerVersionValueList;
    private List<String> providerVersionValueList;

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

    public List<String> getConsumerVersionValueList() {
        return consumerVersionValueList;
    }

    public void setConsumerVersionValueList(List<String> consumerVersionValueList) {
        this.consumerVersionValueList = consumerVersionValueList;
    }

    public List<String> getProviderVersionValueList() {
        return providerVersionValueList;
    }

    public void setProviderVersionValueList(List<String> providerVersionValueList) {
        this.providerVersionValueList = providerVersionValueList;
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