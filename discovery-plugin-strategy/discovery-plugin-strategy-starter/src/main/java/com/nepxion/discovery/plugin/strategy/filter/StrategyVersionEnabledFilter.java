package com.nepxion.discovery.plugin.strategy.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.strategy.adapter.StrategyVersionFilterAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.netflix.loadbalancer.Server;

public class StrategyVersionEnabledFilter extends AbstractStrategyEnabledFilter {
    @Autowired
    protected StrategyVersionFilterAdapter strategyVersionFilterAdapter;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_VERSION_FAILOVER_ENABLED + ":false}")
    protected Boolean versionFailoverEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_VERSION_FAILOVER_STABLE_ENABLED + ":false}")
    protected Boolean versionFailoverStableEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_VERSION_PREFER_ENABLED + ":false}")
    protected Boolean versionPreferEnabled;

    @Override
    public boolean apply(List<? extends Server> servers, Server server) {
        String serviceId = pluginAdapter.getServerServiceId(server);

        String version = pluginAdapter.getServerVersion(server);

        String versions = JsonUtil.fromJsonMap(pluginContextHolder.getContextRouteVersion(), serviceId);
        if (StringUtils.isEmpty(versions)) {
            // 版本偏好，即非蓝绿灰度发布场景下，路由到老的稳定版本的实例，或者指定版本的实例
            if (versionPreferEnabled) {
                // 版本列表排序策略的（取最老的稳定版本的实例）偏好，即不管存在多少版本，直接路由到最老的稳定版本的实例
                String versionPrefers = JsonUtil.fromJsonMap(pluginContextHolder.getContextRouteVersionPrefer(), serviceId);
                if (StringUtils.isEmpty(versionPrefers)) {
                    return containVersion(servers, server);
                } else {
                    // 指定版本的偏好，即不管存在多少版本，直接路由到该版本实例
                    return discoveryMatcher.match(versionPrefers, version, true);
                }
            } else {
                return true;
            }
        } else {
            // 版本故障转移，即无法找到相应版本的服务实例，路由到老的稳定版本的实例，或者指定版本的实例，或者执行负载均衡
            if (versionFailoverEnabled) {
                boolean matched = matchByVersion(servers, versions);
                if (!matched) {
                    String versionFailovers = JsonUtil.fromJsonMap(pluginContextHolder.getContextRouteVersionFailover(), serviceId);
                    if (StringUtils.isEmpty(versionFailovers)) {
                        if (versionFailoverStableEnabled) {
                            // 版本列表排序策略的（取最老的稳定版本的实例）故障转移，即找不到实例的时候，直接路由到最老的稳定版本的实例
                            return containVersion(servers, server);
                        } else {
                            // 负载均衡策略的故障转移，即找不到实例的时候，执行负载均衡策略
                            return true;
                        }
                    } else {
                        // 指定版本的故障转移，即找不到实例的时候，直接路由到该版本实例
                        return discoveryMatcher.match(versionFailovers, version, true);
                    }
                }
            }
        }

        return discoveryMatcher.match(versions, version, true);
    }

    public boolean containVersion(List<? extends Server> servers, Server server) {
        String version = pluginAdapter.getServerVersion(server);
        // 当服务未接入本框架或者版本号未设置（表现出来的值为DiscoveryConstant.DEFAULT），则不过滤，返回
        if (StringUtils.equals(version, DiscoveryConstant.DEFAULT)) {
            return true;
        }

        List<String> versionList = assembleVersionList(servers);
        if (versionList.size() <= 1) {
            return true;
        }

        // 过滤出老的稳定版的版本号列表，一般来说，老的稳定版的版本号只有一个，为了增加扩展性，支持多个
        List<String> filterVersionList = strategyVersionFilterAdapter.filter(versionList);

        return filterVersionList.contains(version);
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 7;
    }
}