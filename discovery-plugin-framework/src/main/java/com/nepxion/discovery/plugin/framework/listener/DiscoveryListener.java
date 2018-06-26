package com.nepxion.discovery.plugin.framework.listener;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;

public interface DiscoveryListener extends Listener {
    void onGetInstances(String serviceId, List<ServiceInstance> instances);

    void onGetServices(List<String> services);
}