package com.nepxion.discovery.plugin.admincenter.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nepxion.discovery.plugin.framework.cache.PluginCache;
import com.nepxion.discovery.plugin.framework.constant.PluginConstant;

@ManagedResource(description = "Admin endpoint")
public class AdminEndpoint implements MvcEndpoint, ApplicationContextAware, EnvironmentAware {
    private static final Logger LOG = LoggerFactory.getLogger(AdminEndpoint.class);

    private ConfigurableApplicationContext context;
    private Environment environment;
    private ServiceRegistry<?> serviceRegistry;
    private Registration registration;

    @Autowired
    private PluginCache pluginCache;

    public AdminEndpoint(ServiceRegistry<?> serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    @RequestMapping(path = "filter", method = RequestMethod.GET)
    @ManagedOperation
    public Object filter(@RequestParam("serviceId") String serviceId, @RequestParam("ip") String ip) {
        Boolean discoveryControlEnabled = environment.getProperty(PluginConstant.SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED, Boolean.class);
        if (!discoveryControlEnabled) {
            return new ResponseEntity<>(Collections.singletonMap("Message", "Admin endpoint is disabled"), HttpStatus.NOT_FOUND);
        }

        pluginCache.put(serviceId, ip);

        return "success";
    }

    @Override
    public String getPath() {
        return "/discovery";
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class<? extends Endpoint> getEndpointType() {
        return null;
    }

    @Override
    public boolean isSensitive() {
        return true;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.context = (ConfigurableApplicationContext) applicationContext;
        }
    }
}