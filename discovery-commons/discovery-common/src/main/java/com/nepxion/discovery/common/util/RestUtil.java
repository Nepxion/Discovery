package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nepxion.discovery.common.handler.DiscoveryResponseErrorHandler;

public class RestUtil {
    public static <T> T fromJson(RestTemplate restTemplate, String result, TypeReference<T> typeReference) {
        try {
            return JsonUtil.fromJson(result, typeReference);
        } catch (Exception e) {
            String cause = getCause(restTemplate);
            if (StringUtils.isNotEmpty(cause)) {
                throw new IllegalArgumentException(cause);
            }

            throw e;
        }
    }

    public static String getCause(RestTemplate restTemplate) {
        ResponseErrorHandler responseErrorHandler = restTemplate.getErrorHandler();
        if (responseErrorHandler instanceof DiscoveryResponseErrorHandler) {
            DiscoveryResponseErrorHandler discoveryResponseErrorHandler = (DiscoveryResponseErrorHandler) responseErrorHandler;

            return discoveryResponseErrorHandler.getCause();
        }

        return null;
    }
}