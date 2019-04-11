package com.nepxion.discovery.plugin.strategy.adapter;

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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.common.util.StringUtil;
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

        enabled = applyAddress(server, metadata);
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

        String version = metadata.get(DiscoveryConstant.VERSION);
        if (StringUtils.isEmpty(version)) {
            return false;
        }

        String versions = null;
        try {
            Map<String, String> versionMap = JsonUtil.fromJson(versionValue, Map.class);
            String serviceId = server.getMetaInfo().getAppName().toLowerCase();
            versions = versionMap.get(serviceId);
        } catch (Exception e) {
            versions = versionValue;
        }

        if (StringUtils.isEmpty(versions)) {
            return true;
        }

        List<String> versionList = StringUtil.splitToList(versions, DiscoveryConstant.SEPARATE);
        if (versionList.contains(version)) {
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private boolean applyRegion(Server server, Map<String, String> metadata) {
        String regionValue = getRegionValue(server);
        if (StringUtils.isEmpty(regionValue)) {
            return true;
        }

        String region = metadata.get(DiscoveryConstant.REGION);
        if (StringUtils.isEmpty(region)) {
            return false;
        }

        String regions = null;
        try {
            Map<String, String> regionMap = JsonUtil.fromJson(regionValue, Map.class);
            String serviceId = server.getMetaInfo().getAppName().toLowerCase();
            regions = regionMap.get(serviceId);
        } catch (Exception e) {
            regions = regionValue;
        }

        if (StringUtils.isEmpty(regions)) {
            return true;
        }

        List<String> regionList = StringUtil.splitToList(regions, DiscoveryConstant.SEPARATE);
        if (regionList.contains(region)) {
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private boolean applyAddress(Server server, Map<String, String> metadata) {
        String addressValue = getAddressValue(server);
        if (StringUtils.isEmpty(addressValue)) {
            return true;
        }

        Map<String, String> addressMap = JsonUtil.fromJson(addressValue, Map.class);
        String serviceId = server.getMetaInfo().getAppName().toLowerCase();
        String addresses = addressMap.get(serviceId);
        if (StringUtils.isEmpty(addresses)) {
            return true;
        }

        List<String> addressList = StringUtil.splitToList(addresses, DiscoveryConstant.SEPARATE);
        if (addressList.contains(server.getHostPort()) || addressList.contains(server.getHost())) {
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

    protected abstract String getAddressValue(Server server);
}