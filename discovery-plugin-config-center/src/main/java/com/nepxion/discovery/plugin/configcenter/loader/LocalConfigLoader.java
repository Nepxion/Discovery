package com.nepxion.discovery.plugin.configcenter.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public abstract class LocalConfigLoader implements ConfigLoader {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public InputStream getInputStream() throws Exception {
        String path = getPath();
        if (StringUtils.isEmpty(path)) {
            return null;
        }

        String filePath = applicationContext.getEnvironment().resolvePlaceholders(path);

        return applicationContext.getResource(filePath).getInputStream();
    }

    protected abstract String getPath();
}