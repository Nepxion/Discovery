package com.nepxion.discovery.plugin.strategy.agent.threadlocal;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

public interface ThreadLocalHook {
    Object create();

    void before(Object object);

    void after();
}