package com.nepxion.discovery.plugin.strategy.gateway.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

public class GatewayStrategyFilterResolver {
    public static void setHeader(ServerHttpRequest.Builder requestBuilder, String headerName, String headerValue, Boolean gatewayHeaderPriority) {
        if (StringUtils.isEmpty(headerValue)) {
            return;
        }

        if (gatewayHeaderPriority) {
            // 需要把外界的Header清除
            requestBuilder.headers(headers -> headers.remove(headerName));
        }

        // 不管外界是否传递了Header，网关侧都加入Header
        // 当外界没传递了Header，由网关侧Header来替代
        // 当外界传递了Header，虽然网关侧也添加了Header，但传递到调用链的还是第一个Header。参考exchange.getRequest().getHeaders().getFirst(name)
        // 在spring-web-5.1.9.RELEASE版本中，requestBuilder.header(headerName, headerValue)已经标识为@Deprecated，为兼容5.1.8版本，改为如下代码
        requestBuilder.headers(headers -> headers.add(headerName, headerValue));
    }

    public static void ignoreHeader(ServerHttpRequest.Builder requestBuilder, String headerName, Boolean gatewayHeaderPriority, Boolean gatewayOriginalHeaderIgnored) {
        if (gatewayHeaderPriority && gatewayOriginalHeaderIgnored) {
            ignoreHeader(requestBuilder, headerName);
        }
    }

    public static void ignoreHeader(ServerHttpRequest.Builder requestBuilder, String headerName) {
        // 需要把外界的Header清除
        requestBuilder.headers(headers -> headers.remove(headerName));
    }
}