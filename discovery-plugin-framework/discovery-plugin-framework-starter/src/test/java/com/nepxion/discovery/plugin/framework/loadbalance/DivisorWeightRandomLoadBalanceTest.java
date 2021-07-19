package com.nepxion.discovery.plugin.framework.loadbalance;

import com.nepxion.discovery.plugin.framework.loadbalance.weight.DivisorWeightRandom;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DivisorWeightRandomLoadBalanceTest {

    public static void main(String[] args) {
        test(1_000_000);
    }

    public static void test(int totalCount) {
        long now = System.currentTimeMillis();

        List<Pair<String, Integer>> list = new ArrayList<>();

        list.add(new ImmutablePair<>("1.0", 4));
        list.add(new ImmutablePair<>("2.0", 3));
        list.add(new ImmutablePair<>("3.0", 2));

        int v1Count = 0;
        int v2Count = 0;
        int v3Count = 0;

        DivisorWeightRandom<String> weightRandom = new DivisorWeightRandom<>();
        for (int i = 0; i < totalCount; i++) {
            String server = weightRandom.choose(list);
            if (server.startsWith("1.0")) {
                ++v1Count;
            }
            if (server.startsWith("2.0")) {
                ++v2Count;
            }
            if (server.startsWith("3.0")) {
                ++v3Count;
            }
        }

        System.out.println("------------------------------");
        System.out.println(totalCount + "次循环，散列方式随机权重准确度和性能：");
        DecimalFormat format = new DecimalFormat("0.0000");
        System.out.println("1.0版本服务随机权重=" + format.format((double) v1Count * 100 / totalCount) + "%");
        System.out.println("2.0版本服务随机权重=" + format.format((double) v2Count * 100 / totalCount) + "%");
        System.out.println("3.0版本服务随机权重=" + format.format((double) v3Count * 100 / totalCount) + "%");
//        System.out.println("4.0版本服务随机权重=" + format.format((double) v4Count * 100 / totalCount) + "%");
//        System.out.println("5.0版本服务随机权重=" + format.format((double) v5Count * 100 / totalCount) + "%");
        System.out.println("耗时时间：" + (System.currentTimeMillis() - now));
        System.out.println("------------------------------");
    }
}

