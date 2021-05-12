package com.nepxion.discovery.plugin.strategy.sentinel.datasource.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Weihua Wang
 * @author Haojun Ren
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
import com.nepxion.discovery.common.apollo.proccessor.ApolloProcessor;
import com.nepxion.discovery.common.consul.proccessor.ConsulProcessor;
import com.nepxion.discovery.common.etcd.proccessor.EtcdProcessor;
import com.nepxion.discovery.common.nacos.proccessor.NacosProcessor;
import com.nepxion.discovery.common.redis.proccessor.RedisProcessor;
import com.nepxion.discovery.common.zookeeper.proccessor.ZookeeperProcessor;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.constant.SentinelStrategyDatasourceConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.entity.SentinelStrategyRuleType;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.loader.SentinelStrategyRuleLoader;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.parser.SentinelStrategyAuthorityRuleParser;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.parser.SentinelStrategyDegradeRuleParser;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.parser.SentinelStrategyFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.parser.SentinelStrategyParamFlowRuleParser;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.parser.SentinelStrategySystemRuleParser;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.processor.SentinelStrategyRuleApolloProcessor;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.processor.SentinelStrategyRuleConsulProcessor;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.processor.SentinelStrategyRuleEtcdProcessor;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.processor.SentinelStrategyRuleNacosProcessor;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.processor.SentinelStrategyRuleRedisProcessor;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.processor.SentinelStrategyRuleZookeeperProcessor;
import com.taobao.text.Color;

@Configuration
@ConditionalOnProperty(value = SentinelStrategyDatasourceConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_DATASOURCE_ENABLED, matchIfMissing = false)
public class SentinelStrategyDatasourceAutoConfiguration {
    static {
        LogoBanner logoBanner = new LogoBanner(SentinelStrategyDatasourceAutoConfiguration.class, "/com/nepxion/sentinel/resource/logo.txt", "Welcome to Nepxion", 8, 5, new Color[] { Color.red, Color.green, Color.cyan, Color.blue, Color.yellow, Color.magenta, Color.red, Color.green }, true);

        NepxionBanner.show(logoBanner, new Description("Protect:", SentinelStrategyDatasourceConstant.SENTINEL_TYPE, 0, 1), new Description(BannerConstant.GITHUB + ":", BannerConstant.NEPXION_GITHUB + "/Discovery", 0, 1));
    }

    @Bean
    public SentinelStrategyFlowRuleParser sentinelStrategyFlowRuleParser() {
        return new SentinelStrategyFlowRuleParser();
    }

    @Bean
    public SentinelStrategyDegradeRuleParser sentinelStrategyDegradeRuleParser() {
        return new SentinelStrategyDegradeRuleParser();
    }

    @Bean
    public SentinelStrategyAuthorityRuleParser sentinelStrategyAuthorityRuleParser() {
        return new SentinelStrategyAuthorityRuleParser();
    }

    @Bean
    public SentinelStrategySystemRuleParser sentinelStrategySystemRuleParser() {
        return new SentinelStrategySystemRuleParser();
    }

    @Bean
    public SentinelStrategyParamFlowRuleParser sentinelStrategyParamFlowRuleParser() {
        return new SentinelStrategyParamFlowRuleParser();
    }

    @Bean
    public SentinelStrategyRuleLoader sentinelStrategyRuleLoader() {
        return new SentinelStrategyRuleLoader();
    }

    @ConditionalOnClass(NacosProcessor.class)
    protected static class SentinelStrategyNacosConfiguration {
        @Bean
        public NacosProcessor sentinelStrategyFlowRuleNacosProcessor() {
            return new SentinelStrategyRuleNacosProcessor(SentinelStrategyRuleType.FLOW);
        }

        @Bean
        public NacosProcessor sentinelStrategyDegradeRuleNacosProcessor() {
            return new SentinelStrategyRuleNacosProcessor(SentinelStrategyRuleType.DEGRADE);
        }

        @Bean
        public NacosProcessor sentinelStrategyAuthorityRuleNacosProcessor() {
            return new SentinelStrategyRuleNacosProcessor(SentinelStrategyRuleType.AUTHORITY);
        }

        @Bean
        public NacosProcessor sentinelStrategySystemRuleNacosProcessor() {
            return new SentinelStrategyRuleNacosProcessor(SentinelStrategyRuleType.SYSTEM);
        }

        @Bean
        public NacosProcessor sentinelStrategyParamFlowRuleNacosProcessor() {
            return new SentinelStrategyRuleNacosProcessor(SentinelStrategyRuleType.PARAM_FLOW);
        }
    }

    @ConditionalOnClass(ApolloProcessor.class)
    protected static class SentinelStrategyApolloConfiguration {
        @Bean
        public ApolloProcessor sentinelStrategyFlowRuleApolloProcessor() {
            return new SentinelStrategyRuleApolloProcessor(SentinelStrategyRuleType.FLOW);
        }

        @Bean
        public ApolloProcessor sentinelStrategyDegradeRuleApolloProcessor() {
            return new SentinelStrategyRuleApolloProcessor(SentinelStrategyRuleType.DEGRADE);
        }

        @Bean
        public ApolloProcessor sentinelStrategyAuthorityRuleApolloProcessor() {
            return new SentinelStrategyRuleApolloProcessor(SentinelStrategyRuleType.AUTHORITY);
        }

        @Bean
        public ApolloProcessor sentinelStrategySystemRuleApolloProcessor() {
            return new SentinelStrategyRuleApolloProcessor(SentinelStrategyRuleType.SYSTEM);
        }

        @Bean
        public ApolloProcessor sentinelStrategyParamFlowRuleApolloProcessor() {
            return new SentinelStrategyRuleApolloProcessor(SentinelStrategyRuleType.PARAM_FLOW);
        }
    }

    @ConditionalOnClass(RedisProcessor.class)
    protected static class SentinelStrategyRedisConfiguration {
        @Bean
        public RedisProcessor sentinelStrategyFlowRuleRedisProcessor() {
            return new SentinelStrategyRuleRedisProcessor(SentinelStrategyRuleType.FLOW);
        }

        @Bean
        public RedisProcessor sentinelStrategyDegradeRuleRedisProcessor() {
            return new SentinelStrategyRuleRedisProcessor(SentinelStrategyRuleType.DEGRADE);
        }

        @Bean
        public RedisProcessor sentinelStrategyAuthorityRuleRedisProcessor() {
            return new SentinelStrategyRuleRedisProcessor(SentinelStrategyRuleType.AUTHORITY);
        }

        @Bean
        public RedisProcessor sentinelStrategySystemRuleRedisProcessor() {
            return new SentinelStrategyRuleRedisProcessor(SentinelStrategyRuleType.SYSTEM);
        }

        @Bean
        public RedisProcessor sentinelStrategyParamFlowRuleRedisProcessor() {
            return new SentinelStrategyRuleRedisProcessor(SentinelStrategyRuleType.PARAM_FLOW);
        }
    }

    @ConditionalOnClass(ZookeeperProcessor.class)
    protected static class SentinelStrategyZookeeperConfiguration {
        @Bean
        public ZookeeperProcessor sentinelStrategyFlowRuleZookeeperProcessor() {
            return new SentinelStrategyRuleZookeeperProcessor(SentinelStrategyRuleType.FLOW);
        }

        @Bean
        public ZookeeperProcessor sentinelStrategyDegradeRuleZookeeperProcessor() {
            return new SentinelStrategyRuleZookeeperProcessor(SentinelStrategyRuleType.DEGRADE);
        }

        @Bean
        public ZookeeperProcessor sentinelStrategyAuthorityRuleZookeeperProcessor() {
            return new SentinelStrategyRuleZookeeperProcessor(SentinelStrategyRuleType.AUTHORITY);
        }

        @Bean
        public ZookeeperProcessor sentinelStrategySystemRuleZookeeperProcessor() {
            return new SentinelStrategyRuleZookeeperProcessor(SentinelStrategyRuleType.SYSTEM);
        }

        @Bean
        public ZookeeperProcessor sentinelStrategyParamFlowRuleZookeeperProcessor() {
            return new SentinelStrategyRuleZookeeperProcessor(SentinelStrategyRuleType.PARAM_FLOW);
        }
    }

    @ConditionalOnClass(ConsulProcessor.class)
    protected static class SentinelStrategyConsulConfiguration {
        @Bean
        public ConsulProcessor sentinelStrategyFlowRuleConsulProcessor() {
            return new SentinelStrategyRuleConsulProcessor(SentinelStrategyRuleType.FLOW);
        }

        @Bean
        public ConsulProcessor sentinelStrategyDegradeRuleConsulProcessor() {
            return new SentinelStrategyRuleConsulProcessor(SentinelStrategyRuleType.DEGRADE);
        }

        @Bean
        public ConsulProcessor sentinelStrategyAuthorityRuleConsulProcessor() {
            return new SentinelStrategyRuleConsulProcessor(SentinelStrategyRuleType.AUTHORITY);
        }

        @Bean
        public ConsulProcessor sentinelStrategySystemRuleConsulProcessor() {
            return new SentinelStrategyRuleConsulProcessor(SentinelStrategyRuleType.SYSTEM);
        }

        @Bean
        public ConsulProcessor sentinelStrategyParamFlowRuleConsulProcessor() {
            return new SentinelStrategyRuleConsulProcessor(SentinelStrategyRuleType.PARAM_FLOW);
        }
    }

    @ConditionalOnClass(EtcdProcessor.class)
    protected static class SentinelStrategyEtcdConfiguration {
        @Bean
        public EtcdProcessor sentinelStrategyFlowRuleEtcdProcessor() {
            return new SentinelStrategyRuleEtcdProcessor(SentinelStrategyRuleType.FLOW);
        }

        @Bean
        public EtcdProcessor sentinelStrategyDegradeRuleEtcdProcessor() {
            return new SentinelStrategyRuleEtcdProcessor(SentinelStrategyRuleType.DEGRADE);
        }

        @Bean
        public EtcdProcessor sentinelStrategyAuthorityRuleEtcdProcessor() {
            return new SentinelStrategyRuleEtcdProcessor(SentinelStrategyRuleType.AUTHORITY);
        }

        @Bean
        public EtcdProcessor sentinelStrategySystemRuleEtcdProcessor() {
            return new SentinelStrategyRuleEtcdProcessor(SentinelStrategyRuleType.SYSTEM);
        }

        @Bean
        public EtcdProcessor sentinelStrategyParamFlowRuleEtcdProcessor() {
            return new SentinelStrategyRuleEtcdProcessor(SentinelStrategyRuleType.PARAM_FLOW);
        }
    }
}