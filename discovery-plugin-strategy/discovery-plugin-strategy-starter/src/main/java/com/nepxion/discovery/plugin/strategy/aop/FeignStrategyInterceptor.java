package com.nepxion.discovery.plugin.strategy.aop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Fengfeng Li
 * @version 1.0
 */

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.InterceptorType;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.util.StrategyUtil;

public class FeignStrategyInterceptor extends AbstractStrategyInterceptor implements RequestInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(FeignStrategyInterceptor.class);

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    // Feign上核心策略Header是否传递。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能
    // 核心策略Header指n-d-开头的Header（不包括n-d-env，因为环境路由隔离，必须传递该Header），不包括n-d-service开头的Header
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_FEIGN_CORE_HEADER_TRANSMISSION_ENABLED + ":true}")
    protected Boolean feignCoreHeaderTransmissionEnabled;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 拦截打印输入的Header
        interceptInputHeader();

        // 处理内部Header的转发
        applyInnerHeader(requestTemplate);

        // 处理外部Header的转发
        applyOuterHeader(requestTemplate);

        // 拦截打印输出的Header
        interceptOutputHeader(requestTemplate);
    }

    // 处理内部Header的转发，即把本地服务的相关属性封装成Header转发到下游服务去
    private void applyInnerHeader(RequestTemplate requestTemplate) {
        requestTemplate.header(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        requestTemplate.header(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            requestTemplate.header(DiscoveryConstant.N_D_SERVICE_APP_ID, serviceAppId);
        }
        requestTemplate.header(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
        requestTemplate.header(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        String version = pluginAdapter.getVersion();
        if (StringUtils.isNotEmpty(version) && !StringUtils.equals(version, DiscoveryConstant.DEFAULT)) {
            requestTemplate.header(DiscoveryConstant.N_D_SERVICE_VERSION, version);
        }
        String region = pluginAdapter.getRegion();
        if (StringUtils.isNotEmpty(region) && !StringUtils.equals(region, DiscoveryConstant.DEFAULT)) {
            requestTemplate.header(DiscoveryConstant.N_D_SERVICE_REGION, region);
        }
        String environment = pluginAdapter.getEnvironment();
        if (StringUtils.isNotEmpty(environment) && !StringUtils.equals(environment, DiscoveryConstant.DEFAULT)) {
            requestTemplate.header(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, environment);
        }
        String zone = pluginAdapter.getZone();
        if (StringUtils.isNotEmpty(zone) && !StringUtils.equals(zone, DiscoveryConstant.DEFAULT)) {
            requestTemplate.header(DiscoveryConstant.N_D_SERVICE_ZONE, zone);
        }
    }

    // 处理外部Header的转发，即外部服务传递过来的Header，中继转发到下游服务去
    private void applyOuterHeader(RequestTemplate requestTemplate) {
        Enumeration<String> headerNames = strategyContextHolder.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = strategyContextHolder.getHeader(headerName);
                boolean isHeaderContains = isHeaderContainsExcludeInner(headerName.toLowerCase());
                if (isHeaderContains) {
                    if (feignCoreHeaderTransmissionEnabled) {
                        requestTemplate.header(headerName, headerValue);
                    } else {
                        boolean isCoreHeaderContains = StrategyUtil.isCoreHeaderContains(headerName);
                        if (!isCoreHeaderContains) {
                            requestTemplate.header(headerName, headerValue);
                        }
                    }
                }
            }
        }

        if (feignCoreHeaderTransmissionEnabled) {
            Map<String, Collection<String>> headers = requestTemplate.headers();
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION))) {
                String routeVersion = strategyContextHolder.getRouteVersion();
                if (StringUtils.isNotEmpty(routeVersion)) {
                    requestTemplate.header(DiscoveryConstant.N_D_VERSION, routeVersion);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION))) {
                String routeRegion = strategyContextHolder.getRouteRegion();
                if (StringUtils.isNotEmpty(routeRegion)) {
                    requestTemplate.header(DiscoveryConstant.N_D_REGION, routeRegion);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ENVIRONMENT))) {
                String routeEnvironment = strategyContextHolder.getRouteEnvironment();
                if (StringUtils.isNotEmpty(routeEnvironment)) {
                    requestTemplate.header(DiscoveryConstant.N_D_ENVIRONMENT, routeEnvironment);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ADDRESS))) {
                String routeAddress = strategyContextHolder.getRouteAddress();
                if (StringUtils.isNotEmpty(routeAddress)) {
                    requestTemplate.header(DiscoveryConstant.N_D_ADDRESS, routeAddress);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION_WEIGHT))) {
                String routeVersionWeight = strategyContextHolder.getRouteVersionWeight();
                if (StringUtils.isNotEmpty(routeVersionWeight)) {
                    requestTemplate.header(DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION_WEIGHT))) {
                String routeRegionWeight = strategyContextHolder.getRouteRegionWeight();
                if (StringUtils.isNotEmpty(routeRegionWeight)) {
                    requestTemplate.header(DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION_PREFER))) {
                String routeVersionPrefer = strategyContextHolder.getRouteVersionPrefer();
                if (StringUtils.isNotEmpty(routeVersionPrefer)) {
                    requestTemplate.header(DiscoveryConstant.N_D_VERSION_PREFER, routeVersionPrefer);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION_FAILOVER))) {
                String routeVersionFailover = strategyContextHolder.getRouteVersionFailover();
                if (StringUtils.isNotEmpty(routeVersionFailover)) {
                    requestTemplate.header(DiscoveryConstant.N_D_VERSION_FAILOVER, routeVersionFailover);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION_TRANSFER))) {
                String routeRegionTransfer = strategyContextHolder.getRouteRegionTransfer();
                if (StringUtils.isNotEmpty(routeRegionTransfer)) {
                    requestTemplate.header(DiscoveryConstant.N_D_REGION_TRANSFER, routeRegionTransfer);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION_FAILOVER))) {
                String routeRegionFailover = strategyContextHolder.getRouteRegionFailover();
                if (StringUtils.isNotEmpty(routeRegionFailover)) {
                    requestTemplate.header(DiscoveryConstant.N_D_REGION_FAILOVER, routeRegionFailover);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ENVIRONMENT_FAILOVER))) {
                String routeEnvironmentFailover = strategyContextHolder.getRouteEnvironmentFailover();
                if (StringUtils.isNotEmpty(routeEnvironmentFailover)) {
                    requestTemplate.header(DiscoveryConstant.N_D_ENVIRONMENT_FAILOVER, routeEnvironmentFailover);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ZONE_FAILOVER))) {
                String routeZoneFailover = strategyContextHolder.getRouteZoneFailover();
                if (StringUtils.isNotEmpty(routeZoneFailover)) {
                    requestTemplate.header(DiscoveryConstant.N_D_ZONE_FAILOVER, routeZoneFailover);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ADDRESS_FAILOVER))) {
                String routeAddressFailover = strategyContextHolder.getRouteAddressFailover();
                if (StringUtils.isNotEmpty(routeAddressFailover)) {
                    requestTemplate.header(DiscoveryConstant.N_D_ADDRESS_FAILOVER, routeAddressFailover);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ID_BLACKLIST))) {
                String routeIdBlacklist = strategyContextHolder.getRouteIdBlacklist();
                if (StringUtils.isNotEmpty(routeIdBlacklist)) {
                    requestTemplate.header(DiscoveryConstant.N_D_ID_BLACKLIST, routeIdBlacklist);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ADDRESS_BLACKLIST))) {
                String routeAddressBlacklist = strategyContextHolder.getRouteAddressBlacklist();
                if (StringUtils.isNotEmpty(routeAddressBlacklist)) {
                    requestTemplate.header(DiscoveryConstant.N_D_ADDRESS_BLACKLIST, routeAddressBlacklist);
                }
            }
        }
    }

    private void interceptOutputHeader(RequestTemplate requestTemplate) {
        if (!interceptDebugEnabled) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append("-------- Feign Intercept Output Header Information ---------").append("\n");
        Map<String, Collection<String>> headers = requestTemplate.headers();
        for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            boolean isHeaderContains = isHeaderContains(headerName.toLowerCase());
            if (isHeaderContains) {
                Collection<String> headerValue = entry.getValue();

                stringBuilder.append(headerName + "=" + headerValue).append("\n");
            }
        }
        stringBuilder.append("------------------------------------------------------------");
        LOG.info(stringBuilder.toString());
    }

    @Override
    protected InterceptorType getInterceptorType() {
        return InterceptorType.FEIGN;
    }

    @Override
    protected Logger getInterceptorLogger() {
        return LOG;
    }
}