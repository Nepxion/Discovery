package com.nepxion.discovery.console.resource;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.console.adapter.ConfigAdapter;

public class ConfigResourceImpl implements ConfigResource {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigResourceImpl.class);

    @Autowired(required = false)
    private ConfigAdapter configAdapter;

    @Override
    public String getConfigType() {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            throw new DiscoveryException("Remote config adapter isn't provided");
        }

        return configAdapter.getConfigType();
    }

    @Override
    public boolean updateConfig(String group, String serviceId, String config) throws Exception {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            throw new DiscoveryException("Remote config adapter isn't provided");
        }

        return configAdapter.updateConfig(group, serviceId, config);
    }

    @Override
    public boolean clearConfig(String group, String serviceId) throws Exception {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            throw new DiscoveryException("Remote config adapter isn't provided");
        }

        return configAdapter.clearConfig(group, serviceId);
    }

    @Override
    public String getConfig(String group, String serviceId) throws Exception {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            throw new DiscoveryException("Remote config adapter isn't provided");
        }

        return configAdapter.getConfig(group, serviceId);
    }
}