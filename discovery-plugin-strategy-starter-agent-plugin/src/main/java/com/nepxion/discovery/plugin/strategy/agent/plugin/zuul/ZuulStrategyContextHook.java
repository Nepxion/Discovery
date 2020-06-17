package com.nepxion.discovery.plugin.strategy.agent.plugin.zuul;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.nepxion.discovery.plugin.strategy.agent.threadlocal.ThreadLocalHook;
import com.nepxion.discovery.plugin.strategy.zuul.context.ZuulStrategyContext;
import com.netflix.zuul.context.RequestContext;

public class ZuulStrategyContextHook implements ThreadLocalHook {
    @Override
    public Object create() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        Map<String, String> headers = RequestContext.getCurrentContext().getZuulRequestHeaders();

        return new Object[] { request, headers };
    }

    @SuppressWarnings("unchecked")
    @Override
    public void before(Object object) {
        if (object.getClass().isArray()) {
            Object[] objects = (Object[]) object;

            if (objects[0] instanceof HttpServletRequest) {
                ZuulStrategyContext.getCurrentContext().setRequest((HttpServletRequest) objects[0]);
            }
            if (objects[1] instanceof Map) {
                ZuulStrategyContext.getCurrentContext().setHeaders((Map<String, String>) objects[1]);
            }
        }
    }

    @Override
    public void after() {
        ZuulStrategyContext.clearCurrentContext();
    }
}