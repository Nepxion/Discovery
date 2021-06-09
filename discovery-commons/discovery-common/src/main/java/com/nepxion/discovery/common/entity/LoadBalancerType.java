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

public enum LoadBalancerType {
    RIBBON(DiscoveryConstant.RIBBON),
    SPRING_CLOUD_LOADBALANCER(DiscoveryConstant.SPRING_CLOUD_LOADBALANCER);

    private String value;

    private LoadBalancerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static LoadBalancerType fromString(String value) {
        for (LoadBalancerType type : LoadBalancerType.values()) {
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