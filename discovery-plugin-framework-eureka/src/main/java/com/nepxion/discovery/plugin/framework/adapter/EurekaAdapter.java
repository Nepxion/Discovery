package com.nepxion.discovery.plugin.framework.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;

import com.nepxion.discovery.plugin.framework.constant.EurekaConstant;
import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.exception.PluginException;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

public class EurekaAdapter extends AbstractPluginAdapter {
    @Override
    public String getIpAddress(Registration registration) {
        if (registration instanceof EurekaRegistration) {
            EurekaRegistration eurekaRegistration = (EurekaRegistration) registration;

            return eurekaRegistration.getInstanceConfig().getIpAddress();
        }

        throw new PluginException("Registration instance isn't the type of EurekaRegistration");
    }

    @Override
    public int getPort(Registration registration) {
        if (registration instanceof EurekaRegistration) {
            EurekaRegistration eurekaRegistration = (EurekaRegistration) registration;

            return eurekaRegistration.getInstanceConfig().getNonSecurePort();
        }

        throw new PluginException("Registration instance isn't the type of EurekaRegistration");
    }

    @Override
    public String getServerVersion(Server server) {
        if (server instanceof DiscoveryEnabledServer) {
            DiscoveryEnabledServer discoveryEnabledServer = (DiscoveryEnabledServer) server;

            return discoveryEnabledServer.getInstanceInfo().getMetadata().get(PluginConstant.VERSION);
        }

        throw new PluginException("Server instance isn't the type of DiscoveryEnabledServer");
    }

    @Override
    public String getLocalVersion() {
        return pluginContextAware.getEnvironment().getProperty(EurekaConstant.METADATA_VERSION);
    }
}