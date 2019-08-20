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

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.matcher.DiscoveryMatcherStrategy;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyWrapper;
import com.netflix.loadbalancer.Server;

public class DefaultDiscoveryEnabledAdapter implements DiscoveryEnabledAdapter {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private DiscoveryEnabledStrategy discoveryEnabledStrategy;

    @Autowired
    private DiscoveryMatcherStrategy discoveryMatcherStrategy;

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyWrapper strategyWrapper;

    protected StrategyContextHolder strategyContextHolder;

    @PostConstruct
    private void initialize() {
        strategyContextHolder = applicationContext.getBean(StrategyContextHolder.class);
    }

    @Override
    public boolean apply(Server server) {
        boolean enabled = applyVersion(server);
        if (!enabled) {
            return false;
        }

        enabled = applyRegion(server);
        if (!enabled) {
            return false;
        }

        enabled = applyAddress(server);
        if (!enabled) {
            return false;
        }

        return applyStrategy(server);
    }

    @SuppressWarnings("unchecked")
    private boolean applyVersion(Server server) {
        String versionValue = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION);
        if (StringUtils.isEmpty(versionValue)) {
            versionValue = strategyWrapper.getRouteVersion();
        }

        if (StringUtils.isEmpty(versionValue)) {
            return true;
        }

        String serviceId = pluginAdapter.getServerServiceId(server);
        String version = pluginAdapter.getServerVersion(server);

        String versions = null;
        try {
            Map<String, String> versionMap = JsonUtil.fromJson(versionValue, Map.class);
            versions = versionMap.get(serviceId);
        } catch (Exception e) {
            versions = versionValue;
        }

        if (StringUtils.isEmpty(versions)) {
            return true;
        }

        // 如果精确匹配不满足，尝试用通配符匹配
        List<String> versionList = StringUtil.splitToList(versions, DiscoveryConstant.SEPARATE);
        if (versionList.contains(version)) {
            return true;
        }

        // 通配符匹配。前者是通配表达式，后者是具体值
        for (String versionPattern : versionList) {
            if (discoveryMatcherStrategy.match(versionPattern, version)) {
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private boolean applyRegion(Server server) {
        String regionValue = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION);
        if (StringUtils.isEmpty(regionValue)) {
            regionValue = strategyWrapper.getRouteRegion();
        }

        if (StringUtils.isEmpty(regionValue)) {
            return true;
        }

        String serviceId = pluginAdapter.getServerServiceId(server);
        String region = pluginAdapter.getServerRegion(server);

        String regions = null;
        try {
            Map<String, String> regionMap = JsonUtil.fromJson(regionValue, Map.class);
            regions = regionMap.get(serviceId);
        } catch (Exception e) {
            regions = regionValue;
        }

        if (StringUtils.isEmpty(regions)) {
            return true;
        }

        // 如果精确匹配不满足，尝试用通配符匹配
        List<String> regionList = StringUtil.splitToList(regions, DiscoveryConstant.SEPARATE);
        if (regionList.contains(region)) {
            return true;
        }

        // 通配符匹配。前者是通配表达式，后者是具体值
        for (String regionPattern : regionList) {
            if (discoveryMatcherStrategy.match(regionPattern, region)) {
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private boolean applyAddress(Server server) {
        String addressValue = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS);
        if (StringUtils.isEmpty(addressValue)) {
            addressValue = strategyWrapper.getRouteAddress();
        }

        if (StringUtils.isEmpty(addressValue)) {
            return true;
        }

        String serviceId = pluginAdapter.getServerServiceId(server);

        Map<String, String> addressMap = JsonUtil.fromJson(addressValue, Map.class);
        String addresses = addressMap.get(serviceId);
        if (StringUtils.isEmpty(addresses)) {
            return true;
        }

        // 如果精确匹配不满足，尝试用通配符匹配
        List<String> addressList = StringUtil.splitToList(addresses, DiscoveryConstant.SEPARATE);
        if (addressList.contains(server.getHostPort()) || addressList.contains(server.getHost())) {
            return true;
        }

        // 通配符匹配。前者是通配表达式，后者是具体值
        for (String addressPattern : addressList) {
            if (discoveryMatcherStrategy.match(addressPattern, server.getHostPort()) || discoveryMatcherStrategy.match(addressPattern, server.getHost())) {
                return true;
            }
        }

        return false;
    }

    private boolean applyStrategy(Server server) {
        if (discoveryEnabledStrategy == null) {
            return true;
        }

        return discoveryEnabledStrategy.apply(server);
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    public StrategyContextHolder getStrategyContextHolder() {
        return strategyContextHolder;
    }
}