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

    @Value("${" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SERVICE_ROUTE_FILTER_ORDER + ":" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_SERVICE_ROUTE_FILTER_ORDER_VALUE + "}")
    protected Integer filterOrder;

    @Override
    public int getOrder() {
        return filterOrder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isExclusion = serviceStrategyFilterExclusion.isExclusion(request, response);
        if (isExclusion) {
            filterChain.doFilter(request, response);

            return;
        }

        // 通过过滤器设置路由Header头部信息，并全链路传递到服务端
        ServiceStrategyRouteFilterRequest serviceStrategyRouteFilterRequest = new ServiceStrategyRouteFilterRequest(request);

        // 处理内部Header的转发
        applyInnerHeader(serviceStrategyRouteFilterRequest);

        // 处理外部Header的转发
        applyOuterHeader(serviceStrategyRouteFilterRequest);

        filterChain.doFilter(serviceStrategyRouteFilterRequest, response);
    }

    // 处理内部Header的转发，即把本地服务的相关属性封装成Header转发到下游服务去
    private void applyInnerHeader(ServiceStrategyRouteFilterRequest serviceStrategyRouteFilterRequest) {
        // 通过Spring OncePerRequestFilter实现Header前置拦截外部Header，内部Header不需要处理
    }

    // 处理外部Header的转发，即外部服务传递过来的Header，中继转发到下游服务去
    private void applyOuterHeader(ServiceStrategyRouteFilterRequest serviceStrategyRouteFilterRequest) {
        // 获取环境匹配路由的配置
        String routeEnvironment = getRouteEnvironment();

        // 设置环境匹配路由的配置到Header中
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

            // 获取版本匹配路由的配置
            String routeVersion = getRouteVersion();

            // 获取区域匹配路由的配置
            String routeRegion = getRouteRegion();

            // 获取IP地址和端口匹配路由的配置
            String routeAddress = getRouteAddress();

            // 获取版本权重路由的配置
            String routeVersionWeight = getRouteVersionWeight();

            // 获取区域权重路由的配置
            String routeRegionWeight = getRouteRegionWeight();

            // 获取全局唯一ID黑名单屏蔽的配置
            String routeIdBlacklist = getRouteIdBlacklist();

            // 获取IP地址和端口黑名单屏蔽的配置
            String routeAddressBlacklist = getRouteAddressBlacklist();

            // 设置版本匹配路由的配置到Header中
            if (StringUtils.isNotEmpty(routeVersion)) {
                ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_VERSION, routeVersion, serviceHeaderPriority);
            }

            // 设置区域匹配路由的配置到Header中
            if (StringUtils.isNotEmpty(routeRegion)) {
                ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_REGION, routeRegion, serviceHeaderPriority);
            }

            // 设置IP地址和端口匹配路由的配置到Header中
            if (StringUtils.isNotEmpty(routeAddress)) {
                ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_ADDRESS, routeAddress, serviceHeaderPriority);
            }

            // 设置版本权重路由的配置到Header中
            if (StringUtils.isNotEmpty(routeVersionWeight)) {
                ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight, serviceHeaderPriority);
            }

            // 设置区域权重路由的配置到Header中
            if (StringUtils.isNotEmpty(routeRegionWeight)) {
                ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight, serviceHeaderPriority);
            }

            // 设置全局唯一ID黑名单屏蔽的配置到Header中
            if (StringUtils.isNotEmpty(routeIdBlacklist)) {
                ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_ID_BLACKLIST, routeIdBlacklist, serviceHeaderPriority);
            }

            // 设置IP地址和端口黑名单屏蔽的配置到Header中
            if (StringUtils.isNotEmpty(routeAddressBlacklist)) {
                ServiceStrategyFilterResolver.setHeader(serviceStrategyRouteFilterRequest, DiscoveryConstant.N_D_ADDRESS_BLACKLIST, routeAddressBlacklist, serviceHeaderPriority);
            }
        }
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }
}