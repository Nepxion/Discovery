package com.nepxion.discovery.console.redis.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author JiKai Sun
 * @version 1.0
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.nepxion.banner.NepxionBanner;
import com.nepxion.discovery.common.redis.constant.RedisConstant;
import com.nepxion.discovery.console.adapter.ConfigAdapter;
import com.nepxion.discovery.console.redis.adapter.RedisConfigAdapter;
import com.taobao.text.Color;

@Configuration
public class RedisConfigAutoConfiguration {
    static {
        /*String bannerShown = System.getProperty(BannerConstant.BANNER_SHOWN, "true");
        if (Boolean.valueOf(bannerShown)) {
            System.out.println("");
            System.out.println("╔═══╗    ╔╗");
            System.out.println("║╔═╗║    ║║");
            System.out.println("║╚═╝╠══╦═╝╠╦══╗");
            System.out.println("║╔╗╔╣║═╣╔╗╠╣══╣");
            System.out.println("║║║╚╣║═╣╚╝║╠══║");
            System.out.println("╚╝╚═╩══╩══╩╩══╝");
            System.out.println(RedisConstant.TYPE + " Config");
            System.out.println("");
        }*/

        LogoBanner logoBanner = new LogoBanner(RedisConfigAutoConfiguration.class, "/com/nepxion/redis/resource/logo.txt", "Welcome to Nepxion", 5, 5, new Color[] { Color.red, Color.green, Color.cyan, Color.blue, Color.yellow }, true);

        NepxionBanner.show(logoBanner, new Description("Config:", RedisConstant.REDIS_TYPE, 0, 1), new Description(BannerConstant.GITHUB + ":", BannerConstant.NEPXION_GITHUB + "/Discovery", 0, 1));
    }

    @Bean
    public ConfigAdapter configAdapter() {
        return new RedisConfigAdapter();
    }
}