package com.nepxion.discovery.plugin.configcenter.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RemoteConfigLoader implements ConfigLoader {
    private static final Logger LOG = LoggerFactory.getLogger(RemoteConfigLoader.class);

    @PostConstruct
    public void initialize() {
        try {
            subscribeConfig();
        } catch (Exception e) {
            LOG.error("Subscribe config failed", e);
        }
    }

    protected abstract void subscribeConfig() throws Exception;
}