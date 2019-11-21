package com.nepxion.discovery.plugin.framework.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public abstract class DefaultEnvironmentTransferAdapter implements EnvironmentTransferAdapter {
    @Autowired
    protected PluginAdapter pluginAdapter;

    @Value("${" + DiscoveryConstant.SPRING_APPLICATION_ENVIRONMENT_TRANSFER + ":" + DiscoveryConstant.SPRING_APPLICATION_ENVIRONMENT_TRANSFER_VALUE + "}")
    protected String environmentTransfer;

    @Override
    public String getTransferredEnvironment() {
        return environmentTransfer;
    }

    public PluginAdapter getPluginAdapter() {
        return pluginAdapter;
    }
}