package com.nepxion.discovery.plugin.framework.entity;

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

public class FilterHolderEntity implements Serializable {
    private static final long serialVersionUID = 8767022123685151416L;

    private IpAddressFilterEntity ipAddressFilterEntity;

    public FilterHolderEntity() {

    }

    public IpAddressFilterEntity getIpAddressFilterEntity() {
        return ipAddressFilterEntity;
    }

    public void setIpAddressFilterEntity(IpAddressFilterEntity ipAddressFilterEntity) {
        this.ipAddressFilterEntity = ipAddressFilterEntity;
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