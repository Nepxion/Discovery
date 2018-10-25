package com.nepxion.discovery.plugin.strategy.zuul.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.hystrix.context.HystrixCallableWrapper;
import com.nepxion.discovery.plugin.strategy.adapter.DiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.zuul.adapter.DefaultDiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.zuul.filter.ClearHystrixContextFilter;
import com.nepxion.discovery.plugin.strategy.zuul.filter.SetHystrixContextFilter;
import com.nepxion.discovery.plugin.strategy.zuul.warpper.DefaultHystrixCallableWrapper;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class ZuulStrategyAutoConfiguration {

    @Bean
    public DiscoveryEnabledAdapter defaultDiscoveryEnabledAdapter(){
        return new DefaultDiscoveryEnabledAdapter();
    }

    @Bean
    public HystrixCallableWrapper hystrixCallableWrapper(){
        return new DefaultHystrixCallableWrapper();
    }

    @Bean
    public SetHystrixContextFilter setHystrixContextFilter(){
        return new SetHystrixContextFilter();
    }

    @Bean
    public ClearHystrixContextFilter clearHystrixContextFilter(){
        return new ClearHystrixContextFilter();
    }
}