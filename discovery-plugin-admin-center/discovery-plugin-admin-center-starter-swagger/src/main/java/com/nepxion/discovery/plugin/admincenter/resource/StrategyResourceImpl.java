package com.nepxion.discovery.plugin.admincenter.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.entity.StrategyConditionEntity;
import com.nepxion.discovery.common.entity.StrategyRouteType;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.admincenter.constant.AdminConstant;
import com.nepxion.discovery.plugin.strategy.condition.StrategyCondition;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyWrapper;

public class StrategyResourceImpl implements StrategyResource {
    @Autowired
    private StrategyCondition strategyCondition;

    @Autowired
    private StrategyWrapper strategyWrapper;

    @Value("${" + AdminConstant.SPRING_APPLICATION_ADMIN_STRATEGY_ENDPOINT_VALIDATE_EXPRESSION_ENABLED + ":true}")
    private Boolean validateExpressionEnabled;

    @Override
    public boolean validateExpression(String expression, String validation) {
        if (!validateExpressionEnabled) {
            throw new DiscoveryException("Strategy endpoint for validate-expression is disabled");
        }

        StrategyConditionEntity strategyConditionEntity = new StrategyConditionEntity();
        strategyConditionEntity.setExpression(expression);

        Map<String, String> map = null;
        try {
            map = StringUtil.splitToMap(validation);
        } catch (Exception e) {
            throw new DiscoveryException("Invalid format for validation input");
        }

        return strategyCondition.isTriggered(strategyConditionEntity, map);
    }

    @Override
    public String validateRoute(String routeType, String validation) {
        StrategyRouteType strategyRouteType = StrategyRouteType.fromString(routeType);

        Map<String, String> map = null;
        try {
            map = StringUtil.splitToMap(validation);
        } catch (Exception e) {
            throw new DiscoveryException("Invalid format for validation input");
        }

        String route = null;
        switch (strategyRouteType) {
            case VERSION:
                route = strategyWrapper.getRouteVersion(map);
                break;
            case REGION:
                route = strategyWrapper.getRouteRegion(map);
                break;
            case ADDRESS:
                route = strategyWrapper.getRouteAddress(map);
                break;
            case VERSION_WEIGHT:
                route = strategyWrapper.getRouteVersionWeight(map);
                break;
            case REGION_WEIGHT:
                route = strategyWrapper.getRouteRegionWeight(map);
                break;
            case ID_BLACKLIST:
                route = strategyWrapper.getRouteIdBlacklist();
                break;
            case ADDRESS_BLACKLIST:
                route = strategyWrapper.getRouteAddressBlacklist();
                break;
        }

        if (StringUtils.isEmpty(route)) {
            throw new DiscoveryException("Not found any " + routeType + " routes");
        }

        return route;
    }
}