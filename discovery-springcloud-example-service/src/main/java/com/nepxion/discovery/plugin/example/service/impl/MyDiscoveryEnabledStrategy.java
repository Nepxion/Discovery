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
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledStrategy;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;
import com.netflix.loadbalancer.Server;

// 实现了组合策略，版本路由策略+区域路由策略+自定义策略
public class MyDiscoveryEnabledStrategy implements DiscoveryEnabledStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(MyDiscoveryEnabledStrategy.class);

    @Autowired
    private ServiceStrategyContextHolder serviceStrategyContextHolder;

    @Override
    public boolean apply(Server server, Map<String, String> metadata) {
        // 对Rest调用传来的Header参数（例如Token）做策略
        boolean enabled = applyFromHeader(server, metadata);
        if (!enabled) {
            return false;
        }

        // 对RPC调用传来的方法参数做策略
        return applyFromMethod(server, metadata);
    }

    // 根据Rest调用传来的Header参数（例如Token），选取执行调用请求的服务实例
    private boolean applyFromHeader(Server server, Map<String, String> metadata) {
        ServletRequestAttributes attributes = serviceStrategyContextHolder.getRequestAttributes();
        if (attributes == null) {
            return true;
        }

        String token = attributes.getRequest().getHeader("token");
        // String value = attributes.getRequest().getParameter("value");

        String serviceId = server.getMetaInfo().getAppName().toLowerCase();

        LOG.info("Serivice端负载均衡用户定制触发：serviceId={}, host={}, metadata={}, attributes={}", serviceId, server.toString(), metadata, attributes);

        String filterServiceId = "discovery-springcloud-example-c";
        String filterToken = "123";
        if (StringUtils.equals(serviceId, filterServiceId) && StringUtils.isNotEmpty(token) && token.contains(filterToken)) {
            LOG.info("过滤条件：当serviceId={} && Token含有'{}'的时候，不能被Ribbon负载均衡到", filterServiceId, filterToken);

            return false;
        }

        return true;
    }

    // 根据RPC调用传来的方法参数（例如接口名、方法名、参数名或参数值等），选取执行调用请求的服务实例
    @SuppressWarnings("unchecked")
    private boolean applyFromMethod(Server server, Map<String, String> metadata) {
        Map<String, Object> attributes = serviceStrategyContextHolder.getMethodAttributes();

        String serviceId = server.getMetaInfo().getAppName().toLowerCase();
        String version = metadata.get(DiscoveryConstant.VERSION);

        LOG.info("Serivice端负载均衡用户定制触发：serviceId={}, host={}, metadata={}, attributes={}", serviceId, server.toString(), metadata, attributes);

        String filterServiceId = "discovery-springcloud-example-b";
        String filterVersion = "1.0";
        String filterBusinessValue = "abc";
        if (StringUtils.equals(serviceId, filterServiceId) && StringUtils.equals(version, filterVersion)) {
            if (attributes.containsKey(ServiceStrategyConstant.PARAMETER_MAP)) {
                Map<String, Object> parameterMap = (Map<String, Object>) attributes.get(ServiceStrategyConstant.PARAMETER_MAP);
                String value = parameterMap.get("value").toString();
                if (StringUtils.isNotEmpty(value) && value.contains(filterBusinessValue)) {
                    LOG.info("过滤条件：当serviceId={} && version={} && 业务参数含有'{}'的时候，不能被Ribbon负载均衡到", filterServiceId, filterVersion, filterBusinessValue);

                    return false;
                }
            }
        }

        return true;
    }
}