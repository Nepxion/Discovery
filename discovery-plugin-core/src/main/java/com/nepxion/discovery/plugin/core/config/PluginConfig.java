package com.nepxion.discovery.plugin.core.config;

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

import com.nepxion.discovery.plugin.core.entity.DiscoveryEntity;
import com.nepxion.discovery.plugin.core.strategy.FilterStrategy;
import com.nepxion.discovery.plugin.core.strategy.VersionStrategy;

@Configuration
public class PluginConfig {
    @Bean
    public DiscoveryEntity discoveryEntity() {
        return new DiscoveryEntity();
    }

    @Bean
    public ReentrantReadWriteLock reentrantReadWriteLock() {
        return new ReentrantReadWriteLock();
    }

    @Bean
    public FilterStrategy filterStrategy() {
        return new FilterStrategy();
    }

    @Bean
    public VersionStrategy versionStrategy() {
        return new VersionStrategy();
    }
}