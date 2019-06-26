package com.nepxion.discovery.plugin.configcenter.parser.json;

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

import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.framework.config.PluginConfigParser;

public class JsonConfigParser implements PluginConfigParser {
    private static final Logger LOG = LoggerFactory.getLogger(JsonConfigParser.class);

    @Override
    public RuleEntity parse(String config) {
        if (StringUtils.isEmpty(config)) {
            throw new DiscoveryException("Config is null or empty");
        }

        try {
            RuleEntity ruleEntity = JsonUtil.fromJson(config, RuleEntity.class);
            ruleEntity.setContent(config);

            LOG.info("Rule content=\n{}", config);

            // Just for RuleEntity print
            // System.out.println(ruleEntity);

            return ruleEntity;
        } catch (Exception e) {
            throw new DiscoveryException(e.getMessage(), e);
        }
    }
}