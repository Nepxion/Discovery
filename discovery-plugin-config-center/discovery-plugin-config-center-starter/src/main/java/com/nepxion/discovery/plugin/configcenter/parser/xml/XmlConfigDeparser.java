package com.nepxion.discovery.plugin.configcenter.parser.xml;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.FilterHolderEntity;
import com.nepxion.discovery.common.entity.FilterType;
import com.nepxion.discovery.common.entity.HostFilterEntity;
import com.nepxion.discovery.common.entity.ParameterEntity;
import com.nepxion.discovery.common.entity.RegisterEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyBlacklistEntity;
import com.nepxion.discovery.common.entity.StrategyCustomizationEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.configcenter.constant.ConfigConstant;
import com.nepxion.discovery.plugin.framework.config.PluginConfigDeparser;

public class XmlConfigDeparser implements PluginConfigDeparser {
    private static final Logger LOG = LoggerFactory.getLogger(XmlConfigDeparser.class);

    private static final String INDENT = "    ";

    @Override
    public String deparse(RuleEntity ruleEntity) {
        if (ruleEntity == null) {
            throw new DiscoveryException("RuleEntity is null");
        }

        StringBuilder stringBuilder = new StringBuilder();

        deparseRoot(stringBuilder, ruleEntity);

        String rule = stringBuilder.toString();

        ruleEntity.setContent(rule);

        return rule;
    }

    private void deparseRoot(StringBuilder stringBuilder, RuleEntity ruleEntity) {
        LOG.info("Start to deparse RuleEntity to xml...");

        RegisterEntity registerEntity = ruleEntity.getRegisterEntity();
        DiscoveryEntity discoveryEntity = ruleEntity.getDiscoveryEntity();
        StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();
        StrategyCustomizationEntity strategyCustomizationEntity = ruleEntity.getStrategyCustomizationEntity();
        StrategyBlacklistEntity strategyBlacklistEntity = ruleEntity.getStrategyBlacklistEntity();
        ParameterEntity parameterEntity = ruleEntity.getParameterEntity();

        stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        stringBuilder.append("<" + ConfigConstant.RULE_ELEMENT_NAME + ">\n");

        deparseRegister(stringBuilder, registerEntity);
        deparseDiscovery(stringBuilder, discoveryEntity);
        deparseStrategy(stringBuilder, strategyEntity);
        deparseStrategyCustomization(stringBuilder, strategyCustomizationEntity);
        deparseStrategyBlacklist(stringBuilder, strategyBlacklistEntity);
        deparseParameter(stringBuilder, parameterEntity);

        stringBuilder.append("</" + ConfigConstant.RULE_ELEMENT_NAME + ">");
    }

    private void deparseRegister(StringBuilder stringBuilder, RegisterEntity registerEntity) {
        if (registerEntity == null) {
            return;
        }

        stringBuilder.append(INDENT + "<" + ConfigConstant.REGISTER_ELEMENT_NAME + ">\n");

        deparseHostFilter(stringBuilder, registerEntity);

        stringBuilder.append(INDENT + "</" + ConfigConstant.REGISTER_ELEMENT_NAME + ">\n");
    }

    private void deparseDiscovery(StringBuilder stringBuilder, DiscoveryEntity discoveryEntity) {

    }

    private void deparseStrategy(StringBuilder stringBuilder, StrategyEntity strategyEntity) {

    }

    private void deparseStrategyCustomization(StringBuilder stringBuilder, StrategyCustomizationEntity strategyCustomizationEntity) {

    }

    private void deparseStrategyBlacklist(StringBuilder stringBuilder, StrategyBlacklistEntity strategyBlacklistEntity) {

    }

    private void deparseParameter(StringBuilder stringBuilder, ParameterEntity parameterEntity) {

    }

    private void deparseHostFilter(StringBuilder stringBuilder, FilterHolderEntity filterHolderEntity) {
        if (filterHolderEntity == null) {
            return;
        }

        HostFilterEntity hostFilterEntity = filterHolderEntity.getHostFilterEntity();
        if (hostFilterEntity == null) {
            return;
        }

        FilterType filterType = hostFilterEntity.getFilterType();
        List<String> globalFilterValueList = hostFilterEntity.getFilterValueList();
        Map<String, List<String>> filterMap = hostFilterEntity.getFilterMap();

        if (CollectionUtils.isEmpty(globalFilterValueList)) {
            stringBuilder.append(INDENT + INDENT + "<" + filterType.toString() + ">\n");
        } else {
            stringBuilder.append(INDENT + INDENT + "<" + filterType.toString() + " " + ConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(globalFilterValueList) + "\">\n");
        }

        for (Map.Entry<String, List<String>> entry : filterMap.entrySet()) {
            String serviceName = entry.getKey();
            List<String> valueList = entry.getValue();
            if (CollectionUtils.isEmpty(valueList)) {
                stringBuilder.append(INDENT + INDENT + INDENT + "<" + ConfigConstant.SERVICE_ELEMENT_NAME + " " + ConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + serviceName + "\"/>\n");
            } else {
                stringBuilder.append(INDENT + INDENT + INDENT + "<" + ConfigConstant.SERVICE_ELEMENT_NAME + " " + ConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + serviceName + "\" " + ConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(valueList) + "\"/>\n");
            }
        }
        stringBuilder.append(INDENT + INDENT + "</" + filterType.toString() + ">\n");
    }
}