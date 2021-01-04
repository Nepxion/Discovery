package com.nepxion.discovery.plugin.example.service.impl;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;
import com.netflix.loadbalancer.Server;

// 实现了组合策略，版本路由策略+区域路由策略+IP地址和端口路由策略+自定义策略
public class MyDiscoveryEnabledStrategy implements DiscoveryEnabledStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(MyDiscoveryEnabledStrategy.class);

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private ServiceStrategyContextHolder serviceStrategyContextHolder;

    @Override
    public boolean apply(Server server) {
        // 对Rest调用传来的Header参数（例如：token）做策略
        boolean enabled = applyFromHeader(server);
        if (!enabled) {
            return false;
        }

        // 对RPC调用传来的方法参数做策略
        return applyFromMethod(server);
    }

    // 根据REST调用传来的Header参数（例如：token），选取执行调用请求的服务实例
    private boolean applyFromHeader(Server server) {
        String token = serviceStrategyContextHolder.getHeader("token");
        String serviceId = pluginAdapter.getServerServiceId(server);
        String version = pluginAdapter.getServerVersion(server);
        String region = pluginAdapter.getServerRegion(server);
        String environment = pluginAdapter.getServerEnvironment(server);
        String address = server.getHost() + ":" + server.getPort();

        LOG.info("负载均衡用户定制触发：token={}, serviceId={}, version={}, region={}, env={}, address={}", token, serviceId, version, region, environment, address);

        String filterServiceId = "discovery-springcloud-example-c";
        String filterToken = "123";
        if (StringUtils.equals(serviceId, filterServiceId) && StringUtils.isNotEmpty(token) && token.contains(filterToken)) {
            LOG.info("过滤条件：当serviceId={} && Token含有'{}'的时候，不能被负载均衡到", filterServiceId, filterToken);

            return false;
        }

        return true;
    }

    // 根据RPC调用传来的方法参数（例如接口名、方法名、参数名或参数值等），选取执行调用请求的服务实例
    @SuppressWarnings("unchecked")
    private boolean applyFromMethod(Server server) {
        Map<String, Object> attributes = serviceStrategyContextHolder.getRpcAttributes();
        String serviceId = pluginAdapter.getServerServiceId(server);
        String version = pluginAdapter.getServerVersion(server);
        String region = pluginAdapter.getServerRegion(server);
        String environment = pluginAdapter.getServerEnvironment(server);
        String address = server.getHost() + ":" + server.getPort();

        LOG.info("负载均衡用户定制触发：attributes={}, serviceId={}, version={}, region={}, env={}, address={}", attributes, serviceId, version, region, environment, address);

        String filterServiceId = "discovery-springcloud-example-b";
        String filterVersion = "1.0";
        String filterBusinessValue = "abc";
        if (StringUtils.equals(serviceId, filterServiceId) && StringUtils.equals(version, filterVersion)) {
            if (attributes.containsKey(DiscoveryConstant.PARAMETER_MAP)) {
                Map<String, Object> parameterMap = (Map<String, Object>) attributes.get(DiscoveryConstant.PARAMETER_MAP);
                String value = parameterMap.get("value").toString();
                if (StringUtils.isNotEmpty(value) && value.contains(filterBusinessValue)) {
                    LOG.info("过滤条件：当serviceId={} && version={} && 业务参数含有'{}'的时候，不能被负载均衡到", filterServiceId, filterVersion, filterBusinessValue);

                    return false;
                }
            }
        }

        return true;
    }
}