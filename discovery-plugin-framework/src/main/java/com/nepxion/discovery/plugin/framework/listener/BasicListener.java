package com.nepxion.discovery.plugin.framework.listener;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.framework.context.PluginContextAware;

public class BasicListener implements Listener {
    @Autowired
    protected PluginContextAware pluginContextAware;

    @Override
    public PluginContextAware getPluginContextAware() {
        return pluginContextAware;
    }
}