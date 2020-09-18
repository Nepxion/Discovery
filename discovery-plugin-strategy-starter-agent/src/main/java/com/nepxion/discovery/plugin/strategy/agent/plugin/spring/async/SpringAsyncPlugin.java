package com.nepxion.discovery.plugin.strategy.agent.plugin.spring.async;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import javassist.CtClass;
import javassist.CtMethod;

import java.security.ProtectionDomain;

import com.nepxion.discovery.plugin.strategy.agent.callback.TransformCallback;
import com.nepxion.discovery.plugin.strategy.agent.callback.TransformTemplate;
import com.nepxion.discovery.plugin.strategy.agent.matcher.ClassMatcher;
import com.nepxion.discovery.plugin.strategy.agent.matcher.MatcherFactory;
import com.nepxion.discovery.plugin.strategy.agent.plugin.Plugin;
import com.nepxion.discovery.plugin.strategy.agent.util.ClassInfo;

public class SpringAsyncPlugin extends Plugin {
    @Override
    public void install(TransformTemplate transformTemplate) {
        ClassMatcher classNameMatcher = MatcherFactory.newClassNameMatcher("org.springframework.aop.interceptor.AsyncExecutionAspectSupport");
        transformTemplate.transform(classNameMatcher, new TransformCallback() {
            @Override
            public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

                try {
                    ClassInfo classInfo = new ClassInfo(className, classfileBuffer, classLoader);
                    CtClass ctClass = classInfo.getCtClass();
                    CtMethod method = ctClass.getMethod("doSubmit", "(Ljava/util/concurrent/Callable;Lorg/springframework/core/task/AsyncTaskExecutor;Ljava/lang/Class;)Ljava/lang/Object;");
                    if (null != method) {
                        String code = "";
                        CtClass[] parameterTypes = method.getParameterTypes();
                        for (int i = 0; i < parameterTypes.length; i++) {
                            final String paramTypeName = parameterTypes[i].getName();
                            if ("java.util.concurrent.Callable".equals(paramTypeName)) {
                                code += String.format("$%d = new com.nepxion.discovery.plugin.strategy.agent.plugin.spring.async.WrapCallable($%d);", i + 1, i + 1);
                            }
                        }

                        if (code.length() > 0) {
                            method.insertBefore(code);
                        }
                    }
                    return ctClass.toBytecode();
                } catch (Exception e) {
                    return null;
                }
            }
        });
    }
}