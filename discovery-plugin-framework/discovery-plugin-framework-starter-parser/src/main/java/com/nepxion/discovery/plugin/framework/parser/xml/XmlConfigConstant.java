package com.nepxion.discovery.plugin.framework.parser.xml;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public class XmlConfigConstant {
    public static final String RULE_ELEMENT_NAME = "rule";
    public static final String REGISTER_ELEMENT_NAME = "register";
    public static final String DISCOVERY_ELEMENT_NAME = "discovery";
    public static final String STRATEGY_ELEMENT_NAME = "strategy";
    public static final String STRATEGY_CUSTOMIZATION_ELEMENT_NAME = "strategy-customization";
    public static final String STRATEGY_BLACKLIST_ELEMENT_NAME = "strategy-blacklist";
    public static final String PARAMETER_ELEMENT_NAME = "parameter";
    public static final String CONDITIONS_ELEMENT_NAME = "conditions";
    public static final String CONDITION_ELEMENT_NAME = "condition";
    public static final String ROUTES_ELEMENT_NAME = "routes";
    public static final String ROUTE_ELEMENT_NAME = "route";
    public static final String HEADERS_ELEMENT_NAME = "headers";
    public static final String HEADER_ELEMENT_NAME = "header";
    public static final String ID_ELEMENT_NAME = "id";
    public static final String SERVICE_ELEMENT_NAME = "service";
    public static final String BLACKLIST_ELEMENT_NAME = "blacklist";
    public static final String WHITELIST_ELEMENT_NAME = "whitelist";
    public static final String COUNT_ELEMENT_NAME = "count";
    public static final String WEIGHT_ELEMENT_NAME = "weight";
    public static final String VERSION_ELEMENT_NAME = DiscoveryConstant.VERSION;
    public static final String REGION_ELEMENT_NAME = DiscoveryConstant.REGION;
    public static final String ADDRESS_ELEMENT_NAME = DiscoveryConstant.ADDRESS;
    public static final String VERSION_WEIGHT_ELEMENT_NAME = DiscoveryConstant.VERSION_WEIGHT;
    public static final String REGION_WEIGHT_ELEMENT_NAME = DiscoveryConstant.REGION_WEIGHT;

    public static final String FILTER_VALUE_ATTRIBUTE_NAME = "filter-value";
    public static final String SERVICE_NAME_ATTRIBUTE_NAME = "service-name";
    public static final String CONSUMER_SERVICE_NAME_ATTRIBUTE_NAME = "consumer-service-name";
    public static final String PROVIDER_SERVICE_NAME_ATTRIBUTE_NAME = "provider-service-name";
    public static final String CONSUMER_VERSION_VALUE_ATTRIBUTE_NAME = "consumer-version-value";
    public static final String PROVIDER_VERSION_VALUE_ATTRIBUTE_NAME = "provider-version-value";
    public static final String CONSUMER_REGION_VALUE_ATTRIBUTE_NAME = "consumer-region-value";
    public static final String PROVIDER_REGION_VALUE_ATTRIBUTE_NAME = "provider-region-value";
    public static final String PROVIDER_WEIGHT_VALUE_ATTRIBUTE_NAME = "provider-weight-value";
    public static final String KEY_ATTRIBUTE_NAME = "key";
    public static final String VALUE_ATTRIBUTE_NAME = "value";
    public static final String ID_ATTRIBUTE_NAME = "id";
    public static final String HEADER_ATTRIBUTE_NAME = "header";
    public static final String TYPE_ATTRIBUTE_NAME = "type";
}