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

public class StrategyFailoverEntity implements Serializable {
    private static final long serialVersionUID = 5321045298130813301L;

    private String versionPreferValue;
    private String versionFailoverValue;
    private String regionTransferValue;
    private String regionFailoverValue;
    private String environmentFailoverValue;
    private String zoneFailoverValue;
    private String addressFailoverValue;

    public String getVersionPreferValue() {
        return versionPreferValue;
    }

    public void setVersionPreferValue(String versionPreferValue) {
        this.versionPreferValue = versionPreferValue;
    }

    public String getVersionFailoverValue() {
        return versionFailoverValue;
    }

    public void setVersionFailoverValue(String versionFailoverValue) {
        this.versionFailoverValue = versionFailoverValue;
    }

    public String getRegionTransferValue() {
        return regionTransferValue;
    }

    public void setRegionTransferValue(String regionTransferValue) {
        this.regionTransferValue = regionTransferValue;
    }

    public String getRegionFailoverValue() {
        return regionFailoverValue;
    }

    public void setRegionFailoverValue(String regionFailoverValue) {
        this.regionFailoverValue = regionFailoverValue;
    }

    public String getEnvironmentFailoverValue() {
        return environmentFailoverValue;
    }

    public void setEnvironmentFailoverValue(String environmentFailoverValue) {
        this.environmentFailoverValue = environmentFailoverValue;
    }

    public String getZoneFailoverValue() {
        return zoneFailoverValue;
    }

    public void setZoneFailoverValue(String zoneFailoverValue) {
        this.zoneFailoverValue = zoneFailoverValue;
    }

    public String getAddressFailoverValue() {
        return addressFailoverValue;
    }

    public void setAddressFailoverValue(String addressFailoverValue) {
        this.addressFailoverValue = addressFailoverValue;
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