package com.nepxion.discovery.plugin.framework.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public interface PluginContextHolder {
    String getContext(String name);

    String getContextRouteVersion();

    String getContextRouteRegion();

    String getContextRouteEnvironment();

    String getContextRouteAddress();

    String getContextRouteVersionWeight();

    String getContextRouteRegionWeight();

    String getContextRouteIdBlacklist();

    String getContextRouteAddressBlacklist();

    String getTraceId();

    String getSpanId();
}