package com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread;

import com.nepxion.discovery.plugin.strategy.starter.agent.async.AsyncContextAccessor;
import com.nepxion.discovery.plugin.strategy.starter.agent.log.SampleLogger;
import com.nepxion.discovery.plugin.strategy.starter.agent.match.InterfaceMatcher;
import com.nepxion.discovery.plugin.strategy.starter.agent.match.Matchers;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.Plugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformCallback;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformTemplate;
import com.nepxion.discovery.plugin.strategy.starter.agent.util.ClassInfo;
import com.nepxion.discovery.plugin.strategy.starter.agent.util.StringUtils;
import javassist.*;

import java.security.ProtectionDomain;

public class ThreadPlugin extends Plugin {

    private final static SampleLogger logger = SampleLogger.getLogger(ThreadPlugin.class.getName());

    public ThreadPlugin(TransformTemplate transformTemplate) {
        super(transformTemplate);
    }

    @Override
    public void install() {
        String threadMatchPackage = System.getProperty("thread.match.package");
        if (StringUtils.isEmpty(threadMatchPackage)) {
            logger.warn("thread.match.package is null,ignore thread context switch.");
            return;
        }
        logger.info(String.format("trace (%s) Runnable/Callable for thread context switch.", threadMatchPackage));
        InterfaceMatcher runnableInterfaceMatcher = Matchers.newPackageBasedMatcher(threadMatchPackage, "java.lang.Runnable");
        InterfaceMatcher callableInterfaceMatcher = Matchers.newPackageBasedMatcher(threadMatchPackage, "java.util.concurrent.Callable");

        transformTemplate.transform(runnableInterfaceMatcher, new RunnableTransformCallback());
        transformTemplate.transform(callableInterfaceMatcher, new CallableTransformCallback());
        logger.info(String.format("%s install success", this.getClass().getSimpleName()));
    }

    public static class RunnableTransformCallback implements TransformCallback {
        @Override
        public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
            try {
                ClassInfo classInfo = new ClassInfo(className, classfileBuffer, classLoader);
                CtClass ctClass = classInfo.getCtClass();
                ClassPool aDefault = ClassPool.getDefault();
                CtClass makeInterface = aDefault.makeInterface(AsyncContextAccessor.class.getName());
                ctClass.addInterface(makeInterface);

                CtField f1 = CtField.make("private com.nepxion.discovery.plugin.strategy.starter.agent.async.AsyncContext asyncContext;", ctClass);
                ctClass.addField(f1);

                CtMethod m1 = CtMethod.make("public com.nepxion.discovery.plugin.strategy.starter.agent.async.AsyncContext getAsyncContext(){return asyncContext;}", ctClass);
                CtMethod m2 = CtMethod.make("public void setAsyncContext(com.nepxion.discovery.plugin.strategy.starter.agent.async.AsyncContext asyncContext){this.asyncContext = asyncContext;}", ctClass);
                ctClass.addMethod(m1);
                ctClass.addMethod(m2);


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
                logger.warn(String.format("transform %s error,message:", className), e);
                return null;
            }
        }
    }

    public static class CallableTransformCallback implements TransformCallback {
        @Override
        public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
            try {
                ClassInfo classInfo = new ClassInfo(className, classfileBuffer, classLoader);
                CtClass ctClass = classInfo.getCtClass();
                ClassPool aDefault = ClassPool.getDefault();
                CtClass makeInterface = aDefault.makeInterface(AsyncContextAccessor.class.getName());
                ctClass.addInterface(makeInterface);

                CtField f1 = CtField.make("private com.nepxion.discovery.plugin.strategy.starter.agent.async.AsyncContext asyncContext;", ctClass);
                ctClass.addField(f1);

                CtMethod m1 = CtMethod.make("public com.nepxion.discovery.plugin.strategy.starter.agent.async.AsyncContext getAsyncContext(){return asyncContext;}", ctClass);
                CtMethod m2 = CtMethod.make("public void setAsyncContext(com.nepxion.discovery.plugin.strategy.starter.agent.async.AsyncContext asyncContext){this.asyncContext = asyncContext;}", ctClass);
                ctClass.addMethod(m1);
                ctClass.addMethod(m2);

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
                logger.warn(String.format("transform %s error,message:", className), e);
                return null;
            }
        }
    }
}
