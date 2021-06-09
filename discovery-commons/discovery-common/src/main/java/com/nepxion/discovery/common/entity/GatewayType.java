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

public enum GatewayType {
    SPRING_CLOUD_GATEWAY(DiscoveryConstant.SPRING_CLOUD_GATEWAY_TYPE, DiscoveryConstant.SPRING_CLOUD_GATEWAY),
    ZUUL(DiscoveryConstant.ZUUL_TYPE, DiscoveryConstant.ZUUL);

    private String value;
    private String name;

    private GatewayType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static GatewayType fromString(String value) {
        return fromString(value, true);
    }

    public static GatewayType fromString(String value, boolean fromValue) {
        if (fromValue) {
            for (GatewayType type : GatewayType.values()) {
                if (type.getValue().equalsIgnoreCase(value)) {
                    return type;
                }
            }
        } else {
            for (GatewayType type : GatewayType.values()) {
                if (type.getName().equalsIgnoreCase(value)) {
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