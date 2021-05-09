package com.nepxion.discovery.plugin.strategy.sentinel.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.collections4.CollectionUtils;
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
import com.nepxion.discovery.plugin.framework.util.FileContextUtil;
import com.nepxion.discovery.plugin.strategy.sentinel.constant.SentinelStrategyConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.entity.SentinelStrategyRuleType;
import com.nepxion.discovery.plugin.strategy.sentinel.parser.SentinelStrategyAuthorityRuleParser;
import com.nepxion.discovery.plugin.strategy.sentinel.parser.SentinelStrategyDegradeRuleParser;
import com.nepxion.discovery.plugin.strategy.sentinel.parser.SentinelStrategyFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.sentinel.parser.SentinelStrategyParamFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.sentinel.parser.SentinelStrategySystemRuleParser;

public class SentinelStrategyRuleLoader {
    private static final Logger LOG = LoggerFactory.getLogger(SentinelStrategyRuleLoader.class);

    @Value("${" + SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_FLOW_PATH + ":" + DiscoveryConstant.PREFIX_CLASSPATH + SentinelStrategyConstant.SENTINEL_STRATEGY_FLOW_KEY + "." + DiscoveryConstant.JSON_FORMAT + "}")
    private String sentinelStrategyFlowPath;

    @Value("${" + SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_DEGRADE_PATH + ":" + DiscoveryConstant.PREFIX_CLASSPATH + SentinelStrategyConstant.SENTINEL_STRATEGY_DEGRADE_KEY + "." + DiscoveryConstant.JSON_FORMAT + "}")
    private String sentinelStrategyDegradePath;

    @Value("${" + SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_AUTHORITY_PATH + ":" + DiscoveryConstant.PREFIX_CLASSPATH + SentinelStrategyConstant.SENTINEL_STRATEGY_AUTHORITY_KEY + "." + DiscoveryConstant.JSON_FORMAT + "}")
    private String sentinelStrategyAuthorityPath;

    @Value("${" + SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_SYSTEM_PATH + ":" + DiscoveryConstant.PREFIX_CLASSPATH + SentinelStrategyConstant.SENTINEL_STRATEGY_SYSTEM_KEY + "." + DiscoveryConstant.JSON_FORMAT + "}")
    private String sentinelStrategySystemPath;

    @Value("${" + SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_PARAM_FLOW_PATH + ":" + DiscoveryConstant.PREFIX_CLASSPATH + SentinelStrategyConstant.SENTINEL_STRATEGY_PARAM_FLOW_KEY + "." + DiscoveryConstant.JSON_FORMAT + "}")
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

    public void loadFileRules(SentinelStrategyRuleType sentinelStrategyRuleType) {
        switch (sentinelStrategyRuleType) {
            case FLOW:
                if (CollectionUtils.isEmpty(FlowRuleManager.getRules())) {
                    loadRules(sentinelStrategyRuleType, getRules(sentinelStrategyFlowPath));
                } else {
                    LOG.info("{} exists, ignore to load from file", sentinelStrategyRuleType.getDescription());
                }
                break;
            case DEGRADE:
                if (CollectionUtils.isEmpty(DegradeRuleManager.getRules())) {
                    loadRules(sentinelStrategyRuleType, getRules(sentinelStrategyDegradePath));
                } else {
                    LOG.info("{} exists, ignore to load from file", sentinelStrategyRuleType.getDescription());
                }
                break;
            case AUTHORITY:
                if (CollectionUtils.isEmpty(AuthorityRuleManager.getRules())) {
                    loadRules(sentinelStrategyRuleType, getRules(sentinelStrategyAuthorityPath));
                } else {
                    LOG.info("{} exists, ignore to load from file", sentinelStrategyRuleType.getDescription());
                }
                break;
            case SYSTEM:
                if (CollectionUtils.isEmpty(SystemRuleManager.getRules())) {
                    loadRules(sentinelStrategyRuleType, getRules(sentinelStrategySystemPath));
                } else {
                    LOG.info("{} exists, ignore to load from file", sentinelStrategyRuleType.getDescription());
                }
                break;
            case PARAM_FLOW:
                if (CollectionUtils.isEmpty(ParamFlowRuleManager.getRules())) {
                    loadRules(sentinelStrategyRuleType, getRules(sentinelStrategyParamFlowPath));
                } else {
                    LOG.info("{} exists, ignore to load from file", sentinelStrategyRuleType.getDescription());
                }
                break;
        }
    }

    public void loadRules(SentinelStrategyRuleType sentinelStrategyRuleType, String sentinelStrategyRule) {
        if (StringUtils.isBlank(sentinelStrategyRule)) {
            sentinelStrategyRule = SentinelStrategyConstant.SENTINEL_STRATEGY_EMPTY_RULE;
        }

        switch (sentinelStrategyRuleType) {
            case FLOW:
                FlowRuleManager.loadRules(sentinelStrategyFlowRuleParser.convert(sentinelStrategyRule));

                LOG.info("Loaded {} count={}", sentinelStrategyRuleType.getDescription(), FlowRuleManager.getRules().size());
                break;
            case DEGRADE:
                DegradeRuleManager.loadRules(sentinelStrategyDegradeRuleParser.convert(sentinelStrategyRule));

                LOG.info("Loaded {} count={}", sentinelStrategyRuleType.getDescription(), DegradeRuleManager.getRules().size());
                break;
            case AUTHORITY:
                AuthorityRuleManager.loadRules(sentinelStrategyAuthorityRuleParser.convert(sentinelStrategyRule));

                LOG.info("Loaded {} count={}", sentinelStrategyRuleType.getDescription(), AuthorityRuleManager.getRules().size());
                break;
            case SYSTEM:
                SystemRuleManager.loadRules(sentinelStrategySystemRuleParser.convert(sentinelStrategyRule));

                LOG.info("Loaded {} count={}", sentinelStrategyRuleType.getDescription(), SystemRuleManager.getRules().size());
                break;
            case PARAM_FLOW:
                ParamFlowRuleManager.loadRules(sentinelStrategyParamFlowRuleParser.convert(sentinelStrategyRule));

                LOG.info("Loaded {} count={}", sentinelStrategyRuleType.getDescription(), ParamFlowRuleManager.getRules().size());
                break;
        }
    }

    public String getRules(String path) {
        String rules = FileContextUtil.getText(applicationContext, path);

        return StringUtils.isNotEmpty(rules) ? rules : SentinelStrategyConstant.SENTINEL_STRATEGY_EMPTY_RULE;
    }
}