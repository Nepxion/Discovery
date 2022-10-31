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
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;

public class DefaultStrategyLogger implements StrategyLogger {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Autowired
    protected StrategyMonitorContext strategyMonitorContext;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_LOGGER_ENABLED + ":false}")
    protected Boolean loggerEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_LOGGER_MDC_KEY_SHOWN + ":true}")
    protected Boolean loggerMdcKeyShown;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_LOGGER_DEBUG_ENABLED + ":false}")
    protected Boolean loggerDebugEnabled;

    @Override
    public void loggerOutput() {
        if (!loggerEnabled) {
            return;
        }

        Map<String, String> customizationMap = strategyMonitorContext.getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                MDC.put(entry.getKey(), (loggerMdcKeyShown ? entry.getKey() + "=" : StringUtils.EMPTY) + entry.getValue());
            }
        }

        String traceId = strategyMonitorContext.getTraceId();
        String spanId = strategyMonitorContext.getSpanId();
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
        String version = pluginAdapter.getVersion();
        if (StringUtils.isNotEmpty(version) && !StringUtils.equals(version, DiscoveryConstant.DEFAULT)) {
            MDC.put(DiscoveryConstant.N_D_SERVICE_VERSION, (loggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_VERSION + "=" : StringUtils.EMPTY) + version);
        }
        String region = pluginAdapter.getRegion();
        if (StringUtils.isNotEmpty(region) && !StringUtils.equals(region, DiscoveryConstant.DEFAULT)) {
            MDC.put(DiscoveryConstant.N_D_SERVICE_REGION, (loggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_REGION + "=" : StringUtils.EMPTY) + region);
        }
        String environment = pluginAdapter.getEnvironment();
        if (StringUtils.isNotEmpty(environment) && !StringUtils.equals(environment, DiscoveryConstant.DEFAULT)) {
            MDC.put(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, (loggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_ENVIRONMENT + "=" : StringUtils.EMPTY) + environment);
        }
        String zone = pluginAdapter.getZone();
        if (StringUtils.isNotEmpty(zone) && !StringUtils.equals(zone, DiscoveryConstant.DEFAULT)) {
            MDC.put(DiscoveryConstant.N_D_SERVICE_ZONE, (loggerMdcKeyShown ? DiscoveryConstant.N_D_SERVICE_ZONE + "=" : StringUtils.EMPTY) + zone);
        }
    }

    @Override
    public void loggerClear() {
        if (!loggerEnabled) {
            return;
        }

        MDC.clear();
    }

    @Override
    public void loggerDebug() {
        if (!loggerDebugEnabled) {
            return;
        }

        System.out.println("----------------------- Logger Debug -----------------------");
        String traceId = strategyMonitorContext.getTraceId();
        String spanId = strategyMonitorContext.getSpanId();
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
        String version = pluginAdapter.getVersion();
        if (StringUtils.isNotEmpty(version) && !StringUtils.equals(version, DiscoveryConstant.DEFAULT)) {
            System.out.println(DiscoveryConstant.N_D_SERVICE_VERSION + "=" + version);
        }
        String region = pluginAdapter.getRegion();
        if (StringUtils.isNotEmpty(region) && !StringUtils.equals(region, DiscoveryConstant.DEFAULT)) {
            System.out.println(DiscoveryConstant.N_D_SERVICE_REGION + "=" + region);
        }
        String environment = pluginAdapter.getEnvironment();
        if (StringUtils.isNotEmpty(environment) && !StringUtils.equals(environment, DiscoveryConstant.DEFAULT)) {
            System.out.println(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT + "=" + environment);
        }
        String zone = pluginAdapter.getZone();
        if (StringUtils.isNotEmpty(zone) && !StringUtils.equals(zone, DiscoveryConstant.DEFAULT)) {
            System.out.println(DiscoveryConstant.N_D_SERVICE_ZONE + "=" + zone);
        }

        String routeVersion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION);
        if (StringUtils.isNotEmpty(routeVersion)) {
            System.out.println(DiscoveryConstant.N_D_VERSION + "=" + routeVersion);
        }
        String routeRegion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION);
        if (StringUtils.isNotEmpty(routeRegion)) {
            System.out.println(DiscoveryConstant.N_D_REGION + "=" + routeRegion);
        }
        String routeEnvironment = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ENVIRONMENT);
        if (StringUtils.isNotEmpty(routeEnvironment)) {
            System.out.println(DiscoveryConstant.N_D_ENVIRONMENT + "=" + routeEnvironment);
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
        String routeVersionPrefer = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_PREFER);
        if (StringUtils.isNotEmpty(routeVersionPrefer)) {
            System.out.println(DiscoveryConstant.N_D_VERSION_PREFER + "=" + routeVersionPrefer);
        }
        String routeVersionFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_FAILOVER);
        if (StringUtils.isNotEmpty(routeVersionFailover)) {
            System.out.println(DiscoveryConstant.N_D_VERSION_FAILOVER + "=" + routeVersionFailover);
        }
        String routeRegionTransfer = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_TRANSFER);
        if (StringUtils.isNotEmpty(routeRegionTransfer)) {
            System.out.println(DiscoveryConstant.N_D_REGION_TRANSFER + "=" + routeRegionTransfer);
        }
        String routeRegionFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_FAILOVER);
        if (StringUtils.isNotEmpty(routeRegionFailover)) {
            System.out.println(DiscoveryConstant.N_D_REGION_FAILOVER + "=" + routeRegionFailover);
        }
        String routeEnvironmentFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ENVIRONMENT_FAILOVER);
        if (StringUtils.isNotEmpty(routeEnvironmentFailover)) {
            System.out.println(DiscoveryConstant.N_D_ENVIRONMENT_FAILOVER + "=" + routeEnvironmentFailover);
        }
        String routeZoneFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ZONE_FAILOVER);
        if (StringUtils.isNotEmpty(routeZoneFailover)) {
            System.out.println(DiscoveryConstant.N_D_ZONE_FAILOVER + "=" + routeZoneFailover);
        }
        String routeAddressFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS_FAILOVER);
        if (StringUtils.isNotEmpty(routeAddressFailover)) {
            System.out.println(DiscoveryConstant.N_D_ADDRESS_FAILOVER + "=" + routeAddressFailover);
        }
        String routeIdBlacklist = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ID_BLACKLIST);
        if (StringUtils.isNotEmpty(routeIdBlacklist)) {
            System.out.println(DiscoveryConstant.N_D_ID_BLACKLIST + "=" + routeIdBlacklist);
        }
        String routeAddressBlacklist = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS_BLACKLIST);
        if (StringUtils.isNotEmpty(routeAddressBlacklist)) {
            System.out.println(DiscoveryConstant.N_D_ADDRESS_BLACKLIST + "=" + routeAddressBlacklist);
        }
        String wareRequestSource = strategyContextHolder.getHeader(DiscoveryConstant.N_DW_REQUEST_SOURCE);
        if (StringUtils.isNotEmpty(wareRequestSource)) {
            System.out.println(DiscoveryConstant.N_DW_REQUEST_SOURCE + "=" + wareRequestSource);
        }

        Map<String, String> customizationMap = strategyMonitorContext.getCustomizationMap();
        if (MapUtils.isNotEmpty(customizationMap)) {
            for (Map.Entry<String, String> entry : customizationMap.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
        }
        System.out.println("------------------------------------------------------------");
    }
}