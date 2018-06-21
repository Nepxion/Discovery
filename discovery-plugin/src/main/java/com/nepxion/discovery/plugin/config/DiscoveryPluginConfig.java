package com.nepxion.discovery.plugin.config;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.plugin.entity.DiscoveryEntity;
import com.nepxion.discovery.plugin.strategy.FilterStrategy;
import com.nepxion.discovery.plugin.strategy.VersionStrategy;

@Configuration
public class DiscoveryPluginConfig {
    @Autowired
    private DiscoveryPluginConfigLoader discoveryPluginConfigLoader;

    @Bean
    public DiscoveryPluginConfigParser discoveryPluginConfigParser() {
        return new DiscoveryPluginConfigParser();
    }

    @Bean
    public DiscoveryPluginConfigLoader discoveryPluginConfigLoader() {
        return new DiscoveryPluginConfigLoader();
    }

    @Bean
    public DiscoveryEntity discoveryEntity() {
        return new DiscoveryEntity();
    }

    @Bean
    public ReentrantLock reentrantLock() {
        return new ReentrantLock();
    }

    @Bean
    public FilterStrategy filterStrategy() {
        return new FilterStrategy();
    }

    @Bean
    public VersionStrategy versionStrategy() {
        return new VersionStrategy();
    }

    @PostConstruct
    public void initialize() {
        discoveryPluginConfigLoader.initialize();
    }
}