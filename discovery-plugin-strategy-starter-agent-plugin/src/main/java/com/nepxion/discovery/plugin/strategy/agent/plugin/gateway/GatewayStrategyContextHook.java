package com.nepxion.discovery.plugin.strategy.agent.plugin.gateway;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.plugin.strategy.agent.threadlocal.ThreadLocalHook;
import com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContext;

public class GatewayStrategyContextHook implements ThreadLocalHook {
    @Override
    public Object create() {
        return GatewayStrategyContext.getCurrentContext().getExchange();
    }

    @Override
    public void before(Object object) {
        if (object instanceof ServerWebExchange) {
            GatewayStrategyContext.getCurrentContext().setExchange((ServerWebExchange) object);
        }
    }

    @Override
    public void after() {
        GatewayStrategyContext.clearCurrentContext();
    }
}