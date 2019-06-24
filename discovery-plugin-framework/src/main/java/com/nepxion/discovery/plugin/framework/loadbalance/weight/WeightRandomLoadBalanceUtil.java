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

import com.nepxion.discovery.common.entity.RegionWeightEntity;
import com.nepxion.discovery.common.entity.VersionWeightEntity;
import com.nepxion.discovery.common.entity.WeightEntity;

public class WeightRandomLoadBalanceUtil {
    public static int getWeight(String consumerServiceId, String providerServiceId, String providerVersion, Map<String, List<WeightEntity>> weightEntityMap) {
        if (MapUtils.isEmpty(weightEntityMap)) {
            return -1;
        }

        List<WeightEntity> weightEntityList = weightEntityMap.get(consumerServiceId);

        return getWeight(providerServiceId, providerVersion, weightEntityList);
    }

    public static int getWeight(String providerServiceId, String providerVersion, List<WeightEntity> weightEntityList) {
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

    public static int getWeight(String providerVersion, VersionWeightEntity versionWeightEntity) {
        if (versionWeightEntity == null) {
            return -1;
        }

        Map<String, Integer> weightMap = versionWeightEntity.getWeightMap();
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

    public static int getWeight(String providerRegion, RegionWeightEntity regionWeightEntity) {
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