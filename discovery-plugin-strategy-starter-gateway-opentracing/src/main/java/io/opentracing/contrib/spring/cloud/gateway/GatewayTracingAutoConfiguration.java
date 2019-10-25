/**
 * Copyright 2017-2019 The OpenTracing Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.opentracing.contrib.spring.cloud.gateway;

import io.opentracing.Tracer;
import io.opentracing.contrib.spring.tracer.configuration.TracerAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GatewayTracingAutoConfiguration
 *
 * @author Weichao Li (liweichao0102@gmail.com)
 * @since 2019/9/27
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(TracerAutoConfiguration.class)
@ConditionalOnClass(GatewayFilterChain.class)
@ConditionalOnProperty(name = "opentracing.spring.cloud.gateway.enabled", havingValue = "true", matchIfMissing = true)
public class GatewayTracingAutoConfiguration {

  @Bean
  @ConditionalOnClass(HttpHeadersFilter.class)
  @ConditionalOnBean(Tracer.class)
  HttpHeadersFilter traceRequestHttpHeadersFilter(Tracer tracer) {
    return new TraceRequestHttpHeadersFilter(tracer);
  }

  @Bean
  @ConditionalOnClass(HttpHeadersFilter.class)
  @ConditionalOnBean(Tracer.class)
  HttpHeadersFilter traceResponseHttpHeadersFilter(Tracer tracer) {
    return new TraceResponseHttpHeadersFilter(tracer);
  }
}
