package com.nepxion.discovery.plugin.strategy.service.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Fan Yang
 * @version 1.0
 */

import java.util.Map;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.plugin.strategy.context.AbstractStrategyContextHolder;

public class ServiceStrategyContextHolder extends AbstractStrategyContextHolder {
    public ServletRequestAttributes getRestAttributes() {
        RequestAttributes requestAttributes = RestStrategyContext.getCurrentContext().getRequestAttributes();
        if (requestAttributes == null) {
            requestAttributes = RequestContextHolder.getRequestAttributes();
        }

        return (ServletRequestAttributes) requestAttributes;
    }

    public Map<String, Object> getRpcAttributes() {
        return RpcStrategyContext.getCurrentContext().getAttributes();
    }

    @Override
    public String getHeader(String name) {
        ServletRequestAttributes attributes = getRestAttributes();
        if (attributes == null) {
            // LOG.warn("The ServletRequestAttributes object is lost for thread switched probably");

            return null;
        }

        return attributes.getRequest().getHeader(name);
    }
    
    // 如果配置了内置条件Header，强制使用内置条件Header的模式
    // 该模式只适用于服务层。不希望在服务层处理的这么复杂，且一般情况下，不会在服务层内置条件Header
    // 该模式不适用于网关层。网关层需要处理条件Header外部优先，还是内部优先
    @Override
    protected boolean isInnerConditionHeaderForced() {
        return true;
    }
}