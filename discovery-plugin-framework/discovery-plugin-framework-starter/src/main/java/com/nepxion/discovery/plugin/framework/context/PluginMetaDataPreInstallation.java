package com.nepxion.discovery.plugin.framework.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.LinkedHashMap;
import java.util.Map;

public class PluginMetaDataPreInstallation {
    private static Map<String, String> metadata = new LinkedHashMap<String, String>();

    public static Map<String, String> getMetadata() {
        return metadata;
    }
}