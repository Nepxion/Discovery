package com.nepxion.discovery.plugin.strategy.starter.agent.plugin.discover;


import com.nepxion.discovery.plugin.strategy.starter.agent.log.SampleLogger;
import com.nepxion.discovery.plugin.strategy.starter.agent.match.ClassMatcher;
import com.nepxion.discovery.plugin.strategy.starter.agent.match.Matchers;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.Plugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.threadLocal.ThreadLocalCopier;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformCallback;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformTemplate;

import java.security.ProtectionDomain;

public class DiscoverPlugin extends Plugin {

    private final static SampleLogger logger = SampleLogger.getLogger(DiscoverPlugin.class.getName());

    public DiscoverPlugin(TransformTemplate transformTemplate) {
        super(transformTemplate);
    }

    @Override
    public void install() {
        ClassMatcher classMatcher = Matchers.newClassNameMatcher("com.nepxion.discovery.plugin.strategy.service.context.RestStrategyContext");
        transformTemplate.transform(classMatcher, new TransformCallback() {
            @Override
            public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                ThreadLocalCopier.register(new RestStrategyContextHook());
                return null;
            }
        });

        ClassMatcher classNameMatcher = Matchers.newClassNameMatcher("com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContext");
        transformTemplate.transform(classNameMatcher, new TransformCallback() {
            @Override
            public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                ThreadLocalCopier.register(new StrategyTracerContextHook());
                return null;
            }
        });
        logger.info(String.format("%s install success", this.getClass().getSimpleName()));
    }
}
