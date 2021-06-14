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

public enum InterceptorType {
    FEIGN(DiscoveryConstant.FEIGN),
    REST_TEMPLATE(DiscoveryConstant.REST_TEMPLATE),
    WEB_CLIENT(DiscoveryConstant.WEB_CLIENT);

    private String value;

    private InterceptorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static InterceptorType fromString(String value) {
        for (InterceptorType type : InterceptorType.values()) {
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