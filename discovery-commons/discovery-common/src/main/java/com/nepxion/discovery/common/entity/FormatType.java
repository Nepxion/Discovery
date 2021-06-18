package com.nepxion.discovery.common.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public enum FormatType {
    XML_FORMAT(DiscoveryConstant.XML_FORMAT),
    JSON_FORMAT(DiscoveryConstant.JSON_FORMAT),
    YAML_FORMAT(DiscoveryConstant.YAML_FORMAT),
    PROPERTIES_FORMAT(DiscoveryConstant.PROPERTIES_FORMAT),
    HTML_FORMAT(DiscoveryConstant.HTML_FORMAT),
    TEXT_FORMAT(DiscoveryConstant.TEXT_FORMAT);

    private String value;

    private FormatType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FormatType fromString(String value) {
        for (FormatType type : FormatType.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("No matched type with value=" + value);
    }

    @Override
    public String toString() {
        return value;
    }
}