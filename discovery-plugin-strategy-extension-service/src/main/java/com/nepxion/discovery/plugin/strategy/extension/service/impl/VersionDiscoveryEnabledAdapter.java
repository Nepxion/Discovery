package com.nepxion.discovery.plugin.strategy.extension.service.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.strategy.discovery.DiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.discovery.DiscoveryEnabledExtension;
import com.netflix.loadbalancer.Server;

public class VersionDiscoveryEnabledAdapter implements DiscoveryEnabledAdapter {
    @Autowired(required = false)
    private DiscoveryEnabledExtension discoveryEnabledExtension;

    @Override
    public boolean apply(Server server, Map<String, String> metadata) {
        boolean enabled = applyVersion(server, metadata);
        if (!enabled) {
            return false;
        }

        return applyExtension(server, metadata);
    }

    @SuppressWarnings("unchecked")
    private boolean applyVersion(Server server, Map<String, String> metadata) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return true;
        }

        String versionJson = attributes.getRequest().getHeader(DiscoveryConstant.VERSION);
        if (StringUtils.isEmpty(versionJson)) {
            return true;
        }

        String serviceId = server.getMetaInfo().getAppName().toLowerCase();
        String version = metadata.get(DiscoveryConstant.VERSION);
        if (StringUtils.isEmpty(version)) {
            return true;
        }

        Map<String, String> versionMap = JsonUtil.fromJson(versionJson, Map.class);
        String versions = versionMap.get(serviceId);
        if (StringUtils.isEmpty(versions)) {
            return true;
        }

        if (versions.contains(version)) {
            return true;
        }

        return false;
    }

    private boolean applyExtension(Server server, Map<String, String> metadata) {
        if (discoveryEnabledExtension == null) {
            return true;
        }

        return discoveryEnabledExtension.apply(server, metadata);
    }
}