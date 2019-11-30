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
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;

@RestController
@RequestMapping(path = "/sentinel-core")
@Api(tags = { "哨兵核心接口" })
public class SentinelCoreEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(SentinelCoreEndpoint.class);

    private static Converter<String, List<FlowRule>> sentinelFlowRuleParser = new Converter<String, List<FlowRule>>() {
        @Override
        public List<FlowRule> convert(String source) {
            return JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
            });
        }
    };

    private static Converter<String, List<DegradeRule>> sentinelDegradeRuleParser = new Converter<String, List<DegradeRule>>() {
        @Override
        public List<DegradeRule> convert(String source) {
            return JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
            });
        }
    };

    private static Converter<String, List<AuthorityRule>> sentinelAuthorityRuleParser = new Converter<String, List<AuthorityRule>>() {
        @Override
        public List<AuthorityRule> convert(String source) {
            return JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
            });
        }
    };

    private static Converter<String, List<SystemRule>> sentinelSystemRuleParser = new Converter<String, List<SystemRule>>() {
        @Override
        public List<SystemRule> convert(String source) {
            return JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
            });
        }
    };

    @Autowired
    private PluginContextAware pluginContextAware;

    @RequestMapping(path = "/update-flow-rules", method = RequestMethod.POST)
    @ApiOperation(value = "更新流控规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateFlowRules(@RequestBody @ApiParam(value = "流控规则内容，JSON格式", required = true) String rule) {
        Boolean isConfigRestControlEnabled = pluginContextAware.isConfigRestControlEnabled();
        if (!isConfigRestControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Config rest control is disabled");
        }

        FlowRuleManager.loadRules(sentinelFlowRuleParser.convert(rule));

        LOG.info("{} flow rules loaded...", FlowRuleManager.getRules().size());

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    @RequestMapping(path = "/clear-flow-rules", method = RequestMethod.POST)
    @ApiOperation(value = "清除流控规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearFlowRules() {
        Boolean isConfigRestControlEnabled = pluginContextAware.isConfigRestControlEnabled();
        if (!isConfigRestControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Config rest control is disabled");
        }

        LOG.info("{} flow rules cleared...", FlowRuleManager.getRules().size());

        FlowRuleManager.loadRules(new ArrayList<FlowRule>());

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    @RequestMapping(path = "/view-flow-rules", method = RequestMethod.GET)
    @ApiOperation(value = "获取流控规则列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<List<FlowRule>> viewFlowRules() {
        return ResponseEntity.ok().body(FlowRuleManager.getRules());
    }

    @RequestMapping(path = "/update-degrade-rules", method = RequestMethod.POST)
    @ApiOperation(value = "更新降级规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateDegradeRules(@RequestBody @ApiParam(value = "降级规则内容，JSON格式", required = true) String rule) {
        Boolean isConfigRestControlEnabled = pluginContextAware.isConfigRestControlEnabled();
        if (!isConfigRestControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Config rest control is disabled");
        }

        DegradeRuleManager.loadRules(sentinelDegradeRuleParser.convert(rule));

        LOG.info("{} degrade rules loaded...", DegradeRuleManager.getRules().size());

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    @RequestMapping(path = "/clear-degrade-rules", method = RequestMethod.POST)
    @ApiOperation(value = "清除降级规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearDegradeRules() {
        Boolean isConfigRestControlEnabled = pluginContextAware.isConfigRestControlEnabled();
        if (!isConfigRestControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Config rest control is disabled");
        }

        LOG.info("{} degrade rules cleared...", DegradeRuleManager.getRules().size());

        DegradeRuleManager.loadRules(new ArrayList<DegradeRule>());

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    @RequestMapping(path = "/view-degrade-rules", method = RequestMethod.GET)
    @ApiOperation(value = "获取降级规则列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<List<DegradeRule>> viewDegradeRules() {
        return ResponseEntity.ok().body(DegradeRuleManager.getRules());
    }

    @RequestMapping(path = "/update-authority-rules", method = RequestMethod.POST)
    @ApiOperation(value = "更新授权规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateAuthorityRules(@RequestBody @ApiParam(value = "授权规则内容，JSON格式", required = true) String rule) {
        Boolean isConfigRestControlEnabled = pluginContextAware.isConfigRestControlEnabled();
        if (!isConfigRestControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Config rest control is disabled");
        }

        AuthorityRuleManager.loadRules(sentinelAuthorityRuleParser.convert(rule));

        LOG.info("{} authority rules loaded...", AuthorityRuleManager.getRules().size());

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    @RequestMapping(path = "/clear-authority-rules", method = RequestMethod.POST)
    @ApiOperation(value = "清除授权规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearAuthorityRules() {
        Boolean isConfigRestControlEnabled = pluginContextAware.isConfigRestControlEnabled();
        if (!isConfigRestControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Config rest control is disabled");
        }

        LOG.info("{} authority rules cleared...", AuthorityRuleManager.getRules().size());

        AuthorityRuleManager.loadRules(new ArrayList<AuthorityRule>());

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    @RequestMapping(path = "/view-authority-rules", method = RequestMethod.GET)
    @ApiOperation(value = "获取授权规则列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<List<AuthorityRule>> viewAuthorityRules() {
        return ResponseEntity.ok().body(AuthorityRuleManager.getRules());
    }

    @RequestMapping(path = "/update-system-rules", method = RequestMethod.POST)
    @ApiOperation(value = "更新系统规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> updateSystemRules(@RequestBody @ApiParam(value = "系统规则内容，JSON格式", required = true) String rule) {
        Boolean isConfigRestControlEnabled = pluginContextAware.isConfigRestControlEnabled();
        if (!isConfigRestControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Config rest control is disabled");
        }

        SystemRuleManager.loadRules(sentinelSystemRuleParser.convert(rule));

        LOG.info("{} system rules loaded...", SystemRuleManager.getRules().size());

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    @RequestMapping(path = "/clear-system-rules", method = RequestMethod.POST)
    @ApiOperation(value = "清除系统规则列表", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> clearSystemRules() {
        Boolean isConfigRestControlEnabled = pluginContextAware.isConfigRestControlEnabled();
        if (!isConfigRestControlEnabled) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Config rest control is disabled");
        }

        LOG.info("{} system rules cleared...", SystemRuleManager.getRules().size());

        SystemRuleManager.loadRules(new ArrayList<SystemRule>());

        return ResponseEntity.ok().body(DiscoveryConstant.OK);
    }

    @RequestMapping(path = "/view-system-rules", method = RequestMethod.GET)
    @ApiOperation(value = "获取系统规则列表", notes = "", response = List.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<List<SystemRule>> viewSystemRules() {
        return ResponseEntity.ok().body(SystemRuleManager.getRules());
    }
}