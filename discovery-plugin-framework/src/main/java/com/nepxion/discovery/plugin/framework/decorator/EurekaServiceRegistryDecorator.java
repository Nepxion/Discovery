package com.nepxion.discovery.plugin.framework.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.framework.listener.RegisterListenerExecutor;

public class EurekaServiceRegistryDecorator extends EurekaServiceRegistry {
    private ServiceRegistry<EurekaRegistration> serviceRegistry;
    private ConfigurableApplicationContext applicationContext;
    private ConfigurableEnvironment environment;

    public EurekaServiceRegistryDecorator(ServiceRegistry<EurekaRegistration> serviceRegistry, ConfigurableApplicationContext applicationContext) {
        this.serviceRegistry = serviceRegistry;
        this.applicationContext = applicationContext;
        this.environment = applicationContext.getEnvironment();
    }

    @Override
    public void register(EurekaRegistration registration) {
        Boolean registerControlEnabled = PluginContextAware.isRegisterControlEnabled(environment);
        if (registerControlEnabled) {
            RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
            registerListenerExecutor.fireRegister(registration);
        }

        serviceRegistry.register(registration);
    }

    @Override
    public void deregister(EurekaRegistration registration) {
        Boolean registerControlEnabled = PluginContextAware.isRegisterControlEnabled(environment);
        if (registerControlEnabled) {
            RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
            registerListenerExecutor.fireDeregister(registration);
        }

        serviceRegistry.deregister(registration);
    }

    @Override
    public void setStatus(EurekaRegistration registration, String status) {
        Boolean registerControlEnabled = PluginContextAware.isRegisterControlEnabled(environment);
        if (registerControlEnabled) {
            RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
            registerListenerExecutor.fireSetStatus(registration, status);
        }

        serviceRegistry.setStatus(registration, status);
    }

    @Override
    public Object getStatus(EurekaRegistration registration) {
        return serviceRegistry.getStatus(registration);
    }

    @Override
    public void close() {
        Boolean registerControlEnabled = PluginContextAware.isRegisterControlEnabled(environment);
        if (registerControlEnabled) {
            RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
            registerListenerExecutor.fireClose();
        }

        serviceRegistry.close();
    }

    public ConfigurableEnvironment getEnvironment() {
        return environment;
    }
}