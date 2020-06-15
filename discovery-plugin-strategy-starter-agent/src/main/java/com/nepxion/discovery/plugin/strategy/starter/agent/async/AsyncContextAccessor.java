package com.nepxion.discovery.plugin.strategy.starter.agent.async;

/**
 * @author Woonduk Kang(emeroad)
 */
public interface AsyncContextAccessor {
    void setAsyncContext(AsyncContext asyncContext);
    AsyncContext getAsyncContext();
}
