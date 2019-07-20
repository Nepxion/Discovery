package com.nepxion.discovery.plugin.strategy.service.route;

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

import com.nepxion.discovery.plugin.strategy.wrapper.CustomizationStrategyWrapper;

public class CustomizationServiceStrategyRouteFilter extends DefaultServiceStrategyRouteFilter {
    @Autowired
    protected CustomizationStrategyWrapper customizationStrategyWrapper;

    @Override
    public String getRouteVersion() {
        String routeVersion = customizationStrategyWrapper.getRouteVersion();
        if (StringUtils.isNotEmpty(routeVersion)) {
            return routeVersion;
        }

        return super.getRouteVersion();
    }

    @Override
    public String getRouteRegion() {
        String routeRegion = customizationStrategyWrapper.getRouteRegion();
        if (StringUtils.isNotEmpty(routeRegion)) {
            return routeRegion;
        }

        return super.getRouteRegion();
    }

    @Override
    public String getRouteAddress() {
        String routeAddress = customizationStrategyWrapper.getRouteAddress();
        if (StringUtils.isNotEmpty(routeAddress)) {
            return routeAddress;
        }

        return super.getRouteAddress();
    }

    @Override
    public String getRouteVersionWeight() {
        String routeVersionWeight = customizationStrategyWrapper.getRouteVersionWeight();
        if (StringUtils.isNotEmpty(routeVersionWeight)) {
            return routeVersionWeight;
        }

        return super.getRouteVersionWeight();
    }

    @Override
    public String getRouteRegionWeight() {
        String routeRegionWeight = customizationStrategyWrapper.getRouteRegionWeight();
        if (StringUtils.isNotEmpty(routeRegionWeight)) {
            return routeRegionWeight;
        }

        return super.getRouteRegionWeight();
    }
}