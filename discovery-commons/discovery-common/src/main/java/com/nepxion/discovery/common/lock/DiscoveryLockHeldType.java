package com.nepxion.discovery.common.lock;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public enum DiscoveryLockHeldType {
    // 锁被分布式持有
    DISTRIBUTION(DiscoveryConstant.DISTRIBUTION),

    // 锁被本地持有
    LOCAL(DiscoveryConstant.LOCAL);

    private String value;

    private DiscoveryLockHeldType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DiscoveryLockHeldType fromString(String value) {
        for (DiscoveryLockHeldType type : DiscoveryLockHeldType.values()) {
            if (type.getValue().equalsIgnoreCase(value.trim())) {
                return type;
            }
        }

        throw new IllegalArgumentException("Mismatched type with value=" + value);
    }

    @Override
    public String toString() {
        return value;
    }
}