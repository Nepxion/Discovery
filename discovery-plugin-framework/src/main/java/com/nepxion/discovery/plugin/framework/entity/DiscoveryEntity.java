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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DiscoveryEntity implements Serializable {
    private static final long serialVersionUID = 6281838121286637807L;

    private Map<String, List<DiscoveryServiceEntity>> serviceEntityMap = new LinkedHashMap<String, List<DiscoveryServiceEntity>>();

    public DiscoveryEntity() {

    }

    public Map<String, List<DiscoveryServiceEntity>> getServiceEntityMap() {
        return serviceEntityMap;
    }

    public void setServiceEntityMap(Map<String, List<DiscoveryServiceEntity>> serviceEntityMap) {
        this.serviceEntityMap = serviceEntityMap;
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