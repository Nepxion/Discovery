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

import com.nepxion.discovery.common.entity.CountFilterEntity;
import com.nepxion.discovery.common.entity.CustomizationEntity;
import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.FilterHolderEntity;
import com.nepxion.discovery.common.entity.FilterType;
import com.nepxion.discovery.common.entity.HostFilterEntity;
import com.nepxion.discovery.common.entity.RegionWeightEntity;
import com.nepxion.discovery.common.entity.RegisterEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.VersionEntity;
import com.nepxion.discovery.common.entity.VersionFilterEntity;
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
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.CUSTOMIZATION_ELEMENT_NAME)) {
                    customizationEntity = new CustomizationEntity();
                    parseCustomization(childElement, customizationEntity);
                }
            }
        }

        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setRegisterEntity(registerEntity);
        ruleEntity.setDiscoveryEntity(discoveryEntity);
        ruleEntity.setCustomizationEntity(customizationEntity);
        ruleEntity.setContent(config);

        LOG.info("Rule entity=\n{}", ruleEntity);

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
            List<String> globalFilterValueList = StringUtil.split(globalFilterValue, ConfigConstant.SEPARATE);
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
                        filterValueList = StringUtil.split(filterValue, ConfigConstant.SEPARATE);
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
                        List<String> consumerVersionValueList = StringUtil.split(consumerVersionValue, ConfigConstant.SEPARATE);
                        versionEntity.setConsumerVersionValueList(consumerVersionValueList);
                    }

                    Attribute providerVersionValueAttribute = childElement.attribute(ConfigConstant.PROVIDER_VERSION_VALUE_ATTRIBUTE_NAME);
                    if (providerVersionValueAttribute != null) {
                        String providerVersionValue = providerVersionValueAttribute.getData().toString().trim();
                        List<String> providerVersionValueList = StringUtil.split(providerVersionValue, ConfigConstant.SEPARATE);
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

        Map<String, List<WeightEntity>> weightEntityMap = weightFilterEntity.getWeightEntityMap();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.SERVICE_ELEMENT_NAME)) {
                    WeightEntity weightEntity = new WeightEntity();

                    Attribute consumerServiceNameAttribute = childElement.attribute(ConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME);
                    String consumerServiceName = null;
                    if (consumerServiceNameAttribute != null) {
                        consumerServiceName = consumerServiceNameAttribute.getData().toString().trim();
                    } else {
                        consumerServiceName = StringUtils.EMPTY;
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
                    List<String> providerWeightValueList = StringUtil.split(providerWeightValue, ConfigConstant.SEPARATE);
                    for (String value : providerWeightValueList) {
                        String[] valueArray = StringUtils.split(value, ConfigConstant.SEPARATE);
                        String version = valueArray[0].trim();
                        int weight = Integer.valueOf(valueArray[1].trim());
                        if (weight < 0) {
                            throw new DiscoveryException("Attribute[" + ConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] has weight value less than 0");
                        }

                        weightMap.put(version, weight);
                    }
                    weightEntity.setWeightMap(weightMap);

                    List<WeightEntity> weightEntityList = weightEntityMap.get(consumerServiceName);
                    if (weightEntityList == null) {
                        weightEntityList = new ArrayList<WeightEntity>();
                        weightEntityMap.put(consumerServiceName, weightEntityList);
                    }

                    weightEntityList.add(weightEntity);
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
                    List<String> providerWeightValueList = StringUtil.split(providerWeightValue, ConfigConstant.SEPARATE);
                    for (String value : providerWeightValueList) {
                        String[] valueArray = StringUtils.split(value, ConfigConstant.SEPARATE);
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
}