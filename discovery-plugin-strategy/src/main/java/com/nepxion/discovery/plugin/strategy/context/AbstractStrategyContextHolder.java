package com.nepxion.discovery.plugin.strategy.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.framework.context.PluginContextHolder;

public abstract class AbstractStrategyContextHolder implements PluginContextHolder, StrategyContextHolder {
    @Override
    public String getContext(String name) {
        return getHeader(name);
    }
}