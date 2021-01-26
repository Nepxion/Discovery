package com.nepxion.discovery.plugin.strategy.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Enumeration;

public interface StrategyContextHolder {
    Enumeration<String> getHeaderNames();

    String getHeader(String name);

    String getParameter(String name);

    String getCookie(String name);

    String getRouteVersion();

    String getRouteRegion();

    String getRouteEnvironment();

    String getRouteAddress();

    String getRouteVersionWeight();

    String getRouteRegionWeight();

    String getRouteIdBlacklist();

    String getRouteAddressBlacklist();
}