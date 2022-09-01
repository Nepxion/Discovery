package com.nepxion.discovery.plugin.strategy.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public interface StrategyRouteFilter {
    String getRouteVersion();

    String getRouteRegion();

    String getRouteEnvironment();

    String getRouteAddress();

    String getRouteVersionWeight();

    String getRouteRegionWeight();

    String getRouteVersionPrefer();

    String getRouteVersionFailover();

    String getRouteRegionTransfer();

    String getRouteRegionFailover();

    String getRouteEnvironmentFailover();

    String getRouteZoneFailover();

    String getRouteAddressFailover();

    String getRouteIdBlacklist();

    String getRouteAddressBlacklist();
}