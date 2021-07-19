package com.nepxion.discovery.common.entity;

/**
 * <p>Title: Nepxion Discovery</p >
 * <p>Description: Nepxion Discovery</p >
 * <p>Copyright: Copyright (c) 2017-2050</p >
 * <p>Company: Nepxion</p >
 * @author Tank
 * @version 1.0
 */

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public enum SentinelMetricType {
    PASS_QPS(DiscoveryConstant.SENTINEL_PASS_QPS_KEY),
    BLOCK_QPS(DiscoveryConstant.SENTINEL_BLOCK_QPS_KEY),
    SUCCESS_QPS(DiscoveryConstant.SENTINEL_SUCCESS_QPS_KEY),
    EXCEPTION_QPS(DiscoveryConstant.SENTINEL_EXCEPTION_QPS_KEY);

    private String value;

    private SentinelMetricType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SentinelMetricType fromString(String value) {
        for (SentinelMetricType type : SentinelMetricType.values()) {
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