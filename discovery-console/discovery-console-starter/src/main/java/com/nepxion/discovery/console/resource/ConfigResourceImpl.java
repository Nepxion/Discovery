package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.ConfigFormatType;
import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.entity.FormatType;
import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.console.adapter.ConfigAdapter;
import com.nepxion.discovery.console.rest.ConfigClearRestInvoker;
import com.nepxion.discovery.console.rest.ConfigUpdateRestInvoker;
import com.nepxion.discovery.console.rest.ConfigViewRestInvoker;
import com.nepxion.discovery.plugin.framework.parser.PluginConfigDeparser;
import com.nepxion.discovery.plugin.framework.parser.PluginConfigParser;

public class ConfigResourceImpl implements ConfigResource {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigResourceImpl.class);

    @Autowired(required = false)
    private ConfigAdapter configAdapter;

    @Autowired
    private ServiceResource serviceResource;

    @Autowired
    private RestTemplate consoleRestTemplate;

    @Autowired
    private PluginConfigParser pluginConfigParser;

    @Autowired
    private PluginConfigDeparser pluginConfigDeparser;

    @Autowired
    private Environment environment;

    @Override
    public ConfigType getConfigType() {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            throw new DiscoveryException("Remote config adapter isn't provided");
        }

        return configAdapter.getConfigType();
    }

    @Override
    public boolean updateRemoteConfig(String group, String serviceId, String config) throws Exception {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            throw new DiscoveryException("Remote config adapter isn't provided");
        }

        return configAdapter.updateConfig(group, serviceId, config);
    }

    @Override
    public boolean updateRemoteConfig(String group, String serviceId, String config, FormatType formatType) throws Exception {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            throw new DiscoveryException("Remote config adapter isn't provided");
        }

        return configAdapter.updateConfig(group, serviceId, config, formatType);
    }

    @Override
    public boolean updateRemoteRuleEntity(String group, String serviceId, RuleEntity ruleEntity) throws Exception {
        String config = fromRuleEntity(ruleEntity);
        String configFormat = environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_CONFIG_FORMAT, String.class, DiscoveryConstant.XML_FORMAT);
        FormatType formatType = FormatType.fromString(configFormat);

        return updateRemoteConfig(group, serviceId, config, formatType);
    }

    @Override
    public boolean clearRemoteConfig(String group, String serviceId) throws Exception {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            throw new DiscoveryException("Remote config adapter isn't provided");
        }

        return configAdapter.clearConfig(group, serviceId);
    }

    @Override
    public String getRemoteConfig(String group, String serviceId) throws Exception {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            throw new DiscoveryException("Remote config adapter isn't provided");
        }

        return configAdapter.getConfig(group, serviceId);
    }

    @Override
    public RuleEntity getRemoteRuleEntity(String group, String serviceId) throws Exception {
        String config = getRemoteConfig(group, serviceId);

        return toRuleEntity(config);
    }

    @Override
    public List<ResultEntity> updateConfig(String serviceId, String config, boolean async) {
        ConfigUpdateRestInvoker configUpdateRestInvoker = new ConfigUpdateRestInvoker(serviceResource, serviceId, consoleRestTemplate, async, config);

        return configUpdateRestInvoker.invoke();
    }

    @Override
    public List<ResultEntity> updateRuleEntity(String serviceId, RuleEntity ruleEntity, boolean async) {
        String config = fromRuleEntity(ruleEntity);

        return updateConfig(serviceId, config, async);
    }

    @Override
    public List<ResultEntity> clearConfig(String serviceId, boolean async) {
        ConfigClearRestInvoker configClearRestInvoker = new ConfigClearRestInvoker(serviceResource, serviceId, consoleRestTemplate, async);

        return configClearRestInvoker.invoke();
    }

    @Override
    public List<ResultEntity> viewConfig(String serviceId) {
        ConfigViewRestInvoker configViewRestInvoker = new ConfigViewRestInvoker(serviceResource, serviceId, consoleRestTemplate);

        return configViewRestInvoker.invoke();
    }

    @Override
    public RuleEntity toRuleEntity(String config) {
        return StringUtils.isNotEmpty(config) ? parse(config) : new RuleEntity();
    }

    @Override
    public String fromRuleEntity(RuleEntity ruleEntity) {
        if (ruleEntity != null) {
            return deparse(ruleEntity);
        }

        String configFormat = environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_CONFIG_FORMAT, String.class, DiscoveryConstant.XML_FORMAT);
        ConfigFormatType configFormatType = ConfigFormatType.fromString(configFormat);
        switch (configFormatType) {
            case XML_FORMAT:
                return DiscoveryConstant.EMPTY_XML_RULE;
            case JSON_FORMAT:
                return DiscoveryConstant.EMPTY_JSON_RULE_SINGLE;
        }

        return StringUtils.EMPTY;
    }

    @Override
    public RuleEntity parse(String config) {
        return pluginConfigParser.parse(config);
    }

    @Override
    public String deparse(RuleEntity ruleEntity) {
        return pluginConfigDeparser.deparse(ruleEntity);
    }
}