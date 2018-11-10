package com.nepxion.discovery.plugin.strategy.service.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;

public class ServiceStrategyContextHolder {
    @Autowired
    private ConfigurableEnvironment environment;

    public ServletRequestAttributes getRestAttributes() {
        Boolean hystrixThreadlocalSupported = environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, Boolean.class, Boolean.FALSE);
        if (hystrixThreadlocalSupported) {
            return RestStrategyContext.getCurrentContext().getRequestAttributes();
        } else {
            return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        }
    }

    public Map<String, Object> getRpcAttributes() {
        return RpcStrategyContext.getCurrentContext().getAttributes();
    }
}