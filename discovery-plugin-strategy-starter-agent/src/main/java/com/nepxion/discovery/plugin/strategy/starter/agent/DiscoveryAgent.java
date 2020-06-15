package com.nepxion.discovery.plugin.strategy.starter.agent;

import com.nepxion.discovery.plugin.strategy.starter.agent.log.SampleLogger;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.PluginFinder;
import com.nepxion.discovery.plugin.strategy.starter.agent.transform.DispatcherClassFileTransformer;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformTemplate;

import java.lang.instrument.Instrumentation;

public class DiscoveryAgent {

    private static SampleLogger logger = SampleLogger.getLogger(DiscoveryAgent.class.getName());

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        logger.info(String.format("%s agent on load.", DiscoveryAgent.class.getSimpleName()));
        TransformTemplate transformTemplate = new TransformTemplate();
        PluginFinder.load(transformTemplate);

        instrumentation.addTransformer(new DispatcherClassFileTransformer(transformTemplate));
        System.setProperty(DiscoveryAgent.class.getSimpleName(), Version.VERSION);
    }
}
