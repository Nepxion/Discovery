package com.nepxion.discovery.plugin.configcenter.loader;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.nepxion.discovery.plugin.framework.util.FileContextUtil;

public abstract class LocalConfigLoader implements ConfigLoader {
    private static final Logger LOG = LoggerFactory.getLogger(LocalConfigLoader.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public String getConfig() throws Exception {
        String path = getPath();

        LOG.info("Start to load local config...");

        return FileContextUtil.getText(applicationContext, path);
    }

    protected abstract String getPath();
}