package com.nepxion.discovery.plugin.strategy.starter.agent.plugin.discovery.zuul;

import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread.ThreadLocalHook;
import com.nepxion.discovery.plugin.strategy.zuul.context.ZuulStrategyContext;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author zifeihan
 * @version 1.0
 */
public class ZuulStrategyContextHook implements ThreadLocalHook {
    @Override
    public Object create() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        Map<String, String> headers = RequestContext.getCurrentContext().getZuulRequestHeaders();
        return new Object[]{request, headers};
    }

    @Override
    public void before(Object object) {
        if (object.getClass().isArray()) {
            Object[] objects = (Object[]) object;

            if (objects[0] instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) objects[0];
                ZuulStrategyContext.getCurrentContext().setRequest(request);
            }
            if (objects[1] instanceof Map) {
                Map<String, String> headers = (Map<String, String>) objects[1];
                ZuulStrategyContext.getCurrentContext().setHeaders(headers);
            }
        }
    }

    @Override
    public void after() {
        ZuulStrategyContext.clearCurrentContext();
    }
}
