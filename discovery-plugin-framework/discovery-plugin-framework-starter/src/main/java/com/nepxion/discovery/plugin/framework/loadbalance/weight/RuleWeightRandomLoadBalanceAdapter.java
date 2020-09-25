package com.nepxion.discovery.plugin.framework.loadbalance.weight;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
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
        String providerServiceId = pluginAdapter.getServerServiceId(server);
        String providerVersion = pluginAdapter.getServerVersion(server);
        String providerRegion = pluginAdapter.getServerRegion(server);
        String serviceId = pluginAdapter.getServiceId();

        return WeightEntityWrapper.getWeight(weightFilterEntity, providerServiceId, providerVersion, providerRegion, serviceId);
    }
}