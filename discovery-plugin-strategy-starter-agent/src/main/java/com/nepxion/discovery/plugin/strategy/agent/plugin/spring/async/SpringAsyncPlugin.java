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
import com.nepxion.discovery.plugin.strategy.agent.logger.AgentLogger;
import com.nepxion.discovery.plugin.strategy.agent.matcher.ClassMatcher;
import com.nepxion.discovery.plugin.strategy.agent.matcher.MatcherFactory;
import com.nepxion.discovery.plugin.strategy.agent.plugin.Plugin;
import com.nepxion.discovery.plugin.strategy.agent.util.ClassInfo;

public class SpringAsyncPlugin extends Plugin {
    public static final String CALLABLE_CLASS_NAME = "java.util.concurrent.Callable";

    private static final AgentLogger LOG = AgentLogger.getLogger(SpringAsyncPlugin.class.getName());

    @Override
    public void install(TransformTemplate transformTemplate) {
        ClassMatcher classMatcher = MatcherFactory.newClassNameMatcher("org.springframework.aop.interceptor.AsyncExecutionAspectSupport");
        transformTemplate.transform(classMatcher, new TransformCallback() {
            @Override
            public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

                try {
                    ClassInfo classInfo = new ClassInfo(className, classfileBuffer, classLoader);
                    CtClass ctClass = classInfo.getCtClass();
                    CtMethod method = ctClass.getMethod("doSubmit", "(Ljava/util/concurrent/Callable;Lorg/springframework/core/task/AsyncTaskExecutor;Ljava/lang/Class;)Ljava/lang/Object;");
                    if (null != method) {
                        StringBuffer sb = new StringBuffer();
                        CtClass[] parameterTypes = method.getParameterTypes();
                        for (int i = 0; i < parameterTypes.length; i++) {
                            final String paramTypeName = parameterTypes[i].getName();
                            if (CALLABLE_CLASS_NAME.equals(paramTypeName)) {
                                sb.append(String.format("$%d = new %s($%d);", i + 1, WrapCallable.class.getName(), i + 1));
                            }
                        }

                        if (sb.length() > 0) {
                            method.insertBefore(sb.toString());
                        }
                    }

                    return ctClass.toBytecode();
                } catch (Exception e) {
                    LOG.warn(String.format("Transform %s error,message:", className), e);

                    return null;
                }
            }
        });
    }
}