package com.nepxion.discovery.plugin.strategy.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.discovery.DiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.discovery.DiscoveryEnabledPredicate;
import com.nepxion.discovery.plugin.strategy.discovery.DiscoveryEnabledRule;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class StrategyAutoConfiguration {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    private DiscoveryEnabledAdapter discoveryEnabledAdapter;

    @Bean
    @ConditionalOnMissingBean
    // @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DiscoveryEnabledRule discoveryEnabledRule() {
        DiscoveryEnabledRule discoveryEnabledRule = new DiscoveryEnabledRule();
        DiscoveryEnabledPredicate discoveryEnabledPredicate = discoveryEnabledRule.getDiscoveryEnabledPredicate();
        discoveryEnabledPredicate.setPluginAdapter(pluginAdapter);
        discoveryEnabledPredicate.setDiscoveryEnabledAdapter(discoveryEnabledAdapter);

        return discoveryEnabledRule;
    }
}