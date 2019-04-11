package com.nepxion.discovery.plugin.framework.configuration;

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

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.ConsulAdapter;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.constant.ConsulConstant;

@Configuration
@RibbonClients(defaultConfiguration = { PluginLoadBalanceConfiguration.class, ConsulLoadBalanceConfiguration.class })
public class ConsulAutoConfiguration {
    static {
        String logoShown = System.getProperty("nepxion.logo.shown", "true");
        if (Boolean.valueOf(logoShown)) {
            System.out.println("");
            System.out.println("╔═══╗");
            System.out.println("╚╗╔╗║");
            System.out.println(" ║║║╠╦══╦══╦══╦╗╔╦══╦═╦╗ ╔╗");
            System.out.println(" ║║║╠╣══╣╔═╣╔╗║╚╝║║═╣╔╣║ ║║");
            System.out.println("╔╝╚╝║╠══║╚═╣╚╝╠╗╔╣║═╣║║╚═╝║");
            System.out.println("╚═══╩╩══╩══╩══╝╚╝╚══╩╝╚═╗╔╝");
            System.out.println("                      ╔═╝║");
            System.out.println("                      ╚══╝");
            System.out.println("Nepxion Discovery - " + ConsulConstant.DISCOVERY_PLUGIN + "  v" + DiscoveryConstant.DISCOVERY_VERSION);
            System.out.println("");
        }
    }

    @Bean
    public PluginAdapter pluginAdapter() {
        return new ConsulAdapter();
    }
}