package com.nepxion.discovery.plugin.strategy.starter.agent.plugin;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.starter.agent.callback.TransformTemplate;

public abstract class Plugin {
    protected final TransformTemplate transformTemplate;

    public Plugin(TransformTemplate transformTemplate) {
        this.transformTemplate = transformTemplate;
    }

    public abstract void install();
}