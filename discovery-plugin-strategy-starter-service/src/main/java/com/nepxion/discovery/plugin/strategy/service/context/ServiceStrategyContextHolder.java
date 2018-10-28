package com.nepxion.discovery.plugin.strategy.service.context;

import java.util.Map;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;

public class ServiceStrategyContextHolder {
    @Autowired
    private ConfigurableEnvironment environment;

    public ServletRequestAttributes getRequestAttributes() {
        Boolean hystrixThreadlocalSupported = environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, Boolean.class, Boolean.FALSE);
        if (hystrixThreadlocalSupported) {
            // 服务端使用Hystrix做线程模式的服务隔离时，实现服务灰度路由的功能，预留待实现
            return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        } else {
            return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        }
    }

    public Map<String, Object> getMethodAttributes() {
        return ServiceStrategyContext.getCurrentContext().getAttributes();
    }
}