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

import com.nepxion.discovery.common.util.StringUtil;

public class StringUtilTest {
    public static void main(String[] args) {
        test1();
        test2();
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
}