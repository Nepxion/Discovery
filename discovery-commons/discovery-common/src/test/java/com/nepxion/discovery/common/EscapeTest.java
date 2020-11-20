package com.nepxion.discovery.common;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.entity.EscapeType;

public class EscapeTest {
    public static void main(String[] args) {
        String value = "#H['a'] == '1' && #H['b'] <= '2' && #H['c'] != '3'";

        value = EscapeType.escape(value, true);
        System.out.println(value);

        value = EscapeType.escape(value, false);
        System.out.println(value);
    }
}
