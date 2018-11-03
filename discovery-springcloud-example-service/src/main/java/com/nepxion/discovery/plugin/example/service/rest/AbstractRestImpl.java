package com.nepxion.discovery.plugin.example.service.rest;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;

public class AbstractRestImpl {
    @Autowired
    private PluginAdapter pluginAdapter;

    public String doRest(String value) {
        String serviceId = pluginAdapter.getServiceId();
        String host = pluginAdapter.getHost();
        int port = pluginAdapter.getPort();
        String version = pluginAdapter.getVersion();
        String region = pluginAdapter.getRegion();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(value + " -> " + serviceId);
        stringBuilder.append("[" + host + ":" + port + "]");
        if (StringUtils.isNotEmpty(version)) {
            stringBuilder.append("[V" + version + "]");
        }
        if (StringUtils.isNotEmpty(region)) {
            stringBuilder.append("[Region=" + region + "]");
        }

        return stringBuilder.toString();
    }
}