package com.nepxion.discovery.plugin.strategy.sentinel.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.sentinel.constant.SentinelStrategyConstant;

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