package com.nepxion.discovery.plugin.strategy.starter.agent.plugin.discovery.zuul;


import com.nepxion.discovery.plugin.strategy.starter.agent.log.SampleLogger;
import com.nepxion.discovery.plugin.strategy.starter.agent.match.ClassMatcher;
import com.nepxion.discovery.plugin.strategy.starter.agent.match.Matchers;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.Plugin;
import com.nepxion.discovery.plugin.strategy.starter.agent.threadLocal.ThreadLocalCopier;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformCallback;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformTemplate;

import java.security.ProtectionDomain;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author zifeihan
 * @version 1.0
 */
public class DiscoveryZuulPlugin extends Plugin {

    private static final SampleLogger LOG = SampleLogger.getLogger(DiscoveryZuulPlugin.class.getName());

    public DiscoveryZuulPlugin(TransformTemplate transformTemplate) {
        super(transformTemplate);
    }

    @Override
    public void install() {
        ClassMatcher classMatcher = Matchers.newClassNameMatcher("com.nepxion.discovery.plugin.strategy.zuul.context.ZuulStrategyContext");
        transformTemplate.transform(classMatcher, new TransformCallback() {
            @Override
            public byte[] doInTransform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                ThreadLocalCopier.register(new ZuulStrategyContextHook());
                return null;
            }
        });
        LOG.info(String.format("%s install success", this.getClass().getSimpleName()));
    }
}
