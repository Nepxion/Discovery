package com.nepxion.discovery.plugin.strategy.sentinel.constant;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Weihua Wang
 * @author Haojun Ren
 * @version 1.0
 */

public class SentinelStrategyConstant {
    public static final String SENTINEL_TYPE = "Sentinel";

    public static final String SPRING_APPLICATION_STRATEGY_SENTINEL_ENABLED = "spring.application.strategy.sentinel.enabled";
    public static final String SPRING_APPLICATION_STRATEGY_SENTINEL_FLOW_PATH = "spring.application.strategy.sentinel.flow.path";
    public static final String SPRING_APPLICATION_STRATEGY_SENTINEL_DEGRADE_PATH = "spring.application.strategy.sentinel.degrade.path";
    public static final String SPRING_APPLICATION_STRATEGY_SENTINEL_AUTHORITY_PATH = "spring.application.strategy.sentinel.authority.path";
    public static final String SPRING_APPLICATION_STRATEGY_SENTINEL_SYSTEM_PATH = "spring.application.strategy.sentinel.system.path";
    public static final String SPRING_APPLICATION_STRATEGY_SENTINEL_PARAM_FLOW_PATH = "spring.application.strategy.sentinel.param.flow.path";

    public static final String SENTINEL_FLOW_KEY = "sentinel-flow";
    public static final String SENTINEL_DEGRADE_KEY = "sentinel-degrade";
    public static final String SENTINEL_AUTHORITY_KEY = "sentinel-authority";
    public static final String SENTINEL_SYSTEM_KEY = "sentinel-system";
    public static final String SENTINEL_PARAM_FLOW_KEY = "sentinel-param-flow";
    public static final String SENTINEL_EMPTY_RULE = "[]";

    public static final String SPRING_APPLICATION_STRATEGY_TRACER_SENTINEL_RULE_OUTPUT_ENABLED = "spring.application.strategy.tracer.sentinel.rule.output.enabled";
    public static final String SPRING_APPLICATION_STRATEGY_TRACER_SENTINEL_ARGS_OUTPUT_ENABLED = "spring.application.strategy.tracer.sentinel.args.output.enabled";

    public static final String SPAN_NAME = "SENTINEL";
    public static final String ORIGIN = "origin";
    public static final String ASYNC = "async";
    public static final String RESOURCE_NAME = "resource.name";
    public static final String RESOURCE_SHOW_NAME = "resource.showname";
    public static final String RESOURCE_TYPE = "resource.type";
    public static final String ENTRY_TYPE = "entry.type";
    public static final String RULE_LIMIT_APP = "rule.limit.app";
    public static final String RULE = "rule";
    public static final String CAUSE = "cause";
    public static final String BLOCK_EXCEPTION = "block.exception";
    public static final String COUNT = "count";
    public static final String ARGS = "args";
}