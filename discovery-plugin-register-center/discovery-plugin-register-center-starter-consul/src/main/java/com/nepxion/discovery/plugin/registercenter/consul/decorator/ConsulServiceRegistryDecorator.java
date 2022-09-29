package com.nepxion.discovery.plugin.registercenter.consul.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.BeansException;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.context.ConfigurableApplicationContext;

import com.nepxion.discovery.plugin.framework.listener.register.RegisterListenerExecutor;

public class ConsulServiceRegistryDecorator extends ConsulServiceRegistry {
    // private static final Logger LOG = LoggerFactory.getLogger(ConsulServiceRegistryDecorator.class);

    private ConsulServiceRegistry serviceRegistry;
    private ConfigurableApplicationContext applicationContext;

    public ConsulServiceRegistryDecorator(ConsulServiceRegistry serviceRegistry, ConfigurableApplicationContext applicationContext) {
        super(null, null, null, null);

        this.serviceRegistry = serviceRegistry;
        this.applicationContext = applicationContext;
    }

    @Override
    public void register(ConsulRegistration registration) {
        try {
            RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
            registerListenerExecutor.onRegister(registration);
        } catch (BeansException e) {
            // LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
        }

        serviceRegistry.register(registration);
    }

    @Override
    public void deregister(ConsulRegistration registration) {
        try {
            RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
            registerListenerExecutor.onDeregister(registration);
        } catch (BeansException e) {
            // LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
        }

        serviceRegistry.deregister(registration);
    }

    @Override
    public void setStatus(ConsulRegistration registration, String status) {
        try {
            RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
            registerListenerExecutor.onSetStatus(registration, status);
        } catch (BeansException e) {
            // LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
        }

        serviceRegistry.setStatus(registration, status);
    }

    @Override
    public Object getStatus(ConsulRegistration registration) {
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