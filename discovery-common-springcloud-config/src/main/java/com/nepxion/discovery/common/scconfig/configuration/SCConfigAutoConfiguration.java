package com.nepxion.discovery.common.scconfig.configuration;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Ankeway
 * @version 1.0
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nepxion.discovery.common.scconfig.operation.SCConfigOperation;

@Configuration
public class SCConfigAutoConfiguration {
    @Bean
    public SCConfigOperation scConfigOperation() {
        return new SCConfigOperation();
    }
}