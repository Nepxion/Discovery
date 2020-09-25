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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.StringUtil;

public class StrategyCustomizationEntity implements Serializable {
    private static final long serialVersionUID = 4903833660194433964L;

    private List<StrategyConditionBlueGreenEntity> strategyConditionBlueGreenEntityList;
    private List<StrategyConditionGrayEntity> strategyConditionGrayEntityList;
    private List<StrategyRouteEntity> strategyRouteEntityList;
    private StrategyHeaderEntity strategyHeaderEntity;

    public List<StrategyConditionBlueGreenEntity> getStrategyConditionBlueGreenEntityList() {
        return strategyConditionBlueGreenEntityList;
    }

    public void setStrategyConditionBlueGreenEntityList(List<StrategyConditionBlueGreenEntity> strategyConditionBlueGreenEntityList) {
        this.strategyConditionBlueGreenEntityList = strategyConditionBlueGreenEntityList;
    }

    public List<StrategyConditionGrayEntity> getStrategyConditionGrayEntityList() {
        return strategyConditionGrayEntityList;
    }

    public void setStrategyConditionGrayEntityList(List<StrategyConditionGrayEntity> strategyConditionGrayEntityList) {
        this.strategyConditionGrayEntityList = strategyConditionGrayEntityList;
    }

    public List<StrategyRouteEntity> getStrategyRouteEntityList() {
        return strategyRouteEntityList;
    }

    public void setStrategyRouteEntityList(List<StrategyRouteEntity> strategyRouteEntityList) {
        this.strategyRouteEntityList = strategyRouteEntityList;

        // Header参数越多，越排在前面
        if (CollectionUtils.isNotEmpty(strategyConditionBlueGreenEntityList)) {
            Collections.sort(strategyConditionBlueGreenEntityList, new Comparator<StrategyConditionBlueGreenEntity>() {
                public int compare(StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity1, StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity2) {
                    Integer count1 = StringUtil.count(strategyConditionBlueGreenEntity1.getConditionHeader(), DiscoveryConstant.EXPRESSION_SUB_PREFIX);
                    Integer count2 = StringUtil.count(strategyConditionBlueGreenEntity2.getConditionHeader(), DiscoveryConstant.EXPRESSION_SUB_PREFIX);

                    return count2.compareTo(count1);
                }
            });
        }

        // Header参数越多，越排在前面
        if (CollectionUtils.isNotEmpty(strategyConditionGrayEntityList)) {
            Collections.sort(strategyConditionGrayEntityList, new Comparator<StrategyConditionGrayEntity>() {
                public int compare(StrategyConditionGrayEntity strategyConditionGrayEntity1, StrategyConditionGrayEntity strategyConditionGrayEntity2) {
                    Integer count1 = StringUtil.count(strategyConditionGrayEntity1.getConditionHeader(), DiscoveryConstant.EXPRESSION_SUB_PREFIX);
                    Integer count2 = StringUtil.count(strategyConditionGrayEntity2.getConditionHeader(), DiscoveryConstant.EXPRESSION_SUB_PREFIX);

                    return count2.compareTo(count1);
                }
            });
        }
    }

    public StrategyHeaderEntity getStrategyHeaderEntity() {
        return strategyHeaderEntity;
    }

    public void setStrategyHeaderEntity(StrategyHeaderEntity strategyHeaderEntity) {
        this.strategyHeaderEntity = strategyHeaderEntity;
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