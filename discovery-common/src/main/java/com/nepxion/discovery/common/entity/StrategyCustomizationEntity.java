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
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StrategyCustomizationEntity implements Serializable {
    private static final long serialVersionUID = 4903833660194433964L;

    private List<StrategyConditionEntity> strategyConditionEntityList = new ArrayList<StrategyConditionEntity>();
    private List<StrategyRouteEntity> strategyRouteEntityList = new ArrayList<StrategyRouteEntity>();

    public List<StrategyConditionEntity> getStrategyConditionEntityList() {
        return strategyConditionEntityList;
    }

    public void setStrategyConditionEntityList(List<StrategyConditionEntity> strategyConditionEntityList) {
        this.strategyConditionEntityList = strategyConditionEntityList;
    }

    public List<StrategyRouteEntity> getStrategyRouteEntityList() {
        return strategyRouteEntityList;
    }

    public void setStrategyRouteEntityList(List<StrategyRouteEntity> strategyRouteEntityList) {
        this.strategyRouteEntityList = strategyRouteEntityList;
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