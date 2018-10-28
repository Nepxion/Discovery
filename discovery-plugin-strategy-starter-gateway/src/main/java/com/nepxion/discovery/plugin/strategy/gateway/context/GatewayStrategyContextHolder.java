package com.nepxion.discovery.plugin.strategy.gateway.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.server.ServerWebExchange;

import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;

public class GatewayStrategyContextHolder {
    @Autowired
    private ConfigurableEnvironment environment;

    public ServerWebExchange getExchange() {
        Boolean hystrixThreadlocalSupported = environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, Boolean.class, Boolean.FALSE);
        if (hystrixThreadlocalSupported) {
            // Spring Cloud Gateway网关端使用Hystrix做线程模式的服务隔离时，实现服务灰度路由的功能
            return GatewayStrategyContext.getCurrentContext().getExchange();
        } else {
            return GatewayStrategyContext.getCurrentContext().getExchange();
        }
    }
}