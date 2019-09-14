package com.nepxion.discovery.plugin.strategy.service.sentinel.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.service.sentinel.constant.SentinelStrategyConstant;

public abstract class AbstractSentinelRuleLoader implements SentinelRuleLoader {
    /**
     * 流控规则文件路径
     */
    @Value("${" + SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_FLOW_PATH + ":file:" + SentinelStrategyConstant.SENTINEL_FLOW_KEY + ".json}")
    protected String flowPath;

    /**
     * 降级规则文件路径
     */
    @Value("${" + SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_DEGRADE_PATH + ":file:" + SentinelStrategyConstant.SENTINEL_DEGRADE_KEY + ".json}")
    protected String degradePath;

    /**
     * 授权规则文件路径
     */
    @Value("${" + SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_AUTHORITY_PATH + ":file:" + SentinelStrategyConstant.SENTINEL_AUTHORITY_KEY + ".json}")
    protected String authorityPath;

    /**
     * 系统规则文件路径
     */
    @Value("${" + SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_SYSTEM_PATH + ":file:" + SentinelStrategyConstant.SENTINEL_SYSTEM_KEY + ".json}")
    protected String systemPath;

    /**
     * 热点参数流控规则文件路径
     */
    @Value("${" + SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_PARAM_FLOW_PATH + ":file:" + SentinelStrategyConstant.SENTINEL_PARAM_FLOW_KEY + ".json}")
    protected String paramFlowPath;

    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    protected PluginAdapter pluginAdapter;

    public String getFlowPath() {
        return flowPath;
    }

    public String getDegradePath() {
        return degradePath;
    }

    public String getAuthorityPath() {
        return authorityPath;
    }

    public String getSystemPath() {
        return systemPath;
    }

    public String getParamFlowPath() {
        return paramFlowPath;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }
}