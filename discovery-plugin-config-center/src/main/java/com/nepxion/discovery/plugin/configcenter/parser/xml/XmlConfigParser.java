package com.nepxion.discovery.plugin.configcenter.parser.xml;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.AddressWeightEntity;
import com.nepxion.discovery.common.entity.ConditionType;
import com.nepxion.discovery.common.entity.CountFilterEntity;
import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.FilterHolderEntity;
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
import com.nepxion.discovery.common.entity.StrategyCustomizationEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.common.entity.StrategyHeaderEntity;
import com.nepxion.discovery.common.entity.StrategyRouteEntity;
import com.nepxion.discovery.common.entity.StrategyRouteType;
import com.nepxion.discovery.common.entity.VersionEntity;
import com.nepxion.discovery.common.entity.VersionFilterEntity;
import com.nepxion.discovery.common.entity.VersionWeightEntity;
import com.nepxion.discovery.common.entity.WeightEntity;
import com.nepxion.discovery.common.entity.WeightEntityWrapper;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.common.entity.WeightType;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.configcenter.constant.ConfigConstant;
import com.nepxion.discovery.plugin.configcenter.parser.xml.dom4j.Dom4JReader;
import com.nepxion.discovery.plugin.framework.config.PluginConfigParser;

public class XmlConfigParser implements PluginConfigParser {
    private static final Logger LOG = LoggerFactory.getLogger(XmlConfigParser.class);

    @Override
    public RuleEntity parse(String config) {
        if (StringUtils.isEmpty(config)) {
            throw new DiscoveryException("Config is null or empty");
        }

        try {
            Document document = Dom4JReader.getDocument(config);

            Element rootElement = document.getRootElement();

            RuleEntity ruleEntity = parseRoot(config, rootElement);

            // Just for Json text print
            /*System.out.println("**************************************************");
            ruleEntity.setContent("");
            System.out.println(JsonUtil.toJson(ruleEntity));
            System.out.println("**************************************************");*/

            return ruleEntity;
        } catch (Exception e) {
            throw new DiscoveryException(e.getMessage(), e);
        }
    }

    @SuppressWarnings("rawtypes")
    private RuleEntity parseRoot(String config, Element element) {
        LOG.info("Start to parse rule xml...");

        int registerElementCount = element.elements(ConfigConstant.REGISTER_ELEMENT_NAME).size();
        if (registerElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.REGISTER_ELEMENT_NAME + "] to be configed");
        }

        int discoveryElementCount = element.elements(ConfigConstant.DISCOVERY_ELEMENT_NAME).size();
        if (discoveryElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.DISCOVERY_ELEMENT_NAME + "] to be configed");
        }

        int strategyElementCount = element.elements(ConfigConstant.STRATEGY_ELEMENT_NAME).size();
        if (strategyElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.STRATEGY_ELEMENT_NAME + "] to be configed");
        }

        int strategyCustomizationElementCount = element.elements(ConfigConstant.STRATEGY_CUSTOMIZATION_ELEMENT_NAME).size();
        if (strategyCustomizationElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.STRATEGY_CUSTOMIZATION_ELEMENT_NAME + "] to be configed");
        }

        int strategyBlacklistElementCount = element.elements(ConfigConstant.STRATEGY_BLACKLIST_ELEMENT_NAME).size();
        if (strategyBlacklistElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.STRATEGY_BLACKLIST_ELEMENT_NAME + "] to be configed");
        }

        int parameterElementCount = element.elements(ConfigConstant.PARAMETER_ELEMENT_NAME).size();
        if (parameterElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.PARAMETER_ELEMENT_NAME + "] to be configed");
        }

        RegisterEntity registerEntity = null;
        DiscoveryEntity discoveryEntity = null;
        StrategyEntity strategyEntity = null;
        StrategyCustomizationEntity strategyCustomizationEntity = null;
        StrategyBlacklistEntity strategyBlacklistEntity = null;
        ParameterEntity parameterEntity = null;
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.REGISTER_ELEMENT_NAME)) {
                    registerEntity = new RegisterEntity();
                    parseRegister(childElement, registerEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.DISCOVERY_ELEMENT_NAME)) {
                    discoveryEntity = new DiscoveryEntity();
                    parseDiscovery(childElement, discoveryEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.STRATEGY_ELEMENT_NAME)) {
                    strategyEntity = new StrategyEntity();
                    parseStrategy(childElement, strategyEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.STRATEGY_CUSTOMIZATION_ELEMENT_NAME)) {
                    strategyCustomizationEntity = new StrategyCustomizationEntity();
                    parseStrategyCustomization(childElement, strategyCustomizationEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.STRATEGY_BLACKLIST_ELEMENT_NAME)) {
                    strategyBlacklistEntity = new StrategyBlacklistEntity();
                    parseStrategyBlacklist(childElement, strategyBlacklistEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.PARAMETER_ELEMENT_NAME)) {
                    parameterEntity = new ParameterEntity();
                    parseParameter(childElement, parameterEntity);
                }
            }
        }

        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setRegisterEntity(registerEntity);
        ruleEntity.setDiscoveryEntity(discoveryEntity);
        ruleEntity.setStrategyEntity(strategyEntity);
        ruleEntity.setStrategyCustomizationEntity(strategyCustomizationEntity);
        ruleEntity.setStrategyBlacklistEntity(strategyBlacklistEntity);
        ruleEntity.setParameterEntity(parameterEntity);
        ruleEntity.setContent(config);

        LOG.info("Rule content=\n{}", config);

        return ruleEntity;
    }

    @SuppressWarnings("rawtypes")
    private void parseRegister(Element element, RegisterEntity registerEntity) {
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.BLACKLIST_ELEMENT_NAME)) {
                    parseHostFilter(childElement, ConfigConstant.BLACKLIST_ELEMENT_NAME, registerEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.WHITELIST_ELEMENT_NAME)) {
                    parseHostFilter(childElement, ConfigConstant.WHITELIST_ELEMENT_NAME, registerEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.COUNT_ELEMENT_NAME)) {
                    parseCountFilter(childElement, registerEntity);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void parseDiscovery(Element element, DiscoveryEntity discoveryEntity) {
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.BLACKLIST_ELEMENT_NAME)) {
                    parseHostFilter(childElement, ConfigConstant.BLACKLIST_ELEMENT_NAME, discoveryEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.WHITELIST_ELEMENT_NAME)) {
                    parseHostFilter(childElement, ConfigConstant.WHITELIST_ELEMENT_NAME, discoveryEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.VERSION_ELEMENT_NAME)) {
                    parseVersionFilter(childElement, discoveryEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.REGION_ELEMENT_NAME)) {
                    parseRegionFilter(childElement, discoveryEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.WEIGHT_ELEMENT_NAME)) {
                    parseWeightFilter(childElement, discoveryEntity);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void parseStrategy(Element element, StrategyEntity strategyEntity) {
        int versionElementCount = element.elements(ConfigConstant.VERSION_ELEMENT_NAME).size();
        if (versionElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.VERSION_ELEMENT_NAME + "] to be configed");
        }

        int regionElementCount = element.elements(ConfigConstant.REGION_ELEMENT_NAME).size();
        if (regionElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.REGION_ELEMENT_NAME + "] to be configed");
        }

        int addressElementCount = element.elements(ConfigConstant.ADDRESS_ELEMENT_NAME).size();
        if (addressElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.ADDRESS_ELEMENT_NAME + "] to be configed");
        }

        int versionWeightElementCount = element.elements(ConfigConstant.VERSION_WEIGHT_ELEMENT_NAME).size();
        if (versionWeightElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.VERSION_WEIGHT_ELEMENT_NAME + "] to be configed");
        }

        int regionWeightElementCount = element.elements(ConfigConstant.REGION_WEIGHT_ELEMENT_NAME).size();
        if (regionWeightElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.REGION_WEIGHT_ELEMENT_NAME + "] to be configed");
        }

        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.VERSION_ELEMENT_NAME)) {
                    String versionValue = childElement.getTextTrim();
                    strategyEntity.setVersionValue(versionValue);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.REGION_ELEMENT_NAME)) {
                    String regionValue = childElement.getTextTrim();
                    strategyEntity.setRegionValue(regionValue);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.ADDRESS_ELEMENT_NAME)) {
                    String addressValue = childElement.getTextTrim();
                    strategyEntity.setAddressValue(addressValue);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.VERSION_WEIGHT_ELEMENT_NAME)) {
                    String versionWeightValue = childElement.getTextTrim();
                    strategyEntity.setVersionWeightValue(versionWeightValue);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.REGION_WEIGHT_ELEMENT_NAME)) {
                    String regionWeightValue = childElement.getTextTrim();
                    strategyEntity.setRegionWeightValue(regionWeightValue);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void parseStrategyCustomization(Element element, StrategyCustomizationEntity strategyCustomizationEntity) {
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.CONDITIONS)) {
                    Attribute typeAttribute = childElement.attribute(ConfigConstant.TYPE_ATTRIBUTE_NAME);
                    if (typeAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.TYPE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String type = typeAttribute.getData().toString().trim();
                    ConditionType conditionType = ConditionType.fromString(type);
                    switch (conditionType) {
                        case BLUE_GREEN:
                            parseStrategyConditionBlueGreen(childElement, strategyCustomizationEntity);
                            break;
                        case GRAY:
                            parseStrategyConditionGray(childElement, strategyCustomizationEntity);
                            break;
                    }
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.ROUTES_ELEMENT_NAME)) {
                    parseStrategyRoute(childElement, strategyCustomizationEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.HEADERS_ELEMENT_NAME)) {
                    parseStrategyHeader(childElement, strategyCustomizationEntity);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void parseStrategyBlacklist(Element element, StrategyBlacklistEntity strategyBlacklistEntity) {
        List<String> idList = new ArrayList<String>();
        List<String> addressList = new ArrayList<String>();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                Attribute valueAttribute = childElement.attribute(ConfigConstant.VALUE_ATTRIBUTE_NAME);
                if (valueAttribute == null) {
                    throw new DiscoveryException("Attribute[" + ConfigConstant.VALUE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String value = valueAttribute.getData().toString().trim();
                List<String> valueList = StringUtil.splitToList(value, DiscoveryConstant.SEPARATE);
                if (StringUtils.equals(childElement.getName(), ConfigConstant.ID_ELEMENT_NAME)) {
                    idList.addAll(valueList);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.ADDRESS_ELEMENT_NAME)) {
                    addressList.addAll(valueList);
                }
            }
        }

        strategyBlacklistEntity.setIdList(idList);
        strategyBlacklistEntity.setAddressList(addressList);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void parseParameter(Element element, ParameterEntity parameterEntity) {
        Map<String, List<ParameterServiceEntity>> parameterServiceMap = parameterEntity.getParameterServiceMap();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.SERVICE_ELEMENT_NAME)) {
                    Attribute serviceNameAttribute = childElement.attribute(ConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME);
                    if (serviceNameAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String serviceName = serviceNameAttribute.getData().toString().trim();

                    ParameterServiceEntity parameterServiceEntity = new ParameterServiceEntity();

                    for (Iterator<Attribute> iterator = childElement.attributeIterator(); iterator.hasNext();) {
                        Attribute attribute = iterator.next();
                        String key = attribute.getName();
                        String value = attribute.getData().toString().trim();

                        parameterServiceEntity.getParameterMap().put(key, value);
                    }

                    List<ParameterServiceEntity> parameterServiceList = parameterServiceMap.get(serviceName);
                    if (parameterServiceList == null) {
                        parameterServiceList = new ArrayList<ParameterServiceEntity>();
                        parameterServiceMap.put(serviceName, parameterServiceList);
                    }
                    parameterServiceList.add(parameterServiceEntity);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void parseHostFilter(Element element, String filterTypeValue, FilterHolderEntity filterHolderEntity) {
        HostFilterEntity hostFilterEntity = filterHolderEntity.getHostFilterEntity();
        if (hostFilterEntity != null) {
            throw new DiscoveryException("Allow only one filter element to be configed, [" + ConfigConstant.BLACKLIST_ELEMENT_NAME + "] or [" + ConfigConstant.WHITELIST_ELEMENT_NAME + "]");
        }

        hostFilterEntity = new HostFilterEntity();
        hostFilterEntity.setFilterType(FilterType.fromString(filterTypeValue));

        Attribute globalFilterAttribute = element.attribute(ConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME);
        if (globalFilterAttribute != null) {
            String globalFilterValue = globalFilterAttribute.getData().toString().trim();
            List<String> globalFilterValueList = StringUtil.splitToList(globalFilterValue, DiscoveryConstant.SEPARATE);
            hostFilterEntity.setFilterValueList(globalFilterValueList);
        }

        Map<String, List<String>> filterMap = hostFilterEntity.getFilterMap();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.SERVICE_ELEMENT_NAME)) {
                    Attribute serviceNameAttribute = childElement.attribute(ConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME);
                    if (serviceNameAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String serviceName = serviceNameAttribute.getData().toString().trim();

                    Attribute filterValueAttribute = childElement.attribute(ConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME);
                    List<String> filterValueList = null;
                    if (filterValueAttribute != null) {
                        String filterValue = filterValueAttribute.getData().toString().trim();
                        filterValueList = StringUtil.splitToList(filterValue, DiscoveryConstant.SEPARATE);
                    }
                    filterMap.put(serviceName, filterValueList);
                }
            }
        }

        filterHolderEntity.setHostFilterEntity(hostFilterEntity);
    }

    @SuppressWarnings("rawtypes")
    private void parseCountFilter(Element element, RegisterEntity registerEntity) {
        CountFilterEntity countFilterEntity = registerEntity.getCountFilterEntity();
        if (countFilterEntity != null) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.COUNT_ELEMENT_NAME + "] to be configed");
        }

        countFilterEntity = new CountFilterEntity();

        Attribute globalFilterAttribute = element.attribute(ConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME);
        if (globalFilterAttribute != null) {
            String globalFilterValue = globalFilterAttribute.getData().toString().trim();
            if (StringUtils.isNotEmpty(globalFilterValue)) {
                Integer globalValue = null;
                try {
                    globalValue = Integer.valueOf(globalFilterValue);
                } catch (Exception e) {
                    throw new DiscoveryException("Attribute[" + ConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "] value in element[" + element.getName() + "] is invalid, must be int type", e);
                }
                countFilterEntity.setFilterValue(globalValue);
            }
        }

        Map<String, Integer> filterMap = countFilterEntity.getFilterMap();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.SERVICE_ELEMENT_NAME)) {
                    Attribute serviceNameAttribute = childElement.attribute(ConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME);
                    if (serviceNameAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String serviceName = serviceNameAttribute.getData().toString().trim();

                    Integer value = null;
                    Attribute filterValueAttribute = childElement.attribute(ConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME);
                    if (filterValueAttribute != null) {
                        String filterValue = filterValueAttribute.getData().toString().trim();
                        if (StringUtils.isNotEmpty(filterValue)) {
                            try {
                                value = Integer.valueOf(filterValue);
                            } catch (Exception e) {
                                throw new DiscoveryException("Attribute[" + ConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "] value in element[" + childElement.getName() + "] is invalid, must be int type", e);
                            }
                        }
                    }

                    filterMap.put(serviceName, value);
                }
            }
        }

        registerEntity.setCountFilterEntity(countFilterEntity);
    }

    @SuppressWarnings("rawtypes")
    private void parseVersionFilter(Element element, DiscoveryEntity discoveryEntity) {
        VersionFilterEntity versionFilterEntity = discoveryEntity.getVersionFilterEntity();
        if (versionFilterEntity != null) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.VERSION_ELEMENT_NAME + "] to be configed");
        }

        versionFilterEntity = new VersionFilterEntity();

        Map<String, List<VersionEntity>> versionEntityMap = versionFilterEntity.getVersionEntityMap();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.SERVICE_ELEMENT_NAME)) {
                    VersionEntity versionEntity = new VersionEntity();

                    Attribute consumerServiceNameAttribute = childElement.attribute(ConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME);
                    if (consumerServiceNameAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String consumerServiceName = consumerServiceNameAttribute.getData().toString().trim();
                    versionEntity.setConsumerServiceName(consumerServiceName);

                    Attribute providerServiceNameAttribute = childElement.attribute(ConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME);
                    if (providerServiceNameAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String providerServiceName = providerServiceNameAttribute.getData().toString().trim();
                    versionEntity.setProviderServiceName(providerServiceName);

                    Attribute consumerVersionValueAttribute = childElement.attribute(ConfigConstant.CONSUMER_VERSION_VALUE_ATTRIBUTE_NAME);
                    if (consumerVersionValueAttribute != null) {
                        String consumerVersionValue = consumerVersionValueAttribute.getData().toString().trim();
                        List<String> consumerVersionValueList = StringUtil.splitToList(consumerVersionValue, DiscoveryConstant.SEPARATE);
                        versionEntity.setConsumerVersionValueList(consumerVersionValueList);
                    }

                    Attribute providerVersionValueAttribute = childElement.attribute(ConfigConstant.PROVIDER_VERSION_VALUE_ATTRIBUTE_NAME);
                    if (providerVersionValueAttribute != null) {
                        String providerVersionValue = providerVersionValueAttribute.getData().toString().trim();
                        List<String> providerVersionValueList = StringUtil.splitToList(providerVersionValue, DiscoveryConstant.SEPARATE);
                        versionEntity.setProviderVersionValueList(providerVersionValueList);
                    }

                    List<VersionEntity> versionEntityList = versionEntityMap.get(consumerServiceName);
                    if (versionEntityList == null) {
                        versionEntityList = new ArrayList<VersionEntity>();
                        versionEntityMap.put(consumerServiceName, versionEntityList);
                    }

                    versionEntityList.add(versionEntity);
                }
            }
        }

        discoveryEntity.setVersionFilterEntity(versionFilterEntity);
    }

    @SuppressWarnings("rawtypes")
    private void parseRegionFilter(Element element, DiscoveryEntity discoveryEntity) {
        RegionFilterEntity regionFilterEntity = discoveryEntity.getRegionFilterEntity();
        if (regionFilterEntity != null) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.REGION_ELEMENT_NAME + "] to be configed");
        }

        regionFilterEntity = new RegionFilterEntity();

        Map<String, List<RegionEntity>> regionEntityMap = regionFilterEntity.getRegionEntityMap();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.SERVICE_ELEMENT_NAME)) {
                    RegionEntity regionEntity = new RegionEntity();

                    Attribute consumerServiceNameAttribute = childElement.attribute(ConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME);
                    if (consumerServiceNameAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String consumerServiceName = consumerServiceNameAttribute.getData().toString().trim();
                    regionEntity.setConsumerServiceName(consumerServiceName);

                    Attribute providerServiceNameAttribute = childElement.attribute(ConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME);
                    if (providerServiceNameAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String providerServiceName = providerServiceNameAttribute.getData().toString().trim();
                    regionEntity.setProviderServiceName(providerServiceName);

                    Attribute consumerRegionValueAttribute = childElement.attribute(ConfigConstant.CONSUMER_REGION_VALUE_ATTRIBUTE_NAME);
                    if (consumerRegionValueAttribute != null) {
                        String consumerRegionValue = consumerRegionValueAttribute.getData().toString().trim();
                        List<String> consumerRegionValueList = StringUtil.splitToList(consumerRegionValue, DiscoveryConstant.SEPARATE);
                        regionEntity.setConsumerRegionValueList(consumerRegionValueList);
                    }

                    Attribute providerRegionValueAttribute = childElement.attribute(ConfigConstant.PROVIDER_REGION_VALUE_ATTRIBUTE_NAME);
                    if (providerRegionValueAttribute != null) {
                        String providerRegionValue = providerRegionValueAttribute.getData().toString().trim();
                        List<String> providerRegionValueList = StringUtil.splitToList(providerRegionValue, DiscoveryConstant.SEPARATE);
                        regionEntity.setProviderRegionValueList(providerRegionValueList);
                    }

                    List<RegionEntity> regionEntityList = regionEntityMap.get(consumerServiceName);
                    if (regionEntityList == null) {
                        regionEntityList = new ArrayList<RegionEntity>();
                        regionEntityMap.put(consumerServiceName, regionEntityList);
                    }

                    regionEntityList.add(regionEntity);
                }
            }
        }

        discoveryEntity.setRegionFilterEntity(regionFilterEntity);
    }

    @SuppressWarnings("rawtypes")
    private void parseWeightFilter(Element element, DiscoveryEntity discoveryEntity) {
        WeightFilterEntity weightFilterEntity = discoveryEntity.getWeightFilterEntity();
        if (weightFilterEntity != null) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.WEIGHT_ELEMENT_NAME + "] to be configed");
        }

        weightFilterEntity = new WeightFilterEntity();

        Map<String, List<WeightEntity>> versionWeightEntityMap = new LinkedHashMap<String, List<WeightEntity>>();
        List<WeightEntity> versionWeightEntityList = new ArrayList<WeightEntity>();
        weightFilterEntity.setVersionWeightEntityMap(versionWeightEntityMap);
        weightFilterEntity.setVersionWeightEntityList(versionWeightEntityList);

        Map<String, List<WeightEntity>> regionWeightEntityMap = new LinkedHashMap<String, List<WeightEntity>>();
        List<WeightEntity> regionWeightEntityList = new ArrayList<WeightEntity>();
        weightFilterEntity.setRegionWeightEntityMap(regionWeightEntityMap);
        weightFilterEntity.setRegionWeightEntityList(regionWeightEntityList);

        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.SERVICE_ELEMENT_NAME)) {
                    WeightEntity weightEntity = new WeightEntity();

                    Attribute typeAttribute = childElement.attribute(ConfigConstant.TYPE_ATTRIBUTE_NAME);
                    if (typeAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.TYPE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String type = typeAttribute.getData().toString().trim();
                    WeightType weightType = WeightType.fromString(type);
                    weightEntity.setType(weightType);

                    Attribute consumerServiceNameAttribute = childElement.attribute(ConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME);
                    String consumerServiceName = null;
                    if (consumerServiceNameAttribute != null) {
                        consumerServiceName = consumerServiceNameAttribute.getData().toString().trim();
                    }
                    weightEntity.setConsumerServiceName(consumerServiceName);

                    Attribute providerServiceNameAttribute = childElement.attribute(ConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME);
                    if (providerServiceNameAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String providerServiceName = providerServiceNameAttribute.getData().toString().trim();
                    weightEntity.setProviderServiceName(providerServiceName);

                    Attribute providerWeightValueAttribute = childElement.attribute(ConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME);
                    if (providerWeightValueAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String providerWeightValue = providerWeightValueAttribute.getData().toString().trim();

                    WeightEntityWrapper.parseWeightEntity(weightEntity, providerWeightValue);

                    if (StringUtils.isNotEmpty(consumerServiceName)) {
                        if (weightType == WeightType.VERSION) {
                            List<WeightEntity> list = versionWeightEntityMap.get(consumerServiceName);
                            if (list == null) {
                                list = new ArrayList<WeightEntity>();
                                versionWeightEntityMap.put(consumerServiceName, list);
                            }

                            list.add(weightEntity);
                        } else if (weightType == WeightType.REGION) {
                            List<WeightEntity> list = regionWeightEntityMap.get(consumerServiceName);
                            if (list == null) {
                                list = new ArrayList<WeightEntity>();
                                regionWeightEntityMap.put(consumerServiceName, list);
                            }

                            list.add(weightEntity);
                        }
                    } else {
                        if (weightType == WeightType.VERSION) {
                            versionWeightEntityList.add(weightEntity);
                        } else if (weightType == WeightType.REGION) {
                            regionWeightEntityList.add(weightEntity);
                        }
                    }
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.VERSION_ELEMENT_NAME)) {
                    VersionWeightEntity versionWeightEntity = weightFilterEntity.getVersionWeightEntity();
                    if (versionWeightEntity != null) {
                        throw new DiscoveryException("Allow only one element[" + ConfigConstant.VERSION_ELEMENT_NAME + "] to be configed");
                    }

                    versionWeightEntity = new VersionWeightEntity();

                    Attribute providerWeightValueAttribute = childElement.attribute(ConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME);
                    if (providerWeightValueAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String providerWeightValue = providerWeightValueAttribute.getData().toString().trim();

                    WeightEntityWrapper.parseWeightEntity(versionWeightEntity, providerWeightValue);

                    weightFilterEntity.setVersionWeightEntity(versionWeightEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.REGION_ELEMENT_NAME)) {
                    RegionWeightEntity regionWeightEntity = weightFilterEntity.getRegionWeightEntity();
                    if (regionWeightEntity != null) {
                        throw new DiscoveryException("Allow only one element[" + ConfigConstant.REGION_ELEMENT_NAME + "] to be configed");
                    }

                    regionWeightEntity = new RegionWeightEntity();

                    Attribute providerWeightValueAttribute = childElement.attribute(ConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME);
                    if (providerWeightValueAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String providerWeightValue = providerWeightValueAttribute.getData().toString().trim();

                    WeightEntityWrapper.parseWeightEntity(regionWeightEntity, providerWeightValue);

                    weightFilterEntity.setRegionWeightEntity(regionWeightEntity);
                }
            }
        }

        discoveryEntity.setWeightFilterEntity(weightFilterEntity);
    }

    @SuppressWarnings("rawtypes")
    private void parseStrategyConditionBlueGreen(Element element, StrategyCustomizationEntity strategyCustomizationEntity) {
        List<StrategyConditionBlueGreenEntity> strategyConditionBlueGreenEntityList = strategyCustomizationEntity.getStrategyConditionBlueGreenEntityList();
        if (strategyConditionBlueGreenEntityList != null) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.CONDITIONS + "] for attribute[" + ConfigConstant.TYPE_ATTRIBUTE_NAME + "]'s value with '" + ConditionType.BLUE_GREEN + "' to be configed");
        }

        strategyConditionBlueGreenEntityList = new ArrayList<StrategyConditionBlueGreenEntity>();
        strategyCustomizationEntity.setStrategyConditionBlueGreenEntityList(strategyConditionBlueGreenEntityList);

        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.CONDITION_ELEMENT_NAME)) {
                    StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity = new StrategyConditionBlueGreenEntity();

                    Attribute idAttribute = childElement.attribute(ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (idAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.ID_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String id = idAttribute.getData().toString().trim();
                    strategyConditionBlueGreenEntity.setId(id);

                    Attribute headerAttribute = childElement.attribute(ConfigConstant.HEADER_ATTRIBUTE_NAME);
                    if (headerAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.HEADER_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String header = headerAttribute.getData().toString().trim();
                    strategyConditionBlueGreenEntity.setConditionHeader(header);

                    Attribute versionIdAttribute = childElement.attribute(ConfigConstant.VERSION_ELEMENT_NAME + DiscoveryConstant.DASH + ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (versionIdAttribute != null) {
                        String versionId = versionIdAttribute.getData().toString().trim();
                        strategyConditionBlueGreenEntity.setVersionId(versionId);
                    }

                    Attribute regionIdAttribute = childElement.attribute(ConfigConstant.REGION_ELEMENT_NAME + DiscoveryConstant.DASH + ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (regionIdAttribute != null) {
                        String regionId = regionIdAttribute.getData().toString().trim();
                        strategyConditionBlueGreenEntity.setRegionId(regionId);
                    }

                    Attribute addressIdAttribute = childElement.attribute(ConfigConstant.ADDRESS_ELEMENT_NAME + DiscoveryConstant.DASH + ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (addressIdAttribute != null) {
                        String addressId = addressIdAttribute.getData().toString().trim();
                        strategyConditionBlueGreenEntity.setAddressId(addressId);
                    }

                    Attribute versionWeightIdAttribute = childElement.attribute(ConfigConstant.VERSION_WEIGHT_ELEMENT_NAME + DiscoveryConstant.DASH + ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (versionWeightIdAttribute != null) {
                        String versionWeightId = versionWeightIdAttribute.getData().toString().trim();
                        strategyConditionBlueGreenEntity.setVersionWeightId(versionWeightId);
                    }

                    Attribute regionWeightIdAttribute = childElement.attribute(ConfigConstant.REGION_WEIGHT_ELEMENT_NAME + DiscoveryConstant.DASH + ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (regionWeightIdAttribute != null) {
                        String regionWeightId = regionWeightIdAttribute.getData().toString().trim();
                        strategyConditionBlueGreenEntity.setRegionWeightId(regionWeightId);
                    }

                    strategyConditionBlueGreenEntityList.add(strategyConditionBlueGreenEntity);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void parseStrategyConditionGray(Element element, StrategyCustomizationEntity strategyCustomizationEntity) {
        List<StrategyConditionGrayEntity> strategyConditionGrayEntityList = strategyCustomizationEntity.getStrategyConditionGrayEntityList();
        if (strategyConditionGrayEntityList != null) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.CONDITIONS + "] for attribute[" + ConfigConstant.TYPE_ATTRIBUTE_NAME + "]'s value with '" + ConditionType.GRAY + "' to be configed");
        }

        strategyConditionGrayEntityList = new ArrayList<StrategyConditionGrayEntity>();
        strategyCustomizationEntity.setStrategyConditionGrayEntityList(strategyConditionGrayEntityList);

        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.CONDITION_ELEMENT_NAME)) {
                    StrategyConditionGrayEntity strategyConditionGrayEntity = new StrategyConditionGrayEntity();

                    Attribute idAttribute = childElement.attribute(ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (idAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.ID_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String id = idAttribute.getData().toString().trim();
                    strategyConditionGrayEntity.setId(id);

                    Attribute headerAttribute = childElement.attribute(ConfigConstant.HEADER_ATTRIBUTE_NAME);
                    if (headerAttribute != null) {
                        String header = headerAttribute.getData().toString().trim();
                        strategyConditionGrayEntity.setConditionHeader(header);
                    }

                    Attribute versionIdAttribute = childElement.attribute(ConfigConstant.VERSION_ELEMENT_NAME + DiscoveryConstant.DASH + ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (versionIdAttribute != null) {
                        String versionId = versionIdAttribute.getData().toString().trim();
                        VersionWeightEntity versionWeightEntity = new VersionWeightEntity();
                        WeightEntityWrapper.parseWeightEntity(versionWeightEntity, versionId);
                        strategyConditionGrayEntity.setVersionWeightEntity(versionWeightEntity);
                    }

                    Attribute regionIdAttribute = childElement.attribute(ConfigConstant.REGION_ELEMENT_NAME + DiscoveryConstant.DASH + ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (regionIdAttribute != null) {
                        String regionId = regionIdAttribute.getData().toString().trim();
                        RegionWeightEntity regionWeightEntity = new RegionWeightEntity();
                        WeightEntityWrapper.parseWeightEntity(regionWeightEntity, regionId);
                        strategyConditionGrayEntity.setRegionWeightEntity(regionWeightEntity);
                    }

                    Attribute addressIdAttribute = childElement.attribute(ConfigConstant.ADDRESS_ELEMENT_NAME + DiscoveryConstant.DASH + ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (addressIdAttribute != null) {
                        String addressId = addressIdAttribute.getData().toString().trim();
                        AddressWeightEntity addressWeightEntity = new AddressWeightEntity();
                        WeightEntityWrapper.parseWeightEntity(addressWeightEntity, addressId);
                        strategyConditionGrayEntity.setAddressWeightEntity(addressWeightEntity);
                    }

                    strategyConditionGrayEntityList.add(strategyConditionGrayEntity);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void parseStrategyRoute(Element element, StrategyCustomizationEntity strategyCustomizationEntity) {
        List<StrategyRouteEntity> strategyRouteEntityList = strategyCustomizationEntity.getStrategyRouteEntityList();
        if (strategyRouteEntityList != null) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.ROUTES_ELEMENT_NAME + "] to be configed");
        }

        strategyRouteEntityList = new ArrayList<StrategyRouteEntity>();
        strategyCustomizationEntity.setStrategyRouteEntityList(strategyRouteEntityList);

        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.ROUTE_ELEMENT_NAME)) {
                    StrategyRouteEntity strategyRouteEntity = new StrategyRouteEntity();

                    Attribute idAttribute = childElement.attribute(ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (idAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.ID_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String id = idAttribute.getData().toString().trim();
                    strategyRouteEntity.setId(id);

                    Attribute typeAttribute = childElement.attribute(ConfigConstant.TYPE_ATTRIBUTE_NAME);
                    if (typeAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.TYPE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String type = typeAttribute.getData().toString().trim();
                    StrategyRouteType strategyRouteType = StrategyRouteType.fromString(type);
                    strategyRouteEntity.setType(strategyRouteType);

                    String value = childElement.getTextTrim();
                    strategyRouteEntity.setValue(value);

                    strategyRouteEntityList.add(strategyRouteEntity);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void parseStrategyHeader(Element element, StrategyCustomizationEntity strategyCustomizationEntity) {
        StrategyHeaderEntity strategyHeaderEntity = strategyCustomizationEntity.getStrategyHeaderEntity();
        if (strategyHeaderEntity != null) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.HEADERS_ELEMENT_NAME + "] to be configed");
        }

        strategyHeaderEntity = new StrategyHeaderEntity();
        strategyCustomizationEntity.setStrategyHeaderEntity(strategyHeaderEntity);

        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.HEADER_ELEMENT_NAME)) {
                    Attribute keyAttribute = childElement.attribute(ConfigConstant.KEY_ATTRIBUTE_NAME);
                    if (keyAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.KEY_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String key = keyAttribute.getData().toString().trim();

                    Attribute valueAttribute = childElement.attribute(ConfigConstant.VALUE_ATTRIBUTE_NAME);
                    if (valueAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.VALUE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String value = valueAttribute.getData().toString().trim();

                    strategyHeaderEntity.getHeaderMap().put(key, value);
                }
            }
        }
    }
}