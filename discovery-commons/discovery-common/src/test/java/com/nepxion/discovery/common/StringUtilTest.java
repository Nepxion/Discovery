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
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.StringUtil;

public class StringUtilTest {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    private static void test1() {
        System.out.println(StringUtil.splitToList(""));
        System.out.println(StringUtil.splitToList(null));

        System.out.println(StringUtil.convertToString(new ArrayList<String>()));
        System.out.println(StringUtil.convertToString((List<String>) null));

        List<String> list = StringUtil.splitToList("a->b->c", "->");
        System.out.println(list);

        String value = StringUtil.convertToString(list, "->");
        System.out.println(value);
    }

    private static void test2() {
        System.out.println(StringUtil.splitToMap(""));
        System.out.println(StringUtil.splitToMap(null));

        System.out.println(StringUtil.convertToString(new HashMap<String, String>()));
        System.out.println(StringUtil.convertToString((Map<String, String>) null));

        Map<String, String> map = StringUtil.splitToMap("a==1&&b==2", "==", "&&");
        System.out.println(map);

        String value = StringUtil.convertToString(map, "==", "&&");
        System.out.println(value);
    }

    private static void test3() {
        Map<String, List<String>> map1 = new LinkedHashMap<String, List<String>>();
        map1.put("a", Arrays.asList("1", "2"));
        map1.put("b", Arrays.asList("3", "4"));

        String value1 = StringUtil.convertToComplexString(map1);

        System.out.println(value1);
        System.out.println(StringUtil.splitToComplexMap(value1));

        Map<String, List<String>> map2 = new LinkedHashMap<String, List<String>>();
        map2.put(DiscoveryConstant.UNDEFINED, Arrays.asList("1", "2"));

        String value2 = StringUtil.convertToComplexString(map2);

        System.out.println(value2);
        System.out.println(StringUtil.splitToComplexMap(value2));
    }
}