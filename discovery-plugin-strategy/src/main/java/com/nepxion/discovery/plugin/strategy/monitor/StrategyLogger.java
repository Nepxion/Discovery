package com.nepxion.discovery.plugin.strategy.monitor;

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
import com.nepxion.discovery.plugin.strategy.adapter.StrategyTracerAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;

public class StrategyLogger {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Autowired(required = false)
    protected StrategyTracer strategyTracer;

    @Autowired(required = false)
    protected StrategyTracerAdapter strategyTracerAdapter;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_LOGGER_ENABLED + ":false}")
    protected Boolean loggerEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_LOGGER_MDC_KEY_SHOWN + ":true}")
    protected Boolean loggerMdcKeyShown;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_LOGGER_DEBUG_ENABLED + ":false}")
    protected Boolean loggerDebugEnabled;

    public void loggerOutput() {
        if (!loggerEnabled) {
            return;
        }

        Map<String, String> customizationMap = getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                MDC.put(entry.getKey(), (loggerMdcKeyShown ? entry.getKey() + "=" : StringUtils.EMPTY) + entry.getValue());
            }
        }

        String traceId = getTraceId();
        String spanId = getSpanId();
        MDC.put(DiscoveryConstant.TRACE_ID, (loggerMdcKeyShown ? DiscoveryConstant.TRACE_ID + "=" : StringUtils.EMPTY) + (StringUtils.isNotEmpty(traceId) ? traceId : StringUtils.EMPTY));
        MDC.put(DiscoveryConstant.SPAN_ID, (loggerMdcKeyShown ? DiscoveryConstant.SPAN_ID + "=" : StringUtils.EMPTY) + (StringUtils.isNotEmpty(spanId) ? spanId : StringUtils.EMPTY));
        MDC.put(DiscoveryConstant.N_D_SERVICE_GROUP, (loggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_GROUP + "=" : StringUtils.EMPTY) + pluginAdapter.getGroup());
        MDC.put(DiscoveryConstant.N_D_SERVICE_TYPE, (loggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_TYPE + "=" : StringUtils.EMPTY) + pluginAdapter.getServiceType());
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            MDC.put(DiscoveryConstant.N_D_SERVICE_APP_ID, (loggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_APP_ID + "=" : StringUtils.EMPTY) + serviceAppId);
        }
        MDC.put(DiscoveryConstant.N_D_SERVICE_ID, (loggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_ID + "=" : StringUtils.EMPTY) + pluginAdapter.getServiceId());
        MDC.put(DiscoveryConstant.N_D_SERVICE_ADDRESS, (loggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_ADDRESS + "=" : StringUtils.EMPTY) + pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        MDC.put(DiscoveryConstant.N_D_SERVICE_VERSION, (loggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_VERSION + "=" : StringUtils.EMPTY) + pluginAdapter.getVersion());
        MDC.put(DiscoveryConstant.N_D_SERVICE_REGION, (loggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_REGION + "=" : StringUtils.EMPTY) + pluginAdapter.getRegion());
        MDC.put(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, (loggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_ENVIRONMENT + "=" : StringUtils.EMPTY) + pluginAdapter.getEnvironment());
    }

    public void loggerClear() {
        if (!loggerEnabled) {
            return;
        }

        MDC.clear();
    }

    public void loggerDebug() {
        if (!loggerDebugEnabled) {
            return;
        }

        System.out.println("------------------ Logger Debug ------------------");
        String traceId = getTraceId();
        String spanId = getSpanId();
        System.out.println(DiscoveryConstant.TRACE_ID + "=" + (StringUtils.isNotEmpty(traceId) ? traceId : StringUtils.EMPTY));
        System.out.println(DiscoveryConstant.SPAN_ID + "=" + (StringUtils.isNotEmpty(spanId) ? spanId : StringUtils.EMPTY));
        System.out.println(DiscoveryConstant.N_D_SERVICE_GROUP + "=" + pluginAdapter.getGroup());
        System.out.println(DiscoveryConstant.N_D_SERVICE_TYPE + "=" + pluginAdapter.getServiceType());
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            System.out.println(DiscoveryConstant.N_D_SERVICE_APP_ID + "=" + serviceAppId);
        }
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
        String routeEnvironment = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ENVIRONMENT);
        if (StringUtils.isNotEmpty(routeEnvironment)) {
            System.out.println(DiscoveryConstant.N_D_ENVIRONMENT + "=" + routeEnvironment);
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

    public String getTraceId() {
        if (strategyTracer != null) {
            return strategyTracer.getTraceId();
        }

        if (strategyTracerAdapter != null) {
            return strategyTracerAdapter.getTraceId();
        }

        return null;
    }

    public String getSpanId() {
        if (strategyTracer != null) {
            return strategyTracer.getSpanId();
        }

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