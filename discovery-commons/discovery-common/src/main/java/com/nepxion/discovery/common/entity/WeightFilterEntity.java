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
    /**
     * 例如配置：<service consumer-service-name="discovery-springcloud-example-b" provider-service-name="discovery-springcloud-example-c" provider-weight-value="1.0=90;1.1=10" type="version"/>
     */
    private Map<String, List<WeightEntity>> versionWeightEntityMap;
    // Consumer-service-name为空，以List方式存储
    /**
     * 例如配置：<service provider-service-name="discovery-springcloud-example-c" provider-weight-value="1.0=90;1.1=10" type="version"/>
     */
    private List<WeightEntity> versionWeightEntityList;

    /**
     * 例如配置：<version provider-weight-value="1.0=90;1.1=10"/>
     */
    private VersionWeightEntity versionWeightEntity;

    // Consumer-service-name非空，作为Key，以Map方式存储
    /**
     * 例如配置：<service consumer-service-name="discovery-springcloud-example-b" provider-service-name="discovery-springcloud-example-c" provider-weight-value="dev=85;qa=15" type="region"/>
     */
    private Map<String, List<WeightEntity>> regionWeightEntityMap;
    // Consumer-service-name为空，以List方式存储
    /**
     * 例如配置： <service provider-service-name="discovery-springcloud-example-c" provider-weight-value="dev=85;qa=15" type="region"/>
     */
    private List<WeightEntity> regionWeightEntityList;
    /**
     * 例如配置：<region provider-weight-value="dev=85;qa=15"/>
     */
    private RegionWeightEntity regionWeightEntity;

    public Map<String, List<WeightEntity>> getVersionWeightEntityMap() {
        return versionWeightEntityMap;
    }

    public void setVersionWeightEntityMap(Map<String, List<WeightEntity>> versionWeightEntityMap) {
        this.versionWeightEntityMap = versionWeightEntityMap;
    }

    public List<WeightEntity> getVersionWeightEntityList() {
        return versionWeightEntityList;
    }

    public void setVersionWeightEntityList(List<WeightEntity> versionWeightEntityList) {
        this.versionWeightEntityList = versionWeightEntityList;
    }

    public VersionWeightEntity getVersionWeightEntity() {
        return versionWeightEntity;
    }

    public void setVersionWeightEntity(VersionWeightEntity versionWeightEntity) {
        this.versionWeightEntity = versionWeightEntity;
    }

    public Map<String, List<WeightEntity>> getRegionWeightEntityMap() {
        return regionWeightEntityMap;
    }

    public void setRegionWeightEntityMap(Map<String, List<WeightEntity>> regionWeightEntityMap) {
        this.regionWeightEntityMap = regionWeightEntityMap;
    }

    public List<WeightEntity> getRegionWeightEntityList() {
        return regionWeightEntityList;
    }

    public void setRegionWeightEntityList(List<WeightEntity> regionWeightEntityList) {
        this.regionWeightEntityList = regionWeightEntityList;
    }

    public RegionWeightEntity getRegionWeightEntity() {
        return regionWeightEntity;
    }

    public void setRegionWeightEntity(RegionWeightEntity regionWeightEntity) {
        this.regionWeightEntity = regionWeightEntity;
    }

    public boolean hasWeight() {
        return MapUtils.isNotEmpty(versionWeightEntityMap) || CollectionUtils.isNotEmpty(versionWeightEntityList) || versionWeightEntity != null ||
                MapUtils.isNotEmpty(regionWeightEntityMap) || CollectionUtils.isNotEmpty(regionWeightEntityList) || regionWeightEntity != null;
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