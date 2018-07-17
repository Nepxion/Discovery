package com.nepxion.discovery.plugin.example.feign;

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
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;

public class AbstractFeignImpl {
    @Autowired
    protected ConfigurableEnvironment environment;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private Registration registration;

    public String doInvoke(String value) {
        return pluginAdapter.mock(registration, value);
    }
}