package com.nepxion.discovery.plugin.strategy.agent.async;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zifeihan
 * @version 1.0
 */

public class AsyncContext {
    private Object[] objects;

    public AsyncContext(Object[] objects) {
        this.objects = objects;
    }

    public Object[] getObjects() {
        return objects;
    }
}