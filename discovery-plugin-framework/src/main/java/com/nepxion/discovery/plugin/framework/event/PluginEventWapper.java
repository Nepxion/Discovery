package com.nepxion.discovery.plugin.framework.event;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;

public class PluginEventWapper {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private PluginPublisher pluginPublisher;

    @Autowired
    private PluginSubscriber pluginSubscriber;

    public void fireRuleUpdated(RuleUpdatedEvent ruleUpdatedEvent, boolean async) {
        if (async) {
            pluginPublisher.asyncPublish(ruleUpdatedEvent);
        } else {
            pluginSubscriber.onRuleUpdated(ruleUpdatedEvent);
        }
    }

    public void fireRuleCleared(RuleClearedEvent ruleClearedEvent, boolean async) {
        if (async) {
            pluginPublisher.asyncPublish(ruleClearedEvent);
        } else {
            pluginSubscriber.onRuleCleared(ruleClearedEvent);
        }
    }

    public void fireRuleFailure(RuleFailureEvent ruleFailureEvent) {
        pluginPublisher.asyncPublish(ruleFailureEvent);
    }

    public void fireVersionUpdated(VersionUpdatedEvent versionUpdatedEvent, boolean async) {
        if (async) {
            pluginPublisher.asyncPublish(versionUpdatedEvent);
        } else {
            pluginSubscriber.onVersionUpdated(versionUpdatedEvent);
        }
    }

    public void fireVersionCleared(VersionClearedEvent versionClearedEvent, boolean async) {
        if (async) {
            pluginPublisher.asyncPublish(versionClearedEvent);
        } else {
            pluginSubscriber.onVersionCleared(versionClearedEvent);
        }
    }

    public void fireParameterChanged(ParameterChangedEvent parameterChangedEvent) {
        pluginPublisher.asyncPublish(parameterChangedEvent);
    }

    public void fireParameterChanged() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        fireParameterChanged(new ParameterChangedEvent(ruleEntity != null ? ruleEntity.getParameterEntity() : null));
    }

    public void fireRegisterFailure(RegisterFailureEvent registerFailureEvent) {
        pluginPublisher.asyncPublish(registerFailureEvent);
    }
}