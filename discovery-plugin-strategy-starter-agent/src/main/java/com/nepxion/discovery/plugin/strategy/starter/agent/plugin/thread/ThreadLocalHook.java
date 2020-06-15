package com.nepxion.discovery.plugin.strategy.starter.agent.plugin.thread;

public interface ThreadLocalHook {
    Object create();

    void before(Object object);

    void after();

}
