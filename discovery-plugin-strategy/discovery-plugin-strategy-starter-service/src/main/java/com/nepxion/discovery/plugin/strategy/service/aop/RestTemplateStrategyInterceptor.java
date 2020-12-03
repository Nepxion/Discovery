package com.nepxion.discovery.plugin.strategy.service.aop;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @author Fengfeng Li
 * @version 1.0
 */

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;
import com.nepxion.discovery.plugin.strategy.service.context.ServiceStrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.util.StrategyUtil;

public class RestTemplateStrategyInterceptor extends AbstractStrategyInterceptor implements ClientHttpRequestInterceptor {
    @Autowired
    protected ServiceStrategyContextHolder serviceStrategyContextHolder;

    // RestTemplate核心策略Header是否传递。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能。核心策略Header，包含如下
    // 1. n-d-version
    // 2. n-d-region
    // 3. n-d-address
    // 4. n-d-version-weight
    // 5. n-d-region-weight
    // 6. n-d-id-blacklist
    // 7. n-d-address-blacklist
    // 8. n-d-env (不属于灰度蓝绿范畴的Header，只要外部传入就会全程传递)
    @Value("${" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_REST_TEMPLATE_CORE_HEADER_TRANSMISSION_ENABLED + ":true}")
    protected Boolean restTemplateCoreHeaderTransmissionEnabled;

    public RestTemplateStrategyInterceptor(String contextRequestHeaders, String businessRequestHeaders) {
        super(contextRequestHeaders, businessRequestHeaders);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        interceptInputHeader();

        applyInnerHeader(request);
        applyOuterHeader(request);

        interceptOutputHeader(request);

        return execution.execute(request, body);
    }

    private void applyInnerHeader(HttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        headers.add(DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        headers.add(DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            headers.add(DiscoveryConstant.N_D_SERVICE_APP_ID, serviceAppId);
        }
        headers.add(DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
        headers.add(DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        headers.add(DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion());
        headers.add(DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion());
        headers.add(DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, pluginAdapter.getEnvironment());
        headers.add(DiscoveryConstant.N_D_SERVICE_ZONE, pluginAdapter.getZone());
    }

    private void applyOuterHeader(HttpRequest request) {
        ServletRequestAttributes attributes = serviceStrategyContextHolder.getRestAttributes();
        if (attributes != null) {
            HttpServletRequest previousRequest = attributes.getRequest();
            Enumeration<String> headerNames = previousRequest.getHeaderNames();
            if (headerNames != null) {
                HttpHeaders headers = request.getHeaders();
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    String headerValue = previousRequest.getHeader(headerName);
                    boolean isHeaderContains = isHeaderContainsExcludeInner(headerName.toLowerCase());
                    if (isHeaderContains) {
                        if (restTemplateCoreHeaderTransmissionEnabled) {
                            headers.add(headerName, headerValue);
                        } else {
                            boolean isCoreHeaderContains = StrategyUtil.isCoreHeaderContains(headerName);
                            if (!isCoreHeaderContains) {
                                headers.add(headerName, headerValue);
                            }
                        }
                    }
                }
            }
        }

        if (restTemplateCoreHeaderTransmissionEnabled) {
            HttpHeaders headers = request.getHeaders();
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION))) {
                String routeVersion = serviceStrategyContextHolder.getRouteVersion();
                if (StringUtils.isNotEmpty(routeVersion)) {
                    headers.add(DiscoveryConstant.N_D_VERSION, routeVersion);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION))) {
                String routeRegion = serviceStrategyContextHolder.getRouteRegion();
                if (StringUtils.isNotEmpty(routeRegion)) {
                    headers.add(DiscoveryConstant.N_D_REGION, routeRegion);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ENVIRONMENT))) {
                String routeEnvironment = serviceStrategyContextHolder.getRouteEnvironment();
                if (StringUtils.isNotEmpty(routeEnvironment)) {
                    headers.add(DiscoveryConstant.N_D_ENVIRONMENT, routeEnvironment);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ADDRESS))) {
                String routeAddress = serviceStrategyContextHolder.getRouteAddress();
                if (StringUtils.isNotEmpty(routeAddress)) {
                    headers.add(DiscoveryConstant.N_D_ADDRESS, routeAddress);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_VERSION_WEIGHT))) {
                String routeVersionWeight = serviceStrategyContextHolder.getRouteVersionWeight();
                if (StringUtils.isNotEmpty(routeVersionWeight)) {
                    headers.add(DiscoveryConstant.N_D_VERSION_WEIGHT, routeVersionWeight);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_REGION_WEIGHT))) {
                String routeRegionWeight = serviceStrategyContextHolder.getRouteRegionWeight();
                if (StringUtils.isNotEmpty(routeRegionWeight)) {
                    headers.add(DiscoveryConstant.N_D_REGION_WEIGHT, routeRegionWeight);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ID_BLACKLIST))) {
                String routeIdBlacklist = serviceStrategyContextHolder.getRouteIdBlacklist();
                if (StringUtils.isNotEmpty(routeIdBlacklist)) {
                    headers.add(DiscoveryConstant.N_D_ID_BLACKLIST, routeIdBlacklist);
                }
            }
            if (CollectionUtils.isEmpty(headers.get(DiscoveryConstant.N_D_ADDRESS_BLACKLIST))) {
                String routeAddressBlacklist = serviceStrategyContextHolder.getRouteAddressBlacklist();
                if (StringUtils.isNotEmpty(routeAddressBlacklist)) {
                    headers.add(DiscoveryConstant.N_D_ADDRESS_BLACKLIST, routeAddressBlacklist);
                }
            }
        }
    }

    private void interceptOutputHeader(HttpRequest request) {
        if (!interceptDebugEnabled) {
            return;
        }

        System.out.println("------- " + getInterceptorName() + " Intercept Output Header Information ------");
        HttpHeaders headers = request.getHeaders();
        for (Iterator<Entry<String, List<String>>> iterator = headers.entrySet().iterator(); iterator.hasNext();) {
            Entry<String, List<String>> header = iterator.next();
            String headerName = header.getKey();
            boolean isHeaderContains = isHeaderContains(headerName.toLowerCase());
            if (isHeaderContains) {
                List<String> headerValue = header.getValue();

                System.out.println(headerName + "=" + headerValue);
            }
        }
        System.out.println("--------------------------------------------------");
    }

    @Override
    protected String getInterceptorName() {
        return "RestTemplate";
    }
}