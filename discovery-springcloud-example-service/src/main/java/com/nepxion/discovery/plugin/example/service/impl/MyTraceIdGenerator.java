package com.nepxion.discovery.plugin.example.service.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.UUID;

import com.nepxion.discovery.plugin.strategy.service.trace.TraceIdGenerator;

public class MyTraceIdGenerator implements TraceIdGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}