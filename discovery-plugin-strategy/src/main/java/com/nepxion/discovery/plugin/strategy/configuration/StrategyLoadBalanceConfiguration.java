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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.discovery.DiscoveryEnabledAdapter;
import com.nepxion.discovery.plugin.strategy.discovery.DiscoveryEnabledBasePredicate;
import com.nepxion.discovery.plugin.strategy.discovery.DiscoveryEnabledBaseRule;
import com.nepxion.discovery.plugin.strategy.discovery.DiscoveryEnabledZoneAvoidancePredicate;
import com.nepxion.discovery.plugin.strategy.discovery.DiscoveryEnabledZoneAvoidanceRule;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;

@Configuration
@AutoConfigureBefore(RibbonClientConfiguration.class)
public class StrategyLoadBalanceConfiguration {
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_ZONE_AVOIDANCE_RULE_ENABLED + ":true}")
    private boolean zoneAvoidanceRuleEnabled;

    @Value("${ribbon.client.name}")
    private String serviceId = "client";

    @Autowired
    private PropertiesFactory propertiesFactory;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private DiscoveryEnabledAdapter discoveryEnabledAdapter;

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        if (this.propertiesFactory.isSet(IRule.class, serviceId)) {
            return this.propertiesFactory.get(IRule.class, config, serviceId);
        }

        if (zoneAvoidanceRuleEnabled) {
            DiscoveryEnabledZoneAvoidanceRule discoveryEnabledRule = new DiscoveryEnabledZoneAvoidanceRule();
            discoveryEnabledRule.initWithNiwsConfig(config);

            DiscoveryEnabledZoneAvoidancePredicate discoveryEnabledPredicate = discoveryEnabledRule.getDiscoveryEnabledPredicate();
            discoveryEnabledPredicate.setPluginAdapter(pluginAdapter);
            discoveryEnabledPredicate.setDiscoveryEnabledAdapter(discoveryEnabledAdapter);

            return discoveryEnabledRule;
        } else {
            DiscoveryEnabledBaseRule discoveryEnabledRule = new DiscoveryEnabledBaseRule();

            DiscoveryEnabledBasePredicate discoveryEnabledPredicate = discoveryEnabledRule.getDiscoveryEnabledPredicate();
            discoveryEnabledPredicate.setPluginAdapter(pluginAdapter);
            discoveryEnabledPredicate.setDiscoveryEnabledAdapter(discoveryEnabledAdapter);

            return discoveryEnabledRule;
        }
    }
}