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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.nepxion.discovery.plugin.framework.constant.PluginConstant;

public abstract class LocalConfigLoader implements ConfigLoader {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public String getConfig() throws Exception {
        String path = getPath();
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("Local path isn't set");
        }

        InputStream inputStream = null;
        try {
            String filePath = applicationContext.getEnvironment().resolvePlaceholders(path);
            inputStream = applicationContext.getResource(filePath).getInputStream();

            return IOUtils.toString(inputStream, PluginConstant.ENCODING_UTF_8);
        } catch (Exception e) {
            throw e;
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }

    protected abstract String getPath();
}