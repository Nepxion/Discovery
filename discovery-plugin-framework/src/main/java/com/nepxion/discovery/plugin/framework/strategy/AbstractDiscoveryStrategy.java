package com.nepxion.discovery.plugin.framework.strategy;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;

public abstract class AbstractDiscoveryStrategy extends BasicStrategy implements DiscoveryStrategy {
    @Autowired
    protected DiscoveryClient discoveryClient;

    public DiscoveryClient getDiscoveryClient() {
        return discoveryClient;
    }
}