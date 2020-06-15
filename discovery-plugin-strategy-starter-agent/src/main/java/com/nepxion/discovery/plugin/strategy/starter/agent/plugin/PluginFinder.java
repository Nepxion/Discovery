package com.nepxion.discovery.plugin.strategy.starter.agent.plugin;


import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.discover.DiscoverPlugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread.ThreadPlugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformTemplate;

public class PluginFinder {
    public static void load(TransformTemplate transformTemplate) {
        new ThreadPlugin(transformTemplate).install();
        new DiscoverPlugin(transformTemplate).install();
    }
}
