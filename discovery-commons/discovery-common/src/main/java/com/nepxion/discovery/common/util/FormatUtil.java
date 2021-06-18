package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.entity.FormatType;

public class FormatUtil {
    public static FormatType getFormatType(String content) {
        if (XmlUtil.isXmlFormat(content)) {
            return FormatType.XML_FORMAT;
        } else if (JsonUtil.isJsonFormat(content)) {
            return FormatType.JSON_FORMAT;
        /*} else if (YamlUtil.isYamlFormat(content)) {
            return FormatType.YAML_FORMAT;
        } else if (PropertiesUtil.isPropertiesFormat(content)) {
            return FormatType.PROPERTIES_FORMAT;*/
        } else {
            return FormatType.TEXT_FORMAT;
        }
    }
}