package com.nepxion.discovery.plugin.framework.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.core.env.Environment;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.PropertiesUtil;

public class MetadataUtil {
    public static void filter(Map<String, String> metadata, Environment environment) {
        Properties properties = System.getProperties();

        // 支持spring.cloud.discovery.metadata.xyz配置获取
        PropertiesUtil.enrichProperties(properties, environment, DiscoveryConstant.SPRING_CLOUD_DISCOVERY_PREFIX, false, true);

        // 支持从-Dmetadata.xyz参数获取
        Set<String> propertyNames = properties.stringPropertyNames();
        for (String propertyName : propertyNames) {
            if (propertyName.startsWith(DiscoveryConstant.METADATA + ".")) {
                String key = propertyName.substring((DiscoveryConstant.METADATA + ".").length());
                String value = properties.get(propertyName).toString();
                metadata.put(key, value);
            }
        }
    }

    public static void filter(List<String> metadata, Environment environment) {
        Properties properties = System.getProperties();

        // 支持spring.cloud.discovery.metadata.xyz配置获取
        PropertiesUtil.enrichProperties(properties, environment, DiscoveryConstant.SPRING_CLOUD_DISCOVERY_PREFIX, false, true);

        // 支持从-Dmetadata.xyz参数获取
        Set<String> propertyNames = properties.stringPropertyNames();
        for (String propertyName : propertyNames) {
            if (propertyName.startsWith(DiscoveryConstant.METADATA + ".")) {
                String key = propertyName.substring((DiscoveryConstant.METADATA + ".").length());
                String value = properties.get(propertyName).toString();

                int index = getIndex(metadata, key);
                if (index > -1) {
                    metadata.set(index, key + "=" + value);
                } else {
                    metadata.add(key + "=" + value);
                }
            }
        }
    }

    public static int getIndex(List<String> metadata, String key) {
        for (int i = 0; i < metadata.size(); i++) {
            String value = metadata.get(i);
            if (value.startsWith(key + "=")) {
                return i;
            }
        }

        return -1;
    }

    public static boolean containsKey(List<String> metadata, String key) {
        for (String value : metadata) {
            if (value.startsWith(key + "=")) {
                return true;
            }
        }

        return false;
    }
}