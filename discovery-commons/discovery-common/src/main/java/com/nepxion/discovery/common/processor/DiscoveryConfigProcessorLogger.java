package com.nepxion.discovery.common.processor;

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

public class DiscoveryConfigProcessorLogger {
    private static final Logger LOG = LoggerFactory.getLogger(DiscoveryConfigProcessorLogger.class);

    public static void logGetStarted(String group, String dataId, String description, String configType, boolean isConfigSingleKey) {
        LOG.info("Get {} config from {} server, " + getLogKey(isConfigSingleKey), description, configType, group, dataId);
    }

    public static void logGetFailed(String group, String dataId, String description, String configType, boolean isConfigSingleKey, Exception e) {
        LOG.error("Get {} config from {} server failed, " + getLogKey(isConfigSingleKey), description, configType, group, dataId, e);
    }

    public static void logSubscribeStarted(String group, String dataId, String description, String configType, boolean isConfigSingleKey) {
        LOG.info("Subscribe {} config from {} server, " + getLogKey(isConfigSingleKey), description, configType, group, dataId);
    }

    public static void logSubscribeFailed(String group, String dataId, String description, String configType, boolean isConfigSingleKey, Exception e) {
        LOG.error("Subscribe {} config from {} server failed, " + getLogKey(isConfigSingleKey), description, configType, group, dataId, e);
    }

    public static void logUnsubscribeStarted(String group, String dataId, String description, String configType, boolean isConfigSingleKey) {
        LOG.info("Unsubscribe {} config from {} server, " + getLogKey(isConfigSingleKey), description, configType, group, dataId);
    }

    public static void logUnsubscribeFailed(String group, String dataId, String description, String configType, boolean isConfigSingleKey, Exception e) {
        LOG.error("Unsubscribe {} config from {} server failed, " + getLogKey(isConfigSingleKey), description, configType, group, dataId, e);
    }

    public static void logCallbackFailed(String description, Exception e) {
        LOG.error("Callback {} config failed", description, e);
    }

    private static String getLogKey(boolean isConfigSingleKey) {
        return isConfigSingleKey ? "key={}-{}" : "group={}, dataId={}";
    }
}