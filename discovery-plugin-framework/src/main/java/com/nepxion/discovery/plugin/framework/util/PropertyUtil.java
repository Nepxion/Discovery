package com.nepxion.discovery.plugin.framework.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PropertyUtil {
    public static void setDefaultProperty(ConfigurableApplicationContext applicationContext, String key, String value) {
        if (applicationContext instanceof AnnotationConfigApplicationContext) {
            return;
        }

        String property = applicationContext.getEnvironment().getProperty(key);
        if (StringUtils.isEmpty(property)) {
            System.setProperty(key, value);
        }
    }
}