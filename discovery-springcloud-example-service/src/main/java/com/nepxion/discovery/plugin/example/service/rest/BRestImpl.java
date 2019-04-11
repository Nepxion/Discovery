package com.nepxion.discovery.plugin.example.service.rest;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.nepxion.discovery.common.constant.DiscoveryConstant;

@RestController
@ConditionalOnProperty(name = DiscoveryConstant.SPRING_APPLICATION_NAME, havingValue = "discovery-springcloud-example-b")
public class BRestImpl extends AbstractRestImpl {
    private static final Logger LOG = LoggerFactory.getLogger(BRestImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/rest", method = RequestMethod.POST)
    @SentinelResource(value = "sentinel-resource", blockHandler = "handleBlock", fallback = "handleFallback")
    public String rest(@RequestBody String value) {
        value = doRest(value);
        value = restTemplate.postForEntity("http://discovery-springcloud-example-c/rest", value, String.class).getBody();

        LOG.info("调用路径：{}", value);

        return value;
    }

    @RequestMapping(path = "/test", method = RequestMethod.POST)
    public String test(@RequestBody String value) {
        return value;
    }

    public String handleBlock(String value, BlockException e) {
        LOG.info("Value={}", value);
        LOG.info("Sentinel BServer Block Causes");
        LOG.error("Sentinel BServer Block Exception", e);
        LOG.info("Sentinel Rule Limit App={}", e.getRuleLimitApp());

        return "Sentinel BServer Block Causes";
    }

    public String handleFallback(String value) {
        LOG.info("Value={}", value);
        LOG.info("Sentinel BServer Fallback Causes");

        return "Sentinel BServer Fallback Causes";
    }
}