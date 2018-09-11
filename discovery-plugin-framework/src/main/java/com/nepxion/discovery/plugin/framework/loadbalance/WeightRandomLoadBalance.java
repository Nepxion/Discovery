package com.nepxion.discovery.plugin.framework.loadbalance;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.RegionWeightEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.WeightEntity;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.netflix.loadbalancer.Server;

public class WeightRandomLoadBalance {
    private PluginAdapter pluginAdapter;

    public WeightFilterEntity getWeightFilterEntity() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity == null) {
            return null;
        }

        DiscoveryEntity discoveryEntity = ruleEntity.getDiscoveryEntity();
        if (discoveryEntity == null) {
            return null;
        }

        WeightFilterEntity weightFilterEntity = discoveryEntity.getWeightFilterEntity();

        return weightFilterEntity;
    }

    public Server choose(List<Server> serverList, WeightFilterEntity weightFilterEntity) {
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }

        int[] weights = new int[serverList.size()];
        for (int i = 0; i < serverList.size(); i++) {
            Server server = serverList.get(i);
            int weight = getWeight(server, weightFilterEntity);
            if (weight > 0) {
                weights[i] = weight;
            }
        }

        int index = getIndex(weights);

        return serverList.get(index);
    }

    private int getIndex(int[] weights) {
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

    private int getWeight(Server server, WeightFilterEntity weightFilterEntity) {
        Map<String, List<WeightEntity>> weightEntityMap = weightFilterEntity.getWeightEntityMap();
        RegionWeightEntity regionWeightEntity = weightFilterEntity.getRegionWeightEntity();

        String providerServiceId = server.getMetaInfo().getAppName();
        String providerVersion = pluginAdapter.getServerVersion(server);
        String providerRegion = pluginAdapter.getServerRegion(server);

        String serviceId = pluginAdapter.getServiceId();
        // 取局部的权重配置
        int weight = getWeight(serviceId, providerServiceId, providerVersion, weightEntityMap);

        // 局部权重配置没找到，取全局的权重配置
        if (weight < 0) {
            weight = getWeight(StringUtils.EMPTY, providerServiceId, providerVersion, weightEntityMap);
        }

        // 全局的权重配置没找到，取区域的权重配置
        if (weight < 0) {
            weight = getWeight(providerRegion, regionWeightEntity);
        }

        if (weight < 0) {
            throw new DiscoveryException("Weight isn't configed for serviceId=" + providerServiceId);
        }

        return weight;
    }

    private int getWeight(String consumerServiceId, String providerServiceId, String providerVersion, Map<String, List<WeightEntity>> weightEntityMap) {
        if (MapUtils.isEmpty(weightEntityMap)) {
            return -1;
        }

        List<WeightEntity> weightEntityList = weightEntityMap.get(consumerServiceId);
        if (CollectionUtils.isEmpty(weightEntityList)) {
            return -1;
        }

        for (WeightEntity weightEntity : weightEntityList) {
            String providerServiceName = weightEntity.getProviderServiceName();
            if (StringUtils.equalsIgnoreCase(providerServiceName, providerServiceId)) {
                Map<String, Integer> weightMap = weightEntity.getWeightMap();
                if (MapUtils.isEmpty(weightMap)) {
                    return -1;
                }

                Integer weight = weightMap.get(providerVersion);
                if (weight != null) {
                    return weight;
                } else {
                    return -1;
                }
            }
        }

        return -1;
    }

    private int getWeight(String providerRegion, RegionWeightEntity regionWeightEntity) {
        if (StringUtils.isEmpty(providerRegion)) {
            return -1;
        }

        if (regionWeightEntity == null) {
            return -1;
        }

        Map<String, Integer> weightMap = regionWeightEntity.getWeightMap();
        if (MapUtils.isEmpty(weightMap)) {
            return -1;
        }

        Integer weight = weightMap.get(providerRegion);
        if (weight != null) {
            return weight;
        } else {
            return -1;
        }
    }

    public void setPluginAdapter(PluginAdapter pluginAdapter) {
        this.pluginAdapter = pluginAdapter;
    }
}