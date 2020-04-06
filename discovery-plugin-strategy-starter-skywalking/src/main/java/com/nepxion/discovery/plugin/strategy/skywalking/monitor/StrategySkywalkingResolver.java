package com.nepxion.discovery.plugin.strategy.skywalking.monitor;

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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StrategySkywalkingResolver {
    private static final Method[] NO_METHODS = new Method[0];
    private static final Field[] NO_FIELDS = new Field[0];
    private static final Map<Class<?>, Method[]> declaredMethodsCache = new ConcurrentHashMap<>(256);
    private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentHashMap<>(256);
    private static final Map<String, AtomicInteger> classLoadFailedTimes = new ConcurrentHashMap<>();

    public static String createTraceId() throws Exception {
        if (System.getProperties().get("skywalking.agent.service_name") == null) {
            throw new IllegalArgumentException("skywalking.agent.service_name isn't set");
        }

        Object traceId = invokeStaticMethod("org.apache.skywalking.apm.agent.core.context.ContextManager", "getGlobalTraceId");
        if (traceId == null) {
            throw new IllegalArgumentException("ContextManager.getGlobalTraceId() returns null");
        }

        return traceId.toString();
    }

    public static String createSpanId() throws Exception {
        if (System.getProperties().get("skywalking.agent.service_name") == null) {
            throw new IllegalArgumentException("skywalking.agent.service_name isn't set");
        }

        Object traceContext = invokeStaticMethod("org.apache.skywalking.apm.agent.core.context.ContextManager", "get");
        if (traceContext == null) {
            throw new IllegalArgumentException("ContextManager.get() returns null");
        }

        String segmentId = null;
        try {
            Field fieldSegment = findField(traceContext.getClass(), "segment");
            Object segment = getField(fieldSegment, traceContext);
            Field fieldSegmentId = findField(segment.getClass(), "traceSegmentId");
            segmentId = getField(fieldSegmentId, segment).toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("Find SegmentId failed", e);
        }

        return segmentId;
    }

    public static Object invokeStaticMethod(String className, String name, Object... parameters) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        try {
            if (classLoadFailedTimes.get(className) != null) {
                if (classLoadFailedTimes.get(className).get() > 20) {
                    return null;
                }
            }
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            Method method = findMethodIngoreParameters(clazz, name);
            method.setAccessible(true);

            return method.invoke(null, parameters);
        } catch (ClassNotFoundException e) {
            if (classLoadFailedTimes.get(className) == null) {
                classLoadFailedTimes.put(className, new AtomicInteger(1));
            } else {
                classLoadFailedTimes.get(className).getAndIncrement();
            }

            throw e;
        }
    }

    // 方法反射区
    public static Method findMethod(Class<?> clazz, String name) {
        return findMethod(clazz, name, new Class[0]);
    }

    public static Method findMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        for (Class<?> searchClass = clazz; searchClass != null; searchClass = searchClass.getSuperclass()) {
            Method[] methods = searchClass.isInterface() ? searchClass.getMethods() : getDeclaredMethods(searchClass);
            for (int i = 0; i < methods.length; ++i) {
                Method method = methods[i];
                if (name.equals(method.getName()) && (parameterTypes == null || Arrays.equals(parameterTypes, method.getParameterTypes()))) {
                    return method;
                }
            }
        }

        return null;
    }

    public static Method findMethodIngoreParameters(Class<?> clazz, String name) {
        for (Class<?> searchClass = clazz; searchClass != null; searchClass = searchClass.getSuperclass()) {
            Method[] methods = searchClass.isInterface() ? searchClass.getMethods() : getDeclaredMethods(searchClass);
            for (int i = 0; i < methods.length; ++i) {
                Method method = methods[i];
                if (name.equals(method.getName())) {
                    return method;
                }
            }
        }

        return null;
    }

    public static Method[] getDeclaredMethods(Class<?> clazz) {
        Method[] result = declaredMethodsCache.get(clazz);
        if (result == null) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            List<Method> defaultMethods = findConcreteMethodsOnInterfaces(clazz);
            if (defaultMethods != null) {
                result = new Method[declaredMethods.length + defaultMethods.size()];
                System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
                int index = declaredMethods.length;
                for (Iterator<Method> iterator = defaultMethods.iterator(); iterator.hasNext(); ++index) {
                    Method defaultMethod = (Method) iterator.next();
                    result[index] = defaultMethod;
                }
            } else {
                result = declaredMethods;
            }
            declaredMethodsCache.put(clazz, result.length == 0 ? NO_METHODS : result);
        }

        return result;
    }

    public static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
        ArrayList<Method> result = null;
        Class<?>[] interfaces = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            Class<?> interfaze = interfaces[i];
            Method[] methods = interfaze.getMethods();
            for (int j = 0; j < methods.length; ++j) {
                Method method = methods[j];
                if (!Modifier.isAbstract(method.getModifiers())) {
                    if (result == null) {
                        result = new ArrayList<Method>();
                    }
                    result.add(method);
                }
            }
        }

        return result;
    }

    // 参数反射区
    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, (Class<?>) null);
    }

    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        for (Class<?> searchType = clazz; Object.class != searchType && searchType != null; searchType = searchType.getSuperclass()) {
            Field[] fields = getDeclaredFields(searchType);
            for (int i = 0; i < fields.length; ++i) {
                Field field = fields[i];
                if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
        }

        return null;
    }

    public static void setField(Field field, Object object, Object value) throws IllegalArgumentException, IllegalAccessException {
        field.set(object, value);
    }

    public static Object getField(Field field, Object object) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);

        return field.get(object);
    }

    public static Field[] getDeclaredFields(Class<?> clazz) {
        Field[] result = declaredFieldsCache.get(clazz);
        if (result == null) {
            result = clazz.getDeclaredFields();
            declaredFieldsCache.put(clazz, result.length == 0 ? NO_FIELDS : result);
        }

        return result;
    }

    public static void clearCache() {
        declaredMethodsCache.clear();
        declaredFieldsCache.clear();
    }
}