package com.nepxion.discovery.plugin.framework.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.framework.cache.PluginCache;
import com.nepxion.discovery.plugin.framework.context.PluginContainerInitializedHandler;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.event.PluginPublisher;
import com.nepxion.discovery.plugin.framework.listener.discovery.DiscoveryListenerExecutor;
import com.nepxion.discovery.plugin.framework.listener.discovery.IpAddressFilterDiscoveryListener;
import com.nepxion.discovery.plugin.framework.listener.discovery.VersionFilterDiscoveryListener;
import com.nepxion.discovery.plugin.framework.listener.impl.IpAddressFilterLoadBalanceListener;
import com.nepxion.discovery.plugin.framework.listener.impl.LoadBalanceListenerExecutor;
import com.nepxion.discovery.plugin.framework.listener.impl.VersionFilterLoadBalanceListener;
import com.nepxion.discovery.plugin.framework.listener.register.CountFilterRegisterListener;
import com.nepxion.discovery.plugin.framework.listener.register.IpAddressFilterRegisterListener;
import com.nepxion.discovery.plugin.framework.listener.register.RegisterListenerExecutor;

@Configuration
public class PluginAutoConfiguration {
    @Bean
    public PluginContainerInitializedHandler pluginContainerInitializedHandler() {
        return new PluginContainerInitializedHandler();
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
    public PluginCache pluginCache() {
        return new PluginCache();
    }

    @Bean
    public RuleEntity ruleEntity() {
        return new RuleEntity();
    }

    @Bean
    public ReentrantReadWriteLock reentrantReadWriteLock() {
        return new ReentrantReadWriteLock();
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
    public IpAddressFilterRegisterListener ipAddressFilterRegisterListener() {
        return new IpAddressFilterRegisterListener();
    }

    @Bean
    public CountFilterRegisterListener countFilterRegisterListener() {
        return new CountFilterRegisterListener();
    }

    @Bean
    public IpAddressFilterDiscoveryListener ipAddressFilterDiscoveryListener() {
        return new IpAddressFilterDiscoveryListener();
    }

    @Bean
    public VersionFilterDiscoveryListener versionFilterDiscoveryListener() {
        return new VersionFilterDiscoveryListener();
    }

    @Bean
    public IpAddressFilterLoadBalanceListener ipAddressFilterLoadBalanceListener() {
        return new IpAddressFilterLoadBalanceListener();
    }

    @Bean
    public VersionFilterLoadBalanceListener versionFilterLoadBalanceListener() {
        return new VersionFilterLoadBalanceListener();
    }
}