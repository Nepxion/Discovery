package com.nepxion.discovery.plugin.framework.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.nepxion.banner.NepxionBanner;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.decorator.DiscoveryClientDecorator;
import com.taobao.text.Color;

public abstract class PluginApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        String applicationContextClassName = applicationContext.getClass().getName();
        if (applicationContextClassName.endsWith(DiscoveryConstant.ANNOTATION_CONFIG_SERVLET_WEB_SERVER_APPLICATION_CONTEXT) || applicationContextClassName.endsWith(DiscoveryConstant.ANNOTATION_CONFIG_REACTIVE_WEB_SERVER_APPLICATION_CONTEXT)) {
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
        }

        if (applicationContextClassName.endsWith(DiscoveryConstant.ANNOTATION_CONFIG_SERVLET_WEB_SERVER_APPLICATION_CONTEXT)) {
            System.setProperty(DiscoveryConstant.SPRING_APPLICATION_SERVLET_WEB_SERVER_ENABLED, "true");
        }
        if (applicationContextClassName.endsWith(DiscoveryConstant.ANNOTATION_CONFIG_REACTIVE_WEB_SERVER_APPLICATION_CONTEXT)) {
            System.setProperty(DiscoveryConstant.SPRING_APPLICATION_REACTIVE_WEB_SERVER_ENABLED, "true");
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

    protected abstract Object afterInitialization(ConfigurableApplicationContext applicationContext, Object bean, String beanName) throws BeansException;
}