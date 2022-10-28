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

import com.nepxion.discovery.plugin.framework.loadbalance.WeightRandomLoadBalance;
import com.netflix.loadbalancer.Server;

public abstract class AbstractArrayWeightRandomLoadBalance<T> implements WeightRandomLoadBalance<T> {
    @Override
    public Server choose(List<Server> serverList, T t) {
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }

        List<Pair<Server, Integer>> weightList = new ArrayList<Pair<Server, Integer>>();
        for (Server server : serverList) {
            int weight = getWeight(server, t);
            weightList.add(new ImmutablePair<Server, Integer>(server, weight));
        }

        ArrayWeightRandom<Server, Integer> weightRandom = new ArrayWeightRandom<Server, Integer>(weightList);

        return weightRandom.random();
    }
}