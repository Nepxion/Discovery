package com.nepxion.discovery.plugin.framework.loadbalance;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.nepxion.discovery.common.exception.DiscoveryException;

public class MapWeightRandomLoadBalanceTest {
    public static class MapWeightRandom<K, V extends Number> {
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

    public static void main(String[] args) {
        long t = System.currentTimeMillis();

        List<Pair<String, Double>> list = new ArrayList<Pair<String, Double>>();
        list.add(new ImmutablePair<String, Double>("1.0", 0D));
        list.add(new ImmutablePair<String, Double>("2.0", 90D));
        list.add(new ImmutablePair<String, Double>("3.0", 10D));
        list.add(new ImmutablePair<String, Double>("1.0", 0D));
        list.add(new ImmutablePair<String, Double>("2.0", 90D));
        list.add(new ImmutablePair<String, Double>("3.0", 10D));
        list.add(new ImmutablePair<String, Double>("4.0", 0D));
        list.add(new ImmutablePair<String, Double>("4.0", 0D));
        list.add(new ImmutablePair<String, Double>("5.0", 0D));
        list.add(new ImmutablePair<String, Double>("5.0", 0D));

        int v1Count = 0;
        int v2Count = 0;
        int v3Count = 0;
        int v4Count = 0;
        int v5Count = 0;
        for (int i = 0; i < 1000000; i++) {
            MapWeightRandom<String, Double> weightRandom = new MapWeightRandom<String, Double>(list);
            String server = weightRandom.random();
            if (server.startsWith("1.0")) {
                v1Count++;
            }
            if (server.startsWith("2.0")) {
                v2Count++;
            }
            if (server.startsWith("3.0")) {
                v3Count++;
            }
            if (server.startsWith("4.0")) {
                v4Count++;
            }
            if (server.startsWith("5.0")) {
                v5Count++;
            }
        }

        System.out.println("1.0版本服务随机权重=" + v1Count);
        System.out.println("2.0版本服务随机权重=" + v2Count);
        System.out.println("3.0版本服务随机权重=" + v3Count);
        System.out.println("4.0版本服务随机权重=" + v4Count);
        System.out.println("5.0版本服务随机权重=" + v5Count);
        System.out.println("耗时时间：" + (System.currentTimeMillis() - t));
    }
}