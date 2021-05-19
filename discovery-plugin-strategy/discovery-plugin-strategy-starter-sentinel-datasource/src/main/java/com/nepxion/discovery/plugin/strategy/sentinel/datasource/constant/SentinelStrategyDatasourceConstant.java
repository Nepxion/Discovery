package com.nepxion.discovery.plugin.strategy.sentinel.datasource.constant;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Weihua Wang
 * @author Haojun Ren
 * @version 1.0
 */

public class SentinelStrategyDatasourceConstant {
    public static final String SENTINEL_TYPE = "Sentinel";

    public static final String SPRING_APPLICATION_STRATEGY_SENTINEL_DATASOURCE_ENABLED = "spring.application.strategy.sentinel.datasource.enabled";
    public static final String SPRING_APPLICATION_STRATEGY_SENTINEL_FLOW_PATH = "spring.application.strategy.sentinel.flow.path";
    public static final String SPRING_APPLICATION_STRATEGY_SENTINEL_DEGRADE_PATH = "spring.application.strategy.sentinel.degrade.path";
    public static final String SPRING_APPLICATION_STRATEGY_SENTINEL_AUTHORITY_PATH = "spring.application.strategy.sentinel.authority.path";
    public static final String SPRING_APPLICATION_STRATEGY_SENTINEL_SYSTEM_PATH = "spring.application.strategy.sentinel.system.path";
    public static final String SPRING_APPLICATION_STRATEGY_SENTINEL_PARAM_FLOW_PATH = "spring.application.strategy.sentinel.param.flow.path";

    public static final String SENTINEL_STRATEGY_FLOW_KEY = "sentinel-flow";
    public static final String SENTINEL_STRATEGY_DEGRADE_KEY = "sentinel-degrade";
    public static final String SENTINEL_STRATEGY_AUTHORITY_KEY = "sentinel-authority";
    public static final String SENTINEL_STRATEGY_SYSTEM_KEY = "sentinel-system";
    public static final String SENTINEL_STRATEGY_PARAM_FLOW_KEY = "sentinel-param-flow";

    public static final String SENTINEL_STRATEGY_FLOW_DESCRIPTION = "Sentinel flow rules";
    public static final String SENTINEL_STRATEGY_DEGRADE_DESCRIPTION = "Sentinel degrade rules";
    public static final String SENTINEL_STRATEGY_AUTHORITY_DESCRIPTION = "Sentinel authority rules";
    public static final String SENTINEL_STRATEGY_SYSTEM_DESCRIPTION = "Sentinel system rules";
    public static final String SENTINEL_STRATEGY_PARAM_FLOW_DESCRIPTION = "Sentinel param flow rules";
}