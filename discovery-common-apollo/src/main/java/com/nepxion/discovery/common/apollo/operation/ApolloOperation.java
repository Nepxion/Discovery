package com.nepxion.discovery.common.apollo.operation;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.google.common.collect.Sets;

public class ApolloOperation {
    @Autowired
    private Config apolloConfig;

    public String getConfig(String group, String serviceId) {
        return apolloConfig.getProperty(group + "-" + serviceId, null);
    }

    public ConfigChangeListener subscribeConfig(String group, String serviceId, ApolloSubscribeCallback subscribeCallback) {
        ConfigChangeListener configListener = new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent changeEvent) {
                ConfigChange change = changeEvent.getChange(group + "-" + serviceId);
                String config = change.getNewValue();
                subscribeCallback.callback(config);
            }
        };

        apolloConfig.addChangeListener(configListener, Sets.newHashSet(group + "-" + serviceId));

        return configListener;
    }

    public void unsubscribeConfig(String group, String serviceId, ConfigChangeListener configListener) {
        apolloConfig.removeChangeListener(configListener);
    }
}