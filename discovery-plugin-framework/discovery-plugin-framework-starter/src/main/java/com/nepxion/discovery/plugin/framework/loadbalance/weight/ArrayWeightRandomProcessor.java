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
import org.apache.commons.lang3.tuple.Pair;

public class ArrayWeightRandomProcessor<T> implements WeightRandomProcessor<T> {
    private ArrayWeightRandom arrayWeightRandom = new ArrayWeightRandom();

    @Override
    public T random(List<Pair<T, Integer>> weightList) {
        if (CollectionUtils.isEmpty(weightList)) {
            return null;
        }

        int[] weights = new int[weightList.size()];
        for (int i = 0; i < weightList.size(); i++) {
            Pair<T, Integer> pair = weightList.get(i);
            weights[i] = pair.getValue();
        }

        int index = arrayWeightRandom.getIndex(weights);

        return weightList.get(index).getKey();
    }
}