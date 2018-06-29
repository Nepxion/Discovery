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
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.plugin.framework.constant.ZookeeperConstant;
import com.nepxion.discovery.plugin.framework.exception.PluginException;

public class ZookeeperAdapter implements PluginAdapter {
    @Autowired
    protected ConfigurableEnvironment environment;

    @Override
    public String getIpAddress(Registration registration) {
        if (registration instanceof ZookeeperRegistration) {
            ZookeeperRegistration zookeeperRegistration = (ZookeeperRegistration) registration;

            return zookeeperRegistration.getServiceInstance().getAddress();
        }

        throw new PluginException("Registration instance isn't the type of Zookeeper");
    }

    @Override
    public String getVersion() {
        return environment.getProperty(ZookeeperConstant.METADATA_VERSION);
    }
}