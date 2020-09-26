package com.nepxion.discovery.plugin.example.service.sentinel;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;

public class MyRestTemplateBlockHandler {
    public static SentinelClientHttpResponse handleBlock(HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException e) {
        return new SentinelClientHttpResponse("RestTemplate client sentinel block, cause=" + e.getClass().getName() + ", rule=" + e.getRule() + ", limitApp=" + e.getRuleLimitApp());
    }
}