package com.nepxion.discovery.common;

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

import com.nepxion.discovery.common.expression.DiscoveryExpressionResolver;
import com.nepxion.discovery.common.expression.DiscoveryTypeComparor;
import com.nepxion.discovery.common.util.StringUtil;

public class DiscoveryExpressionTest {
    public static void main(String[] args) {
        System.out.println(test1());
        System.out.println(test2());
        System.out.println(test3());
        System.out.println(test4());
    }

    private static boolean test1() {
        // String expression = "#H['a'] != 'x' || #H['b'] != 'y'";
        // String expression = "#H['a'] == '123' && #H['b'] == '456'";
        // String expression = "#H['a'] == '123' || #H['b'] == '456'";
        // String expression = "#H['a'] >= '123' && #H['b'] <= '456'";
        // String expression = "#H['a'] >= '123' || #H['b'] <= '456'";
        // String expression = "#H['a'] != '123' || #H['b'] != '456'";
        // String expression = "#H['a'] < '2' && #H['b'] == '3'";
        String expression = "#H['a'] matches '[a-z]{3}2'";
        Map<String, String> map = new HashMap<String, String>();
        // map.put("a", "123");
        // map.put("b", "456");
        map.put("a", "1.2333");
        map.put("b", "y");

        return DiscoveryExpressionResolver.eval(expression, "H", map, new DiscoveryTypeComparor());
    }

    private static List<String> test2() {
        String regex = "\\#H\\['\\S+'\\]";
        Pattern pattern = Pattern.compile(regex);

        String expression = "#H['a-A'] != '123' || #H['b//SS'] != '456' && #H['C**44!!66'] == '123'";
        Matcher matcher = pattern.matcher(expression);

        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            String group = matcher.group();
            String value = StringUtils.substringBetween(group, "#H['", "']");
            list.add(value);
        }

        return list;
    }

    private static List<String> test3() {
        String regex = "\\[\\S+\\]";
        Pattern pattern = Pattern.compile(regex);

        String expression = "[ID=solar-service-a][H=172.27.208.1:3001][V=1.0][R=dev][E=env1][G=solar-group][TID=123][SID=N/A]";
        expression = StringUtils.replace(expression, "][", "] [");
        Matcher matcher = pattern.matcher(expression);

        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            String group = matcher.group();
            String value = StringUtils.substringBetween(group, "[", "]");
            list.add(value);
        }

        return list;
    }

    private static int test4() {
        String expression = "#H['a-A'] != '123' || #H['b//SS'] != '456' && #H['C**44!!66'] == '123'";
        String key = "#H['";

        return StringUtil.count(expression, key);
    }
}