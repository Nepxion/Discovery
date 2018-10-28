package com.nepxion.discovery.plugin.strategy.adapter;

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

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.JsonUtil;
import com.netflix.loadbalancer.Server;

public abstract class AbstractDiscoveryEnabledAdapter implements DiscoveryEnabledAdapter {
    @Autowired(required = false)
    private DiscoveryEnabledStrategy discoveryEnabledStrategy;

    @Override
    public boolean apply(Server server, Map<String, String> metadata) {
        boolean enabled = applyVersion(server, metadata);
        if (!enabled) {
            return false;
        }

        enabled = applyRegion(server, metadata);
        if (!enabled) {
            return false;
        }

        return applyStrategy(server, metadata);
    }

    @SuppressWarnings("unchecked")
    private boolean applyVersion(Server server, Map<String, String> metadata) {
        String versionValue = getVersionValue(server);
        if (StringUtils.isEmpty(versionValue)) {
            return true;
        }

        String serviceId = server.getMetaInfo().getAppName().toLowerCase();
        String version = metadata.get(DiscoveryConstant.VERSION);
        if (StringUtils.isEmpty(version)) {
            return false;
        }

        Map<String, String> versionMap = JsonUtil.fromJson(versionValue, Map.class);
        String versions = versionMap.get(serviceId);
        if (versions == null) {
            return true;
        }

        if (versions.contains(version)) {
            return true;
        }

        return false;
    }

    private boolean applyRegion(Server server, Map<String, String> metadata) {
        String regionValue = getRegionValue(server);
        if (StringUtils.isEmpty(regionValue)) {
            return true;
        }

        String region = metadata.get(DiscoveryConstant.REGION);
        if (StringUtils.isEmpty(region)) {
            return false;
        }

        if (StringUtils.equals(regionValue, region)) {
            return true;
        }

        return false;
    }

    private boolean applyStrategy(Server server, Map<String, String> metadata) {
        if (discoveryEnabledStrategy == null) {
            return true;
        }

        return discoveryEnabledStrategy.apply(server, metadata);
    }

    protected abstract String getVersionValue(Server server);

    protected abstract String getRegionValue(Server server);
}