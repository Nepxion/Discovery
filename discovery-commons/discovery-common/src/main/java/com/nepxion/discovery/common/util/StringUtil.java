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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
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

        String[] valueArray = StringUtils.splitByWholeSeparator(value, separate);

        return Arrays.asList(valueArray);
    }

    public static String convertToString(List<String> list) {
        return convertToString(list, DiscoveryConstant.SEPARATE);
    }

    public static String convertToString(List<String> list, String separate) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String value = list.get(i);
            stringBuilder.append(value);
            if (i < list.size() - 1) {
                stringBuilder.append(separate);
            }
        }

        return stringBuilder.toString();
    }

    public static Map<String, String> splitToMap(String value) {
        return splitToMap(value, DiscoveryConstant.EQUALS, DiscoveryConstant.SEPARATE);
    }

    public static Map<String, String> splitToMap(String value, String equals, String separate) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        Map<String, String> map = new LinkedHashMap<String, String>();
        String[] separateArray = StringUtils.splitByWholeSeparator(value, separate);
        for (String separateValue : separateArray) {
            String[] equalsArray = StringUtils.splitByWholeSeparator(separateValue, equals);
            map.put(equalsArray[0].trim(), equalsArray[1].trim());
        }

        return map;
    }

    public static Map<String, List<String>> splitToComplexMap(String value) {
        return splitToComplexMap(value, DiscoveryConstant.SEPARATE);
    }

    // Json {"a":"1;2", "b":"3;4"} -> Map {a=[1, 2], b=[3, 4]}
    // String "1;2;3;4"-> Map {undefined=[1, 2, 3, 4]}
    @SuppressWarnings("unchecked")
    public static Map<String, List<String>> splitToComplexMap(String value, String separate) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
        try {
            Map<String, String> jsonMap = JsonUtil.fromJson(value, Map.class);
            for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
                String key = entry.getKey();
                String list = entry.getValue();

                map.put(key, StringUtil.splitToList(list, separate));
            }
        } catch (Exception e) {
            List<String> valueList = StringUtil.splitToList(value, separate);

            map.put(DiscoveryConstant.UNDEFINED, valueList);
        }

        return map;
    }

    public static String convertToString(Map<String, ?> map) {
        return convertToString(map, DiscoveryConstant.EQUALS, DiscoveryConstant.SEPARATE);
    }

    public static String convertToString(Map<String, ?> map, String equals, String separate) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            stringBuilder.append(key + equals + value);

            if (index < map.size() - 1) {
                stringBuilder.append(separate);
            }

            index++;
        }

        return stringBuilder.toString();
    }

    public static String convertToComplexString(Map<String, List<String>> map) {
        return convertToComplexString(map, DiscoveryConstant.SEPARATE);
    }

    // Map {a=[1, 2], b=[3, 4]} -> Json {"a":"1;2", "b":"3;4"}
    // Map {undefined=[1, 2, 3, 4]} -> String "1;2;3;4"
    public static String convertToComplexString(Map<String, List<String>> map, String separate) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }

        if (map.size() == 1 && map.containsKey(DiscoveryConstant.UNDEFINED)) {
            List<String> value = map.get(DiscoveryConstant.UNDEFINED);

            return convertToString(value);
        } else {
            Map<String, String> jsonMap = new LinkedHashMap<String, String>();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();

                jsonMap.put(key, convertToString(value));
            }

            return JsonUtil.toJson(jsonMap);
        }
    }

    public static Map<String, String> convertToMap(List<String> list) {
        return convertToMap(list, DiscoveryConstant.EQUALS);
    }

    public static Map<String, String> convertToMap(List<String> list, String separate) {
        if (list == null) {
            return null;
        }

        Map<String, String> map = new LinkedHashMap<String, String>();
        for (String value : list) {
            String[] valueArray = StringUtils.splitByWholeSeparator(value, separate);
            map.put(valueArray[0], valueArray[1]);
        }

        return map;
    }

    public static String firstToUpperCase(String value) {
        return StringUtils.capitalize(value);
    }

    public static String firstToLowerCase(String value) {
        return StringUtils.uncapitalize(value);
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