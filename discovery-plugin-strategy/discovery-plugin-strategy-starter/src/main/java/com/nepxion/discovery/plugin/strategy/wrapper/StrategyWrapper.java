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
import com.nepxion.discovery.common.entity.StrategyBlacklistEntity;
import com.nepxion.discovery.common.entity.StrategyConditionBlueGreenEntity;
import com.nepxion.discovery.common.entity.StrategyConditionGrayEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.common.entity.StrategyHeaderEntity;
import com.nepxion.discovery.common.entity.StrategyReleaseEntity;
import com.nepxion.discovery.common.entity.StrategyRouteEntity;
import com.nepxion.discovery.common.entity.StrategyRouteType;
import com.nepxion.discovery.common.entity.VersionWeightEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.loadbalance.weight.WeightRandomProcessor;
import com.nepxion.discovery.plugin.strategy.condition.StrategyCondition;

public class StrategyWrapper {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Autowired
    protected StrategyCondition strategyCondition;

    @Autowired
    protected WeightRandomProcessor<String> strategyWeightRandomProcessor;

    // 从远程配置中心或者本地配置文件获取版本匹配路由配置。如果是远程配置中心，则值会动态改变
    // 返回格式允许如下一种
    // 1. Json格式，内容：服务名 -> 单个或者逗号分隔多个版本号（例如：{"a-service":"1.0", "b-service":"1.1;1.2"}）
    // 2. 纯字符串格式，内容：单个或者逗号分隔多个版本号（例如：1.0;1.1;1.2）
    public String getRouteVersion() {
        return getRouteVersion(null);
    }

    // 从远程配置中心或者本地配置文件获取区域匹配路由配置。如果是远程配置中心，则值会动态改变
    // 返回格式允许如下一种
    // 1. Json格式，内容：服务名 -> 单个或者逗号分隔多个区域值（例如：{"a-service":"dev", "b-service":"dev;qa"}）
    // 2. 纯字符串格式，内容：单个或者逗号分隔多个区域值（例如：dev;qa）
    public String getRouteRegion() {
        return getRouteRegion(null);
    }

    // 从远程配置中心或者本地配置文件获取IP地址和端口匹配路由配置。如果是远程配置中心，则值会动态改变
    // 返回格式允许如下一种
    // 1. Json格式，内容：服务名 -> 单个或者逗号分隔多个IP地址和端口（例如：{"a-service":"127.0.0.1:3001", "b-service":"4002"}）
    // 2. 纯字符串格式，内容：单个或者逗号分隔多个IP地址和端口（例如：127.0.0.1:3001;4002）
    public String getRouteAddress() {
        return getRouteAddress(null);
    }

    // 从远程配置中心或者本地配置文件获取版本权重配置。如果是远程配置中心，则值会动态改变
    // 返回格式允许如下一种
    // 1. Json格式，内容：服务名 -> 单个或者逗号分隔多个版本百分比（例如：1.0=90;1.1=10）
    // 2. 纯字符串格式，内容：单个或者逗号分隔多个版本百分比（例如：1.0=90;1.1=10）
    public String getRouteVersionWeight() {
        return getRouteVersionWeight(null);
    }

    // 从远程配置中心或者本地配置文件获取区域权重配置。如果是远程配置中心，则值会动态改变
    // 返回格式允许如下一种
    // 1. Json格式，内容：服务名 -> 单个或者逗号分隔多个区域百分比（例如：dev=85;qa=15）
    // 2. 纯字符串格式，内容：单个或者逗号分隔多个区域百分比（例如：dev=85;qa=15）
    public String getRouteRegionWeight() {
        return getRouteRegionWeight(null);
    }

    // 从远程配置中心或者本地配置文件获取全局唯一ID黑名单屏蔽配置。如果是远程配置中心，则值会动态改变
    // 返回格式允许如下一种
    // 1. Json格式，内容：服务名 -> 单个或者逗号分隔多个全局唯一ID
    // 2. 纯字符串格式，内容：单个或者逗号分隔多个全局唯一ID
    public String getRouteIdBlacklist() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyBlacklistEntity strategyBlacklistEntity = ruleEntity.getStrategyBlacklistEntity();
            if (strategyBlacklistEntity != null) {
                return strategyBlacklistEntity.getIdValue();
            }
        }

        return null;
    }

    // 从远程配置中心或者本地配置文件获取IP地址和端口黑名单屏蔽配置。如果是远程配置中心，则值会动态改变
    // 返回格式允许如下一种
    // 1. Json格式，内容：服务名 -> 单个或者逗号分隔多个IP地址和端口
    // 2. 纯字符串格式，内容：单个或者逗号分隔多个IP地址和端口
    public String getRouteAddressBlacklist() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyBlacklistEntity strategyBlacklistEntity = ruleEntity.getStrategyBlacklistEntity();
            if (strategyBlacklistEntity != null) {
                return strategyBlacklistEntity.getAddressValue();
            }
        }

        return null;
    }

    // 从远程配置中心或者本地配置文件获取版本匹配路由配置。如果是远程配置中心，则值会动态改变
    // Map值为如下一种
    // 1. Header
    // 2. Parameter
    // 3. Cookie
    // 执行优先级为
    // 1. 蓝绿路由配置
    // 2. 灰度路由配置
    // 3. 全局兜底路由配置
    public String getRouteVersion(Map<String, String> map) {
        String routeVersion = getConditionBlueGreenRouteVersion(map);
        if (StringUtils.isEmpty(routeVersion)) {
            routeVersion = getConditionGrayRouteVersion(map);
            if (StringUtils.isEmpty(routeVersion)) {
                routeVersion = getGlobalRouteVersion();
            }
        }

        return routeVersion;
    }

    // 从远程配置中心或者本地配置文件获取区域匹配路由配置。如果是远程配置中心，则值会动态改变
    // Map值为如下一种
    // 1. Header
    // 2. Parameter
    // 3. Cookie
    // 执行优先级为
    // 1. 蓝绿路由配置
    // 2. 灰度路由配置
    // 3. 全局缺省路由配置
    public String getRouteRegion(Map<String, String> map) {
        String routeRegion = getConditionBlueGreenRouteRegion(map);
        if (StringUtils.isEmpty(routeRegion)) {
            routeRegion = getConditionGrayRouteRegion(map);
            if (StringUtils.isEmpty(routeRegion)) {
                routeRegion = getGlobalRouteRegion();
            }
        }

        return routeRegion;
    }

    // 从远程配置中心或者本地配置文件获取IP地址和端口匹配路由配置。如果是远程配置中心，则值会动态改变
    // Map值为如下一种
    // 1. Header
    // 2. Parameter
    // 3. Cookie
    // 执行优先级为
    // 1. 蓝绿路由配置
    // 2. 灰度路由配置
    // 3. 全局缺省路由配置
    public String getRouteAddress(Map<String, String> map) {
        String routeAddress = getConditionBlueGreenRouteAddress(map);
        if (StringUtils.isEmpty(routeAddress)) {
            routeAddress = getConditionGrayRouteAddress(map);
            if (StringUtils.isEmpty(routeAddress)) {
                routeAddress = getGlobalRouteAddress();
            }
        }

        return routeAddress;
    }

    // 从远程配置中心或者本地配置文件获取版本权重路由配置。如果是远程配置中心，则值会动态改变
    // Map值为如下一种
    // 1. Header
    // 2. Parameter
    // 3. Cookie
    // 执行优先级为
    // 1. 蓝绿路由配置
    // 2. 全局缺省路由配置
    public String getRouteVersionWeight(Map<String, String> map) {
        String routeVersionWeight = getConditionBlueGreenRouteVersionWeight(map);
        if (StringUtils.isEmpty(routeVersionWeight)) {
            routeVersionWeight = getGlobalRouteVersionWeight();
        }

        return routeVersionWeight;
    }

    // 从远程配置中心或者本地配置文件获取区域权重路由配置。如果是远程配置中心，则值会动态改变
    // Map值为如下一种
    // 1. Header
    // 2. Parameter
    // 3. Cookie
    // 执行优先级为
    // 1. 蓝绿路由配置
    // 2. 全局缺省路由配置
    public String getRouteRegionWeight(Map<String, String> map) {
        String routeRegionWeight = getConditionBlueGreenRouteRegionWeight(map);
        if (StringUtils.isEmpty(routeRegionWeight)) {
            routeRegionWeight = getGlobalRouteRegionWeight();
        }

        return routeRegionWeight;
    }

    // 获取全局版本匹配路由配置
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

    // 获取全局区域匹配路由配置
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

    // 获取全局IP地址和端口匹配路由配置
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

    // 获取全局版本权重路由配置
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

    // 获取全局区域权重路由配置
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

    // 获取条件驱动下，版本匹配路由的蓝绿配置
    public String getConditionBlueGreenRouteVersion(Map<String, String> map) {
        StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity = getTriggeredStrategyConditionBlueGreenEntity(StrategyRouteType.VERSION, map);
        if (strategyConditionBlueGreenEntity != null) {
            String versionId = strategyConditionBlueGreenEntity.getVersionId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(versionId, StrategyRouteType.VERSION);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    // 获取条件驱动下，区域匹配路由的蓝绿配置
    public String getConditionBlueGreenRouteRegion(Map<String, String> map) {
        StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity = getTriggeredStrategyConditionBlueGreenEntity(StrategyRouteType.REGION, map);
        if (strategyConditionBlueGreenEntity != null) {
            String regionId = strategyConditionBlueGreenEntity.getRegionId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(regionId, StrategyRouteType.REGION);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    // 获取条件驱动下，IP地址和端口匹配路由的蓝绿配置
    public String getConditionBlueGreenRouteAddress(Map<String, String> map) {
        StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity = getTriggeredStrategyConditionBlueGreenEntity(StrategyRouteType.ADDRESS, map);
        if (strategyConditionBlueGreenEntity != null) {
            String addressId = strategyConditionBlueGreenEntity.getAddressId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(addressId, StrategyRouteType.ADDRESS);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    // 获取条件驱动下，版本权重路由的蓝绿配置
    public String getConditionBlueGreenRouteVersionWeight(Map<String, String> map) {
        StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity = getTriggeredStrategyConditionBlueGreenEntity(StrategyRouteType.VERSION_WEIGHT, map);
        if (strategyConditionBlueGreenEntity != null) {
            String versionWeightId = strategyConditionBlueGreenEntity.getVersionWeightId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(versionWeightId, StrategyRouteType.VERSION_WEIGHT);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    // 获取条件驱动下，区域权重路由的蓝绿配置
    public String getConditionBlueGreenRouteRegionWeight(Map<String, String> map) {
        StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity = getTriggeredStrategyConditionBlueGreenEntity(StrategyRouteType.REGION_WEIGHT, map);
        if (strategyConditionBlueGreenEntity != null) {
            String regionWeightId = strategyConditionBlueGreenEntity.getRegionWeightId();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(regionWeightId, StrategyRouteType.REGION_WEIGHT);
            if (strategyRouteEntity != null) {
                return strategyRouteEntity.getValue();
            }
        }

        return null;
    }

    // 获取被条件命中的蓝绿条件对象
    private StrategyConditionBlueGreenEntity getTriggeredStrategyConditionBlueGreenEntity(StrategyRouteType strategyRouteType, Map<String, String> map) {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyReleaseEntity strategyReleaseEntity = ruleEntity.getStrategyReleaseEntity();
            if (strategyReleaseEntity != null) {
                List<StrategyConditionBlueGreenEntity> strategyConditionBlueGreenEntityList = strategyReleaseEntity.getStrategyConditionBlueGreenEntityList();
                if (CollectionUtils.isNotEmpty(strategyConditionBlueGreenEntityList)) {
                    StrategyConditionBlueGreenEntity expressionStrategyConditionBlueGreenEntity = getTriggeredExpressionStrategyConditionBlueGreenEntity(strategyConditionBlueGreenEntityList, strategyRouteType, map);
                    if (expressionStrategyConditionBlueGreenEntity != null) {
                        return expressionStrategyConditionBlueGreenEntity;
                    } else {
                        StrategyConditionBlueGreenEntity globalStrategyConditionBlueGreenEntity = getTriggeredGlobalStrategyConditionBlueGreenyEntity(strategyConditionBlueGreenEntityList, strategyRouteType);
                        if (globalStrategyConditionBlueGreenEntity != null) {
                            return globalStrategyConditionBlueGreenEntity;
                        }
                    }
                }
            }
        }

        return null;
    }

    // 获取被条件命中的蓝绿条件对象
    private StrategyConditionBlueGreenEntity getTriggeredExpressionStrategyConditionBlueGreenEntity(List<StrategyConditionBlueGreenEntity> strategyConditionBlueGreenEntityList, StrategyRouteType strategyRouteType, Map<String, String> map) {
        for (StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity : strategyConditionBlueGreenEntityList) {
            boolean isValidated = validateBlueGreenStrategyType(strategyConditionBlueGreenEntity, strategyRouteType);
            if (isValidated) {
                boolean isTriggered = false;
                if (map == null) {
                    isTriggered = strategyCondition.isTriggered(strategyConditionBlueGreenEntity);
                } else {
                    isTriggered = strategyCondition.isTriggered(strategyConditionBlueGreenEntity, map);
                }
                if (isTriggered) {
                    return strategyConditionBlueGreenEntity;
                }
            }
        }

        return null;
    }

    // 获取不带有条件设置的蓝绿条件对象
    // 蓝绿条件驱动允许缺省
    private StrategyConditionBlueGreenEntity getTriggeredGlobalStrategyConditionBlueGreenyEntity(List<StrategyConditionBlueGreenEntity> strategyConditionBlueGreenEntityList, StrategyRouteType strategyRouteType) {
        for (StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity : strategyConditionBlueGreenEntityList) {
            boolean isValidated = validateBlueGreenStrategyType(strategyConditionBlueGreenEntity, strategyRouteType);
            if (isValidated) {
                String expression = strategyConditionBlueGreenEntity.getExpression();
                if (StringUtils.isEmpty(expression)) {
                    return strategyConditionBlueGreenEntity;
                }
            }
        }

        return null;
    }

    // 校验蓝绿路由的类型
    private boolean validateBlueGreenStrategyType(StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity, StrategyRouteType strategyRouteType) {
        switch (strategyRouteType) {
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
            case ID_BLACKLIST:
                break;
            case ADDRESS_BLACKLIST:
                break;
        }

        return false;
    }

    // 获取条件驱动下，版本权重路由的灰度配置
    public String getConditionGrayRouteVersion(Map<String, String> map) {
        StrategyConditionGrayEntity strategyConditionGrayEntity = getTriggeredStrategyConditionGrayEntity(map);
        if (strategyConditionGrayEntity != null) {
            VersionWeightEntity versionWeightEntity = strategyConditionGrayEntity.getVersionWeightEntity();
            if (versionWeightEntity != null) {
                return getTriggeredStrategyGrayRoute(versionWeightEntity, StrategyRouteType.VERSION);
            }
        }

        return null;
    }

    // 获取条件驱动下，区域权重路由的灰度配置
    public String getConditionGrayRouteRegion(Map<String, String> map) {
        StrategyConditionGrayEntity strategyConditionGrayEntity = getTriggeredStrategyConditionGrayEntity(map);
        if (strategyConditionGrayEntity != null) {
            RegionWeightEntity regionWeightEntity = strategyConditionGrayEntity.getRegionWeightEntity();
            if (regionWeightEntity != null) {
                return getTriggeredStrategyGrayRoute(regionWeightEntity, StrategyRouteType.REGION);
            }
        }

        return null;
    }

    // 获取条件驱动下，IP地址和端口权重路由的灰度配置
    public String getConditionGrayRouteAddress(Map<String, String> map) {
        StrategyConditionGrayEntity strategyConditionGrayEntity = getTriggeredStrategyConditionGrayEntity(map);
        if (strategyConditionGrayEntity != null) {
            AddressWeightEntity addressWeightEntity = strategyConditionGrayEntity.getAddressWeightEntity();
            if (addressWeightEntity != null) {
                return getTriggeredStrategyGrayRoute(addressWeightEntity, StrategyRouteType.ADDRESS);
            }
        }

        return null;
    }

    // 命中灰度条件
    private StrategyConditionGrayEntity getTriggeredStrategyConditionGrayEntity(Map<String, String> map) {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyReleaseEntity strategyReleaseEntity = ruleEntity.getStrategyReleaseEntity();
            if (strategyReleaseEntity != null) {
                List<StrategyConditionGrayEntity> strategyConditionGrayEntityList = strategyReleaseEntity.getStrategyConditionGrayEntityList();
                if (CollectionUtils.isNotEmpty(strategyConditionGrayEntityList)) {
                    StrategyConditionGrayEntity expressionStrategyConditionGrayEntity = getTriggeredExpressionStrategyConditionGrayEntity(strategyConditionGrayEntityList, map);
                    if (expressionStrategyConditionGrayEntity != null) {
                        return expressionStrategyConditionGrayEntity;
                    } else {
                        StrategyConditionGrayEntity globalStrategyConditionGrayEntity = getTriggeredGlobalStrategyConditionGrayEntity(strategyConditionGrayEntityList);
                        if (globalStrategyConditionGrayEntity != null) {
                            return globalStrategyConditionGrayEntity;
                        }
                    }
                }
            }
        }

        return null;
    }

    // 获取被条件命中的灰度条件对象
    private StrategyConditionGrayEntity getTriggeredExpressionStrategyConditionGrayEntity(List<StrategyConditionGrayEntity> strategyConditionGrayEntityList, Map<String, String> map) {
        for (StrategyConditionGrayEntity strategyConditionGrayEntity : strategyConditionGrayEntityList) {
            boolean isTriggered = false;
            if (map == null) {
                isTriggered = strategyCondition.isTriggered(strategyConditionGrayEntity);
            } else {
                isTriggered = strategyCondition.isTriggered(strategyConditionGrayEntity, map);
            }
            if (isTriggered) {
                return strategyConditionGrayEntity;
            }
        }

        return null;
    }

    // 获取不带有条件设置的灰度条件对象
    // 灰度条件驱动允许缺省
    private StrategyConditionGrayEntity getTriggeredGlobalStrategyConditionGrayEntity(List<StrategyConditionGrayEntity> strategyConditionGrayEntityList) {
        for (StrategyConditionGrayEntity strategyConditionGrayEntity : strategyConditionGrayEntityList) {
            String expression = strategyConditionGrayEntity.getExpression();
            if (StringUtils.isEmpty(expression)) {
                return strategyConditionGrayEntity;
            }
        }

        return null;
    }

    // 获取被条件命中的灰度路由
    private String getTriggeredStrategyGrayRoute(MapWeightEntity mapWeightEntity, StrategyRouteType strategyRouteType) {
        Map<String, Integer> weightMap = mapWeightEntity.getWeightMap();
        if (MapUtils.isEmpty(weightMap)) {
            return null;
        }

        List<Pair<String, Integer>> weightList = new ArrayList<Pair<String, Integer>>();
        for (Map.Entry<String, Integer> entry : weightMap.entrySet()) {
            String id = entry.getKey();
            StrategyRouteEntity strategyRouteEntity = getTriggeredStrategyRouteEntity(id, strategyRouteType);
            if (strategyRouteEntity != null) {
                String strategyRoute = strategyRouteEntity.getValue();
                Integer weight = Integer.valueOf(entry.getValue());
                weightList.add(new ImmutablePair<String, Integer>(strategyRoute, weight));
            }
        }

        return strategyWeightRandomProcessor.random(weightList);
    }

    // 根据路由ID和路由类型查询相关的路由对象
    private StrategyRouteEntity getTriggeredStrategyRouteEntity(String id, StrategyRouteType strategyRouteType) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }

        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyReleaseEntity strategyReleaseEntity = ruleEntity.getStrategyReleaseEntity();
            if (strategyReleaseEntity != null) {
                List<StrategyRouteEntity> strategyRouteEntityList = strategyReleaseEntity.getStrategyRouteEntityList();
                if (CollectionUtils.isNotEmpty(strategyRouteEntityList)) {
                    for (StrategyRouteEntity strategyRouteEntity : strategyRouteEntityList) {
                        if (StringUtils.equals(strategyRouteEntity.getId(), id) && strategyRouteEntity.getType() == strategyRouteType) {
                            return strategyRouteEntity;
                        }
                    }
                }
            }
        }

        return null;
    }

    // 获取内置Header Map
    public Map<String, String> getHeaderMap() {
        RuleEntity ruleEntity = pluginAdapter.getRule();
        if (ruleEntity != null) {
            StrategyReleaseEntity strategyReleaseEntity = ruleEntity.getStrategyReleaseEntity();
            if (strategyReleaseEntity != null) {
                StrategyHeaderEntity strategyHeaderEntity = strategyReleaseEntity.getStrategyHeaderEntity();
                if (strategyHeaderEntity != null) {
                    return strategyHeaderEntity.getHeaderMap();
                }
            }
        }

        return null;
    }

    // 获取内置Header
    public String getHeader(String name) {
        Map<String, String> headerMap = getHeaderMap();
        if (MapUtils.isNotEmpty(headerMap)) {
            return headerMap.get(name);
        }

        return null;
    }
}