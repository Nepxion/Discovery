package com.nepxion.discovery.plugin.framework.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public class PluginContextAware implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.environment = applicationContext.getEnvironment();
    }

    public Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    public Object getBean(String name, Object... args) throws BeansException {
        return applicationContext.getBean(name, args);
    }

    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }

    public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
        return applicationContext.getBean(requiredType, args);
    }

    public boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isPrototype(name);
    }

    public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
        return applicationContext.isTypeMatch(name, typeToMatch);
    }

    public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
        return applicationContext.isTypeMatch(name, typeToMatch);
    }

    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }

    public String[] getAliases(String name) {
        return applicationContext.getAliases(name);
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Boolean isRegisterControlEnabled() {
        return isRegisterControlEnabled(environment);
    }

    public Boolean isDiscoveryControlEnabled() {
        return isDiscoveryControlEnabled(environment);
    }

    public Boolean isConfigRestControlEnabled() {
        return isConfigRestControlEnabled(environment);
    }

    public String getConfigFormat() {
        return getConfigFormat(environment);
    }

    public String getConfigPath() {
        return getConfigPath(environment);
    }

    public String getGroupKey() {
        return getGroupKey(environment);
    }

    public String getContextPath() {
        return getContextPath(environment);
    }

    public static Boolean isRegisterControlEnabled(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_REGISTER_CONTROL_ENABLED, Boolean.class, Boolean.TRUE);
    }

    public static Boolean isDiscoveryControlEnabled(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED, Boolean.class, Boolean.TRUE);
    }

    public static Boolean isConfigRestControlEnabled(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_CONFIG_REST_CONTROL_ENABLED, Boolean.class, Boolean.TRUE);
    }

    public static String getConfigFormat(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_CONFIG_FORMAT, String.class, DiscoveryConstant.XML_FORMAT);
    }

    public static String getConfigPath(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_CONFIG_PATH, String.class, StringUtils.equals(getConfigFormat(environment), DiscoveryConstant.XML_FORMAT) ? DiscoveryConstant.PREFIX_CLASSPATH + DiscoveryConstant.RULE + "." + DiscoveryConstant.XML_FORMAT : DiscoveryConstant.PREFIX_CLASSPATH + DiscoveryConstant.RULE + "." + DiscoveryConstant.JSON_FORMAT);
    }

    public static String getGroupKey(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY, String.class, DiscoveryConstant.GROUP);
    }

    public static String getContextPath(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_CONTEXT_PATH, String.class, "/");
    }
}