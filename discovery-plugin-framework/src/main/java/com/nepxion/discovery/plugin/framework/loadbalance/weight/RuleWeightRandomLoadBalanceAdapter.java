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
import com.nepxion.discovery.common.entity.WeightEntityWrapper;
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
        Map<String, List<WeightEntity>> versionWeightEntityMap = weightFilterEntity.getVersionWeightEntityMap();
        List<WeightEntity> versionWeightEntityList = weightFilterEntity.getVersionWeightEntityList();
        VersionWeightEntity versionWeightEntity = weightFilterEntity.getVersionWeightEntity();

        Map<String, List<WeightEntity>> regionWeightEntityMap = weightFilterEntity.getRegionWeightEntityMap();
        List<WeightEntity> regionWeightEntityList = weightFilterEntity.getRegionWeightEntityList();
        RegionWeightEntity regionWeightEntity = weightFilterEntity.getRegionWeightEntity();

        String providerServiceId = pluginAdapter.getServerServiceId(server);
        String providerVersion = pluginAdapter.getServerVersion(server);
        String providerRegion = pluginAdapter.getServerRegion(server);

        String serviceId = pluginAdapter.getServiceId();
        int weight = WeightEntityWrapper.getWeight(serviceId, providerServiceId, providerVersion, versionWeightEntityMap);
        if (weight < 0) {
            weight = WeightEntityWrapper.getWeight(providerServiceId, providerVersion, versionWeightEntityList);
        }
        if (weight < 0) {
            weight = WeightEntityWrapper.getWeight(providerVersion, versionWeightEntity);
        }

        if (weight < 0) {
            weight = WeightEntityWrapper.getWeight(serviceId, providerServiceId, providerRegion, regionWeightEntityMap);
        }
        if (weight < 0) {
            weight = WeightEntityWrapper.getWeight(providerServiceId, providerRegion, regionWeightEntityList);
        }
        if (weight < 0) {
            weight = WeightEntityWrapper.getWeight(providerRegion, regionWeightEntity);
        }

        // 所有的权重配置都没找到，则按权重值为0来处理
        if (weight < 0) {
            weight = 0;
        }

        return weight;
    }
}