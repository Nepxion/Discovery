package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.TypeComparator;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.ConditionBlueGreenEntity;
import com.nepxion.discovery.common.entity.ConditionBlueGreenRoute;
import com.nepxion.discovery.common.entity.ConditionGrayEntity;
import com.nepxion.discovery.common.entity.ConditionRouteStrategy;
import com.nepxion.discovery.common.entity.ConditionStrategy;
import com.nepxion.discovery.common.entity.InstanceEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyConditionBlueGreenEntity;
import com.nepxion.discovery.common.entity.StrategyConditionGrayEntity;
import com.nepxion.discovery.common.entity.StrategyEntity;
import com.nepxion.discovery.common.entity.StrategyHeaderEntity;
import com.nepxion.discovery.common.entity.StrategyReleaseEntity;
import com.nepxion.discovery.common.entity.StrategyRouteEntity;
import com.nepxion.discovery.common.entity.StrategyRouteType;
import com.nepxion.discovery.common.entity.VersionSortType;
import com.nepxion.discovery.common.entity.VersionWeightEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.expression.DiscoveryExpressionResolver;
import com.nepxion.discovery.common.expression.DiscoveryTypeComparator;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.common.util.VersionSortUtil;
import com.nepxion.discovery.common.util.YamlUtil;
import com.nepxion.discovery.common.yaml.YamlSafeConstructor;
import com.nepxion.discovery.console.constant.ConsoleConstant;
import com.nepxion.discovery.console.delegate.ConsoleResourceDelegateImpl;

public class StrategyResourceImpl extends ConsoleResourceDelegateImpl implements StrategyResource {
    public static final String CONDITION = "condition";
    public static final String ROUTE = "route";

    private TypeComparator typeComparator = new DiscoveryTypeComparator();

    private YamlSafeConstructor conditionStrategyYamlSafeConstructor = new YamlSafeConstructor(new LinkedHashSet<Class<?>>(Arrays.asList(ConditionStrategy.class)));

    private YamlSafeConstructor conditionRouteStrategyYamlSafeConstructor = new YamlSafeConstructor(new LinkedHashSet<Class<?>>(Arrays.asList(ConditionRouteStrategy.class)));

    @Autowired
    private ServiceResource serviceResource;

    @Autowired
    private ConfigResource configResource;

    @Value("${" + ConsoleConstant.SPRING_APPLICATION_CONSOLE_STRATEGY_ENDPOINT_VALIDATE_EXPRESSION_ENABLED + ":true}")
    private Boolean validateExpressionEnabled;

    @Override
    public ConditionStrategy getVersionRelease(String group) {
        return getVersionRelease(group, null);
    }

    @Override
    public String createVersionRelease(String group, String conditionStrategyYaml) {
        ConditionStrategy conditionStrategy = deparseVersionReleaseYaml(conditionStrategyYaml);

        return createVersionRelease(group, conditionStrategy);
    }

    @Override
    public String createVersionRelease(String group, ConditionStrategy conditionStrategy) {
        return createVersionRelease(group, null, conditionStrategy);
    }

    @Override
    public String recreateVersionRelease(String group, String conditionRouteStrategyYaml) {
        return recreateVersionRelease(group, null, conditionRouteStrategyYaml);
    }

    @Override
    public String recreateVersionRelease(String group, ConditionRouteStrategy conditionRouteStrategy) {
        return recreateVersionRelease(group, null, conditionRouteStrategy);
    }

    @Override
    public String resetRelease(String group) {
        return resetRelease(group, null);
    }

    @Override
    public String clearRelease(String group) {
        return clearRelease(group, null);
    }

    @Override
    public ConditionStrategy getVersionRelease(String group, String serviceId) {
        RuleEntity ruleEntity = getRemoteRuleEntity(group, serviceId);

        return deparseVersionStrategyRelease(ruleEntity);
    }

    @Override
    public String createVersionRelease(String group, String serviceId, String conditionStrategyYaml) {
        ConditionStrategy conditionStrategy = deparseVersionReleaseYaml(conditionStrategyYaml);

        return createVersionRelease(group, serviceId, conditionStrategy);
    }

    @Override
    public String createVersionRelease(String group, String serviceId, ConditionStrategy conditionStrategy) {
        RuleEntity ruleEntity = getRemoteRuleEntity(group, serviceId);

        createVersionStrategyRelease(ruleEntity, conditionStrategy);

        updateRemoteRuleEntity(group, serviceId, ruleEntity);

        return configResource.fromRuleEntity(ruleEntity);
    }

    @Override
    public String recreateVersionRelease(String group, String serviceId, String conditionRouteStrategyYaml) {
        ConditionRouteStrategy conditionRouteStrategy = YamlUtil.fromYaml(conditionRouteStrategyYamlSafeConstructor, conditionRouteStrategyYaml, ConditionRouteStrategy.class);

        return recreateVersionRelease(group, serviceId, conditionRouteStrategy);
    }

    @Override
    public String recreateVersionRelease(String group, String serviceId, ConditionRouteStrategy conditionRouteStrategy) {
        RuleEntity ruleEntity = getRemoteRuleEntity(group, serviceId);

        List<String> service = conditionRouteStrategy.getService();
        boolean condition = conditionRouteStrategy.isCondition(); // 是否创建条件路由
        String sort = conditionRouteStrategy.getSort();

        ConditionStrategy conditionStrategy = deparseVersionStrategyRelease(ruleEntity);
        conditionStrategy.setService(service);
        conditionStrategy.setSort(sort);

        createVersionStrategyRelease(ruleEntity, conditionStrategy, condition);

        updateRemoteRuleEntity(group, serviceId, ruleEntity);

        return configResource.fromRuleEntity(ruleEntity);
    }

    @Override
    public String resetRelease(String group, String serviceId) {
        RuleEntity ruleEntity = getRemoteRuleEntity(group, serviceId);

        resetStrategyRelease(ruleEntity);

        updateRemoteRuleEntity(group, serviceId, ruleEntity);

        return configResource.fromRuleEntity(ruleEntity);
    }

    @Override
    public String clearRelease(String group, String serviceId) {
        RuleEntity ruleEntity = getRemoteRuleEntity(group, serviceId);

        clearStrategyRelease(ruleEntity);

        updateRemoteRuleEntity(group, serviceId, ruleEntity);

        return configResource.fromRuleEntity(ruleEntity);
    }

    @Override
    public String parseVersionRelease(String conditionStrategyYaml) {
        ConditionStrategy conditionStrategy = deparseVersionReleaseYaml(conditionStrategyYaml);

        return parseVersionRelease(conditionStrategy);
    }

    @Override
    public String parseVersionRelease(ConditionStrategy conditionStrategy) {
        RuleEntity ruleEntity = new RuleEntity();

        createVersionStrategyRelease(ruleEntity, conditionStrategy);

        return configResource.fromRuleEntity(ruleEntity);
    }

    @Override
    public ConditionStrategy deparseVersionReleaseXml(String ruleXml) {
        RuleEntity ruleEntity = configResource.toRuleEntity(ruleXml);

        return deparseVersionStrategyRelease(ruleEntity);
    }

    @Override
    public ConditionStrategy deparseVersionReleaseYaml(String conditionStrategyYaml) {
        return YamlUtil.fromYaml(conditionStrategyYamlSafeConstructor, conditionStrategyYaml, ConditionStrategy.class);
    }

    @Override
    public boolean validateExpression(String expression, String validation) {
        if (!validateExpressionEnabled) {
            throw new DiscoveryException("Strategy endpoint for validate-expression is disabled");
        }

        Map<String, String> map = null;
        try {
            map = StringUtil.splitToMap(validation);
        } catch (Exception e) {
            throw new DiscoveryException("Invalid format for validation input");
        }

        return DiscoveryExpressionResolver.eval(expression, DiscoveryConstant.EXPRESSION_PREFIX, map, typeComparator);
    }

    private void createVersionStrategyRelease(RuleEntity ruleEntity, ConditionStrategy conditionStrategy) {
        createVersionStrategyRelease(ruleEntity, conditionStrategy, true);
    }

    private void createVersionStrategyRelease(RuleEntity ruleEntity, ConditionStrategy conditionStrategy, boolean createConditionRoute) {
        List<String> serviceList = conditionStrategy.getService();
        // 输入的服务列表为空，不允许执行蓝绿灰度发布，抛出异常
        if (CollectionUtils.isEmpty(serviceList)) {
            throw new DiscoveryException("Services are empty");
        }

        String sort = conditionStrategy.getSort();
        VersionSortType versionSortType = VersionSortType.fromString(sort);
        Map<String, String> stableVersionMap = new LinkedHashMap<String, String>();
        Map<String, String> unstableVersionMap = new LinkedHashMap<String, String>();
        for (String service : serviceList) {
            List<String> versionList = assembleVersionList(service, versionSortType);
            // 如果线上版本为0个，不允许执行蓝绿灰度发布，抛出异常
            if (CollectionUtils.isEmpty(versionList)) {
                throw new DiscoveryException("Service[" + service + "] has no versions");
            }

            // 旧版本，取值第1个
            String stableVersion = versionList.get(0);
            stableVersionMap.put(service, stableVersion);

            String unstableVersion = null;
            // 如果线上版本只有1个（属于用户误把非蓝绿灰度发布的服务添加入名单），新/旧版本相同，取值第1个
            if (versionList.size() == 1) {
                unstableVersion = versionList.get(0);
                // 如果线上版本只有2个（标准蓝绿灰度发布），取值第2个
            } else if (versionList.size() == 2) {
                unstableVersion = versionList.get(1);
                // 如果线上版本多于2个，取值第2个到最后1个，用分号分隔
                // 例如，3个版本为1.0, 2.0, 3.0，新版本为“2.0;3.0”
            } else {
                versionList.remove(0);

                unstableVersion = StringUtil.convertToString(versionList);
            }
            unstableVersionMap.put(service, unstableVersion);
        }

        List<ConditionBlueGreenEntity> blueGreenList = conditionStrategy.getBlueGreen();
        List<ConditionGrayEntity> grayList = conditionStrategy.getGray();

        StrategyEntity strategyEntity = new StrategyEntity();
        strategyEntity.setVersionValue(JsonUtil.toJson(stableVersionMap));
        ruleEntity.setStrategyEntity(strategyEntity);

        List<StrategyConditionBlueGreenEntity> strategyConditionBlueGreenEntityList = null;
        if (CollectionUtils.isNotEmpty(blueGreenList)) {
            if (blueGreenList.size() != 2) {
                throw new DiscoveryException("BlueGreens must have two expressions");
            }

            ConditionBlueGreenEntity conditionBlueGreenEntity0 = blueGreenList.get(0);
            String blueGreenExpression0 = conditionBlueGreenEntity0.getExpression();
            if (StringUtils.isEmpty(blueGreenExpression0)) {
                throw new DiscoveryException("BlueGreens must not have empty expressions");
            }
            String blueGreenRoute0 = conditionBlueGreenEntity0.getRoute();

            ConditionBlueGreenEntity conditionBlueGreenEntity1 = blueGreenList.get(1);
            String blueGreenExpression1 = conditionBlueGreenEntity1.getExpression();
            if (StringUtils.isEmpty(blueGreenExpression1)) {
                throw new DiscoveryException("BlueGreens must not have empty expressions");
            }
            String blueGreenRoute1 = conditionBlueGreenEntity1.getRoute();

            if (ConditionBlueGreenRoute.fromString(blueGreenRoute0) == ConditionBlueGreenRoute.fromString(blueGreenRoute1)) {
                throw new DiscoveryException("BlueGreens must have one expression with green, another expression with blue");
            }

            StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity0 = new StrategyConditionBlueGreenEntity();
            strategyConditionBlueGreenEntity0.setId(CONDITION + "-" + (ConditionBlueGreenRoute.fromString(blueGreenRoute0) == ConditionBlueGreenRoute.GREEN ? "0" : "1"));
            strategyConditionBlueGreenEntity0.setVersionId(ROUTE + "-" + (ConditionBlueGreenRoute.fromString(blueGreenRoute0) == ConditionBlueGreenRoute.GREEN ? "0" : "1"));
            strategyConditionBlueGreenEntity0.setExpression(blueGreenExpression0);

            StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity1 = new StrategyConditionBlueGreenEntity();
            strategyConditionBlueGreenEntity1.setId(CONDITION + "-" + (ConditionBlueGreenRoute.fromString(blueGreenRoute1) == ConditionBlueGreenRoute.BLUE ? "1" : "0"));
            strategyConditionBlueGreenEntity1.setVersionId(ROUTE + "-" + (ConditionBlueGreenRoute.fromString(blueGreenRoute1) == ConditionBlueGreenRoute.BLUE ? "1" : "0"));
            strategyConditionBlueGreenEntity1.setExpression(blueGreenExpression1);

            strategyConditionBlueGreenEntityList = new ArrayList<StrategyConditionBlueGreenEntity>();
            strategyConditionBlueGreenEntityList.add(strategyConditionBlueGreenEntity0);
            strategyConditionBlueGreenEntityList.add(strategyConditionBlueGreenEntity1);
        }

        List<StrategyConditionGrayEntity> strategyConditionGrayEntityList = null;
        if (CollectionUtils.isNotEmpty(grayList)) {
            strategyConditionGrayEntityList = new ArrayList<StrategyConditionGrayEntity>();

            int index = 0;
            for (ConditionGrayEntity gray : grayList) {
                String grayExpression = gray.getExpression();
                List<Integer> grayWeightList = gray.getWeight();
                if (grayWeightList.size() != 2) {
                    throw new DiscoveryException("Grays weights must have 2 values");
                }

                Map<String, Integer> weightMap = new LinkedHashMap<String, Integer>();
                weightMap.put(ROUTE + "-0", grayWeightList.get(0));
                weightMap.put(ROUTE + "-1", grayWeightList.get(1));

                VersionWeightEntity versionWeightEntity = new VersionWeightEntity();
                versionWeightEntity.setWeightMap(weightMap);

                StrategyConditionGrayEntity strategyConditionGrayEntity = new StrategyConditionGrayEntity();
                strategyConditionGrayEntity.setId(CONDITION + "-" + index);
                strategyConditionGrayEntity.setExpression(grayExpression);
                strategyConditionGrayEntity.setVersionWeightEntity(versionWeightEntity);
                strategyConditionGrayEntityList.add(strategyConditionGrayEntity);

                index++;
            }
        }

        if (strategyConditionBlueGreenEntityList != null || strategyConditionGrayEntityList != null) {
            StrategyReleaseEntity strategyReleaseEntity = new StrategyReleaseEntity();
            ruleEntity.setStrategyReleaseEntity(strategyReleaseEntity);

            if (strategyConditionBlueGreenEntityList != null) {
                strategyReleaseEntity.setStrategyConditionBlueGreenEntityList(strategyConditionBlueGreenEntityList);
            }

            if (strategyConditionGrayEntityList != null) {
                strategyReleaseEntity.setStrategyConditionGrayEntityList(strategyConditionGrayEntityList);
            }

            if (createConditionRoute) {
                StrategyRouteEntity strategyRouteEntity0 = new StrategyRouteEntity();
                strategyRouteEntity0.setId(ROUTE + "-0");
                strategyRouteEntity0.setType(StrategyRouteType.VERSION);
                strategyRouteEntity0.setValue(JsonUtil.toJson(stableVersionMap));

                StrategyRouteEntity strategyRouteEntity1 = new StrategyRouteEntity();
                strategyRouteEntity1.setId(ROUTE + "-1");
                strategyRouteEntity1.setType(StrategyRouteType.VERSION);
                strategyRouteEntity1.setValue(JsonUtil.toJson(unstableVersionMap));

                List<StrategyRouteEntity> strategyRouteEntityList = new ArrayList<StrategyRouteEntity>();
                strategyRouteEntityList.add(strategyRouteEntity0);
                strategyRouteEntityList.add(strategyRouteEntity1);
                strategyReleaseEntity.setStrategyRouteEntityList(strategyRouteEntityList);
            }

            Map<String, String> headerMap = conditionStrategy.getHeader();
            StrategyHeaderEntity strategyHeaderEntity = new StrategyHeaderEntity();
            strategyHeaderEntity.setHeaderMap(headerMap);
            strategyReleaseEntity.setStrategyHeaderEntity(strategyHeaderEntity);
        }
    }

    @SuppressWarnings("unchecked")
    private ConditionStrategy deparseVersionStrategyRelease(RuleEntity ruleEntity) {
        ConditionStrategy conditionStrategy = new ConditionStrategy();

        StrategyEntity strategyEntity = ruleEntity.getStrategyEntity();
        if (strategyEntity != null) {
            String versionValue = strategyEntity.getVersionValue();
            if (StringUtils.isNotEmpty(versionValue)) {
                Map<String, String> serviceMap = JsonUtil.fromJson(versionValue, Map.class);
                conditionStrategy.setService(serviceMap.entrySet().stream().map(entry -> entry.getKey()).collect(Collectors.toList()));
            }
        }

        StrategyReleaseEntity strategyReleaseEntity = ruleEntity.getStrategyReleaseEntity();
        if (strategyReleaseEntity != null) {
            List<StrategyConditionBlueGreenEntity> strategyConditionBlueGreenEntityList = strategyReleaseEntity.getStrategyConditionBlueGreenEntityList();
            if (CollectionUtils.isNotEmpty(strategyConditionBlueGreenEntityList)) {
                List<ConditionBlueGreenEntity> blueGreen = new ArrayList<ConditionBlueGreenEntity>();
                for (StrategyConditionBlueGreenEntity strategyConditionBlueGreenEntity : strategyConditionBlueGreenEntityList) {
                    String id = strategyConditionBlueGreenEntity.getId();
                    String expression = strategyConditionBlueGreenEntity.getExpression();

                    ConditionBlueGreenEntity conditionBlueGreenEntity = new ConditionBlueGreenEntity();
                    conditionBlueGreenEntity.setExpression(expression);
                    if (StringUtils.equals(id, CONDITION + "-0")) {
                        conditionBlueGreenEntity.setRoute(ConditionBlueGreenRoute.GREEN.toString());
                    } else if (StringUtils.equals(id, CONDITION + "-1")) {
                        conditionBlueGreenEntity.setRoute(ConditionBlueGreenRoute.BLUE.toString());
                    }
                    blueGreen.add(conditionBlueGreenEntity);
                }
                conditionStrategy.setBlueGreen(blueGreen);
            }

            List<StrategyConditionGrayEntity> strategyConditionGrayEntityList = strategyReleaseEntity.getStrategyConditionGrayEntityList();
            if (CollectionUtils.isNotEmpty(strategyConditionGrayEntityList)) {
                List<ConditionGrayEntity> gray = new ArrayList<ConditionGrayEntity>();
                for (StrategyConditionGrayEntity strategyConditionGrayEntity : strategyConditionGrayEntityList) {
                    String expression = strategyConditionGrayEntity.getExpression();

                    ConditionGrayEntity conditionGrayEntity = new ConditionGrayEntity();
                    conditionGrayEntity.setExpression(expression);
                    VersionWeightEntity versionWeightEntity = strategyConditionGrayEntity.getVersionWeightEntity();
                    if (versionWeightEntity != null) {
                        Map<String, Integer> weightMap = versionWeightEntity.getWeightMap();
                        if (MapUtils.isNotEmpty(weightMap)) {
                            int weight0 = weightMap.get(ROUTE + "-0");
                            int weight1 = weightMap.get(ROUTE + "-1");
                            conditionGrayEntity.setWeight(Arrays.asList(weight0, weight1));
                        }
                    }
                    gray.add(conditionGrayEntity);
                }
                conditionStrategy.setGray(gray);
            }

            StrategyHeaderEntity strategyHeaderEntity = strategyReleaseEntity.getStrategyHeaderEntity();
            if (strategyHeaderEntity != null) {
                conditionStrategy.setHeader(strategyHeaderEntity.getHeaderMap());
            }
        }

        return conditionStrategy;
    }

    private void clearStrategyRelease(RuleEntity ruleEntity) {
        ruleEntity.setStrategyEntity(null);
        ruleEntity.setStrategyReleaseEntity(null);
    }

    private void resetStrategyRelease(RuleEntity ruleEntity) {
        ruleEntity.setStrategyEntity(null);
        StrategyReleaseEntity strategyReleaseEntity = ruleEntity.getStrategyReleaseEntity();
        if (strategyReleaseEntity != null) {
            strategyReleaseEntity.setStrategyRouteEntityList(null);
        }
    }

    private List<String> assembleVersionList(String serviceId, VersionSortType versionSortType) {
        List<InstanceEntity> instanceEntityList = serviceResource.getInstanceList(serviceId);

        return VersionSortUtil.assembleVersionList(instanceEntityList, versionSortType);
    }
}