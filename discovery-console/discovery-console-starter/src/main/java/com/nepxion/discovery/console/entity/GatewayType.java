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

public enum GatewayType {
    SPRING_CLOUD_GATEWAY(ConsoleConstant.SPRING_CLOUD_GATEWAY),
    ZUUL(ConsoleConstant.ZUUL);

    private String value;

    private GatewayType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GatewayType fromString(String value) {
        for (GatewayType type : GatewayType.values()) {
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