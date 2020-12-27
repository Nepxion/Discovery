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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.ServiceType;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextHolder;
import com.nepxion.discovery.plugin.strategy.adapter.StrategyVersionFilterAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
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

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_CONSUMER_ISOLATION_ENABLED + ":false}")
    protected Boolean consumerIsolationEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_ENVIRONMENT_ROUTE + ":" + StrategyConstant.SPRING_APPLICATION_STRATEGY_ENVIRONMENT_ROUTE_VALUE + "}")
    protected String environmentRoute;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_ZONE_AFFINITY_ENABLED + ":false}")
    protected Boolean zoneAffinityEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_ZONE_ROUTE_ENABLED + ":true}")
    protected Boolean zoneRouteEnabled;

    public boolean apply(Server server) {
        // 获取对端服务的版本号
        String version = pluginAdapter.getServerVersion(server);
        // 当服务未接入本框架或者版本号未设置（表现出来的值为DiscoveryConstant.DEFAULT），则不过滤，返回
        if (StringUtils.equals(version, DiscoveryConstant.DEFAULT)) {
            return true;
        }

        // 获取对端服务所有的版本号列表
        List<String> versionList = getVersionList(server);
        // 如果没有版本号，不过滤；如果版本号只有一个，则不过滤，返回
        if (versionList.size() <= 1) {
            return true;
        }

        // 过滤出老的稳定版的版本号列表，一般来说，老的稳定版的版本号只有一个，为了增加扩展性，支持多个
        List<String> filterVersionList = strategyVersionFilterAdapter.filter(versionList);

        // 判断版本号是否在认可列表里
        return filterVersionList.contains(version);
    }

    private List<String> getVersionList(Server server) {
        // 获取对端服务的服务名
        String serviceId = pluginAdapter.getServerServiceId(server);
        // 从负载均衡缓存获取对端服务列表
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

        List<String> versionList = new ArrayList<String>();
        for (ServiceInstance instance : instances) {
            String version = pluginAdapter.getInstanceVersion(instance);
            // 当服务未接入本框架或者版本号未设置（表现出来的值为DiscoveryConstant.DEFAULT），并且不在指定环境、指定区域、指定可用区、指定IP地址和端口、指定黑名单里，不添加到认可列表
            if (!versionList.contains(version) && !StringUtils.equals(version, DiscoveryConstant.DEFAULT) && applyGroup(instance) && applyEnvironment(instance) && applyZone(instance) && applyRegion(instance) && applyAddress(instance) && applyIdBlacklist(instance) && applyAddressBlacklist(instance)) {
                versionList.add(version);
            }
        }

        if (versionList.size() > 1) {
            // 排序，把最小的版本号放在最前
            Collections.sort(versionList);
        }

        return versionList;
    }

    public boolean applyGroup(ServiceInstance instance) {
        if (!consumerIsolationEnabled) {
            return true;
        }

        String instanceServiceType = pluginAdapter.getInstanceServiceType(instance);
        if (StringUtils.equals(instanceServiceType, ServiceType.GATEWAY.toString())) {
            return true;
        }

        String instanceGroup = pluginAdapter.getInstanceGroup(instance);
        String group = pluginAdapter.getGroup();

        return StringUtils.equals(instanceGroup, group);
    }

    public boolean applyEnvironment(ServiceInstance instance) {
        String environmentValue = pluginContextHolder.getContextRouteEnvironment();
        if (StringUtils.isEmpty(environmentValue)) {
            return true;
        }

        String serviceId = pluginAdapter.getInstanceServiceId(instance);
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

        boolean matched = false;
        for (ServiceInstance serviceInstance : instances) {
            String instanceEnvironment = pluginAdapter.getInstanceEnvironment(serviceInstance);
            if (StringUtils.equals(environmentValue, instanceEnvironment)) {
                matched = true;

                break;
            }
        }

        String environment = pluginAdapter.getInstanceEnvironment(instance);
        if (matched) {
            // 匹配到传递过来的环境Header的服务实例，返回匹配的环境的服务实例
            return StringUtils.equals(environment, environmentValue);
        } else {
            // 没有匹配上，则寻址Common环境，返回Common环境的服务实例
            return StringUtils.equals(environment, environmentRoute) || StringUtils.equals(environment, DiscoveryConstant.DEFAULT);
        }
    }

    public boolean applyZone(ServiceInstance instance) {
        if (!zoneAffinityEnabled) {
            return true;
        }

        String zone = pluginAdapter.getZone();
        // 当服务未接入本框架或者版本号未设置（表现出来的值为DiscoveryConstant.DEFAULT），则不过滤，返回
        if (StringUtils.equals(zone, DiscoveryConstant.DEFAULT)) {
            return true;
        }

        String serviceId = pluginAdapter.getInstanceServiceId(instance);
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);

        boolean matched = false;
        for (ServiceInstance serviceInstance : instances) {
            String instanceZone = pluginAdapter.getInstanceZone(serviceInstance);
            if (StringUtils.equals(zone, instanceZone)) {
                matched = true;

                break;
            }
        }

        String instanceZone = pluginAdapter.getInstanceZone(instance);
        if (matched) {
            // 可用区存在：执行可用区亲和性，即调用端实例和提供端实例的元数据Metadata的zone配置值相等才能调用
            return StringUtils.equals(instanceZone, zone);
        } else {
            // 可用区不存在：路由开关打开，可路由到其它可用区；路由开关关闭，不可路由到其它可用区或者不归属任何可用区
            if (zoneRouteEnabled) {
                return true;
            } else {
                return StringUtils.equals(instanceZone, zone);
            }
        }
    }

    public boolean applyRegion(ServiceInstance instance) {
        String serviceId = pluginAdapter.getInstanceServiceId(instance);

        String regions = getRegions(serviceId);
        if (StringUtils.isEmpty(regions)) {
            return true;
        }

        String region = pluginAdapter.getInstanceRegion(instance);

        // 如果精确匹配不满足，尝试用通配符匹配
        List<String> regionList = StringUtil.splitToList(regions);
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
    public String getRegions(String serviceId) {
        String regionValue = pluginContextHolder.getContextRouteRegion();
        if (StringUtils.isEmpty(regionValue)) {
            return null;
        }

        String regions = null;
        try {
            Map<String, String> regionMap = JsonUtil.fromJson(regionValue, Map.class);
            regions = regionMap.get(serviceId);
        } catch (Exception e) {
            regions = regionValue;
        }

        return regions;
    }

    public boolean applyAddress(ServiceInstance instance) {
        String serviceId = pluginAdapter.getInstanceServiceId(instance);

        String addresses = getAddresses(serviceId);
        if (StringUtils.isEmpty(addresses)) {
            return true;
        }

        // 如果精确匹配不满足，尝试用通配符匹配
        List<String> addressList = StringUtil.splitToList(addresses);
        if (addressList.contains(instance.getHost() + ":" + instance.getPort()) || addressList.contains(instance.getHost()) || addressList.contains(String.valueOf(instance.getPort()))) {
            return true;
        }

        // 通配符匹配。前者是通配表达式，后者是具体值
        for (String addressPattern : addressList) {
            if (discoveryMatcherStrategy.match(addressPattern, instance.getHost() + ":" + instance.getPort()) || discoveryMatcherStrategy.match(addressPattern, instance.getHost()) || discoveryMatcherStrategy.match(addressPattern, String.valueOf(instance.getPort()))) {
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    public String getAddresses(String serviceId) {
        String addressValue = pluginContextHolder.getContextRouteAddress();
        if (StringUtils.isEmpty(addressValue)) {
            return null;
        }

        String addresses = null;
        try {
            Map<String, String> addressMap = JsonUtil.fromJson(addressValue, Map.class);
            addresses = addressMap.get(serviceId);
        } catch (Exception e) {
            addresses = addressValue;
        }

        return addresses;
    }

    public boolean applyIdBlacklist(ServiceInstance instance) {
        String ids = pluginContextHolder.getContextRouteIdBlacklist();
        if (StringUtils.isEmpty(ids)) {
            return true;
        }

        String serviceUUId = pluginAdapter.getInstanceServiceUUId(instance);

        List<String> idList = StringUtil.splitToList(ids);
        if (idList.contains(serviceUUId)) {
            return false;
        }

        return true;
    }

    public boolean applyAddressBlacklist(ServiceInstance instance) {
        String addresses = pluginContextHolder.getContextRouteAddressBlacklist();
        if (StringUtils.isEmpty(addresses)) {
            return true;
        }

        // 如果精确匹配不满足，尝试用通配符匹配
        List<String> addressList = StringUtil.splitToList(addresses);
        if (addressList.contains(instance.getHost() + ":" + instance.getPort()) || addressList.contains(instance.getHost()) || addressList.contains(String.valueOf(instance.getPort()))) {
            return false;
        }

        // 通配符匹配。前者是通配表达式，后者是具体值
        for (String addressPattern : addressList) {
            if (discoveryMatcherStrategy.match(addressPattern, instance.getHost() + ":" + instance.getPort()) || discoveryMatcherStrategy.match(addressPattern, instance.getHost()) || discoveryMatcherStrategy.match(addressPattern, String.valueOf(instance.getPort()))) {
                return false;
            }
        }

        return true;
    }

    public boolean applyVersion(ServiceInstance instance) {
        String serviceId = pluginAdapter.getInstanceServiceId(instance);

        String versions = getVersions(serviceId);
        if (StringUtils.isEmpty(versions)) {
            return true;
        }

        String version = pluginAdapter.getInstanceVersion(instance);

        // 如果精确匹配不满足，尝试用通配符匹配
        List<String> versionList = StringUtil.splitToList(versions);
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
    public String getVersions(String serviceId) {
        String versionValue = pluginContextHolder.getContextRouteVersion();
        if (StringUtils.isEmpty(versionValue)) {
            return null;
        }

        String versions = null;
        try {
            Map<String, String> versionMap = JsonUtil.fromJson(versionValue, Map.class);
            versions = versionMap.get(serviceId);
        } catch (Exception e) {
            versions = versionValue;
        }

        return versions;
    }
}