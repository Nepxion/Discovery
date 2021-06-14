package com.nepxion.discovery.common.util;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.handler.DiscoveryResponseErrorHandler;

public class RestUtil {
    public static HttpHeaders processHeader(HttpHeaders httpHeaders, Map<String, String> headerMap) {
        if (MapUtils.isNotEmpty(headerMap)) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpHeaders.add(key, value);
            }
        }

        return httpHeaders;
    }

    public static String processParameter(String url, Map<String, String> parameterMap) {
        if (MapUtils.isNotEmpty(parameterMap)) {
            StringBuilder parameterStringBuilder = new StringBuilder();

            int index = 0;
            for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                parameterStringBuilder.append(key + DiscoveryConstant.EQUALS + value);
                if (index < parameterMap.size() - 1) {
                    parameterStringBuilder.append("&");
                }

                index++;
            }

            String parameter = parameterStringBuilder.toString();
            parameter = StringUtils.isNotEmpty(parameter) ? "?" + parameter : "";

            url += parameter;
        }

        return url;
    }

    public static HttpHeaders processCookie(HttpHeaders httpHeaders, Map<String, String> cookieMap) {
        if (MapUtils.isNotEmpty(cookieMap)) {
            List<String> cookieList = new ArrayList<String>();
            for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                cookieList.add(key + DiscoveryConstant.EQUALS + value);
            }

            httpHeaders.put(DiscoveryConstant.COOKIE_TYPE, cookieList);
        }

        return httpHeaders;
    }

    public static <T> T fromJson(RestTemplate restTemplate, String result, TypeReference<T> typeReference) {
        try {
            return JsonUtil.fromJson(result, typeReference);
        } catch (Exception e) {
            String error = getError(restTemplate);
            if (StringUtils.isNotEmpty(error)) {
                throw new IllegalArgumentException(error);
            }

            throw e;
        }
    }

    public static String getError(RestTemplate restTemplate) {
        ResponseErrorHandler responseErrorHandler = restTemplate.getErrorHandler();
        if (responseErrorHandler instanceof DiscoveryResponseErrorHandler) {
            DiscoveryResponseErrorHandler discoveryResponseErrorHandler = (DiscoveryResponseErrorHandler) responseErrorHandler;

            return discoveryResponseErrorHandler.getError();
        }

        return null;
    }
}