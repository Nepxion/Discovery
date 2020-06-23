package com.nepxion.discovery.plugin.strategy.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextHolder;
import com.nepxion.discovery.plugin.strategy.adapter.StrategyVersionFilterAdapter;
import com.nepxion.discovery.plugin.strategy.matcher.DiscoveryMatcherStrategy;
import com.netflix.loadbalancer.Server;

public class StrategyVersionFilter {
    @Autowired
    private DiscoveryMatcherStrategy discoveryMatcherStrategy;

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected PluginContextHolder pluginContextHolder;

    @Autowired
    protected StrategyVersionFilterAdapter strategyVersionFilterAdapter;

    @Autowired
    protected DiscoveryClient discoveryClient;

    public boolean apply(Server server) {
        // 抽象获取版本号的Value
        String version = pluginAdapter.getServerVersion(server);
        // 当服务未接入本框架或者版本号未设置（表现出来的值为DiscoveryConstant.DEFAULT），不过滤
        if (StringUtils.isEmpty(version) || StringUtils.equals(version, DiscoveryConstant.DEFAULT)) {
            return true;
        }

        // 获取对端的服务名
        String serviceId = pluginAdapter.getServerServiceId(server);
        // 从负载均衡缓存获取对端的服务列表
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        // 获取对端服务所有的版本号列表
        List<String> instanceVersionList = getInstanceVersionList(instances);
        // 如果没有版本号，不过滤；如果版本号只有一个，不过滤
        if (instanceVersionList.size() <= 1) {
            return true;
        }

        // 通过暴露对外接口获取跨组调用下的老的稳定版的版本号列表，一般来说，老的稳定版的版本号只有一个，为了增加扩展性，支持多个
        // 给予全局版本号列表，过滤出老的稳定版的版本号列表
        List<String> filterInstanceVersionList = strategyVersionFilterAdapter.filter(instanceVersionList);

        // 判断版本号是否在认可列表里
        return filterInstanceVersionList.contains(version);
    }

    private List<String> getInstanceVersionList(List<ServiceInstance> instances) {
        List<String> instanceVersionList = new ArrayList<String>();
        for (ServiceInstance instance : instances) {
            String instanceVersion = pluginAdapter.getInstanceVersion(instance);
            // 当服务未接入本框架或者版本号未设置（表现出来的值为DiscoveryConstant.DEFAULT），并且在指定区域，不添加到认可列表
            if (StringUtils.isNotEmpty(instanceVersion) && !StringUtils.equals(instanceVersion, DiscoveryConstant.DEFAULT) && !instanceVersionList.contains(instanceVersion) && applyRegion(instance)) {
                instanceVersionList.add(instanceVersion);
            }
        }

        // 排序有助于更好的过滤
        Collections.sort(instanceVersionList);

        return instanceVersionList;
    }

    private boolean applyRegion(ServiceInstance instance) {
        String regions = getRegions(instance);
        if (StringUtils.isEmpty(regions)) {
            return true;
        }

        String region = pluginAdapter.getInstanceRegion(instance);

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
    private String getRegions(ServiceInstance instance) {
        String regionValue = pluginContextHolder.getContextRouteRegion();
        if (StringUtils.isEmpty(regionValue)) {
            return null;
        }

        String serviceId = pluginAdapter.getInstanceServiceId(instance);

        String regions = null;
        try {
            Map<String, String> regionMap = JsonUtil.fromJson(regionValue, Map.class);
            regions = regionMap.get(serviceId);
        } catch (Exception e) {
            regions = regionValue;
        }

        return regions;
    }
}