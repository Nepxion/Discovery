package com.nepxion.discovery.plugin.strategy.sentinel.apollo.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Weihua Wang
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.nepxion.banner.NepxionBanner;
import com.nepxion.discovery.common.apollo.constant.ApolloConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.apollo.loader.SentinelApolloRuleLoader;
import com.nepxion.discovery.plugin.strategy.sentinel.configuration.SentinelStrategyAutoConfiguration;
import com.nepxion.discovery.plugin.strategy.sentinel.constant.SentinelStrategyConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.loader.SentinelRuleLoader;
import com.taobao.text.Color;

@Configuration
@ConditionalOnProperty(value = SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_ENABLED, matchIfMissing = false)
@AutoConfigureAfter(SentinelStrategyAutoConfiguration.class)
public class SentinelStrategyApolloAutoConfiguration {
    static {
        LogoBanner logoBanner = new LogoBanner(SentinelStrategyApolloAutoConfiguration.class, "/com/nepxion/sentinel/resource/logo.txt", "Welcome to Nepxion", 8, 5, new Color[] { Color.red, Color.green, Color.cyan, Color.blue, Color.yellow, Color.magenta, Color.red, Color.green }, true);

        NepxionBanner.show(logoBanner, new Description("Protect:", SentinelStrategyConstant.SENTINEL_TYPE, 0, 1), new Description("Config:", ApolloConstant.APOLLO_TYPE, 0, 1), new Description(BannerConstant.GITHUB + ":", BannerConstant.NEPXION_GITHUB + "/Discovery", 0, 1));
    }

    @Bean
    public SentinelRuleLoader sentinelRuleLoader() {
        return new SentinelApolloRuleLoader();
    }
}