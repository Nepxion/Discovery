package com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;

import java.lang.reflect.Method;
import java.security.ProtectionDomain;

import com.nepxion.discovery.plugin.strategy.starter.agent.async.AsyncContextAccessor;
import com.nepxion.discovery.plugin.strategy.starter.agent.callback.TransformCallback;
import com.nepxion.discovery.plugin.strategy.starter.agent.callback.TransformTemplate;
import com.nepxion.discovery.plugin.strategy.starter.agent.logger.AgentLogger;
import com.nepxion.discovery.plugin.strategy.starter.agent.matcher.InterfaceMatcher;
import com.nepxion.discovery.plugin.strategy.starter.agent.matcher.MatcherFactory;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.Plugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.util.ClassInfo;
import com.nepxion.discovery.plugin.strategy.starter.agent.util.StringUtil;

public class ThreadPlugin extends Plugin {
    private static final AgentLogger LOG = AgentLogger.getLogger(ThreadPlugin.class.getName());

    public ThreadPlugin(TransformTemplate transformTemplate) {
        super(transformTemplate);
    }

    @Override
    public void install() {
        String threadScanPackages = System.getProperty(ThreadConstant.THREAD_SCAN_PACKAGES);
        if (StringUtil.isEmpty(threadScanPackages)) {
            LOG.warn(String.format("Thread scan packages (%s) is null, ignore thread context switch", ThreadConstant.THREAD_SCAN_PACKAGES));

            return;
        }
        LOG.info(String.format("Trace (%s) Runnable/Callable for thread context switch", threadScanPackages));
        InterfaceMatcher runnableInterfaceMatcher = MatcherFactory.newPackageBasedMatcher(threadScanPackages, ThreadConstant.RUNNABLE_CLASS_NAME);
        InterfaceMatcher callableInterfaceMatcher = MatcherFactory.newPackageBasedMatcher(threadScanPackages, ThreadConstant.CALLABLE_CLASS_NAME);

        transformTemplate.transform(runnableInterfaceMatcher, new RunnableTransformCallback());
        transformTemplate.transform(callableInterfaceMatcher, new CallableTransformCallback());
        LOG.info(String.format("%s install successfully", this.getClass().getSimpleName()));
    }

    public static class RunnableTransformCallback implements TransformCallback {
        @Override
        public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
            try {
                ClassInfo classInfo = new ClassInfo(className, classfileBuffer, classLoader);
                CtClass ctClass = classInfo.getCtClass();

                addField(ctClass, AsyncContextAccessor.class);

                CtConstructor[] declaredConstructors = ctClass.getDeclaredConstructors();
                for (CtConstructor ctConstructor : declaredConstructors) {
                    String before = "com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread.interceptor.ThreadConstructorInterceptor.before(this);\n";
                    ctConstructor.insertAfter(before);
                }

                CtMethod ctMethod = ctClass.getDeclaredMethod("run");
                if (null != ctMethod) {
                    ctMethod.insertBefore("com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread.interceptor.ThreadCallInterceptor.before(this);\n");
                    ctMethod.insertAfter("com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread.interceptor.ThreadCallInterceptor.after(this);\n");
                }

                return ctClass.toBytecode();
            } catch (Exception e) {
                LOG.warn(String.format("Transform %s error,message:", className), e);

                return null;
            }
        }
    }

    public static void addField(CtClass ctClass, Class<?> clazz) {
        try {
            ClassPool aDefault = ClassPool.getDefault();
            CtClass makeInterface = aDefault.makeInterface(clazz.getName());
            ctClass.addInterface(makeInterface);

            Method[] methods = clazz.getDeclaredMethods();
            if (methods.length != 2) {
                throw new RuntimeException("accessorType has to declare 2 methods. " + clazz.getName() + " has " + methods.length + ".");
            }

            Method getter;
            Method setter;

            if (methods[0].getParameterTypes().length == 0) {
                getter = methods[0];
                setter = methods[1];
            } else {
                getter = methods[1];
                setter = methods[0];
            }

            Class<?> fieldType = getter.getReturnType();
            String fieldName = fieldType.getSimpleName().toLowerCase();
            String field = String.format("private %s %s;", fieldType.getName(), fieldName);

            CtField f1 = CtField.make(field, ctClass);
            ctClass.addField(f1);

            String getMethod = String.format("public %s %s(){return %s;}", fieldType.getName(), getter.getName(), fieldName);
            String setMethod = String.format("public void %s(%s %s){this.%s = %s;}", setter.getName(), fieldType.getName(), fieldName, fieldName, fieldName);

            CtMethod m1 = CtMethod.make(getMethod, ctClass);
            CtMethod m2 = CtMethod.make(setMethod, ctClass);
            ctClass.addMethod(m1);
            ctClass.addMethod(m2);
        } catch (Exception e) {
            LOG.warn(String.format("Add field %s error, message:", clazz.getName()), e);
        }
    }

    public static class CallableTransformCallback implements TransformCallback {
        @Override
        public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
            try {
                ClassInfo classInfo = new ClassInfo(className, classfileBuffer, classLoader);
                CtClass ctClass = classInfo.getCtClass();

                addField(ctClass, AsyncContextAccessor.class);

                CtConstructor[] declaredConstructors = ctClass.getDeclaredConstructors();
                for (CtConstructor ctConstructor : declaredConstructors) {
                    String before = "com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread.interceptor.ThreadConstructorInterceptor.before(this);\n";
                    ctConstructor.insertAfter(before);
                }

                CtMethod ctMethod = ctClass.getDeclaredMethod("call");
                if (null != ctMethod) {
                    ctMethod.insertBefore("com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread.interceptor.ThreadCallInterceptor.before(this);\n");
                    ctMethod.insertAfter("com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread.interceptor.ThreadCallInterceptor.after(this);\n");
                }

                return ctClass.toBytecode();
            } catch (Exception e) {
                LOG.warn(String.format("Transform %s error, message:", className), e);

                return null;
            }
        }
    }
}