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
import com.nepxion.discovery.plugin.strategy.gateway.filter.DefaultGatewayStrategyClearFilter;
import com.nepxion.discovery.plugin.strategy.gateway.filter.DefaultGatewayStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.gateway.filter.GatewayStrategyClearFilter;
import com.nepxion.discovery.plugin.strategy.gateway.filter.GatewayStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.gateway.monitor.DefaultGatewayStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.gateway.monitor.GatewayStrategyMonitor;
import com.nepxion.discovery.plugin.strategy.gateway.wrapper.DefaultGatewayStrategyCallableWrapper;
import com.nepxion.discovery.plugin.strategy.gateway.wrapper.GatewayStrategyCallableWrapper;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class GatewayStrategyAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GatewayStrategyRouteFilter gatewayStrategyRouteFilter() {
        return new DefaultGatewayStrategyRouteFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public GatewayStrategyClearFilter gatewayStrategyClearFilter() {
        return new DefaultGatewayStrategyClearFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public GatewayStrategyMonitor gatewayStrategyMonitor() {
        return new DefaultGatewayStrategyMonitor();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, matchIfMissing = false)
    public GatewayStrategyCallableWrapper gatewayStrategyCallableWrapper() {
        return new DefaultGatewayStrategyCallableWrapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public DiscoveryEnabledAdapter discoveryEnabledAdapter() {
        return new DefaultDiscoveryEnabledAdapter();
    }
}