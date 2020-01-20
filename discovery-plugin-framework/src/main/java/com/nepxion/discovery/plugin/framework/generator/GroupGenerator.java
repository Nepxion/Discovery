package com.nepxion.discovery.plugin.framework.generator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Haojun Ren
 * @version 1.0
 */

import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

public class GroupGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(GroupGenerator.class);

    @Autowired
    private ApplicationContext applicationContext;

    private Integer generatorLength;
    private String generatorCharacter;
    private String applicationName;

    private String group;

    @PostConstruct
    public void initialize() {
        generatorLength = PluginContextAware.getGroupGeneratorLength(applicationContext.getEnvironment());
        generatorCharacter = PluginContextAware.getGroupGeneratorSymbolCharacter(applicationContext.getEnvironment());
        applicationName = PluginContextAware.getApplicationName(applicationContext.getEnvironment());

        if (generatorLength > 0) {
            group = applicationName.substring(0, generatorLength);
        } else if (StringUtils.isNotEmpty(generatorCharacter)) {
            group = applicationName.split(generatorCharacter)[0];
        }

        LOG.info("--------------------------------------------------");
        if (StringUtils.isNotEmpty(group)) {
            LOG.info("Use application name prefix={} as metadata group", group);
        } else {
            LOG.error("Not substring application name prefix for generator length={}, charactor={}, use default metadata group setting",
                    generatorLength, generatorCharacter);
        }
        LOG.info("--------------------------------------------------");
    }

    public Integer getGeneratorLength() {
        return generatorLength;
    }

    public String getGeneratorCharacter() {
        return generatorCharacter;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getGroup() {
        return group;
    }
}