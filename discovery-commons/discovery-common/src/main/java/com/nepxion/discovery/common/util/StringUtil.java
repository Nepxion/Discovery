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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public class StringUtil {
    public static List<String> splitToList(String value) {
        return splitToList(value, DiscoveryConstant.SEPARATE);
    }

    public static List<String> splitToList(String value, String separate) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        String[] valueArray = StringUtils.split(value, separate);

        return Arrays.asList(valueArray);
    }

    public static Map<String, String> splitToMap(String value) {
        return splitToMap(value, DiscoveryConstant.SEPARATE, DiscoveryConstant.EQUALS);
    }

    public static Map<String, String> splitToMap(String value, String separate, String equals) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        Map<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotEmpty(value)) {
            String[] separateArray = StringUtils.split(value, separate);
            for (String separateValue : separateArray) {
                String[] equalsArray = StringUtils.split(separateValue, equals);
                map.put(equalsArray[0].trim(), equalsArray[1].trim());
            }
        }

        return map;
    }

    public static String convertToString(List<String> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < list.size(); i++) {
                String value = list.get(i);

                stringBuilder.append(value);
                if (i < list.size() - 1) {
                    stringBuilder.append(DiscoveryConstant.SEPARATE);
                }
            }

            return stringBuilder.toString();
        }

        return null;
    }

    public static String simulateText(String value, int size, String padValue) {
        return StringUtils.rightPad(value, size, padValue);
    }

    public static String simulateText(int size) {
        return simulateText("10", size, "10");
    }

    public static String toDisplaySize(String value) {
        return FileUtils.byteCountToDisplaySize(value.length());
    }

    public static int count(String text, String keyText) {
        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(keyText)) {
            return -1;
        }

        int count = 0;
        while (text.indexOf(keyText) != -1) {
            text = text.substring(text.indexOf(keyText) + 1, text.length());
            count++;
        }

        return count;
    }
}