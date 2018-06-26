package com.nepxion.discovery.plugin.framework.listener;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class BasicListener implements Listener {
    @Autowired
    protected ConfigurableApplicationContext applicationContext;

    @Autowired
    protected ConfigurableEnvironment environment;

    @Override
    public ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public ConfigurableEnvironment getEnvironment() {
        return environment;
    }
}