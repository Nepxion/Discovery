package com.nepxion.discovery.plugin.example.service.sentinel;

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

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class MyRestTemplateBlockHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MyRestTemplateBlockHandler.class);

    public static String handleBlock(String value, BlockException e) {
        LOG.info("Value={}", value);
        LOG.info("Sentinel RestTemplate Client Block Causes");
        LOG.error("Sentinel RestTemplate Client Block Exception", e);
        LOG.info("Sentinel Rule Limit App={}", e.getRuleLimitApp());
        
        return "Sentinel RestTemplate Client Block Causes";
    }
}