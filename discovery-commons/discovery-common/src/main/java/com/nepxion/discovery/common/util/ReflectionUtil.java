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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectionUtil {
    private static Map<String, Method> methodCache = new ConcurrentHashMap<String, Method>();
    private static Map<String, Field> fieldCache = new ConcurrentHashMap<String, Field>();

    public static Object invoke(Class<?> clazz, Object instance, String name, Class<?>[] parameterTypes, Object[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String key = clazz.getName() + name;

        Method method = methodCache.get(key);
        if (method == null) {
            method = clazz.getMethod(name, parameterTypes);

            methodCache.put(key, method);
        }

        return method.invoke(instance, args);
    }

    public static Object getValue(Class<?> clazz, Object instance, String name) throws NoSuchFieldException, IllegalAccessException {
        String key = clazz.getName() + name;

        Field field = fieldCache.get(key);
        if (field == null) {
            field = clazz.getDeclaredField(name);
            field.setAccessible(true);

            fieldCache.put(key, field);
        }

        return field.get(instance);
    }
}