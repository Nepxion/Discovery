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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public class ConditionStrategy implements Serializable {
    private static final long serialVersionUID = 2137809071409890088L;

    private List<String> service;
    private List<ConditionBlueGreenEntity> blueGreen;
    private List<ConditionGrayEntity> gray;
    private Map<String, String> header;
    private String sort = DiscoveryConstant.SORT_BY_VERSION;

    public List<String> getService() {
        return service;
    }

    public void setService(List<String> service) {
        this.service = service;
    }

    public List<ConditionBlueGreenEntity> getBlueGreen() {
        return blueGreen;
    }

    public void setBlueGreen(List<ConditionBlueGreenEntity> blueGreen) {
        this.blueGreen = blueGreen;
    }

    public List<ConditionGrayEntity> getGray() {
        return gray;
    }

    public void setGray(List<ConditionGrayEntity> gray) {
        this.gray = gray;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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