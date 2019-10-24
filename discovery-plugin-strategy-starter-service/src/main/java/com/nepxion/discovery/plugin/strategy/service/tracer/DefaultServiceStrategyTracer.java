package com.nepxion.discovery.plugin.strategy.service.tracer;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.aopalliance.intercept.MethodInvocation;

import com.nepxion.discovery.plugin.strategy.tracer.StrategyTracer;

public class DefaultServiceStrategyTracer extends StrategyTracer implements ServiceStrategyTracer {
    @Override
    public void trace(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {
        mdcLocal();
        debugLocal();
    }

    @Override
    public void error(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation, Throwable e) {
        // 一般来说，日志方式对异常不需要做特殊处理，但必须也要把上下文参数放在MDC里，否则链路中异常环节会串不起来
        mdcLocal();
    }

    @Override
    public void release(ServiceStrategyTracerInterceptor interceptor, MethodInvocation invocation) {
        mdcClear();
    }
}