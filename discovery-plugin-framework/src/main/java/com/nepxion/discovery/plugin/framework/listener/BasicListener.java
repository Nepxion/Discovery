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

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.event.PluginEventWapper;

public class BasicListener implements Listener {
    @Autowired
    protected PluginContextAware pluginContextAware;

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected PluginEventWapper pluginEventWapper;

    public PluginContextAware getPluginContextAware() {
        return pluginContextAware;
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    public PluginEventWapper getPluginEventWapper() {
        return pluginEventWapper;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}