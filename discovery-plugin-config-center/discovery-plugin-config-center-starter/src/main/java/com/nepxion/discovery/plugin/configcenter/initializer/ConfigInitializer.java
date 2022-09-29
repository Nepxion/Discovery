package com.nepxion.discovery.plugin.configcenter.initializer;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.SubscriptionType;
import com.nepxion.discovery.plugin.configcenter.loader.LocalConfigLoader;
import com.nepxion.discovery.plugin.configcenter.loader.RemoteConfigLoader;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.event.PluginEventWapper;
import com.nepxion.discovery.plugin.framework.event.RuleFailureEvent;
import com.nepxion.discovery.plugin.framework.parser.PluginConfigParser;

public class ConfigInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigInitializer.class);

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private PluginConfigParser pluginConfigParser;

    @Autowired
    private PluginEventWapper pluginEventWapper;

    @Autowired
    private LocalConfigLoader localConfigLoader;

    @Autowired(required = false)
    private RemoteConfigLoader remoteConfigLoader;

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_PARAMETER_EVENT_ONSTART_ENABLED + ":true}")
    private Boolean parameterEventOnstartEnabled;

    @PostConstruct
    public void initialize() {
        LOG.info("------------- Load Discovery Config --------------");

        String[] remoteConfigList = getRemoteConfigList();
        if (remoteConfigList != null) {
            String partialRemoteConfig = remoteConfigList[0];
            if (StringUtils.isNotEmpty(partialRemoteConfig)) {
                LOG.info("Initialize partial remote config...");

                try {
                    RuleEntity ruleEntity = pluginConfigParser.parse(partialRemoteConfig);
                    pluginAdapter.setDynamicPartialRule(ruleEntity);
                } catch (Exception e) {
                    LOG.error("Initialize partial remote config failed", e);

                    pluginEventWapper.fireRuleFailure(new RuleFailureEvent(SubscriptionType.PARTIAL, partialRemoteConfig, e));
                }
            }

            String globalRemoteConfig = remoteConfigList[1];
            if (StringUtils.isNotEmpty(globalRemoteConfig)) {
                LOG.info("Initialize global remote config...");

                try {
                    RuleEntity ruleEntity = pluginConfigParser.parse(globalRemoteConfig);
                    pluginAdapter.setDynamicGlobalRule(ruleEntity);
                } catch (Exception e) {
                    LOG.error("Initialize global remote config failed", e);

                    pluginEventWapper.fireRuleFailure(new RuleFailureEvent(SubscriptionType.GLOBAL, globalRemoteConfig, e));
                }
            }
        }

        String[] localConfigList = getLocalConfigList();
        if (localConfigList != null) {
            String localConfig = localConfigList[0];
            if (StringUtils.isNotEmpty(localConfig)) {
                LOG.info("Initialize local config...");

                try {
                    RuleEntity ruleEntity = pluginConfigParser.parse(localConfig);
                    pluginAdapter.setLocalRule(ruleEntity);
                } catch (Exception e) {
                    LOG.error("Initialize local config failed", e);
                }
            }
        }

        if (remoteConfigList == null && localConfigList == null) {
            LOG.info("No configs are found");
        }

        // 初始化配置的时候，是否触发fireParameterChanged的EventBus事件
        if (parameterEventOnstartEnabled) {
            pluginEventWapper.fireParameterChanged();
        }

        LOG.info("--------------------------------------------------");
    }

    private String[] getRemoteConfigList() {
        if (remoteConfigLoader != null) {
            String[] configList = null;

            try {
                configList = remoteConfigLoader.getConfigList();
            } catch (Exception e) {
                LOG.warn("Get remote config list failed", e);
            }

            return configList;
        } else {
            LOG.info("Remote config loader isn't provided");
        }

        return null;
    }

    private String[] getLocalConfigList() {
        String[] configList = null;

        try {
            configList = localConfigLoader.getConfigList();
        } catch (Exception e) {
            LOG.warn("Get local config list failed", e);
        }

        return configList;
    }
}