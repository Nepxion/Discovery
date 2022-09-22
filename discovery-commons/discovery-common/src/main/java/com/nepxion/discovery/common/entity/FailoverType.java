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

public enum FailoverType {
    VERSION_PREFER(DiscoveryConstant.VERSION_PREFER),
    VERSION_FAILOVER(DiscoveryConstant.VERSION_FAILOVER),
    REGION_TRANSFER(DiscoveryConstant.REGION_TRANSFER),
    REGION_FAILOVER(DiscoveryConstant.REGION_FAILOVER),
    ENVIRONMENT_FAILOVER(DiscoveryConstant.ENVIRONMENT_FAILOVER),
    ZONE_FAILOVER(DiscoveryConstant.ZONE_FAILOVER),  
    ADDRESS_FAILOVER(DiscoveryConstant.ADDRESS_FAILOVER);

    private String value;

    private FailoverType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FailoverType fromString(String value) {
        for (FailoverType type : FailoverType.values()) {
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