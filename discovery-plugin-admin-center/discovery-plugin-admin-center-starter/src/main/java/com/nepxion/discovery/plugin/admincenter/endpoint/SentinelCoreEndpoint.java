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

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.plugin.admincenter.resource.SentinelCoreResource;

@RestController
@RequestMapping(path = "/sentinel-core")
@Api(tags = { "哨兵核心接口" })
public class SentinelCoreEndpoint {
    @Autowired
    private SentinelCoreResource sentinelCoreResource;

    @RequestMapping(path = "/update-flow-rules", method = RequestMethod.POST)
    @ApiOperation(value = "更新流控规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateFlowRules(@RequestBody @ApiParam(value = "流控规则内容，JSON格式", required = true) String rule) {
        return doUpdateFlowRules(rule);
    }

    @RequestMapping(path = "/clear-flow-rules", method = RequestMethod.POST)
    @ApiOperation(value = "清除流控规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearFlowRules() {
        return doClearFlowRules();
    }

    @RequestMapping(path = "/view-flow-rules", method = RequestMethod.GET)
    @ApiOperation(value = "查看流控规则列表", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> viewFlowRules() {
        return doViewFlowRules();
    }

    @RequestMapping(path = "/update-degrade-rules", method = RequestMethod.POST)
    @ApiOperation(value = "更新降级规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateDegradeRules(@RequestBody @ApiParam(value = "降级规则内容，JSON格式", required = true) String rule) {
        return doUpdateDegradeRules(rule);
    }

    @RequestMapping(path = "/clear-degrade-rules", method = RequestMethod.POST)
    @ApiOperation(value = "清除降级规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearDegradeRules() {
        return doClearDegradeRules();
    }

    @RequestMapping(path = "/view-degrade-rules", method = RequestMethod.GET)
    @ApiOperation(value = "查看降级规则列表", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> viewDegradeRules() {
        return doViewDegradeRules();
    }

    @RequestMapping(path = "/update-authority-rules", method = RequestMethod.POST)
    @ApiOperation(value = "更新授权规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateAuthorityRules(@RequestBody @ApiParam(value = "授权规则内容，JSON格式", required = true) String rule) {
        return doUpdateAuthorityRules(rule);
    }

    @RequestMapping(path = "/clear-authority-rules", method = RequestMethod.POST)
    @ApiOperation(value = "清除授权规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearAuthorityRules() {
        return doClearAuthorityRules();
    }

    @RequestMapping(path = "/view-authority-rules", method = RequestMethod.GET)
    @ApiOperation(value = "查看授权规则列表", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> viewAuthorityRules() {
        return doViewAuthorityRules();
    }

    @RequestMapping(path = "/update-system-rules", method = RequestMethod.POST)
    @ApiOperation(value = "更新系统规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateSystemRules(@RequestBody @ApiParam(value = "系统规则内容，JSON格式", required = true) String rule) {
        return doUpdateSystemRules(rule);
    }

    @RequestMapping(path = "/clear-system-rules", method = RequestMethod.POST)
    @ApiOperation(value = "清除系统规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearSystemRules() {
        return doClearSystemRules();
    }

    @RequestMapping(path = "/view-system-rules", method = RequestMethod.GET)
    @ApiOperation(value = "查看系统规则列表", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> viewSystemRules() {
        return doViewSystemRules();
    }

    private ResponseEntity<?> doUpdateFlowRules(String rule) {
        try {
            sentinelCoreResource.updateFlowRules(rule);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doClearFlowRules() {
        try {
            sentinelCoreResource.clearFlowRules();

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doViewFlowRules() {
        try {
            List<FlowRule> flowRules = sentinelCoreResource.viewFlowRules();

            return ResponseUtil.getSuccessResponse(flowRules);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doUpdateDegradeRules(String rule) {
        try {
            sentinelCoreResource.updateDegradeRules(rule);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doClearDegradeRules() {
        try {
            sentinelCoreResource.clearDegradeRules();

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doViewDegradeRules() {
        try {
            List<DegradeRule> degradeRules = sentinelCoreResource.viewDegradeRules();

            return ResponseUtil.getSuccessResponse(degradeRules);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doUpdateAuthorityRules(String rule) {
        try {
            sentinelCoreResource.updateAuthorityRules(rule);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doClearAuthorityRules() {
        try {
            sentinelCoreResource.clearAuthorityRules();

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doViewAuthorityRules() {
        try {
            List<AuthorityRule> authorityRules = sentinelCoreResource.viewAuthorityRules();

            return ResponseUtil.getSuccessResponse(authorityRules);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doUpdateSystemRules(String rule) {
        try {
            sentinelCoreResource.updateSystemRules(rule);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doClearSystemRules() {
        try {
            sentinelCoreResource.clearSystemRules();

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doViewSystemRules() {
        try {
            List<SystemRule> systemRules = sentinelCoreResource.viewSystemRules();

            return ResponseUtil.getSuccessResponse(systemRules);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}