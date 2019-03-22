package com.nepxion.discovery.plugin.example.service.sentinel;

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

public class MyRestTemplateFallbackHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MyRestTemplateFallbackHandler.class);

    public static void hanldleFallback() {
        LOG.info("Sentinel RestTemplate Client Fallback Causes");
    }
}