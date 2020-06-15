package com.nepxion.discovery.plugin.strategy.starter.agent.plugin;


import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformTemplate;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author zifeihan
 * @version 1.0
 */
public abstract class Plugin {

    protected final TransformTemplate transformTemplate;

    public Plugin(TransformTemplate transformTemplate) {
        this.transformTemplate = transformTemplate;
    }

    public abstract void install();
}
