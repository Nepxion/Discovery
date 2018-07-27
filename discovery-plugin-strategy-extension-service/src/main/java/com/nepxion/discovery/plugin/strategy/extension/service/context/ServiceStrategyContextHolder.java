package com.nepxion.discovery.plugin.strategy.extension.service.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public class ServiceStrategyContextHolder {
    private static final ThreadLocal<ServiceStrategyContext> HOLDER = new InheritableThreadLocal<ServiceStrategyContext>() {
        @Override
        protected ServiceStrategyContext initialValue() {
            return new ServiceStrategyContext();
        }
    };

    public static ServiceStrategyContext currentContext() {
        return HOLDER.get();
    }

    public static void clearContext() {
        HOLDER.remove();
    }
}