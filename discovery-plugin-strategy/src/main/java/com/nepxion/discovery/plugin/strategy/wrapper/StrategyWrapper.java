package com.nepxion.discovery.plugin.strategy.wrapper;

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
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.MapWeightEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyConditionEntity;
import com.nepxion.discovery.common.entity.StrategyCustomizationEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.common.entity.StrategyRouteEntity;
import com.nepxion.discovery.common.entity.StrategyType;
import com.nepxion.discovery.common.entity.StrategyWeightEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.loadbalance.weight.MapWeightRandom;
import com.nepxion.discovery.plugin.strategy.condition.StrategyCondition;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;

public class StrategyWrapper {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    @Autowired
    protected StrategyCondition strategyCondition;

    // 从远程配置中心或者本地配置文件获取版本路由配置。如果是远程配置中心，则值会动态改变
    public String getRouteVersion() {
        String routeVersion = getConditionRouteVersion();
        if (StringUtils.isEmpty(routeVersion)) {
            routeVersion = getWeightRouteVersion();
            if (StringUtils.isEmpty(routeVersion)) {
                routeVersion = getGlobalRouteVersion();
            }
        }

        return routeVersion;
    }

    // 从远程配置中心或者本地配置文件获取区域路由配置。如果是远程配置中心，则值会动态改变
    public String getRouteRegion() {
        String routeRegion = getConditionRouteRegion();
        if (StringUtils.isEmpty(routeRegion)) {
            routeRegion = getWeightRouteRegion();
            if (StringUtils.isEmpty(routeRegion)) {
                routeRegion = getGlobalRouteRegion();
            }
        }

        return routeRegion;
    }

    // 从远程配置中心或者本地配置文件获取IP地址和端口路由配置。如果是远程配置中心，则值会动态改变
    public String getRouteAddress() {
        String routeAddress = getConditionRouteAddress();
        if (StringUtils.isEmpty(routeAddress)) {
            routeAddress = getWeightRouteAddress();
            if (StringUtils.isEmpty(routeAddress)) {
                routeAddress = getGlobalRouteAddress();
            }
        }

        return routeAddress;
    }

    // 从远程配置中心或者本地配置文件获取版本权重配置。如果是远程配置中心，则值会动态改变
    public String getRouteVersionWeight() {
        String routeVersionWeight = getConditionRouteVersionWeight();
        if (StringUtils.isEmpty(routeVersionWeight)) {
            routeVersionWeight = getGlobalRouteVersionWeight();
        }

        return routeVersionWeight;
    }

    // 从远程配置中心或者本地配置文件获取区域权重配置。如果是远程配置中心，则值会动态改变
    public String getRouteRegionWeight() {
        String routeRegionWeight = getConditionRouteRegionWeight();
        if (StringUtils.isEmpty(routeRegionWeight)) {
            routeRegionWeight = getGlobalRouteRegionWeight();
        }

        return routeRegionWeight;
    }

    public String getGlobalRouteVersion() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();
            if (strategyEntity != null) {
                return strategyEntity.getVersionValue();
            }
        }

        return null;
    }

    public String getGlobalRouteRegion() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();
            if (strategyEntity != null) {
                return strategyEntity.getRegionValue();
            }
        }

        return null;
    }

    public String getGlobalRouteAddress() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();
            if (strategyEntity != null) {
                return strategyEntity.getAddressValue();
            }
        }

        return null;
    }

    public String getGlobalRouteVersionWeight() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();
            if (strategyEntity != null) {
                return strategyEntity.getVersionWeightValue();
            }
        }

        return null;
    }

    public String getGlobalRouteRegionWeight() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();
            if (strategyEntity != null) {
                return strategyEntity.getRegionWeightValue();
            }
        }

        return null;
    }

    public String getConditionRouteVersion() {
        StrategyConditionEntity strategyConditionEntity = getTriggeredStrategyConditionEntity(StrategyType.VERSION);
        if (strategyConditionEntity != null) {
            String versionId = strategyConditionEntity.getVersionId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(versionId, StrategyType.VERSION);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    public String getConditionRouteRegion() {
        StrategyConditionEntity strategyConditionEntity = getTriggeredStrategyConditionEntity(StrategyType.REGION);
        if (strategyConditionEntity != null) {
            String regionId = strategyConditionEntity.getRegionId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(regionId, StrategyType.REGION);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    public String getConditionRouteAddress() {
        StrategyConditionEntity strategyConditionEntity = getTriggeredStrategyConditionEntity(StrategyType.ADDRESS);
        if (strategyConditionEntity != null) {
            String addressId = strategyConditionEntity.getAddressId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(addressId, StrategyType.ADDRESS);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    public String getConditionRouteVersionWeight() {
        StrategyConditionEntity strategyConditionEntity = getTriggeredStrategyConditionEntity(StrategyType.VERSION_WEIGHT);
        if (strategyConditionEntity != null) {
            String versionWeightId = strategyConditionEntity.getVersionWeightId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(versionWeightId, StrategyType.VERSION_WEIGHT);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    public String getConditionRouteRegionWeight() {
        StrategyConditionEntity strategyConditionEntity = getTriggeredStrategyConditionEntity(StrategyType.REGION_WEIGHT);
        if (strategyConditionEntity != null) {
            String regionWeightId = strategyConditionEntity.getRegionWeightId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(regionWeightId, StrategyType.REGION_WEIGHT);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    private StrategyRouteEntity getTriggeredStrategyRouteEntity(String id, StrategyType strategyType) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyCustomizationEntity strategyCustomizationEntity = ruleEntity.getStrategyCustomizationEntity();
            if (strategyCustomizationEntity != null) {
                List<StrategyRouteEntity> strategyRouteEntityList = strategyCustomizationEntity.getStrategyRouteEntityList();
                if (CollectionUtils.isNotEmpty(strategyRouteEntityList)) {
                    for (StrategyRouteEntity strategyRouteEntity : strategyRouteEntityList) {
                        if (StringUtils.equals(strategyRouteEntity.getId(), id) && strategyRouteEntity.getType() == strategyType) {
                            return strategyRouteEntity;
                        }
                    }
                }
            }
        }

        return null;
    }

    private StrategyConditionEntity getTriggeredStrategyConditionEntity(StrategyType strategyType) {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyCustomizationEntity strategyCustomizationEntity = ruleEntity.getStrategyCustomizationEntity();
            if (strategyCustomizationEntity != null) {
                List<StrategyConditionEntity> strategyConditionEntityList = strategyCustomizationEntity.getStrategyConditionEntityList();
                if (CollectionUtils.isNotEmpty(strategyConditionEntityList)) {
                    for (StrategyConditionEntity strategyConditionEntity : strategyConditionEntityList) {
                        boolean isValidated = validateStrategyType(strategyConditionEntity, strategyType);
                        if (isValidated) {
                            boolean isTriggered = strategyCondition.isTriggered(strategyConditionEntity);
                            if (isTriggered) {
                                return strategyConditionEntity;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    private boolean validateStrategyType(StrategyConditionEntity strategyConditionEntity, StrategyType strategyType) {
        switch (strategyType) {
            case VERSION:
                return StringUtils.isNotEmpty(strategyConditionEntity.getVersionId());
            case REGION:
                return StringUtils.isNotEmpty(strategyConditionEntity.getRegionId());
            case ADDRESS:
                return StringUtils.isNotEmpty(strategyConditionEntity.getAddressId());
            case VERSION_WEIGHT:
                return StringUtils.isNotEmpty(strategyConditionEntity.getVersionWeightId());
            case REGION_WEIGHT:
                return StringUtils.isNotEmpty(strategyConditionEntity.getRegionWeightId());
        }

        return false;
    }

    public String getWeightRouteVersion() {
        StrategyWeightEntity strategyWeightEntity = getTriggeredStrategyWeightEntity();
        if (strategyWeightEntity != null) {
            MapWeightEntity mapWeightEntity = strategyWeightEntity.getVersionMapWeightEntity();
            if (mapWeightEntity != null) {
                return getTriggeredStrategyWeight(mapWeightEntity, StrategyType.VERSION);
            }
        }

        return null;
    }

    public String getWeightRouteRegion() {
        StrategyWeightEntity strategyWeightEntity = getTriggeredStrategyWeightEntity();
        if (strategyWeightEntity != null) {
            MapWeightEntity mapWeightEntity = strategyWeightEntity.getRegionMapWeightEntity();
            if (mapWeightEntity != null) {
                return getTriggeredStrategyWeight(mapWeightEntity, StrategyType.REGION);
            }
        }

        return null;
    }

    public String getWeightRouteAddress() {
        StrategyWeightEntity strategyWeightEntity = getTriggeredStrategyWeightEntity();
        if (strategyWeightEntity != null) {
            MapWeightEntity mapWeightEntity = strategyWeightEntity.getAddressMapWeightEntity();
            if (mapWeightEntity != null) {
                return getTriggeredStrategyWeight(mapWeightEntity, StrategyType.ADDRESS);
            }
        }

        return null;
    }

    private StrategyWeightEntity getTriggeredStrategyWeightEntity() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyCustomizationEntity strategyCustomizationEntity = ruleEntity.getStrategyCustomizationEntity();
            if (strategyCustomizationEntity != null) {
                List<StrategyWeightEntity> strategyWeightEntityList = strategyCustomizationEntity.getStrategyWeightEntityList();
                if (CollectionUtils.isNotEmpty(strategyWeightEntityList)) {
                    return strategyWeightEntityList.get(0);
                }
            }
        }

        return null;
    }

    private String getTriggeredStrategyWeight(MapWeightEntity mapWeightEntity, StrategyType strategyType) {
        Map<String, Integer> weightMap = mapWeightEntity.getWeightMap();
        if (MapUtils.isEmpty(weightMap)) {
            return null;
        }

        List<Pair<String, Double>> weightList = new ArrayList<Pair<String, Double>>();
        for (Map.Entry<String, Integer> entry : weightMap.entrySet()) {
            String id = entry.getKey();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(id, strategyType);
            if (strategyRouteEntity != null) {
                String strategyRoute = strategyRouteEntity.getValue();
                Double weight = Double.valueOf(entry.getValue());
                weightList.add(new ImmutablePair<String, Double>(strategyRoute, weight));
            }
        }
        MapWeightRandom<String, Double> weightRandom = new MapWeightRandom<String, Double>(weightList);

        return weightRandom.random();
    }
}