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

import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.adapter.DefaultDiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.gateway.filter.GatewayStrategyFilter;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class GatewayStrategyAutoConfiguration {
    @Bean
    public GatewayStrategyFilter gatewayStrategyFilter() {
        return new GatewayStrategyFilter();
    }

    @Bean
    public GatewayStrategyContextHolder gatewayStrategyContextHolder() {
        return new GatewayStrategyContextHolder();
    }

    @Bean
    @ConditionalOnMissingBean
    public DiscoveryEnabledAdapter discoveryEnabledAdapter() {
        return new DefaultDiscoveryEnabledAdapter();
    }
}