package com.nepxion.discovery.plugin.configcenter.parser.xml;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.ConditionType;
import com.nepxion.discovery.common.entity.StrategyConditionBlueGreenEntity;
import com.nepxion.discovery.common.entity.StrategyCustomizationEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.common.entity.StrategyRouteEntity;
import com.nepxion.discovery.common.entity.StrategyRouteType;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.configcenter.constant.ConfigConstant;
import com.nepxion.discovery.plugin.framework.config.PluginConfigParser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author zlliu
 * @date 2020/10/22 13:16
 */
public class XmlConfigParserExt extends XmlConfigParser implements PluginConfigParser {
    /**
     * json 字符串格式：{"gateway-client":"1.0"}
     */
    private Pattern jsonPattern = Pattern.compile("\\{\\S+}");

    /**
     * 适配支持strategy节点version等配置：
     * <version>
     *     {"gateway-client":"1.0","gateway-client2":"1.0"}
     * </version>
     * 为：
     * <version>
     *     <gateway-client>1.0</gateway-client>
     *     <gateway-client2>1.0</gateway-client2>
     * </version>
     * @param element xml节点
     * @param strategyEntity    strategy节点对象
     */
    @Override
    public void parseStrategy(Element element, StrategyEntity strategyEntity) {
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

        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext(); ) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;

                List elementList = childElement.elements();
                String elementValue = StringUtils.EMPTY;
                if (CollectionUtils.isNotEmpty(elementList)) {
                    Map<String, String> versionMap = new HashMap<>(16);
                    for (Object versionEl : elementList) {
                        versionMap.put(((Element) versionEl).getName(), ((Element) versionEl).getTextTrim());
                    }
                    elementValue = JsonUtil.toJson(versionMap);
                } else if (StringUtils.isNotBlank(childElement.getTextTrim())) {
                    if (jsonPattern.matcher(childElement.getTextTrim()).matches()) {
                        elementValue = childElement.getTextTrim();
                    }
                }
                if (StringUtils.equals(childElement.getName(), ConfigConstant.VERSION_ELEMENT_NAME)) {
                    strategyEntity.setVersionValue(elementValue);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.REGION_ELEMENT_NAME)) {
                    strategyEntity.setRegionValue(elementValue);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.ADDRESS_ELEMENT_NAME)) {
                    strategyEntity.setAddressValue(elementValue);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.VERSION_WEIGHT_ELEMENT_NAME)) {
                    strategyEntity.setVersionWeightValue(elementValue);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.REGION_WEIGHT_ELEMENT_NAME)) {
                    strategyEntity.setRegionWeightValue(elementValue);
                }
            }
        }
    }

    /**
     * 扩展 <strategy-customization>的conditions节点：
     * 如果没有指定type，缺省blue-green模式
     * @param element
     * @param strategyCustomizationEntity
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void parseStrategyCustomization(Element element, StrategyCustomizationEntity strategyCustomizationEntity) {
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext(); ) {
            Object childElementObject = elementIterator.next();
            if (childElementObject instanceof Element) {
                Element childElement = (Element) childElementObject;
                if (StringUtils.equals(childElement.getName(), ConfigConstant.CONDITIONS)) {
                    Attribute typeAttribute = childElement.attribute(ConfigConstant.TYPE_ATTRIBUTE_NAME);
                    if (typeAttribute != null) {
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
                    } else {
                        //  如果没有指定type，缺省blue-green模式
                        parseStrategyConditionBlueGreen(childElement, strategyCustomizationEntity);
                    }
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.ROUTES_ELEMENT_NAME)) {
                    parseStrategyRoute(childElement, strategyCustomizationEntity);
                } else if (StringUtils.equals(childElement.getName(), ConfigConstant.HEADERS_ELEMENT_NAME)) {
                    parseStrategyHeader(childElement, strategyCustomizationEntity);
                }
            }
        }
    }

    /**
     * 扩展blue-green模式的condition：可以没有header属性
     * @param element
     * @param strategyCustomizationEntity
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void parseStrategyConditionBlueGreen(Element element, StrategyCustomizationEntity strategyCustomizationEntity) {
        List<StrategyConditionBlueGreenEntity> strategyConditionBlueGreenEntityList = strategyCustomizationEntity.getStrategyConditionBlueGreenEntityList();
        if (strategyConditionBlueGreenEntityList != null) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.CONDITIONS + "] for attribute[" + ConfigConstant.TYPE_ATTRIBUTE_NAME + "]'s value with '" + ConditionType.BLUE_GREEN + "' to be configed");
        }
        strategyConditionBlueGreenEntityList = new ArrayList<>();
        strategyCustomizationEntity.setStrategyConditionBlueGreenEntityList(strategyConditionBlueGreenEntityList);
        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext(); ) {
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
                    String header = headerAttribute == null ? null : headerAttribute.getData().toString().trim();
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
                    //  param节点
                    Attribute paramAttribute = childElement.attribute("param");
                    if (paramAttribute != null) {
                        String param = paramAttribute.getData().toString().trim();
                        strategyConditionBlueGreenEntity.setParam(param);
                    }
                    //  body 节点
                    Attribute bodyAttribute = childElement.attribute("body");
                    if (bodyAttribute != null) {
                        String body = bodyAttribute.getData().toString().trim();
                        strategyConditionBlueGreenEntity.setBody(body);
                    }

                    strategyConditionBlueGreenEntityList.add(strategyConditionBlueGreenEntity);
                }
            }
        }
    }

    /**
     * 适配支持route节点的配置：{"gateway-client":"1.0"} --> <gateway-client>1.0</gateway-client>
     * @param element
     * @param strategyCustomizationEntity
     */
    @Override
    public void parseStrategyRoute(Element element, StrategyCustomizationEntity strategyCustomizationEntity) {
        List<StrategyRouteEntity> strategyRouteEntityList = strategyCustomizationEntity.getStrategyRouteEntityList();
        if (strategyRouteEntityList != null) {
            throw new DiscoveryException("Allow only one element[" + ConfigConstant.ROUTES_ELEMENT_NAME + "] to be configed");
        }

        strategyRouteEntityList = new ArrayList<>();
        strategyCustomizationEntity.setStrategyRouteEntityList(strategyRouteEntityList);

        for (Iterator elementIterator = element.elementIterator(); elementIterator.hasNext(); ) {
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
                    StrategyRouteType strategyType = StrategyRouteType.fromString(type);
                    strategyRouteEntity.setType(strategyType);

                    List elementList = childElement.elements();
                    String elementValue = StringUtils.EMPTY;
                    if (CollectionUtils.isNotEmpty(elementList)) {
                        Map<String, String> versionMap = new HashMap<>(16);
                        for (Object versionEl : elementList) {
                            versionMap.put(((Element) versionEl).getName(), ((Element) versionEl).getTextTrim());
                        }
                        elementValue = JsonUtil.toJson(versionMap);
                    } else if (StringUtils.isNotBlank(childElement.getTextTrim())) {
                        if (jsonPattern.matcher(childElement.getTextTrim()).matches()) {
                            elementValue = childElement.getTextTrim();
                        }
                    }
                    strategyRouteEntity.setValue(elementValue);

                    strategyRouteEntityList.add(strategyRouteEntity);
                }
            }
        }
    }

}