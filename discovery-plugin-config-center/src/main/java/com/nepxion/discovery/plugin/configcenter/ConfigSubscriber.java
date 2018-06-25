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
import org.springframework.beans.factory.annotation.Value;

import com.google.common.eventbus.Subscribe;
import com.nepxion.discovery.plugin.configcenter.constant.ConfigConstant;
import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.eventbus.annotation.EventBus;
import com.nepxion.eventbus.core.Event;

@EventBus
public class ConfigSubscriber {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigSubscriber.class);

    @Value("${" + PluginConstant.SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED + ":true}")
    private Boolean discoveryControlEnabled;

    @Value("${" + ConfigConstant.SPRING_APPLICATION_DISCOVERY_REMOTE_CONFIG_ENABLED + ":true}")
    private Boolean remoteConfigEnabled;

    @Autowired
    private ConfigParser configParser;

    @Subscribe
    public void subscribe(Event event) {
        if (!discoveryControlEnabled) {
            LOG.info("********** Discovery control is disabled, reject to accept remote push **********");

            return;
        }

        if (!remoteConfigEnabled) {
            LOG.info("********** Remote config is disabled, reject to accept remote push **********");

            return;
        }

        Object object = event.getSource();
        if (object instanceof InputStream) {
            LOG.info("********** Remote config change has been retrieved **********");

            InputStream inputStream = (InputStream) object;
            configParser.parse(inputStream);
        }
    }
}