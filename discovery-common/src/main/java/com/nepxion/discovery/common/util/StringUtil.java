package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
    public static List<String> split(String value, String separate) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        String[] valueArray = StringUtils.split(value, separate);

        return Arrays.asList(valueArray);
    }
}
