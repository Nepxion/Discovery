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
import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.plugin.framework.loadbalance.WeightRandomLoadBalance;

public abstract class AbstractArrayWeightRandomLoadBalance<T> implements WeightRandomLoadBalance<T> {
    private ArrayWeightRandom arrayWeightRandom = new ArrayWeightRandom();

    @Override
    public ServiceInstance choose(List<ServiceInstance> serverList, T t) {
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }

        int[] weights = new int[serverList.size()];
        for (int i = 0; i < serverList.size(); i++) {
            ServiceInstance server = serverList.get(i);
            weights[i] = getWeight(server, t);
        }

        int index = arrayWeightRandom.getIndex(weights);

        return serverList.get(index);
    }
}