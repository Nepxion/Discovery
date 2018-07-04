package com.nepxion.discovery.plugin.configcenter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.plugin.configcenter.constant.ConfigConstant;
import com.nepxion.discovery.plugin.configcenter.xml.Dom4JParser;
import com.nepxion.discovery.plugin.framework.config.PluginConfigParser;
import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.entity.CountEntity;
import com.nepxion.discovery.plugin.framework.entity.DiscoveryEntity;
import com.nepxion.discovery.plugin.framework.entity.DiscoveryServiceEntity;
import com.nepxion.discovery.plugin.framework.entity.FilterEntity;
import com.nepxion.discovery.plugin.framework.entity.FilterHolderEntity;
import com.nepxion.discovery.plugin.framework.entity.FilterType;
import com.nepxion.discovery.plugin.framework.entity.RegisterEntity;
import com.nepxion.discovery.plugin.framework.entity.RuleEntity;
import com.nepxion.discovery.plugin.framework.entity.VersionEntity;
import com.nepxion.discovery.plugin.framework.exception.PluginException;

public class ConfigParser extends Dom4JParser implements PluginConfigParser {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigParser.class);

    @Autowired
    private RuleEntity ruleEntity;

    @Autowired
    private ReentrantReadWriteLock reentrantReadWriteLock;

    @Override
    public void parse(InputStream inputStream) {
        try {
            String text = IOUtils.toString(inputStream, PluginConstant.ENCODING_UTF_8);

            super.parse(text);
        } catch (NullPointerException e) {
            LOG.warn("No input stream is retrieved");
        } catch (Exception e) {
            LOG.error("Parse rule xml failed", e);
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected void parseRoot(Element element) {
        LOG.info("Start to parse rule xml...");

        int registerElementCount = element.elements(ConfigConstant.REGISTER_ELEMENT_NAME).size();
        if (registerElementCount > 1) {
            throw new PluginException("Allow only one element[" + ConfigConstant.REGISTER_ELEMENT_NAME + "] to be configed");
        }

        int discoveryElementCount = element.elements(ConfigConstant.DISCOVERY_ELEMENT_NAME).size();
        if (discoveryElementCount > 1) {
            throw new PluginException("Allow only one element[" + ConfigConstant.DISCOVERY_ELEMENT_NAME + "] to be configed");
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

        String text = getText();
        try {
            reentrantReadWriteLock.writeLock().lock();

            ruleEntity.setRegisterEntity(registerEntity);
            ruleEntity.setDiscoveryEntity(discoveryEntity);
            ruleEntity.setContent(text);
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }

        LOG.info("Rule entity=\n{}", ruleEntity);
    }

    @SuppressWarnings("rawtypes")
    private void parseRegister(Element element, RegisterEntity registerEntity) {
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                if (StringUtils.equals(childElement.getName(), ConfigConstant.BLACKLIST_ELEMENT_NAME)) {
                    parseFilter(childElement, ConfigConstant.BLACKLIST_ELEMENT_NAME, registerEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.WHITELIST_ELEMENT_NAME)) {
                    parseFilter(childElement, ConfigConstant.WHITELIST_ELEMENT_NAME, registerEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.COUNT_ELEMENT_NAME)) {
                    parseCount(childElement, registerEntity);
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
                    parseFilter(childElement, ConfigConstant.BLACKLIST_ELEMENT_NAME, discoveryEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.WHITELIST_ELEMENT_NAME)) {
                    parseFilter(childElement, ConfigConstant.WHITELIST_ELEMENT_NAME, discoveryEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.VERSION_ELEMENT_NAME)) {
                    parseVersion(childElement, discoveryEntity);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void parseFilter(Element element, String filterTypeValue, FilterHolderEntity filterHolderEntity) {
        FilterEntity filterEntity = filterHolderEntity.getFilterEntity();
        if (filterEntity != null) {
            throw new PluginException("Allow only one filter element to be configed, [" + ConfigConstant.BLACKLIST_ELEMENT_NAME + "] or [" + ConfigConstant.WHITELIST_ELEMENT_NAME + "]");
        }

        filterEntity = new FilterEntity();
        filterEntity.setFilterType(FilterType.fromString(filterTypeValue));

        Attribute globalFilterAttribute = element.attribute(ConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME);
        if (globalFilterAttribute != null) {
            String globalFilterValue = globalFilterAttribute.getData().toString().trim();
            List<String> globalFilterValueList = parseList(globalFilterValue);
            filterEntity.setFilterValueList(globalFilterValueList);
        }

        Map<String, List<String>> filterMap = filterEntity.getFilterMap();
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
                List<String> filterValueList = null;
                if (filterValueAttribute != null) {
                    String filterValue = filterValueAttribute.getData().toString().trim();
                    filterValueList = parseList(filterValue);
                }
                filterMap.put(serviceName, filterValueList);
            }
        }

        filterHolderEntity.setFilterEntity(filterEntity);
    }

    @SuppressWarnings("rawtypes")
    private void parseCount(Element element, RegisterEntity registerEntity) {
        CountEntity countEntity = registerEntity.getCountEntity();
        if (countEntity != null) {
            throw new PluginException("Allow only one element[" + ConfigConstant.COUNT_ELEMENT_NAME + "] to be configed");
        }

        countEntity = new CountEntity();

        Attribute globalFilterAttribute = element.attribute(ConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME);
        if (globalFilterAttribute != null) {
            String globalFilterValue = globalFilterAttribute.getData().toString().trim();
            if (StringUtils.isNotEmpty(globalFilterValue)) {
                Integer globalValue = null;
                try {
                    globalValue = Integer.valueOf(globalFilterValue);
                } catch (NumberFormatException e) {
                    throw new PluginException("Attribute[" + ConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "] value in element[" + element.getName() + "] is invalid, must be int type", e);
                }
                countEntity.setFilterValue(globalValue);
            }
        }

        Map<String, Integer> filterMap = countEntity.getFilterMap();
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext();) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                Attribute serviceNameAttribute = childElement.attribute(ConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME);
                if (serviceNameAttribute == null) {
                    throw new PluginException("Attribute[" + ConfigConstant.SERVICE_NAME_ATTRIBUTE_NAME + "] in element[" + childElement.getName() + "] is missing");
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
                            throw new PluginException("Attribute[" + ConfigConstant.FILTER_VALUE_ATTRIBUTE_NAME + "] value in element[" + childElement.getName() + "] is invalid, must be int type", e);
                        }
                    }
                }

                filterMap.put(serviceName, value);
            }
        }

        registerEntity.setCountEntity(countEntity);
    }

    @SuppressWarnings("rawtypes")
    private void parseVersion(Element element, DiscoveryEntity discoveryEntity) {
        VersionEntity versionEntity = discoveryEntity.getVersionEntity();
        if (versionEntity != null) {
            throw new PluginException("Allow only one element[" + ConfigConstant.VERSION_ELEMENT_NAME + "] to be configed");
        }

        versionEntity = new VersionEntity();

        Map<String, List<DiscoveryServiceEntity>> serviceEntityMap = versionEntity.getServiceEntityMap();
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
                    List<String> consumerVersionValueList = parseList(consumerVersionValue);
                    serviceEntity.setConsumerVersionValueList(consumerVersionValueList);
                }

                Attribute providerVersionValueAttribute = childElement.attribute(ConfigConstant.PROVIDER_VERSION_VALUE_ATTRIBUTE_NAME);
                if (providerVersionValueAttribute != null) {
                    String providerVersionValue = providerVersionValueAttribute.getData().toString().trim();
                    List<String> providerVersionValueList = parseList(providerVersionValue);
                    serviceEntity.setProviderVersionValueList(providerVersionValueList);
                }

                List<DiscoveryServiceEntity> serviceEntityList = serviceEntityMap.get(consumerServiceName);
                if (serviceEntityList == null) {
                    serviceEntityList = new ArrayList<DiscoveryServiceEntity>();
                    serviceEntityMap.put(consumerServiceName, serviceEntityList);
                }

                serviceEntityList.add(serviceEntity);
            }
        }

        discoveryEntity.setVersionEntity(versionEntity);
    }

    private List<String> parseList(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        String[] valueArray = StringUtils.split(value, PluginConstant.SEPARATE);

        return Arrays.asList(valueArray);
    }
}