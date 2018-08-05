package com.nepxion.discovery.plugin.framework.decorator;

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
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.DiscoveryEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.WeightEntity;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;

public class ZoneAvoidanceRuleDecorator extends ZoneAvoidanceRule {
    private static final Logger LOG = LoggerFactory.getLogger(ZoneAvoidanceRuleDecorator.class);

    @Autowired
    private PluginAdapter pluginAdapter;

    private final Random random = new Random();

    @Override
    public Server choose(Object key) {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity == null) {
            return super.choose(key);
        }

        DiscoveryEntity discoveryEntity = ruleEntity.getDiscoveryEntity();
        if (discoveryEntity == null) {
            return super.choose(key);
        }

        WeightFilterEntity weightFilterEntity = discoveryEntity.getWeightFilterEntity();
        if (weightFilterEntity == null) {
            return super.choose(key);
        }

        Map<String, List<WeightEntity>> weightEntityMap = weightFilterEntity.getWeightEntityMap();
        if (MapUtils.isEmpty(weightEntityMap)) {
            return super.choose(key);
        }

        String serviceId = pluginAdapter.getServiceId();

        List<WeightEntity> weightEntityList = weightEntityMap.get(serviceId);
        if (CollectionUtils.isEmpty(weightEntityList)) {
            return super.choose(key);
        }

        List<Server> eligibleServers = getPredicate().getEligibleServers(getLoadBalancer().getAllServers(), key);

        try {
            return choose(eligibleServers, weightEntityList);
        } catch (Exception e) {
            return super.choose(key);
        }
    }

    private Server choose(List<Server> serverList, List<WeightEntity> weightEntityList) {
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }

        int[] weights = new int[serverList.size()];
        for (int i = 0; i < serverList.size(); i++) {
            Server server = serverList.get(i);
            int weight = getWeight(server, weightEntityList);
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
        int hitWeight = random.nextInt(totalWeight) + 1; // [1, totalWeight)
        for (int i = 0; i < weightHolder.length; i++) {
            if (hitWeight <= weightHolder[i][1]) {
                return weightHolder[i][0];
            }
        }

        return weightHolder[0][0];
    }

    private int getWeight(Server server, List<WeightEntity> weightEntityList) {
        String providerServiceId = server.getMetaInfo().getAppName();
        String providerVersion = pluginAdapter.getServerVersion(server);

        for (WeightEntity weightEntity : weightEntityList) {
            String providerServiceName = weightEntity.getProviderServiceName();
            if (StringUtils.equalsIgnoreCase(providerServiceName, providerServiceId)) {
                Map<String, Integer> weightMap = weightEntity.getWeightMap();
                Integer weight = weightMap.get(providerVersion);
                if (weight != null) {
                    return weight;
                } else {
                    LOG.error("Weight isn't configed for serviceId={}, version={}", providerServiceId, providerVersion);

                    throw new DiscoveryException("Weight isn't configed for serviceId=" + providerServiceId + ", version=" + providerVersion);
                }
            }
        }

        return -1;
    }
}