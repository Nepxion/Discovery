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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.entity.SentinelRuleType;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.console.resource.SentinelResource;

@RestController
@RequestMapping(path = "/sentinel")
@Api(tags = { "哨兵接口" })
public class SentinelEndpoint {
    @Autowired
    private SentinelResource sentinelResource;

    @RequestMapping(path = "/remote/update/{ruleType}/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "更新哨兵规则到远程配置中心", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> remoteSentinelUpdate(@PathVariable(value = "ruleType") @ApiParam(value = "哨兵规则类型。取值： flow | degrade | authority | system | param-flow", defaultValue = "flow", required = true) String ruleType, @PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "哨兵规则内容，Json格式", required = true) String rule) {
        return doRemoteSentinelUpdate(ruleType, group, serviceId, rule);
    }

    @RequestMapping(path = "/remote/clear/{ruleType}/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "清除哨兵规则到远程配置中心", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> remoteSentinelClear(@PathVariable(value = "ruleType") @ApiParam(value = "哨兵规则类型。取值： flow | degrade | authority | system | param-flow", defaultValue = "flow", required = true) String ruleType, @PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doRemoteSentinelClear(ruleType, group, serviceId);
    }

    @RequestMapping(path = "/remote/view/{ruleType}/{group}/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "查看远程配置中心的哨兵规则", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> remoteSentinelView(@PathVariable(value = "ruleType") @ApiParam(value = "哨兵规则类型。取值： flow | degrade | authority | system | param-flow", defaultValue = "flow", required = true) String ruleType, @PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doRemoteSentinelView(ruleType, group, serviceId);
    }

    @RequestMapping(path = "/update/{ruleType}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量更新哨兵规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> sentinelUpdate(@PathVariable(value = "ruleType") @ApiParam(value = "哨兵规则类型。取值： flow | degrade | authority | system | param-flow", defaultValue = "flow", required = true) String ruleType, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "哨兵规则内容，Json格式", required = true) String rule) {
        return doSentinelUpdate(ruleType, serviceId, rule);
    }

    @RequestMapping(path = "/clear/{ruleType}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量清除哨兵规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> sentinelClear(@PathVariable(value = "ruleType") @ApiParam(value = "哨兵规则类型。取值： flow | degrade | authority | system | param-flow", defaultValue = "flow", required = true) String ruleType, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doSentinelClear(ruleType, serviceId);
    }

    @RequestMapping(path = "/view/{ruleType}/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "批量查看哨兵规则列表", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> sentinelView(@PathVariable(value = "ruleType") @ApiParam(value = "哨兵规则类型。取值： flow | degrade | authority | system | param-flow", defaultValue = "flow", required = true) String ruleType, @PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doSentinelView(ruleType, serviceId);
    }

    private ResponseEntity<?> doRemoteSentinelUpdate(String ruleType, String group, String serviceId, String rule) {
        try {
            boolean result = sentinelResource.updateRemoteSentinel(SentinelRuleType.fromString(ruleType), group, serviceId, rule);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRemoteSentinelClear(String ruleType, String group, String serviceId) {
        try {
            boolean result = sentinelResource.clearRemoteSentinel(SentinelRuleType.fromString(ruleType), group, serviceId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRemoteSentinelView(String ruleType, String group, String serviceId) {
        try {
            String result = sentinelResource.getRemoteSentinel(SentinelRuleType.fromString(ruleType), group, serviceId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doSentinelUpdate(String ruleType, String serviceId, String rule) {
        try {
            List<ResultEntity> resultEntityList = sentinelResource.updateSentinel(SentinelRuleType.fromString(ruleType), serviceId, rule);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doSentinelClear(String ruleType, String serviceId) {
        try {
            List<ResultEntity> resultEntityList = sentinelResource.clearSentinel(SentinelRuleType.fromString(ruleType), serviceId);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doSentinelView(String ruleType, String serviceId) {
        try {
            List<ResultEntity> resultEntityList = sentinelResource.viewSentinel(SentinelRuleType.fromString(ruleType), serviceId);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}