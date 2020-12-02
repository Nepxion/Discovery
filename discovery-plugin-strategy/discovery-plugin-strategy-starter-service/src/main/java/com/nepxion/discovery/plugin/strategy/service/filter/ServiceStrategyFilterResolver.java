package com.nepxion.discovery.plugin.strategy.service.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;

public class ServiceStrategyFilterResolver {
    public static void setHeader(ServiceStrategyRouteFilterRequest serviceStrategyRouteFilterRequest, String headerName, String headerValue, Boolean serviceHeaderPriority) {
        if (StringUtils.isEmpty(headerValue)) {
            return;
        }

        // 内置策略，例如，AbstractServiceStrategyRouteFilter中String routeVersion = getRouteVersion();是从strategyWrapper.getRouteVersion()获取
        // 服务设置为优先的时候，直接把内置策略加入。在负载均衡前，通过OncePerRequestFilter装饰方式代替掉外界的Header
        if (serviceHeaderPriority) {
            serviceStrategyRouteFilterRequest.addHeader(headerName, headerValue);
        } else {
            // 外界传值为优先的时候，外界已传值，则返回；外界未传值，则需要把内置策略加入
            String originalHeaderValue = serviceStrategyRouteFilterRequest.getOriginalRequest().getHeader(headerName);
            if (StringUtils.isEmpty(originalHeaderValue)) {
                serviceStrategyRouteFilterRequest.addHeader(headerName, headerValue);
            }
        }
    }
}