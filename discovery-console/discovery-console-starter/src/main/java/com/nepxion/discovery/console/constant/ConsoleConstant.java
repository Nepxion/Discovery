package com.nepxion.discovery.console.constant;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Arrays;
import java.util.List;

public class ConsoleConstant {
    public static final String[] DISCOVERY_TYPES = { "Eureka", "Consul", "Zookeeper", "Nacos" };
    public static final List<String> GATEWAY_TYPES = Arrays.asList(new String[] { "gateway", "zuul" });
    public static final List<String> SENTINEL_TYPES = Arrays.asList(new String[] { "flow", "degrade", "authority", "system", "param-flow" });
}