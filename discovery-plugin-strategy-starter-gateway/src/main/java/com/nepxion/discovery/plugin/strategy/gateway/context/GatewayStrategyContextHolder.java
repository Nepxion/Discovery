package com.nepxion.discovery.plugin.strategy.gateway.context;

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
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.plugin.strategy.context.AbstractStrategyContextHolder;

public class GatewayStrategyContextHolder extends AbstractStrategyContextHolder {
    private static final Logger LOG = LoggerFactory.getLogger(GatewayStrategyContextHolder.class);

    public ServerWebExchange getExchange() {
        return GatewayStrategyContext.getCurrentContext().getExchange();
    }

    @Override
    public String getHeader(String name) {
        ServerWebExchange exchange = getExchange();
        if (exchange == null) {
            LOG.warn("The ServerWebExchange object is lost for thread switched, or it is got before context filter probably");

            return null;
        }

        return exchange.getRequest().getHeaders().getFirst(name);
    }
}