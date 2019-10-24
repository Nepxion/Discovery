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
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.MDC;
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

    public void logTraceHeader() {
        String traceId = getTraceId();
        String spanId = getSpanId();
        MDC.put(DiscoveryConstant.TRACE_ID, DiscoveryConstant.TRACE_ID + "=" + (StringUtils.isNotEmpty(traceId) ? traceId : StringUtils.EMPTY));
        MDC.put(DiscoveryConstant.SPAN_ID, DiscoveryConstant.SPAN_ID + "=" + (StringUtils.isNotEmpty(spanId) ? spanId : StringUtils.EMPTY));
        MDC.put(DiscoveryConstant.N_D_SERVICE_GROUP, DiscoveryConstant.N_D_SERVICE_GROUP + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_GROUP));
        MDC.put(DiscoveryConstant.N_D_SERVICE_TYPE, DiscoveryConstant.N_D_SERVICE_TYPE + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_TYPE));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ID, DiscoveryConstant.N_D_SERVICE_ID + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ID));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ADDRESS, DiscoveryConstant.N_D_SERVICE_ADDRESS + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ADDRESS));
        MDC.put(DiscoveryConstant.N_D_SERVICE_VERSION, DiscoveryConstant.N_D_SERVICE_VERSION + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_VERSION));
        MDC.put(DiscoveryConstant.N_D_SERVICE_REGION, DiscoveryConstant.N_D_SERVICE_REGION + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_REGION));
        Map<String, String> customizationMap = getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                MDC.put(entry.getKey(), entry.getKey() + "=" + entry.getValue());
            }
        }
    }

    public void logTraceLocal() {
        String traceId = getTraceId();
        String spanId = getSpanId();
        MDC.put(DiscoveryConstant.TRACE_ID, DiscoveryConstant.TRACE_ID + "=" + (StringUtils.isNotEmpty(traceId) ? traceId : StringUtils.EMPTY));
        MDC.put(DiscoveryConstant.SPAN_ID, DiscoveryConstant.SPAN_ID + "=" + (StringUtils.isNotEmpty(spanId) ? spanId : StringUtils.EMPTY));
        MDC.put(DiscoveryConstant.N_D_SERVICE_GROUP, DiscoveryConstant.N_D_SERVICE_GROUP + "=" + pluginAdapter.getGroup());
        MDC.put(DiscoveryConstant.N_D_SERVICE_TYPE, DiscoveryConstant.N_D_SERVICE_TYPE + "=" + pluginAdapter.getServiceType());
        MDC.put(DiscoveryConstant.N_D_SERVICE_ID, DiscoveryConstant.N_D_SERVICE_ID + "=" + pluginAdapter.getServiceId());
        MDC.put(DiscoveryConstant.N_D_SERVICE_ADDRESS, DiscoveryConstant.N_D_SERVICE_ADDRESS + "=" + pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        MDC.put(DiscoveryConstant.N_D_SERVICE_VERSION, DiscoveryConstant.N_D_SERVICE_VERSION + "=" + pluginAdapter.getVersion());
        MDC.put(DiscoveryConstant.N_D_SERVICE_REGION, DiscoveryConstant.N_D_SERVICE_REGION + "=" + pluginAdapter.getRegion());
        Map<String, String> customizationMap = getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                MDC.put(entry.getKey(), entry.getKey() + "=" + entry.getValue());
            }
        }
    }

    public void debugTraceHeader() {
        if (!traceDebugEnabled) {
            return;
        }

        System.out.println("---------------- Trace Information ---------------");
        String traceId = getTraceId();
        String spanId = getSpanId();
        System.out.println(DiscoveryConstant.TRACE_ID + "=" + (StringUtils.isNotEmpty(traceId) ? traceId : StringUtils.EMPTY));
        System.out.println(DiscoveryConstant.SPAN_ID + "=" + (StringUtils.isNotEmpty(spanId) ? spanId : StringUtils.EMPTY));
        System.out.println(DiscoveryConstant.N_D_SERVICE_GROUP + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_GROUP));
        System.out.println(DiscoveryConstant.N_D_SERVICE_TYPE + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_TYPE));
        System.out.println(DiscoveryConstant.N_D_SERVICE_ID + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ID));
        System.out.println(DiscoveryConstant.N_D_SERVICE_ADDRESS + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ADDRESS));
        System.out.println(DiscoveryConstant.N_D_SERVICE_VERSION + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_VERSION));
        System.out.println(DiscoveryConstant.N_D_SERVICE_REGION + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_REGION));
        Map<String, String> customizationMap = getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
        }
        System.out.println("--------------------------------------------------");
    }

    public void debugTraceLocal() {
        if (!traceDebugEnabled) {
            return;
        }

        System.out.println("---------------- Trace Information ---------------");
        String traceId = getTraceId();
        String spanId = getSpanId();
        System.out.println(DiscoveryConstant.TRACE_ID + "=" + (StringUtils.isNotEmpty(traceId) ? traceId : StringUtils.EMPTY));
        System.out.println(DiscoveryConstant.SPAN_ID + "=" + (StringUtils.isNotEmpty(spanId) ? spanId : StringUtils.EMPTY));
        System.out.println(DiscoveryConstant.N_D_SERVICE_GROUP + "=" + pluginAdapter.getGroup());
        System.out.println(DiscoveryConstant.N_D_SERVICE_TYPE + "=" + pluginAdapter.getServiceType());
        System.out.println(DiscoveryConstant.N_D_SERVICE_ID + "=" + pluginAdapter.getServiceId());
        System.out.println(DiscoveryConstant.N_D_SERVICE_ADDRESS + "=" + pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        System.out.println(DiscoveryConstant.N_D_SERVICE_VERSION + "=" + pluginAdapter.getVersion());
        System.out.println(DiscoveryConstant.N_D_SERVICE_REGION + "=" + pluginAdapter.getRegion());
        Map<String, String> customizationMap = getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
        }
        System.out.println("--------------------------------------------------");
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    public StrategyContextHolder getStrategyContextHolder() {
        return strategyContextHolder;
    }

    public String getTraceId() {
        return null;
    }

    public String getSpanId() {
        return null;
    }

    public Map<String, String> getCustomizationMap() {
        return null;
    }
}