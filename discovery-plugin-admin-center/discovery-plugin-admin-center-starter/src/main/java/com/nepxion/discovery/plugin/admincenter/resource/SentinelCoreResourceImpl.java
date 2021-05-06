package com.nepxion.discovery.plugin.admincenter.resource;

import java.util.ArrayList;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class SentinelCoreResourceImpl implements SentinelCoreResource {
    private static final Logger LOG = LoggerFactory.getLogger(SentinelCoreResourceImpl.class);

    private static Converter<String, List<FlowRule>> sentinelFlowRuleParser = new Converter<String, List<FlowRule>>() {
        @Override
        public List<FlowRule> convert(String source) {
            return JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
            });
        }
    };

    private static Converter<String, List<DegradeRule>> sentinelDegradeRuleParser = new Converter<String, List<DegradeRule>>() {
        @Override
        public List<DegradeRule> convert(String source) {
            return JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
            });
        }
    };

    private static Converter<String, List<AuthorityRule>> sentinelAuthorityRuleParser = new Converter<String, List<AuthorityRule>>() {
        @Override
        public List<AuthorityRule> convert(String source) {
            return JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
            });
        }
    };

    private static Converter<String, List<SystemRule>> sentinelSystemRuleParser = new Converter<String, List<SystemRule>>() {
        @Override
        public List<SystemRule> convert(String source) {
            return JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
            });
        }
    };

    @Override
    public void updateFlowRules(String rule) {
        FlowRuleManager.loadRules(sentinelFlowRuleParser.convert(rule));

        LOG.info("{} flow rules loaded...", FlowRuleManager.getRules().size());
    }

    @Override
    public void clearFlowRules() {
        LOG.info("{} flow rules cleared...", FlowRuleManager.getRules().size());

        FlowRuleManager.loadRules(new ArrayList<FlowRule>());
    }

    @Override
    public List<FlowRule> viewFlowRules() {
        return FlowRuleManager.getRules();
    }

    @Override
    public void updateDegradeRules(String rule) {
        DegradeRuleManager.loadRules(sentinelDegradeRuleParser.convert(rule));

        LOG.info("{} degrade rules loaded...", DegradeRuleManager.getRules().size());
    }

    @Override
    public void clearDegradeRules() {
        LOG.info("{} degrade rules cleared...", DegradeRuleManager.getRules().size());

        DegradeRuleManager.loadRules(new ArrayList<DegradeRule>());
    }

    @Override
    public List<DegradeRule> viewDegradeRules() {
        return DegradeRuleManager.getRules();
    }

    @Override
    public void updateAuthorityRules(String rule) {
        AuthorityRuleManager.loadRules(sentinelAuthorityRuleParser.convert(rule));

        LOG.info("{} authority rules loaded...", AuthorityRuleManager.getRules().size());
    }

    @Override
    public void clearAuthorityRules() {
        LOG.info("{} authority rules cleared...", AuthorityRuleManager.getRules().size());

        AuthorityRuleManager.loadRules(new ArrayList<AuthorityRule>());
    }

    @Override
    public List<AuthorityRule> viewAuthorityRules() {
        return AuthorityRuleManager.getRules();
    }

    @Override
    public void updateSystemRules(String rule) {
        SystemRuleManager.loadRules(sentinelSystemRuleParser.convert(rule));

        LOG.info("{} system rules loaded...", SystemRuleManager.getRules().size());
    }

    @Override
    public void clearSystemRules() {
        LOG.info("{} system rules cleared...", SystemRuleManager.getRules().size());

        SystemRuleManager.loadRules(new ArrayList<SystemRule>());
    }

    @Override
    public List<SystemRule> viewSystemRules() {
        return SystemRuleManager.getRules();
    }
}