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

import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyCustomizationEntity;
import com.nepxion.discovery.common.entity.StrategyHeaderEntity;
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

    @Override
    public String getRouteVersion() {
        Map<String, String> headerMap = getHeaderMap();
        if (headerMap == null) {
            return super.getRouteVersion();
        } else {
            return strategyWrapper.getRouteVersion(headerMap);
        }
    }

    @Override
    public String getRouteRegion() {
        Map<String, String> headerMap = getHeaderMap();
        if (headerMap == null) {
            return super.getRouteRegion();
        } else {
            return strategyWrapper.getRouteRegion(headerMap);
        }
    }

    @Override
    public String getRouteAddress() {
        Map<String, String> headerMap = getHeaderMap();
        if (headerMap == null) {
            return super.getRouteAddress();
        } else {
            return strategyWrapper.getRouteAddress(headerMap);
        }
    }

    @Override
    public String getRouteVersionWeight() {
        Map<String, String> headerMap = getHeaderMap();
        if (headerMap == null) {
            return super.getRouteVersionWeight();
        } else {
            return strategyWrapper.getRouteVersionWeight(headerMap);
        }
    }

    @Override
    public String getRouteRegionWeight() {
        Map<String, String> headerMap = getHeaderMap();
        if (headerMap == null) {
            return super.getRouteRegionWeight();
        } else {
            return strategyWrapper.getRouteRegionWeight(headerMap);
        }
    }

    public Map<String, String> getHeaderMap() {
        // 内置Header
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyCustomizationEntity strategyCustomizationEntity = ruleEntity.getStrategyCustomizationEntity();
            if (strategyCustomizationEntity != null) {
                StrategyHeaderEntity strategyHeaderEntity = strategyCustomizationEntity.getStrategyHeaderEntity();
                if (strategyHeaderEntity != null) {
                    return strategyHeaderEntity.getHeaderMap();
                }
            }
        }

        return null;
    }
}