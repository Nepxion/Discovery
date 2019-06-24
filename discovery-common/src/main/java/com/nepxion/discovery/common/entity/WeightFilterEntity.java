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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WeightFilterEntity implements Serializable {
    private static final long serialVersionUID = 7313443273653189837L;

    // Consumer-service-name非空，作为Key，以Map方式存储
    private Map<String, List<WeightEntity>> weightEntityMap = new LinkedHashMap<String, List<WeightEntity>>();
    // Consumer-service-name为空，以List方式存储
    private List<WeightEntity> weightEntityList = new ArrayList<WeightEntity>();
    private VersionWeightEntity versionWeightEntity;
    private RegionWeightEntity regionWeightEntity;

    public Map<String, List<WeightEntity>> getWeightEntityMap() {
        return weightEntityMap;
    }

    public void setWeightEntityMap(Map<String, List<WeightEntity>> weightEntityMap) {
        this.weightEntityMap = weightEntityMap;
    }

    public List<WeightEntity> getWeightEntityList() {
        return weightEntityList;
    }

    public void setWeightEntityList(List<WeightEntity> weightEntityList) {
        this.weightEntityList = weightEntityList;
    }

    public VersionWeightEntity getVersionWeightEntity() {
        return versionWeightEntity;
    }

    public void setVersionWeightEntity(VersionWeightEntity versionWeightEntity) {
        this.versionWeightEntity = versionWeightEntity;
    }

    public RegionWeightEntity getRegionWeightEntity() {
        return regionWeightEntity;
    }

    public void setRegionWeightEntity(RegionWeightEntity regionWeightEntity) {
        this.regionWeightEntity = regionWeightEntity;
    }

    public boolean hasWeight() {
        return MapUtils.isNotEmpty(weightEntityMap) || CollectionUtils.isNotEmpty(weightEntityList) || versionWeightEntity != null || regionWeightEntity != null;
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