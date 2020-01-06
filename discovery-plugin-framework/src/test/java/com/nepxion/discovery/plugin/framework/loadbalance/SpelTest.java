package com.nepxion.discovery.plugin.framework.loadbalance;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.framework.util.SpelUtil;

public class SpelTest {
    public static void main(String[] args) {
        System.out.println(test1());
        System.out.println(test2());
        System.out.println(test3());
    }

    private static boolean test1() {
        // String expression = "#H['a'] == '123' && #H['b'] == '456'";
        // String expression = "#H['a'] == '123' || #H['b'] == '456'";
        // String expression = "#H['a'] >= '123' && #H['b'] <= '456'";
        // String expression = "#H['a'] >= '123' || #H['b'] <= '456'";
        String expression = "#H['a'] != '123' || #H['b'] != '456'";

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("a", "123");
        headerMap.put("b", "456");

        return SpelUtil.eval(expression, "H", headerMap);
    }

    private static List<String> test2() {
        String regex = "\\#H\\['\\S+'\\]";
        Pattern pattern = Pattern.compile(regex);

        String expression = "#H['a-A'] != '123' || #H['b//SS'] != '456' && #H['C**44!!66'] = 123";
        Matcher matcher = pattern.matcher(expression);

        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            String group = matcher.group();
            String value = StringUtils.substringBetween(group, "#H['", "']");
            list.add(value);
        }

        return list;
    }

    private static int test3() {
        String expression = "#H['a-A'] != '123' || #H['b//SS'] != '456' && #H['C**44!!66'] = 123";
        String key = "#H['";

        return StringUtil.count(expression, key);
    }
}