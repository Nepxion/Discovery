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

public enum WeightRandomType {
    MAP_WEIGHT_RANDOM(DiscoveryConstant.MAP_WEIGHT_RANDOM),
    ARRAY_WEIGHT_RANDOM(DiscoveryConstant.ARRAY_WEIGHT_RANDOM);

    private String value;

    private WeightRandomType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static WeightRandomType fromString(String value) {
        for (WeightRandomType type : WeightRandomType.values()) {
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