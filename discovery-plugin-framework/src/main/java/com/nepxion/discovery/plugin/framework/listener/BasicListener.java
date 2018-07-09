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
import com.nepxion.discovery.plugin.framework.cache.RuleCache;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.event.PluginPublisher;

public class BasicListener implements Listener {
    @Autowired
    protected RuleCache ruleCache;

    @Autowired
    protected PluginContextAware pluginContextAware;

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected PluginPublisher pluginPublisher;
}