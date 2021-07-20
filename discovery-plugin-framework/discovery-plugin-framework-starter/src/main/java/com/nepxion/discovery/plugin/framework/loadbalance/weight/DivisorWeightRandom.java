package com.nepxion.discovery.plugin.framework.loadbalance.weight;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author fly
 * @version 1.0
 */

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class DivisorWeightRandom<K> {
    /**
     * 上一次的下标
     */
    private int lastIndex = -1;

    /**
     * 当前权重
     */
    private int currentWeight = 0;

    public K random(List<Pair<K, Integer>> pairs) {
        int size = pairs != null ? pairs.size() : 0;
        if (size <= 0) {
            return null;
        }

        if (size == 1) {
            return pairs.get(0).getKey();
        }

        // 获取最大权重
        int maxWeight = 0;
        // 最大公约数
        int gcdWeight = 0;

        for (Pair<K, Integer> pair : pairs) {
            maxWeight = Math.max(maxWeight, pair.getValue());
            gcdWeight = (gcdWeight == 0) ? pair.getValue() : gcd(gcdWeight, pair.getValue());
        }

        synchronized (this) {
            while (true) {
                lastIndex = (lastIndex + 1) % size;
                if (lastIndex == 0) {
                    currentWeight = currentWeight - gcdWeight;
                    if (currentWeight <= 0) {
                        currentWeight = maxWeight;
                    }
                }
                if (size <= lastIndex) {
                    lastIndex = -1;
                    continue;
                }
                Pair<K, Integer> pair = pairs.get(lastIndex);
                if (pair.getValue() >= currentWeight) {
                    return pair.getKey();
                }
            }
        }
    }

    /**
     * 辗转相除法
     * 求整数a和b最大公约数：
     * (1) a%b得余数c
     * (2) c==0，则b为最大公约数
     * (3) c!=0，则a=b，b=c，继续goto (1)
     * @return 最大公约数
     */
    private int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }
}