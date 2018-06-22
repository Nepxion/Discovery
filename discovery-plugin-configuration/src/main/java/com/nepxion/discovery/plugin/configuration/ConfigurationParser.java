package com.nepxion.discovery.plugin.configuration;

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

import com.nepxion.discovery.plugin.configuration.constant.ConfigurationConstant;
import com.nepxion.discovery.plugin.configuration.xml.Dom4JParser;
import com.nepxion.discovery.plugin.core.entity.DiscoveryEntity;
import com.nepxion.discovery.plugin.core.entity.DiscoveryServiceEntity;
import com.nepxion.discovery.plugin.core.entity.PluginEntity;
import com.nepxion.discovery.plugin.core.entity.RegisterEntity;
import com.nepxion.discovery.plugin.core.entity.RegisterFilterType;
import com.nepxion.discovery.plugin.core.exception.PluginException;

public class ConfigurationParser extends Dom4JParser {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationParser.class);

    @Autowired
    private PluginEntity pluginEntity;

    @Autowired
    private ReentrantReadWriteLock reentrantReadWriteLock;

    @SuppressWarnings("rawtypes")
    @Override
    protected void parseRoot(Element element) {
        LOG.info("Start to parse plugin xml...");

        int registerElementCount = element.elements(ConfigurationConstant.REGISTER_ELEMENT_NAME).size();
        if (registerElementCount > 1) {
            throw new PluginException("The count of element[" + ConfigurationConstant.REGISTER_ELEMENT_NAME + "] can't be more than 1");
        }

        int discoveryElementCount = element.elements(ConfigurationConstant.DISCOVERY_ELEMENT_NAME).size();
        if (discoveryElementCount > 1) {
            throw new PluginException("The count of element[" + ConfigurationConstant.DISCOVERY_ELEMENT_NAME + "] can't be more than 1");
        }

        RegisterEntity registerEntity = new RegisterEntity();
        DiscoveryEntity discoveryEntity = new DiscoveryEntity();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigurationConstant.REGISTER_ELEMENT_NAME)) {
                    parseRegister(childElement, registerEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigurationConstant.DISCOVERY_ELEMENT_NAME)) {
                    parseDiscovery(childElement, discoveryEntity);
                }
            }
        }

        try {
            reentrantReadWriteLock.writeLock().lock();

            pluginEntity.setRegisterEntity(registerEntity);
            pluginEntity.setDiscoveryEntity(discoveryEntity);
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }

        LOG.info("Plugin entity is {}", pluginEntity);
    }

    @SuppressWarnings("rawtypes")
    private void parseRegister(Element element, RegisterEntity registerEntity) {
        Attribute filterTypeAttribute = element.attribute(ConfigurationConstant.FILTER_TYPE_ATTRIBUTE_NAME);
        if (filterTypeAttribute == null) {
            throw new PluginException("Attribute[" + ConfigurationConstant.FILTER_TYPE_ATTRIBUTE_NAME + "] in element[" + element.getName() + "] is missing");
        }
        String filterType = filterTypeAttribute.getData().toString().trim();
        registerEntity.setFilterType(RegisterFilterType.fromString(filterType));

        Attribute globalFilterAttribute = element.attribute(ConfigurationConstant.FILTER_VALUE_ATTRIBUTE_NAME);
        if (globalFilterAttribute != null) {
            String globalFilterValue = globalFilterAttribute.getData().toString().trim();
            registerEntity.setFilterValue(globalFilterValue);
        }

        Map<String, String> filterMap = registerEntity.getFilterMap();

        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                Attribute serviceNameAttribute = childElement.attribute(ConfigurationConstant.SERVICE_NAME_ATTRIBUTE_NAME);
                if (serviceNameAttribute == null) {
                    throw new PluginException("Attribute[" + ConfigurationConstant.SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String serviceName = serviceNameAttribute.getData().toString().trim();

                Attribute filterValueAttribute = childElement.attribute(ConfigurationConstant.FILTER_VALUE_ATTRIBUTE_NAME);
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

                Attribute consumerServiceNameAttribute = childElement.attribute(ConfigurationConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME);
                if (consumerServiceNameAttribute == null) {
                    throw new PluginException("Attribute[" + ConfigurationConstant.CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String consumerServiceName = consumerServiceNameAttribute.getData().toString().trim();
                serviceEntity.setConsumerServiceName(consumerServiceName);

                Attribute providerServiceNameAttribute = childElement.attribute(ConfigurationConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME);
                if (providerServiceNameAttribute == null) {
                    throw new PluginException("Attribute[" + ConfigurationConstant.PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String providerServiceName = providerServiceNameAttribute.getData().toString().trim();
                serviceEntity.setProviderServiceName(providerServiceName);

                Attribute consumerVersionValueAttribute = childElement.attribute(ConfigurationConstant.CONSUMER_VERSION_VALUE_ATTRIBUTE_NAME);
                if (consumerVersionValueAttribute != null) {
                    String consumerVersionValue = consumerVersionValueAttribute.getData().toString().trim();
                    serviceEntity.setConsumerVersionValue(consumerVersionValue);
                }

                Attribute providerVersionValueAttribute = childElement.attribute(ConfigurationConstant.PROVIDER_VERSION_VALUE_ATTRIBUTE_NAME);
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