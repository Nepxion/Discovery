package com.nepxion.discovery.plugin.strategy.agent.plugin.mdc;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author HaojunRen
 * @version 1.0
 */

import java.util.Map;

import org.slf4j.MDC;

import com.nepxion.discovery.plugin.strategy.agent.threadlocal.AbstractThreadLocalHook;

public class MDCContextHook extends AbstractThreadLocalHook {
    @Override
    public Object create() {
        return MDC.getCopyOfContextMap();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void before(Object object) {
        if (object instanceof Map) {
            MDC.setContextMap((Map<String, String>) object);
        }
    }

    @Override
    public void after() {
        MDC.clear();
    }
}