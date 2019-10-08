package com.nepxion.discovery.plugin.strategy.service.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;

public abstract class DefaultFeignStrategyInterceptorAdapter implements FeignStrategyInterceptorAdapter {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected ServiceStrategyContextHolder serviceStrategyContextHolder;

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    public ServiceStrategyContextHolder getServiceStrategyContextHolder() {
        return serviceStrategyContextHolder;
    }
}