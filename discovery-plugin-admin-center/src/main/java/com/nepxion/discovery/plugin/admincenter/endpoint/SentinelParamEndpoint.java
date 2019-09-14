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

import java.util.List;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;

@RestController
@RequestMapping(path = "/sentinel-param")
@Api(tags = { "哨兵参数接口" })
@RestControllerEndpoint(id = "sentinel-param")
@ManagedResource(description = "Sentinel Param Endpoint")
public class SentinelParamEndpoint {
    @RequestMapping(path = "/param-flow-rules", method = RequestMethod.GET)
    @ApiOperation(value = "获取热点参数流控规则列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<ParamFlowRule> paramFlowRules() {
        return ParamFlowRuleManager.getRules();
    }
}