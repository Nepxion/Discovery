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
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.constant.ZookeeperConstant;
import com.nepxion.discovery.plugin.framework.exception.PluginException;
import com.netflix.loadbalancer.Server;

public class ZookeeperAdapter extends AbstractPluginAdapter {
    @Autowired
    private ConfigurableEnvironment environment;

    @Override
    public String getIpAddress(Registration registration) {
        if (registration instanceof ZookeeperRegistration) {
            ZookeeperRegistration zookeeperRegistration = (ZookeeperRegistration) registration;

            return zookeeperRegistration.getServiceInstance().getAddress();
        }

        throw new PluginException("Registration instance isn't the type of ZookeeperRegistration");
    }

    @Override
    public int getPort(Registration registration) {
        if (registration instanceof ZookeeperRegistration) {
            ZookeeperRegistration zookeeperRegistration = (ZookeeperRegistration) registration;

            return zookeeperRegistration.getServiceInstance().getPort();
        }

        throw new PluginException("Registration instance isn't the type of ZookeeperRegistration");
    }

    @Override
    public String getServerVersion(Server server) {
        if (server instanceof ZookeeperServer) {
            ZookeeperServer zookeeperServer = (ZookeeperServer) server;

            return zookeeperServer.getInstance().getPayload().getMetadata().get(PluginConstant.VERSION);
        }

        throw new PluginException("Server instance isn't the type of ZookeeperServer");
    }

    @Override
    public String getLocalVersion() {
        return environment.getProperty(ZookeeperConstant.METADATA_VERSION);
    }
}