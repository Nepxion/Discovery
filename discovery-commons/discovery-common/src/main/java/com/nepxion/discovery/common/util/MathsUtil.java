package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Aquarius</p>
 * <p>Description: Nepxion Aquarius</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;

public class MathsUtil {
    private static final char ASTERISK = '*';

    public static Long calculate(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        long result = 1;
        try {
            String[] array = StringUtils.split(value, ASTERISK);
            for (String data : array) {
                result *= Long.parseLong(data.trim());
            }
        } catch (Exception e) {
            return null;
        }

        return result;
    }
}