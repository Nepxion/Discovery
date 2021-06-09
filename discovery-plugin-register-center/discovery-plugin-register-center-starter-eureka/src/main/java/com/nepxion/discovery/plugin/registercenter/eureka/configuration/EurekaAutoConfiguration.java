package com.nepxion.discovery.plugin.registercenter.eureka.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.nepxion.banner.NepxionBanner;
import com.nepxion.discovery.common.entity.DiscoveryType;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.configuration.PluginLoadBalanceConfiguration;
import com.nepxion.discovery.plugin.registercenter.eureka.adapter.EurekaAdapter;
import com.taobao.text.Color;

@Configuration
@RibbonClients(defaultConfiguration = { PluginLoadBalanceConfiguration.class, EurekaLoadBalanceConfiguration.class })
public class EurekaAutoConfiguration {
    static {
        /*String bannerShown = System.getProperty(BannerConstant.BANNER_SHOWN, "true");
        if (Boolean.valueOf(bannerShown)) {
            System.out.println("");
            System.out.println("╔═══╗       ╔╗");
            System.out.println("║╔══╝       ║║");
            System.out.println("║╚══╦╗╔╦═╦══╣║╔╦══╗");
            System.out.println("║╔══╣║║║╔╣║═╣╚╝╣╔╗║");
            System.out.println("║╚══╣╚╝║║║║═╣╔╗╣╔╗║");
            System.out.println("╚═══╩══╩╝╚══╩╝╚╩╝╚╝");
            System.out.println(EurekaConstant.DISCOVERY_PLUGIN + " Discovery");
            System.out.println("");
        }*/

        LogoBanner logoBanner = new LogoBanner(EurekaAutoConfiguration.class, "/com/nepxion/eureka/resource/logo.txt", "Welcome to Nepxion", 6, 5, new Color[] { Color.red, Color.green, Color.cyan, Color.blue, Color.yellow, Color.magenta }, true);

        NepxionBanner.show(logoBanner, new Description("Discovery:", DiscoveryType.EUREKA.toString(), 0, 1), new Description(BannerConstant.GITHUB + ":", BannerConstant.NEPXION_GITHUB + "/Discovery", 0, 1));
    }

    @Bean
    public PluginAdapter pluginAdapter() {
        return new EurekaAdapter();
    }
}