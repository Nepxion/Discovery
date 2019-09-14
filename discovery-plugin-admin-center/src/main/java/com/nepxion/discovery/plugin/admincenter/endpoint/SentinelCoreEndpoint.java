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

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;

@RestController
@RequestMapping(path = "/sentinel-core")
@Api(tags = { "哨兵核心接口" })
@RestControllerEndpoint(id = "sentinel-core")
@ManagedResource(description = "Sentinel Core Endpoint")
public class SentinelCoreEndpoint {
    @RequestMapping(path = "/flow-rules", method = RequestMethod.GET)
    @ApiOperation(value = "获取流控规则列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<FlowRule> flowRules() {
        return FlowRuleManager.getRules();
    }

    @RequestMapping(path = "/degrade-rules", method = RequestMethod.GET)
    @ApiOperation(value = "获取降级规则列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<DegradeRule> degradeRules() {
        return DegradeRuleManager.getRules();
    }

    @RequestMapping(path = "/authority-rules", method = RequestMethod.GET)
    @ApiOperation(value = "获取授权规则列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<AuthorityRule> authorityRules() {
        return AuthorityRuleManager.getRules();
    }

    @RequestMapping(path = "/system-rules", method = RequestMethod.GET)
    @ApiOperation(value = "获取系统规则列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    @ManagedOperation
    public List<SystemRule> systemRules() {
        return SystemRuleManager.getRules();
    }
}