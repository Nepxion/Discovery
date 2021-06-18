package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.dom4j.DocumentHelper;

public class XmlUtil {
    public static boolean isXmlFormat(String xml) {
        try {
            DocumentHelper.parseText(xml);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}