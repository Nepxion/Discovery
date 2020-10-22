package com.nepxion.discovery.plugin.strategy.context;

import java.util.Map;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public interface StrategyContextHolder {
    String getHeader(String name);

    Map<String, String> getHeaders();

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

    /**
     * 获得调用方法
     * @return
     */
    String getMethod();

    /**
     * 获得POST body
     * @return
     */
    Map<String, Object> getBody();

    /**
     * 获得URI请求参数
     * @return
     */
    Map<String, Object> getParam();
}