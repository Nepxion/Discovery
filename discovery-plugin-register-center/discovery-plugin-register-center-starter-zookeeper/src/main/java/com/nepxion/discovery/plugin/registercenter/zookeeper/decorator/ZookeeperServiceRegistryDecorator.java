package com.nepxion.discovery.plugin.registercenter.zookeeper.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.BeansException;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.context.ConfigurableApplicationContext;

import com.nepxion.discovery.plugin.framework.listener.register.RegisterListenerExecutor;

public class ZookeeperServiceRegistryDecorator extends ZookeeperServiceRegistry {
    // private static final Logger LOG = LoggerFactory.getLogger(ZookeeperServiceRegistryDecorator.class);

    private ZookeeperServiceRegistry serviceRegistry;
    private ConfigurableApplicationContext applicationContext;

    public ZookeeperServiceRegistryDecorator(ZookeeperServiceRegistry serviceRegistry, ConfigurableApplicationContext applicationContext) {
        super(null);

        this.serviceRegistry = serviceRegistry;
        this.applicationContext = applicationContext;
    }

    @Override
    public void register(ZookeeperRegistration registration) {
        try {
            RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
            registerListenerExecutor.onRegister(registration);
        } catch (BeansException e) {
            // LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
        }

        serviceRegistry.register(registration);
    }

    @Override
    public void deregister(ZookeeperRegistration registration) {
        try {
            RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
            registerListenerExecutor.onDeregister(registration);
        } catch (BeansException e) {
            // LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
        }

        serviceRegistry.deregister(registration);
    }

    @Override
    public void setStatus(ZookeeperRegistration registration, String status) {
        try {
            RegisterListenerExecutor registerListenerExecutor = applicationContext.getBean(RegisterListenerExecutor.class);
            registerListenerExecutor.onSetStatus(registration, status);
        } catch (BeansException e) {
            // LOG.warn("Get bean for RegisterListenerExecutor failed, ignore to executor listener");
        }

        serviceRegistry.setStatus(registration, status);
    }

    @Override
    public Object getStatus(ZookeeperRegistration registration) {
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

    @Override
    public void afterSingletonsInstantiated() {
        serviceRegistry.afterSingletonsInstantiated();
    }
}