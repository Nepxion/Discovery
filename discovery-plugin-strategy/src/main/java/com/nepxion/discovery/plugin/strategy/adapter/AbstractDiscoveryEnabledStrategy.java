package com.nepxion.discovery.plugin.strategy.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;

public abstract class AbstractDiscoveryEnabledStrategy implements DiscoveryEnabledStrategy {
    @Autowired
    protected PluginContextAware pluginContextAware;

    @Autowired
    protected PluginAdapter pluginAdapter;

    protected StrategyContextHolder strategyContextHolder;

    @PostConstruct
    private void initialize() {
        strategyContextHolder = pluginContextAware.getBean(StrategyContextHolder.class);
    }

    public PluginContextAware getPluginContextAware() {
        return pluginContextAware;
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    public StrategyContextHolder getStrategyContextHolder() {
        return strategyContextHolder;
    }
}