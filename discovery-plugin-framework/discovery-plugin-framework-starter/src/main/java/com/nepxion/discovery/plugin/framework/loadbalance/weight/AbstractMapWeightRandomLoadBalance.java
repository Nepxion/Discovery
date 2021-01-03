package com.nepxion.discovery.plugin.framework.loadbalance.weight;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.plugin.framework.loadbalance.WeightRandomLoadBalance;

public abstract class AbstractMapWeightRandomLoadBalance<T> implements WeightRandomLoadBalance<T> {
    @Override
    public ServiceInstance choose(List<ServiceInstance> serverList, T t) {
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }

        List<Pair<ServiceInstance, Integer>> weightPairList = new ArrayList<Pair<ServiceInstance, Integer>>();
        for (ServiceInstance server : serverList) {
            int weight = getWeight(server, t);
            weightPairList.add(new ImmutablePair<ServiceInstance, Integer>(server, weight));
        }

        MapWeightRandom<ServiceInstance, Integer> weightRandom = new MapWeightRandom<ServiceInstance, Integer>(weightPairList);

        return weightRandom.random();
    }
}