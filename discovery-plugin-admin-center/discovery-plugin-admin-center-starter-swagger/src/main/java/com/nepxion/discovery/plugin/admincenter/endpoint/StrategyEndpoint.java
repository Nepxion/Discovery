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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.plugin.admincenter.resource.StrategyResource;

@RestController
@RequestMapping(path = "/strategy")
@Api(tags = { "策略接口" })
public class StrategyEndpoint {
    @Autowired
    private StrategyResource strategyResource;

    @RequestMapping(path = "/validate-expression", method = RequestMethod.GET)
    @ApiOperation(value = "校验策略的条件表达式", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> validateExpression(@RequestParam @ApiParam(value = "条件表达式，格式示例：#H['a'] == '1' && #H['b'] != '2'。注意，引号是否为中文格式", defaultValue = "#H['a'] == '1' && #H['b'] != '2'", required = true) String expression, @RequestParam(defaultValue = "", required = false) @ApiParam(value = "校验参数，格式示例：a=1;b=1。如果多个用“;”分隔，不允许出现空格。允许为空", defaultValue = "a=1;b=1") String validation) {
        return doValidateExpression(expression, validation);
    }

    @RequestMapping(path = "/validate-route", method = RequestMethod.GET)
    @ApiOperation(value = "校验策略的全链路路由", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> validateRoute(@RequestParam @ApiParam(value = "路由策略类型。取值：version | region | address | version-weight | region-weight | id-blacklist | address-blacklist", defaultValue = "version", required = true) String routeType, @RequestParam(defaultValue = "", required = false) @ApiParam(value = "校验参数，格式示例：a=1;b=1。如果多个用“;”分隔，不允许出现空格，允许为空。如果选择最后两项策略类型，则不需要校验参数", defaultValue = "a=1;b=1") String validation) {
        return doValidateRoute(routeType, validation);
    }

    private ResponseEntity<?> doValidateExpression(String expression, String validation) {
        try {
            boolean result = strategyResource.validateExpression(expression, validation);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doValidateRoute(String routeType, String validation) {
        try {
            String route = strategyResource.validateRoute(routeType, validation);

            return ResponseUtil.getSuccessResponse(route);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}