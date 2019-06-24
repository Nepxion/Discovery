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

import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.RegionWeightEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.VersionWeightEntity;
import com.nepxion.discovery.common.entity.WeightEntity;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.netflix.loadbalancer.Server;

public class RuleWeightRandomLoadBalanceAdapter extends AbstractWeightRandomLoadBalanceAdapter<WeightFilterEntity> {
    public RuleWeightRandomLoadBalanceAdapter(PluginAdapter pluginAdapter) {
        super(pluginAdapter);
    }

    @Override
    public WeightFilterEntity getT() {
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

    @Override
    public int getWeight(Server server, WeightFilterEntity weightFilterEntity) {
        Map<String, List<WeightEntity>> weightEntityMap = weightFilterEntity.getWeightEntityMap();
        List<WeightEntity> weightEntityList = weightFilterEntity.getWeightEntityList();
        VersionWeightEntity versionWeightEntity = weightFilterEntity.getVersionWeightEntity();
        RegionWeightEntity regionWeightEntity = weightFilterEntity.getRegionWeightEntity();

        String providerServiceId = pluginAdapter.getServerServiceId(server);
        String providerVersion = pluginAdapter.getServerVersion(server);
        String providerRegion = pluginAdapter.getServerRegion(server);

        String serviceId = pluginAdapter.getServiceId();
        // 取局部的权重配置
        int weight = WeightRandomLoadBalanceUtil.getWeight(serviceId, providerServiceId, providerVersion, weightEntityMap);

        // 局部权重配置没找到，取全局的权重配置
        if (weight < 0) {
            weight = WeightRandomLoadBalanceUtil.getWeight(providerServiceId, providerVersion, weightEntityList);
        }

        // 全局的权重配置没找到，取版本的权重配置
        if (weight < 0) {
            weight = WeightRandomLoadBalanceUtil.getWeight(providerVersion, versionWeightEntity);
        }

        // 全局的权重配置没找到，取区域的权重配置
        if (weight < 0) {
            weight = WeightRandomLoadBalanceUtil.getWeight(providerRegion, regionWeightEntity);
        }

        // 所有的权重配置都没找到，则按权重值为0来处理
        if (weight < 0) {
            weight = 0;
        }

        return weight;
    }
}