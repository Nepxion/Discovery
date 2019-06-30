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

public enum ServiceType {
    SERVICE(DiscoveryConstant.SERVICE_TYPE, DiscoveryConstant.SERVICE_TYPE), 
    GATEWAY(DiscoveryConstant.GATEWAY_TYPE, DiscoveryConstant.GATEWAY_TYPE);

    private String value;
    private String description;

    private ServiceType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static ServiceType fromString(String value) {
        for (ServiceType type : ServiceType.values()) {
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