package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.dom4j.Dom4JReader;

public class XmlUtil {
    public static boolean isXmlFormat(String xml) {
        try {
            Dom4JReader.getDocument(xml);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}