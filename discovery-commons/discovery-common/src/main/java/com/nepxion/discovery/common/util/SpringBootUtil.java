package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;

public class SpringBootUtil {
    public static List<String> getBasePackages(ApplicationContext applicationContext) {
        return AutoConfigurationPackages.get(applicationContext);
    }

    public static String convertBasePackages(ApplicationContext applicationContext) {
        List<String> basePackages = getBasePackages(applicationContext);

        return StringUtil.convertToString(basePackages);
    }
}