package com.nepxion.discovery.plugin.example.service.sentinel;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.alibaba.sentinel.rest.SentinelClientHttpResponse;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class MyRestTemplateFallbackHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MyRestTemplateFallbackHandler.class);

    public static SentinelClientHttpResponse handleFallback(HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException e) {
        LOG.info("Sentinel RestTemplate Client Fallback Causes");
        LOG.error("Sentinel RestTemplate Client Fallback Exception", e);
        LOG.info("Sentinel Rule Limit App={}", e.getRuleLimitApp());
        LOG.info("Sentinel Exception Name={}", e.getClass().getCanonicalName());

        return new SentinelClientHttpResponse("Sentinel RestTemplate Client Fallback Causes");
    }
}