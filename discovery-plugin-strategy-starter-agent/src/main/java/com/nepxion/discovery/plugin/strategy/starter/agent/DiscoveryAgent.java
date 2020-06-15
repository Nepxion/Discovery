package com.nepxion.discovery.plugin.strategy.starter.agent;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import java.lang.instrument.Instrumentation;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.starter.agent.logger.AgentLogger;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.PluginFinder;
import com.nepxion.discovery.plugin.strategy.starter.agent.transform.DispatcherClassFileTransformer;
import com.nepxion.discovery.plugin.strategy.starter.agent.transformer.TransformTemplate;

public class DiscoveryAgent {
    private final static AgentLogger LOG = AgentLogger.getLogger(DiscoveryAgent.class.getName());

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        LOG.info(String.format("%s agent on load...", DiscoveryAgent.class.getSimpleName()));
        TransformTemplate transformTemplate = new TransformTemplate();
        PluginFinder.load(transformTemplate);

        instrumentation.addTransformer(new DispatcherClassFileTransformer(transformTemplate));
        System.setProperty(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_AGENT_VERSION, DiscoveryConstant.DISCOVERY_VERSION);
    }
}