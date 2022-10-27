package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class PluginInfoUtil {
    private static Pattern pattern = Pattern.compile("\\[\\S+\\]");

    public static List<Map<String, String>> assembleAll(String text, String filters) {
        List<String> filterList = StringUtil.splitToList(filters, ",");

        return assembleAll(text, filterList);
    }

    public static List<Map<String, String>> assembleAll(String text, List<String> filterList) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        List<String> singleList = StringUtil.splitToList(text, " -> ");
        for (String value : singleList) {
            Map<String, String> map = assembleSingle(value, filterList);

            list.add(map);
        }

        return list;
    }

    public static Map<String, String> assembleSingle(String text, String filters) {
        List<String> filterList = StringUtil.splitToList(filters, ",");

        return assembleSingle(text, filterList);
    }

    public static Map<String, String> assembleSingle(String text, List<String> filterList) {
        text = StringUtils.replace(text, "][", "] [");
        Matcher matcher = pattern.matcher(text);

        Map<String, String> map = new LinkedHashMap<String, String>();
        while (matcher.find()) {
            String group = matcher.group();
            String value = StringUtils.substringBetween(group, "[", "]");
            String[] array = StringUtils.split(value, "=");
            if (CollectionUtils.isEmpty(filterList) || filterList.contains(array[0])) {
                map.put(array[0], array[1]);
            }
        }

        return map;
    }

    public static String extractAll(String text, String filters) {
        List<String> filterList = StringUtil.splitToList(filters, ",");

        return extractAll(text, filterList);
    }

    public static String extractAll(String text, List<String> filterList) {
        if (CollectionUtils.isEmpty(filterList)) {
            return text;
        }

        StringBuilder stringBuilder = new StringBuilder();

        List<Map<String, String>> list = assembleAll(text, filterList);
        for (Map<String, String> map : list) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                stringBuilder.append("[").append(key).append("=").append(value).append("]");
            }
            stringBuilder.append(" -> ");
        }

        stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length());

        return stringBuilder.toString();
    }

    public static String substringSingle(String text, String keyword) {
        if (text.contains("[" + keyword + "=")) {
            String value = text.substring(text.indexOf("[" + keyword + "=") + keyword.length() + 2);

            return value.substring(0, value.indexOf("]"));
        }

        return null;
    }
}