package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectionUtil {
    private static Map<String, Field> fieldCache = new ConcurrentHashMap<String, Field>();

    public static Object getValue(Class<?> clazz, Object instance, String name) throws NoSuchFieldException, IllegalAccessException {
        String key = clazz.getName() + name;

        Field cacheField = fieldCache.get(key);
        if (cacheField == null) {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);

            fieldCache.put(key, field);
            cacheField = field;
        }

        return cacheField.get(instance);
    }
}