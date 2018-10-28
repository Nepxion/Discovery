package com.nepxion.discovery.plugin.strategy.zuul.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.netflix.zuul.context.RequestContext;

public class ZuulStrategyContextHolder {
    @Autowired
    private ConfigurableEnvironment environment;

    public HttpServletRequest getRequest() {
        Boolean hystrixThreadlocalSupported = environment.getProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, Boolean.class, Boolean.FALSE);
        if (hystrixThreadlocalSupported) {
            return ZuulStrategyContext.getCurrentContext().getRequest();
        } else {
            return RequestContext.getCurrentContext().getRequest();
        }
    }
}