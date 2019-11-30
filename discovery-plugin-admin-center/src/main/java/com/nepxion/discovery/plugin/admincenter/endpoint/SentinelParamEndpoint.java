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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;

@RestController
@RequestMapping(path = "/sentinel-param")
@Api(tags = { "哨兵参数接口" })
public class SentinelParamEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(SentinelParamEndpoint.class);

    private static Converter<String, List<ParamFlowRule>> sentinelParamFlowRuleParser = new Converter<String, List<ParamFlowRule>>() {
        @Override
        public List<ParamFlowRule> convert(String source) {
            return JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
            });
        }
    };

    @Autowired
    private PluginContextAware pluginContextAware;

    @RequestMapping(path = "/update-param-flow-rules", method = RequestMethod.POST)
    @ApiOperation(value = "更新热点参数流控规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateParamFlowRules(@RequestBody @ApiParam(value = "热点参数流控规则内容，JSON格式", required = true) String rule) {
        Boolean isConfigRestControlEnabled = pluginContextAware.isConfigRestControlEnabled();
        if (!isConfigRestControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Config rest control is disabled");
        }

        ParamFlowRuleManager.loadRules(sentinelParamFlowRuleParser.convert(rule));

        LOG.info("{} param flow rules loaded...", ParamFlowRuleManager.getRules().size());

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    @RequestMapping(path = "/clear-param-flow-rules", method = RequestMethod.POST)
    @ApiOperation(value = "清除热点参数流控规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearParamFlowRules() {
        Boolean isConfigRestControlEnabled = pluginContextAware.isConfigRestControlEnabled();
        if (!isConfigRestControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Config rest control is disabled");
        }

        LOG.info("{} param flow rules cleared...", ParamFlowRuleManager.getRules().size());

        ParamFlowRuleManager.loadRules(new ArrayList<ParamFlowRule>());

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    @RequestMapping(path = "/view-param-flow-rules", method = RequestMethod.GET)
    @ApiOperation(value = "获取热点参数流控规则列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<List<ParamFlowRule>> viewParamFlowRules() {
        return ResponseEntity.ok().body(ParamFlowRuleManager.getRules());
    }
}