package com.nepxion.discovery.plugin.configcenter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.configcenter.loader.LocalConfigLoader;
import com.nepxion.discovery.plugin.configcenter.loader.RemoteConfigLoader;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.config.PluginConfigParser;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;

public class ConfigInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigInitializer.class);

    @Autowired
    private PluginContextAware pluginContextAware;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private PluginConfigParser pluginConfigParser;

    @Autowired
    private LocalConfigLoader localConfigLoader;

    @Autowired(required = false)
    private RemoteConfigLoader remoteConfigLoader;

    @PostConstruct
    public void initialize() {
        Boolean registerControlEnabled = pluginContextAware.isRegisterControlEnabled();
        Boolean discoveryControlEnabled = pluginContextAware.isDiscoveryControlEnabled();

        if (!registerControlEnabled && !discoveryControlEnabled) {
            LOG.info("Register and Discovery controls are all disabled, ignore to initialize");

            return;
        }

        LOG.info("Rule starts to initialize...");

        String remoteConfig = getRemoteConfig();
        if (StringUtils.isNotEmpty(remoteConfig)) {
            try {
                RuleEntity ruleEntity = pluginConfigParser.parse(remoteConfig);
                pluginAdapter.setDynamicRule(ruleEntity);
            } catch (Exception e) {
                LOG.error("Parse rule xml failed", e);
            }
        }

        String localConfig = getLocalConfig();
        if (StringUtils.isNotEmpty(localConfig)) {
            try {
                RuleEntity ruleEntity = pluginConfigParser.parse(localConfig);
                pluginAdapter.setLocalRule(ruleEntity);
            } catch (Exception e) {
                LOG.error("Parse rule xml failed", e);
            }
        }

        if (StringUtils.isEmpty(remoteConfig) && StringUtils.isEmpty(localConfig)) {
            LOG.info("No config is retrieved");
        }
    }

    private String getRemoteConfig() {
        if (remoteConfigLoader != null) {
            String config = null;

            try {
                config = remoteConfigLoader.getConfig();
            } catch (Exception e) {
                LOG.warn("Get remote config failed", e);
            }

            if (StringUtils.isNotEmpty(config)) {
                LOG.info("Remote config is retrieved");

                return config;
            } else {
                LOG.info("Remote config isn't retrieved");
            }
        } else {
            LOG.info("Remote config loader isn't provided");
        }

        return null;
    }

    private String getLocalConfig() {
        String config = null;

        try {
            config = localConfigLoader.getConfig();
        } catch (Exception e) {
            LOG.warn("Get local config failed", e);
        }

        if (StringUtils.isNotEmpty(config)) {
            LOG.info("Local config is retrieved");

            return config;
        } else {
            LOG.info("Local config isn't retrieved");
        }

        return null;
    }
}