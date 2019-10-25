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

import io.opentracing.Span;
import io.opentracing.Tracer;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;



/**
 * TraceResponseHttpHeadersFilter
 *
 * @author Weichao Li (liweichao0102@gmail.com)
 * @since 2019/9/28
 */
public class TraceResponseHttpHeadersFilter extends AbstractHttpHeadersFilter {

  private final Logger log = LoggerFactory.getLogger(TraceResponseHttpHeadersFilter.class);

  protected TraceResponseHttpHeadersFilter(Tracer tracer) {
    super(tracer);
  }

  @Override
  public HttpHeaders filter(HttpHeaders input, ServerWebExchange exchange) {
    Object storedSpan = exchange.getAttribute(SPAN_ATTRIBUTE);
    if (storedSpan == null) {
      return input;
    }
    log.debug("Will instrument the response");
    Span span = (Span) storedSpan;
    if (Objects.nonNull(span)) {
      span.finish();
    }
    log.debug("The response was handled for span " + storedSpan);
    return input;
  }

  @Override
  public boolean supports(Type type) {
    return type.equals(Type.RESPONSE);
  }
}
