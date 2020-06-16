package com.nepxion.discovery.plugin.strategy.starter.agent.plugin.discovery.zuul;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import java.security.ProtectionDomain;

import com.nepxion.discovery.plugin.strategy.starter.agent.core.callback.TransformCallback;
import com.nepxion.discovery.plugin.strategy.starter.agent.core.callback.TransformTemplate;
import com.nepxion.discovery.plugin.strategy.starter.agent.core.matcher.ClassMatcher;
import com.nepxion.discovery.plugin.strategy.starter.agent.core.matcher.MatcherFactory;
import com.nepxion.discovery.plugin.strategy.starter.agent.core.plugin.Plugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.core.threadlocal.ThreadLocalCopier;

public class DiscoveryZuulPlugin extends Plugin {
    @Override
    public void install(TransformTemplate transformTemplate) {
        ClassMatcher classMatcher = MatcherFactory.newClassNameMatcher("com.nepxion.discovery.plugin.strategy.zuul.context.ZuulStrategyContext");
        transformTemplate.transform(classMatcher, new TransformCallback() {
            @Override
            public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                ThreadLocalCopier.register(new ZuulStrategyContextHook());

                return null;
            }
        });
    }
}