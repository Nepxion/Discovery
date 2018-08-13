package com.nepxion.discovery.plugin.strategy.extension.gateway.impl;

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

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.strategy.discovery.DiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.extension.gateway.context.GatewayStrategyContext;
import com.netflix.loadbalancer.Server;

public class VersionDiscoveryEnabledAdapter implements DiscoveryEnabledAdapter {
    @SuppressWarnings("unchecked")
    @Override
    public boolean apply(Server server, Map<String, String> metadata) {
        GatewayStrategyContext context = GatewayStrategyContext.getCurrentContext();
        String versionJson = context.getExchange().getRequest().getHeaders().getFirst(DiscoveryConstant.VERSION);
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
}