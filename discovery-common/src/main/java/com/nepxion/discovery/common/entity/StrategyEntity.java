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

public class StrategyEntity implements Serializable {
    private static final long serialVersionUID = -588258474716367451L;

    private String versionValue;
    private String regionValue;
    private String addressValue;
    private String versionWeightValue;
    private String regionWeightValue;

    public String getVersionValue() {
        return versionValue;
    }

    public void setVersionValue(String versionValue) {
        this.versionValue = versionValue;
    }

    public String getRegionValue() {
        return regionValue;
    }

    public void setRegionValue(String regionValue) {
        this.regionValue = regionValue;
    }

    public String getAddressValue() {
        return addressValue;
    }

    public void setAddressValue(String addressValue) {
        this.addressValue = addressValue;
    }

    public String getVersionWeightValue() {
        return versionWeightValue;
    }

    public void setVersionWeightValue(String versionWeightValue) {
        this.versionWeightValue = versionWeightValue;
    }

    public String getRegionWeightValue() {
        return regionWeightValue;
    }

    public void setRegionWeightValue(String regionWeightValue) {
        this.regionWeightValue = regionWeightValue;
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