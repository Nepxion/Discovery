package com.nepxion.discovery.common;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.util.UuidUtil;

public class UuidUtilTest {
    public static void main(String[] args) {
        System.out.println("Time UUID with default:");
        System.out.println(UuidUtil.getTimeUUID());
        System.out.println("--------------------");

        System.out.println("Time UUID with prefix:");
        System.out.println(UuidUtil.getTimeUUID("nepxion"));
        System.out.println("--------------------");

        System.out.println("Time UUID with prefix and randomArray:");
        System.out.println(UuidUtil.getTimeUUID("nepxion", new int[] { 9, 99, 999, 9999 }));
        System.out.println("--------------------");

        System.out.println("Time UUID with all:");
        System.out.println(UuidUtil.getTimeUUID("nepxion", "yyyyMMdd-HHmmss", new int[] { 9, 99, 999, 9999 }, "-", "@"));
        System.out.println("--------------------");

        System.out.println("Random:");
        System.out.println(UuidUtil.random(999));
        System.out.println(UuidUtil.random(999, 5));
        System.out.println("--------------------");

        System.out.println("UUID:");
        System.out.println(UuidUtil.getUUID());
    }
}