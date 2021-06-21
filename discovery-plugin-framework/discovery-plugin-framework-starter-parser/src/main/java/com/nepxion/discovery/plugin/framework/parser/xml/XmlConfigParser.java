package com.nepxion.discovery.plugin.framework.parser.xml;

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
import com.nepxion.discovery.common.dom4j.Dom4JReader;
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
import com.nepxion.discovery.common.entity.StrategyReleaseEntity;
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
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.framework.parser.PluginConfigParser;

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

    private RuleEntity parseRoot(String config, Element element) {
        LOG.info("Start to parse rule from xml...");

        int registerElementCount = element.elements(XmlConfigConstant.REGISTER_ELEMENT_NAME).size();
        if (registerElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.REGISTER_ELEMENT_NAME + "] to be configed");
        }

        int discoveryElementCount = element.elements(XmlConfigConstant.DISCOVERY_ELEMENT_NAME).size();
        if (discoveryElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.DISCOVERY_ELEMENT_NAME + "] to be configed");
        }

        int strategyElementCount = element.elements(XmlConfigConstant.STRATEGY_ELEMENT_NAME).size();
        if (strategyElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.STRATEGY_ELEMENT_NAME + "] to be configed");
        }

        int strategyReleaseElementCount = element.elements(XmlConfigConstant.STRATEGY_RELEASE_ELEMENT_NAME).size();
        if (strategyReleaseElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.STRATEGY_RELEASE_ELEMENT_NAME + "] to be configed");
        }

        int strategyCustomizationElementCount = element.elements(XmlConfigConstant.STRATEGY_CUSTOMIZATION_ELEMENT_NAME).size();
        if (strategyCustomizationElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.STRATEGY_CUSTOMIZATION_ELEMENT_NAME + "] to be configed");
        }

        if (strategyReleaseElementCount > 0 && strategyCustomizationElementCount > 0) {
            throw new DiscoveryException("Attribute[" + XmlConfigConstant.STRATEGY_RELEASE_ELEMENT_NAME + "] and [" + XmlConfigConstant.STRATEGY_CUSTOMIZATION_ELEMENT_NAME + "] are all configed, only one of them exists");
        }

        int strategyBlacklistElementCount = element.elements(XmlConfigConstant.STRATEGY_BLACKLIST_ELEMENT_NAME).size();
        if (strategyBlacklistElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.STRATEGY_BLACKLIST_ELEMENT_NAME + "] to be configed");
        }

        int parameterElementCount = element.elements(XmlConfigConstant.PARAMETER_ELEMENT_NAME).size();
        if (parameterElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.PARAMETER_ELEMENT_NAME + "] to be configed");
        }

        RegisterEntity registerEntity = null;
        DiscoveryEntity discoveryEntity = null;
        StrategyEntity strategyEntity = null;
        StrategyReleaseEntity strategyReleaseEntity = null;
        StrategyBlacklistEntity strategyBlacklistEntity = null;
        ParameterEntity parameterEntity = null;
        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.REGISTER_ELEMENT_NAME)) {
                registerEntity = new RegisterEntity();
                parseRegister(childElement, registerEntity);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.DISCOVERY_ELEMENT_NAME)) {
                discoveryEntity = new DiscoveryEntity();
                parseDiscovery(childElement, discoveryEntity);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.STRATEGY_ELEMENT_NAME)) {
                strategyEntity = new StrategyEntity();
                parseStrategy(childElement, strategyEntity);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.STRATEGY_RELEASE_ELEMENT_NAME)) {
                strategyReleaseEntity = new StrategyReleaseEntity();
                parseStrategyRelease(childElement, strategyReleaseEntity);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.STRATEGY_CUSTOMIZATION_ELEMENT_NAME)) {
                strategyReleaseEntity = new StrategyReleaseEntity();
                parseStrategyRelease(childElement, strategyReleaseEntity);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.STRATEGY_BLACKLIST_ELEMENT_NAME)) {
                strategyBlacklistEntity = new StrategyBlacklistEntity();
                parseStrategyBlacklist(childElement, strategyBlacklistEntity);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.PARAMETER_ELEMENT_NAME)) {
                parameterEntity = new ParameterEntity();
                parseParameter(childElement, parameterEntity);
            }
        }

        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setRegisterEntity(registerEntity);
        ruleEntity.setDiscoveryEntity(discoveryEntity);
        ruleEntity.setStrategyEntity(strategyEntity);
        ruleEntity.setStrategyReleaseEntity(strategyReleaseEntity);
        ruleEntity.setStrategyBlacklistEntity(strategyBlacklistEntity);
        ruleEntity.setParameterEntity(parameterEntity);
        ruleEntity.setContent(config);

        LOG.info("Rule content=\n{}", config);

        return ruleEntity;
    }

    private void parseRegister(Element element, RegisterEntity registerEntity) {
        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.BLACKLIST_ELEMENT_NAME)) {
                parseHostFilter(childElement, XmlConfigConstant.BLACKLIST_ELEMENT_NAME, registerEntity);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.WHITELIST_ELEMENT_NAME)) {
                parseHostFilter(childElement, XmlConfigConstant.WHITELIST_ELEMENT_NAME, registerEntity);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.COUNT_ELEMENT_NAME)) {
                parseCountFilter(childElement, registerEntity);
            }
        }
    }

    private void parseDiscovery(Element element, DiscoveryEntity discoveryEntity) {
        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.BLACKLIST_ELEMENT_NAME)) {
                parseHostFilter(childElement, XmlConfigConstant.BLACKLIST_ELEMENT_NAME, discoveryEntity);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.WHITELIST_ELEMENT_NAME)) {
                parseHostFilter(childElement, XmlConfigConstant.WHITELIST_ELEMENT_NAME, discoveryEntity);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.VERSION_ELEMENT_NAME)) {
                parseVersionFilter(childElement, discoveryEntity);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.REGION_ELEMENT_NAME)) {
                parseRegionFilter(childElement, discoveryEntity);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.WEIGHT_ELEMENT_NAME)) {
                parseWeightFilter(childElement, discoveryEntity);
            }
        }
    }

    private void parseStrategy(Element element, StrategyEntity strategyEntity) {
        int versionElementCount = element.elements(XmlConfigConstant.VERSION_ELEMENT_NAME).size();
        if (versionElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.VERSION_ELEMENT_NAME + "] to be configed");
        }

        int regionElementCount = element.elements(XmlConfigConstant.REGION_ELEMENT_NAME).size();
        if (regionElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.REGION_ELEMENT_NAME + "] to be configed");
        }

        int addressElementCount = element.elements(XmlConfigConstant.ADDRESS_ELEMENT_NAME).size();
        if (addressElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.ADDRESS_ELEMENT_NAME + "] to be configed");
        }

        int versionWeightElementCount = element.elements(XmlConfigConstant.VERSION_WEIGHT_ELEMENT_NAME).size();
        if (versionWeightElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.VERSION_WEIGHT_ELEMENT_NAME + "] to be configed");
        }

        int regionWeightElementCount = element.elements(XmlConfigConstant.REGION_WEIGHT_ELEMENT_NAME).size();
        if (regionWeightElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.REGION_WEIGHT_ELEMENT_NAME + "] to be configed");
        }

        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.VERSION_ELEMENT_NAME)) {
                String versionValue = childElement.getTextTrim();
                strategyEntity.setVersionValue(versionValue);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.REGION_ELEMENT_NAME)) {
                String regionValue = childElement.getTextTrim();
                strategyEntity.setRegionValue(regionValue);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.ADDRESS_ELEMENT_NAME)) {
                String addressValue = childElement.getTextTrim();
                strategyEntity.setAddressValue(addressValue);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.VERSION_WEIGHT_ELEMENT_NAME)) {
                String versionWeightValue = childElement.getTextTrim();
                strategyEntity.setVersionWeightValue(versionWeightValue);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.REGION_WEIGHT_ELEMENT_NAME)) {
                String regionWeightValue = childElement.getTextTrim();
                strategyEntity.setRegionWeightValue(regionWeightValue);
            }
        }
    }

    private void parseStrategyRelease(Element element, StrategyReleaseEntity strategyReleaseEntity) {
        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.CONDITIONS_ELEMENT_NAME)) {
                Attribute typeAttribute = childElement.attribute(XmlConfigConstant.TYPE_ATTRIBUTE_NAME);
                if (typeAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.TYPE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String type = typeAttribute.getData().toString().trim();
                ConditionType conditionType = ConditionType.fromString(type);
                switch (conditionType) {
                    case BLUE_GREEN:
                        parseStrategyConditionBlueGreen(childElement, strategyReleaseEntity);
                        break;
                    case GRAY:
                        parseStrategyConditionGray(childElement, strategyReleaseEntity);
                        break;
                }
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.ROUTES_ELEMENT_NAME)) {
                parseStrategyRoute(childElement, strategyReleaseEntity);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.HEADER_ELEMENT_NAME)) {
                parseStrategyHeader(childElement, strategyReleaseEntity);
            }
        }
    }

    private void parseStrategyBlacklist(Element element, StrategyBlacklistEntity strategyBlacklistEntity) {
        int idElementCount = element.elements(XmlConfigConstant.ID_ELEMENT_NAME).size();
        if (idElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.ID_ELEMENT_NAME + "] to be configed");
        }

        int addressElementCount = element.elements(XmlConfigConstant.ADDRESS_ELEMENT_NAME).size();
        if (addressElementCount > 1) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.ADDRESS_ELEMENT_NAME + "] to be configed");
        }

        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.ID_ELEMENT_NAME)) {
                String idValue = childElement.getTextTrim();
                strategyBlacklistEntity.setIdValue(idValue);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.ADDRESS_ELEMENT_NAME)) {
                String addressValue = childElement.getTextTrim();
                strategyBlacklistEntity.setAddressValue(addressValue);
            }
        }
    }

    private void parseParameter(Element element, ParameterEntity parameterEntity) {
        Map<String, List<ParameterServiceEntity>> parameterServiceMap = parameterEntity.getParameterServiceMap();
        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.SERVICE_ELEMENT_NAME)) {
                Attribute serviceNameAttribute = childElement.attribute(XmlConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME);
                if (serviceNameAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String serviceName = serviceNameAttribute.getData().toString().trim();

                ParameterServiceEntity parameterServiceEntity = new ParameterServiceEntity();

                for (Iterator<Attribute> iterator = childElement.attributeIterator(); iterator.hasNext();) {
                    Attribute attribute = iterator.next();
                    String key = attribute.getName();
                    String value = attribute.getData().toString().trim();

                    parameterServiceEntity.getParameterMap().put(key, value);
                }

                List<ParameterServiceEntity> parameterServiceEntityList = parameterServiceMap.get(serviceName);
                if (parameterServiceEntityList == null) {
                    parameterServiceEntityList = new ArrayList<ParameterServiceEntity>();
                    parameterServiceMap.put(serviceName, parameterServiceEntityList);
                }
                parameterServiceEntityList.add(parameterServiceEntity);
            }
        }
    }

    private void parseHostFilter(Element element, String filterTypeValue, FilterHolderEntity filterHolderEntity) {
        HostFilterEntity hostFilterEntity = filterHolderEntity.getHostFilterEntity();
        if (hostFilterEntity != null) {
            throw new DiscoveryException("Allow only one filter element to be configed, [" + XmlConfigConstant.BLACKLIST_ELEMENT_NAME + "] or [" + XmlConfigConstant.WHITELIST_ELEMENT_NAME + "]");
        }

        hostFilterEntity = new HostFilterEntity();
        hostFilterEntity.setFilterType(FilterType.fromString(filterTypeValue));

        Attribute globalFilterAttribute = element.attribute(XmlConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME);
        if (globalFilterAttribute != null) {
            String globalFilterValue = globalFilterAttribute.getData().toString().trim();
            List<String> globalFilterValueList = StringUtil.splitToList(globalFilterValue);
            hostFilterEntity.setFilterValueList(globalFilterValueList);
        }

        Map<String, List<String>> filterMap = hostFilterEntity.getFilterMap();
        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.SERVICE_ELEMENT_NAME)) {
                Attribute serviceNameAttribute = childElement.attribute(XmlConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME);
                if (serviceNameAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String serviceName = serviceNameAttribute.getData().toString().trim();

                Attribute filterValueAttribute = childElement.attribute(XmlConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME);
                List<String> filterValueList = null;
                if (filterValueAttribute != null) {
                    String filterValue = filterValueAttribute.getData().toString().trim();
                    filterValueList = StringUtil.splitToList(filterValue);
                }
                filterMap.put(serviceName, filterValueList);
            }
        }

        filterHolderEntity.setHostFilterEntity(hostFilterEntity);
    }

    private void parseCountFilter(Element element, RegisterEntity registerEntity) {
        CountFilterEntity countFilterEntity = registerEntity.getCountFilterEntity();
        if (countFilterEntity != null) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.COUNT_ELEMENT_NAME + "] to be configed");
        }

        countFilterEntity = new CountFilterEntity();

        Attribute globalFilterAttribute = element.attribute(XmlConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME);
        if (globalFilterAttribute != null) {
            String globalFilterValue = globalFilterAttribute.getData().toString().trim();
            if (StringUtils.isNotEmpty(globalFilterValue)) {
                Integer globalValue = null;
                try {
                    globalValue = Integer.valueOf(globalFilterValue);
                } catch (Exception e) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "] value in element[" + element.getName() + "] is invalid, must be int type", e);
                }
                countFilterEntity.setFilterValue(globalValue);
            }
        }

        Map<String, Integer> filterMap = countFilterEntity.getFilterMap();
        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.SERVICE_ELEMENT_NAME)) {
                Attribute serviceNameAttribute = childElement.attribute(XmlConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME);
                if (serviceNameAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String serviceName = serviceNameAttribute.getData().toString().trim();

                Integer value = null;
                Attribute filterValueAttribute = childElement.attribute(XmlConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME);
                if (filterValueAttribute != null) {
                    String filterValue = filterValueAttribute.getData().toString().trim();
                    if (StringUtils.isNotEmpty(filterValue)) {
                        try {
                            value = Integer.valueOf(filterValue);
                        } catch (Exception e) {
                            throw new DiscoveryException("Attribute[" + XmlConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "] value in element[" + childElement.getName() + "] is invalid, must be int type", e);
                        }
                    }
                }

                filterMap.put(serviceName, value);
            }
        }

        registerEntity.setCountFilterEntity(countFilterEntity);
    }

    private void parseVersionFilter(Element element, DiscoveryEntity discoveryEntity) {
        VersionFilterEntity versionFilterEntity = discoveryEntity.getVersionFilterEntity();
        if (versionFilterEntity != null) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.VERSION_ELEMENT_NAME + "] to be configed");
        }

        versionFilterEntity = new VersionFilterEntity();

        Map<String, List<VersionEntity>> versionEntityMap = versionFilterEntity.getVersionEntityMap();
        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.SERVICE_ELEMENT_NAME)) {
                VersionEntity versionEntity = new VersionEntity();

                Attribute consumerServiceNameAttribute = childElement.attribute(XmlConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME);
                if (consumerServiceNameAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String consumerServiceName = consumerServiceNameAttribute.getData().toString().trim();
                versionEntity.setConsumerServiceName(consumerServiceName);

                Attribute providerServiceNameAttribute = childElement.attribute(XmlConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME);
                if (providerServiceNameAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String providerServiceName = providerServiceNameAttribute.getData().toString().trim();
                versionEntity.setProviderServiceName(providerServiceName);

                Attribute consumerVersionValueAttribute = childElement.attribute(XmlConfigConstant.CONSUMER_VERSION_VALUE_ATTRIBUTE_NAME);
                if (consumerVersionValueAttribute != null) {
                    String consumerVersionValue = consumerVersionValueAttribute.getData().toString().trim();
                    List<String> consumerVersionValueList = StringUtil.splitToList(consumerVersionValue);
                    versionEntity.setConsumerVersionValueList(consumerVersionValueList);
                }

                Attribute providerVersionValueAttribute = childElement.attribute(XmlConfigConstant.PROVIDER_VERSION_VALUE_ATTRIBUTE_NAME);
                if (providerVersionValueAttribute != null) {
                    String providerVersionValue = providerVersionValueAttribute.getData().toString().trim();
                    List<String> providerVersionValueList = StringUtil.splitToList(providerVersionValue);
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

        discoveryEntity.setVersionFilterEntity(versionFilterEntity);
    }

    private void parseRegionFilter(Element element, DiscoveryEntity discoveryEntity) {
        RegionFilterEntity regionFilterEntity = discoveryEntity.getRegionFilterEntity();
        if (regionFilterEntity != null) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.REGION_ELEMENT_NAME + "] to be configed");
        }

        regionFilterEntity = new RegionFilterEntity();

        Map<String, List<RegionEntity>> regionEntityMap = regionFilterEntity.getRegionEntityMap();
        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.SERVICE_ELEMENT_NAME)) {
                RegionEntity regionEntity = new RegionEntity();

                Attribute consumerServiceNameAttribute = childElement.attribute(XmlConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME);
                if (consumerServiceNameAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String consumerServiceName = consumerServiceNameAttribute.getData().toString().trim();
                regionEntity.setConsumerServiceName(consumerServiceName);

                Attribute providerServiceNameAttribute = childElement.attribute(XmlConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME);
                if (providerServiceNameAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String providerServiceName = providerServiceNameAttribute.getData().toString().trim();
                regionEntity.setProviderServiceName(providerServiceName);

                Attribute consumerRegionValueAttribute = childElement.attribute(XmlConfigConstant.CONSUMER_REGION_VALUE_ATTRIBUTE_NAME);
                if (consumerRegionValueAttribute != null) {
                    String consumerRegionValue = consumerRegionValueAttribute.getData().toString().trim();
                    List<String> consumerRegionValueList = StringUtil.splitToList(consumerRegionValue);
                    regionEntity.setConsumerRegionValueList(consumerRegionValueList);
                }

                Attribute providerRegionValueAttribute = childElement.attribute(XmlConfigConstant.PROVIDER_REGION_VALUE_ATTRIBUTE_NAME);
                if (providerRegionValueAttribute != null) {
                    String providerRegionValue = providerRegionValueAttribute.getData().toString().trim();
                    List<String> providerRegionValueList = StringUtil.splitToList(providerRegionValue);
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

        discoveryEntity.setRegionFilterEntity(regionFilterEntity);
    }

    private void parseWeightFilter(Element element, DiscoveryEntity discoveryEntity) {
        WeightFilterEntity weightFilterEntity = discoveryEntity.getWeightFilterEntity();
        if (weightFilterEntity != null) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.WEIGHT_ELEMENT_NAME + "] to be configed");
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

        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.SERVICE_ELEMENT_NAME)) {
                WeightEntity weightEntity = new WeightEntity();

                Attribute typeAttribute = childElement.attribute(XmlConfigConstant.TYPE_ATTRIBUTE_NAME);
                if (typeAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.TYPE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String type = typeAttribute.getData().toString().trim();
                WeightType weightType = WeightType.fromString(type);
                weightEntity.setType(weightType);

                Attribute consumerServiceNameAttribute = childElement.attribute(XmlConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME);
                String consumerServiceName = null;
                if (consumerServiceNameAttribute != null) {
                    consumerServiceName = consumerServiceNameAttribute.getData().toString().trim();
                }
                weightEntity.setConsumerServiceName(consumerServiceName);

                Attribute providerServiceNameAttribute = childElement.attribute(XmlConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME);
                if (providerServiceNameAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String providerServiceName = providerServiceNameAttribute.getData().toString().trim();
                weightEntity.setProviderServiceName(providerServiceName);

                Attribute providerWeightValueAttribute = childElement.attribute(XmlConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME);
                if (providerWeightValueAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
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
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.VERSION_ELEMENT_NAME)) {
                VersionWeightEntity versionWeightEntity = weightFilterEntity.getVersionWeightEntity();
                if (versionWeightEntity != null) {
                    throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.VERSION_ELEMENT_NAME + "] to be configed");
                }

                versionWeightEntity = new VersionWeightEntity();

                Attribute providerWeightValueAttribute = childElement.attribute(XmlConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME);
                if (providerWeightValueAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String providerWeightValue = providerWeightValueAttribute.getData().toString().trim();

                WeightEntityWrapper.parseWeightEntity(versionWeightEntity, providerWeightValue);

                weightFilterEntity.setVersionWeightEntity(versionWeightEntity);
            } else if (StringUtils.equals(childElement.getName(), XmlConfigConstant.REGION_ELEMENT_NAME)) {
                RegionWeightEntity regionWeightEntity = weightFilterEntity.getRegionWeightEntity();
                if (regionWeightEntity != null) {
                    throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.REGION_ELEMENT_NAME + "] to be configed");
                }

                regionWeightEntity = new RegionWeightEntity();

                Attribute providerWeightValueAttribute = childElement.attribute(XmlConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME);
                if (providerWeightValueAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String providerWeightValue = providerWeightValueAttribute.getData().toString().trim();

                WeightEntityWrapper.parseWeightEntity(regionWeightEntity, providerWeightValue);

                weightFilterEntity.setRegionWeightEntity(regionWeightEntity);
            }
        }

        discoveryEntity.setWeightFilterEntity(weightFilterEntity);
    }

    private void parseStrategyConditionBlueGreen(Element element, StrategyReleaseEntity strategyReleaseEntity) {
        List<StrategyConditionBlueGreenEntity> strategyConditionBlueGreenEntityList = strategyReleaseEntity.getStrategyConditionBlueGreenEntityList();
        if (strategyConditionBlueGreenEntityList != null) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.CONDITIONS_ELEMENT_NAME + "] for attribute[" + XmlConfigConstant.TYPE_ATTRIBUTE_NAME + "]'s value with '" + ConditionType.BLUE_GREEN + "' to be configed");
        }

        strategyConditionBlueGreenEntityList = new ArrayList<StrategyConditionBlueGreenEntity>();
        strategyReleaseEntity.setStrategyConditionBlueGreenEntityList(strategyConditionBlueGreenEntityList);

        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.CONDITION_ELEMENT_NAME)) {
                StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity = new StrategyConditionBlueGreenEntity();

                Attribute idAttribute = childElement.attribute(XmlConfigConstant.ID_ATTRIBUTE_NAME);
                if (idAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.ID_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String id = idAttribute.getData().toString().trim();
                strategyConditionBlueGreenEntity.setId(id);

                Attribute expressionAttribute = childElement.attribute(XmlConfigConstant.EXPRESSION_ATTRIBUTE_NAME);
                Attribute headerAttribute = childElement.attribute(XmlConfigConstant.HEADER_ATTRIBUTE_NAME);
                if (expressionAttribute != null && headerAttribute != null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.EXPRESSION_ATTRIBUTE_NAME + "] and [" + XmlConfigConstant.HEADER_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] are all configed, only one of them exists");
                }

                if (expressionAttribute != null) {
                    String expression = expressionAttribute.getData().toString().trim();
                    strategyConditionBlueGreenEntity.setExpression(expression);
                }

                if (headerAttribute != null) {
                    String expression = headerAttribute.getData().toString().trim();
                    strategyConditionBlueGreenEntity.setExpression(expression);
                }

                Attribute versionIdAttribute = childElement.attribute(XmlConfigConstant.VERSION_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME);
                if (versionIdAttribute != null) {
                    String versionId = versionIdAttribute.getData().toString().trim();
                    strategyConditionBlueGreenEntity.setVersionId(versionId);
                }

                Attribute regionIdAttribute = childElement.attribute(XmlConfigConstant.REGION_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME);
                if (regionIdAttribute != null) {
                    String regionId = regionIdAttribute.getData().toString().trim();
                    strategyConditionBlueGreenEntity.setRegionId(regionId);
                }

                Attribute addressIdAttribute = childElement.attribute(XmlConfigConstant.ADDRESS_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME);
                if (addressIdAttribute != null) {
                    String addressId = addressIdAttribute.getData().toString().trim();
                    strategyConditionBlueGreenEntity.setAddressId(addressId);
                }

                Attribute versionWeightIdAttribute = childElement.attribute(XmlConfigConstant.VERSION_WEIGHT_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME);
                if (versionWeightIdAttribute != null) {
                    String versionWeightId = versionWeightIdAttribute.getData().toString().trim();
                    strategyConditionBlueGreenEntity.setVersionWeightId(versionWeightId);
                }

                Attribute regionWeightIdAttribute = childElement.attribute(XmlConfigConstant.REGION_WEIGHT_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME);
                if (regionWeightIdAttribute != null) {
                    String regionWeightId = regionWeightIdAttribute.getData().toString().trim();
                    strategyConditionBlueGreenEntity.setRegionWeightId(regionWeightId);
                }

                strategyConditionBlueGreenEntityList.add(strategyConditionBlueGreenEntity);
            }
        }
    }

    private void parseStrategyConditionGray(Element element, StrategyReleaseEntity strategyReleaseEntity) {
        List<StrategyConditionGrayEntity> strategyConditionGrayEntityList = strategyReleaseEntity.getStrategyConditionGrayEntityList();
        if (strategyConditionGrayEntityList != null) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.CONDITIONS_ELEMENT_NAME + "] for attribute[" + XmlConfigConstant.TYPE_ATTRIBUTE_NAME + "]'s value with '" + ConditionType.GRAY + "' to be configed");
        }

        strategyConditionGrayEntityList = new ArrayList<StrategyConditionGrayEntity>();
        strategyReleaseEntity.setStrategyConditionGrayEntityList(strategyConditionGrayEntityList);

        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.CONDITION_ELEMENT_NAME)) {
                StrategyConditionGrayEntity strategyConditionGrayEntity = new StrategyConditionGrayEntity();

                Attribute idAttribute = childElement.attribute(XmlConfigConstant.ID_ATTRIBUTE_NAME);
                if (idAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.ID_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String id = idAttribute.getData().toString().trim();
                strategyConditionGrayEntity.setId(id);

                Attribute expressionAttribute = childElement.attribute(XmlConfigConstant.EXPRESSION_ATTRIBUTE_NAME);
                Attribute headerAttribute = childElement.attribute(XmlConfigConstant.HEADER_ATTRIBUTE_NAME);
                if (expressionAttribute != null && headerAttribute != null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.EXPRESSION_ATTRIBUTE_NAME + "] and [" + XmlConfigConstant.HEADER_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] are all configed, only one of them exists");
                }

                if (expressionAttribute != null) {
                    String expression = expressionAttribute.getData().toString().trim();
                    strategyConditionGrayEntity.setExpression(expression);
                }

                if (headerAttribute != null) {
                    String expression = headerAttribute.getData().toString().trim();
                    strategyConditionGrayEntity.setExpression(expression);
                }

                Attribute versionIdAttribute = childElement.attribute(XmlConfigConstant.VERSION_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME);
                if (versionIdAttribute != null) {
                    String versionId = versionIdAttribute.getData().toString().trim();
                    VersionWeightEntity versionWeightEntity = new VersionWeightEntity();
                    WeightEntityWrapper.parseWeightEntity(versionWeightEntity, versionId);
                    strategyConditionGrayEntity.setVersionWeightEntity(versionWeightEntity);
                }

                Attribute regionIdAttribute = childElement.attribute(XmlConfigConstant.REGION_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME);
                if (regionIdAttribute != null) {
                    String regionId = regionIdAttribute.getData().toString().trim();
                    RegionWeightEntity regionWeightEntity = new RegionWeightEntity();
                    WeightEntityWrapper.parseWeightEntity(regionWeightEntity, regionId);
                    strategyConditionGrayEntity.setRegionWeightEntity(regionWeightEntity);
                }

                Attribute addressIdAttribute = childElement.attribute(XmlConfigConstant.ADDRESS_ELEMENT_NAME + DiscoveryConstant.DASH + XmlConfigConstant.ID_ATTRIBUTE_NAME);
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

    private void parseStrategyRoute(Element element, StrategyReleaseEntity strategyReleaseEntity) {
        List<StrategyRouteEntity> strategyRouteEntityList = strategyReleaseEntity.getStrategyRouteEntityList();
        if (strategyRouteEntityList != null) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.ROUTES_ELEMENT_NAME + "] to be configed");
        }

        strategyRouteEntityList = new ArrayList<StrategyRouteEntity>();
        strategyReleaseEntity.setStrategyRouteEntityList(strategyRouteEntityList);

        for (Iterator<Element> elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Element childElement = elementIterator.next();

            if (StringUtils.equals(childElement.getName(), XmlConfigConstant.ROUTE_ELEMENT_NAME)) {
                StrategyRouteEntity strategyRouteEntity = new StrategyRouteEntity();

                Attribute idAttribute = childElement.attribute(XmlConfigConstant.ID_ATTRIBUTE_NAME);
                if (idAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.ID_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String id = idAttribute.getData().toString().trim();
                strategyRouteEntity.setId(id);

                Attribute typeAttribute = childElement.attribute(XmlConfigConstant.TYPE_ATTRIBUTE_NAME);
                if (typeAttribute == null) {
                    throw new DiscoveryException("Attribute[" + XmlConfigConstant.TYPE_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
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

    @SuppressWarnings("unchecked")
    private void parseStrategyHeader(Element element, StrategyReleaseEntity strategyReleaseEntity) {
        StrategyHeaderEntity strategyHeaderEntity = strategyReleaseEntity.getStrategyHeaderEntity();
        if (strategyHeaderEntity != null) {
            throw new DiscoveryException("Allow only one element[" + XmlConfigConstant.HEADER_ELEMENT_NAME + "] to be configed");
        }

        strategyHeaderEntity = new StrategyHeaderEntity();
        strategyReleaseEntity.setStrategyHeaderEntity(strategyHeaderEntity);

        String headerValue = element.getTextTrim();

        Map<String, String> headerMap = JsonUtil.fromJson(headerValue, Map.class);
        strategyHeaderEntity.getHeaderMap().putAll(headerMap);
    }
}