package com.nepxion.discovery.plugin.example.service.rest;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.nepxion.discovery.common.constant.DiscoveryConstant;

import java.util.List;

@RestController
@ConditionalOnProperty(name = DiscoveryConstant.SPRING_APPLICATION_NAME, havingValue = "discovery-springcloud-example-c")
public class CRestImpl extends AbstractRestImpl {
    private static final Logger LOG = LoggerFactory.getLogger(CRestImpl.class);

    @RequestMapping(path = "/rest", method = RequestMethod.POST)
    @SentinelResource("sentinel-resource-c")
    public String rest(@RequestBody String value) {
        value = doRest(value);

        LOG.info("调用路径：{}", value);

        return value;
    }

    @RequestMapping(path = "/test", method = RequestMethod.POST)
    public String test(@RequestBody String value) {
        return value;
    }

    @GetMapping("/rule")
    public List<FlowRule> showRules(){
        return FlowRuleManager.getRules();
    }

    @ExceptionHandler
    public String flowExceptionHandler(FlowException ex){
        return "限流规则触发，请稍后再试 from C Server";
    }
}