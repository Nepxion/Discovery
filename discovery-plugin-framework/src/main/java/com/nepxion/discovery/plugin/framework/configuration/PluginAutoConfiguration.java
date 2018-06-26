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

import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.event.PluginPublisher;
import com.nepxion.discovery.plugin.framework.strategy.DiscoveryStrategyExecutor;
import com.nepxion.discovery.plugin.framework.strategy.RegisterStrategyExecutor;
import com.nepxion.discovery.plugin.framework.strategy.impl.IpAddressFilterDiscoveryStrategy;
import com.nepxion.discovery.plugin.framework.strategy.impl.IpAddressFilterRegisterStrategy;
import com.nepxion.discovery.plugin.framework.strategy.impl.VersionFilterDiscoveryStrategy;

@Configuration
public class PluginAutoConfiguration {
    static {
        System.out.println("");
        System.out.println("╔═══╗");
        System.out.println("╚╗╔╗║");
        System.out.println(" ║║║╠╦══╦══╦══╦╗╔╦══╦═╦╗ ╔╗");
        System.out.println(" ║║║╠╣══╣╔═╣╔╗║╚╝║║═╣╔╣║ ║║");
        System.out.println("╔╝╚╝║╠══║╚═╣╚╝╠╗╔╣║═╣║║╚═╝║");
        System.out.println("╚═══╩╩══╩══╩══╝╚╝╚══╩╝╚═╗╔╝");
        System.out.println("                      ╔═╝║");
        System.out.println("                      ╚══╝");
        System.out.println("Nepxion Discovery  v2.0.3");
        System.out.println("");
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
    public RuleEntity ruleEntity() {
        return new RuleEntity();
    }

    @Bean
    public ReentrantReadWriteLock reentrantReadWriteLock() {
        return new ReentrantReadWriteLock();
    }

    @Bean
    public RegisterStrategyExecutor registerStrategyExecutor() {
        return new RegisterStrategyExecutor();
    }

    @Bean
    public DiscoveryStrategyExecutor discoveryStrategyExecutor() {
        return new DiscoveryStrategyExecutor();
    }

    @Bean
    public IpAddressFilterRegisterStrategy ipAddressFilterRegisterStrategy() {
        return new IpAddressFilterRegisterStrategy();
    }

    @Bean
    public IpAddressFilterDiscoveryStrategy ipAddressFilterDiscoveryStrategy() {
        return new IpAddressFilterDiscoveryStrategy();
    }

    @Bean
    public VersionFilterDiscoveryStrategy versionFilterDiscoveryStrategy() {
        return new VersionFilterDiscoveryStrategy();
    }
}