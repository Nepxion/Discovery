package com.nepxion.discovery.plugin.configcenter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.Subscribe;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.eventbus.annotation.EventBus;
import com.nepxion.eventbus.core.Event;

@EventBus
public class ConfigSubscriber {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigSubscriber.class);

    @Autowired
    private PluginContextAware pluginContextAware;

    @Autowired
    private ConfigParser configParser;

    @Subscribe
    public void subscribe(Event event) {
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        Boolean remoteConfigEnabled = pluginContextAware.isRemoteConfigEnabled();

        if (!discoveryControlEnabled) {
            LOG.info("********** Discovery control is disabled, ignore to subscribe **********");

            return;
        }

        if (!remoteConfigEnabled) {
            LOG.info("********** Remote config is disabled, ignore to subscribe **********");

            return;
        }

        Object object = event.getSource();
        if (object instanceof InputStream) {
            LOG.info("********** Remote config change has been subscribed **********");

            InputStream inputStream = (InputStream) object;
            configParser.parse(inputStream);
        }
    }
}