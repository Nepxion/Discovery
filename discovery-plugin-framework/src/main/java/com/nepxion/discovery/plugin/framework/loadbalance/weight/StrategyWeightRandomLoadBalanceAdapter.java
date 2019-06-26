package com.nepxion.discovery.plugin.framework.loadbalance.weight;

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

import org.apache.commons.lang3.StringUtils;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.RegionWeightEntity;
import com.nepxion.discovery.common.entity.VersionWeightEntity;
import com.nepxion.discovery.common.entity.WeightEntity;
import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextHolder;
import com.netflix.loadbalancer.Server;

public class StrategyWeightRandomLoadBalanceAdapter extends AbstractWeightRandomLoadBalanceAdapter<WeightFilterEntity> {
    public StrategyWeightRandomLoadBalanceAdapter(PluginAdapter pluginAdapter, PluginContextHolder pluginContextHolder) {
        super(pluginAdapter, pluginContextHolder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public WeightFilterEntity getT() {
        WeightFilterEntity weightFilterEntity = new WeightFilterEntity();

        String versionWeightValue = pluginContextHolder.getContext(DiscoveryConstant.N_D_VERSION_WEIGHT);
        if (StringUtils.isNotEmpty(versionWeightValue)) {
            try {
                List<WeightEntity> weightEntityList = new ArrayList<WeightEntity>();

                Map<String, String> versionWeightMap = JsonUtil.fromJson(versionWeightValue, Map.class);
                for (Map.Entry<String, String> entry : versionWeightMap.entrySet()) {
                    String providerServiceName = entry.getKey();
                    String providerWeightValue = entry.getValue();

                    WeightEntity weightEntity = new WeightEntity();
                    weightEntity.setProviderServiceName(providerServiceName);

                    Map<String, Integer> weightMap = weightEntity.getWeightMap();
                    List<String> providerWeightValueList = StringUtil.splitToList(providerWeightValue, DiscoveryConstant.SEPARATE);
                    for (String value : providerWeightValueList) {
                        String[] valueArray = StringUtils.split(value, DiscoveryConstant.EQUALS);
                        String version = valueArray[0].trim();
                        int weight = 0;
                        try {
                            weight = Integer.valueOf(valueArray[1].trim());
                            if (weight < 0) {
                                weight = 0;
                            }
                        } catch (NumberFormatException e) {

                        }

                        weightMap.put(version, weight);
                    }

                    weightEntityList.add(weightEntity);
                }

                weightFilterEntity.setVersionWeightEntityList(weightEntityList);
            } catch (Exception e) {
                VersionWeightEntity versionWeightEntity = new VersionWeightEntity();

                Map<String, Integer> weightMap = versionWeightEntity.getWeightMap();
                List<String> providerWeightValueList = StringUtil.splitToList(versionWeightValue, DiscoveryConstant.SEPARATE);
                for (String value : providerWeightValueList) {
                    String[] valueArray = StringUtils.split(value, DiscoveryConstant.EQUALS);
                    String version = valueArray[0].trim();
                    int weight = 0;
                    try {
                        weight = Integer.valueOf(valueArray[1].trim());
                        if (weight < 0) {
                            weight = 0;
                        }
                    } catch (NumberFormatException ex) {

                    }

                    weightMap.put(version, weight);
                }

                weightFilterEntity.setVersionWeightEntity(versionWeightEntity);
            }
        }

        String regionWeightValue = pluginContextHolder.getContext(DiscoveryConstant.N_D_REGION_WEIGHT);
        if (StringUtils.isNotEmpty(regionWeightValue)) {
            try {
                List<WeightEntity> weightEntityList = new ArrayList<WeightEntity>();

                Map<String, String> regionWeightMap = JsonUtil.fromJson(regionWeightValue, Map.class);
                for (Map.Entry<String, String> entry : regionWeightMap.entrySet()) {
                    String providerServiceName = entry.getKey();
                    String providerWeightValue = entry.getValue();

                    WeightEntity weightEntity = new WeightEntity();
                    weightEntity.setProviderServiceName(providerServiceName);

                    Map<String, Integer> weightMap = weightEntity.getWeightMap();
                    List<String> providerWeightValueList = StringUtil.splitToList(providerWeightValue, DiscoveryConstant.SEPARATE);
                    for (String value : providerWeightValueList) {
                        String[] valueArray = StringUtils.split(value, DiscoveryConstant.EQUALS);
                        String region = valueArray[0].trim();
                        int weight = 0;
                        try {
                            weight = Integer.valueOf(valueArray[1].trim());
                            if (weight < 0) {
                                weight = 0;
                            }
                        } catch (NumberFormatException e) {

                        }

                        weightMap.put(region, weight);
                    }

                    weightEntityList.add(weightEntity);
                }

                weightFilterEntity.setRegionWeightEntityList(weightEntityList);
            } catch (Exception e) {
                RegionWeightEntity regionWeightEntity = new RegionWeightEntity();

                Map<String, Integer> weightMap = regionWeightEntity.getWeightMap();
                List<String> providerWeightValueList = StringUtil.splitToList(regionWeightValue, DiscoveryConstant.SEPARATE);
                for (String value : providerWeightValueList) {
                    String[] valueArray = StringUtils.split(value, DiscoveryConstant.EQUALS);
                    String region = valueArray[0].trim();
                    int weight = 0;
                    try {
                        weight = Integer.valueOf(valueArray[1].trim());
                        if (weight < 0) {
                            weight = 0;
                        }
                    } catch (NumberFormatException ex) {

                    }

                    weightMap.put(region, weight);
                }

                weightFilterEntity.setRegionWeightEntity(regionWeightEntity);
            }
        }

        return weightFilterEntity;
    }

    @Override
    public int getWeight(Server server, WeightFilterEntity weightFilterEntity) {
        List<WeightEntity> versionWeightEntityList = weightFilterEntity.getVersionWeightEntityList();
        VersionWeightEntity versionWeightEntity = weightFilterEntity.getVersionWeightEntity();

        List<WeightEntity> regionWeightEntityList = weightFilterEntity.getRegionWeightEntityList();
        RegionWeightEntity regionWeightEntity = weightFilterEntity.getRegionWeightEntity();

        String providerServiceId = pluginAdapter.getServerServiceId(server);
        String providerVersion = pluginAdapter.getServerVersion(server);
        String providerRegion = pluginAdapter.getServerRegion(server);

        int weight = WeightRandomLoadBalanceUtil.getVersionWeight(providerServiceId, providerVersion, versionWeightEntityList);
        if (weight < 0) {
            weight = WeightRandomLoadBalanceUtil.getVersionWeight(providerVersion, versionWeightEntity);
        }

        if (weight < 0) {
            weight = WeightRandomLoadBalanceUtil.getRegionWeight(providerServiceId, providerRegion, regionWeightEntityList);
        }
        if (weight < 0) {
            weight = WeightRandomLoadBalanceUtil.getRegionWeight(providerRegion, regionWeightEntity);
        }

        // 所有的权重配置都没找到，则按权重值为0来处理
        if (weight < 0) {
            weight = 0;
        }

        return weight;
    }
}