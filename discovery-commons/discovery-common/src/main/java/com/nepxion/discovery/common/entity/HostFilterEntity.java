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
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class HostFilterEntity implements Serializable {
    private static final long serialVersionUID = 3830016495318834467L;

    private FilterType filterType;
    private List<String> filterValueList;
    private Map<String, List<String>> filterMap = new LinkedHashMap<String, List<String>>();

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public List<String> getFilterValueList() {
        return filterValueList;
    }

    public void setFilterValueList(List<String> filterValueList) {
        this.filterValueList = filterValueList;
    }

    public Map<String, List<String>> getFilterMap() {
        return filterMap;
    }

    public void setFilterMap(Map<String, List<String>> filterMap) {
        this.filterMap = filterMap;
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