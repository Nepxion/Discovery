package com.nepxion.discovery.plugin.strategy.service.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyCustomizationEntity;
import com.nepxion.discovery.common.entity.StrategyHeaderEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyWrapper;

public class DefaultServiceStrategyRouteFilter extends AbstractServiceStrategyRouteFilter {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private StrategyWrapper strategyWrapper;

    @Override
    public String getRouteVersion() {
        Map<String, String> headerMap = getHeaderMap();
        if (headerMap == null) {
            return strategyWrapper.getRouteVersion();
        } else {
            return strategyWrapper.getRouteVersion(headerMap);
        }
    }

    @Override
    public String getRouteRegion() {
        Map<String, String> headerMap = getHeaderMap();
        if (headerMap == null) {
            return strategyWrapper.getRouteRegion();
        } else {
            return strategyWrapper.getRouteRegion(headerMap);
        }
    }

    @Override
    public String getRouteAddress() {
        Map<String, String> headerMap = getHeaderMap();
        if (headerMap == null) {
            return strategyWrapper.getRouteAddress();
        } else {
            return strategyWrapper.getRouteAddress(headerMap);
        }
    }

    @Override
    public String getRouteVersionWeight() {
        Map<String, String> headerMap = getHeaderMap();
        if (headerMap == null) {
            return strategyWrapper.getRouteVersionWeight();
        } else {
            return strategyWrapper.getRouteVersionWeight(headerMap);
        }
    }

    @Override
    public String getRouteRegionWeight() {
        Map<String, String> headerMap = getHeaderMap();
        if (headerMap == null) {
            return strategyWrapper.getRouteRegionWeight();
        } else {
            return strategyWrapper.getRouteRegionWeight(headerMap);
        }
    }

    public Map<String, String> getHeaderMap() {
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