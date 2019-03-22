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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public abstract class LocalConfigLoader implements ConfigLoader {
    private static final Logger LOG = LoggerFactory.getLogger(LocalConfigLoader.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public String getConfig() throws Exception {
        String path = getPath();
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("Config local path isn't set");
        }

        LOG.info("Config local path is {}", path);

        InputStream inputStream = null;
        try {
            String filePath = applicationContext.getEnvironment().resolvePlaceholders(path);
            inputStream = applicationContext.getResource(filePath).getInputStream();

            return IOUtils.toString(inputStream, DiscoveryConstant.ENCODING_UTF_8);
        } catch (Exception e) {
            LOG.warn("File [{}] isn't found or invalid, ignore to load...", path);

            return null;
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }

    protected abstract String getPath();
}