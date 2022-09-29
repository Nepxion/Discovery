package com.nepxion.discovery.plugin.registercenter.nacos.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.BeansException;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.ConfigurableApplicationContext;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import com.nepxion.discovery.plugin.framework.listener.register.RegisterListenerExecutor;

public class NacosServiceRegistryDecorator extends NacosServiceRegistry {
    // private static final Logger LOG = LoggerFactory.getLogger(NacosServiceRegistryDecorator.class);

    private NacosServiceRegistry serviceRegistry;
    private ConfigurableApplicationContext applicationContext;

    public NacosServiceRegistryDecorator(NacosServiceManager nacosServiceManager, NacosDiscoveryProperties nacosDiscoveryProperties, NacosServiceRegistry serviceRegistry, ConfigurableApplicationContext applicationContext) {
        super(nacosServiceManager, nacosDiscoveryProperties);

        this.serviceRegistry = serviceRegistry;
        this.applicationContext = applicationContext;
    }

    @Override
    public void register(Registration registration) {
        try {
            RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
            registerListenerExecutor.onRegister(registration);
        } catch (BeansException e) {
            // LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
        }

        serviceRegistry.register(registration);
    }

    @Override
    public void deregister(Registration registration) {
        try {
            RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
            registerListenerExecutor.onDeregister(registration);
        } catch (BeansException e) {
            // LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
        }

        serviceRegistry.deregister(registration);
    }

    @Override
    public void setStatus(Registration registration, String status) {
        try {
            RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
            registerListenerExecutor.onSetStatus(registration, status);
        } catch (BeansException e) {
            // LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
        }

        serviceRegistry.setStatus(registration, status);
    }

    @Override
    public Object getStatus(Registration registration) {
        return serviceRegistry.getStatus(registration);
    }

    @Override
    public void close() {
        try {
            RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
            registerListenerExecutor.onClose();
        } catch (BeansException e) {
            // LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
        }

        serviceRegistry.close();
    }
}