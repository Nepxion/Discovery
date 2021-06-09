package com.nepxion.discovery.console.apollo.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.nepxion.banner.BannerConstant;
import com.nepxion.banner.Description;
import com.nepxion.banner.LogoBanner;
import com.nepxion.banner.NepxionBanner;
import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.console.adapter.ConfigAdapter;
import com.nepxion.discovery.console.apollo.adapter.ApolloConfigAdapter;
import com.nepxion.discovery.console.apollo.constant.ApolloConstant;
import com.taobao.text.Color;

@Configuration
public class ApolloConfigAutoConfiguration {
    static {
        /*String bannerShown = System.getProperty(BannerConstant.BANNER_SHOWN, "true");
        if (Boolean.valueOf(bannerShown)) {
            System.out.println("");
            System.out.println("╔═══╗     ╔╗╔╗");
            System.out.println("║╔═╗║     ║║║║");
            System.out.println("║║ ║╠══╦══╣║║║╔══╗");
            System.out.println("║╚═╝║╔╗║╔╗║║║║║╔╗║");
            System.out.println("║╔═╗║╚╝║╚╝║╚╣╚╣╚╝║");
            System.out.println("╚╝ ╚╣╔═╩══╩═╩═╩══╝");
            System.out.println("    ║║");
            System.out.println("    ╚╝");
            System.out.println(ApolloConstant.TYPE + " Config");
            System.out.println("");
        }*/

        LogoBanner logoBanner = new LogoBanner(ApolloConfigAutoConfiguration.class, "/com/nepxion/apollo/resource/logo.txt", "Welcome to Nepxion", 6, 5, new Color[] { Color.red, Color.green, Color.cyan, Color.blue, Color.yellow, Color.magenta }, true);

        NepxionBanner.show(logoBanner, new Description("Config:", ConfigType.APOLLO.toString(), 0, 1), new Description(BannerConstant.GITHUB + ":", BannerConstant.NEPXION_GITHUB + "/Discovery", 0, 1));
    }

    @Autowired
    private Environment environment;

    @Bean
    public ApolloOpenApiClient openApiClient() {
        String portalUrl = environment.getProperty(ApolloConstant.APOLLO_PORTAL_URL);
        if (StringUtils.isEmpty(portalUrl)) {
            throw new DiscoveryException(ApolloConstant.APOLLO_PORTAL_URL + " can't be null or empty");
        }

        String token = environment.getProperty(ApolloConstant.APOLLO_TOKEN);
        if (StringUtils.isEmpty(token)) {
            throw new DiscoveryException(ApolloConstant.APOLLO_TOKEN + " can't be null or empty");
        }

        int connectTimeout = environment.getProperty(ApolloConstant.APOLLO_CONNECT_TIMEOUT, Integer.class, ApolloConstant.DEFAULT_CONNECT_TIMEOUT);
        int readTimeout = environment.getProperty(ApolloConstant.APOLLO_READ_TIMEOUT, Integer.class, ApolloConstant.DEFAULT_READ_TIMEOUT);

        return ApolloOpenApiClient.newBuilder().withPortalUrl(portalUrl).withToken(token).withConnectTimeout(connectTimeout).withReadTimeout(readTimeout).build();
    }

    @Bean
    public ConfigAdapter configAdapter() {
        return new ApolloConfigAdapter();
    }
}