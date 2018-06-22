package com.nepxion.discovery.plugin.config;

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
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.constant.DiscoveryPluginConstant;
import com.nepxion.discovery.plugin.entity.ConsumerEntity;
import com.nepxion.discovery.plugin.entity.DiscoveryEntity;
import com.nepxion.discovery.plugin.entity.FilterEntity;
import com.nepxion.discovery.plugin.entity.FilterType;
import com.nepxion.discovery.plugin.entity.VersionEntity;
import com.nepxion.discovery.plugin.exception.DiscoveryPluginException;
import com.nepxion.discovery.plugin.xml.Dom4JParser;

public class DiscoveryPluginConfigParser extends Dom4JParser {
    private static final Logger LOG = LoggerFactory.getLogger(DiscoveryPluginConfigParser.class);

    @Autowired
    private DiscoveryEntity discoveryEntity;

    @Autowired
    private ReentrantLock reentrantLock;

    @SuppressWarnings("rawtypes")
    @Override
    protected void parseRoot(Element element) {
        LOG.info("Start to parse discovery.xml...");

        int filterElementCount = element.elements(DiscoveryPluginConstant.FILTER_ELEMENT_NAME).size();
        if (filterElementCount > 1) {
            throw new DiscoveryPluginException("The count of element[" + DiscoveryPluginConstant.FILTER_ELEMENT_NAME + "] can't be more than 1");
        }

        int versionElementCount = element.elements(DiscoveryPluginConstant.VERSION_ELEMENT_NAME).size();
        if (versionElementCount > 1) {
            throw new DiscoveryPluginException("The count of element[" + DiscoveryPluginConstant.VERSION_ELEMENT_NAME + "] can't be more than 1");
        }

        FilterEntity filterEntity = new FilterEntity();
        VersionEntity versionEntity = new VersionEntity();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), DiscoveryPluginConstant.FILTER_ELEMENT_NAME)) {
                    parseFilter(childElement, filterEntity);
                } else if (StringUtils.equals(childElement.getName(), DiscoveryPluginConstant.VERSION_ELEMENT_NAME)) {
                    parseVersion(childElement, versionEntity);
                }
            }
        }

        try {
            reentrantLock.lock();

            discoveryEntity.setFilterEntity(filterEntity);
            discoveryEntity.setVersionEntity(versionEntity);
        } finally {
            reentrantLock.unlock();
        }

        LOG.info("Discovery entity is {}", discoveryEntity);
    }

    @SuppressWarnings("rawtypes")
    private void parseFilter(Element element, FilterEntity filterEntity) {
        Attribute filterTypeAttribute = element.attribute(DiscoveryPluginConstant.FILTER_TYPE_ATTRIBUTE_NAME);
        if (filterTypeAttribute == null) {
            throw new DiscoveryPluginException("Attribute[" + DiscoveryPluginConstant.FILTER_TYPE_ATTRIBUTE_NAME + "] in element[" + element.getName() + "] is missing");
        }
        String filterType = filterTypeAttribute.getData().toString().trim();
        filterEntity.setFilterType(FilterType.fromString(filterType));

        Attribute globalFilterAttribute = element.attribute(DiscoveryPluginConstant.FILTER_VALUE_ATTRIBUTE_NAME);
        if (globalFilterAttribute != null) {
            String globalFilterValue = globalFilterAttribute.getData().toString().trim();
            filterEntity.setFilterValue(globalFilterValue);
        }

        Map<String, String> filterMap = filterEntity.getFilterMap();

        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                Attribute serviceNameAttribute = childElement.attribute(DiscoveryPluginConstant.SERVICE_NAME_ATTRIBUTE_NAME);
                if (serviceNameAttribute == null) {
                    throw new DiscoveryPluginException("Attribute[" + DiscoveryPluginConstant.SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String serviceName = serviceNameAttribute.getData().toString().trim();

                Attribute filterValueAttribute = childElement.attribute(DiscoveryPluginConstant.FILTER_VALUE_ATTRIBUTE_NAME);
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
                Attribute serviceNameAttribute = childElement.attribute(DiscoveryPluginConstant.SERVICE_NAME_ATTRIBUTE_NAME);
                if (serviceNameAttribute == null) {
                    throw new DiscoveryPluginException("Attribute[" + DiscoveryPluginConstant.SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
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

                Attribute serviceNameAttribute = childElement.attribute(DiscoveryPluginConstant.SERVICE_NAME_ATTRIBUTE_NAME);
                if (serviceNameAttribute == null) {
                    throw new DiscoveryPluginException("Attribute[" + DiscoveryPluginConstant.SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String serviceName = serviceNameAttribute.getData().toString().trim();

                Attribute versionValueAttribute = childElement.attribute(DiscoveryPluginConstant.VERSION_VALUE_NAME_ATTRIBUTE_NAME);
                if (versionValueAttribute == null) {
                    throw new DiscoveryPluginException("Attribute[" + DiscoveryPluginConstant.VERSION_VALUE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
                }
                String versionValue = versionValueAttribute.getData().toString().trim();

                providerMap.put(serviceName, versionValue);
            }
        }
    }
}