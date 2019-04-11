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

import com.nepxion.discovery.common.redis.constant.RedisConstant;
import com.nepxion.discovery.console.adapter.ConfigAdapter;
import com.nepxion.discovery.console.redis.adapter.RedisConfigAdapter;

@Configuration
public class RedisConfigAutoConfiguration {
    static {
        String logoShown = System.getProperty("nepxion.logo.shown", "true");
        if (Boolean.valueOf(logoShown)) {
            System.out.println("");
            System.out.println("╔═══╗    ╔╗");
            System.out.println("║╔═╗║    ║║");
            System.out.println("║╚═╝╠══╦═╝╠╦══╗");
            System.out.println("║╔╗╔╣║═╣╔╗╠╣══╣");
            System.out.println("║║║╚╣║═╣╚╝║╠══║");
            System.out.println("╚╝╚═╩══╩══╩╩══╝");
            System.out.println(RedisConstant.TYPE + " Config");
            System.out.println("");
        }
    }

    @Bean
    public ConfigAdapter configAdapter() {
        return new RedisConfigAdapter();
    }
}