package com.nepxion.discovery.plugin.strategy.opentracing.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.nepxion.banner.NepxionBanner;
import com.nepxion.discovery.common.entity.TracingType;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.monitor.StrategyTracer;
import com.nepxion.discovery.plugin.strategy.opentracing.monitor.OpenTracingStrategyTracer;
import com.taobao.text.Color;

@Configuration
public class OpenTracingStrategyAutoConfiguration {
    static {
        LogoBanner logoBanner = new LogoBanner(OpenTracingStrategyAutoConfiguration.class, "/com/nepxion/opentracing/resource/logo.txt", "Welcome to Nepxion", 8, 5, new Color[] { Color.red, Color.green, Color.cyan, Color.blue, Color.yellow, Color.magenta, Color.red, Color.green }, true);

        NepxionBanner.show(logoBanner, new Description("Tracing:", TracingType.OPENTRACING.toString(), 0, 1), new Description(BannerConstant.GITHUB + ":", BannerConstant.NEPXION_GITHUB + "/Discovery", 0, 1));
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, matchIfMissing = false)
    public StrategyTracer strategyTracer() {
        return new OpenTracingStrategyTracer();
    }
}