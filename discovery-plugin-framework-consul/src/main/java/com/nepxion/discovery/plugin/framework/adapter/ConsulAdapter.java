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
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.plugin.framework.constant.ConsulConstant;
import com.nepxion.discovery.plugin.framework.exception.PluginException;

public class ConsulAdapter implements PluginAdapter {
    @Autowired
    protected ConfigurableEnvironment environment;

    @Override
    public String getIpAddress(Registration registration) {
        if (registration instanceof ConsulRegistration) {
            ConsulRegistration consulRegistration = (ConsulRegistration) registration;

            return consulRegistration.getService().getAddress();
        }

        throw new PluginException("Registration instance isn't the type of Consul");
    }

    @Override
    public String getVersion() {
        return environment.getProperty(ConsulConstant.CONSUL_METADATA_VERSION);
    }
}