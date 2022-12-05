package com.nepxion.discovery.plugin.strategy.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.context.StrategyHeaderContext;

public class DefaultStrategyLogger implements StrategyLogger {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultStrategyLogger.class);

    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Autowired
    protected StrategyMonitorContext strategyMonitorContext;

    @Autowired
    protected StrategyHeaderContext strategyHeaderContext;

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

        String traceId = strategyMonitorContext.getTraceId();
        String spanId = strategyMonitorContext.getSpanId();
        if (StringUtils.isNotEmpty(traceId)) {
            MDC.put(DiscoveryConstant.TRACE_ID, (loggerMdcKeyShown ? DiscoveryConstant.TRACE_ID + "=" : StringUtils.EMPTY) + traceId);
        }
        if (StringUtils.isNotEmpty(spanId)) {
            MDC.put(DiscoveryConstant.SPAN_ID, (loggerMdcKeyShown ? DiscoveryConstant.SPAN_ID + "=" : StringUtils.EMPTY) + spanId);
        }
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

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append("--------------- Strategy Logger Information ----------------").append("\n");
        String traceId = strategyMonitorContext.getTraceId();
        String spanId = strategyMonitorContext.getSpanId();
        if (StringUtils.isNotEmpty(traceId)) {
            stringBuilder.append(DiscoveryConstant.TRACE_ID + "=" + traceId).append("\n");
        }
        if (StringUtils.isNotEmpty(spanId)) {
            stringBuilder.append(DiscoveryConstant.SPAN_ID + "=" + spanId).append("\n");
        }
        stringBuilder.append(DiscoveryConstant.N_D_SERVICE_GROUP + "=" + pluginAdapter.getGroup()).append("\n");
        stringBuilder.append(DiscoveryConstant.N_D_SERVICE_TYPE + "=" + pluginAdapter.getServiceType()).append("\n");
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            stringBuilder.append(DiscoveryConstant.N_D_SERVICE_APP_ID + "=" + serviceAppId).append("\n");
        }
        stringBuilder.append(DiscoveryConstant.N_D_SERVICE_ID + "=" + pluginAdapter.getServiceId()).append("\n");
        stringBuilder.append(DiscoveryConstant.N_D_SERVICE_ADDRESS + "=" + pluginAdapter.getHost() + ":" + pluginAdapter.getPort()).append("\n");
        String version = pluginAdapter.getVersion();
        if (StringUtils.isNotEmpty(version) && !StringUtils.equals(version, DiscoveryConstant.DEFAULT)) {
            stringBuilder.append(DiscoveryConstant.N_D_SERVICE_VERSION + "=" + version).append("\n");
        }
        String region = pluginAdapter.getRegion();
        if (StringUtils.isNotEmpty(region) && !StringUtils.equals(region, DiscoveryConstant.DEFAULT)) {
            stringBuilder.append(DiscoveryConstant.N_D_SERVICE_REGION + "=" + region).append("\n");
        }
        String environment = pluginAdapter.getEnvironment();
        if (StringUtils.isNotEmpty(environment) && !StringUtils.equals(environment, DiscoveryConstant.DEFAULT)) {
            stringBuilder.append(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT + "=" + environment).append("\n");
        }
        String zone = pluginAdapter.getZone();
        if (StringUtils.isNotEmpty(zone) && !StringUtils.equals(zone, DiscoveryConstant.DEFAULT)) {
            stringBuilder.append(DiscoveryConstant.N_D_SERVICE_ZONE + "=" + zone).append("\n");
        }

        String routeVersion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION);
        if (StringUtils.isNotEmpty(routeVersion)) {
            stringBuilder.append(DiscoveryConstant.N_D_VERSION + "=" + routeVersion).append("\n");
        }
        String routeRegion = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION);
        if (StringUtils.isNotEmpty(routeRegion)) {
            stringBuilder.append(DiscoveryConstant.N_D_REGION + "=" + routeRegion).append("\n");
        }
        String routeEnvironment = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ENVIRONMENT);
        if (StringUtils.isNotEmpty(routeEnvironment)) {
            stringBuilder.append(DiscoveryConstant.N_D_ENVIRONMENT + "=" + routeEnvironment).append("\n");
        }
        String routeAddress = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS);
        if (StringUtils.isNotEmpty(routeAddress)) {
            stringBuilder.append(DiscoveryConstant.N_D_ADDRESS + "=" + routeAddress).append("\n");
        }
        String routeVersionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_WEIGHT);
        if (StringUtils.isNotEmpty(routeVersionWeight)) {
            stringBuilder.append(DiscoveryConstant.N_D_VERSION_WEIGHT + "=" + routeVersionWeight).append("\n");
        }
        String routeRegionWeight = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_WEIGHT);
        if (StringUtils.isNotEmpty(routeRegionWeight)) {
            stringBuilder.append(DiscoveryConstant.N_D_REGION_WEIGHT + "=" + routeRegionWeight).append("\n");
        }
        String routeVersionPrefer = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_PREFER);
        if (StringUtils.isNotEmpty(routeVersionPrefer)) {
            stringBuilder.append(DiscoveryConstant.N_D_VERSION_PREFER + "=" + routeVersionPrefer).append("\n");
        }
        String routeVersionFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_VERSION_FAILOVER);
        if (StringUtils.isNotEmpty(routeVersionFailover)) {
            stringBuilder.append(DiscoveryConstant.N_D_VERSION_FAILOVER + "=" + routeVersionFailover).append("\n");
        }
        String routeRegionTransfer = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_TRANSFER);
        if (StringUtils.isNotEmpty(routeRegionTransfer)) {
            stringBuilder.append(DiscoveryConstant.N_D_REGION_TRANSFER + "=" + routeRegionTransfer).append("\n");
        }
        String routeRegionFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_REGION_FAILOVER);
        if (StringUtils.isNotEmpty(routeRegionFailover)) {
            stringBuilder.append(DiscoveryConstant.N_D_REGION_FAILOVER + "=" + routeRegionFailover).append("\n");
        }
        String routeEnvironmentFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ENVIRONMENT_FAILOVER);
        if (StringUtils.isNotEmpty(routeEnvironmentFailover)) {
            stringBuilder.append(DiscoveryConstant.N_D_ENVIRONMENT_FAILOVER + "=" + routeEnvironmentFailover).append("\n");
        }
        String routeZoneFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ZONE_FAILOVER);
        if (StringUtils.isNotEmpty(routeZoneFailover)) {
            stringBuilder.append(DiscoveryConstant.N_D_ZONE_FAILOVER + "=" + routeZoneFailover).append("\n");
        }
        String routeAddressFailover = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS_FAILOVER);
        if (StringUtils.isNotEmpty(routeAddressFailover)) {
            stringBuilder.append(DiscoveryConstant.N_D_ADDRESS_FAILOVER + "=" + routeAddressFailover).append("\n");
        }
        String routeIdBlacklist = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ID_BLACKLIST);
        if (StringUtils.isNotEmpty(routeIdBlacklist)) {
            stringBuilder.append(DiscoveryConstant.N_D_ID_BLACKLIST + "=" + routeIdBlacklist).append("\n");
        }
        String routeAddressBlacklist = strategyContextHolder.getHeader(DiscoveryConstant.N_D_ADDRESS_BLACKLIST);
        if (StringUtils.isNotEmpty(routeAddressBlacklist)) {
            stringBuilder.append(DiscoveryConstant.N_D_ADDRESS_BLACKLIST + "=" + routeAddressBlacklist).append("\n");
        }

        List<String> requestHeaderNameList = strategyHeaderContext.getRequestHeaderNameList();
        if (CollectionUtils.isNotEmpty(requestHeaderNameList)) {
            for (String requestHeaderName : requestHeaderNameList) {
                String requestHeaderValue = strategyContextHolder.getHeader(requestHeaderName);
                if (StringUtils.isNotEmpty(requestHeaderValue)) {
                    stringBuilder.append(requestHeaderName + "=" + requestHeaderValue).append("\n");
                }
            }
        }
        stringBuilder.append("------------------------------------------------------------");
        LOG.info(stringBuilder.toString());
    }
}