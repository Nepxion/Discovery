package com.nepxion.discovery.plugin.framework.listener.register;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

import com.nepxion.discovery.plugin.framework.listener.BasicListener;

public abstract class AbstractRegisterListener extends BasicListener implements RegisterListener {
    @Autowired
    protected ServiceRegistry<?> serviceRegistry;

    public ServiceRegistry<?> getServiceRegistry() {
        return serviceRegistry;
    }
}