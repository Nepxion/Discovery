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

import org.springframework.cloud.client.ServiceInstance;

import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;

public class RuleMapWeightRandomLoadBalance extends AbstractMapWeightRandomLoadBalance<WeightFilterEntity> {
    private RuleWeightRandomLoadBalanceAdapter ruleWeightRandomLoadBalanceAdapter;

    public RuleMapWeightRandomLoadBalance(PluginAdapter pluginAdapter) {
        ruleWeightRandomLoadBalanceAdapter = new RuleWeightRandomLoadBalanceAdapter(pluginAdapter);
    }

    @Override
    public WeightFilterEntity getT() {
        return ruleWeightRandomLoadBalanceAdapter.getT();
    }

    @Override
    public int getWeight(ServiceInstance server, WeightFilterEntity weightFilterEntity) {
        return ruleWeightRandomLoadBalanceAdapter.getWeight(server, weightFilterEntity);
    }

    @Override
    public boolean checkWeight(List<ServiceInstance> serverList, WeightFilterEntity weightFilterEntity) {
        return ruleWeightRandomLoadBalanceAdapter.checkWeight(serverList, weightFilterEntity);
    }
}