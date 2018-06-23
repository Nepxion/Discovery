package com.nepxion.discovery.plugin.admincenter.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nepxion.discovery.plugin.framework.cache.PluginCache;

@ManagedResource(description = "Discovery endpoint")
public class AdminEndpoint implements MvcEndpoint, ApplicationContextAware, EnvironmentAware {
    private ConfigurableApplicationContext context;
    private Environment environment;

    @Autowired
    private PluginCache pluginCache;

    @RequestMapping(path = "filter", method = RequestMethod.GET)
    @ManagedOperation
    public Object filter(@RequestParam("serviceId") String serviceId, @RequestParam("ip") String ip) {
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