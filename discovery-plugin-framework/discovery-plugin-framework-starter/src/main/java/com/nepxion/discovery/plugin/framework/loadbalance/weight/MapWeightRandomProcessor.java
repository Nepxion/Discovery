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

public class MapWeightRandomProcessor<T> implements WeightRandomProcessor<T> {
    @Override
    public T random(List<Pair<T, Integer>> weightList) {
        if (CollectionUtils.isEmpty(weightList)) {
            return null;
        }

        MapWeightRandom<T, Integer> weightRandom = new MapWeightRandom<T, Integer>(weightList);

        return weightRandom.random();
    }
}