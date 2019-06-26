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

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.nepxion.banner.NepxionBanner;
import com.nepxion.discovery.common.nacos.constant.NacosConstant;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;
import com.nepxion.discovery.plugin.configcenter.nacos.adapter.NacosConfigAdapter;
import com.taobao.text.Color;

@Configuration
public class NacosConfigAutoConfiguration {
    static {
        /*String bannerShown = System.getProperty(BannerConstant.BANNER_SHOWN, "true");
        if (Boolean.valueOf(bannerShown)) {
            System.out.println("");
            System.out.println("╔═╗ ╔╗");
            System.out.println("║║╚╗║║");
            System.out.println("║╔╗╚╝╠══╦══╦══╦══╗");
            System.out.println("║║╚╗║║╔╗║╔═╣╔╗║══╣");
            System.out.println("║║ ║║║╔╗║╚═╣╚╝╠══║");
            System.out.println("╚╝ ╚═╩╝╚╩══╩══╩══╝");
            System.out.println(NacosConstant.TYPE + " Config");
            System.out.println("");
        }*/

        LogoBanner logoBanner = new LogoBanner(NacosConfigAutoConfiguration.class, "/com/nepxion/nacos/resource/logo.txt", "Welcome to Nepxion", 5, 5, new Color[] { Color.red, Color.green, Color.cyan, Color.blue, Color.yellow }, true);

        NepxionBanner.show(logoBanner, new Description("Config:", NacosConstant.TYPE, 0, 1), new Description(BannerConstant.GITHUB + ":", BannerConstant.NEPXION_GITHUB + "/Discovery", 0, 1));
    }

    @Bean
    public ConfigAdapter configAdapter() {
        return new NacosConfigAdapter();
    }
}