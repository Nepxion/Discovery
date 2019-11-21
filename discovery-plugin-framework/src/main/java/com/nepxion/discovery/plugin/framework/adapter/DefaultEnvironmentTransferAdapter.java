package com.nepxion.discovery.plugin.framework.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

public abstract class DefaultEnvironmentTransferAdapter implements EnvironmentTransferAdapter {
    @Autowired
    protected PluginAdapter pluginAdapter;

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }
}