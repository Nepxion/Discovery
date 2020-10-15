package com.nepxion.discovery.plugin.strategy.agent.plugin;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.net.URLClassLoader;
import java.security.ProtectionDomain;

import com.nepxion.discovery.plugin.strategy.agent.callback.TransformCallback;
import com.nepxion.discovery.plugin.strategy.agent.callback.TransformTemplate;
import com.nepxion.discovery.plugin.strategy.agent.loader.AgentClassLoader;
import com.nepxion.discovery.plugin.strategy.agent.logger.AgentLogger;
import com.nepxion.discovery.plugin.strategy.agent.matcher.ClassMatcher;
import com.nepxion.discovery.plugin.strategy.agent.matcher.MatcherFactory;
import com.nepxion.discovery.plugin.strategy.agent.threadlocal.ThreadLocalCopier;

public abstract class AbstractPlugin extends Plugin {
    private final static AgentLogger LOG = AgentLogger.getLogger(AbstractPlugin.class.getName());

    @Override
    public void install(TransformTemplate transformTemplate) {
        String matcherClassName = getMatcherClassName();
        Class<? extends AbstractPlugin> pluginClass = getClass();

        ClassMatcher classMatcher = MatcherFactory.newClassNameMatcher(matcherClassName);
        transformTemplate.transform(classMatcher, new TransformCallback() {
            @Override
            public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                try {
                    String hookClassName = getHookClassName();

                    ThreadLocalCopier.register(AgentClassLoader.load((URLClassLoader) pluginClass.getClassLoader(), classLoader, hookClassName));
                } catch (Exception e) {
                    LOG.warn(String.format("Load %s error", className), e);
                }

                return null;
            }
        });
    }

    protected boolean isEnabled() {
        return true;
    }

    protected abstract String getMatcherClassName();

    protected abstract String getHookClassName();
}