package com.nepxion.discovery.plugin.framework.loadbalance;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

public class WeightRandomLoadBalanceTest {
    public static void main(String[] args) {
        System.out.println("随机权重测试期望值：");
        System.out.println("1.0版本服务随机权重=10%");
        System.out.println("2.0版本服务随机权重=50%");
        System.out.println("3.0版本服务随机权重=20%");
        System.out.println("4.0版本服务随机权重=5%");
        System.out.println("5.0版本服务随机权重=15%");
        System.out.println("");

        int[] totalCounts = new int[] { 1000000, 2000000, 5000000, 10000000, 50000000, 100000000 };
        for (int i = 0; i < totalCounts.length; i++) {
            test(i + 1, totalCounts[i]);
        }
    }

    public static void test(int testcaseNo, int totalCount) {
        System.out.println("**********测试场景" + testcaseNo + "**********");
        MapWeightRandomLoadBalanceTest.test(totalCount);
        ArrayWeightRandomLoadBalanceTest.test(totalCount);
        // NacosWeightRandomLoadBalanceTest.test(totalCount);
        System.out.println("");
    }
}