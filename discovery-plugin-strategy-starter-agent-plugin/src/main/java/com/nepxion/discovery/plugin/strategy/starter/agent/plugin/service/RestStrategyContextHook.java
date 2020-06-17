package com.nepxion.discovery.plugin.strategy.starter.agent.plugin.service;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

import java.util.Map;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.nepxion.discovery.plugin.strategy.service.context.RestStrategyContext;
import com.nepxion.discovery.plugin.strategy.service.context.RpcStrategyContext;
import com.nepxion.discovery.plugin.strategy.starter.agent.threadlocal.ThreadLocalHook;

public class RestStrategyContextHook implements ThreadLocalHook {
    @Override
    public Object create() {
        RequestAttributes request = RequestContextHolder.getRequestAttributes();
        Map<String, Object> attributes = RpcStrategyContext.getCurrentContext().getAttributes();

        return new Object[] { request, attributes };
    }

    @SuppressWarnings("unchecked")
    @Override
    public void before(Object object) {
        if (object.getClass().isArray()) {
            Object[] objects = (Object[]) object;

            if (objects[0] instanceof RequestAttributes) {
                RestStrategyContext.getCurrentContext().setRequestAttributes((RequestAttributes) objects[0]);
            }
            if (objects[1] instanceof Map) {
                RpcStrategyContext.getCurrentContext().setAttributes((Map<String, Object>) objects[1]);
            }
        }
    }

    @Override
    public void after() {
        RestStrategyContext.clearCurrentContext();
        RpcStrategyContext.clearCurrentContext();
    }
}