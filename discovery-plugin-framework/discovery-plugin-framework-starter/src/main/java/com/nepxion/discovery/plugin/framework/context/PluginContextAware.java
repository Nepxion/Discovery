package com.nepxion.discovery.plugin.framework.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Robin.G
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

    private static ApplicationContext staticApplicationContext;
    private static Environment staticEnvironment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.environment = applicationContext.getEnvironment();

        staticApplicationContext = applicationContext;
        staticEnvironment = applicationContext.getEnvironment();
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

    public static ApplicationContext getStaticApplicationContext() {
        return staticApplicationContext;
    }

    public static Environment getStaticEnvironment() {
        return staticEnvironment;
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

    public String getApplicationName() {
        return getApplicationName(environment);
    }

    public String getApplicationType() {
        return getApplicationType(environment);
    }

    public String getApplicationUUId() {
        return getApplicationUUId(environment);
    }

    public String getGroupKey() {
        return getGroupKey(environment);
    }

    public String getDefaultPropertiesPath() {
        return getDefaultPropertiesPath(environment);
    }

    public Boolean isGroupGeneratorEnabled() {
        return isGroupGeneratorEnabled(environment);
    }

    public Integer getGroupGeneratorLength() {
        return getGroupGeneratorLength(environment);
    }

    public String getGroupGeneratorCharacter() {
        return getGroupGeneratorCharacter(environment);
    }

    public Boolean isGitGeneratorEnabled() {
        return isGitGeneratorEnabled(environment);
    }

    public String getGitGeneratorPath() {
        return getGitGeneratorPath(environment);
    }

    public String getGitVersionKey() {
        return getGitVersionKey(environment);
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

    public static String getApplicationName(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_NAME, String.class, DiscoveryConstant.UNKNOWN);
    }

    public static String getApplicationType(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_TYPE, String.class, DiscoveryConstant.UNKNOWN);
    }

    public static String getApplicationGatewayType(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_GATEWAY_TYPE, String.class);
    }

    public static String getApplicationProtocol(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_PROTOCOL, String.class, "http");
    }

    public static String getApplicationUUId(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_UUID, String.class, DiscoveryConstant.UNKNOWN);
    }

    public static String getGroupKey(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY, String.class, DiscoveryConstant.GROUP);
    }

    public static String getDefaultPropertiesPath(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_DEFAULT_PROPERTIES_PATH, String.class, DiscoveryConstant.SPRING_APPLICATION_DEFAULT_PROPERTIES_PATH_VALUE + "." + DiscoveryConstant.PROPERTIES_FORMAT);
    }

    public static Boolean isGroupGeneratorEnabled(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_GROUP_GENERATOR_ENABLED, Boolean.class, Boolean.FALSE);
    }

    public static Integer getGroupGeneratorLength(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_GROUP_GENERATOR_LENGTH, Integer.class, -1);
    }

    public static String getGroupGeneratorCharacter(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_GROUP_GENERATOR_CHARACTER, String.class, StringUtils.EMPTY);
    }

    public static Boolean isGitGeneratorEnabled(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_GIT_GENERATOR_ENABLED, Boolean.class, Boolean.FALSE);
    }

    public static String getGitGeneratorPath(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_GIT_GENERATOR_PATH, String.class, DiscoveryConstant.PREFIX_CLASSPATH + DiscoveryConstant.GIT + "." + DiscoveryConstant.PROPERTIES_FORMAT);
    }

    public static String getGitVersionKey(Environment environment) {
        return environment.getProperty(DiscoveryConstant.SPRING_APPLICATION_GIT_VERSION_KEY, String.class, "{" + DiscoveryConstant.GIT_COMMIT_TIME + "}-{" + DiscoveryConstant.GIT_TOTAL_COMMIT_COUNT + "}");
    }

    public static String getContextPath(Environment environment) {
        return environment.getProperty(DiscoveryConstant.CONTEXT_PATH, String.class, "/");
    }
}