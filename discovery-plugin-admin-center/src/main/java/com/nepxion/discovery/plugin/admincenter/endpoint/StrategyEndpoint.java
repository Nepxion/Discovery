package com.nepxion.discovery.plugin.admincenter.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.StrategyConditionEntity;
import com.nepxion.discovery.common.entity.StrategyType;
import com.nepxion.discovery.plugin.strategy.condition.StrategyCondition;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyWrapper;

@RestController
@RequestMapping(path = "/strategy")
@Api(tags = { "策略接口" })
public class StrategyEndpoint {
    @Autowired
    private StrategyCondition strategyCondition;

    @Autowired
    private StrategyWrapper strategyWrapper;

    @RequestMapping(path = "/validate-expression", method = RequestMethod.GET)
    @ApiOperation(value = "校验策略的条件表达式", notes = "", response = Boolean.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<Boolean> validateExpression(@RequestParam @ApiParam(value = "条件表达式，例如：#H['a'] == '1' && #H['b'] != '2'", required = true) String condition, @RequestParam(required = false, defaultValue = "") @ApiParam(value = "变量赋值，例如：a=1;b=1。如果多个用“;”分隔，不允许出现空格。允许为空", required = false, defaultValue = "") String validation) {
        StrategyConditionEntity strategyConditionEntity = new StrategyConditionEntity();
        strategyConditionEntity.setConditionHeader(condition);

        Map<String, String> map = convertMap(validation);

        boolean validated = strategyCondition.isTriggered(strategyConditionEntity, map);

        return ResponseEntity.ok().body(validated);
    }

    @RequestMapping(path = "/validate-route", method = RequestMethod.GET)
    @ApiOperation(value = "校验策略的全链路路由", notes = "", response = String.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<String> validateVersionRoute(@RequestParam @ApiParam(value = "策略类型，例如：version, region, address, version-weight, region-weight", required = true) String strategyType, @RequestParam(required = false, defaultValue = "") @ApiParam(value = "变量赋值，例如：a=1;b=1。如果多个用“;”分隔，不允许出现空格。允许为空", required = false, defaultValue = "") String validation) {
        StrategyType type = StrategyType.fromString(strategyType);

        Map<String, String> map = convertMap(validation);

        String route = null;
        switch (type) {
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
        }

        return ResponseEntity.ok().body(route);
    }

    private Map<String, String> convertMap(String text) {
        Map<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotEmpty(text)) {
            String[] separateArray = text.split(DiscoveryConstant.SEPARATE);
            for (String value : separateArray) {
                String[] equalsArray = value.split(DiscoveryConstant.EQUALS);
                map.put(equalsArray[0], equalsArray[1]);
            }
        }

        return map;
    }
}