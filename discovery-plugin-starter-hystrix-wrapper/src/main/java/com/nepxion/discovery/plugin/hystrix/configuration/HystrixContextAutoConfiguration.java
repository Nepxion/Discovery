package com.nepxion.discovery.plugin.hystrix.configuration;

import com.netflix.hystrix.Hystrix;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Hystrix.class)
@ConditionalOnProperty(name ="zuul.ribbon-isolation-strategy",havingValue ="thread" )
public class HystrixContextAutoConfiguration {

    @Bean
    public HystrixContextConcurrencyStrategy hystrixContextConcurrencyStrategy(){
        return new HystrixContextConcurrencyStrategy();
    }
}
