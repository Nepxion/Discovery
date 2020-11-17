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

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.entity.StrategyConditionEntity;
import com.nepxion.discovery.common.entity.StrategyRouteType;
import com.nepxion.discovery.common.util.StringUtil;
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
    public ResponseEntity<?> validateExpression(@RequestParam @ApiParam(value = "条件表达式，格式示例：#H['a'] == '1' && #H['b'] != '2'。注意，引号是否为中文格式", required = true) String condition, @RequestParam(required = false, defaultValue = "") @ApiParam(value = "校验参数，格式示例：a=1;b=1。如果多个用“;”分隔，不允许出现空格。允许为空", required = false, defaultValue = "") String validation) {
        StrategyConditionEntity strategyConditionEntity = new StrategyConditionEntity();
        strategyConditionEntity.setConditionHeader(condition);

        Map<String, String> map = null;
        try {
            map = StringUtil.splitToMap(validation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid format for validation input");
        }

        boolean validated = strategyCondition.isTriggered(strategyConditionEntity, map);

        return ResponseEntity.ok().body(validated);
    }

    @RequestMapping(path = "/validate-route", method = RequestMethod.GET)
    @ApiOperation(value = "校验策略的全链路路由", notes = "", response = String.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> validateVersionRoute(@RequestParam @ApiParam(value = "路由策略类型取值：version | region | address | version-weight | region-weight | id-blacklist | address-blacklist", required = true) String routeType, @RequestParam(required = false, defaultValue = "") @ApiParam(value = "校验参数，格式示例：a=1;b=1。如果多个用“;”分隔，不允许出现空格，允许为空。如果选择最后两项策略类型，则不需要校验参数", required = false, defaultValue = "") String validation) {
        StrategyRouteType strategyRouteType = StrategyRouteType.fromString(routeType);

        Map<String, String> map = null;
        try {
            map = StringUtil.splitToMap(validation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid format for validation input");
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

        return ResponseEntity.ok().body(route);
    }
}