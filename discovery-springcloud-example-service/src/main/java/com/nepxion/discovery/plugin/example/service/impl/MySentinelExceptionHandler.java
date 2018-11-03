package com.nepxion.discovery.plugin.example.service.impl;

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

public class MySentinelExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MySentinelExceptionHandler.class);

    public static void handleException(BlockException e) {
        LOG.error("Sentinel exception causes", e);
    }
}