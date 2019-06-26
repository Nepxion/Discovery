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

public enum StrategyType {
    VERSION(DiscoveryConstant.VERSION, DiscoveryConstant.VERSION), 
    REGION(DiscoveryConstant.REGION, DiscoveryConstant.REGION), 
    ADDRESS(DiscoveryConstant.ADDRESS, DiscoveryConstant.ADDRESS), 
    VERSION_WEIGHT(DiscoveryConstant.VERSION_WEIGHT, DiscoveryConstant.VERSION_WEIGHT), 
    REGION_WEIGHT(DiscoveryConstant.REGION_WEIGHT, DiscoveryConstant.REGION_WEIGHT);

    private String value;
    private String description;

    private StrategyType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static StrategyType fromString(String value) {
        for (StrategyType type : StrategyType.values()) {
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