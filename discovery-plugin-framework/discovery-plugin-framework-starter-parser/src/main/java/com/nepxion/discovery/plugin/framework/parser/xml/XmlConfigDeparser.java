package com.nepxion.discovery.plugin.framework.parser.xml;

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

import com.nepxion.discovery.common.entity.CountFilterEntity;
import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.FilterHolderEntity;
import com.nepxion.discovery.common.entity.FilterType;
import com.nepxion.discovery.common.entity.HostFilterEntity;
import com.nepxion.discovery.common.entity.ParameterEntity;
import com.nepxion.discovery.common.entity.ParameterServiceEntity;
import com.nepxion.discovery.common.entity.RegionEntity;
import com.nepxion.discovery.common.entity.RegionFilterEntity;
import com.nepxion.discovery.common.entity.RegisterEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyBlacklistEntity;
import com.nepxion.discovery.common.entity.StrategyCustomizationEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.common.entity.VersionEntity;
import com.nepxion.discovery.common.entity.VersionFilterEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.framework.parser.PluginConfigDeparser;

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
        stringBuilder.append("<" + XmlConfigConstant.RULE_ELEMENT_NAME + ">\n");

        deparseRegister(stringBuilder, registerEntity);
        deparseDiscovery(stringBuilder, discoveryEntity);
        deparseStrategy(stringBuilder, strategyEntity);
        deparseStrategyCustomization(stringBuilder, strategyCustomizationEntity);
        deparseStrategyBlacklist(stringBuilder, strategyBlacklistEntity);
        deparseParameter(stringBuilder, parameterEntity);

        stringBuilder.append("</" + XmlConfigConstant.RULE_ELEMENT_NAME + ">");
    }

    private void deparseRegister(StringBuilder stringBuilder, RegisterEntity registerEntity) {
        if (registerEntity == null) {
            return;
        }

        stringBuilder.append(INDENT + "<" + XmlConfigConstant.REGISTER_ELEMENT_NAME + ">\n");

        deparseHostFilter(stringBuilder, registerEntity);
        deparseCountFilter(stringBuilder, registerEntity);

        stringBuilder.append(INDENT + "</" + XmlConfigConstant.REGISTER_ELEMENT_NAME + ">\n");
    }

    private void deparseDiscovery(StringBuilder stringBuilder, DiscoveryEntity discoveryEntity) {
        if (discoveryEntity == null) {
            return;
        }

        stringBuilder.append(INDENT + "<" + XmlConfigConstant.DISCOVERY_ELEMENT_NAME + ">\n");

        deparseHostFilter(stringBuilder, discoveryEntity);
        deparseVersionFilter(stringBuilder, discoveryEntity);
        deparseRegionFilter(stringBuilder, discoveryEntity);
        deparseWeightFilter(stringBuilder, discoveryEntity);

        stringBuilder.append(INDENT + "</" + XmlConfigConstant.DISCOVERY_ELEMENT_NAME + ">\n");
    }

    private void deparseStrategy(StringBuilder stringBuilder, StrategyEntity strategyEntity) {
        stringBuilder.append(INDENT + "<" + XmlConfigConstant.STRATEGY_ELEMENT_NAME + ">\n");

        String versionValue = strategyEntity.getVersionValue();
        String regionValue = strategyEntity.getRegionValue();
        String addressValue = strategyEntity.getAddressValue();
        String versionWeightValue = strategyEntity.getVersionWeightValue();
        String regionWeightValue = strategyEntity.getRegionWeightValue();

        if (versionValue != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.VERSION_ELEMENT_NAME + ">" + versionValue + "</" + XmlConfigConstant.VERSION_ELEMENT_NAME + ">\n");
        }
        if (regionValue != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.REGION_ELEMENT_NAME + ">" + regionValue + "</" + XmlConfigConstant.REGION_ELEMENT_NAME + ">\n");
        }
        if (addressValue != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.ADDRESS_ELEMENT_NAME + ">" + addressValue + "</" + XmlConfigConstant.ADDRESS_ELEMENT_NAME + ">\n");
        }
        if (versionWeightValue != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.VERSION_WEIGHT_ELEMENT_NAME + ">" + versionWeightValue + "</" + XmlConfigConstant.VERSION_WEIGHT_ELEMENT_NAME + ">\n");
        }
        if (regionWeightValue != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.REGION_WEIGHT_ELEMENT_NAME + ">" + regionWeightValue + "</" + XmlConfigConstant.REGION_WEIGHT_ELEMENT_NAME + ">\n");
        }

        stringBuilder.append(INDENT + "</" + XmlConfigConstant.STRATEGY_ELEMENT_NAME + ">\n");
    }

    private void deparseStrategyCustomization(StringBuilder stringBuilder, StrategyCustomizationEntity strategyCustomizationEntity) {

    }

    private void deparseStrategyBlacklist(StringBuilder stringBuilder, StrategyBlacklistEntity strategyBlacklistEntity) {
        stringBuilder.append(INDENT + "<" + XmlConfigConstant.STRATEGY_BLACKLIST_ELEMENT_NAME + ">\n");

        List<String> idList = strategyBlacklistEntity.getIdList();
        List<String> addressList = strategyBlacklistEntity.getAddressList();

        if (idList != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.ID_ELEMENT_NAME + " " + XmlConfigConstant.VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(idList) + "\"/>\n");
        }
        if (addressList != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.ADDRESS_ELEMENT_NAME + " " + XmlConfigConstant.VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(addressList) + "\"/>\n");
        }

        stringBuilder.append(INDENT + "</" + XmlConfigConstant.STRATEGY_BLACKLIST_ELEMENT_NAME + ">\n");
    }

    private void deparseParameter(StringBuilder stringBuilder, ParameterEntity parameterEntity) {
        stringBuilder.append(INDENT + "<" + XmlConfigConstant.PARAMETER_ELEMENT_NAME + ">\n");

        Map<String, List<ParameterServiceEntity>> parameterServiceMap = parameterEntity.getParameterServiceMap();
        for (Map.Entry<String, List<ParameterServiceEntity>> parameterServiceEntry : parameterServiceMap.entrySet()) {
            List<ParameterServiceEntity> parameterServiceEntityList = parameterServiceEntry.getValue();
            if (CollectionUtils.isNotEmpty(parameterServiceEntityList)) {
                for (ParameterServiceEntity parameterServiceEntity : parameterServiceEntityList) {
                    stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME);

                    Map<String, String> parameterMap = parameterServiceEntity.getParameterMap();
                    for (Map.Entry<String, String> parameterEntry : parameterMap.entrySet()) {
                        String key = parameterEntry.getKey();
                        String value = parameterEntry.getValue();

                        stringBuilder.append(" " + key + "=\"" + value + "\"");
                    }

                    stringBuilder.append("/>\n");
                }
            }
        }

        stringBuilder.append(INDENT + "</" + XmlConfigConstant.PARAMETER_ELEMENT_NAME + ">\n");
    }

    private void deparseHostFilter(StringBuilder stringBuilder, FilterHolderEntity filterHolderEntity) {
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
            stringBuilder.append(INDENT + INDENT + "<" + filterType.toString() + " " + XmlConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(globalFilterValueList) + "\">\n");
        }

        for (Map.Entry<String, List<String>> entry : filterMap.entrySet()) {
            String serviceName = entry.getKey();
            List<String> filterValueList = entry.getValue();
            if (CollectionUtils.isEmpty(filterValueList)) {
                stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + serviceName + "\"/>\n");
            } else {
                stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + serviceName + "\" " + XmlConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(filterValueList) + "\"/>\n");
            }
        }
        stringBuilder.append(INDENT + INDENT + "</" + filterType.toString() + ">\n");
    }

    private void deparseCountFilter(StringBuilder stringBuilder, RegisterEntity registerEntity) {
        CountFilterEntity countFilterEntity = registerEntity.getCountFilterEntity();
        if (countFilterEntity == null) {
            return;
        }

        Integer globalFilterValue = countFilterEntity.getFilterValue();
        Map<String, Integer> filterMap = countFilterEntity.getFilterMap();

        if (globalFilterValue == null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.COUNT_ELEMENT_NAME + ">\n");
        } else {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.COUNT_ELEMENT_NAME + " " + XmlConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "=\"" + globalFilterValue + "\">\n");
        }

        for (Map.Entry<String, Integer> entry : filterMap.entrySet()) {
            String serviceName = entry.getKey();
            Integer filterValue = entry.getValue();
            if (filterValue == null) {
                stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + serviceName + "\"/>\n");
            } else {
                stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + serviceName + "\" " + XmlConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "=\"" + filterValue + "\"/>\n");
            }
        }
        stringBuilder.append(INDENT + INDENT + "</" + XmlConfigConstant.COUNT_ELEMENT_NAME + ">\n");
    }

    private void deparseVersionFilter(StringBuilder stringBuilder, DiscoveryEntity discoveryEntity) {
        VersionFilterEntity versionFilterEntity = discoveryEntity.getVersionFilterEntity();
        if (versionFilterEntity == null) {
            return;
        }

        stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.VERSION_ELEMENT_NAME + ">\n");

        Map<String, List<VersionEntity>> versionEntityMap = versionFilterEntity.getVersionEntityMap();
        for (Map.Entry<String, List<VersionEntity>> entry : versionEntityMap.entrySet()) {
            List<VersionEntity> versionEntityList = entry.getValue();
            for (VersionEntity versionEntity : versionEntityList) {
                String consumerServiceName = versionEntity.getConsumerServiceName();
                String providerServiceName = versionEntity.getProviderServiceName();
                List<String> consumerVersionValueList = versionEntity.getConsumerVersionValueList();
                List<String> providerVersionValueList = versionEntity.getProviderVersionValueList();

                stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + consumerServiceName + "\" " + XmlConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + providerServiceName + "\"");
                if (consumerVersionValueList != null) {
                    stringBuilder.append(" " + XmlConfigConstant.CONSUMER_VERSION_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(consumerVersionValueList) + "\"");
                }
                if (providerVersionValueList != null) {
                    stringBuilder.append(" " + XmlConfigConstant.PROVIDER_VERSION_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(providerVersionValueList) + "\"");
                }
                stringBuilder.append("/>\n");
            }
        }

        stringBuilder.append(INDENT + INDENT + "</" + XmlConfigConstant.VERSION_ELEMENT_NAME + ">\n");
    }

    private void deparseRegionFilter(StringBuilder stringBuilder, DiscoveryEntity discoveryEntity) {
        RegionFilterEntity regionFilterEntity = discoveryEntity.getRegionFilterEntity();
        if (regionFilterEntity == null) {
            return;
        }

        stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.REGION_ELEMENT_NAME + ">\n");

        Map<String, List<RegionEntity>> regionEntityMap = regionFilterEntity.getRegionEntityMap();
        for (Map.Entry<String, List<RegionEntity>> entry : regionEntityMap.entrySet()) {
            List<RegionEntity> regionEntityList = entry.getValue();
            for (RegionEntity regionEntity : regionEntityList) {
                String consumerServiceName = regionEntity.getConsumerServiceName();
                String providerServiceName = regionEntity.getProviderServiceName();
                List<String> consumerVersionValueList = regionEntity.getConsumerRegionValueList();
                List<String> providerVersionValueList = regionEntity.getProviderRegionValueList();

                stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + consumerServiceName + "\" " + XmlConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + providerServiceName + "\"");
                if (consumerVersionValueList != null) {
                    stringBuilder.append(" " + XmlConfigConstant.CONSUMER_REGION_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(consumerVersionValueList) + "\"");
                }
                if (providerVersionValueList != null) {
                    stringBuilder.append(" " + XmlConfigConstant.PROVIDER_REGION_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(providerVersionValueList) + "\"");
                }
                stringBuilder.append("/>\n");
            }
        }

        stringBuilder.append(INDENT + INDENT + "</" + XmlConfigConstant.REGION_ELEMENT_NAME + ">\n");
    }

    private void deparseWeightFilter(StringBuilder stringBuilder, DiscoveryEntity discoveryEntity) {
        RegionFilterEntity regionFilterEntity = discoveryEntity.getRegionFilterEntity();
        if (regionFilterEntity == null) {
            return;
        }

        stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.WEIGHT_ELEMENT_NAME + ">\n");

        stringBuilder.append(INDENT + INDENT + "</" + XmlConfigConstant.WEIGHT_ELEMENT_NAME + ">\n");

    }

    private void deparseStrategyConditionBlueGreen(StringBuilder stringBuilder, StrategyCustomizationEntity strategyCustomizationEntity) {

    }

    private void deparseStrategyConditionGray(StringBuilder stringBuilder, StrategyCustomizationEntity strategyCustomizationEntity) {

    }

    private void deparseStrategyRoute(StringBuilder stringBuilder, StrategyCustomizationEntity strategyCustomizationEntity) {

    }

    private void deparseStrategyHeader(StringBuilder stringBuilder, StrategyCustomizationEntity strategyCustomizationEntity) {

    }
}