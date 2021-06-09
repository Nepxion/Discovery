package com.nepxion.discovery.common.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public enum SentinelRuleType {
    FLOW(DiscoveryConstant.SENTINEL_FLOW, DiscoveryConstant.SENTINEL_FLOW_KEY, DiscoveryConstant.SENTINEL_FLOW_DESCRIPTION),
    DEGRADE(DiscoveryConstant.SENTINEL_DEGRADE, DiscoveryConstant.SENTINEL_DEGRADE_KEY, DiscoveryConstant.SENTINEL_DEGRADE_DESCRIPTION),
    AUTHORITY(DiscoveryConstant.SENTINEL_AUTHORITY, DiscoveryConstant.SENTINEL_AUTHORITY_KEY, DiscoveryConstant.SENTINEL_AUTHORITY_DESCRIPTION),
    SYSTEM(DiscoveryConstant.SENTINEL_SYSTEM, DiscoveryConstant.SENTINEL_SYSTEM_KEY, DiscoveryConstant.SENTINEL_SYSTEM_DESCRIPTION),
    PARAM_FLOW(DiscoveryConstant.SENTINEL_PARAM_FLOW, DiscoveryConstant.SENTINEL_PARAM_FLOW_KEY, DiscoveryConstant.SENTINEL_PARAM_FLOW_DESCRIPTION);

    private String value;
    private String key;
    private String description;

    private SentinelRuleType(String value, String key, String description) {
        this.value = value;
        this.key = key;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public static SentinelRuleType fromString(String value) {
        return fromString(value, true);
    }

    public static SentinelRuleType fromString(String value, boolean fromValue) {
        if (fromValue) {
            for (SentinelRuleType type : SentinelRuleType.values()) {
                if (type.getValue().equalsIgnoreCase(value)) {
                    return type;
                }
            }
        } else {
            for (SentinelRuleType type : SentinelRuleType.values()) {
                if (type.getKey().equalsIgnoreCase(value)) {
                    return type;
                }
            }
        }

        throw new IllegalArgumentException("No matched type with value=" + value);
    }

    @Override
    public String toString() {
        return value;
    }
}