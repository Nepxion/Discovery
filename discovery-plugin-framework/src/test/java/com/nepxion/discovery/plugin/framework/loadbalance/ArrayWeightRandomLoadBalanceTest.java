package com.nepxion.discovery.plugin.framework.loadbalance;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.collections4.CollectionUtils;

public class ArrayWeightRandomLoadBalanceTest {
    public static void main(String[] args) {
        test(1000000);
    }

    public static void test(int totalCount) {
        long t = System.currentTimeMillis();

        List<String> serverList = new ArrayList<String>();
        serverList.add("1.0");
        serverList.add("2.0");
        serverList.add("3.0");
        serverList.add("1.0");
        serverList.add("2.0");
        serverList.add("3.0");
        serverList.add("4.0");
        serverList.add("4.0");
        serverList.add("5.0");
        serverList.add("5.0");

        Map<String, Integer> weightMap = new HashMap<String, Integer>();
        weightMap.put("1.0", 10);
        weightMap.put("2.0", 50);
        weightMap.put("3.0", 20);
        weightMap.put("4.0", 5);
        weightMap.put("5.0", 15);

        int v1Count = 0;
        int v2Count = 0;
        int v3Count = 0;
        int v4Count = 0;
        int v5Count = 0;
        for (int i = 0; i < totalCount; i++) {
            String server = choose(serverList, weightMap);
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

        System.out.println("------------------------------");
        System.out.println(totalCount + "次循环，数组方式随机权重准确度和性能：");
        DecimalFormat format = new DecimalFormat("0.0000");
        System.out.println("1.0版本服务随机权重=" + format.format((double) v1Count * 100 / totalCount) + "%");
        System.out.println("2.0版本服务随机权重=" + format.format((double) v2Count * 100 / totalCount) + "%");
        System.out.println("3.0版本服务随机权重=" + format.format((double) v3Count * 100 / totalCount) + "%");
        System.out.println("4.0版本服务随机权重=" + format.format((double) v4Count * 100 / totalCount) + "%");
        System.out.println("5.0版本服务随机权重=" + format.format((double) v5Count * 100 / totalCount) + "%");
        System.out.println("耗时时间：" + (System.currentTimeMillis() - t));
        System.out.println("------------------------------");
    }

    public static String choose(List<String> serverList, Map<String, Integer> weightMap) {
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }

        int[] weights = new int[serverList.size()];
        for (int i = 0; i < serverList.size(); i++) {
            String server = serverList.get(i);
            weights[i] = weightMap.get(server);
        }

        int index = getIndex(weights);

        return serverList.get(index);
    }

    private static int getIndex(int[] weights) {
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