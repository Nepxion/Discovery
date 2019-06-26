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
import java.util.Collections;
import java.util.Comparator;
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
import com.nepxion.discovery.common.entity.CountFilterEntity;
import com.nepxion.discovery.common.entity.CustomizationEntity;
import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.FilterHolderEntity;
import com.nepxion.discovery.common.entity.FilterType;
import com.nepxion.discovery.common.entity.HostFilterEntity;
import com.nepxion.discovery.common.entity.RegionWeightEntity;
import com.nepxion.discovery.common.entity.RegisterEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyConditionEntity;
import com.nepxion.discovery.common.entity.StrategyCustomizationEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.common.entity.StrategyRouteEntity;
import com.nepxion.discovery.common.entity.VersionEntity;
import com.nepxion.discovery.common.entity.VersionFilterEntity;
import com.nepxion.discovery.common.entity.VersionWeightEntity;
import com.nepxion.discovery.common.entity.WeightEntity;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
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

            return parseRoot(config, rootElement);
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

        int customizationElementCount = element.elements(ConfigConstant.CUSTOMIZATION_ELEMENT_NAME).size();
        if (customizationElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.CUSTOMIZATION_ELEMENT_NAME + "] to be configed");
        }

        RegisterEntity registerEntity = null;
        DiscoveryEntity discoveryEntity = null;
        StrategyEntity strategyEntity = null;
        StrategyCustomizationEntity strategyCustomizationEntity = null;
        CustomizationEntity customizationEntity = null;
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
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.CUSTOMIZATION_ELEMENT_NAME)) {
                    customizationEntity = new CustomizationEntity();
                    parseCustomization(childElement, customizationEntity);
                }
            }
        }

        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setRegisterEntity(registerEntity);
        ruleEntity.setDiscoveryEntity(discoveryEntity);
        ruleEntity.setStrategyEntity(strategyEntity);
        ruleEntity.setStrategyCustomizationEntity(strategyCustomizationEntity);
        ruleEntity.setCustomizationEntity(customizationEntity);
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
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.WEIGHT_ELEMENT_NAME)) {
                    parseWeightFilter(childElement, discoveryEntity);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void parseStrategy(Element element, StrategyEntity strategyEntity) {
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
        List<StrategyConditionEntity> strategyConditionEntityList = strategyCustomizationEntity.getStrategyConditionEntityList();
        List<StrategyRouteEntity> strategyRouteEntityList = strategyCustomizationEntity.getStrategyRouteEntityList();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.CONDITIONS_ELEMENT_NAME)) {
                    parseStrategyCondition(childElement, strategyConditionEntityList);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.ROUTES_ELEMENT_NAME)) {
                    parseStrategyRoute(childElement, strategyRouteEntityList);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void parseCustomization(Element element, CustomizationEntity customizationEntity) {
        Map<String, Map<String, String>> customizationMap = customizationEntity.getCustomizationMap();
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

                    Map<String, String> customizationParameter = customizationMap.get(serviceName);
                    if (customizationParameter == null) {
                        customizationParameter = new LinkedHashMap<String, String>();
                        customizationMap.put(serviceName, customizationParameter);
                    }
                    customizationParameter.put(key, value);
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
                } catch (NumberFormatException e) {
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
                            } catch (NumberFormatException e) {
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
                    if (!StringUtils.equals(type, ConfigConstant.VERSION_ELEMENT_NAME) && !StringUtils.equals(type, ConfigConstant.REGION_ELEMENT_NAME)) {
                        throw new DiscoveryException("The value of attribute[" + ConfigConstant.TYPE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] must be '" + ConfigConstant.VERSION_ELEMENT_NAME + "' or '" + ConfigConstant.REGION_ELEMENT_NAME + "'");
                    }

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
                    Map<String, Integer> weightMap = new LinkedHashMap<String, Integer>();
                    List<String> providerWeightValueList = StringUtil.splitToList(providerWeightValue, DiscoveryConstant.SEPARATE);
                    for (String value : providerWeightValueList) {
                        String[] valueArray = StringUtils.split(value, DiscoveryConstant.EQUALS);
                        String version = valueArray[0].trim();
                        int weight = Integer.valueOf(valueArray[1].trim());
                        if (weight < 0) {
                            throw new DiscoveryException("Attribute[" + ConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] has weight value less than 0");
                        }

                        weightMap.put(version, weight);
                    }
                    weightEntity.setWeightMap(weightMap);

                    if (StringUtils.isNotEmpty(consumerServiceName)) {
                        if (StringUtils.equals(type, ConfigConstant.VERSION_ELEMENT_NAME)) {
                            List<WeightEntity> list = versionWeightEntityMap.get(consumerServiceName);
                            if (list == null) {
                                list = new ArrayList<WeightEntity>();
                                versionWeightEntityMap.put(consumerServiceName, list);
                            }

                            list.add(weightEntity);
                        } else if (StringUtils.equals(type, ConfigConstant.REGION_ELEMENT_NAME)) {
                            List<WeightEntity> list = regionWeightEntityMap.get(consumerServiceName);
                            if (list == null) {
                                list = new ArrayList<WeightEntity>();
                                regionWeightEntityMap.put(consumerServiceName, list);
                            }

                            list.add(weightEntity);
                        }
                    } else {
                        if (StringUtils.equals(type, ConfigConstant.VERSION_ELEMENT_NAME)) {
                            versionWeightEntityList.add(weightEntity);
                        } else if (StringUtils.equals(type, ConfigConstant.REGION_ELEMENT_NAME)) {
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
                    Map<String, Integer> weightMap = new LinkedHashMap<String, Integer>();
                    List<String> providerWeightValueList = StringUtil.splitToList(providerWeightValue, DiscoveryConstant.SEPARATE);
                    for (String value : providerWeightValueList) {
                        String[] valueArray = StringUtils.split(value, DiscoveryConstant.EQUALS);
                        String version = valueArray[0].trim();
                        int weight = Integer.valueOf(valueArray[1].trim());
                        if (weight < 0) {
                            throw new DiscoveryException("Attribute[" + ConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] has weight value less than 0");
                        }

                        weightMap.put(version, weight);
                    }
                    versionWeightEntity.setWeightMap(weightMap);

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
                    Map<String, Integer> weightMap = new LinkedHashMap<String, Integer>();
                    List<String> providerWeightValueList = StringUtil.splitToList(providerWeightValue, DiscoveryConstant.SEPARATE);
                    for (String value : providerWeightValueList) {
                        String[] valueArray = StringUtils.split(value, DiscoveryConstant.EQUALS);
                        String region = valueArray[0].trim();
                        int weight = Integer.valueOf(valueArray[1].trim());
                        if (weight < 0) {
                            throw new DiscoveryException("Attribute[" + ConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] has weight value less than 0");
                        }

                        weightMap.put(region, weight);
                    }
                    regionWeightEntity.setWeightMap(weightMap);

                    weightFilterEntity.setRegionWeightEntity(regionWeightEntity);
                }
            }
        }

        discoveryEntity.setWeightFilterEntity(weightFilterEntity);
    }

    @SuppressWarnings("rawtypes")
    private void parseStrategyCondition(Element element, List<StrategyConditionEntity> strategyConditionEntityList) {
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.CONDITION_ELEMENT_NAME)) {
                    StrategyConditionEntity strategyConditionEntity = new StrategyConditionEntity();

                    Attribute idAttribute = childElement.attribute(ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (idAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.ID_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String id = idAttribute.getData().toString().trim();
                    strategyConditionEntity.setId(id);

                    Attribute headerAttribute = childElement.attribute(ConfigConstant.HEADER_ATTRIBUTE_NAME);
                    if (headerAttribute == null) {
                        throw new DiscoveryException("Attribute[" + ConfigConstant.HEADER_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                    }
                    String header = headerAttribute.getData().toString().trim();
                    List<String> headerList = StringUtil.splitToList(header, DiscoveryConstant.SEPARATE);
                    for (String value : headerList) {
                        String[] valueArray = StringUtils.split(value, DiscoveryConstant.EQUALS);
                        String headerName = valueArray[0].trim();
                        String headerValue = valueArray[1].trim();

                        strategyConditionEntity.getHeaderMap().put(headerName, headerValue);
                    }

                    Attribute versionIdAttribute = childElement.attribute(ConfigConstant.VERSION_ELEMENT_NAME + DiscoveryConstant.DASH + ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (versionIdAttribute != null) {
                        String versionId = versionIdAttribute.getData().toString().trim();
                        strategyConditionEntity.setVersionId(versionId);
                    }

                    Attribute regionIdAttribute = childElement.attribute(ConfigConstant.REGION_ELEMENT_NAME + DiscoveryConstant.DASH + ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (regionIdAttribute != null) {
                        String regionId = regionIdAttribute.getData().toString().trim();
                        strategyConditionEntity.setRegionId(regionId);
                    }

                    Attribute addressIdAttribute = childElement.attribute(ConfigConstant.ADDRESS_ELEMENT_NAME + DiscoveryConstant.DASH + ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (addressIdAttribute != null) {
                        String addressId = addressIdAttribute.getData().toString().trim();
                        strategyConditionEntity.setAddressId(addressId);
                    }

                    Attribute versionWeightIdAttribute = childElement.attribute(ConfigConstant.VERSION_WEIGHT_ELEMENT_NAME + DiscoveryConstant.DASH + ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (versionWeightIdAttribute != null) {
                        String versionWeightId = versionWeightIdAttribute.getData().toString().trim();
                        strategyConditionEntity.setVersionWeightId(versionWeightId);
                    }

                    Attribute regionWeightIdAttribute = childElement.attribute(ConfigConstant.REGION_WEIGHT_ELEMENT_NAME + DiscoveryConstant.DASH + ConfigConstant.ID_ATTRIBUTE_NAME);
                    if (regionWeightIdAttribute != null) {
                        String regionWeightId = regionWeightIdAttribute.getData().toString().trim();
                        strategyConditionEntity.setRegionWeightId(regionWeightId);
                    }

                    strategyConditionEntityList.add(strategyConditionEntity);
                }
            }
        }

        // Header参数越多，越排在前面
        Collections.sort(strategyConditionEntityList, new Comparator<StrategyConditionEntity>() {
            public int compare(StrategyConditionEntity object1, StrategyConditionEntity object2) {
                Integer count1 = object1.getHeaderMap().size();
                Integer count2 = object2.getHeaderMap().size();

                return count2.compareTo(count1);
            }
        });
    }

    @SuppressWarnings("rawtypes")
    private void parseStrategyRoute(Element element, List<StrategyRouteEntity> strategyRouteEntityList) {
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
                    if (!StringUtils.equals(type, ConfigConstant.VERSION_ELEMENT_NAME) && !StringUtils.equals(type, ConfigConstant.REGION_ELEMENT_NAME) && !StringUtils.equals(type, ConfigConstant.ADDRESS_ELEMENT_NAME) && !StringUtils.equals(type, ConfigConstant.VERSION_WEIGHT_ELEMENT_NAME) && !StringUtils.equals(type, ConfigConstant.REGION_WEIGHT_ELEMENT_NAME)) {
                        throw new DiscoveryException("The value of attribute[" + ConfigConstant.TYPE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] must be '" + ConfigConstant.VERSION_ELEMENT_NAME + "' or '" + ConfigConstant.REGION_ELEMENT_NAME + "' or '" + ConfigConstant.ADDRESS_ELEMENT_NAME + "' or '" + ConfigConstant.VERSION_WEIGHT_ELEMENT_NAME + "' or '" + ConfigConstant.REGION_WEIGHT_ELEMENT_NAME + "'");
                    }
                    strategyRouteEntity.setType(type);

                    String value = childElement.getTextTrim();
                    strategyRouteEntity.setValue(value);

                    strategyRouteEntityList.add(strategyRouteEntity);
                }
            }
        }
    }
}