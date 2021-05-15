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
    SERVICE(DiscoveryConstant.SERVICE),
    GATEWAY(DiscoveryConstant.GATEWAY),
    CONSOLE(DiscoveryConstant.CONSOLE),
    TEST(DiscoveryConstant.TEST);

    private String value;

    private ServiceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
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