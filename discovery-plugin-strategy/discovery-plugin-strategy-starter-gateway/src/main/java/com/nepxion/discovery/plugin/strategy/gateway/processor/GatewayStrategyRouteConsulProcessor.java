package com.nepxion.discovery.plugin.strategy.gateway.processor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.consul.proccessor.ConsulProcessor;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.gateway.constant.GatewayStrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.route.GatewayStrategyRoute;

public class GatewayStrategyRouteConsulProcessor extends ConsulProcessor {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private GatewayStrategyRoute gatewayStrategyRoute;

    @Override
    public String getGroup() {
        return pluginAdapter.getGroup();
    }

    @Override
    public String getDataId() {
        return pluginAdapter.getServiceId() + "-" + GatewayStrategyConstant.GATEWAY_DYNAMIC_ROUTE;
    }

    @Override
    public String getDescription() {
        return GatewayStrategyConstant.GATEWAY_DYNAMIC_ROUTE_DESCRIPTION;
    }

    @Override
    public void callbackConfig(String config) {
        gatewayStrategyRoute.updateAll(config);
    }
}