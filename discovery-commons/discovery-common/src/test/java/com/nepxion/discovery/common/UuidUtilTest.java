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
        System.out.println(UuidUtil.getTimeUUID());

        System.out.println(UuidUtil.getTimeUUID("nepxion"));

        System.out.println(UuidUtil.getTimeUUID("nepxion", new int[] { 10, 100, 1000, 10000 }));

        System.out.println(UuidUtil.getTimeUUID("nepxion", "yyyyMMdd-HHmmss", new int[] { 10, 100, 1000, 10000 }, "-", "@"));

        System.out.println(UuidUtil.random(999, 3));

        System.out.println(UuidUtil.getUUID());
    }
}