package com.nepxion.discovery.plugin.opentracing.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.nepxion.banner.NepxionBanner;
import com.nepxion.discovery.plugin.opentracing.constant.StrategyOpentracingConstant;
import com.nepxion.discovery.plugin.opentracing.operation.StrategyOpentracingOperation;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.taobao.text.Color;

@Configuration
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTROL_ENABLED, matchIfMissing = true)
public class StrategyOpentracingAutoConfiguration {
    static {
        LogoBanner logoBanner = new LogoBanner(StrategyOpentracingAutoConfiguration.class, "/com/nepxion/opentracing/resource/logo.txt", "Welcome to Nepxion", 9, 5, new Color[] { Color.red, Color.green, Color.cyan, Color.blue, Color.yellow, Color.magenta, Color.red, Color.green, Color.cyan }, true);

        NepxionBanner.show(logoBanner, new Description("Tracer:", StrategyOpentracingConstant.OPENTRACING_TYPE, 0, 1), new Description(BannerConstant.GITHUB + ":", BannerConstant.NEPXION_GITHUB + "/Discovery", 0, 1));
    }

    @Bean
    @ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACE_ENABLED, matchIfMissing = false)
    public StrategyOpentracingOperation strategyOpentracingOperation() {
        return new StrategyOpentracingOperation();
    }
}