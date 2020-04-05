package com.nepxion.discovery.plugin.strategy.skywalking.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StrategySkywalkingTracerResolver {
    private static final Method[] NO_METHODS = new Method[0];
    private static final Field[] NO_FIELDS = new Field[0];
    private static final Map<Class<?>, Method[]> declaredMethodsCache = new ConcurrentHashMap<>(256);
    private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentHashMap<>(256);
    private static final Map<String, AtomicInteger> classLoadFailedTimes = new ConcurrentHashMap<>();

    public static final MethodFilter NON_BRIDGED_METHODS = new MethodFilter() {
        public boolean matches(Method method) {
            return !method.isBridge();
        }
    };

    public static final MethodFilter USER_DECLARED_METHODS = new MethodFilter() {
        public boolean matches(Method method) {
            return !method.isBridge() && method.getDeclaringClass() != Object.class;
        }
    };

    public static final FieldFilter COPYABLE_FIELDS = new FieldFilter() {
        public boolean matches(Field field) {
            return !Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers());
        }
    };

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

    public static void setField(Field field, Object target, Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            handleReflectionException(e);

            throw new IllegalStateException("Unexpected reflection exception - " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static Object getField(Field field, Object target) {
        try {
            field.setAccessible(true);

            return field.get(target);
        } catch (IllegalAccessException e) {
            handleReflectionException(e);

            throw new IllegalStateException("Unexpected reflection exception - " + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static Method findMethod(Class<?> clazz, String name) {
        return findMethod(clazz, name, new Class[0]);
    }

    public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        for (Class<?> searchType = clazz; searchType != null; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.isInterface() ? searchType.getMethods() : getDeclaredMethods(searchType);
            for (int i = 0; i < methods.length; ++i) {
                Method method = methods[i];
                if (name.equals(method.getName()) && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
                    return method;
                }
            }
        }

        return null;
    }

    public static Method findMethodIngoreParam(Class<?> clazz, String name) {
        for (Class<?> searchType = clazz; searchType != null; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.isInterface() ? searchType.getMethods() : getDeclaredMethods(searchType);
            for (int i = 0; i < methods.length; ++i) {
                Method method = methods[i];
                if (name.equals(method.getName())) {
                    return method;
                }
            }
        }

        return null;
    }

    public static Object invokeMethod(Method method, Object target) {
        return invokeMethod(method, target, new Object[0]);
    }

    public static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            return method.invoke(target, args);
        } catch (Exception e) {
            handleReflectionException(e);

            throw new IllegalStateException("Should never get here");
        }
    }

    public static void handleReflectionException(Exception e) {
        if (e instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + e.getMessage());
        } else if (e instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method: " + e.getMessage());
        } else {
            if (e instanceof InvocationTargetException) {
                handleInvocationTargetException((InvocationTargetException) e);
            }
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new UndeclaredThrowableException(e);
            }
        }
    }

    public static void handleInvocationTargetException(InvocationTargetException e) {
        rethrowRuntimeException(e.getTargetException());
    }

    public static void rethrowRuntimeException(Throwable e) {
        if (e instanceof RuntimeException) {
            throw (RuntimeException) e;
        } else if (e instanceof Error) {
            throw (Error) e;
        } else {
            throw new UndeclaredThrowableException(e);
        }
    }

    public static void rethrowException(Throwable e) throws Exception {
        if (e instanceof Exception) {
            throw (Exception) e;
        } else if (e instanceof Error) {
            throw (Error) e;
        } else {
            throw new UndeclaredThrowableException(e);
        }
    }

    public static boolean declaresException(Method method, Class<?> exceptionType) {
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        for (int i = 0; i < exceptionTypes.length; ++i) {
            Class<?> type = exceptionTypes[i];
            if (type.isAssignableFrom(exceptionType)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isPublicStaticFinal(Field field) {
        int modifiers = field.getModifiers();

        return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
    }

    public static boolean isEqualsMethod(Method method) {
        if (method != null && method.getName().equals("equals")) {
            Class<?>[] paramTypes = method.getParameterTypes();

            return paramTypes.length == 1 && paramTypes[0] == Object.class;
        } else {
            return false;
        }
    }

    public static boolean isHashCodeMethod(Method method) {
        return method != null && method.getName().equals("hashCode") && method.getParameterTypes().length == 0;
    }

    public static boolean isToStringMethod(Method method) {
        return method != null && method.getName().equals("toString") && method.getParameterTypes().length == 0;
    }

    public static boolean isObjectMethod(Method method) {
        if (method == null) {
            return false;
        } else {
            try {
                Object.class.getDeclaredMethod(method.getName(), method.getParameterTypes());

                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public static boolean isCglibRenamedMethod(Method method) {
        String name = method.getName();
        if (!name.startsWith("CGLIB$")) {
            return false;
        } else {
            int i;
            for (i = name.length() - 1; i >= 0 && Character.isDigit(name.charAt(i)); --i) {
                ;
            }

            return i > "CGLIB$".length() && i < name.length() - 1 && name.charAt(i) == 36;
        }
    }

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    public static void makeAccessible(Constructor<?> ctor) {
        if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
            ctor.setAccessible(true);
        }
    }

    public static void doWithLocalMethods(Class<?> clazz, MethodCallback methodCallback) {
        Method[] methods = getDeclaredMethods(clazz);
        for (int i = 0; i < methods.length; ++i) {
            Method method = methods[i];
            try {
                methodCallback.doWith(method);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Not allowed to access method '" + method.getName() + "': " + e);
            }
        }
    }

    public static void doWithMethods(Class<?> clazz, MethodCallback methodCallback) {
        doWithMethods(clazz, methodCallback, (MethodFilter) null);
    }

    public static void doWithMethods(Class<?> clazz, MethodCallback methodCallback, MethodFilter methodFilter) {
        Method[] methods = getDeclaredMethods(clazz);
        for (int i = 0; i < methods.length; ++i) {
            Method method = methods[i];
            if (methodFilter == null || methodFilter.matches(method)) {
                try {
                    methodCallback.doWith(method);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Not allowed to access method '" + method.getName() + "': " + e);
                }
            }
        }
        if (clazz.getSuperclass() != null) {
            doWithMethods(clazz.getSuperclass(), methodCallback, methodFilter);
        } else if (clazz.isInterface()) {
            Class<?>[] interfaces = clazz.getInterfaces();
            for (int i = 0; i < interfaces.length; ++i) {
                Class<?> interfaze = interfaces[i];
                doWithMethods(interfaze, methodCallback, methodFilter);
            }
        }
    }

    public static Method[] getAllDeclaredMethods(Class<?> clazz) {
        final ArrayList<Method> methods = new ArrayList<Method>(32);
        doWithMethods(clazz, new MethodCallback() {
            public void doWith(Method method) {
                methods.add(method);
            }
        });

        return methods.toArray(new Method[methods.size()]);
    }

    public static Method[] getUniqueDeclaredMethods(Class<?> clazz) {
        final ArrayList<Method> methods = new ArrayList<Method>(32);
        doWithMethods(clazz, new MethodCallback() {
            public void doWith(Method method) {
                boolean knownSignature = false;
                Method methodBeingOverriddenWithCovariantReturnType = null;
                Iterator<Method> iterator = methods.iterator();
                while (iterator.hasNext()) {
                    Method existingMethod = iterator.next();
                    if (method.getName().equals(existingMethod.getName()) && Arrays.equals(method.getParameterTypes(), existingMethod.getParameterTypes())) {
                        if (existingMethod.getReturnType() != method.getReturnType() && existingMethod.getReturnType().isAssignableFrom(method.getReturnType())) {
                            methodBeingOverriddenWithCovariantReturnType = existingMethod;
                            break;
                        }

                        knownSignature = true;
                        break;
                    }
                }
                if (methodBeingOverriddenWithCovariantReturnType != null) {
                    methods.remove(methodBeingOverriddenWithCovariantReturnType);
                }
                if (!knownSignature && !isCglibRenamedMethod(method)) {
                    methods.add(method);
                }
            }
        });

        return methods.toArray(new Method[methods.size()]);
    }

    private static Method[] getDeclaredMethods(Class<?> clazz) {
        Method[] result = declaredMethodsCache.get(clazz);
        if (result == null) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            List<?> defaultMethods = findConcreteMethodsOnInterfaces(clazz);
            if (defaultMethods != null) {
                result = new Method[declaredMethods.length + defaultMethods.size()];
                System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
                int index = declaredMethods.length;
                for (Iterator<?> iterator = defaultMethods.iterator(); iterator.hasNext(); ++index) {
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

    private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
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

    public static void doWithLocalFields(Class<?> clazz, FieldCallback fieldCallback) {
        Field[] fields = getDeclaredFields(clazz);
        for (int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            try {
                fieldCallback.doWith(field);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + e);
            }
        }
    }

    public static void doWithFields(Class<?> clazz, FieldCallback fieldCallback) {
        doWithFields(clazz, fieldCallback, (FieldFilter) null);
    }

    public static void doWithFields(Class<?> clazz, FieldCallback fieldCallback, FieldFilter fieldFilter) {
        do {
            Field[] fields = getDeclaredFields(clazz);
            for (int i = 0; i < fields.length; ++i) {
                Field field = fields[i];
                if (fieldFilter == null || fieldFilter.matches(field)) {
                    try {
                        fieldCallback.doWith(field);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("Not allowed to access field '" + field.getName() + "': " + e);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null && clazz != Object.class);
    }

    private static Field[] getDeclaredFields(Class<?> clazz) {
        Field[] result = (Field[]) declaredFieldsCache.get(clazz);
        if (result == null) {
            result = clazz.getDeclaredFields();
            declaredFieldsCache.put(clazz, result.length == 0 ? NO_FIELDS : result);
        }

        return result;
    }

    public static void shallowCopyFieldState(final Object src, final Object dest) {
        if (!src.getClass().isAssignableFrom(dest.getClass())) {
            throw new IllegalArgumentException("Destination class [" + dest.getClass().getName() + "] must be same or subclass as source class [" + src.getClass().getName() + "]");
        } else {
            doWithFields(src.getClass(), new FieldCallback() {
                public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                    makeAccessible(field);
                    Object srcValue = field.get(src);
                    field.set(dest, srcValue);
                }
            }, COPYABLE_FIELDS);
        }
    }

    public static void clearCache() {
        declaredMethodsCache.clear();
        declaredFieldsCache.clear();
    }

    public interface FieldFilter {
        boolean matches(Field field);
    }

    public interface FieldCallback {
        void doWith(Field method) throws IllegalArgumentException, IllegalAccessException;
    }

    public interface MethodFilter {
        boolean matches(Method method);
    }

    public interface MethodCallback {
        void doWith(Method method) throws IllegalArgumentException, IllegalAccessException;
    }

    public static <T> T getFieldValue(Object object, String fieldName, Class<T> clazzT) {
        return getFieldValueWithSuper(object, fieldName, 0, clazzT);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFieldValueWithSuper(Object object, String fieldName, int superLevel, Class<T> clazzT) {
        Class<?> clazz = object.getClass();
        while (superLevel > 0) {
            clazz = clazz.getSuperclass();
        }
        Field field = findField(clazz, fieldName);
        field.setAccessible(true);
        Object result = getField(field, object);
        if (result == null) {
            return null;
        } else {
            return (T) result;
        }
    }

    public static Object invokeStaticMethod(String className, String name, Object... params) {
        try {
            if (classLoadFailedTimes.get(className) != null) {
                if (classLoadFailedTimes.get(className).get() > 20) {
                    return null;
                }
            }
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            Method method = findMethodIngoreParam(clazz, name);
            method.setAccessible(true);

            return method.invoke(null, params);
        } catch (ClassNotFoundException e) {
            if (classLoadFailedTimes.get(className) == null) {
                classLoadFailedTimes.put(className, new AtomicInteger(1));
            } else {
                classLoadFailedTimes.get(className).getAndIncrement();
            }
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}