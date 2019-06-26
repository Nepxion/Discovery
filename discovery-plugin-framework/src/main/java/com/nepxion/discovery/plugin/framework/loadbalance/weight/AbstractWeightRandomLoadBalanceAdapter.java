package com.nepxion.discovery.plugin.framework.loadbalance.weight;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextHolder;
import com.netflix.loadbalancer.Server;

public abstract class AbstractWeightRandomLoadBalanceAdapter<T> {
    protected PluginAdapter pluginAdapter;
    protected PluginContextHolder pluginContextHolder;

    public AbstractWeightRandomLoadBalanceAdapter(PluginAdapter pluginAdapter) {
        this(pluginAdapter, null);
    }

    public AbstractWeightRandomLoadBalanceAdapter(PluginAdapter pluginAdapter, PluginContextHolder pluginContextHolder) {
        this.pluginAdapter = pluginAdapter;
        this.pluginContextHolder = pluginContextHolder;
    }

    public abstract T getT();

    public abstract int getWeight(Server server, T t);
}