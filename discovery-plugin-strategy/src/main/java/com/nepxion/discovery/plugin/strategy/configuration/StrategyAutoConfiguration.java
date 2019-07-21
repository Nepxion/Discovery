package com.nepxion.discovery.plugin.strategy.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.isolation.ConsumerIsolationDiscoveryStrategy;
import com.nepxion.discovery.plugin.strategy.isolation.ConsumerIsolationLoadBalanceStrategy;
import com.nepxion.discovery.plugin.strategy.isolation.ConsumerIsolationRegisterStrategy;
import com.nepxion.discovery.plugin.strategy.matcher.DiscoveryAntPathMatcherStrategy;
import com.nepxion.discovery.plugin.strategy.matcher.DiscoveryMatcherStrategy;
import com.nepxion.discovery.plugin.strategy.wrapper.CustomizationStrategyWrapper;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyWrapper;

@Configuration
@RibbonClients(defaultConfiguration = { StrategyLoadBalanceConfiguration.class })
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class StrategyAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DiscoveryMatcherStrategy discoveryMatcherStrategy() {
        return new DiscoveryAntPathMatcherStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_REGISTER_ISOLATION_ENABLED, matchIfMissing = false)
    public ConsumerIsolationRegisterStrategy consumerIsolationRegisterStrategy() {
        return new ConsumerIsolationRegisterStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONSUMER_ISOLATION_ENABLED, matchIfMissing = false)
    public ConsumerIsolationDiscoveryStrategy consumerIsolationDiscoveryStrategy() {
        return new ConsumerIsolationDiscoveryStrategy();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONSUMER_ISOLATION_ENABLED, matchIfMissing = false)
    public ConsumerIsolationLoadBalanceStrategy consumerIsolationLoadBalanceStrategy() {
        return new ConsumerIsolationLoadBalanceStrategy();
    }

    @Bean
    public StrategyWrapper strategyWrapper() {
        return new StrategyWrapper();
    }

    @Bean
    public CustomizationStrategyWrapper customizationStrategyWrapper() {
        return new CustomizationStrategyWrapper();
    }
}