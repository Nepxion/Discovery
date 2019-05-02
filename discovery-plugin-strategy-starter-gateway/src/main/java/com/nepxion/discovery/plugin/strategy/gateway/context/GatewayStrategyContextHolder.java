package com.nepxion.discovery.plugin.strategy.gateway.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.web.server.ServerWebExchange;

public class GatewayStrategyContextHolder {
    public ServerWebExchange getExchange() {
        return GatewayStrategyContext.getCurrentContext().getExchange();
    }
}