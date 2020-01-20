package com.nepxion.discovery.plugin.framework.generator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nepxion.discovery.plugin.framework.context.PluginContextAware;

public class GroupGeneratorLength extends GroupGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(GroupGeneratorLength.class);

    private Integer generatorLength;

    @PostConstruct
    public void initialize() {
        generatorLength = PluginContextAware.getGroupGeneratorLength(applicationContext.getEnvironment());
        applicationName = PluginContextAware.getApplicationName(applicationContext.getEnvironment());

        if (generatorLength > 0) {
            group = applicationName.substring(0, generatorLength);
        }

        LOG.info("--------------------------------------------------");
        if (StringUtils.isNotEmpty(group)) {
            LOG.info("Use application name prefix={} as metadata group", group);
        } else {
            LOG.error("Not substring application name prefix for generator length={}, use default metadata group setting", generatorLength);
        }
        LOG.info("--------------------------------------------------");
    }

    public Integer getGeneratorLength() {
        return generatorLength;
    }

}