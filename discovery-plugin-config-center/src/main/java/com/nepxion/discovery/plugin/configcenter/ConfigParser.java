package com.nepxion.discovery.plugin.configcenter;

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
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.configcenter.constant.ConfigConstant;
import com.nepxion.discovery.plugin.configcenter.xml.Dom4JParser;
import com.nepxion.discovery.plugin.framework.entity.DiscoveryEntity;
import com.nepxion.discovery.plugin.framework.entity.DiscoveryServiceEntity;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.entity.RegisterEntity;
import com.nepxion.discovery.plugin.framework.entity.RegisterFilterType;
import com.nepxion.discovery.plugin.framework.exception.PluginException;

public class ConfigParser extends Dom4JParser {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigParser.class);

    @Autowired
    private RuleEntity ruleEntity;

    @Autowired
    private ReentrantReadWriteLock reentrantReadWriteLock;

    @SuppressWarnings("rawtypes")
    @Override
    protected void parseRoot(Element element) {
        LOG.info("Start to parse plugin xml...");

        int registerElementCount = element.elements(ConfigConstant.REGISTER_ELEMENT_NAME).size();
        if (registerElementCount > 1) {
            throw new PluginException("The count of element[" + ConfigConstant.REGISTER_ELEMENT_NAME + "] can't be more than 1");
        }

        int discoveryElementCount = element.elements(ConfigConstant.DISCOVERY_ELEMENT_NAME).size();
        if (discoveryElementCount > 1) {
            throw new PluginException("The count of element[" + ConfigConstant.DISCOVERY_ELEMENT_NAME + "] can't be more than 1");
        }

        RegisterEntity registerEntity = null;
        DiscoveryEntity discoveryEntity = null;
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
                }
            }
        }

        try {
            reentrantReadWriteLock.writeLock().lock();

            ruleEntity.setRegisterEntity(registerEntity);
            ruleEntity.setDiscoveryEntity(discoveryEntity);
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }

        LOG.info("Rule entity is {}", ruleEntity);
    }

    @SuppressWarnings("rawtypes")
    private void parseRegister(Element element, RegisterEntity registerEntity) {
        Attribute filterTypeAttribute = element.attribute(ConfigConstant.FILTER_TYPE_ATTRIBUTE_NAME);
        if (filterTypeAttribute == null) {
            throw new PluginException("Attribute[" + ConfigConstant.FILTER_TYPE_ATTRIBUTE_NAME + "] in element[" + element.getName() + "] is missing");
        }
        String filterType = filterTypeAttribute.getData().toString().trim();
        registerEntity.setFilterType(RegisterFilterType.fromString(filterType));

        Attribute globalFilterAttribute = element.attribute(ConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME);
        if (globalFilterAttribute != null) {
            String globalFilterValue = globalFilterAttribute.getData().toString().trim();
            registerEntity.setFilterValue(globalFilterValue);
        }

        Map<String, String> filterMap = registerEntity.getFilterMap();

        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                Attribute serviceNameAttribute = childElement.attribute(ConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME);
                if (serviceNameAttribute == null) {
                    throw new PluginException("Attribute[" + ConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String serviceName = serviceNameAttribute.getData().toString().trim();

                Attribute filterValueAttribute = childElement.attribute(ConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME);
                String filterValue = null;
                if (filterValueAttribute != null) {
                    filterValue = filterValueAttribute.getData().toString().trim();
                }
                filterMap.put(serviceName, filterValue);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void parseDiscovery(Element element, DiscoveryEntity discoveryEntity) {
        Map<String, List<DiscoveryServiceEntity>> serviceEntityMap = discoveryEntity.getServiceEntityMap();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                DiscoveryServiceEntity serviceEntity = new DiscoveryServiceEntity();

                Attribute consumerServiceNameAttribute = childElement.attribute(ConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME);
                if (consumerServiceNameAttribute == null) {
                    throw new PluginException("Attribute[" + ConfigConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String consumerServiceName = consumerServiceNameAttribute.getData().toString().trim();
                serviceEntity.setConsumerServiceName(consumerServiceName);

                Attribute providerServiceNameAttribute = childElement.attribute(ConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME);
                if (providerServiceNameAttribute == null) {
                    throw new PluginException("Attribute[" + ConfigConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String providerServiceName = providerServiceNameAttribute.getData().toString().trim();
                serviceEntity.setProviderServiceName(providerServiceName);

                Attribute consumerVersionValueAttribute = childElement.attribute(ConfigConstant.CONSUMER_VERSION_VALUE_ATTRIBUTE_NAME);
                if (consumerVersionValueAttribute != null) {
                    String consumerVersionValue = consumerVersionValueAttribute.getData().toString().trim();
                    serviceEntity.setConsumerVersionValue(consumerVersionValue);
                }

                Attribute providerVersionValueAttribute = childElement.attribute(ConfigConstant.PROVIDER_VERSION_VALUE_ATTRIBUTE_NAME);
                if (providerVersionValueAttribute != null) {
                    String providerVersionValue = providerVersionValueAttribute.getData().toString().trim();
                    serviceEntity.setProviderVersionValue(providerVersionValue);
                }

                List<DiscoveryServiceEntity> serviceEntityList = serviceEntityMap.get(consumerServiceName);
                if (serviceEntityList == null) {
                    serviceEntityList = new ArrayList<DiscoveryServiceEntity>();
                    serviceEntityMap.put(consumerServiceName, serviceEntityList);
                }

                serviceEntityList.add(serviceEntity);
            }
        }
    }
}