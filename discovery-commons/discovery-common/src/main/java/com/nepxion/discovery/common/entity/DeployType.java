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

public enum DeployType {
    DOMAIN_GATEWAY(DiscoveryConstant.DOMAIN_GATEWAY),
    NON_DOMAIN_GATEWAY(DiscoveryConstant.NON_DOMAIN_GATEWAY);

    private String value;

    private DeployType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DeployType fromString(String value) {
        for (DeployType type : DeployType.values()) {
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