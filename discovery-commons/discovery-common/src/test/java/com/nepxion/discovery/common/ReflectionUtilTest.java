package com.nepxion.discovery.common;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.util.ReflectionUtil;

public class ReflectionUtilTest {
    public static class User {
        private String name = "zhangsan";

        public String getName(String type1, int type2) {
            if (type1.equals("a") && type2 == 0) {
                return "lisi";
            } else {
                return name;
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(ReflectionUtil.invoke(User.class, new User(), "getName", new Class[] { String.class, int.class }, new Object[] { "a", 0 }));
            System.out.println(ReflectionUtil.getValue(User.class, new User(), "name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}