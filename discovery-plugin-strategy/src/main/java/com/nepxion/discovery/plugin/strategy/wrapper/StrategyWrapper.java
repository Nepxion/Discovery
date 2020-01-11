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

import com.nepxion.discovery.common.entity.AddressWeightEntity;
import com.nepxion.discovery.common.entity.MapWeightEntity;
import com.nepxion.discovery.common.entity.RegionWeightEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyConditionBlueGreenEntity;
import com.nepxion.discovery.common.entity.StrategyConditionGrayEntity;
import com.nepxion.discovery.common.entity.StrategyCustomizationEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.common.entity.StrategyRouteEntity;
import com.nepxion.discovery.common.entity.StrategyType;
import com.nepxion.discovery.common.entity.VersionWeightEntity;
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
        String routeVersion = getConditionBlueGreenRouteVersion();
        if (StringUtils.isEmpty(routeVersion)) {
            routeVersion = getConditionGrayRouteVersion();
            if (StringUtils.isEmpty(routeVersion)) {
                routeVersion = getGlobalRouteVersion();
            }
        }

        return routeVersion;
    }

    // 从远程配置中心或者本地配置文件获取区域路由配置。如果是远程配置中心，则值会动态改变
    public String getRouteRegion() {
        String routeRegion = getConditionBlueGreenRouteRegion();
        if (StringUtils.isEmpty(routeRegion)) {
            routeRegion = getConditionGrayRouteRegion();
            if (StringUtils.isEmpty(routeRegion)) {
                routeRegion = getGlobalRouteRegion();
            }
        }

        return routeRegion;
    }

    // 从远程配置中心或者本地配置文件获取IP地址和端口路由配置。如果是远程配置中心，则值会动态改变
    public String getRouteAddress() {
        String routeAddress = getConditionBlueGreenRouteAddress();
        if (StringUtils.isEmpty(routeAddress)) {
            routeAddress = getConditionGrayRouteAddress();
            if (StringUtils.isEmpty(routeAddress)) {
                routeAddress = getGlobalRouteAddress();
            }
        }

        return routeAddress;
    }

    // 从远程配置中心或者本地配置文件获取版本权重配置。如果是远程配置中心，则值会动态改变
    public String getRouteVersionWeight() {
        String routeVersionWeight = getConditionBlueGreenRouteVersionWeight();
        if (StringUtils.isEmpty(routeVersionWeight)) {
            routeVersionWeight = getGlobalRouteVersionWeight();
        }

        return routeVersionWeight;
    }

    // 从远程配置中心或者本地配置文件获取区域权重配置。如果是远程配置中心，则值会动态改变
    public String getRouteRegionWeight() {
        String routeRegionWeight = getConditionBlueGreenRouteRegionWeight();
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

    public String getConditionBlueGreenRouteVersion() {
        StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity = getTriggeredStrategyConditionBlueGreenEntity(StrategyType.VERSION);
        if (strategyConditionBlueGreenEntity != null) {
            String versionId = strategyConditionBlueGreenEntity.getVersionId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(versionId, StrategyType.VERSION);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    public String getConditionBlueGreenRouteRegion() {
        StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity = getTriggeredStrategyConditionBlueGreenEntity(StrategyType.REGION);
        if (strategyConditionBlueGreenEntity != null) {
            String regionId = strategyConditionBlueGreenEntity.getRegionId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(regionId, StrategyType.REGION);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    public String getConditionBlueGreenRouteAddress() {
        StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity = getTriggeredStrategyConditionBlueGreenEntity(StrategyType.ADDRESS);
        if (strategyConditionBlueGreenEntity != null) {
            String addressId = strategyConditionBlueGreenEntity.getAddressId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(addressId, StrategyType.ADDRESS);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    public String getConditionBlueGreenRouteVersionWeight() {
        StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity = getTriggeredStrategyConditionBlueGreenEntity(StrategyType.VERSION_WEIGHT);
        if (strategyConditionBlueGreenEntity != null) {
            String versionWeightId = strategyConditionBlueGreenEntity.getVersionWeightId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(versionWeightId, StrategyType.VERSION_WEIGHT);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    public String getConditionBlueGreenRouteRegionWeight() {
        StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity = getTriggeredStrategyConditionBlueGreenEntity(StrategyType.REGION_WEIGHT);
        if (strategyConditionBlueGreenEntity != null) {
            String regionWeightId = strategyConditionBlueGreenEntity.getRegionWeightId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(regionWeightId, StrategyType.REGION_WEIGHT);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    private StrategyConditionBlueGreenEntity getTriggeredStrategyConditionBlueGreenEntity(StrategyType strategyType) {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyCustomizationEntity strategyCustomizationEntity = ruleEntity.getStrategyCustomizationEntity();
            if (strategyCustomizationEntity != null) {
                List<StrategyConditionBlueGreenEntity> strategyConditionBlueGreenEntityList = strategyCustomizationEntity.getStrategyConditionBlueGreenEntityList();
                if (CollectionUtils.isNotEmpty(strategyConditionBlueGreenEntityList)) {
                    StrategyConditionBlueGreenEntity expressionStrategyConditionBlueGreenEntity = getTriggeredExpressionStrategyConditionBlueGreenEntity(strategyConditionBlueGreenEntityList, strategyType);
                    if (expressionStrategyConditionBlueGreenEntity != null) {
                        return expressionStrategyConditionBlueGreenEntity;
                    }
                }
            }
        }

        return null;
    }

    private StrategyConditionBlueGreenEntity getTriggeredExpressionStrategyConditionBlueGreenEntity(List<StrategyConditionBlueGreenEntity> strategyConditionBlueGreenEntityList, StrategyType strategyType) {
        for (StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity : strategyConditionBlueGreenEntityList) {
            boolean isValidated = validateBlueGreenStrategyType(strategyConditionBlueGreenEntity, strategyType);
            if (isValidated) {
                boolean isTriggered = strategyCondition.isTriggered(strategyConditionBlueGreenEntity);
                if (isTriggered) {
                    return strategyConditionBlueGreenEntity;
                }
            }
        }

        return null;
    }

    private boolean validateBlueGreenStrategyType(StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity, StrategyType strategyType) {
        switch (strategyType) {
            case VERSION:
                return StringUtils.isNotEmpty(strategyConditionBlueGreenEntity.getVersionId());
            case REGION:
                return StringUtils.isNotEmpty(strategyConditionBlueGreenEntity.getRegionId());
            case ADDRESS:
                return StringUtils.isNotEmpty(strategyConditionBlueGreenEntity.getAddressId());
            case VERSION_WEIGHT:
                return StringUtils.isNotEmpty(strategyConditionBlueGreenEntity.getVersionWeightId());
            case REGION_WEIGHT:
                return StringUtils.isNotEmpty(strategyConditionBlueGreenEntity.getRegionWeightId());
        }

        return false;
    }

    public String getConditionGrayRouteVersion() {
        StrategyConditionGrayEntity strategyConditionGrayEntity = getTriggeredStrategyConditionGrayEntity();
        if (strategyConditionGrayEntity != null) {
            VersionWeightEntity versionWeightEntity = strategyConditionGrayEntity.getVersionWeightEntity();
            if (versionWeightEntity != null) {
                return getTriggeredStrategyGrayRoute(versionWeightEntity, StrategyType.VERSION);
            }
        }

        return null;
    }

    public String getConditionGrayRouteRegion() {
        StrategyConditionGrayEntity strategyConditionGrayEntity = getTriggeredStrategyConditionGrayEntity();
        if (strategyConditionGrayEntity != null) {
            RegionWeightEntity regionWeightEntity = strategyConditionGrayEntity.getRegionWeightEntity();
            if (regionWeightEntity != null) {
                return getTriggeredStrategyGrayRoute(regionWeightEntity, StrategyType.REGION);
            }
        }

        return null;
    }

    public String getConditionGrayRouteAddress() {
        StrategyConditionGrayEntity strategyConditionGrayEntity = getTriggeredStrategyConditionGrayEntity();
        if (strategyConditionGrayEntity != null) {
            AddressWeightEntity addressWeightEntity = strategyConditionGrayEntity.getAddressWeightEntity();
            if (addressWeightEntity != null) {
                return getTriggeredStrategyGrayRoute(addressWeightEntity, StrategyType.ADDRESS);
            }
        }

        return null;
    }

    private StrategyConditionGrayEntity getTriggeredStrategyConditionGrayEntity() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyCustomizationEntity strategyCustomizationEntity = ruleEntity.getStrategyCustomizationEntity();
            if (strategyCustomizationEntity != null) {
                List<StrategyConditionGrayEntity> strategyConditionGrayEntityList = strategyCustomizationEntity.getStrategyConditionGrayEntityList();
                if (CollectionUtils.isNotEmpty(strategyConditionGrayEntityList)) {
                    StrategyConditionGrayEntity globalStrategyConditionGrayEntity = getTriggeredGlobalStrategyConditionGrayEntity(strategyConditionGrayEntityList);
                    if (globalStrategyConditionGrayEntity != null) {
                        return globalStrategyConditionGrayEntity;
                    } else {
                        StrategyConditionGrayEntity expressionStrategyConditionGrayEntity = getTriggeredExpressionStrategyConditionGrayEntity(strategyConditionGrayEntityList);
                        if (expressionStrategyConditionGrayEntity != null) {
                            return expressionStrategyConditionGrayEntity;
                        }
                    }
                }
            }
        }

        return null;
    }

    private StrategyConditionGrayEntity getTriggeredGlobalStrategyConditionGrayEntity(List<StrategyConditionGrayEntity> strategyConditionGrayEntityList) {
        for (StrategyConditionGrayEntity strategyConditionGrayEntity : strategyConditionGrayEntityList) {
            String conditionHeader = strategyConditionGrayEntity.getConditionHeader();
            if (StringUtils.isEmpty(conditionHeader)) {
                return strategyConditionGrayEntity;
            }
        }

        return null;
    }

    private StrategyConditionGrayEntity getTriggeredExpressionStrategyConditionGrayEntity(List<StrategyConditionGrayEntity> strategyConditionGrayEntityList) {
        for (StrategyConditionGrayEntity strategyConditionGrayEntity : strategyConditionGrayEntityList) {
            boolean isTriggered = strategyCondition.isTriggered(strategyConditionGrayEntity);
            if (isTriggered) {
                return strategyConditionGrayEntity;
            }
        }

        return null;
    }

    private String getTriggeredStrategyGrayRoute(MapWeightEntity mapWeightEntity, StrategyType strategyType) {
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
}