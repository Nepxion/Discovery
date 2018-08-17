package com.nepxion.discovery.plugin.strategy.extension;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import com.netflix.loadbalancer.Server;

public interface DiscoveryEnabledExtension {
    boolean apply(Server server, Map<String, String> metadata);
}