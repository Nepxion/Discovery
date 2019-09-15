package com.nepxion.discovery.plugin.strategy.service.sentinel.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.util.FileContextUtil;
import com.nepxion.discovery.plugin.strategy.service.sentinel.constant.SentinelStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelAuthorityRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelDegradeRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelParamFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelSystemRuleParser;

public abstract class AbstractSentinelRuleLoader implements SentinelRuleLoader {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractSentinelRuleLoader.class);

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

    public void loadLocal() {
        if (CollectionUtils.isEmpty(FlowRuleManager.getRules())) {
            FlowRuleManager.loadRules(new SentinelFlowRuleParser().convert(getRuleText(applicationContext, flowPath)));
        }

        if (CollectionUtils.isEmpty(DegradeRuleManager.getRules())) {
            DegradeRuleManager.loadRules(new SentinelDegradeRuleParser().convert(getRuleText(applicationContext, degradePath)));
        }

        if (CollectionUtils.isEmpty(AuthorityRuleManager.getRules())) {
            AuthorityRuleManager.loadRules(new SentinelAuthorityRuleParser().convert(getRuleText(applicationContext, authorityPath)));
        }

        if (CollectionUtils.isEmpty(SystemRuleManager.getRules())) {
            SystemRuleManager.loadRules(new SentinelSystemRuleParser().convert(getRuleText(applicationContext, systemPath)));
        }

        if (CollectionUtils.isEmpty(ParamFlowRuleManager.getRules())) {
            ParamFlowRuleManager.loadRules(new SentinelParamFlowRuleParser().convert(getRuleText(applicationContext, paramFlowPath)));
        }
    }

    public void loadRemote(ReadableDataSource<String, List<FlowRule>> flowRuleDataSource, ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource, ReadableDataSource<String, List<AuthorityRule>> authorityRuleDataSource, ReadableDataSource<String, List<SystemRule>> systemRuleDataSource, ReadableDataSource<String, List<ParamFlowRule>> paramFlowRuleDataSource) {
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());
        AuthorityRuleManager.register2Property(authorityRuleDataSource.getProperty());
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());
        ParamFlowRuleManager.register2Property(paramFlowRuleDataSource.getProperty());
    }

    public void loadLog() {
        LOG.info("{} flow rules loaded...", FlowRuleManager.getRules().size());
        LOG.info("{} degrade rules loaded...", DegradeRuleManager.getRules().size());
        LOG.info("{} authority rules loaded...", AuthorityRuleManager.getRules().size());
        LOG.info("{} system rules loaded...", SystemRuleManager.getRules().size());
        LOG.info("{} param flow rules loaded...", ParamFlowRuleManager.getRules().size());
    }

    public static String getRuleText(ApplicationContext applicationContext, String path) {
        String text = FileContextUtil.getText(applicationContext, path);
        if (StringUtils.isEmpty(text)) {
            text = SentinelStrategyConstant.SENTINEL_EMPTY_RULE;
        }

        return text;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getRuleList(ApplicationContext applicationContext, String path) {
        String text = getRuleText(applicationContext, path);

        return (T) JsonUtil.fromJson(text, new TypeReference<List<T>>() {
        });
    }
}