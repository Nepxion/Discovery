package com.nepxion.discovery.console.rest;

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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.constant.DiscoveryMetaDataConstant;
import com.nepxion.discovery.common.entity.InstanceEntityWrapper;
import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.common.util.RestUtil;
import com.nepxion.discovery.console.resource.ServiceResource;

public abstract class AbstractRestInvoker {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractRestInvoker.class);

    protected ServiceResource serviceResource;
    protected String serviceId;
    protected RestTemplate restTemplate;
    protected boolean async;

    public AbstractRestInvoker(ServiceResource serviceResource, String serviceId, RestTemplate restTemplate) {
        this(serviceResource, serviceId, restTemplate, false);
    }

    public AbstractRestInvoker(ServiceResource serviceResource, String serviceId, RestTemplate restTemplate, boolean async) {
        this.serviceResource = serviceResource;
        this.serviceId = serviceId;
        this.restTemplate = restTemplate;
        this.async = async;
    }

    public List<ResultEntity> invoke() {
        List<ServiceInstance> instances = serviceResource.getInstances(serviceId);
        if (CollectionUtils.isEmpty(instances)) {
            LOG.warn("No service instances found");

            throw new DiscoveryException("No service instances found");
        }

        List<ResultEntity> resultEntityList = new ArrayList<ResultEntity>();
        for (ServiceInstance instance : instances) {
            Map<String, String> metadata = instance.getMetadata();
            String host = instance.getHost();
            int port = instance.getPort();

            String protocol = InstanceEntityWrapper.getProtocol(metadata);
            String contextPath = InstanceEntityWrapper.getFormatContextPath(metadata);

            String url = protocol + "://" + host + ":" + port + contextPath + getSuffixPath();

            String result = null;
            try {
                checkPermission(instance);

                result = doRest(url);
                String error = RestUtil.getError(restTemplate);
                if (StringUtils.isNotEmpty(error)) {
                    result = error;
                }
            } catch (Exception e) {
                result = ResponseUtil.getFailureMessage(e);
            }

            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setServiceId(serviceId);
            resultEntity.setHost(host);
            resultEntity.setPort(port);
            resultEntity.setUrl(url);
            resultEntity.setResult(result);

            resultEntityList.add(resultEntity);
        }

        String description = getDescription();

        LOG.info(description + " results=\n{}", resultEntityList);

        return resultEntityList;
    }

    protected String getInvokeType() {
        return async ? DiscoveryConstant.ASYNC : DiscoveryConstant.SYNC;
    }

    protected HttpHeaders getInvokeHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return headers;
    }

    protected HttpEntity<String> getInvokeEntity(String content) {
        HttpHeaders headers = getInvokeHeaders();

        return new HttpEntity<String>(content, headers);
    }

    protected void checkDiscoveryControlPermission(ServiceInstance instance) {
        Map<String, String> metadata = instance.getMetadata();

        String discoveryControlEnabled = metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED);
        if (StringUtils.isEmpty(discoveryControlEnabled)) {
            throw new DiscoveryException("No metadata for key=" + DiscoveryMetaDataConstant.SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED);
        }

        if (!Boolean.valueOf(discoveryControlEnabled)) {
            throw new DiscoveryException("Discovery control is disabled");
        }
    }

    protected void checkConfigRestControlPermission(ServiceInstance instance) {
        Map<String, String> metadata = instance.getMetadata();

        String configRestControlEnabled = metadata.get(DiscoveryMetaDataConstant.SPRING_APPLICATION_CONFIG_REST_CONTROL_ENABLED);
        if (StringUtils.isEmpty(configRestControlEnabled)) {
            throw new DiscoveryException("No metadata for key=" + DiscoveryMetaDataConstant.SPRING_APPLICATION_CONFIG_REST_CONTROL_ENABLED);
        }

        if (!Boolean.valueOf(configRestControlEnabled)) {
            throw new DiscoveryException("Config rest control is disabled");
        }
    }

    protected void checkPermission(ServiceInstance instance) throws Exception {

    }

    protected abstract String getDescription();

    protected abstract String getSuffixPath();

    protected abstract String doRest(String url);
}