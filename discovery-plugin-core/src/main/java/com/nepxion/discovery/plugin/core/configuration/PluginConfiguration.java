package com.nepxion.discovery.plugin.core.configuration;

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

import com.nepxion.discovery.plugin.core.cache.PluginCache;
import com.nepxion.discovery.plugin.core.entity.PluginEntity;
import com.nepxion.discovery.plugin.core.strategy.DiscoveryStrategy;
import com.nepxion.discovery.plugin.core.strategy.RegisterStrategy;

@Configuration
public class PluginConfiguration {
    @Bean
    public PluginEntity pluginEntity() {
        return new PluginEntity();
    }

    @Bean
    public PluginCache pluginCache() {
        return new PluginCache();
    }

    @Bean
    public ReentrantReadWriteLock reentrantReadWriteLock() {
        return new ReentrantReadWriteLock();
    }

    @Bean
    public RegisterStrategy registerStrategy() {
        return new RegisterStrategy();
    }

    @Bean
    public DiscoveryStrategy discoveryStrategy() {
        return new DiscoveryStrategy();
    }
}