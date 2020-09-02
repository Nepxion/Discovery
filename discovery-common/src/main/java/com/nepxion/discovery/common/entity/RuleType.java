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

public enum RuleType {
    LOCAL(DiscoveryConstant.LOCAL_TYPE, DiscoveryConstant.LOCAL_TYPE),
    REMOTE_GLOBAL(DiscoveryConstant.REMOTE_GLOBAL_TYPE, DiscoveryConstant.REMOTE_GLOBAL_TYPE), 
    REMOTE_PARTIAL(DiscoveryConstant.REMOTE_PARTIAL_TYPE, DiscoveryConstant.REMOTE_PARTIAL_TYPE);

    private String value;
    private String description;

    private RuleType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static RuleType fromString(String value) {
        for (RuleType type : RuleType.values()) {
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