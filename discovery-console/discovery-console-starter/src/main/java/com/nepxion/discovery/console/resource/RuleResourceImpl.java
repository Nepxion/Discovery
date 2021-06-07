package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.ParameterEntity;
import com.nepxion.discovery.common.entity.RegisterEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyBlacklistEntity;
import com.nepxion.discovery.common.entity.StrategyCustomizationEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.plugin.framework.parser.PluginConfigDeparser;
import com.nepxion.discovery.plugin.framework.parser.PluginConfigParser;

public class RuleResourceImpl implements RuleResource {
    @Autowired
    private ServiceResource serviceResource;

    @Autowired
    private ConfigResource configResource;

    @Autowired
    private PluginConfigParser pluginConfigParser;

    @Autowired
    private PluginConfigDeparser pluginConfigDeparser;

    @Override
    public void updateRemoteRuleEntity(String serviceId, RuleEntity ruleEntity) throws Exception {
        String group = serviceResource.getGroup(serviceId);

        updateRemoteRuleEntity(group, serviceId, ruleEntity);
    }

    @Override
    public void updateRemoteRuleEntity(String group, String serviceId, RuleEntity ruleEntity) throws Exception {
        // 先从远程配置中心拉一遍，确保其它已有规则不会被当前规则覆盖掉
        RuleEntity remoteRuleEntity = getRemoteRuleEntity(group, serviceId);

        RegisterEntity registerEntity = ruleEntity.getRegisterEntity();
        if (registerEntity != null) {
            remoteRuleEntity.setRegisterEntity(registerEntity);
        }

        DiscoveryEntity discoveryEntity = ruleEntity.getDiscoveryEntity();
        if (discoveryEntity != null) {
            remoteRuleEntity.setDiscoveryEntity(discoveryEntity);
        }

        StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();
        if (strategyEntity != null) {
            remoteRuleEntity.setStrategyEntity(strategyEntity);
        }

        StrategyCustomizationEntity strategyCustomizationEntity = ruleEntity.getStrategyCustomizationEntity();
        if (strategyCustomizationEntity != null) {
            remoteRuleEntity.setStrategyCustomizationEntity(strategyCustomizationEntity);
        }

        StrategyBlacklistEntity strategyBlacklistEntity = ruleEntity.getStrategyBlacklistEntity();
        if (strategyBlacklistEntity != null) {
            remoteRuleEntity.setStrategyBlacklistEntity(strategyBlacklistEntity);
        }

        ParameterEntity parameterEntity = ruleEntity.getParameterEntity();
        if (parameterEntity != null) {
            remoteRuleEntity.setParameterEntity(parameterEntity);
        }

        String config = fromRuleEntity(remoteRuleEntity);

        configResource.updateRemoteConfig(group, serviceId, config);
    }

    @Override
    public void clearRemoteRuleEntity(String serviceId) throws Exception {
        String group = serviceResource.getGroup(serviceId);

        clearRemoteRuleEntity(group, serviceId);
    }

    @Override
    public void clearRemoteRuleEntity(String group, String serviceId) throws Exception {
        configResource.clearRemoteConfig(group, serviceId);
    }

    @Override
    public RuleEntity getRemoteRuleEntity(String serviceId) throws Exception {
        String group = serviceResource.getGroup(serviceId);

        return getRemoteRuleEntity(group, serviceId);
    }

    @Override
    public RuleEntity getRemoteRuleEntity(String group, String serviceId) throws Exception {
        String config = configResource.getRemoteConfig(group, serviceId);

        return toRuleEntity(config);
    }

    @Override
    public RuleEntity toRuleEntity(String config) {
        return StringUtils.isNotEmpty(config) ? parse(config) : new RuleEntity();
    }

    @Override
    public String fromRuleEntity(RuleEntity ruleEntity) {
        return ruleEntity != null ? deparse(ruleEntity) : DiscoveryConstant.EMPTY_XML_RULE;
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