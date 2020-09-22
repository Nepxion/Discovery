package com.nepxion.discovery.plugin.strategy.agent.plugin.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import java.net.URLClassLoader;
import java.security.ProtectionDomain;

import com.nepxion.discovery.plugin.strategy.agent.callback.TransformCallback;
import com.nepxion.discovery.plugin.strategy.agent.callback.TransformTemplate;
import com.nepxion.discovery.plugin.strategy.agent.logger.AgentLogger;
import com.nepxion.discovery.plugin.strategy.agent.matcher.ClassMatcher;
import com.nepxion.discovery.plugin.strategy.agent.matcher.MatcherFactory;
import com.nepxion.discovery.plugin.strategy.agent.plugin.Plugin;
import com.nepxion.discovery.plugin.strategy.agent.threadlocal.ThreadLocalCopier;
import com.nepxion.discovery.plugin.strategy.agent.threadlocal.ThreadLocalHook;

public class DiscoveryMonitorPlugin extends Plugin {
    private final static AgentLogger LOG = AgentLogger.getLogger(DiscoveryMonitorPlugin.class.getName());

    @Override
    public void install(TransformTemplate transformTemplate) {
        ClassMatcher classMatcher = MatcherFactory.newClassNameMatcher("com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContext");
        transformTemplate.transform(classMatcher, new TransformCallback() {
            @Override
            public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                try {
                    URLClassLoader urlClassLoader = new URLClassLoader(((URLClassLoader) DiscoveryMonitorPlugin.class.getClassLoader()).getURLs(), classLoader);
                    Object object = Class.forName("com.nepxion.discovery.plugin.strategy.agent.plugin.monitor.StrategyTracerContextHook", true, urlClassLoader).newInstance();
                    ThreadLocalCopier.register((ThreadLocalHook) object);
                } catch (Exception e) {
                    LOG.warn(String.format("Load %s error", className), e);
                }

                return null;
            }
        });
    }
}