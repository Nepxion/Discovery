package com.nepxion.discovery.plugin.framework.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public class FileContextUtil {
    private static final Logger LOG = LoggerFactory.getLogger(FileContextUtil.class);

    public static File getFile(ApplicationContext applicationContext, String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("File path isn't set");
        }

        LOG.info("File path is {}", path);

        try {
            String filePath = applicationContext.getEnvironment().resolvePlaceholders(path);

            return applicationContext.getResource(filePath).getFile();
        } catch (Exception e) {
            LOG.warn("File [{}] isn't found or invalid, ignore to load...", path);
        }

        return null;
    }

    public static InputStream getInputStream(ApplicationContext applicationContext, String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("File path isn't set");
        }

        LOG.info("File path is {}", path);

        try {
            String filePath = applicationContext.getEnvironment().resolvePlaceholders(path);

            return applicationContext.getResource(filePath).getInputStream();
        } catch (Exception e) {
            LOG.warn("File [{}] isn't found or invalid, ignore to load...", path);
        }

        return null;
    }

    public static String getText(ApplicationContext applicationContext, String path) {
        InputStream inputStream = null;
        try {
            inputStream = getInputStream(applicationContext, path);
            if (inputStream != null) {
                try {
                    return IOUtils.toString(inputStream, DiscoveryConstant.ENCODING_UTF_8);
                } catch (IOException e) {
                    LOG.warn("InputStream to String failed, ignore to load...");
                }
            }
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }

        return null;
    }
}