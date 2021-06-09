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
import com.nepxion.discovery.common.entity.ProtectorType;
import com.nepxion.discovery.common.entity.SentinelRuleType;
import com.nepxion.discovery.common.etcd.proccessor.EtcdProcessor;
import com.nepxion.discovery.common.nacos.proccessor.NacosProcessor;
import com.nepxion.discovery.common.redis.proccessor.RedisProcessor;
import com.nepxion.discovery.common.zookeeper.proccessor.ZookeeperProcessor;
import com.nepxion.discovery.plugin.strategy.sentinel.datasource.constant.SentinelStrategyDataSourceConstant;
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
@ConditionalOnProperty(value = SentinelStrategyDataSourceConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_DATASOURCE_ENABLED, matchIfMissing = false)
public class SentinelStrategyDataSourceAutoConfiguration {
    static {
        LogoBanner logoBanner = new LogoBanner(SentinelStrategyDataSourceAutoConfiguration.class, "/com/nepxion/sentinel/resource/logo.txt", "Welcome to Nepxion", 8, 5, new Color[] { Color.red, Color.green, Color.cyan, Color.blue, Color.yellow, Color.magenta, Color.red, Color.green }, true);

        NepxionBanner.show(logoBanner, new Description("Protector:", ProtectorType.SENTINEL.toString(), 0, 1), new Description(BannerConstant.GITHUB + ":", BannerConstant.NEPXION_GITHUB + "/Discovery", 0, 1));
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
            return new SentinelStrategyRuleNacosProcessor(SentinelRuleType.FLOW);
        }

        @Bean
        public NacosProcessor sentinelStrategyDegradeRuleNacosProcessor() {
            return new SentinelStrategyRuleNacosProcessor(SentinelRuleType.DEGRADE);
        }

        @Bean
        public NacosProcessor sentinelStrategyAuthorityRuleNacosProcessor() {
            return new SentinelStrategyRuleNacosProcessor(SentinelRuleType.AUTHORITY);
        }

        @Bean
        public NacosProcessor sentinelStrategySystemRuleNacosProcessor() {
            return new SentinelStrategyRuleNacosProcessor(SentinelRuleType.SYSTEM);
        }

        @Bean
        public NacosProcessor sentinelStrategyParamFlowRuleNacosProcessor() {
            return new SentinelStrategyRuleNacosProcessor(SentinelRuleType.PARAM_FLOW);
        }
    }

    @ConditionalOnClass(ApolloProcessor.class)
    protected static class SentinelStrategyApolloConfiguration {
        @Bean
        public ApolloProcessor sentinelStrategyFlowRuleApolloProcessor() {
            return new SentinelStrategyRuleApolloProcessor(SentinelRuleType.FLOW);
        }

        @Bean
        public ApolloProcessor sentinelStrategyDegradeRuleApolloProcessor() {
            return new SentinelStrategyRuleApolloProcessor(SentinelRuleType.DEGRADE);
        }

        @Bean
        public ApolloProcessor sentinelStrategyAuthorityRuleApolloProcessor() {
            return new SentinelStrategyRuleApolloProcessor(SentinelRuleType.AUTHORITY);
        }

        @Bean
        public ApolloProcessor sentinelStrategySystemRuleApolloProcessor() {
            return new SentinelStrategyRuleApolloProcessor(SentinelRuleType.SYSTEM);
        }

        @Bean
        public ApolloProcessor sentinelStrategyParamFlowRuleApolloProcessor() {
            return new SentinelStrategyRuleApolloProcessor(SentinelRuleType.PARAM_FLOW);
        }
    }

    @ConditionalOnClass(RedisProcessor.class)
    protected static class SentinelStrategyRedisConfiguration {
        @Bean
        public RedisProcessor sentinelStrategyFlowRuleRedisProcessor() {
            return new SentinelStrategyRuleRedisProcessor(SentinelRuleType.FLOW);
        }

        @Bean
        public RedisProcessor sentinelStrategyDegradeRuleRedisProcessor() {
            return new SentinelStrategyRuleRedisProcessor(SentinelRuleType.DEGRADE);
        }

        @Bean
        public RedisProcessor sentinelStrategyAuthorityRuleRedisProcessor() {
            return new SentinelStrategyRuleRedisProcessor(SentinelRuleType.AUTHORITY);
        }

        @Bean
        public RedisProcessor sentinelStrategySystemRuleRedisProcessor() {
            return new SentinelStrategyRuleRedisProcessor(SentinelRuleType.SYSTEM);
        }

        @Bean
        public RedisProcessor sentinelStrategyParamFlowRuleRedisProcessor() {
            return new SentinelStrategyRuleRedisProcessor(SentinelRuleType.PARAM_FLOW);
        }
    }

    @ConditionalOnClass(ZookeeperProcessor.class)
    protected static class SentinelStrategyZookeeperConfiguration {
        @Bean
        public ZookeeperProcessor sentinelStrategyFlowRuleZookeeperProcessor() {
            return new SentinelStrategyRuleZookeeperProcessor(SentinelRuleType.FLOW);
        }

        @Bean
        public ZookeeperProcessor sentinelStrategyDegradeRuleZookeeperProcessor() {
            return new SentinelStrategyRuleZookeeperProcessor(SentinelRuleType.DEGRADE);
        }

        @Bean
        public ZookeeperProcessor sentinelStrategyAuthorityRuleZookeeperProcessor() {
            return new SentinelStrategyRuleZookeeperProcessor(SentinelRuleType.AUTHORITY);
        }

        @Bean
        public ZookeeperProcessor sentinelStrategySystemRuleZookeeperProcessor() {
            return new SentinelStrategyRuleZookeeperProcessor(SentinelRuleType.SYSTEM);
        }

        @Bean
        public ZookeeperProcessor sentinelStrategyParamFlowRuleZookeeperProcessor() {
            return new SentinelStrategyRuleZookeeperProcessor(SentinelRuleType.PARAM_FLOW);
        }
    }

    @ConditionalOnClass(ConsulProcessor.class)
    protected static class SentinelStrategyConsulConfiguration {
        @Bean
        public ConsulProcessor sentinelStrategyFlowRuleConsulProcessor() {
            return new SentinelStrategyRuleConsulProcessor(SentinelRuleType.FLOW);
        }

        @Bean
        public ConsulProcessor sentinelStrategyDegradeRuleConsulProcessor() {
            return new SentinelStrategyRuleConsulProcessor(SentinelRuleType.DEGRADE);
        }

        @Bean
        public ConsulProcessor sentinelStrategyAuthorityRuleConsulProcessor() {
            return new SentinelStrategyRuleConsulProcessor(SentinelRuleType.AUTHORITY);
        }

        @Bean
        public ConsulProcessor sentinelStrategySystemRuleConsulProcessor() {
            return new SentinelStrategyRuleConsulProcessor(SentinelRuleType.SYSTEM);
        }

        @Bean
        public ConsulProcessor sentinelStrategyParamFlowRuleConsulProcessor() {
            return new SentinelStrategyRuleConsulProcessor(SentinelRuleType.PARAM_FLOW);
        }
    }

    @ConditionalOnClass(EtcdProcessor.class)
    protected static class SentinelStrategyEtcdConfiguration {
        @Bean
        public EtcdProcessor sentinelStrategyFlowRuleEtcdProcessor() {
            return new SentinelStrategyRuleEtcdProcessor(SentinelRuleType.FLOW);
        }

        @Bean
        public EtcdProcessor sentinelStrategyDegradeRuleEtcdProcessor() {
            return new SentinelStrategyRuleEtcdProcessor(SentinelRuleType.DEGRADE);
        }

        @Bean
        public EtcdProcessor sentinelStrategyAuthorityRuleEtcdProcessor() {
            return new SentinelStrategyRuleEtcdProcessor(SentinelRuleType.AUTHORITY);
        }

        @Bean
        public EtcdProcessor sentinelStrategySystemRuleEtcdProcessor() {
            return new SentinelStrategyRuleEtcdProcessor(SentinelRuleType.SYSTEM);
        }

        @Bean
        public EtcdProcessor sentinelStrategyParamFlowRuleEtcdProcessor() {
            return new SentinelStrategyRuleEtcdProcessor(SentinelRuleType.PARAM_FLOW);
        }
    }
}