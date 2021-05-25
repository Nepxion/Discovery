package com.nepxion.discovery.common.apollo.configuration;

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
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.nepxion.discovery.common.apollo.constant.ApolloConstant;
import com.nepxion.discovery.common.apollo.operation.ApolloOperation;

@Configuration
public class ApolloAutoConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    @ConditionalOnMissingBean
    public Config apolloConfig() {
        String namespace = getNamespace(environment);

        return ConfigService.getConfig(namespace);
    }

    @Bean
    public ApolloOperation apolloOperation() {
        return new ApolloOperation();
    }

    public static String getNamespace(Environment environment) {
        String namespace = environment.getProperty(ApolloConstant.APOLLO_PLUGIN_NAMESPACE);
        if (StringUtils.isEmpty(namespace) || namespace.contains(ApolloConstant.SEPARATE)) {
            namespace = environment.getProperty(ApolloConstant.APOLLO_BOOTSTRAP_NAMESPACES);
        }
        if (StringUtils.isEmpty(namespace) || namespace.contains(ApolloConstant.SEPARATE)) {
            namespace = ApolloConstant.NAMESPACE_APPLICATION;
        }

        return namespace;
    }
}