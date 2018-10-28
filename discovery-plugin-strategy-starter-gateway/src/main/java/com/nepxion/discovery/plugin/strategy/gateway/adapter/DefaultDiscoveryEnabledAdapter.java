package com.nepxion.discovery.plugin.strategy.gateway.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.adapter.AbstractDiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContextHolder;
import com.netflix.loadbalancer.Server;

public class DefaultDiscoveryEnabledAdapter extends AbstractDiscoveryEnabledAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultDiscoveryEnabledAdapter.class);

    @Autowired
    private GatewayStrategyContextHolder gatewayStrategyContextHolder;

    @Override
    protected String getVersionValue(Server server) {
        ServerWebExchange exchange = gatewayStrategyContextHolder.getExchange();
        if (exchange == null) {
            String serviceId = server.getMetaInfo().getAppName().toLowerCase();

            LOG.warn("The ServerWebExchange object is null, ignore to do version filter for service={}...", serviceId);

            return null;
        }

        return exchange.getRequest().getHeaders().getFirst(DiscoveryConstant.VERSION);
    }

    @Override
    protected String getRegionValue(Server server) {
        ServerWebExchange exchange = gatewayStrategyContextHolder.getExchange();
        if (exchange == null) {
            String serviceId = server.getMetaInfo().getAppName().toLowerCase();

            LOG.warn("The ServerWebExchange object is null, ignore to do region filter for service={}...", serviceId);

            return null;
        }

        return exchange.getRequest().getHeaders().getFirst(DiscoveryConstant.REGION);
    }
}