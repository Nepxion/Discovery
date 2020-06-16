package com.nepxion.discovery.plugin.strategy.starter.agent.plugin;

import com.nepxion.discovery.plugin.strategy.starter.agent.callback.TransformTemplate;

public abstract class Plugin {
    public abstract void install(TransformTemplate transformTemplate);
}