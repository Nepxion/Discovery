package com.nepxion.discovery.plugin.framework.parser.json;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.framework.parser.PluginConfigDeparser;

public class JsonConfigDeparser implements PluginConfigDeparser {
    private static final Logger LOG = LoggerFactory.getLogger(JsonConfigDeparser.class);

    @Override
    public String deparse(RuleEntity ruleEntity) {
        if (ruleEntity == null) {
            throw new DiscoveryException("RuleEntity is null");
        }

        LOG.info("Start to deparse RuleEntity to json...");

        try {
            String config = JsonUtil.toPrettyJson(ruleEntity);

            LOG.info("Rule content=\n{}", config);

            return config;
        } catch (Exception e) {
            throw new DiscoveryException(e.getMessage(), e);
        }
    }
}