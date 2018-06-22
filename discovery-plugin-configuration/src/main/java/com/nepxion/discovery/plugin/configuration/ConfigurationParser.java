package com.nepxion.discovery.plugin.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

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
import com.nepxion.discovery.plugin.core.entity.ConsumerEntity;
import com.nepxion.discovery.plugin.core.entity.DiscoveryEntity;
import com.nepxion.discovery.plugin.core.entity.FilterEntity;
import com.nepxion.discovery.plugin.core.entity.FilterType;
import com.nepxion.discovery.plugin.core.entity.VersionEntity;
import com.nepxion.discovery.plugin.core.exception.PluginException;

public class ConfigurationParser extends Dom4JParser {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationParser.class);

    @Autowired
    private DiscoveryEntity discoveryEntity;

    @Autowired
    private ReentrantReadWriteLock reentrantReadWriteLock;

    @SuppressWarnings("rawtypes")
    @Override
    protected void parseRoot(Element element) {
        LOG.info("Start to parse xml...");

        int filterElementCount = element.elements(ConfigurationConstant.FILTER_ELEMENT_NAME).size();
        if (filterElementCount > 1) {
            throw new PluginException("The count of element[" + ConfigurationConstant.FILTER_ELEMENT_NAME + "] can't be more than 1");
        }

        int versionElementCount = element.elements(ConfigurationConstant.VERSION_ELEMENT_NAME).size();
        if (versionElementCount > 1) {
            throw new PluginException("The count of element[" + ConfigurationConstant.VERSION_ELEMENT_NAME + "] can't be more than 1");
        }

        FilterEntity filterEntity = new FilterEntity();
        VersionEntity versionEntity = new VersionEntity();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigurationConstant.FILTER_ELEMENT_NAME)) {
                    parseFilter(childElement, filterEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigurationConstant.VERSION_ELEMENT_NAME)) {
                    parseVersion(childElement, versionEntity);
                }
            }
        }

        try {
            reentrantReadWriteLock.writeLock().lock();

            discoveryEntity.setFilterEntity(filterEntity);
            discoveryEntity.setVersionEntity(versionEntity);
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }

        LOG.info("Discovery entity is {}", discoveryEntity);
    }

    @SuppressWarnings("rawtypes")
    private void parseFilter(Element element, FilterEntity filterEntity) {
        Attribute filterTypeAttribute = element.attribute(ConfigurationConstant.FILTER_TYPE_ATTRIBUTE_NAME);
        if (filterTypeAttribute == null) {
            throw new PluginException("Attribute[" + ConfigurationConstant.FILTER_TYPE_ATTRIBUTE_NAME + "] in element[" + element.getName() + "] is missing");
        }
        String filterType = filterTypeAttribute.getData().toString().trim();
        filterEntity.setFilterType(FilterType.fromString(filterType));

        Attribute globalFilterAttribute = element.attribute(ConfigurationConstant.FILTER_VALUE_ATTRIBUTE_NAME);
        if (globalFilterAttribute != null) {
            String globalFilterValue = globalFilterAttribute.getData().toString().trim();
            filterEntity.setFilterValue(globalFilterValue);
        }

        Map<String, String> filterMap = filterEntity.getFilterMap();

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
    private void parseVersion(Element element, VersionEntity versionEntity) {
        List<ConsumerEntity> consumerEntityList = versionEntity.getConsumerEntityList();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                ConsumerEntity consumerEntity = new ConsumerEntity();
                Attribute serviceNameAttribute = childElement.attribute(ConfigurationConstant.SERVICE_NAME_ATTRIBUTE_NAME);
                if (serviceNameAttribute == null) {
                    throw new PluginException("Attribute[" + ConfigurationConstant.SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String serviceName = serviceNameAttribute.getData().toString().trim();
                consumerEntity.setServiceName(serviceName);

                parseConsumer(childElement, consumerEntity);

                consumerEntityList.add(consumerEntity);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void parseConsumer(Element element, ConsumerEntity consumerEntity) {
        Map<String, String> providerMap = consumerEntity.getProviderMap();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                Attribute serviceNameAttribute = childElement.attribute(ConfigurationConstant.SERVICE_NAME_ATTRIBUTE_NAME);
                if (serviceNameAttribute == null) {
                    throw new PluginException("Attribute[" + ConfigurationConstant.SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String serviceName = serviceNameAttribute.getData().toString().trim();

                Attribute versionValueAttribute = childElement.attribute(ConfigurationConstant.VERSION_VALUE_NAME_ATTRIBUTE_NAME);
                if (versionValueAttribute == null) {
                    throw new PluginException("Attribute[" + ConfigurationConstant.VERSION_VALUE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String versionValue = versionValueAttribute.getData().toString().trim();

                providerMap.put(serviceName, versionValue);
            }
        }
    }
}