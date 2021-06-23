package com.nepxion.discovery.plugin.strategy.gateway.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import reactor.core.publisher.Mono;

import org.apache.skywalking.apm.agent.core.context.TracingContext;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractTracingSpan;
import org.apache.skywalking.apm.agent.core.context.trace.EntrySpan;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.ReflectionUtil;
import com.nepxion.discovery.plugin.strategy.monitor.StrategySpan;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContext;

public class SkyWalkingGatewayStrategyFilter implements GatewayStrategyFilter {
    public static final String SKYWALING_SPAN = "SKYWALING_SPAN";
    public static final String OWNER = "owner";

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = null;

        try {
            EntrySpan entrySpan = exchange.getAttribute(SKYWALING_SPAN);

            traceId = getTraceId(entrySpan);
        } catch (Exception e) {

        }

        StrategySpan strategySpan = new StrategySpan();
        strategySpan.setTraceId(traceId != null ? traceId : DiscoveryConstant.IGNORED);
        strategySpan.setSpanId(DiscoveryConstant.IGNORED);

        StrategyTracerContext.getCurrentContext().setSpan(strategySpan);

        return chain.filter(exchange);
    }

    private String getTraceId(AbstractTracingSpan tracingSpan) throws Exception {
        if (tracingSpan == null) {
            return null;
        }

        Object owner = ReflectionUtil.getValue(AbstractTracingSpan.class, tracingSpan, OWNER);
        if (owner instanceof TracingContext) {
            TracingContext tracingContext = (TracingContext) owner;

            return tracingContext.getReadablePrimaryTraceId();
        }

        return null;
    }
}