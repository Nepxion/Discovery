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

public enum StrategyRouteType {
    VERSION(DiscoveryConstant.VERSION),
    REGION(DiscoveryConstant.REGION),
    ADDRESS(DiscoveryConstant.ADDRESS),
    VERSION_WEIGHT(DiscoveryConstant.VERSION_WEIGHT),
    REGION_WEIGHT(DiscoveryConstant.REGION_WEIGHT),
    ID_BLACKLIST(DiscoveryConstant.ID_BLACKLIST),
    ADDRESS_BLACKLIST(DiscoveryConstant.ADDRESS_BLACKLIST);

    private String value;

    private StrategyRouteType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static StrategyRouteType fromString(String value) {
        for (StrategyRouteType type : StrategyRouteType.values()) {
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