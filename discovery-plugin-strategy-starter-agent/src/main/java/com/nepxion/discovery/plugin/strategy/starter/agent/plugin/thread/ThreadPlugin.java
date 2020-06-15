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
import com.nepxion.discovery.plugin.strategy.starter.agent.logger.SampleLogger;
import com.nepxion.discovery.plugin.strategy.starter.agent.match.InterfaceMatcher;
import com.nepxion.discovery.plugin.strategy.starter.agent.match.Matchers;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.Plugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformCallback;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformTemplate;
import com.nepxion.discovery.plugin.strategy.starter.agent.util.ClassInfo;
import com.nepxion.discovery.plugin.strategy.starter.agent.util.StringUtil;

public class ThreadPlugin extends Plugin {
    private static final SampleLogger LOG = SampleLogger.getLogger(ThreadPlugin.class.getName());

    public ThreadPlugin(TransformTemplate transformTemplate) {
        super(transformTemplate);
    }

    @Override
    public void install() {
        String threadMatchPackage = System.getProperty(ThreadConstant.THREAD_MATCH_PACKAGE);
        if (StringUtil.isEmpty(threadMatchPackage)) {
            LOG.warn(String.format("%s is null, ignore thread context switch.", ThreadConstant.THREAD_MATCH_PACKAGE));

            return;
        }
        LOG.info(String.format("trace (%s) Runnable/Callable for thread context switch.", threadMatchPackage));
        InterfaceMatcher runnableInterfaceMatcher = Matchers.newPackageBasedMatcher(threadMatchPackage, ThreadConstant.RUNNABLE_CLASS_NAME);
        InterfaceMatcher callableInterfaceMatcher = Matchers.newPackageBasedMatcher(threadMatchPackage, ThreadConstant.CALLABLE_CLASS_NAME);

        transformTemplate.transform(runnableInterfaceMatcher, new RunnableTransformCallback());
        transformTemplate.transform(callableInterfaceMatcher, new CallableTransformCallback());
        LOG.info(String.format("%s install success", this.getClass().getSimpleName()));
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
                LOG.warn(String.format("transform %s error,message:", className), e);

                return null;
            }
        }
    }

    public static void addField(CtClass ctClass, Class clazz) {
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
            LOG.warn(String.format("add field %s error, message:", clazz.getName()), e);
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
                LOG.warn(String.format("transform %s error, message:", className), e);

                return null;
            }
        }
    }
}