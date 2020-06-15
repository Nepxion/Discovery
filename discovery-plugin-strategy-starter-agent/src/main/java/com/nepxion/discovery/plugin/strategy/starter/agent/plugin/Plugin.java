package com.nepxion.discovery.plugin.strategy.starter.agent.plugin;


import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformTemplate;

public abstract class Plugin {

    protected final TransformTemplate transformTemplate;

    public Plugin(TransformTemplate transformTemplate) {
        this.transformTemplate = transformTemplate;
    }

    public abstract void install();
}
