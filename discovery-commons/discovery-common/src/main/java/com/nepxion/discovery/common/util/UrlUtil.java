package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;

public class UrlUtil {
    public static String formatUrl(String url) {
        if (!url.endsWith("/")) {
            url = url + "/";
        }

        return url;
    }

    public static String formatContextPath(String contextPath) {
        if (StringUtils.isEmpty(contextPath)) {
            return "/";
        } else {
            if (!contextPath.startsWith("/")) {
                contextPath = "/" + contextPath;
            }
            if (!contextPath.endsWith("/")) {
                contextPath = contextPath + "/";
            }
        }

        return contextPath;
    }
}