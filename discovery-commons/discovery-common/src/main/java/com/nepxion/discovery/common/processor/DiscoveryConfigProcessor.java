package com.nepxion.discovery.common.processor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.DisposableBean;

import com.nepxion.discovery.common.entity.ConfigType;

public abstract class DiscoveryConfigProcessor implements DisposableBean {
    public void logGetStarted() {
        String group = getGroup();
        String dataId = getDataId();
        String description = getDescription();
        ConfigType configType = getConfigType();
        boolean isConfigSingleKey = isConfigSingleKey();

        DiscoveryConfigProcessorLogger.logGetStarted(group, dataId, description, configType, isConfigSingleKey);
    }

    public void logGetFailed(Exception e) {
        String group = getGroup();
        String dataId = getDataId();
        String description = getDescription();
        ConfigType configType = getConfigType();
        boolean isConfigSingleKey = isConfigSingleKey();

        DiscoveryConfigProcessorLogger.logGetFailed(group, dataId, description, configType, isConfigSingleKey, e);
    }

    public void logNotFound() {
        String description = getDescription();
        ConfigType configType = getConfigType();

        DiscoveryConfigProcessorLogger.logNotFound(description, configType);
    }

    public void logSubscribeStarted() {
        String group = getGroup();
        String dataId = getDataId();
        String description = getDescription();
        ConfigType configType = getConfigType();
        boolean isConfigSingleKey = isConfigSingleKey();

        DiscoveryConfigProcessorLogger.logSubscribeStarted(group, dataId, description, configType, isConfigSingleKey);
    }

    public void logSubscribeFailed(Exception e) {
        String group = getGroup();
        String dataId = getDataId();
        String description = getDescription();
        ConfigType configType = getConfigType();
        boolean isConfigSingleKey = isConfigSingleKey();

        DiscoveryConfigProcessorLogger.logSubscribeFailed(group, dataId, description, configType, isConfigSingleKey, e);
    }

    public void logUnsubscribeStarted() {
        String group = getGroup();
        String dataId = getDataId();
        String description = getDescription();
        ConfigType configType = getConfigType();
        boolean isConfigSingleKey = isConfigSingleKey();

        DiscoveryConfigProcessorLogger.logUnsubscribeStarted(group, dataId, description, configType, isConfigSingleKey);
    }

    public void logUnsubscribeFailed(Exception e) {
        String group = getGroup();
        String dataId = getDataId();
        String description = getDescription();
        ConfigType configType = getConfigType();
        boolean isConfigSingleKey = isConfigSingleKey();

        DiscoveryConfigProcessorLogger.logUnsubscribeFailed(group, dataId, description, configType, isConfigSingleKey, e);
    }

    public void logCallbackFailed(Exception e) {
        String description = getDescription();

        DiscoveryConfigProcessorLogger.logCallbackFailed(description, e);
    }

    public boolean isConfigSingleKey() {
        ConfigType configType = getConfigType();

        return ConfigType.isSingleKey(configType);
    }

    public void beforeInitialization() {

    }

    public void afterInitialization() {

    }

    public abstract ConfigType getConfigType();

    public abstract String getGroup();

    public abstract String getDataId();

    public abstract String getDescription();

    public abstract void callbackConfig(String config);
}