package com.nepxion.discovery.console.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.console.constant.ConsoleConstant;

public enum SentinelRuleType {
    FLOW(ConsoleConstant.SENTINEL_FLOW),
    DEGRADE(ConsoleConstant.SENTINEL_DEGRADE),
    AUTHORITY(ConsoleConstant.SENTINEL_RULE_AUTHORITY),
    SYSTEM(ConsoleConstant.SENTINEL_RULE_SYSTEM),
    PARAM_FLOW(ConsoleConstant.SENTINEL_RULE_PARAM_FLOW);

    private String value;

    private SentinelRuleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SentinelRuleType fromString(String value) {
        for (SentinelRuleType type : SentinelRuleType.values()) {
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