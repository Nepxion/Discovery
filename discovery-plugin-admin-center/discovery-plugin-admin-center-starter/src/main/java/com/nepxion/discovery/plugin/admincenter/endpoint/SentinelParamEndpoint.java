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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.plugin.admincenter.resource.SentinelParamResource;

@RestController
@RequestMapping(path = "/sentinel-param")
@Api(tags = { "哨兵参数接口" })
public class SentinelParamEndpoint {
    @Autowired
    private SentinelParamResource sentinelParamResource;

    @RequestMapping(path = "/update-param-flow-rules", method = RequestMethod.POST)
    @ApiOperation(value = "更新热点参数流控规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateParamFlowRules(@RequestBody @ApiParam(value = "热点参数流控规则内容，Json格式", required = true) String rule) {
        return doUpdateParamFlowRules(rule);
    }

    @RequestMapping(path = "/clear-param-flow-rules", method = RequestMethod.POST)
    @ApiOperation(value = "清除热点参数流控规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearParamFlowRules() {
        return doClearParamFlowRules();
    }

    @RequestMapping(path = "/view-param-flow-rules", method = RequestMethod.GET)
    @ApiOperation(value = "查看热点参数流控规则列表", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> viewParamFlowRules() {
        return doViewParamFlowRules();
    }

    private ResponseEntity<?> doUpdateParamFlowRules(String rule) {
        try {
            sentinelParamResource.updateParamFlowRules(rule);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doClearParamFlowRules() {
        try {
            sentinelParamResource.clearParamFlowRules();

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doViewParamFlowRules() {
        try {
            List<ParamFlowRule> paramFlowRules = sentinelParamResource.viewParamFlowRules();

            return ResponseUtil.getSuccessResponse(paramFlowRules);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}