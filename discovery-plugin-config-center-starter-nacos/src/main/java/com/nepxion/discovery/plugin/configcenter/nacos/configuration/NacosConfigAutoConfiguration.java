package com.nepxion.discovery.plugin.configcenter.nacos.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.common.nacos.constant.NacosConstant;
import com.nepxion.discovery.plugin.configcenter.nacos.adapter.NacosConfigAdapter;

@Configuration
public class NacosConfigAutoConfiguration {
    static {
        System.out.println("");
        System.out.println("╔═╗ ╔╗");
        System.out.println("║║╚╗║║");
        System.out.println("║╔╗╚╝╠══╦══╦══╦══╗");
        System.out.println("║║╚╗║║╔╗║╔═╣╔╗║══╣");
        System.out.println("║║ ║║║╔╗║╚═╣╚╝╠══║");
        System.out.println("╚╝ ╚═╩╝╚╩══╩══╩══╝");
        System.out.println(NacosConstant.TYPE + " Config");
        System.out.println("");
    }

    @Bean
    public NacosConfigAdapter configAdapter() {
        return new NacosConfigAdapter();
    }
}