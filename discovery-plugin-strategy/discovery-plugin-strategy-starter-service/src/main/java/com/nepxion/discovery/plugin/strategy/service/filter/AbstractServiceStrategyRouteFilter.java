package com.nepxion.discovery.plugin.strategy.service.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyWrapper;

public abstract class AbstractServiceStrategyRouteFilter extends ServiceStrategyRouteFilter {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyWrapper strategyWrapper;

    @Autowired
    protected ServiceStrategyFilterExclusion serviceStrategyFilterExclusion;

    // 如果外界也传了相同的Header，例如，从Postman传递过来的Header，当下面的变量为true，以服务设置为优先，否则以外界传值为优先
    @Value("${" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SERVICE_HEADER_PRIORITY + ":true}")
    protected Boolean serviceHeaderPriority;

    // Service上核心策略Header是否传递。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能。核心策略Header，包含如下
    // 1. n-d-version
    // 2. n-d-region
    // 3. n-d-address
    // 4. n-d-version-weight
    // 5. n-d-region-weight
    // 6. n-d-id-blacklist
    // 7. n-d-address-blacklist
    // 8. n-d-env (不属于蓝绿灰度范畴的Header，只要外部传入就会全程传递)
    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_FEIGN_CORE_HEADER_TRANSMISSION_ENABLED + ":true}")
    protected Boolean feignCoreHeaderTransmissionEnabled;

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_REST_TEMPLATE_CORE_HEADER_TRANSMISSION_ENABLED + ":true}")
    protected Boolean restTemplateCoreHeaderTransmissionEnabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isExclusion = serviceStrategyFilterExclusion.isExclusion(request, response);
        if (isExclusion) {
            filterChain.doFilter(request, response);

            return;
        }

        ServiceStrategyRouteFilterRequest serviceStrategyRouteFilterRequest = new ServiceStrategyRouteFilterRequest(request);

        String routeEnvironment = getRouteEnvironment();
        // 通过过滤器设置路由Header头部信息，并全链路传递到服务端
        if (StringUtils.isNotEmpty(routeEnvironment)) {
            ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_ENVIRONMENT, routeEnvironment, false);
        }

        if (feignCoreHeaderTransmissionEnabled || restTemplateCoreHeaderTransmissionEnabled) {
            // 内置Header预先塞入
            Map<String, String> headerMap = strategyWrapper.getHeaderMap();
            if (MapUtils.isNotEmpty(headerMap)) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, key, value, serviceHeaderPriority);
                }
            }

            String routeVersion = getRouteVersion();
            String routeRegion = getRouteRegion();
            String routeAddress = getRouteAddress();
            String routeVersionWeight = getRouteVersionWeight();
            String routeRegionWeight = getRouteRegionWeight();
            String routeIdBlacklist = getRouteIdBlacklist();
            String routeAddressBlacklist = getRouteAddressBlacklist();
            if (StringUtils.isNotEmpty(routeVersion)) {
                ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_VERSION, routeVersion, serviceHeaderPriority);
            }
            if (StringUtils.isNotEmpty(routeRegion)) {
                ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_REGION, routeRegion, serviceHeaderPriority);
            }
            if (StringUtils.isNotEmpty(routeAddress)) {
                ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_ADDRESS, routeAddress, serviceHeaderPriority);
            }
            if (StringUtils.isNotEmpty(routeVersionWeight)) {
                ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight, serviceHeaderPriority);
            }
            if (StringUtils.isNotEmpty(routeRegionWeight)) {
                ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight, serviceHeaderPriority);
            }
            if (StringUtils.isNotEmpty(routeIdBlacklist)) {
                ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_ID_BLACKLIST, routeIdBlacklist, serviceHeaderPriority);
            }
            if (StringUtils.isNotEmpty(routeAddressBlacklist)) {
                ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_ADDRESS_BLACKLIST, routeAddressBlacklist, serviceHeaderPriority);
            }
        }

        filterChain.doFilter(serviceStrategyRouteFilterRequest, response);
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }
}