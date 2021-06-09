package com.nepxion.discovery.plugin.strategy.sentinel.datasource.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.SentinelRuleType;
import com.nepxion.discovery.common.util.FileUtil;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.constant.SentinelStrategyDataSourceConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.parser.SentinelStrategyAuthorityRuleParser;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.parser.SentinelStrategyDegradeRuleParser;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.parser.SentinelStrategyFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.parser.SentinelStrategyParamFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.parser.SentinelStrategySystemRuleParser;

public class SentinelStrategyRuleLoader {
    private static final Logger LOG = LoggerFactory.getLogger(SentinelStrategyRuleLoader.class);

    @Value("${" + SentinelStrategyDataSourceConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_FLOW_PATH + ":" + DiscoveryConstant.PREFIX_CLASSPATH + DiscoveryConstant.SENTINEL_FLOW_KEY + "." + DiscoveryConstant.JSON_FORMAT + "}")
    private String sentinelStrategyFlowPath;

    @Value("${" + SentinelStrategyDataSourceConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_DEGRADE_PATH + ":" + DiscoveryConstant.PREFIX_CLASSPATH + DiscoveryConstant.SENTINEL_DEGRADE_KEY + "." + DiscoveryConstant.JSON_FORMAT + "}")
    private String sentinelStrategyDegradePath;

    @Value("${" + SentinelStrategyDataSourceConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_AUTHORITY_PATH + ":" + DiscoveryConstant.PREFIX_CLASSPATH + DiscoveryConstant.SENTINEL_AUTHORITY_KEY + "." + DiscoveryConstant.JSON_FORMAT + "}")
    private String sentinelStrategyAuthorityPath;

    @Value("${" + SentinelStrategyDataSourceConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_SYSTEM_PATH + ":" + DiscoveryConstant.PREFIX_CLASSPATH + DiscoveryConstant.SENTINEL_SYSTEM_KEY + "." + DiscoveryConstant.JSON_FORMAT + "}")
    private String sentinelStrategySystemPath;

    @Value("${" + SentinelStrategyDataSourceConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_PARAM_FLOW_PATH + ":" + DiscoveryConstant.PREFIX_CLASSPATH + DiscoveryConstant.SENTINEL_PARAM_FLOW_KEY + "." + DiscoveryConstant.JSON_FORMAT + "}")
    private String sentinelStrategyParamFlowPath;

    @Autowired
    private SentinelStrategyFlowRuleParser sentinelStrategyFlowRuleParser;

    @Autowired
    private SentinelStrategyDegradeRuleParser sentinelStrategyDegradeRuleParser;

    @Autowired
    private SentinelStrategyAuthorityRuleParser sentinelStrategyAuthorityRuleParser;

    @Autowired
    private SentinelStrategySystemRuleParser sentinelStrategySystemRuleParser;

    @Autowired
    private SentinelStrategyParamFlowRuleParser sentinelStrategyParamFlowRuleParser;

    @Autowired
    private ApplicationContext applicationContext;

    private boolean sentinelStrategyFlowRuleRetrieved = false;

    private boolean sentinelStrategyDegradeRuleRetrieved = false;

    private boolean sentinelStrategyAuthorityRuleRetrieved = false;

    private boolean sentinelStrategySystemRuleRetrieved = false;

    private boolean sentinelStrategyParamFlowRuleRetrieved = false;

    public void loadFileRules(SentinelRuleType sentinelRuleType) {
        String ruleTypeDescription = sentinelRuleType.getDescription();

        switch (sentinelRuleType) {
            case FLOW:
                if (!sentinelStrategyFlowRuleRetrieved) {
                    String sentinelStrategyRule = getRules(sentinelStrategyFlowPath);
                    if (StringUtils.isNotBlank(sentinelStrategyRule)) {
                        loadRules(sentinelRuleType, sentinelStrategyRule);
                    }
                } else {
                    LOG.info("{} is retrieved from remote config, ignore to load from file...", ruleTypeDescription);
                }
                break;
            case DEGRADE:
                if (!sentinelStrategyDegradeRuleRetrieved) {
                    String sentinelStrategyRule = getRules(sentinelStrategyDegradePath);
                    if (StringUtils.isNotBlank(sentinelStrategyRule)) {
                        loadRules(sentinelRuleType, sentinelStrategyRule);
                    }
                } else {
                    LOG.info("{} is retrieved from remote config, ignore to load from file...", ruleTypeDescription);
                }
                break;
            case AUTHORITY:
                if (!sentinelStrategyAuthorityRuleRetrieved) {
                    String sentinelStrategyRule = getRules(sentinelStrategyAuthorityPath);
                    if (StringUtils.isNotBlank(sentinelStrategyRule)) {
                        loadRules(sentinelRuleType, sentinelStrategyRule);
                    }
                } else {
                    LOG.info("{} is retrieved from remote config, ignore to load from file...", ruleTypeDescription);
                }
                break;
            case SYSTEM:
                if (!sentinelStrategySystemRuleRetrieved) {
                    String sentinelStrategyRule = getRules(sentinelStrategySystemPath);
                    if (StringUtils.isNotBlank(sentinelStrategyRule)) {
                        loadRules(sentinelRuleType, sentinelStrategyRule);
                    }
                } else {
                    LOG.info("{} is retrieved from remote config, ignore to load from file...", ruleTypeDescription);
                }
                break;
            case PARAM_FLOW:
                if (!sentinelStrategyParamFlowRuleRetrieved) {
                    String sentinelStrategyRule = getRules(sentinelStrategyParamFlowPath);
                    if (StringUtils.isNotBlank(sentinelStrategyRule)) {
                        loadRules(sentinelRuleType, sentinelStrategyRule);
                    }
                } else {
                    LOG.info("{} is retrieved from remote config, ignore to load from file...", ruleTypeDescription);
                }
                break;
        }
    }

    public void loadRules(SentinelRuleType sentinelRuleType, String sentinelStrategyRule) {
        if (StringUtils.isBlank(sentinelStrategyRule)) {
            sentinelStrategyRule = DiscoveryConstant.EMPTY_JSON_RULE_MULTIPLE;
        }

        String ruleTypeDescription = sentinelRuleType.getDescription();

        switch (sentinelRuleType) {
            case FLOW:
                FlowRuleManager.loadRules(sentinelStrategyFlowRuleParser.convert(sentinelStrategyRule));

                sentinelStrategyFlowRuleRetrieved = true;

                LOG.info("Loaded {} count={}", ruleTypeDescription, FlowRuleManager.getRules().size());
                break;
            case DEGRADE:
                DegradeRuleManager.loadRules(sentinelStrategyDegradeRuleParser.convert(sentinelStrategyRule));

                sentinelStrategyDegradeRuleRetrieved = true;

                LOG.info("Loaded {} count={}", ruleTypeDescription, DegradeRuleManager.getRules().size());
                break;
            case AUTHORITY:
                AuthorityRuleManager.loadRules(sentinelStrategyAuthorityRuleParser.convert(sentinelStrategyRule));

                sentinelStrategyAuthorityRuleRetrieved = true;

                LOG.info("Loaded {} count={}", ruleTypeDescription, AuthorityRuleManager.getRules().size());
                break;
            case SYSTEM:
                SystemRuleManager.loadRules(sentinelStrategySystemRuleParser.convert(sentinelStrategyRule));

                sentinelStrategySystemRuleRetrieved = true;

                LOG.info("Loaded {} count={}", ruleTypeDescription, SystemRuleManager.getRules().size());
                break;
            case PARAM_FLOW:
                ParamFlowRuleManager.loadRules(sentinelStrategyParamFlowRuleParser.convert(sentinelStrategyRule));

                sentinelStrategyParamFlowRuleRetrieved = true;

                LOG.info("Loaded {} count={}", ruleTypeDescription, ParamFlowRuleManager.getRules().size());
                break;
        }
    }

    public String getRules(String path) {
        return FileUtil.getText(applicationContext, path);
    }
}