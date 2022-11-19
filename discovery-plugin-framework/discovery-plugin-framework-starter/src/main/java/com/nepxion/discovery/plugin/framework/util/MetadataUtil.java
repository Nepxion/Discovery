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
    // 过滤设置元数据到Metadata Map
    public static void filter(Map<String, String> metadata, Environment environment) {
        // 运维参数元数据的设置方式
        // 支持从-Dmetadata.xyz参数获取
        Properties properties = System.getProperties();
        Set<String> propertyNames = properties.stringPropertyNames();
        for (String propertyName : propertyNames) {
            if (propertyName.startsWith(DiscoveryConstant.METADATA + ".")) {
                String key = propertyName.substring((DiscoveryConstant.METADATA + ".").length());
                String value = properties.get(propertyName).toString();

                // -Dmetadata.xyz优先级最高
                // 不管元数据是否存在，直接放入或者覆盖
                metadata.put(key, value);
            }
        }

        // 统一注册中心元数据的设置方式
        // 支持spring.cloud.discovery.metadata.xyz配置获取
        Properties enrichProperties = new Properties();
        PropertiesUtil.enrichProperties(enrichProperties, environment, DiscoveryConstant.SPRING_CLOUD_DISCOVERY_PREFIX, false, true);
        Set<String> enrichPropertyNames = enrichProperties.stringPropertyNames();
        for (String enrichPropertyName : enrichPropertyNames) {
            if (enrichPropertyName.startsWith(DiscoveryConstant.METADATA + ".")) {
                String key = enrichPropertyName.substring((DiscoveryConstant.METADATA + ".").length());
                String value = enrichProperties.get(enrichPropertyName).toString();

                // spring.cloud.discovery.metadata.xyz优先级最低
                // 当元数据不存在，才放入
                if (!metadata.containsKey(key)) {
                    metadata.put(key, value);
                }
            }
        }
    }

    // 过滤设置元数据到Metadata List
    // 该方式适用于旧版Consul元数据模式
    public static void filter(List<String> metadata, Environment environment) {
        // 运维参数元数据的设置方式
        // 支持从-Dmetadata.xyz参数获取
        Properties properties = System.getProperties();
        Set<String> propertyNames = properties.stringPropertyNames();
        for (String propertyName : propertyNames) {
            if (propertyName.startsWith(DiscoveryConstant.METADATA + ".")) {
                String key = propertyName.substring((DiscoveryConstant.METADATA + ".").length());
                String value = properties.get(propertyName).toString();

                // -Dmetadata.xyz优先级最高
                // 不管元数据是否存在，直接放入或者覆盖
                int index = getIndex(metadata, key);
                if (index > -1) {
                    metadata.set(index, key + "=" + value);
                } else {
                    metadata.add(key + "=" + value);
                }
            }
        }

        // 统一注册中心元数据的设置方式
        // 支持spring.cloud.discovery.metadata.xyz配置获取
        Properties enrichProperties = new Properties();
        PropertiesUtil.enrichProperties(enrichProperties, environment, DiscoveryConstant.SPRING_CLOUD_DISCOVERY_PREFIX, false, true);
        Set<String> enrichPropertyNames = enrichProperties.stringPropertyNames();
        for (String enrichPropertyName : enrichPropertyNames) {
            if (enrichPropertyName.startsWith(DiscoveryConstant.METADATA + ".")) {
                String key = enrichPropertyName.substring((DiscoveryConstant.METADATA + ".").length());
                String value = enrichProperties.get(enrichPropertyName).toString();

                // spring.cloud.discovery.metadata.xyz优先级最低
                // 当元数据不存在，才放入
                int index = getIndex(metadata, key);
                if (index <= -1) {
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