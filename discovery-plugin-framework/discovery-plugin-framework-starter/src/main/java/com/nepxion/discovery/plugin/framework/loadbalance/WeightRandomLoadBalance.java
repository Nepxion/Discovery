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

import com.netflix.loadbalancer.Server;

public interface WeightRandomLoadBalance<T> {
    T getT();

    int getWeight(Server server, T t);

    boolean checkWeight(List<Server> serverList, T t);

    Server choose(List<Server> serverList, T t);
}