package com.nepxion.discovery.console.endpoint;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.entity.ConditionStrategy;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.console.resource.StrategyResource;

@RestController
@RequestMapping(path = "/strategy")
@Api(tags = { "策略接口" })
public class StrategyEndpoint {
    @Autowired
    private StrategyResource strategyResource;

    @RequestMapping(path = "/parse-version-release", method = RequestMethod.POST)
    @ApiOperation(value = "解析版本蓝绿灰度发布", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> parseVersionRelease(@RequestBody @ApiParam(value = "蓝绿灰度策略对象", required = true) ConditionStrategy conditionStrategy) {
        return doParseVersionRelease(conditionStrategy);
    }

    @RequestMapping(path = "/create-version-release/{group}", method = RequestMethod.POST)
    @ApiOperation(value = "全局组订阅方式，创建版本蓝绿灰度发布", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> createVersionRelease(@PathVariable(value = "group") @ApiParam(value = "订阅的组名", required = true) String group, @RequestBody @ApiParam(value = "蓝绿灰度策略对象", required = true) ConditionStrategy conditionStrategy) {
        return doCreateVersionRelease(group, conditionStrategy);
    }

    @RequestMapping(path = "/clear-release/{group}", method = RequestMethod.POST)
    @ApiOperation(value = "全局组订阅方式，清除蓝绿灰度发布", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearRelease(@PathVariable(value = "group") @ApiParam(value = "订阅的组名", required = true) String group) {
        return doClearRelease(group);
    }

    @RequestMapping(path = "/create-version-release/{group}/{gatewayId}", method = RequestMethod.POST)
    @ApiOperation(value = "局部网关订阅方式，创建版本蓝绿灰度发布", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> createVersionRelease(@PathVariable(value = "group") @ApiParam(value = "订阅的组名", required = true) String group, @PathVariable(value = "gatewayId") @ApiParam(value = "订阅的网关名", required = true) String gatewayId, @RequestBody @ApiParam(value = "蓝绿灰度策略对象", required = true) ConditionStrategy conditionStrategy) {
        return doCreateVersionRelease(group, gatewayId, conditionStrategy);
    }

    @RequestMapping(path = "/clear-release/{group}/{gatewayId}", method = RequestMethod.POST)
    @ApiOperation(value = "局部网关订阅方式，清除蓝绿灰度发布", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearRelease(@PathVariable(value = "group") @ApiParam(value = "订阅的组名", required = true) String group, @PathVariable(value = "gatewayId") @ApiParam(value = "订阅的网关名", required = true) String gatewayId) {
        return doClearRelease(group, gatewayId);
    }

    @RequestMapping(path = "/validate-expression", method = RequestMethod.GET)
    @ApiOperation(value = "校验策略的条件表达式", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> validateExpression(@RequestParam @ApiParam(value = "条件表达式，格式示例：#H['a'] == '1' && #H['b'] != '2'。注意，引号是否为中文格式", defaultValue = "#H['a'] == '1' && #H['b'] != '2'", required = true) String expression, @RequestParam(defaultValue = "", required = false) @ApiParam(value = "校验参数，格式示例：a=1;b=1。如果多个用“;”分隔，不允许出现空格。允许为空", defaultValue = "a=1;b=1") String validation) {
        return doValidateExpression(expression, validation);
    }

    private ResponseEntity<?> doParseVersionRelease(ConditionStrategy conditionStrategy) {
        try {
            String result = strategyResource.parseVersionRelease(conditionStrategy);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doCreateVersionRelease(String group, ConditionStrategy conditionStrategy) {
        try {
            String result = strategyResource.createVersionRelease(group, conditionStrategy);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doClearRelease(String group) {
        try {
            String result = strategyResource.clearRelease(group);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doCreateVersionRelease(String group, String gatewayId, ConditionStrategy conditionStrategy) {
        try {
            String result = strategyResource.createVersionRelease(group, gatewayId, conditionStrategy);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doClearRelease(String group, String gatewayId) {
        try {
            String result = strategyResource.clearRelease(group, gatewayId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doValidateExpression(String expression, String validation) {
        try {
            boolean result = strategyResource.validateExpression(expression, validation);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}