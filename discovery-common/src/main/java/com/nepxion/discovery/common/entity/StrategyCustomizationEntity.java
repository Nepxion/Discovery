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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.StringUtil;

public class StrategyCustomizationEntity implements Serializable {
    private static final long serialVersionUID = 4903833660194433964L;

    private List<StrategyConditionEntity> strategyConditionEntityList;
    private List<StrategyWeightEntity> strategyWeightEntityList;
    private List<StrategyRouteEntity> strategyRouteEntityList;

    public List<StrategyConditionEntity> getStrategyConditionEntityList() {
        return strategyConditionEntityList;
    }

    public void setStrategyConditionEntityList(List<StrategyConditionEntity> strategyConditionEntityList) {
        this.strategyConditionEntityList = strategyConditionEntityList;
    }

    public List<StrategyWeightEntity> getStrategyWeightEntityList() {
        return strategyWeightEntityList;
    }

    public void setStrategyWeightEntityList(List<StrategyWeightEntity> strategyWeightEntityList) {
        this.strategyWeightEntityList = strategyWeightEntityList;
    }

    public List<StrategyRouteEntity> getStrategyRouteEntityList() {
        return strategyRouteEntityList;
    }

    public void setStrategyRouteEntityList(List<StrategyRouteEntity> strategyRouteEntityList) {
        this.strategyRouteEntityList = strategyRouteEntityList;

        // Header参数越多，越排在前面
        Collections.sort(this.strategyConditionEntityList, new Comparator<StrategyConditionEntity>() {
            public int compare(StrategyConditionEntity strategyConditionEntity1, StrategyConditionEntity strategyConditionEntity2) {
                Integer count1 = StringUtil.count(strategyConditionEntity1.getConditionHeader(), DiscoveryConstant.EXPRESSION_SUB_PREFIX);
                Integer count2 = StringUtil.count(strategyConditionEntity2.getConditionHeader(), DiscoveryConstant.EXPRESSION_SUB_PREFIX);

                return count2.compareTo(count1);
            }
        });
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