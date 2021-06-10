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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.console.resource.RuleResource;

@RestController
@RequestMapping(path = "/rule")
@Api(tags = { "规则接口" })
public class RuleEndpoint {
    @Autowired
    private RuleResource ruleResource;

    @RequestMapping(path = "/parse", method = RequestMethod.POST)
    @ApiOperation(value = "解析规则配置内容成对象", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> ruleParse(@RequestBody @ApiParam(value = "规则配置内容", required = true) String config) {
        return doRuleParse(config);
    }

    @RequestMapping(path = "/deparse", method = RequestMethod.POST)
    @ApiOperation(value = "反解析规则配置对象成内容", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> ruleDeparse(@RequestBody @ApiParam(value = "规则配置对象，RuleEntity格式", required = true) RuleEntity ruleEntity) {
        return doRuleDeparse(ruleEntity);
    }

    private ResponseEntity<?> doRuleParse(String config) {
        try {
            RuleEntity ruleEntity = ruleResource.toRuleEntity(config);

            return ResponseUtil.getSuccessResponse(ruleEntity);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRuleDeparse(RuleEntity ruleEntity) {
        try {
            String config = ruleResource.fromRuleEntity(ruleEntity);

            return ResponseUtil.getSuccessResponse(config);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}