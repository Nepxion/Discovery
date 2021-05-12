package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.console.adapter.ConfigAdapter;
import com.nepxion.discovery.console.rest.ConfigClearRestInvoker;
import com.nepxion.discovery.console.rest.ConfigUpdateRestInvoker;
import com.nepxion.discovery.console.rest.ConfigViewRestInvoker;

public class ConfigResourceImpl implements ConfigResource {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigResourceImpl.class);

    @Autowired(required = false)
    private ConfigAdapter configAdapter;

    @Autowired
    private ServiceResource serviceResource;

    @Autowired
    private RestTemplate consoleRestTemplate;

    @Override
    public String getConfigType() {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            throw new DiscoveryException("Remote config adapter isn't provided");
        }

        return configAdapter.getConfigType();
    }

    @Override
    public boolean updateRemoteConfig(String group, String serviceId, String config) throws Exception {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            throw new DiscoveryException("Remote config adapter isn't provided");
        }

        return configAdapter.updateConfig(group, serviceId, config);
    }

    @Override
    public boolean clearRemoteConfig(String group, String serviceId) throws Exception {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            throw new DiscoveryException("Remote config adapter isn't provided");
        }

        return configAdapter.clearConfig(group, serviceId);
    }

    @Override
    public String getRemoteConfig(String group, String serviceId) throws Exception {
        if (configAdapter == null) {
            LOG.error("Remote config adapter isn't provided");

            throw new DiscoveryException("Remote config adapter isn't provided");
        }

        return configAdapter.getConfig(group, serviceId);
    }

    @Override
    public List<ResultEntity> updateConfig(String serviceId, String config, boolean async) {
        ConfigUpdateRestInvoker configUpdateRestInvoker = new ConfigUpdateRestInvoker(serviceResource, serviceId, consoleRestTemplate, async, config);

        return configUpdateRestInvoker.invoke();
    }

    @Override
    public List<ResultEntity> clearConfig(String serviceId, boolean async) {
        ConfigClearRestInvoker configClearRestInvoker = new ConfigClearRestInvoker(serviceResource, serviceId, consoleRestTemplate, async);

        return configClearRestInvoker.invoke();
    }

    @Override
    public List<ResultEntity> viewConfig(String serviceId) {
        ConfigViewRestInvoker configViewRestInvoker = new ConfigViewRestInvoker(serviceResource, serviceId, consoleRestTemplate);

        return configViewRestInvoker.invoke();
    }
}