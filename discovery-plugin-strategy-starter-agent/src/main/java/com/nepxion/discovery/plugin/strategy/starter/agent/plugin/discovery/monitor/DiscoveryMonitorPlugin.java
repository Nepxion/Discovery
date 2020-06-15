package com.nepxion.discovery.plugin.strategy.starter.agent.plugin.discovery.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.starter.agent.log.SampleLogger;
import com.nepxion.discovery.plugin.strategy.starter.agent.match.ClassMatcher;
import com.nepxion.discovery.plugin.strategy.starter.agent.match.Matchers;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.Plugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.threadLocal.ThreadLocalCopier;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformCallback;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformTemplate;

import java.security.ProtectionDomain;

public class DiscoveryMonitorPlugin extends Plugin {
    private static final SampleLogger LOG = SampleLogger.getLogger(DiscoveryMonitorPlugin.class.getName());

    public DiscoveryMonitorPlugin(TransformTemplate transformTemplate) {
        super(transformTemplate);
    }

    @Override
    public void install() {
        ClassMatcher classNameMatcher = Matchers.newClassNameMatcher("com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContext");
        transformTemplate.transform(classNameMatcher, new TransformCallback() {
            @Override
            public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                ThreadLocalCopier.register(new StrategyTracerContextHook());

                return null;
            }
        });
        LOG.info(String.format("%s install success", this.getClass().getSimpleName()));
    }
}