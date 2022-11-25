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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.nepxion.banner.NepxionBanner;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.property.DiscoveryProperties;
import com.nepxion.discovery.plugin.framework.decorator.DiscoveryClientDecorator;
import com.nepxion.discovery.plugin.framework.generator.GitGenerator;
import com.nepxion.discovery.plugin.framework.generator.GroupGenerator;
import com.taobao.text.Color;

public abstract class PluginApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {
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

            initializeDefaultProperties(applicationContext);
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
    }

    private void initializeDefaultProperties(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String path = PluginContextAware.getDefaultPropertiesPath(environment);

        try {
            DiscoveryProperties properties = new DiscoveryProperties(path, DiscoveryConstant.ENCODING_GBK, DiscoveryConstant.ENCODING_UTF_8);
            Map<String, String> propertiesMap = properties.getMap();
            for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                // 如果已经设置，则尊重已经设置的值
                if (environment.getProperty(key) == null && System.getProperty(key) == null && System.getenv(key.toUpperCase()) == null) {
                    System.setProperty(key, value);
                }
            }

            LOG.info("{} is loaded...", path);
        } catch (IOException e) {

        }
    }

    protected String getPrefixGroup(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        Boolean isGroupGeneratorEnabled = PluginContextAware.isGroupGeneratorEnabled(environment);
        if (isGroupGeneratorEnabled) {
            GroupGenerator groupGenerator = applicationContext.getBean(GroupGenerator.class);

            return groupGenerator.getGroup();
        }

        return null;
    }

    protected String getGitVersion(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        Boolean isGitGeneratorEnabled = PluginContextAware.isGitGeneratorEnabled(environment);
        if (isGitGeneratorEnabled) {
            GitGenerator gitGenerator = applicationContext.getBean(GitGenerator.class);

            return gitGenerator.getVersion();
        }

        return null;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    protected abstract Object afterInitialization(ConfigurableApplicationContext applicationContext, Object bean, String beanName) throws BeansException;
}