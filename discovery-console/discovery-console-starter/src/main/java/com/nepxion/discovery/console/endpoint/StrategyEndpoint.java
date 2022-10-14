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

import com.nepxion.discovery.common.entity.ConditionRouteStrategy;
import com.nepxion.discovery.common.entity.ConditionStrategy;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.console.resource.StrategyResource;

@RestController
@RequestMapping(path = "/strategy")
@Api(tags = { "策略接口" })
public class StrategyEndpoint {
    @Autowired
    private StrategyResource strategyResource;

    @RequestMapping(path = "/get-version-release/{group}", method = RequestMethod.GET)
    @ApiOperation(value = "全局订阅方式，获取Json格式的蓝绿灰度发布", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> getVersionRelease(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group) {
        return doGetVersionRelease(group);
    }

    @RequestMapping(path = "/create-version-release-yaml/{group}", method = RequestMethod.POST)
    @ApiOperation(value = "全局订阅方式，根据Yaml格式，创建版本蓝绿灰度发布", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> createVersionRelease(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @RequestBody @ApiParam(value = "蓝绿灰度策略Yaml", required = true) String conditionStrategyYaml) {
        return doCreateVersionRelease(group, conditionStrategyYaml);
    }

    @RequestMapping(path = "/create-version-release-json/{group}", method = RequestMethod.POST)
    @ApiOperation(value = "全局订阅方式，根据Json格式，创建版本蓝绿灰度发布", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> createVersionRelease(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @RequestBody @ApiParam(value = "蓝绿灰度策略Json", required = true) ConditionStrategy conditionStrategy) {
        return doCreateVersionRelease(group, conditionStrategy);
    }

    @RequestMapping(path = "/recreate-version-release-yaml/{group}", method = RequestMethod.POST)
    @ApiOperation(value = "全局订阅方式，根据Yaml格式，重新创建版本蓝绿灰度发布（创建链路智能编排，不创建条件表达式）", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> recreateVersionRelease(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @RequestBody @ApiParam(value = "蓝绿灰度路由策略Yaml", required = true) String conditionRouteStrategyYaml) {
        return doRecreateVersionRelease(group, conditionRouteStrategyYaml);
    }

    @RequestMapping(path = "/recreate-version-release-json/{group}", method = RequestMethod.POST)
    @ApiOperation(value = "全局订阅方式，根据Json格式，重新创建版本蓝绿灰度发布（创建链路智能编排，不创建条件表达式）", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> recreateVersionRelease(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @RequestBody @ApiParam(value = "蓝绿灰度路由策略Json", required = true) ConditionRouteStrategy conditionRouteStrategy) {
        return doRecreateVersionRelease(group, conditionRouteStrategy);
    }

    @RequestMapping(path = "/reset-release/{group}", method = RequestMethod.POST)
    @ApiOperation(value = "全局订阅方式，重置蓝绿灰度发布（清除链路智能编排，不清除条件表达式）", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> resetRelease(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group) {
        return doResetRelease(group);
    }

    @RequestMapping(path = "/clear-release/{group}", method = RequestMethod.POST)
    @ApiOperation(value = "全局订阅方式，清除蓝绿灰度发布", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearRelease(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group) {
        return doClearRelease(group);
    }

    @RequestMapping(path = "/get-version-release/{group}/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "局部订阅方式，获取Json格式的蓝绿灰度发布", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> getVersionRelease(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doGetVersionRelease(group, serviceId);
    }

    @RequestMapping(path = "/create-version-release-yaml/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "局部订阅方式，根据Yaml格式，创建版本蓝绿灰度发布", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> createVersionRelease(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "蓝绿灰度策略Yaml", required = true) String conditionStrategyYaml) {
        return doCreateVersionRelease(group, serviceId, conditionStrategyYaml);
    }

    @RequestMapping(path = "/create-version-release-json/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "局部订阅方式，根据Json格式，创建版本蓝绿灰度发布", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> createVersionRelease(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "蓝绿灰度策略Json", required = true) ConditionStrategy conditionStrategy) {
        return doCreateVersionRelease(group, serviceId, conditionStrategy);
    }

    @RequestMapping(path = "/recreate-version-release-yaml/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "局部订阅方式，根据Yaml格式，重新创建版本蓝绿灰度发布（创建链路智能编排，不创建条件表达式）", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> recreateVersionRelease(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "蓝绿灰度路由策略Yaml", required = true) String conditionRouteStrategyYaml) {
        return doRecreateVersionRelease(group, serviceId, conditionRouteStrategyYaml);
    }

    @RequestMapping(path = "/recreate-version-release-json/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "局部订阅方式，根据Json格式，重新创建版本蓝绿灰度发布（创建链路智能编排，不创建条件表达式）", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> recreateVersionRelease(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "蓝绿灰度路由策略Json", required = true) ConditionRouteStrategy conditionRouteStrategy) {
        return doRecreateVersionRelease(group, serviceId, conditionRouteStrategy);
    }

    @RequestMapping(path = "/reset-release/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "局部订阅方式，重置蓝绿灰度发布（清除链路智能编排，不清除条件表达式）", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> resetRelease(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doResetRelease(group, serviceId);
    }

    @RequestMapping(path = "/clear-release/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "局部订阅方式，清除蓝绿灰度发布", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearRelease(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doClearRelease(group, serviceId);
    }

    @RequestMapping(path = "/parse-version-release-yaml", method = RequestMethod.POST)
    @ApiOperation(value = "根据Yaml格式，解析版本蓝绿灰度发布策略为Xml格式", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> parseVersionRelease(@RequestBody @ApiParam(value = "蓝绿灰度策略Yaml", required = true) String conditionStrategyYaml) {
        return doParseVersionRelease(conditionStrategyYaml);
    }

    @RequestMapping(path = "/parse-version-release-json", method = RequestMethod.POST)
    @ApiOperation(value = "根据Json格式，解析版本蓝绿灰度发布策略为Xml格式", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> parseVersionRelease(@RequestBody @ApiParam(value = "蓝绿灰度策略Json", required = true) ConditionStrategy conditionStrategy) {
        return doParseVersionRelease(conditionStrategy);
    }

    @RequestMapping(path = "/deparse-version-release-xml", method = RequestMethod.POST)
    @ApiOperation(value = "根据Xml格式，反解析版本蓝绿灰度发布策略为Json格式", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> deparseVersionRelease(@RequestBody @ApiParam(value = "蓝绿灰度策略Xml", required = true) String ruleXml) {
        return doDeparseVersionReleaseXml(ruleXml);
    }

    @RequestMapping(path = "/deparse-version-release-yaml", method = RequestMethod.POST)
    @ApiOperation(value = "根据Yaml格式，反解析版本蓝绿灰度发布策略为Json格式", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> convertVersionRelease(@RequestBody @ApiParam(value = "蓝绿灰度策略Yaml", required = true) String conditionStrategyYaml) {
        return doDeparseVersionReleaseYaml(conditionStrategyYaml);
    }

    @RequestMapping(path = "/validate-expression", method = RequestMethod.GET)
    @ApiOperation(value = "校验策略的条件表达式", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> validateExpression(@RequestParam @ApiParam(value = "条件表达式，格式示例：#H['a'] == '1' && #H['b'] != '2'。注意，引号是否为中文格式", defaultValue = "#H['a'] == '1' && #H['b'] != '2'", required = true) String expression, @RequestParam(defaultValue = "", required = false) @ApiParam(value = "校验参数，格式示例：a=1;b=1。如果多个用“;”分隔，不允许出现空格。允许为空", defaultValue = "a=1;b=1") String validation) {
        return doValidateExpression(expression, validation);
    }

    private ResponseEntity<?> doGetVersionRelease(String group) {
        try {
            ConditionStrategy result = strategyResource.getVersionRelease(group);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doCreateVersionRelease(String group, String conditionStrategyYaml) {
        try {
            String result = strategyResource.createVersionRelease(group, conditionStrategyYaml);

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

    private ResponseEntity<?> doRecreateVersionRelease(String group, String conditionRouteStrategyYaml) {
        try {
            String result = strategyResource.recreateVersionRelease(group, conditionRouteStrategyYaml);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRecreateVersionRelease(String group, ConditionRouteStrategy conditionRouteStrategy) {
        try {
            String result = strategyResource.recreateVersionRelease(group, conditionRouteStrategy);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doResetRelease(String group) {
        try {
            String result = strategyResource.resetRelease(group);

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

    private ResponseEntity<?> doGetVersionRelease(String group, String serviceId) {
        try {
            ConditionStrategy result = strategyResource.getVersionRelease(group, serviceId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doCreateVersionRelease(String group, String serviceId, String conditionStrategyYaml) {
        try {
            String result = strategyResource.createVersionRelease(group, serviceId, conditionStrategyYaml);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doCreateVersionRelease(String group, String serviceId, ConditionStrategy conditionStrategy) {
        try {
            String result = strategyResource.createVersionRelease(group, serviceId, conditionStrategy);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRecreateVersionRelease(String group, String serviceId, String conditionRouteStrategyYaml) {
        try {
            String result = strategyResource.recreateVersionRelease(group, serviceId, conditionRouteStrategyYaml);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRecreateVersionRelease(String group, String serviceId, ConditionRouteStrategy conditionRouteStrategy) {
        try {
            String result = strategyResource.recreateVersionRelease(group, serviceId, conditionRouteStrategy);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doResetRelease(String group, String serviceId) {
        try {
            String result = strategyResource.resetRelease(group, serviceId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doClearRelease(String group, String serviceId) {
        try {
            String result = strategyResource.clearRelease(group, serviceId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doParseVersionRelease(String conditionStrategyYaml) {
        try {
            String result = strategyResource.parseVersionRelease(conditionStrategyYaml);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doParseVersionRelease(ConditionStrategy conditionStrategy) {
        try {
            String result = strategyResource.parseVersionRelease(conditionStrategy);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doDeparseVersionReleaseXml(String ruleXml) {
        try {
            ConditionStrategy conditionStrategy = strategyResource.deparseVersionReleaseXml(ruleXml);

            return ResponseUtil.getSuccessResponse(conditionStrategy);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doDeparseVersionReleaseYaml(String conditionStrategyYaml) {
        try {
            ConditionStrategy result = strategyResource.deparseVersionReleaseYaml(conditionStrategyYaml);

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