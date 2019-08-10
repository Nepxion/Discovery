package com.nepxion.discovery.plugin.strategy.tracer;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;

public class StrategyTracer {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACE_DEBUG_ENABLED + ":false}")
    protected Boolean traceDebugEnabled;

    public void debugTraceHeader() {
        if (!traceDebugEnabled) {
            return;
        }

        System.out.println("---------------- Trace Information ---------------");
        Map<String, String> debugTraceMap = getDebugTraceMap();
        if (MapUtils.isNotEmpty(debugTraceMap)) {
            for (Map.Entry<String, String> entry : debugTraceMap.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
        }
        System.out.println(DiscoveryConstant.N_D_SERVICE_GROUP + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_GROUP));
        System.out.println(DiscoveryConstant.N_D_SERVICE_TYPE + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_TYPE));
        System.out.println(DiscoveryConstant.N_D_SERVICE_ID + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ID));
        System.out.println(DiscoveryConstant.N_D_SERVICE_ADDRESS + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ADDRESS));
        System.out.println(DiscoveryConstant.N_D_SERVICE_VERSION + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_VERSION));
        System.out.println(DiscoveryConstant.N_D_SERVICE_REGION + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_REGION));
        System.out.println("--------------------------------------------------");
    }

    public void debugTraceLocal() {
        if (!traceDebugEnabled) {
            return;
        }

        System.out.println("---------------- Trace Information ---------------");
        Map<String, String> debugTraceMap = getDebugTraceMap();
        if (MapUtils.isNotEmpty(debugTraceMap)) {
            for (Map.Entry<String, String> entry : debugTraceMap.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
        }
        System.out.println(DiscoveryConstant.N_D_SERVICE_GROUP + "=" + pluginAdapter.getGroup());
        System.out.println(DiscoveryConstant.N_D_SERVICE_TYPE + "=" + pluginAdapter.getServiceType());
        System.out.println(DiscoveryConstant.N_D_SERVICE_ID + "=" + pluginAdapter.getServiceId());
        System.out.println(DiscoveryConstant.N_D_SERVICE_ADDRESS + "=" + pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        System.out.println(DiscoveryConstant.N_D_SERVICE_VERSION + "=" + pluginAdapter.getVersion());
        System.out.println(DiscoveryConstant.N_D_SERVICE_REGION + "=" + pluginAdapter.getRegion());
        System.out.println("--------------------------------------------------");
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    public StrategyContextHolder getStrategyContextHolder() {
        return strategyContextHolder;
    }

    public Map<String, String> getDebugTraceMap() {
        return null;
    }
}