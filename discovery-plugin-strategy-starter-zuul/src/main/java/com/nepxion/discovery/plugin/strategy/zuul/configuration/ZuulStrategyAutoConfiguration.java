package com.nepxion.discovery.plugin.strategy.zuul.configuration;

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
import com.nepxion.discovery.plugin.strategy.wrapper.CallableWrapper;
import com.nepxion.discovery.plugin.strategy.zuul.constant.ZuulStrategyConstant;
import com.nepxion.discovery.plugin.strategy.zuul.filter.DefaultZuulStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.zuul.filter.ZuulStrategyRouteFilter;
import com.nepxion.discovery.plugin.strategy.zuul.wrapper.DefaultCallableWrapper;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class ZuulStrategyAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_ROUTE_FILTER_ENABLED, matchIfMissing = true)
    public ZuulStrategyRouteFilter zuulStrategyRouteFilter() {
        return new DefaultZuulStrategyRouteFilter();
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