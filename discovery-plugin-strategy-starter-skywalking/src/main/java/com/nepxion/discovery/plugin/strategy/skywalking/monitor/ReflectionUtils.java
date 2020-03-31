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

public abstract class ReflectionUtils {
    private static final Method[] NO_METHODS = new Method[0];
    private static final Field[] NO_FIELDS = new Field[0];
    private static final Map<Class<?>, Method[]> declaredMethodsCache = new ConcurrentHashMap<>(256);
    private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentHashMap<>(256);
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

    public ReflectionUtils() {
    }

    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, (Class) null);
    }

    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        //        Assert.notNull(clazz, "Class must not be null");
        //        Assert.isTrue(name != null || type != null, "Either name or type of the field must be specified");

        for (Class searchType = clazz; Object.class != searchType && searchType != null; searchType = searchType.getSuperclass()) {
            Field[] fields = getDeclaredFields(searchType);
            Field[] var5 = fields;
            int var6 = fields.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Field field = var5[var7];
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
        } catch (IllegalAccessException var4) {
            handleReflectionException(var4);
            throw new IllegalStateException("Unexpected reflection exception - " + var4.getClass().getName() + ": " + var4.getMessage());
        }
    }

    public static Object getField(Field field, Object target) {
        try {
            field.setAccessible(true);
            return field.get(target);
        } catch (IllegalAccessException var3) {
            handleReflectionException(var3);
            throw new IllegalStateException("Unexpected reflection exception - " + var3.getClass().getName() + ": " + var3.getMessage());
        }
    }

    public static Method findMethod(Class<?> clazz, String name) {
        return findMethod(clazz, name, new Class[0]);
    }

    public static Method findMethod(Class<?> clazz, String name, Class... paramTypes) {
        //        Assert.notNull(clazz, "Class must not be null");
        //        Assert.notNull(name, "Method name must not be null");

        for (Class searchType = clazz; searchType != null; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.isInterface() ? searchType.getMethods() : getDeclaredMethods(searchType);
            Method[] var5 = methods;
            int var6 = methods.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Method method = var5[var7];
                if (name.equals(method.getName()) && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
                    return method;
                }
            }
        }

        return null;
    }

    public static Method findMethodIngoreParam(Class<?> clazz, String name) {
        for (Class searchType = clazz; searchType != null; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.isInterface() ? searchType.getMethods() : getDeclaredMethods(searchType);
            Method[] var5 = methods;
            int var6 = methods.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Method method = var5[var7];
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
        } catch (Exception var4) {
            handleReflectionException(var4);
            throw new IllegalStateException("Should never get here");
        }
    }

    //    public static Object invokeJdbcMethod(Method method, Object target) throws SQLException {
    //        return invokeJdbcMethod(method, target, new Object[0]);
    //    }
    //
    //    public static Object invokeJdbcMethod(Method method, Object target, Object... args) throws SQLException {
    //        try {
    //            return method.invoke(target, args);
    //        } catch (IllegalAccessException var4) {
    //            handleReflectionException(var4);
    //        } catch (InvocationTargetException var5) {
    //            if(var5.getTargetException() instanceof SQLException) {
    //                throw (SQLException)var5.getTargetException();
    //            }
    //
    //            handleInvocationTargetException(var5);
    //        }
    //
    //        throw new IllegalStateException("Should never get here");
    //    }

    public static void handleReflectionException(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        } else if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method: " + ex.getMessage());
        } else {
            if (ex instanceof InvocationTargetException) {
                handleInvocationTargetException((InvocationTargetException) ex);
            }

            if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new UndeclaredThrowableException(ex);
            }
        }
    }

    public static void handleInvocationTargetException(InvocationTargetException ex) {
        rethrowRuntimeException(ex.getTargetException());
    }

    public static void rethrowRuntimeException(Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        } else if (ex instanceof Error) {
            throw (Error) ex;
        } else {
            throw new UndeclaredThrowableException(ex);
        }
    }

    public static void rethrowException(Throwable ex) throws Exception {
        if (ex instanceof Exception) {
            throw (Exception) ex;
        } else if (ex instanceof Error) {
            throw (Error) ex;
        } else {
            throw new UndeclaredThrowableException(ex);
        }
    }

    public static boolean declaresException(Method method, Class<?> exceptionType) {
        //        Assert.notNull(method, "Method must not be null");
        Class[] declaredExceptions = method.getExceptionTypes();
        Class[] var3 = declaredExceptions;
        int var4 = declaredExceptions.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Class declaredException = var3[var5];
            if (declaredException.isAssignableFrom(exceptionType)) {
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
            Class[] paramTypes = method.getParameterTypes();
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
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static boolean isCglibRenamedMethod(Method renamedMethod) {
        String name = renamedMethod.getName();
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

    public static void doWithLocalMethods(Class<?> clazz, MethodCallback mc) {
        Method[] methods = getDeclaredMethods(clazz);
        Method[] var3 = methods;
        int var4 = methods.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];

            try {
                mc.doWith(method);
            } catch (IllegalAccessException var8) {
                throw new IllegalStateException("Not allowed to access method \'" + method.getName() + "\': " + var8);
            }
        }

    }

    public static void doWithMethods(Class<?> clazz, MethodCallback mc) {
        doWithMethods(clazz, mc, (MethodFilter) null);
    }

    public static void doWithMethods(Class<?> clazz, MethodCallback mc, MethodFilter mf) {
        Method[] methods = getDeclaredMethods(clazz);
        Method[] var4 = methods;
        int var5 = methods.length;

        int var6;
        for (var6 = 0; var6 < var5; ++var6) {
            Method superIfc = var4[var6];
            if (mf == null || mf.matches(superIfc)) {
                try {
                    mc.doWith(superIfc);
                } catch (IllegalAccessException var9) {
                    throw new IllegalStateException("Not allowed to access method \'" + superIfc.getName() + "\': " + var9);
                }
            }
        }

        if (clazz.getSuperclass() != null) {
            doWithMethods(clazz.getSuperclass(), mc, mf);
        } else if (clazz.isInterface()) {
            Class[] var10 = clazz.getInterfaces();
            var5 = var10.length;

            for (var6 = 0; var6 < var5; ++var6) {
                Class var11 = var10[var6];
                doWithMethods(var11, mc, mf);
            }
        }

    }

    public static Method[] getAllDeclaredMethods(Class<?> leafClass) {
        final ArrayList methods = new ArrayList(32);
        doWithMethods(leafClass, new MethodCallback() {
            public void doWith(Method method) {
                methods.add(method);
            }
        });
        return (Method[]) methods.toArray(new Method[methods.size()]);
    }

    public static Method[] getUniqueDeclaredMethods(Class<?> leafClass) {
        final ArrayList methods = new ArrayList(32);
        doWithMethods(leafClass, new MethodCallback() {
            public void doWith(Method method) {
                boolean knownSignature = false;
                Method methodBeingOverriddenWithCovariantReturnType = null;
                Iterator var4 = methods.iterator();

                while (var4.hasNext()) {
                    Method existingMethod = (Method) var4.next();
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

                if (!knownSignature && !ReflectionUtils.isCglibRenamedMethod(method)) {
                    methods.add(method);
                }

            }
        });
        return (Method[]) methods.toArray(new Method[methods.size()]);
    }

    private static Method[] getDeclaredMethods(Class<?> clazz) {
        //        Assert.notNull(clazz, "Class must not be null");
        Method[] result = (Method[]) declaredMethodsCache.get(clazz);
        if (result == null) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            List defaultMethods = findConcreteMethodsOnInterfaces(clazz);
            if (defaultMethods != null) {
                result = new Method[declaredMethods.length + defaultMethods.size()];
                System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
                int index = declaredMethods.length;

                for (Iterator var5 = defaultMethods.iterator(); var5.hasNext(); ++index) {
                    Method defaultMethod = (Method) var5.next();
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
        ArrayList result = null;
        Class[] var2 = clazz.getInterfaces();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Class ifc = var2[var4];
            Method[] var6 = ifc.getMethods();
            int var7 = var6.length;

            for (int var8 = 0; var8 < var7; ++var8) {
                Method ifcMethod = var6[var8];
                if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
                    if (result == null) {
                        result = new ArrayList();
                    }

                    result.add(ifcMethod);
                }
            }
        }

        return result;
    }

    public static void doWithLocalFields(Class<?> clazz, FieldCallback fc) {
        Field[] var2 = getDeclaredFields(clazz);
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Field field = var2[var4];

            try {
                fc.doWith(field);
            } catch (IllegalAccessException var7) {
                throw new IllegalStateException("Not allowed to access field \'" + field.getName() + "\': " + var7);
            }
        }

    }

    public static void doWithFields(Class<?> clazz, FieldCallback fc) {
        doWithFields(clazz, fc, (FieldFilter) null);
    }

    public static void doWithFields(Class<?> clazz, FieldCallback fc, FieldFilter ff) {
        Class targetClass = clazz;

        do {
            Field[] fields = getDeclaredFields(targetClass);
            Field[] var5 = fields;
            int var6 = fields.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Field field = var5[var7];
                if (ff == null || ff.matches(field)) {
                    try {
                        fc.doWith(field);
                    } catch (IllegalAccessException var10) {
                        throw new IllegalStateException("Not allowed to access field \'" + field.getName() + "\': " + var10);
                    }
                }
            }

            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);

    }

    private static Field[] getDeclaredFields(Class<?> clazz) {
        //        Assert.notNull(clazz, "Class must not be null");
        Field[] result = (Field[]) declaredFieldsCache.get(clazz);
        if (result == null) {
            result = clazz.getDeclaredFields();
            declaredFieldsCache.put(clazz, result.length == 0 ? NO_FIELDS : result);
        }

        return result;
    }

    public static void shallowCopyFieldState(final Object src, final Object dest) {
        //        Assert.notNull(src, "Source for field copy cannot be null");
        //        Assert.notNull(dest, "Destination for field copy cannot be null");
        if (!src.getClass().isAssignableFrom(dest.getClass())) {
            throw new IllegalArgumentException("Destination class [" + dest.getClass().getName() + "] must be same or subclass as source class [" + src.getClass().getName() + "]");
        } else {
            doWithFields(src.getClass(), new FieldCallback() {
                public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                    ReflectionUtils.makeAccessible(field);
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
        boolean matches(Field var1);
    }

    public interface FieldCallback {
        void doWith(Field var1) throws IllegalArgumentException, IllegalAccessException;
    }

    public interface MethodFilter {
        boolean matches(Method var1);
    }

    public interface MethodCallback {
        void doWith(Method var1) throws IllegalArgumentException, IllegalAccessException;
    }

    public static <T> T getFieldValue(Object object, String fieldName, Class<T> clazzT) {
        return getFieldValueWithSuper(object, fieldName, 0, clazzT);
    }

    public static <T> T getFieldValueWithSuper(Object object, String fieldName, int superLevel, Class<T> clazzT) {
        Class clazz = object.getClass();
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

    public static Map<String, AtomicInteger> classLoadFailedTimes = new ConcurrentHashMap<>();

    public static Object invokeStaticMethod(String className, String name, Object... params) {
        try {
            if (classLoadFailedTimes.get(className) != null) {
                if (classLoadFailedTimes.get(className).get() > 20) {
                    return null;
                }
            }
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
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