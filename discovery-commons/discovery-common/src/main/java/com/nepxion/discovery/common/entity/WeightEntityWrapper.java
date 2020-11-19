package com.nepxion.discovery.common.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.common.util.StringUtil;

public class WeightEntityWrapper {
    @SuppressWarnings("unchecked")
    public static List<WeightEntity> parseWeightEntityList(String weightValue) {
        List<WeightEntity> weightEntityList = new ArrayList<WeightEntity>();

        Map<String, String> jsonWeightMap = JsonUtil.fromJson(weightValue, Map.class);
        for (Map.Entry<String, String> entry : jsonWeightMap.entrySet()) {
            String providerServiceName = entry.getKey();
            String providerWeightValue = entry.getValue();

            WeightEntity weightEntity = new WeightEntity();
            weightEntity.setProviderServiceName(providerServiceName);

            parseWeightEntity(weightEntity, providerWeightValue);

            weightEntityList.add(weightEntity);
        }

        return weightEntityList;
    }

    public static void parseWeightEntity(MapWeightEntity weightEntity, String weightValue) {
        Map<String, Integer> weightMap = weightEntity.getWeightMap();
        List<String> providerWeightValueList = StringUtil.splitToList(weightValue);
        if (CollectionUtils.isNotEmpty(providerWeightValueList)) {
            for (String value : providerWeightValueList) {
                String[] valueArray = StringUtils.split(value, DiscoveryConstant.EQUALS);
                String key = valueArray[0].trim();
                int weight = 0;
                try {
                    weight = Integer.valueOf(valueArray[1].trim());
                    if (weight < 0) {
                        weight = 0;
                    }
                } catch (Exception e) {

                }

                weightMap.put(key, weight);
            }
        }
    }

    public static int getWeight(String consumerServiceId, String providerServiceId, String providerKey, Map<String, List<WeightEntity>> weightEntityMap) {
        if (MapUtils.isEmpty(weightEntityMap)) {
            return -1;
        }

        List<WeightEntity> weightEntityList = weightEntityMap.get(consumerServiceId);

        return getWeight(providerServiceId, providerKey, weightEntityList);
    }

    public static int getWeight(String providerServiceId, String providerKey, List<WeightEntity> weightEntityList) {
        if (CollectionUtils.isEmpty(weightEntityList)) {
            return -1;
        }

        for (WeightEntity weightEntity : weightEntityList) {
            String providerServiceName = weightEntity.getProviderServiceName();
            if (StringUtils.equalsIgnoreCase(providerServiceName, providerServiceId)) {
                return getWeight(providerKey, weightEntity);
            }
        }

        return -1;
    }

    public static int getWeight(String providerKey, MapWeightEntity weightEntity) {
        if (weightEntity == null) {
            return -1;
        }

        Map<String, Integer> weightMap = weightEntity.getWeightMap();
        if (MapUtils.isEmpty(weightMap)) {
            return -1;
        }

        Integer weight = weightMap.get(providerKey);
        if (weight != null) {
            return weight;
        } else {
            return -1;
        }
    }

    public static int getWeight(WeightFilterEntity weightFilterEntity, String providerServiceId, String providerVersion, String providerRegion, String serviceId) {
        int weight = -1;
        if (StringUtils.isNotEmpty(serviceId) && weight < 0) {
            Map<String, List<WeightEntity>> versionWeightEntityMap = weightFilterEntity.getVersionWeightEntityMap();
            weight = getWeight(serviceId, providerServiceId, providerVersion, versionWeightEntityMap);
        }
        if (weight < 0) {
            List<WeightEntity> versionWeightEntityList = weightFilterEntity.getVersionWeightEntityList();
            weight = getWeight(providerServiceId, providerVersion, versionWeightEntityList);
        }
        if (weight < 0) {
            VersionWeightEntity versionWeightEntity = weightFilterEntity.getVersionWeightEntity();
            weight = getWeight(providerVersion, versionWeightEntity);
        }

        if (StringUtils.isNotEmpty(serviceId) && weight < 0) {
            Map<String, List<WeightEntity>> regionWeightEntityMap = weightFilterEntity.getRegionWeightEntityMap();
            weight = getWeight(serviceId, providerServiceId, providerRegion, regionWeightEntityMap);
        }
        if (weight < 0) {
            List<WeightEntity> regionWeightEntityList = weightFilterEntity.getRegionWeightEntityList();
            weight = getWeight(providerServiceId, providerRegion, regionWeightEntityList);
        }
        if (weight < 0) {
            RegionWeightEntity regionWeightEntity = weightFilterEntity.getRegionWeightEntity();
            weight = getWeight(providerRegion, regionWeightEntity);
        }

        return weight;
    }
}