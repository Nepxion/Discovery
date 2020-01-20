package com.nepxion.discovery.plugin.framework.generator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

public class GroupGeneratorSymbol extends GroupGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(GroupGeneratorSymbol.class);

    private String generatorSymbolCharacter;

    @PostConstruct
    public void initialize() {
        generatorSymbolCharacter = PluginContextAware.getGroupGeneratorSymbolCharacter(applicationContext.getEnvironment());
        applicationName = PluginContextAware.getApplicationName(applicationContext.getEnvironment());

        group = applicationName.split(generatorSymbolCharacter)[0];

        LOG.info("--------------------------------------------------");
        if (StringUtils.isNotEmpty(group)) {
            LOG.info("Use application name prefix={} as metadata group", group);
        } else {
            LOG.error("Not substring application name prefix for generator length={}, use default metadata group setting", generatorSymbolCharacter);
        }
        LOG.info("--------------------------------------------------");
    }

    public String getGeneratorSymbolCharacter() {
        return generatorSymbolCharacter;
    }

}