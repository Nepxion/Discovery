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

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.configcenter.loader.ConfigLoader;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;

public class ConfigInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigInitializer.class);

    @Autowired
    private PluginContextAware pluginContextAware;

    @Autowired(required = false)
    private ConfigLoader configLoader;

    @Autowired
    private ConfigParser configParser;

    @PostConstruct
    public void initialize() {
        Boolean registerControlEnabled = pluginContextAware.isRegisterControlEnabled();
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();
        Boolean remoteConfigEnabled = pluginContextAware.isRemoteConfigEnabled();

        if (!registerControlEnabled && !discoveryControlEnabled) {
            LOG.info("********** Register and Discovery controls are all disabled, ignore to initialize **********");

            return;
        }

        if (configLoader == null) {
            LOG.info("********** ConfigLoader isn't provided, ignore to initialize **********");

            return;
        }

        LOG.info("********** {} config starts to initialize **********", remoteConfigEnabled ? "Remote" : "Local");

        InputStream inputStream = null;
        if (remoteConfigEnabled) {
            inputStream = configLoader.getRemoteInputStream();
        } else {
            inputStream = configLoader.getLocalInputStream();
        }
        try {
            configParser.parse(inputStream);
        } catch (Exception e) {
            LOG.error("Parse rule xml failed", e);
        }
    }
}