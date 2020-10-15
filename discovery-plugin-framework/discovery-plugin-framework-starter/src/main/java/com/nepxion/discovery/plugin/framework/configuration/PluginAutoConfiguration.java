package com.nepxion.discovery.plugin.framework.configuration;

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
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.cache.PluginCache;
import com.nepxion.discovery.plugin.framework.cache.RuleCache;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.event.PluginEventWapper;
import com.nepxion.discovery.plugin.framework.event.PluginPublisher;
import com.nepxion.discovery.plugin.framework.event.PluginSubscriber;
import com.nepxion.discovery.plugin.framework.generator.GitGenerator;
import com.nepxion.discovery.plugin.framework.generator.GroupGenerator;
import com.nepxion.discovery.plugin.framework.listener.discovery.DiscoveryListenerExecutor;
import com.nepxion.discovery.plugin.framework.listener.discovery.HostFilterDiscoveryListener;
import com.nepxion.discovery.plugin.framework.listener.discovery.RegionFilterDiscoveryListener;
import com.nepxion.discovery.plugin.framework.listener.discovery.VersionFilterDiscoveryListener;
import com.nepxion.discovery.plugin.framework.listener.discovery.ZoneFilterDiscoveryListener;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.HostFilterLoadBalanceListener;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.LoadBalanceListenerExecutor;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.NotificationLoadBalanceListener;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.RegionFilterLoadBalanceListener;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.VersionFilterLoadBalanceListener;
import com.nepxion.discovery.plugin.framework.listener.loadbalance.ZoneFilterLoadBalanceListener;
import com.nepxion.discovery.plugin.framework.listener.register.CountFilterRegisterListener;
import com.nepxion.discovery.plugin.framework.listener.register.HostFilterRegisterListener;
import com.nepxion.discovery.plugin.framework.listener.register.RegisterListenerExecutor;
import com.nepxion.discovery.plugin.framework.ribbon.RibbonProcessor;
import com.nepxion.eventbus.annotation.EnableEventBus;

@Configuration
@EnableEventBus
public class PluginAutoConfiguration {
    @Bean
    @LoadBalanced
    public RestTemplate pluginRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PluginContextAware pluginContextAware() {
        return new PluginContextAware();
    }

    @Bean
    public PluginPublisher pluginPublisher() {
        return new PluginPublisher();
    }

    @Bean
    public PluginSubscriber pluginSubscriber() {
        return new PluginSubscriber();
    }

    @Bean
    public PluginEventWapper pluginEventWapper() {
        return new PluginEventWapper();
    }

    @Bean
    public PluginCache pluginCache() {
        return new PluginCache();
    }

    @Bean
    public RuleCache ruleCache() {
        return new RuleCache();
    }

    @Bean
    public RibbonProcessor ribbonProcessor() {
        return new RibbonProcessor();
    }

    @Bean
    public RegisterListenerExecutor registerListenerExecutor() {
        return new RegisterListenerExecutor();
    }

    @Bean
    public DiscoveryListenerExecutor discoveryListenerExecutor() {
        return new DiscoveryListenerExecutor();
    }

    @Bean
    public LoadBalanceListenerExecutor loadBalanceListenerExecutor() {
        return new LoadBalanceListenerExecutor();
    }

    @Bean
    @ConditionalOnMissingBean
    public HostFilterRegisterListener hostFilterRegisterListener() {
        return new HostFilterRegisterListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public CountFilterRegisterListener countFilterRegisterListener() {
        return new CountFilterRegisterListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public HostFilterDiscoveryListener hostFilterDiscoveryListener() {
        return new HostFilterDiscoveryListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public VersionFilterDiscoveryListener versionFilterDiscoveryListener() {
        return new VersionFilterDiscoveryListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public RegionFilterDiscoveryListener regionFilterDiscoveryListener() {
        return new RegionFilterDiscoveryListener();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = DiscoveryConstant.SPRING_APPLICATION_ZONE_AFFINITY_ENABLED, matchIfMissing = false)
    public ZoneFilterDiscoveryListener zoneFilterDiscoveryListener() {
        return new ZoneFilterDiscoveryListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public HostFilterLoadBalanceListener hostFilterLoadBalanceListener() {
        return new HostFilterLoadBalanceListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public VersionFilterLoadBalanceListener versionFilterLoadBalanceListener() {
        return new VersionFilterLoadBalanceListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public RegionFilterLoadBalanceListener regionFilterLoadBalanceListener() {
        return new RegionFilterLoadBalanceListener();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = DiscoveryConstant.SPRING_APPLICATION_ZONE_AFFINITY_ENABLED, matchIfMissing = false)
    public ZoneFilterLoadBalanceListener zoneFilterLoadBalanceListener() {
        return new ZoneFilterLoadBalanceListener();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = DiscoveryConstant.SPRING_APPLICATION_NO_SERVERS_NOTIFY_ENABLED, matchIfMissing = false)
    public NotificationLoadBalanceListener notificationLoadBalanceListener() {
        return new NotificationLoadBalanceListener();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = DiscoveryConstant.SPRING_APPLICATION_GROUP_GENERATOR_ENABLED, matchIfMissing = false)
    public GroupGenerator groupGenerator() {
        return new GroupGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = DiscoveryConstant.SPRING_APPLICATION_GIT_GENERATOR_ENABLED, matchIfMissing = false)
    public GitGenerator gitGenerator() {
        return new GitGenerator();
    }
}