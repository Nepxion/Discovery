package com.nepxion.discovery.plugin.strategy.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.context.PluginContextHolder;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyWrapper;

public abstract class AbstractStrategyContextHolder implements PluginContextHolder, StrategyContextHolder {
    @Autowired
    protected StrategyWrapper strategyWrapper;

    @Override
    public String getContext(String name) {
        return getHeader(name);
    }

    @Override
    public String getContextRouteVersion() {
        String versionValue = getContext(DiscoveryConstant.N_D_VERSION);
        if (StringUtils.isEmpty(versionValue)) {
            versionValue = strategyWrapper.getRouteVersion();
        }

        return versionValue;
    }

    @Override
    public String getContextRouteRegion() {
        String regionValue = getContext(DiscoveryConstant.N_D_REGION);
        if (StringUtils.isEmpty(regionValue)) {
            regionValue = strategyWrapper.getRouteRegion();
        }

        return regionValue;
    }

    @Override
    public String getContextRouteAddress() {
        String addressValue = getContext(DiscoveryConstant.N_D_ADDRESS);
        if (StringUtils.isEmpty(addressValue)) {
            addressValue = strategyWrapper.getRouteAddress();
        }

        return addressValue;
    }

    @Override
    public String getContextRouteVersionWeight() {
        String versionWeightValue = getContext(DiscoveryConstant.N_D_VERSION_WEIGHT);
        if (StringUtils.isEmpty(versionWeightValue)) {
            versionWeightValue = strategyWrapper.getRouteVersionWeight();
        }

        return versionWeightValue;
    }

    @Override
    public String getContextRouteRegionWeight() {
        String regionWeightValue = getContext(DiscoveryConstant.N_D_REGION_WEIGHT);
        if (StringUtils.isEmpty(regionWeightValue)) {
            regionWeightValue = strategyWrapper.getRouteRegionWeight();
        }

        return regionWeightValue;
    }

    @Override
    public String getRouteVersion() {
        return strategyWrapper.getRouteVersion();
    }

    @Override
    public String getRouteRegion() {
        return strategyWrapper.getRouteRegion();
    }

    @Override
    public String getRouteAddress() {
        return strategyWrapper.getRouteAddress();
    }

    @Override
    public String getRouteVersionWeight() {
        return strategyWrapper.getRouteVersionWeight();
    }

    @Override
    public String getRouteRegionWeight() {
        return strategyWrapper.getRouteRegionWeight();
    }
}