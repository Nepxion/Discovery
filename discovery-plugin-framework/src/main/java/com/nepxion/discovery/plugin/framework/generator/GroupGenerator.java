package com.nepxion.discovery.plugin.framework.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author Robin
 * @date 2020/1/20
 */
public class GroupGenerator {

    @Autowired
    protected ApplicationContext applicationContext;

    protected String applicationName;

    protected String group;

    public String getApplicationName() {
        return applicationName;
    }

    public String getGroup() {
        return group;
    }
}