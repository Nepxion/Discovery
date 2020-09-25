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

public class RuleEntity implements Serializable {
    private static final long serialVersionUID = 7079024435084528751L;

    private RegisterEntity registerEntity;
    private DiscoveryEntity discoveryEntity;
    private StrategyEntity strategyEntity;
    private StrategyCustomizationEntity strategyCustomizationEntity;
    private StrategyBlacklistEntity strategyBlacklistEntity;
    private ParameterEntity parameterEntity;
    private String content;

    public RegisterEntity getRegisterEntity() {
        return registerEntity;
    }

    public void setRegisterEntity(RegisterEntity registerEntity) {
        this.registerEntity = registerEntity;
    }

    public DiscoveryEntity getDiscoveryEntity() {
        return discoveryEntity;
    }

    public void setDiscoveryEntity(DiscoveryEntity discoveryEntity) {
        this.discoveryEntity = discoveryEntity;
    }

    public StrategyEntity getStrategyEntity() {
        return strategyEntity;
    }

    public void setStrategyEntity(StrategyEntity strategyEntity) {
        this.strategyEntity = strategyEntity;
    }

    public StrategyCustomizationEntity getStrategyCustomizationEntity() {
        return strategyCustomizationEntity;
    }

    public void setStrategyCustomizationEntity(StrategyCustomizationEntity strategyCustomizationEntity) {
        this.strategyCustomizationEntity = strategyCustomizationEntity;
    }

    public StrategyBlacklistEntity getStrategyBlacklistEntity() {
        return strategyBlacklistEntity;
    }

    public void setStrategyBlacklistEntity(StrategyBlacklistEntity strategyBlacklistEntity) {
        this.strategyBlacklistEntity = strategyBlacklistEntity;
    }

    public ParameterEntity getParameterEntity() {
        return parameterEntity;
    }

    public void setParameterEntity(ParameterEntity parameterEntity) {
        this.parameterEntity = parameterEntity;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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