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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.TypeComparator;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.ConditionBlueGreenEntity;
import com.nepxion.discovery.common.entity.ConditionBlueGreenRoute;
import com.nepxion.discovery.common.entity.ConditionGrayEntity;
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
import com.nepxion.discovery.common.entity.VersionWeightEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.expression.DiscoveryExpressionResolver;
import com.nepxion.discovery.common.expression.DiscoveryTypeComparor;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.console.delegate.ConsoleResourceDelegateImpl;

public class StrategyResourceImpl extends ConsoleResourceDelegateImpl implements StrategyResource {
    public static final String CONDITION = "condition";
    public static final String ROUTE = "route";

    private TypeComparator typeComparator = new DiscoveryTypeComparor();

    @Autowired
    private ServiceResource serviceResource;

    @Autowired
    private ConfigResource configResource;

    @Override
    public String parseVersionRelease(ConditionStrategy conditionStrategy) {
        RuleEntity ruleEntity = new RuleEntity();

        createVersionStrategyRelease(ruleEntity, conditionStrategy);

        return configResource.deparse(ruleEntity);
    }

    @Override
    public String createVersionRelease(String group, ConditionStrategy conditionStrategy) {
        return createVersionRelease(group, null, conditionStrategy);
    }

    @Override
    public String clearRelease(String group) {
        return clearRelease(group, null);
    }

    @Override
    public String createVersionRelease(String group, String gatewayId, ConditionStrategy conditionStrategy) {
        RuleEntity ruleEntity = getRemoteRuleEntity(group, gatewayId);

        createVersionStrategyRelease(ruleEntity, conditionStrategy);

        updateRemoteRuleEntity(group, gatewayId, ruleEntity);

        return configResource.deparse(ruleEntity);
    }

    @Override
    public String clearRelease(String group, String gatewayId) {
        RuleEntity ruleEntity = getRemoteRuleEntity(group, gatewayId);

        clearStrategyRelease(ruleEntity);

        updateRemoteRuleEntity(group, gatewayId, ruleEntity);

        return configResource.deparse(ruleEntity);
    }

    @Override
    public boolean validateExpression(String expression, String validation) {
        Map<String, String> map = null;
        try {
            map = StringUtil.splitToMap(validation);
        } catch (Exception e) {
            throw new DiscoveryException("Invalid format for validation input");
        }

        return DiscoveryExpressionResolver.eval(expression, DiscoveryConstant.EXPRESSION_PREFIX, map, typeComparator);
    }

    private void createVersionStrategyRelease(RuleEntity ruleEntity, ConditionStrategy conditionStrategy) {
        List<String> serviceList = conditionStrategy.getService();
        if (CollectionUtils.isEmpty(serviceList)) {
            throw new DiscoveryException("Services are empty");
        }

        Map<String, String> stableVersionMap = new LinkedHashMap<String, String>();
        Map<String, String> unstableVersionMap = new LinkedHashMap<String, String>();
        for (String service : serviceList) {
            List<String> versionList = assembleVersionList(service);
            if (CollectionUtils.isEmpty(versionList)) {
                throw new DiscoveryException("Register Centery - Service[" + service + "] has no versions");
            }

            String stableVersion = versionList.get(0);
            stableVersionMap.put(service, stableVersion);

            if (versionList.size() > 1) {
                String unstableVersion = versionList.get(1);
                unstableVersionMap.put(service, unstableVersion);
            }
        }

        List<ConditionBlueGreenEntity> blueGreenList = conditionStrategy.getBlueGreen();
        List<ConditionGrayEntity> grayList = conditionStrategy.getGray();
        if (CollectionUtils.isNotEmpty(blueGreenList) || CollectionUtils.isNotEmpty(grayList)) {
            if (stableVersionMap.size() != unstableVersionMap.size()) {
                throw new DiscoveryException("Register Centery - all services must have two versions while BlueGreen or Gray Release");
            }
        }

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

            Map<String, String> headerMap = conditionStrategy.getHeader();
            StrategyHeaderEntity strategyHeaderEntity = new StrategyHeaderEntity();
            strategyHeaderEntity.setHeaderMap(headerMap);
            strategyReleaseEntity.setStrategyHeaderEntity(strategyHeaderEntity);
        }
    }

    private void clearStrategyRelease(RuleEntity ruleEntity) {
        ruleEntity.setStrategyEntity(null);
        ruleEntity.setStrategyReleaseEntity(null);
    }

    private List<String> assembleVersionList(String serviceId) {
        List<InstanceEntity> instanceEntityList = serviceResource.getInstanceList(serviceId);

        List<String> versionList = new ArrayList<String>();
        for (InstanceEntity instanceEntity : instanceEntityList) {
            String version = instanceEntity.getVersion();
            versionList.add(version);
        }

        if (versionList.size() > 1) {
            Collections.sort(versionList);
        }

        return versionList;
    }
}