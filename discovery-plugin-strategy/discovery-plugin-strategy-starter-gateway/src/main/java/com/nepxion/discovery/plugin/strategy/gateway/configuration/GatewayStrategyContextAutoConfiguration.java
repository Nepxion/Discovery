package com.nepxion.discovery.plugin.strategy.gateway.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Haojun Ren
 * @author Ning Zhang
 * @version 1.0
 */

import com.nepxion.discovery.plugin.framework.adapter.DynamicRouteAdapter;
import com.nepxion.discovery.plugin.strategy.gateway.adapter.GatewayDynamicRouteAdapter;
import com.nepxion.discovery.plugin.strategy.gateway.context.GatewayStrategyContextHolder;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
public class GatewayStrategyContextAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GatewayStrategyContextHolder gatewayStrategyContextHolder() {
        return new GatewayStrategyContextHolder();
    }

    @Bean
    @ConditionalOnMissingBean
    public DynamicRouteAdapter gatewayDynamicRouteAdapter() {
        return new GatewayDynamicRouteAdapter();
    }
}