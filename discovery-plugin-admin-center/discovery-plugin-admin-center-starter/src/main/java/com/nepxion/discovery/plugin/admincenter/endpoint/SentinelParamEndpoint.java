package com.nepxion.discovery.plugin.admincenter.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

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
public class SentinelParamEndpoint {
    @Autowired
    private SentinelParamResource sentinelParamResource;

    @RequestMapping(path = "/update-param-flow-rules", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateParamFlowRules(@RequestBody String rule) {
        return doUpdateParamFlowRules(rule);
    }

    @RequestMapping(path = "/clear-param-flow-rules", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> clearParamFlowRules() {
        return doClearParamFlowRules();
    }

    @RequestMapping(path = "/view-param-flow-rules", method = RequestMethod.GET)
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