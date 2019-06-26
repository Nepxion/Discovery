package com.nepxion.discovery.plugin.strategy.gateway.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.strategy.adapter.DefaultDiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.constant.GatewayStrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.filter.DefaultGatewayStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.gateway.filter.GatewayStrategyFilter;
import com.nepxion.discovery.plugin.strategy.gateway.filter.GatewayStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.gateway.wrapper.DefaultCallableWrapper;
import com.nepxion.discovery.plugin.strategy.wrapper.CallableWrapper;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class GatewayStrategyAutoConfiguration {
    @Bean
    public GatewayStrategyFilter gatewayStrategyFilter() {
        return new GatewayStrategyFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = GatewayStrategyConstant.SPRING_APPLICATION_STRATEGY_GATEWAY_ROUTE_FILTER_ENABLED, matchIfMissing = true)
    public GatewayStrategyRouteFilter gatewayStrategyRouteFilter() {
        return new DefaultGatewayStrategyRouteFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public DiscoveryEnabledAdapter discoveryEnabledAdapter() {
        return new DefaultDiscoveryEnabledAdapter();
    }

    @Bean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, matchIfMissing = false)
    public CallableWrapper callableWrapper() {
        return new DefaultCallableWrapper();
    }
}