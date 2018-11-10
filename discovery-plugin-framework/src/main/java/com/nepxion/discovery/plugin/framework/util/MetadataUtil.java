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

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public class MetadataUtil {
    public static void filter(Map<String, String> metadata) {
        Properties properties = System.getProperties();
        Set<String> propertyNames = properties.stringPropertyNames();
        for (String propertyName : propertyNames) {
            if (propertyName.startsWith(DiscoveryConstant.EXT + ".")) {
                String key = propertyName.substring((DiscoveryConstant.EXT + ".").length());
                String value = properties.get(propertyName).toString();
                metadata.put(key, value);
            }
        }
    }

    public static void filter(List<String> metadata) {
        Properties properties = System.getProperties();
        Set<String> propertyNames = properties.stringPropertyNames();
        for (String propertyName : propertyNames) {
            if (propertyName.startsWith(DiscoveryConstant.EXT + ".")) {
                String key = propertyName.substring((DiscoveryConstant.EXT + ".").length());
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

    private static int getIndex(List<String> metadata, String key) {
        for (int i = 0; i < metadata.size(); i++) {
            String result = metadata.get(i);
            if (result.startsWith(key + "=")) {
                return i;
            }
        }

        return -1;
    }
}