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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StrategyWeightEntity implements Serializable {
    private static final long serialVersionUID = 4992751531082022953L;

    private String id;
    private MapWeightEntity versionMapWeightEntity;
    private MapWeightEntity regionMapWeightEntity;
    private MapWeightEntity addressMapWeightEntity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MapWeightEntity getVersionMapWeightEntity() {
        return versionMapWeightEntity;
    }

    public void setVersionMapWeightEntity(MapWeightEntity versionMapWeightEntity) {
        this.versionMapWeightEntity = versionMapWeightEntity;
    }

    public MapWeightEntity getRegionMapWeightEntity() {
        return regionMapWeightEntity;
    }

    public void setRegionMapWeightEntity(MapWeightEntity regionMapWeightEntity) {
        this.regionMapWeightEntity = regionMapWeightEntity;
    }

    public MapWeightEntity getAddressMapWeightEntity() {
        return addressMapWeightEntity;
    }

    public void setAddressMapWeightEntity(MapWeightEntity addressMapWeightEntity) {
        this.addressMapWeightEntity = addressMapWeightEntity;
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