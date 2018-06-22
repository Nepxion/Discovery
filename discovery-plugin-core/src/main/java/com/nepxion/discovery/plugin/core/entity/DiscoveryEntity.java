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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DiscoveryEntity implements Serializable {
    private static final long serialVersionUID = 6281838121286637807L;

    private List<ConsumerEntity> consumerEntityList = new ArrayList<ConsumerEntity>();

    public DiscoveryEntity() {

    }

    public List<ConsumerEntity> getConsumerEntityList() {
        return consumerEntityList;
    }

    public void setConsumerEntityList(List<ConsumerEntity> consumerEntityList) {
        this.consumerEntityList = consumerEntityList;
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