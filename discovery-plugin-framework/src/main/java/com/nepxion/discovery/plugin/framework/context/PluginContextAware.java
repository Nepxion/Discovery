package com.nepxion.discovery.plugin.framework.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

import com.nepxion.discovery.plugin.framework.constant.PluginConstant;

public class PluginContextAware implements ApplicationContextAware {
    private Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.environment = applicationContext.getEnvironment();
    }

    public Boolean isRegisterControlEnabled() {
        return isRegisterControlEnabled(environment);
    }

    public Boolean isDiscoveryControlEnabled() {
        return isDiscoveryControlEnabled(environment);
    }

    public Boolean isRemoteConfigEnabled() {
        return isRemoteConfigEnabled(environment);
    }

    public static Boolean isRegisterControlEnabled(Environment environment) {
        return environment.getProperty(PluginConstant.SPRING_APPLICATION_REGISTER_CONTROL_ENABLED, Boolean.class, Boolean.TRUE);
    }

    public static Boolean isDiscoveryControlEnabled(Environment environment) {
        return environment.getProperty(PluginConstant.SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED, Boolean.class, Boolean.TRUE);
    }

    public static Boolean isRemoteConfigEnabled(Environment environment) {
        return environment.getProperty(PluginConstant.SPRING_APPLICATION_DISCOVERY_REMOTE_CONFIG_ENABLED, Boolean.class, Boolean.TRUE);
    }

    public Environment getEnvironment() {
        return environment;
    }
}