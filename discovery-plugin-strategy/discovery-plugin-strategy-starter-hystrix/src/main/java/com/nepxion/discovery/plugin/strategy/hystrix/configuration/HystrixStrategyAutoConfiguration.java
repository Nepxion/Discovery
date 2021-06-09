package com.nepxion.discovery.plugin.strategy.hystrix.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Hao Huang
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.nepxion.banner.NepxionBanner;
import com.nepxion.discovery.common.entity.ProtectorType;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.hystrix.context.HystrixContextConcurrencyStrategy;
import com.netflix.hystrix.Hystrix;
import com.taobao.text.Color;

@Configuration
@ConditionalOnClass(Hystrix.class)
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_HYSTRIX_THREADLOCAL_SUPPORTED, matchIfMissing = false)
public class HystrixStrategyAutoConfiguration {
    static {
        LogoBanner logoBanner = new LogoBanner(HystrixStrategyAutoConfiguration.class, "/com/nepxion/hystrix/resource/logo.txt", "Welcome to Nepxion", 7, 5, new Color[] { Color.red, Color.green, Color.cyan, Color.blue, Color.yellow, Color.magenta, Color.red }, true);

        NepxionBanner.show(logoBanner, new Description("Protector:", ProtectorType.HYSTRIX.toString(), 0, 1), new Description(BannerConstant.GITHUB + ":", BannerConstant.NEPXION_GITHUB + "/Discovery", 0, 1));
    }

    @Bean
    public HystrixContextConcurrencyStrategy hystrixContextConcurrencyStrategy() {
        return new HystrixContextConcurrencyStrategy();
    }
}