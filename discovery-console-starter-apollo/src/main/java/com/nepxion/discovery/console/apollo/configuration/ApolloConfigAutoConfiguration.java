package com.nepxion.discovery.console.apollo.configuration;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.nepxion.discovery.console.adapter.ConfigAdapter;
import com.nepxion.discovery.console.apollo.adapter.ApolloConfigAdapter;
import com.nepxion.discovery.console.apollo.constant.ApolloConstant;

@Configuration
public class ApolloConfigAutoConfiguration {
    static {
        System.out.println("");
        System.out.println("╔═╗ ╔╗");
        System.out.println("║║╚╗║║");
        System.out.println("║╔╗╚╝╠══╦══╦══╦══╗");
        System.out.println("║║╚╗║║╔╗║╔═╣╔╗║══╣");
        System.out.println("║║ ║║║╔╗║╚═╣╚╝╠══║");
        System.out.println("╚╝ ╚═╩╝╚╩══╩══╩══╝");
        System.out.println(ApolloConstant.TYPE + " Config");
        System.out.println("");
    }

    @Autowired
    private Environment environment;

    @Bean
    public ApolloOpenApiClient openApiClient() {
        String portalUrl = environment.getProperty(ApolloConstant.APOLLO_PORTAL_URL);
        if (StringUtils.isEmpty(portalUrl)) {
            throw new IllegalArgumentException(ApolloConstant.APOLLO_PORTAL_URL + " can't be null or empty");
        }

        String token = environment.getProperty(ApolloConstant.APOLLO_TOKEN);
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException(ApolloConstant.APOLLO_TOKEN + " can't be null or empty");
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