package com.nepxion.discovery.plugin.strategy.wrapper;

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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyConditionEntity;
import com.nepxion.discovery.common.entity.StrategyCustomizationEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.common.entity.StrategyRouteEntity;
import com.nepxion.discovery.common.entity.StrategyType;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;

public class StrategyWrapper {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyContextHolder strategyContextHolder;

    // 从远程配置中心或者本地配置文件获取版本路由配置。如果是远程配置中心，则值会动态改变
    public String getRouteVersion() {
        String routeVersion = getCustomizationRouteVersion();
        if (StringUtils.isEmpty(routeVersion)) {
            routeVersion = getGlobalRouteVersion();
        }

        return routeVersion;
    }

    // 从远程配置中心或者本地配置文件获取区域路由配置。如果是远程配置中心，则值会动态改变
    public String getRouteRegion() {
        String routeRegion = getCustomizationRouteRegion();
        if (StringUtils.isEmpty(routeRegion)) {
            routeRegion = getGlobalRouteRegion();
        }

        return routeRegion;
    }

    // 从远程配置中心或者本地配置文件获取IP地址和端口路由配置。如果是远程配置中心，则值会动态改变
    public String getRouteAddress() {
        String routeAddress = getCustomizationRouteAddress();
        if (StringUtils.isEmpty(routeAddress)) {
            routeAddress = getGlobalRouteAddress();
        }

        return routeAddress;
    }

    // 从远程配置中心或者本地配置文件获取版本权重配置。如果是远程配置中心，则值会动态改变
    public String getRouteVersionWeight() {
        String routeVersionWeight = getCustomizationRouteVersionWeight();
        if (StringUtils.isEmpty(routeVersionWeight)) {
            routeVersionWeight = getGlobalRouteVersionWeight();
        }

        return routeVersionWeight;
    }

    // 从远程配置中心或者本地配置文件获取区域权重配置。如果是远程配置中心，则值会动态改变
    public String getRouteRegionWeight() {
        String routeRegionWeight = getCustomizationRouteRegionWeight();
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

    public String getCustomizationRouteVersion() {
        StrategyConditionEntity strategyConditionEntity = getCustomizationTriggeredStrategyConditionEntity();
        if (strategyConditionEntity != null) {
            String versionId = strategyConditionEntity.getVersionId();
            StrategyRouteEntity strategyRouteEntity = getCustomizationTriggeredStrategyRouteEntity(versionId, StrategyType.VERSION);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    public String getCustomizationRouteRegion() {
        StrategyConditionEntity strategyConditionEntity = getCustomizationTriggeredStrategyConditionEntity();
        if (strategyConditionEntity != null) {
            String regionId = strategyConditionEntity.getRegionId();
            StrategyRouteEntity strategyRouteEntity = getCustomizationTriggeredStrategyRouteEntity(regionId, StrategyType.REGION);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    public String getCustomizationRouteAddress() {
        StrategyConditionEntity strategyConditionEntity = getCustomizationTriggeredStrategyConditionEntity();
        if (strategyConditionEntity != null) {
            String addressId = strategyConditionEntity.getAddressId();
            StrategyRouteEntity strategyRouteEntity = getCustomizationTriggeredStrategyRouteEntity(addressId, StrategyType.ADDRESS);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    public String getCustomizationRouteVersionWeight() {
        StrategyConditionEntity strategyConditionEntity = getCustomizationTriggeredStrategyConditionEntity();
        if (strategyConditionEntity != null) {
            String versionWeightId = strategyConditionEntity.getVersionWeightId();
            StrategyRouteEntity strategyRouteEntity = getCustomizationTriggeredStrategyRouteEntity(versionWeightId, StrategyType.VERSION_WEIGHT);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    public String getCustomizationRouteRegionWeight() {
        StrategyConditionEntity strategyConditionEntity = getCustomizationTriggeredStrategyConditionEntity();
        if (strategyConditionEntity != null) {
            String regionWeightId = strategyConditionEntity.getRegionWeightId();
            StrategyRouteEntity strategyRouteEntity = getCustomizationTriggeredStrategyRouteEntity(regionWeightId, StrategyType.REGION_WEIGHT);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    private StrategyRouteEntity getCustomizationTriggeredStrategyRouteEntity(String id, StrategyType type) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyCustomizationEntity strategyCustomizationEntity = ruleEntity.getStrategyCustomizationEntity();
            if (strategyCustomizationEntity != null) {
                List<StrategyRouteEntity> strategyRouteEntityList = strategyCustomizationEntity.getStrategyRouteEntityList();
                for (StrategyRouteEntity strategyRouteEntity : strategyRouteEntityList) {
                    if (StringUtils.equals(strategyRouteEntity.getId(), id) && strategyRouteEntity.getType() == type) {
                        return strategyRouteEntity;
                    }
                }
            }
        }

        return null;
    }

    private StrategyConditionEntity getCustomizationTriggeredStrategyConditionEntity() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyCustomizationEntity strategyCustomizationEntity = ruleEntity.getStrategyCustomizationEntity();
            if (strategyCustomizationEntity != null) {
                List<StrategyConditionEntity> strategyConditionEntityList = strategyCustomizationEntity.getStrategyConditionEntityList();
                for (StrategyConditionEntity strategyConditionEntity : strategyConditionEntityList) {
                    boolean isTriggered = isCustomizationTriggered(strategyConditionEntity);
                    if (isTriggered) {
                        return strategyConditionEntity;
                    }
                }
            }
        }

        return null;
    }

    private boolean isCustomizationTriggered(StrategyConditionEntity strategyConditionEntity) {
        Map<String, String> headerMap = strategyConditionEntity.getHeaderMap();
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            String headerName = entry.getKey();
            String headerValue = entry.getValue();

            String value = strategyContextHolder.getHeader(headerName);
            if (!StringUtils.equals(headerValue, value)) {
                return false;
            }
        }

        return true;
    }
}