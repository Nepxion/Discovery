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

public enum ConfigType {
    NACOS(DiscoveryConstant.NACOS),
    APOLLO(DiscoveryConstant.APOLLO),
    REDIS(DiscoveryConstant.REDIS),
    ZOOKEEPER(DiscoveryConstant.ZOOKEEPER),
    CONSUL(DiscoveryConstant.CONSUL),
    ETCD(DiscoveryConstant.ETCD);

    public static final ConfigType[] SINGLE_KEY_CONFIG_TYPES = new ConfigType[] { APOLLO, CONSUL, ETCD };
    public static final ConfigType[] MULTI_KEY_CONFIG_TYPES = new ConfigType[] { NACOS, REDIS, ZOOKEEPER };

    private String value;

    private ConfigType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ConfigType[] getSingleKeyConfigTypes() {
        return SINGLE_KEY_CONFIG_TYPES;
    }

    public static ConfigType[] getMultiKeyConfigTypes() {
        return MULTI_KEY_CONFIG_TYPES;
    }

    public static boolean isSingleKey(ConfigType configType) {
        for (int i = 0; i < SINGLE_KEY_CONFIG_TYPES.length; i++) {
            ConfigType type = SINGLE_KEY_CONFIG_TYPES[i];
            if (type == configType) {
                return true;
            }
        }

        return false;
    }

    public static ConfigType fromString(String value) {
        for (ConfigType type : ConfigType.values()) {
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