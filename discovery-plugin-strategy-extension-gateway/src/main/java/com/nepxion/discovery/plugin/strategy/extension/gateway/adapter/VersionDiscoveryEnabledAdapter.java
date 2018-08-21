package com.nepxion.discovery.plugin.strategy.extension.gateway.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.http.server.reactive.ServerHttpRequest;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.adapter.AbstractVersionDiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.extension.gateway.context.GatewayStrategyContext;

public class VersionDiscoveryEnabledAdapter extends AbstractVersionDiscoveryEnabledAdapter {
    @Override
    protected String getVersionJson() {
        ServerHttpRequest request = getRequest();
        if (request == null) {
            return null;
        }

        return request.getHeaders().getFirst(DiscoveryConstant.VERSION);
    }

    public ServerHttpRequest getRequest() {
        GatewayStrategyContext context = GatewayStrategyContext.getCurrentContext();

        return context.getExchange().getRequest();
    }
}