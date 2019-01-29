package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;

import static com.nepxion.discovery.common.constant.DiscoveryConstant.HTTP_PREFIX;
import static com.nepxion.discovery.common.constant.DiscoveryConstant.SLASH;

public class UrlUtil {

    public static String formatUrl(String url) {

        if (StringUtils.isEmpty(url)) {
            return SLASH;
        }

        if (!url.startsWith(HTTP_PREFIX)) {
            url = HTTP_PREFIX + url;
        }

        if (!url.endsWith(SLASH)) {
            url = url + SLASH;
        }

        return url;
    }

    public static String formatContextPath(String contextPath) {
        if (StringUtils.isEmpty(contextPath)) {
            contextPath = SLASH;
        } else {
            if (!contextPath.startsWith(SLASH)) {
                contextPath = SLASH + contextPath;
            }
            if (!contextPath.endsWith(SLASH)) {
                contextPath = contextPath + SLASH;
            }
        }

        return contextPath;
    }
}