package com.nepxion.discovery.plugin.strategy.starter.agent.plugin;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.discovery.gateway.DiscoveryGatewayPlugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.discovery.monitor.DiscoveryMonitorPlugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.discovery.service.DiscoveryServicePlugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.discovery.zuul.DiscoveryZuulPlugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread.ThreadPlugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformTemplate;

public class PluginFinder {
    public static void load(TransformTemplate transformTemplate) {
        new ThreadPlugin(transformTemplate).install();
        new DiscoveryMonitorPlugin(transformTemplate).install();
        new DiscoveryServicePlugin(transformTemplate).install();
        new DiscoveryZuulPlugin(transformTemplate).install();
        new DiscoveryGatewayPlugin(transformTemplate).install();
    }
}