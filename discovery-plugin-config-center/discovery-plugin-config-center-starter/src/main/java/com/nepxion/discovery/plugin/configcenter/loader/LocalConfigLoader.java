package com.nepxion.discovery.plugin.configcenter.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.nepxion.discovery.plugin.framework.util.FileContextUtil;

public abstract class LocalConfigLoader implements ConfigLoader {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public String[] getConfigList() throws Exception {
        String path = getPath();

        String[] config = new String[1];
        config[0] = FileContextUtil.getText(applicationContext, path);

        return config;
    }

    protected abstract String getPath();
}