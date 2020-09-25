package com.nepxion.discovery.plugin.framework.loadbalance.weight;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.concurrent.ThreadLocalRandom;

public class ArrayWeightRandom {
    public int getIndex(int[] weights) {
        // 次序号/权重区间值
        int[][] weightHolder = new int[weights.length][2];
        // 总权重
        int totalWeight = 0;
        // 赋值次序号和区间值累加的数组值，从小到大排列
        // 例如，对于权重分别为20，40， 60的三个服务，将形成[0, 20)，[20, 60)，[60, 120]三个区间
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] <= 0) {
                continue;
            }

            totalWeight += weights[i];
            weightHolder[i][0] = i;
            weightHolder[i][1] = totalWeight;
        }

        // 获取介于0(含)和n(不含)伪随机，均匀分布的int值
        int hitWeight = ThreadLocalRandom.current().nextInt(totalWeight) + 1; // [1, totalWeight)
        for (int i = 0; i < weightHolder.length; i++) {
            if (hitWeight <= weightHolder[i][1]) {
                return weightHolder[i][0];
            }
        }

        return weightHolder[0][0];
    }
}