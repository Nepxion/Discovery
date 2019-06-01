package com.nepxion.discovery.plugin.strategy.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.netflix.loadbalancer.Server;

public abstract class AbstractDiscoveryEnabledStrategy implements DiscoveryEnabledStrategy {
    private StrategyContextHolder strategyContextHolder;

    @Autowired
    private PluginContextAware pluginContextAware;

    @PostConstruct
    public void initialize() {
        strategyContextHolder = pluginContextAware.getBean(StrategyContextHolder.class);
    }

    @Override
    public boolean apply(Server server, Map<String, String> metadata) {
        return apply(server, metadata, strategyContextHolder);
    }

    public abstract boolean apply(Server server, Map<String, String> metadata, StrategyContextHolder strategyContextHolder);
}