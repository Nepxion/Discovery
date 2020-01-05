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
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StrategyConditionEntity implements Serializable {
    private static final long serialVersionUID = 1249482860170990672L;

    private String id;
    private String conditionHeader;
    private Map<String, String> conditionHeaderMap = new LinkedHashMap<String, String>();
    private String versionId;
    private String regionId;
    private String addressId;
    private String versionWeightId;
    private String regionWeightId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConditionHeader() {
        return conditionHeader;
    }

    public void setConditionHeader(String conditionHeader) {
        this.conditionHeader = conditionHeader;
    }

    public Map<String, String> getConditionHeaderMap() {
        return conditionHeaderMap;
    }

    public void setConditionHeaderMap(Map<String, String> conditionHeaderMap) {
        this.conditionHeaderMap = conditionHeaderMap;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getVersionWeightId() {
        return versionWeightId;
    }

    public void setVersionWeightId(String versionWeightId) {
        this.versionWeightId = versionWeightId;
    }

    public String getRegionWeightId() {
        return regionWeightId;
    }

    public void setRegionWeightId(String regionWeightId) {
        this.regionWeightId = regionWeightId;
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