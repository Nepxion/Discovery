package com.nepxion.discovery.plugin.framework.decorator;

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
import org.springframework.beans.BeansException;
import org.springframework.cloud.alibaba.nacos.registry.NacosRegistration;
import org.springframework.cloud.alibaba.nacos.registry.NacosServiceRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.listener.register.RegisterListenerExecutor;

public class NacosServiceRegistryDecorator extends NacosServiceRegistry {
    private static final Logger LOG = LoggerFactory.getLogger(NacosServiceRegistryDecorator.class);

    private NacosServiceRegistry serviceRegistry;
    private ConfigurableApplicationContext applicationContext;
    private ConfigurableEnvironment environment;

    public NacosServiceRegistryDecorator(NacosServiceRegistry serviceRegistry, ConfigurableApplicationContext applicationContext) {
        this.serviceRegistry = serviceRegistry;
        this.applicationContext = applicationContext;
        this.environment = applicationContext.getEnvironment();
    }

    @Override
    public void register(NacosRegistration registration) {
        Boolean registerControlEnabled = PluginContextAware.isRegisterControlEnabled(environment);
        if (registerControlEnabled) {
            try {
                RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
                registerListenerExecutor.onRegister(registration);
            } catch (BeansException e) {
                LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
            }
        }

        serviceRegistry.register(registration);
    }

    @Override
    public void deregister(NacosRegistration registration) {
        Boolean registerControlEnabled = PluginContextAware.isRegisterControlEnabled(environment);
        if (registerControlEnabled) {
            try {
                RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
                registerListenerExecutor.onDeregister(registration);
            } catch (BeansException e) {
                LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
            }
        }

        serviceRegistry.deregister(registration);
    }

    @Override
    public void setStatus(NacosRegistration registration, String status) {
        Boolean registerControlEnabled = PluginContextAware.isRegisterControlEnabled(environment);
        if (registerControlEnabled) {
            try {
                RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
                registerListenerExecutor.onSetStatus(registration, status);
            } catch (BeansException e) {
                LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
            }
        }

        serviceRegistry.setStatus(registration, status);
    }

    @Override
    public <T> T getStatus(NacosRegistration registration) {
        return serviceRegistry.getStatus(registration);
    }

    @Override
    public void close() {
        Boolean registerControlEnabled = PluginContextAware.isRegisterControlEnabled(environment);
        if (registerControlEnabled) {
            try {
                RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
                registerListenerExecutor.onClose();
            } catch (BeansException e) {
                LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
            }
        }

        serviceRegistry.close();
    }

    public ConfigurableEnvironment getEnvironment() {
        return environment;
    }
}