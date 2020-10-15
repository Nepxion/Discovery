package com.nepxion.discovery.plugin.strategy.agent.plugin.mdc;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author HaojunRen
 * @version 1.0
 */

import com.nepxion.discovery.plugin.strategy.agent.plugin.AbstractPlugin;

public class MDCPlugin extends AbstractPlugin {
    private Boolean threadMDCEnabled = Boolean.valueOf(System.getProperty("thread.mdc.enabled", "false"));

    @Override
    protected String getMatcherClassName() {
        return "org.slf4j.MDC";
    }

    @Override
    protected String getHookClassName() {
        return MDCContextHook.class.getName();
    }

    @Override
    protected boolean isEnabled() {
        return threadMDCEnabled;
    }
}