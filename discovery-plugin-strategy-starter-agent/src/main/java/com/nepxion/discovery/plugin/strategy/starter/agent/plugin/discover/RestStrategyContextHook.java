package com.nepxion.discovery.plugin.strategy.starter.agent.plugin.discover;


import com.nepxion.discovery.plugin.strategy.service.context.RestStrategyContext;
import com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread.ThreadLocalHook;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class RestStrategyContextHook implements ThreadLocalHook {
    @Override
    public Object create() {
        return RequestContextHolder.getRequestAttributes();
    }

    @Override
    public void before(Object object) {
        if (object instanceof RequestAttributes) {
            RestStrategyContext.getCurrentContext().setRequestAttributes((RequestAttributes) object);
        }
    }

    @Override
    public void after() {
        RestStrategyContext.clearCurrentContext();
    }
}
