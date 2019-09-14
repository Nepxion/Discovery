package com.nepxion.discovery.plugin.strategy.service.sentinel.local.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Weihua Wang
 * @author Haojun Ren
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.nepxion.discovery.plugin.strategy.service.sentinel.loader.AbstractSentinelRuleLoader;
import com.nepxion.discovery.plugin.strategy.service.sentinel.loader.SentinelRuleLoaderUtil;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelAuthorityRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelDegradeRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelParamFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.service.sentinel.parser.SentinelSystemRuleParser;

public class SentinelLocalRuleLoader extends AbstractSentinelRuleLoader {
    private static final Logger LOG = LoggerFactory.getLogger(SentinelLocalRuleLoader.class);

    @Override
    public void load() {
        FlowRuleManager.loadRules(new SentinelFlowRuleParser().convert(SentinelRuleLoaderUtil.getRuleText(applicationContext, flowPath)));
        LOG.info("{} flow rules loaded...", FlowRuleManager.getRules().size());

        DegradeRuleManager.loadRules(new SentinelDegradeRuleParser().convert(SentinelRuleLoaderUtil.getRuleText(applicationContext, degradePath)));
        LOG.info("{} degrade rules loaded...", DegradeRuleManager.getRules().size());

        AuthorityRuleManager.loadRules(new SentinelAuthorityRuleParser().convert(SentinelRuleLoaderUtil.getRuleText(applicationContext, authorityPath)));
        LOG.info("{} authority rules loaded...", AuthorityRuleManager.getRules().size());

        SystemRuleManager.loadRules(new SentinelSystemRuleParser().convert(SentinelRuleLoaderUtil.getRuleText(applicationContext, systemPath)));
        LOG.info("{} system rules loaded...", SystemRuleManager.getRules().size());

        ParamFlowRuleManager.loadRules(new SentinelParamFlowRuleParser().convert(SentinelRuleLoaderUtil.getRuleText(applicationContext, paramFlowPath)));
        LOG.info("{} param flow rules loaded...", ParamFlowRuleManager.getRules().size());
    }
}