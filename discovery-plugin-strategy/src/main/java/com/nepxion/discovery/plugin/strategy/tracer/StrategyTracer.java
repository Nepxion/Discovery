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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.adapter.StrategyTracerAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;

public class StrategyTracer {
    private static final Logger LOG = LoggerFactory.getLogger(StrategyTracer.class);

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Autowired(required = false)
    protected StrategyTracerAdapter strategyTracerAdapter;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACE_LOGGER_ENABLED + ":false}")
    protected Boolean traceLoggerEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACE_LOGGER_MDC_KEY_SHOWN + ":true}")
    protected Boolean traceLoggerMdcKeyShown;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACE_DEBUG_ENABLED + ":false}")
    protected Boolean traceDebugEnabled;

    public void mdcHeader() {
        if (!traceLoggerEnabled) {
            return;
        }

        Map<String, String> customizationMap = getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                MDC.put(entry.getKey(), (traceLoggerMdcKeyShown ? entry.getKey() + "=" : StringUtils.EMPTY) + entry.getValue());
            }
        }

        String traceId = getTraceId();
        String spanId = getSpanId();
        MDC.put(DiscoveryConstant.TRACE_ID, (traceLoggerMdcKeyShown ? DiscoveryConstant.TRACE_ID + "=" : StringUtils.EMPTY) + (StringUtils.isNotEmpty(traceId) ? traceId : StringUtils.EMPTY));
        MDC.put(DiscoveryConstant.SPAN_ID, (traceLoggerMdcKeyShown ? DiscoveryConstant.SPAN_ID + "=" : StringUtils.EMPTY) + (StringUtils.isNotEmpty(spanId) ? spanId : StringUtils.EMPTY));
        MDC.put(DiscoveryConstant.N_D_SERVICE_GROUP, (traceLoggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_GROUP + "=" : StringUtils.EMPTY) + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_GROUP));
        MDC.put(DiscoveryConstant.N_D_SERVICE_TYPE, (traceLoggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_TYPE + "=" : StringUtils.EMPTY) + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_TYPE));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ID, (traceLoggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_ID + "=" : StringUtils.EMPTY) + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ID));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ADDRESS, (traceLoggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_ADDRESS + "=" : StringUtils.EMPTY) + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ADDRESS));
        MDC.put(DiscoveryConstant.N_D_SERVICE_VERSION, (traceLoggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_VERSION + "=" : StringUtils.EMPTY) + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_VERSION));
        MDC.put(DiscoveryConstant.N_D_SERVICE_REGION, (traceLoggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_REGION + "=" : StringUtils.EMPTY) + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_REGION));
        MDC.put(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, (traceLoggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_ENVIRONMENT + "=" : StringUtils.EMPTY) + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT));

        LOG.debug("Trace chain information outputs to MDC...");
    }

    public void mdcLocal() {
        if (!traceLoggerEnabled) {
            return;
        }

        Map<String, String> customizationMap = getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                MDC.put(entry.getKey(), (traceLoggerMdcKeyShown ? entry.getKey() + "=" : StringUtils.EMPTY) + entry.getValue());
            }
        }

        String traceId = getTraceId();
        String spanId = getSpanId();
        MDC.put(DiscoveryConstant.TRACE_ID, (traceLoggerMdcKeyShown ? DiscoveryConstant.TRACE_ID + "=" : StringUtils.EMPTY) + (StringUtils.isNotEmpty(traceId) ? traceId : StringUtils.EMPTY));
        MDC.put(DiscoveryConstant.SPAN_ID, (traceLoggerMdcKeyShown ? DiscoveryConstant.SPAN_ID + "=" : StringUtils.EMPTY) + (StringUtils.isNotEmpty(spanId) ? spanId : StringUtils.EMPTY));
        MDC.put(DiscoveryConstant.N_D_SERVICE_GROUP, (traceLoggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_GROUP + "=" : StringUtils.EMPTY) + pluginAdapter.getGroup());
        MDC.put(DiscoveryConstant.N_D_SERVICE_TYPE, (traceLoggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_TYPE + "=" : StringUtils.EMPTY) + pluginAdapter.getServiceType());
        MDC.put(DiscoveryConstant.N_D_SERVICE_ID, (traceLoggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_ID + "=" : StringUtils.EMPTY) + pluginAdapter.getServiceId());
        MDC.put(DiscoveryConstant.N_D_SERVICE_ADDRESS, (traceLoggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_ADDRESS + "=" : StringUtils.EMPTY) + pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        MDC.put(DiscoveryConstant.N_D_SERVICE_VERSION, (traceLoggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_VERSION + "=" : StringUtils.EMPTY) + pluginAdapter.getVersion());
        MDC.put(DiscoveryConstant.N_D_SERVICE_REGION, (traceLoggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_REGION + "=" : StringUtils.EMPTY) + pluginAdapter.getRegion());
        MDC.put(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, (traceLoggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_ENVIRONMENT + "=" : StringUtils.EMPTY) + pluginAdapter.getEnvironment());

        LOG.debug("Trace chain information outputs to MDC...");
    }

    public void mdcClear() {
        if (!traceLoggerEnabled) {
            return;
        }

        MDC.clear();

        LOG.debug("Trace chain context of MDC cleared...");
    }

    public void debugHeader() {
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
        System.out.println(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT + "=" + strategyContextHolder.getHeader(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT));

        String routeVersion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION);
        if (StringUtils.isNotEmpty(routeVersion)) {
            System.out.println(DiscoveryConstant.N_D_VERSION + "=" + routeVersion);
        }
        String routeRegion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION);
        if (StringUtils.isNotEmpty(routeRegion)) {
            System.out.println(DiscoveryConstant.N_D_REGION + "=" + routeRegion);
        }
        String routeAddress = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS);
        if (StringUtils.isNotEmpty(routeAddress)) {
            System.out.println(DiscoveryConstant.N_D_ADDRESS + "=" + routeAddress);
        }
        String routeVersionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_WEIGHT);
        if (StringUtils.isNotEmpty(routeVersionWeight)) {
            System.out.println(DiscoveryConstant.N_D_VERSION_WEIGHT + "=" + routeVersionWeight);
        }
        String routeRegionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_WEIGHT);
        if (StringUtils.isNotEmpty(routeRegionWeight)) {
            System.out.println(DiscoveryConstant.N_D_REGION_WEIGHT + "=" + routeRegionWeight);
        }

        Map<String, String> customizationMap = getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
        }
        System.out.println("--------------------------------------------------");
    }

    public void debugLocal() {
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
        System.out.println(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT + "=" + pluginAdapter.getEnvironment());

        String routeVersion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION);
        if (StringUtils.isNotEmpty(routeVersion)) {
            System.out.println(DiscoveryConstant.N_D_VERSION + "=" + routeVersion);
        }
        String routeRegion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION);
        if (StringUtils.isNotEmpty(routeRegion)) {
            System.out.println(DiscoveryConstant.N_D_REGION + "=" + routeRegion);
        }
        String routeAddress = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS);
        if (StringUtils.isNotEmpty(routeAddress)) {
            System.out.println(DiscoveryConstant.N_D_ADDRESS + "=" + routeAddress);
        }
        String routeVersionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_WEIGHT);
        if (StringUtils.isNotEmpty(routeVersionWeight)) {
            System.out.println(DiscoveryConstant.N_D_VERSION_WEIGHT + "=" + routeVersionWeight);
        }
        String routeRegionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_WEIGHT);
        if (StringUtils.isNotEmpty(routeRegionWeight)) {
            System.out.println(DiscoveryConstant.N_D_REGION_WEIGHT + "=" + routeRegionWeight);
        }

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
        if (strategyTracerAdapter != null) {
            return strategyTracerAdapter.getTraceId();
        }

        return null;
    }

    public String getSpanId() {
        if (strategyTracerAdapter != null) {
            return strategyTracerAdapter.getSpanId();
        }

        return null;
    }

    public Map<String, String> getCustomizationMap() {
        if (strategyTracerAdapter != null) {
            return strategyTracerAdapter.getCustomizationMap();
        }

        return null;
    }
}