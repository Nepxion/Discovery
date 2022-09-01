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
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.AddressWeightEntity;
import com.nepxion.discovery.common.entity.ConditionType;
import com.nepxion.discovery.common.entity.CountFilterEntity;
import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.EscapeType;
import com.nepxion.discovery.common.entity.FilterType;
import com.nepxion.discovery.common.entity.HostFilterEntity;
import com.nepxion.discovery.common.entity.ParameterEntity;
import com.nepxion.discovery.common.entity.ParameterServiceEntity;
import com.nepxion.discovery.common.entity.RegionEntity;
import com.nepxion.discovery.common.entity.RegionFilterEntity;
import com.nepxion.discovery.common.entity.RegionWeightEntity;
import com.nepxion.discovery.common.entity.RegisterEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyBlacklistEntity;
import com.nepxion.discovery.common.entity.StrategyConditionBlueGreenEntity;
import com.nepxion.discovery.common.entity.StrategyConditionGrayEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.common.entity.StrategyFailoverEntity;
import com.nepxion.discovery.common.entity.StrategyHeaderEntity;
import com.nepxion.discovery.common.entity.StrategyReleaseEntity;
import com.nepxion.discovery.common.entity.StrategyRouteEntity;
import com.nepxion.discovery.common.entity.StrategyRouteType;
import com.nepxion.discovery.common.entity.VersionEntity;
import com.nepxion.discovery.common.entity.VersionFilterEntity;
import com.nepxion.discovery.common.entity.VersionWeightEntity;
import com.nepxion.discovery.common.entity.WeightEntity;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.common.entity.WeightType;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.JsonUtil;
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

        String config = stringBuilder.toString();
        ruleEntity.setContent(config);

        LOG.info("Rule content=\n{}", config);

        return config;
    }

    private void deparseRoot(StringBuilder stringBuilder, RuleEntity ruleEntity) {
        LOG.info("Start to deparse RuleEntity to xml...");

        RegisterEntity registerEntity = ruleEntity.getRegisterEntity();
        DiscoveryEntity discoveryEntity = ruleEntity.getDiscoveryEntity();
        StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();
        StrategyReleaseEntity strategyReleaseEntity = ruleEntity.getStrategyReleaseEntity();
        StrategyFailoverEntity strategyFailoverEntity = ruleEntity.getStrategyFailoverEntity();
        StrategyBlacklistEntity strategyBlacklistEntity = ruleEntity.getStrategyBlacklistEntity();
        ParameterEntity parameterEntity = ruleEntity.getParameterEntity();

        stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        stringBuilder.append("<" + XmlConfigConstant.RULE_ELEMENT_NAME + ">\n");
        if (registerEntity != null) {
            deparseRegister(stringBuilder, registerEntity);
        }
        if (discoveryEntity != null) {
            deparseDiscovery(stringBuilder, discoveryEntity);
        }
        if (strategyEntity != null) {
            deparseStrategy(stringBuilder, strategyEntity);
        }
        if (strategyReleaseEntity != null) {
            deparseStrategyRelease(stringBuilder, strategyReleaseEntity);
        }
        if (strategyFailoverEntity != null) {
            deparseStrategyFailover(stringBuilder, strategyFailoverEntity);
        }
        if (strategyBlacklistEntity != null) {
            deparseStrategyBlacklist(stringBuilder, strategyBlacklistEntity);
        }
        if (parameterEntity != null) {
            deparseParameter(stringBuilder, parameterEntity);
        }
        stringBuilder.append("</" + XmlConfigConstant.RULE_ELEMENT_NAME + ">");
    }

    private void deparseRegister(StringBuilder stringBuilder, RegisterEntity registerEntity) {
        HostFilterEntity hostFilterEntity = registerEntity.getHostFilterEntity();
        CountFilterEntity countFilterEntity = registerEntity.getCountFilterEntity();

        if (hostFilterEntity != null || countFilterEntity != null) {
            stringBuilder.append(INDENT + "<" + XmlConfigConstant.REGISTER_ELEMENT_NAME + ">\n");
        }
        if (hostFilterEntity != null) {
            deparseHostFilter(stringBuilder, hostFilterEntity);
        }
        if (countFilterEntity != null) {
            deparseCountFilter(stringBuilder, countFilterEntity);
        }
        if (hostFilterEntity != null || countFilterEntity != null) {
            stringBuilder.append(INDENT + "</" + XmlConfigConstant.REGISTER_ELEMENT_NAME + ">\n");
        }
    }

    private void deparseDiscovery(StringBuilder stringBuilder, DiscoveryEntity discoveryEntity) {
        HostFilterEntity hostFilterEntity = discoveryEntity.getHostFilterEntity();
        VersionFilterEntity versionFilterEntity = discoveryEntity.getVersionFilterEntity();
        RegionFilterEntity regionFilterEntity = discoveryEntity.getRegionFilterEntity();
        WeightFilterEntity weightFilterEntity = discoveryEntity.getWeightFilterEntity();

        if (hostFilterEntity != null || versionFilterEntity != null || regionFilterEntity != null || weightFilterEntity != null) {
            stringBuilder.append(INDENT + "<" + XmlConfigConstant.DISCOVERY_ELEMENT_NAME + ">\n");
        }
        if (hostFilterEntity != null) {
            deparseHostFilter(stringBuilder, hostFilterEntity);
        }
        if (versionFilterEntity != null) {
            deparseVersionFilter(stringBuilder, versionFilterEntity);
        }
        if (regionFilterEntity != null) {
            deparseRegionFilter(stringBuilder, regionFilterEntity);
        }
        if (weightFilterEntity != null) {
            deparseWeightFilter(stringBuilder, weightFilterEntity);
        }
        if (hostFilterEntity != null || versionFilterEntity != null || regionFilterEntity != null || weightFilterEntity != null) {
            stringBuilder.append(INDENT + "</" + XmlConfigConstant.DISCOVERY_ELEMENT_NAME + ">\n");
        }
    }

    private void deparseStrategy(StringBuilder stringBuilder, StrategyEntity strategyEntity) {
        String versionValue = strategyEntity.getVersionValue();
        String regionValue = strategyEntity.getRegionValue();
        String addressValue = strategyEntity.getAddressValue();
        String versionWeightValue = strategyEntity.getVersionWeightValue();
        String regionWeightValue = strategyEntity.getRegionWeightValue();

        if (versionValue != null || regionValue != null || addressValue != null || versionWeightValue != null || regionWeightValue != null) {
            stringBuilder.append(INDENT + "<" + XmlConfigConstant.STRATEGY_ELEMENT_NAME + ">\n");
        }
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
        if (versionValue != null || regionValue != null || addressValue != null || versionWeightValue != null || regionWeightValue != null) {
            stringBuilder.append(INDENT + "</" + XmlConfigConstant.STRATEGY_ELEMENT_NAME + ">\n");
        }
    }

    private void deparseStrategyRelease(StringBuilder stringBuilder, StrategyReleaseEntity strategyReleaseEntity) {
        List<StrategyConditionBlueGreenEntity> strategyConditionBlueGreenEntityList = strategyReleaseEntity.getStrategyConditionBlueGreenEntityList();
        List<StrategyConditionGrayEntity> strategyConditionGrayEntityList = strategyReleaseEntity.getStrategyConditionGrayEntityList();
        List<StrategyRouteEntity> strategyRouteEntityList = strategyReleaseEntity.getStrategyRouteEntityList();
        StrategyHeaderEntity strategyHeaderEntity = strategyReleaseEntity.getStrategyHeaderEntity();

        if (CollectionUtils.isNotEmpty(strategyConditionBlueGreenEntityList) || CollectionUtils.isNotEmpty(strategyConditionGrayEntityList) || CollectionUtils.isNotEmpty(strategyRouteEntityList) || strategyHeaderEntity != null) {
            stringBuilder.append(INDENT + "<" + XmlConfigConstant.STRATEGY_RELEASE_ELEMENT_NAME + ">\n");
        }
        if (CollectionUtils.isNotEmpty(strategyConditionBlueGreenEntityList)) {
            deparseStrategyConditionBlueGreen(stringBuilder, strategyConditionBlueGreenEntityList);
        }
        if (CollectionUtils.isNotEmpty(strategyConditionGrayEntityList)) {
            deparseStrategyConditionGray(stringBuilder, strategyConditionGrayEntityList);
        }
        if (CollectionUtils.isNotEmpty(strategyRouteEntityList)) {
            deparseStrategyRoute(stringBuilder, strategyRouteEntityList);
        }
        if (strategyHeaderEntity != null) {
            deparseStrategyHeader(stringBuilder, strategyHeaderEntity);
        }
        if (CollectionUtils.isNotEmpty(strategyConditionBlueGreenEntityList) || CollectionUtils.isNotEmpty(strategyConditionGrayEntityList) || CollectionUtils.isNotEmpty(strategyRouteEntityList) || strategyHeaderEntity != null) {
            stringBuilder.append(INDENT + "</" + XmlConfigConstant.STRATEGY_RELEASE_ELEMENT_NAME + ">\n");
        }
    }

    private void deparseStrategyFailover(StringBuilder stringBuilder, StrategyFailoverEntity strategyFailoverEntity) {
        String versionPreferValue = strategyFailoverEntity.getVersionPreferValue();
        String versionFailoverValue = strategyFailoverEntity.getVersionFailoverValue();
        String regionTransferValue = strategyFailoverEntity.getRegionTransferValue();
        String regionFailoverValue = strategyFailoverEntity.getRegionFailoverValue();
        String environmentFailoverValue = strategyFailoverEntity.getEnvironmentFailoverValue();
        String zoneFailoverValue = strategyFailoverEntity.getZoneFailoverValue();
        String addressFailoverValue = strategyFailoverEntity.getAddressFailoverValue();
        if (versionPreferValue != null || versionFailoverValue != null || regionTransferValue != null || regionFailoverValue != null || environmentFailoverValue != null || zoneFailoverValue != null || addressFailoverValue != null) {
            stringBuilder.append(INDENT + "<" + XmlConfigConstant.STRATEGY_FAILOVER_ELEMENT_NAME + ">\n");
        }
        if (versionPreferValue != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.VERSION_PREFER_ELEMENT_NAME + ">" + versionPreferValue + "</" + XmlConfigConstant.VERSION_PREFER_ELEMENT_NAME + ">\n");
        }
        if (versionFailoverValue != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.VERSION_FAILOVER_ELEMENT_NAME + ">" + versionFailoverValue + "</" + XmlConfigConstant.VERSION_FAILOVER_ELEMENT_NAME + ">\n");
        }
        if (regionTransferValue != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.REGION_TRANSFER_ELEMENT_NAME + ">" + regionTransferValue + "</" + XmlConfigConstant.REGION_TRANSFER_ELEMENT_NAME + ">\n");
        }
        if (regionFailoverValue != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.REGION_FAILOVER_ELEMENT_NAME + ">" + regionFailoverValue + "</" + XmlConfigConstant.REGION_FAILOVER_ELEMENT_NAME + ">\n");
        }
        if (environmentFailoverValue != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.ENVIRONMENT_FAILOVER_ELEMENT_NAME + ">" + environmentFailoverValue + "</" + XmlConfigConstant.ENVIRONMENT_FAILOVER_ELEMENT_NAME + ">\n");
        }
        if (zoneFailoverValue != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.ZONE_FAILOVER_ELEMENT_NAME + ">" + zoneFailoverValue + "</" + XmlConfigConstant.ZONE_FAILOVER_ELEMENT_NAME + ">\n");
        }
        if (addressFailoverValue != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.ADDRESS_FAILOVER_ELEMENT_NAME + ">" + addressFailoverValue + "</" + XmlConfigConstant.ADDRESS_FAILOVER_ELEMENT_NAME + ">\n");
        }
        if (versionPreferValue != null || versionFailoverValue != null || regionTransferValue != null || regionFailoverValue != null || environmentFailoverValue != null || zoneFailoverValue != null || addressFailoverValue != null) {
            stringBuilder.append(INDENT + "</" + XmlConfigConstant.STRATEGY_FAILOVER_ELEMENT_NAME + ">\n");
        }
    }

    private void deparseStrategyBlacklist(StringBuilder stringBuilder, StrategyBlacklistEntity strategyBlacklistEntity) {
        String idValue = strategyBlacklistEntity.getIdValue();
        String addressValue = strategyBlacklistEntity.getAddressValue();
        if (idValue != null || addressValue != null) {
            stringBuilder.append(INDENT + "<" + XmlConfigConstant.STRATEGY_BLACKLIST_ELEMENT_NAME + ">\n");
        }
        if (idValue != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.ID_ELEMENT_NAME + ">" + idValue + "</" + XmlConfigConstant.ID_ELEMENT_NAME + ">\n");
        }
        if (addressValue != null) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.ADDRESS_ELEMENT_NAME + ">" + addressValue + "</" + XmlConfigConstant.ADDRESS_ELEMENT_NAME + ">\n");
        }
        if (idValue != null || addressValue != null) {
            stringBuilder.append(INDENT + "</" + XmlConfigConstant.STRATEGY_BLACKLIST_ELEMENT_NAME + ">\n");
        }
    }

    private void deparseParameter(StringBuilder stringBuilder, ParameterEntity parameterEntity) {
        Map<String, List<ParameterServiceEntity>> parameterServiceMap = parameterEntity.getParameterServiceMap();
        if (MapUtils.isNotEmpty(parameterServiceMap)) {
            stringBuilder.append(INDENT + "<" + XmlConfigConstant.PARAMETER_ELEMENT_NAME + ">\n");
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
    }

    private void deparseHostFilter(StringBuilder stringBuilder, HostFilterEntity hostFilterEntity) {
        FilterType filterType = hostFilterEntity.getFilterType();
        List<String> globalFilterValueList = hostFilterEntity.getFilterValueList();
        Map<String, List<String>> filterMap = hostFilterEntity.getFilterMap();

        if (CollectionUtils.isNotEmpty(globalFilterValueList) || MapUtils.isNotEmpty(filterMap)) {
            if (CollectionUtils.isEmpty(globalFilterValueList)) {
                stringBuilder.append(INDENT + INDENT + "<" + filterType.toString() + ">\n");
            } else {
                stringBuilder.append(INDENT + INDENT + "<" + filterType.toString() + " " + XmlConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(globalFilterValueList) + "\">\n");
            }
        }
        if (MapUtils.isNotEmpty(filterMap)) {
            for (Map.Entry<String, List<String>> entry : filterMap.entrySet()) {
                String serviceName = entry.getKey();
                List<String> filterValueList = entry.getValue();
                if (CollectionUtils.isEmpty(filterValueList)) {
                    stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + serviceName + "\"/>\n");
                } else {
                    stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + serviceName + "\" " + XmlConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(filterValueList) + "\"/>\n");
                }
            }
        }
        if (CollectionUtils.isNotEmpty(globalFilterValueList) || MapUtils.isNotEmpty(filterMap)) {
            stringBuilder.append(INDENT + INDENT + "</" + filterType.toString() + ">\n");
        }
    }

    private void deparseCountFilter(StringBuilder stringBuilder, CountFilterEntity countFilterEntity) {
        Integer globalFilterValue = countFilterEntity.getFilterValue();
        Map<String, Integer> filterMap = countFilterEntity.getFilterMap();

        if (globalFilterValue != null || MapUtils.isNotEmpty(filterMap)) {
            if (globalFilterValue == null) {
                stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.COUNT_ELEMENT_NAME + ">\n");
            } else {
                stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.COUNT_ELEMENT_NAME + " " + XmlConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "=\"" + globalFilterValue + "\">\n");
            }
        }
        if (MapUtils.isNotEmpty(filterMap)) {
            for (Map.Entry<String, Integer> entry : filterMap.entrySet()) {
                String serviceName = entry.getKey();
                Integer filterValue = entry.getValue();
                if (filterValue == null) {
                    stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + serviceName + "\"/>\n");
                } else {
                    stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + serviceName + "\" " + XmlConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "=\"" + filterValue + "\"/>\n");
                }
            }
        }
        if (globalFilterValue != null || MapUtils.isNotEmpty(filterMap)) {
            stringBuilder.append(INDENT + INDENT + "</" + XmlConfigConstant.COUNT_ELEMENT_NAME + ">\n");
        }
    }

    private void deparseVersionFilter(StringBuilder stringBuilder, VersionFilterEntity versionFilterEntity) {
        Map<String, List<VersionEntity>> versionEntityMap = versionFilterEntity.getVersionEntityMap();
        if (MapUtils.isNotEmpty(versionEntityMap)) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.VERSION_ELEMENT_NAME + ">\n");
            for (Map.Entry<String, List<VersionEntity>> entry : versionEntityMap.entrySet()) {
                List<VersionEntity> versionEntityList = entry.getValue();
                for (VersionEntity versionEntity : versionEntityList) {
                    String consumerServiceName = versionEntity.getConsumerServiceName();
                    String providerServiceName = versionEntity.getProviderServiceName();
                    List<String> consumerVersionValueList = versionEntity.getConsumerVersionValueList();
                    List<String> providerVersionValueList = versionEntity.getProviderVersionValueList();

                    stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + consumerServiceName + "\" " + XmlConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + providerServiceName + "\"");
                    if (CollectionUtils.isNotEmpty(consumerVersionValueList)) {
                        stringBuilder.append(" " + XmlConfigConstant.CONSUMER_VERSION_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(consumerVersionValueList) + "\"");
                    }
                    if (CollectionUtils.isNotEmpty(providerVersionValueList)) {
                        stringBuilder.append(" " + XmlConfigConstant.PROVIDER_VERSION_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(providerVersionValueList) + "\"");
                    }
                    stringBuilder.append("/>\n");
                }
            }
            stringBuilder.append(INDENT + INDENT + "</" + XmlConfigConstant.VERSION_ELEMENT_NAME + ">\n");
        }
    }

    private void deparseRegionFilter(StringBuilder stringBuilder, RegionFilterEntity regionFilterEntity) {
        Map<String, List<RegionEntity>> regionEntityMap = regionFilterEntity.getRegionEntityMap();
        if (MapUtils.isNotEmpty(regionEntityMap)) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.REGION_ELEMENT_NAME + ">\n");
            for (Map.Entry<String, List<RegionEntity>> entry : regionEntityMap.entrySet()) {
                List<RegionEntity> regionEntityList = entry.getValue();
                for (RegionEntity regionEntity : regionEntityList) {
                    String consumerServiceName = regionEntity.getConsumerServiceName();
                    String providerServiceName = regionEntity.getProviderServiceName();
                    List<String> consumerVersionValueList = regionEntity.getConsumerRegionValueList();
                    List<String> providerVersionValueList = regionEntity.getProviderRegionValueList();

                    stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + consumerServiceName + "\" " + XmlConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + providerServiceName + "\"");
                    if (CollectionUtils.isNotEmpty(consumerVersionValueList)) {
                        stringBuilder.append(" " + XmlConfigConstant.CONSUMER_REGION_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(consumerVersionValueList) + "\"");
                    }
                    if (CollectionUtils.isNotEmpty(providerVersionValueList)) {
                        stringBuilder.append(" " + XmlConfigConstant.PROVIDER_REGION_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(providerVersionValueList) + "\"");
                    }
                    stringBuilder.append("/>\n");
                }
            }
            stringBuilder.append(INDENT + INDENT + "</" + XmlConfigConstant.REGION_ELEMENT_NAME + ">\n");
        }
    }

    private void deparseWeightFilter(StringBuilder stringBuilder, WeightFilterEntity weightFilterEntity) {
        Map<String, List<WeightEntity>> versionWeightEntityMap = weightFilterEntity.getVersionWeightEntityMap();
        List<WeightEntity> versionWeightEntityList = weightFilterEntity.getVersionWeightEntityList();
        VersionWeightEntity versionWeightEntity = weightFilterEntity.getVersionWeightEntity();
        Map<String, Integer> versionWeightMap = versionWeightEntity.getWeightMap();

        Map<String, List<WeightEntity>> regionWeightEntityMap = weightFilterEntity.getRegionWeightEntityMap();
        List<WeightEntity> regionWeightEntityList = weightFilterEntity.getRegionWeightEntityList();
        RegionWeightEntity regionWeightEntity = weightFilterEntity.getRegionWeightEntity();
        Map<String, Integer> regionWeightMap = regionWeightEntity.getWeightMap();

        stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.WEIGHT_ELEMENT_NAME + ">\n");
        if (MapUtils.isNotEmpty(versionWeightEntityMap)) {
            for (Map.Entry<String, List<WeightEntity>> entry : versionWeightEntityMap.entrySet()) {
                List<WeightEntity> weightEntityList = entry.getValue();

                if (CollectionUtils.isNotEmpty(weightEntityList)) {
                    for (WeightEntity weightEntity : weightEntityList) {
                        String consumerServiceName = weightEntity.getConsumerServiceName();
                        String providerServiceName = weightEntity.getProviderServiceName();
                        Map<String, Integer> weightMap = weightEntity.getWeightMap();
                        WeightType type = weightEntity.getType();

                        stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + consumerServiceName + "\" " + XmlConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + providerServiceName + "\" " + XmlConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(weightMap) + "\" " + XmlConfigConstant.TYPE_ATTRIBUTE_NAME + "=\"" + type + "\"/>\n");
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(versionWeightEntityList)) {
            for (WeightEntity weightEntity : versionWeightEntityList) {
                String providerServiceName = weightEntity.getProviderServiceName();
                Map<String, Integer> weightMap = weightEntity.getWeightMap();
                WeightType type = weightEntity.getType();

                stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + providerServiceName + "\" " + XmlConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(weightMap) + "\" " + XmlConfigConstant.TYPE_ATTRIBUTE_NAME + "=\"" + type + "\"/>\n");
            }
        }
        if (MapUtils.isNotEmpty(versionWeightMap)) {
            stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.VERSION_ELEMENT_NAME + " " + XmlConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(versionWeightMap) + "\"/>\n");
        }
        if (MapUtils.isNotEmpty(regionWeightEntityMap)) {
            for (Map.Entry<String, List<WeightEntity>> entry : regionWeightEntityMap.entrySet()) {
                List<WeightEntity> weightEntityList = entry.getValue();

                if (CollectionUtils.isNotEmpty(weightEntityList)) {
                    for (WeightEntity weightEntity : weightEntityList) {
                        String consumerServiceName = weightEntity.getConsumerServiceName();
                        String providerServiceName = weightEntity.getProviderServiceName();
                        Map<String, Integer> weightMap = weightEntity.getWeightMap();
                        WeightType type = weightEntity.getType();

                        stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + consumerServiceName + "\" " + XmlConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + providerServiceName + "\" " + XmlConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(weightMap) + "\" " + XmlConfigConstant.TYPE_ATTRIBUTE_NAME + "=\"" + type + "\"/>\n");
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(regionWeightEntityList)) {
            for (WeightEntity weightEntity : regionWeightEntityList) {
                String providerServiceName = weightEntity.getProviderServiceName();
                Map<String, Integer> weightMap = weightEntity.getWeightMap();
                WeightType type = weightEntity.getType();

                stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.SERVICE_ELEMENT_NAME + " " + XmlConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "=\"" + providerServiceName + "\" " + XmlConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(weightMap) + "\" " + XmlConfigConstant.TYPE_ATTRIBUTE_NAME + "=\"" + type + "\"/>\n");
            }
        }
        if (MapUtils.isNotEmpty(regionWeightMap)) {
            stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.REGION_ELEMENT_NAME + " " + XmlConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(regionWeightMap) + "\"/>\n");
        }
        stringBuilder.append(INDENT + INDENT + "</" + XmlConfigConstant.WEIGHT_ELEMENT_NAME + ">\n");
    }

    private void deparseStrategyConditionBlueGreen(StringBuilder stringBuilder, List<StrategyConditionBlueGreenEntity> strategyConditionBlueGreenEntityList) {
        if (CollectionUtils.isNotEmpty(strategyConditionBlueGreenEntityList)) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.CONDITIONS_ELEMENT_NAME + " " + XmlConfigConstant.TYPE_ATTRIBUTE_NAME + "=\"" + ConditionType.BLUE_GREEN.toString() + "\">\n");
            for (StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity : strategyConditionBlueGreenEntityList) {
                String id = strategyConditionBlueGreenEntity.getId();
                String expression = EscapeType.escape(strategyConditionBlueGreenEntity.getExpression(), true);
                String versionId = strategyConditionBlueGreenEntity.getVersionId();
                String regionId = strategyConditionBlueGreenEntity.getRegionId();
                String addressId = strategyConditionBlueGreenEntity.getAddressId();
                String versionWeightId = strategyConditionBlueGreenEntity.getVersionWeightId();
                String regionWeightId = strategyConditionBlueGreenEntity.getRegionWeightId();

                if (StringUtils.isNotEmpty(expression)) {
                    stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.CONDITION_ELEMENT_NAME + " " + XmlConfigConstant.ID_ATTRIBUTE_NAME + "=\"" + id + "\" " + XmlConfigConstant.EXPRESSION_ATTRIBUTE_NAME + "=\"" + expression + "\"");
                } else {
                    stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.CONDITION_ELEMENT_NAME + " " + XmlConfigConstant.ID_ATTRIBUTE_NAME + "=\"" + id + "\"");
                }
                if (versionId != null) {
                    stringBuilder.append(" " + XmlConfigConstant.VERSION_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME + "=\"" + versionId + "\"");
                }
                if (regionId != null) {
                    stringBuilder.append(" " + XmlConfigConstant.REGION_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME + "=\"" + regionId + "\"");
                }
                if (addressId != null) {
                    stringBuilder.append(" " + XmlConfigConstant.ADDRESS_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME + "=\"" + addressId + "\"");
                }
                if (versionWeightId != null) {
                    stringBuilder.append(" " + XmlConfigConstant.VERSION_WEIGHT_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME + "=\"" + versionWeightId + "\"");
                }
                if (regionWeightId != null) {
                    stringBuilder.append(" " + XmlConfigConstant.REGION_WEIGHT_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME + "=\"" + regionWeightId + "\"");
                }
                stringBuilder.append("/>\n");
            }
            stringBuilder.append(INDENT + INDENT + "</" + XmlConfigConstant.CONDITIONS_ELEMENT_NAME + ">\n");
        }
    }

    private void deparseStrategyConditionGray(StringBuilder stringBuilder, List<StrategyConditionGrayEntity> strategyConditionGrayEntityList) {
        if (CollectionUtils.isNotEmpty(strategyConditionGrayEntityList)) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.CONDITIONS_ELEMENT_NAME + " " + XmlConfigConstant.TYPE_ATTRIBUTE_NAME + "=\"" + ConditionType.GRAY.toString() + "\">\n");
            for (StrategyConditionGrayEntity strategyConditionGrayEntity : strategyConditionGrayEntityList) {
                String id = strategyConditionGrayEntity.getId();
                String expression = EscapeType.escape(strategyConditionGrayEntity.getExpression(), true);
                VersionWeightEntity versionWeightEntity = strategyConditionGrayEntity.getVersionWeightEntity();
                RegionWeightEntity regionWeightEntity = strategyConditionGrayEntity.getRegionWeightEntity();
                AddressWeightEntity addressWeightEntity = strategyConditionGrayEntity.getAddressWeightEntity();

                if (StringUtils.isNotEmpty(expression)) {
                    stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.CONDITION_ELEMENT_NAME + " " + XmlConfigConstant.ID_ATTRIBUTE_NAME + "=\"" + id + "\" " + XmlConfigConstant.EXPRESSION_ATTRIBUTE_NAME + "=\"" + expression + "\"");
                } else {
                    stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.CONDITION_ELEMENT_NAME + " " + XmlConfigConstant.ID_ATTRIBUTE_NAME + "=\"" + id + "\"");
                }
                if (versionWeightEntity != null) {
                    stringBuilder.append(" " + XmlConfigConstant.VERSION_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(versionWeightEntity.getWeightMap()) + "\"");
                }
                if (regionWeightEntity != null) {
                    stringBuilder.append(" " + XmlConfigConstant.REGION_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(regionWeightEntity.getWeightMap()) + "\"");
                }
                if (addressWeightEntity != null) {
                    stringBuilder.append(" " + XmlConfigConstant.ADDRESS_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME + "=\"" + StringUtil.convertToString(addressWeightEntity.getWeightMap()) + "\"");
                }
                stringBuilder.append("/>\n");
            }
            stringBuilder.append(INDENT + INDENT + "</" + XmlConfigConstant.CONDITIONS_ELEMENT_NAME + ">\n");
        }
    }

    private void deparseStrategyRoute(StringBuilder stringBuilder, List<StrategyRouteEntity> strategyRouteEntityList) {
        if (CollectionUtils.isNotEmpty(strategyRouteEntityList)) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.ROUTES_ELEMENT_NAME + ">\n");
            for (StrategyRouteEntity strategyRouteEntity : strategyRouteEntityList) {
                String id = strategyRouteEntity.getId();
                StrategyRouteType type = strategyRouteEntity.getType();
                String value = strategyRouteEntity.getValue();

                stringBuilder.append(INDENT + INDENT + INDENT + "<" + XmlConfigConstant.ROUTE_ELEMENT_NAME + " " + XmlConfigConstant.ID_ATTRIBUTE_NAME + "=\"" + id + "\" " + XmlConfigConstant.TYPE_ATTRIBUTE_NAME + "=\"" + type + "\">" + value + "</" + XmlConfigConstant.ROUTE_ELEMENT_NAME + ">\n");
            }
            stringBuilder.append(INDENT + INDENT + "</" + XmlConfigConstant.ROUTES_ELEMENT_NAME + ">\n");
        }
    }

    private void deparseStrategyHeader(StringBuilder stringBuilder, StrategyHeaderEntity strategyHeaderEntity) {
        Map<String, String> headerMap = strategyHeaderEntity.getHeaderMap();
        if (MapUtils.isNotEmpty(headerMap)) {
            stringBuilder.append(INDENT + INDENT + "<" + XmlConfigConstant.HEADER_ELEMENT_NAME + ">" + JsonUtil.toJson(headerMap) + "</" + XmlConfigConstant.HEADER_ELEMENT_NAME + ">\n");
        }
    }
}