package com.nepxion.discovery.plugin.configcenter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.configcenter.loader.AbstractConfigLoader;
import com.nepxion.discovery.plugin.framework.event.PluginPublisher;
import com.nepxion.discovery.plugin.framework.event.RuleChangedEvent;

public abstract class ConfigAdapter extends AbstractConfigLoader {
    @Autowired
    private PluginPublisher pluginPublisher;

    public void publish(RuleChangedEvent ruleChangedEvent) {
        pluginPublisher.asyncPublish(ruleChangedEvent);
    }
}