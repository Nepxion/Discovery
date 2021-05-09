package com.nepxion.discovery.plugin.strategy.sentinel.datasource.entity;

import com.nepxion.discovery.plugin.strategy.sentinel.datasource.constant.SentinelStrategyConstant;

public enum SentinelStrategyRuleType {
    FLOW(SentinelStrategyConstant.SENTINEL_STRATEGY_FLOW_KEY, SentinelStrategyConstant.SENTINEL_STRATEGY_FLOW_DESCRIPTION), 
    DEGRADE(SentinelStrategyConstant.SENTINEL_STRATEGY_DEGRADE_KEY, SentinelStrategyConstant.SENTINEL_STRATEGY_DEGRADE_DESCRIPTION), 
    AUTHORITY(SentinelStrategyConstant.SENTINEL_STRATEGY_AUTHORITY_KEY, SentinelStrategyConstant.SENTINEL_STRATEGY_AUTHORITY_DESCRIPTION), 
    SYSTEM(SentinelStrategyConstant.SENTINEL_STRATEGY_SYSTEM_KEY, SentinelStrategyConstant.SENTINEL_STRATEGY_SYSTEM_DESCRIPTION), 
    PARAM_FLOW(SentinelStrategyConstant.SENTINEL_STRATEGY_PARAM_FLOW_KEY, SentinelStrategyConstant.SENTINEL_STRATEGY_PARAM_FLOW_DESCRIPTION);

    private String value;
    private String description;

    private SentinelStrategyRuleType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static SentinelStrategyRuleType fromString(String value) {
        for (SentinelStrategyRuleType type : SentinelStrategyRuleType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("No matched type with value=" + value);
    }

    @Override
    public String toString() {
        return value;
    }
}