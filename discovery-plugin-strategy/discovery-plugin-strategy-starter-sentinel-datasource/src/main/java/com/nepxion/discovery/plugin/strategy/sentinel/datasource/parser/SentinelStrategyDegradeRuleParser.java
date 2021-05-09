package com.nepxion.discovery.plugin.strategy.sentinel.datasource.parser;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class SentinelStrategyDegradeRuleParser implements Converter<String, List<DegradeRule>> {
    @Override
    public List<DegradeRule> convert(String source) {
        return JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
        });
    }
}