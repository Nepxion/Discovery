package com.nepxion.discovery.plugin.framework.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.consul.discovery.ConsulServer;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;

import com.nepxion.discovery.plugin.framework.constant.ConsulConstant;
import com.nepxion.discovery.plugin.framework.constant.PluginConstant;
import com.nepxion.discovery.plugin.framework.exception.PluginException;
import com.netflix.loadbalancer.Server;

public class ConsulAdapter extends AbstractPluginAdapter {
    private String version;

    @PostConstruct
    private void initialize() {
        String value = pluginContextAware.getEnvironment().getProperty(ConsulConstant.METADATA_VERSION);
        if (StringUtils.isEmpty(value)) {
            return;
        }

        String[] valueArray = StringUtils.split(value, ",");
        if (ArrayUtils.isEmpty(valueArray)) {
            return;
        }

        for (String text : valueArray) {
            String[] textArray = StringUtils.split(text.trim(), "=");
            if (textArray.length != 2) {
                throw new PluginException("Invalid tags config for consul");
            }

            if (StringUtils.equals(textArray[0].trim(), PluginConstant.VERSION)) {
                version = textArray[1].trim();
                return;
            }
        }
    }

    @Override
    public String getIpAddress(Registration registration) {
        if (registration instanceof ConsulRegistration) {
            ConsulRegistration consulRegistration = (ConsulRegistration) registration;

            return consulRegistration.getService().getAddress();
        }

        throw new PluginException("Registration instance isn't the type of ConsulRegistration");
    }

    @Override
    public int getPort(Registration registration) {
        if (registration instanceof ConsulRegistration) {
            ConsulRegistration consulRegistration = (ConsulRegistration) registration;

            return consulRegistration.getService().getPort();
        }

        throw new PluginException("Registration instance isn't the type of ConsulRegistration");
    }

    @Override
    public String getServerVersion(Server server) {
        if (server instanceof ConsulServer) {
            ConsulServer consulServer = (ConsulServer) server;

            return consulServer.getMetadata().get(PluginConstant.VERSION);
        }

        throw new PluginException("Server instance isn't the type of ConsulServer");
    }

    @Override
    public String getLocalVersion() {
        return version;
    }
}