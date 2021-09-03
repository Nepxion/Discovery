package com.nepxion.discovery.plugin.strategy.service.wrapper;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Hao Huang
 * @version 1.0
 */

import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.nepxion.discovery.plugin.strategy.monitor.StrategyTracerContext;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.context.RestStrategyContext;
import com.nepxion.discovery.plugin.strategy.service.context.RpcStrategyContext;
import com.nepxion.discovery.plugin.strategy.service.decorator.ServiceStrategyRequestDecoratorFactory;

public class DefaultServiceStrategyCallableWrapper implements ServiceStrategyCallableWrapper {
    @Value("${" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_REST_REQUEST_DECORATOR_ENABLED + ":true}")
    protected Boolean requestDecoratorEnabled;

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        RequestAttributes originRequestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestDecoratorEnabled) {
            if (originRequestAttributes != null) {
                originRequestAttributes = ServiceStrategyRequestDecoratorFactory.decorateRequestAttributes(originRequestAttributes);
            }
        }

        RequestAttributes requestAttributes = originRequestAttributes;

        Map<String, Object> attributes = RpcStrategyContext.getCurrentContext().getAttributes();

        Object span = StrategyTracerContext.getCurrentContext().getSpan();

        return new Callable<T>() {
            @Override
            public T call() throws Exception {
                try {
                    RequestContextHolder.setRequestAttributes(requestAttributes);
                    RestStrategyContext.getCurrentContext().setRequestAttributes(requestAttributes);
                    RpcStrategyContext.getCurrentContext().setAttributes(attributes);

                    StrategyTracerContext.getCurrentContext().setSpan(span);

                    return callable.call();
                } finally {
                    RequestContextHolder.resetRequestAttributes();
                    RestStrategyContext.clearCurrentContext();
                    RpcStrategyContext.clearCurrentContext();

                    StrategyTracerContext.clearCurrentContext();
                }
            }
        };
    }
}