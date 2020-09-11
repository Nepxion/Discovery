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

public enum ApplicationType {
    SERVICE(DiscoveryConstant.SERVICE, DiscoveryConstant.SERVICE), 
    GATEWAY(DiscoveryConstant.GATEWAY, DiscoveryConstant.GATEWAY), 
    CONSOLE(DiscoveryConstant.CONSOLE, DiscoveryConstant.CONSOLE), 
    TEST(DiscoveryConstant.TEST, DiscoveryConstant.TEST);

    private String value;
    private String description;

    private ApplicationType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static ApplicationType fromString(String value) {
        for (ApplicationType type : ApplicationType.values()) {
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