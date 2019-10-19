package com.nepxion.discovery.plugin.framework.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.nepxion.banner.NepxionBanner;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.property.DiscoveryProperties;
import com.nepxion.discovery.plugin.framework.decorator.DiscoveryClientDecorator;
import com.nepxion.discovery.plugin.framework.generator.GitGenerator;
import com.taobao.text.Color;

public abstract class PluginApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final Logger LOG = LoggerFactory.getLogger(PluginApplicationContextInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!(applicationContext instanceof AnnotationConfigApplicationContext)) {
            /*String bannerShown = System.getProperty(BannerConstant.BANNER_SHOWN, "true");
            if (Boolean.valueOf(bannerShown)) {
                System.out.println("");
                System.out.println("╔═══╗");
                System.out.println("╚╗╔╗║");
                System.out.println(" ║║║╠╦══╦══╦══╦╗╔╦══╦═╦╗ ╔╗");
                System.out.println(" ║║║╠╣══╣╔═╣╔╗║╚╝║║═╣╔╣║ ║║");
                System.out.println("╔╝╚╝║╠══║╚═╣╚╝╠╗╔╣║═╣║║╚═╝║");
                System.out.println("╚═══╩╩══╩══╩══╝╚╝╚══╩╝╚═╗╔╝");
                System.out.println("                      ╔═╝║");
                System.out.println("                      ╚══╝");
                System.out.println("Nepxion Discovery  v" + DiscoveryConstant.DISCOVERY_VERSION);
                System.out.println("");
            }*/

            LogoBanner logoBanner = new LogoBanner(PluginApplicationContextInitializer.class, "/com/nepxion/discovery/resource/logo.txt", "Welcome to Nepxion", 9, 5, new Color[] { Color.red, Color.green, Color.cyan, Color.blue, Color.yellow, Color.magenta, Color.red, Color.green, Color.cyan }, true);

            NepxionBanner.show(logoBanner, new Description(BannerConstant.VERSION + ":", DiscoveryConstant.DISCOVERY_VERSION, 0, 1), new Description(BannerConstant.GITHUB + ":", BannerConstant.NEPXION_GITHUB + "/Discovery", 0, 1));

            boolean servletWebServerEnabled = applicationContext.getClass().getName().endsWith(DiscoveryConstant.ANNOTATION_CONFIG_SERVLET_WEB_SERVER_APPLICATION_CONTEXT);
            System.setProperty(DiscoveryConstant.SPRING_APPLICATION_SERVLET_WEB_SERVER_ENABLED, Boolean.toString(servletWebServerEnabled));
        }

        applicationContext.getBeanFactory().addBeanPostProcessor(new InstantiationAwareBeanPostProcessorAdapter() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof DiscoveryClient) {
                    DiscoveryClient discoveryClient = (DiscoveryClient) bean;

                    return new DiscoveryClientDecorator(discoveryClient, applicationContext);
                } else {
                    return afterInitialization(applicationContext, bean, beanName);
                }
            }
        });

        initializeDefaultProperties(applicationContext);
    }

    private void initializeDefaultProperties(ConfigurableApplicationContext applicationContext) {
        if (applicationContext instanceof AnnotationConfigApplicationContext) {
            return;
        }

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String path = PluginContextAware.getDefaultPropertiesPath(environment);

        try {
            DiscoveryProperties properties = new DiscoveryProperties(path, DiscoveryConstant.ENCODING_GBK, DiscoveryConstant.ENCODING_UTF_8);
            Map<String, String> propertiesMap = properties.getMap();
            for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                String property = environment.getProperty(key);
                if (StringUtils.isEmpty(property)) {
                    System.setProperty(key, value);
                }
            }

            LOG.info("{} is loaded", path);
        } catch (IOException e) {

        }
    }

    protected String getGitVersion(ConfigurableApplicationContext applicationContext) {
        try {
            GitGenerator gitGenerator = applicationContext.getBean(GitGenerator.class);
            String versionKey = gitGenerator.getGitVersionKey();
            String version = gitGenerator.getVersion();

            LOG.info("--------------------------------------------------");
            if (StringUtils.isNotEmpty(version)) {
                LOG.info("Use {}={} as metadata version", versionKey, version);
            } else {
                LOG.warn("Not found value of {}, use default metadata version setting", versionKey);
            }
            LOG.info("--------------------------------------------------");

            return version;
        } catch (Exception e) {

        }

        return null;
    }

    protected abstract Object afterInitialization(ConfigurableApplicationContext applicationContext, Object bean, String beanName) throws BeansException;
}