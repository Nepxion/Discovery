package com.nepxion.discovery.plugin.strategy.zuul.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyCustomizationEntity;
import com.nepxion.discovery.common.entity.StrategyHeaderEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.zuul.constant.ZuulStrategyConstant;
import com.nepxion.discovery.plugin.strategy.zuul.tracer.ZuulStrategyTracer;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public abstract class AbstractZuulStrategyRouteFilter extends ZuulFilter implements ZuulStrategyRouteFilter {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Autowired(required = false)
    private List<ZuulStrategyTracer> zuulStrategyTracerList;

    // 如果外界也传了相同的Header，例如，从Postman传递过来的Header，当下面的变量为true，以网关设置为优先，否则以外界传值为优先
    @Value("${" + ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_HEADER_PRIORITY + ":true}")
    protected Boolean zuulHeaderPriority;

    // 当以网关设置为优先的时候，网关未配置Header，而外界配置了Header，仍旧忽略外界的Header
    @Value("${" + ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_ORIGINAL_HEADER_IGNORED + ":true}")
    protected Boolean zuulOriginalHeaderIgnored;

    @Value("${" + ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_ROUTE_FILTER_ORDER + ":" + ZuulStrategyConstant.SPRING_APPLICATION_STRATEGY_ZUUL_ROUTE_FILTER_ORDER_VALUE + "}")
    protected Integer filterOrder;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return filterOrder;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyCustomizationEntity strategyCustomizationEntity = ruleEntity.getStrategyCustomizationEntity();
            if (strategyCustomizationEntity != null) {
                StrategyHeaderEntity strategyHeaderEntity = strategyCustomizationEntity.getStrategyHeaderEntity();
                if (strategyHeaderEntity != null) {
                    Map<String, String> headerMap = strategyHeaderEntity.getHeaderMap();
                    for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();

                        ZuulStrategyFilterResolver.setHeader(key, value, zuulHeaderPriority);
                    }
                }
            }
        }

        String routeVersion = getRouteVersion();
        String routeRegion = getRouteRegion();
        String routeAddress = getRouteAddress();
        String routeVersionWeight = getRouteVersionWeight();
        String routeRegionWeight = getRouteRegionWeight();

        // 通过过滤器设置路由Header头部信息，并全链路传递到服务端
        if (StringUtils.isNotEmpty(routeVersion)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_VERSION, routeVersion, zuulHeaderPriority);
        } else {
            ZuulStrategyFilterResolver.ignoreHeader(DiscoveryConstant.N_D_VERSION, zuulHeaderPriority, zuulOriginalHeaderIgnored);
        }
        if (StringUtils.isNotEmpty(routeRegion)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_REGION, routeRegion, zuulHeaderPriority);
        } else {
            ZuulStrategyFilterResolver.ignoreHeader(DiscoveryConstant.N_D_REGION, zuulHeaderPriority, zuulOriginalHeaderIgnored);
        }
        if (StringUtils.isNotEmpty(routeAddress)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_ADDRESS, routeAddress, zuulHeaderPriority);
        } else {
            ZuulStrategyFilterResolver.ignoreHeader(DiscoveryConstant.N_D_ADDRESS, zuulHeaderPriority, zuulOriginalHeaderIgnored);
        }
        if (StringUtils.isNotEmpty(routeVersionWeight)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight, zuulHeaderPriority);
        } else {
            ZuulStrategyFilterResolver.ignoreHeader(DiscoveryConstant.N_D_VERSION_WEIGHT, zuulHeaderPriority, zuulOriginalHeaderIgnored);
        }
        if (StringUtils.isNotEmpty(routeRegionWeight)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight, zuulHeaderPriority);
        } else {
            ZuulStrategyFilterResolver.ignoreHeader(DiscoveryConstant.N_D_REGION_WEIGHT, zuulHeaderPriority, zuulOriginalHeaderIgnored);
        }

        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup(), zuulHeaderPriority);
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType(), zuulHeaderPriority);
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_SERVICE_APP_ID, serviceAppId, zuulHeaderPriority);
        }
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId(), zuulHeaderPriority);
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort(), zuulHeaderPriority);
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion(), zuulHeaderPriority);
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion(), zuulHeaderPriority);
        ZuulStrategyFilterResolver.setHeader(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, pluginAdapter.getEnvironment(), zuulHeaderPriority);

        extendFilter();

        // 调用链追踪
        RequestContext context = RequestContext.getCurrentContext();
        if (CollectionUtils.isNotEmpty(zuulStrategyTracerList)) {
            for (ZuulStrategyTracer zuulStrategyTracer : zuulStrategyTracerList) {
                zuulStrategyTracer.trace(context);
            }
        }

        return null;
    }

    protected void extendFilter() {

    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }

    public StrategyContextHolder getStrategyContextHolder() {
        return strategyContextHolder;
    }
}