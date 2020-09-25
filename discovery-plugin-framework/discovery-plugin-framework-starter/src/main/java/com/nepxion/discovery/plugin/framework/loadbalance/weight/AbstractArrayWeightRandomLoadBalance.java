package com.nepxion.discovery.plugin.framework.loadbalance.weight;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.nepxion.discovery.plugin.framework.loadbalance.WeightRandomLoadBalance;
import com.netflix.loadbalancer.Server;

public abstract class AbstractArrayWeightRandomLoadBalance<T> implements WeightRandomLoadBalance<T> {
    private ArrayWeightRandom arrayWeightRandom = new ArrayWeightRandom();

    @Override
    public Server choose(List<Server> serverList, T t) {
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }

        int[] weights = new int[serverList.size()];
        for (int i = 0; i < serverList.size(); i++) {
            Server server = serverList.get(i);
            weights[i] = getWeight(server, t);
        }

        int index = arrayWeightRandom.getIndex(weights);

        return serverList.get(index);
    }
}