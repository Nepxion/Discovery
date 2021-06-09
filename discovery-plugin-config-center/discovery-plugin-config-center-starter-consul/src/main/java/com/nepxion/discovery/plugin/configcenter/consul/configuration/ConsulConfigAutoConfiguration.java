package com.nepxion.discovery.plugin.configcenter.consul.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Congwei Xu
 * @version 1.0
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.nepxion.banner.NepxionBanner;
import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.plugin.configcenter.adapter.ConfigAdapter;
import com.nepxion.discovery.plugin.configcenter.consul.adapter.ConsulConfigAdapter;
import com.taobao.text.Color;

@Configuration
public class ConsulConfigAutoConfiguration {
    static {
        LogoBanner logoBanner = new LogoBanner(ConsulConfigAutoConfiguration.class, "/com/nepxion/consul/resource/logo.txt", "Welcome to Nepxion", 6, 5, new Color[] { Color.red, Color.green, Color.cyan, Color.blue, Color.yellow, Color.magenta }, true);
        NepxionBanner.show(logoBanner, new Description("Config:", ConfigType.CONSUL.toString(), 0, 1), new Description(BannerConstant.GITHUB + ":", BannerConstant.NEPXION_GITHUB + "/Discovery", 0, 1));
    }

    @Bean
    public ConfigAdapter configAdapter() {
        return new ConsulConfigAdapter();
    }
}