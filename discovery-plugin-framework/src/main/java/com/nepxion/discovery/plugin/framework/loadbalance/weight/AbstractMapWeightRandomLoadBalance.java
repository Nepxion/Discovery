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
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.loadbalance.WeightRandomLoadBalance;
import com.netflix.loadbalancer.Server;

public abstract class AbstractMapWeightRandomLoadBalance<T> implements WeightRandomLoadBalance<T> {
    @Override
    public Server choose(List<Server> serverList, T t) {
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }

        List<Pair<Server, Integer>> weightPairList = new ArrayList<Pair<Server, Integer>>();
        for (Server server : serverList) {
            int weight = getWeight(server, t);
            weightPairList.add(new ImmutablePair<Server, Integer>(server, weight));
        }

        MapWeightRandom<Server, Integer> weightRandom = new MapWeightRandom<Server, Integer>(weightPairList);

        return weightRandom.random();
    }

    public class MapWeightRandom<K, V extends Number> {
        private TreeMap<Double, K> weightMap = new TreeMap<Double, K>();

        public MapWeightRandom(List<Pair<K, V>> pairlist) {
            for (Pair<K, V> pair : pairlist) {
                double value = pair.getValue().doubleValue();
                if (value <= 0) {
                    continue;
                }

                double lastWeight = weightMap.size() == 0 ? 0 : weightMap.lastKey().doubleValue();
                weightMap.put(value + lastWeight, pair.getKey());
            }
        }

        public K random() {
            if (MapUtils.isEmpty(weightMap)) {
                throw new DiscoveryException("No weight value is configed");
            }

            double randomWeight = weightMap.lastKey() * Math.random();
            SortedMap<Double, K> tailMap = weightMap.tailMap(randomWeight, false);

            return weightMap.get(tailMap.firstKey());
        }
    }
}