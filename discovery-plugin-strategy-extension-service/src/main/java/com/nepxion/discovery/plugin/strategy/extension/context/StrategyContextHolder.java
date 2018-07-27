package com.nepxion.discovery.plugin.strategy.extension.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public class StrategyContextHolder {
    private static final ThreadLocal<StrategyContext> HOLDER = new InheritableThreadLocal<StrategyContext>() {
        @Override
        protected StrategyContext initialValue() {
            return new StrategyContext();
        }
    };

    public static StrategyContext currentContext() {
        return HOLDER.get();
    }

    public static void clearContext() {
        HOLDER.remove();
    }
}