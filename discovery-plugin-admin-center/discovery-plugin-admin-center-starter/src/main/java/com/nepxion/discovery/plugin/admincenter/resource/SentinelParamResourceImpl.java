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
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class SentinelParamResourceImpl implements SentinelParamResource {
    private static final Logger LOG = LoggerFactory.getLogger(SentinelParamResourceImpl.class);

    private static Converter<String, List<ParamFlowRule>> sentinelParamFlowRuleParser = new Converter<String, List<ParamFlowRule>>() {
        @Override
        public List<ParamFlowRule> convert(String source) {
            return JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
            });
        }
    };

    @Override
    public void updateParamFlowRules(String rule) {
        ParamFlowRuleManager.loadRules(sentinelParamFlowRuleParser.convert(rule));

        LOG.info("{} param flow rules loaded...", ParamFlowRuleManager.getRules().size());
    }

    @Override
    public void clearParamFlowRules() {
        LOG.info("{} param flow rules cleared...", ParamFlowRuleManager.getRules().size());

        ParamFlowRuleManager.loadRules(new ArrayList<ParamFlowRule>());
    }

    @Override
    public List<ParamFlowRule> viewParamFlowRules() {
        return ParamFlowRuleManager.getRules();
    }
}