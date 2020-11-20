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

public enum ElementType {
    PORTAL(DiscoveryConstant.PORTAL),
    BLUE(DiscoveryConstant.BLUE),
    GREEN(DiscoveryConstant.GREEN),
    BASIC(DiscoveryConstant.BASIC),
    GRAY(DiscoveryConstant.GRAY),
    STABLE(DiscoveryConstant.STABLE),
    UNDEFINED(DiscoveryConstant.UNDEFINED);

    private String value;

    private ElementType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ElementType fromString(String value) {
        for (ElementType type : ElementType.values()) {
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