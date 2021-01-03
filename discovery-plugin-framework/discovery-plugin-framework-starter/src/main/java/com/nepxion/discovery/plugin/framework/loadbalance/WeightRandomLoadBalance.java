package com.nepxion.discovery.plugin.framework.loadbalance;

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

public interface WeightRandomLoadBalance<T> {
    T getT();

    int getWeight(ServiceInstance server, T t);

    boolean checkWeight(List<ServiceInstance> serverList, T t);

    ServiceInstance choose(List<ServiceInstance> serverList, T t);
}