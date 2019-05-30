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
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.RegionWeightEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.WeightEntity;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.loadbalance.IWeightRandomLoadBalance;
import com.netflix.loadbalancer.Server;

public abstract class AbstractWeightRandomLoadBalance implements IWeightRandomLoadBalance {
    private PluginAdapter pluginAdapter;

    @Override
    public void setPluginAdapter(PluginAdapter pluginAdapter) {
        this.pluginAdapter = pluginAdapter;
    }

    @Override
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

    protected int getWeight(Server server, WeightFilterEntity weightFilterEntity) {
        Map<String, List<WeightEntity>> weightEntityMap = weightFilterEntity.getWeightEntityMap();
        RegionWeightEntity regionWeightEntity = weightFilterEntity.getRegionWeightEntity();

        String providerServiceId = pluginAdapter.getServerServiceId(server);
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

        // 所有的权重配置都没找到，则按权重值为0来处理
        if (weight < 0) {
            weight = 0;
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
}