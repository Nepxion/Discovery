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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.util.RestUtil;
import com.nepxion.discovery.common.util.UrlUtil;

public abstract class AbstractRestInvoker {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractRestInvoker.class);

    protected List<ServiceInstance> instances;
    protected RestTemplate restTemplate;
    protected boolean async;

    public AbstractRestInvoker(List<ServiceInstance> instances, RestTemplate restTemplate) {
        this(instances, restTemplate, false);
    }

    public AbstractRestInvoker(List<ServiceInstance> instances, RestTemplate restTemplate, boolean async) {
        this.instances = instances;
        this.restTemplate = restTemplate;
        this.async = async;
    }

    public ResponseEntity<?> invoke() {
        if (CollectionUtils.isEmpty(instances)) {
            LOG.warn("No service instances found");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No service instances found");
        }

        List<ResultEntity> resultEntityList = new ArrayList<ResultEntity>();
        for (ServiceInstance instance : instances) {
            Map<String, String> metadata = instance.getMetadata();
            String host = instance.getHost();
            int port = instance.getPort();
            String contextPath = metadata.get(DiscoveryConstant.SPRING_APPLICATION_CONTEXT_PATH);
            String url = "http://" + host + ":" + port + UrlUtil.formatContextPath(contextPath) + getSuffixPath();

            String result = null;
            try {
                checkPermission(instance);

                result = doRest(url);
                if (!StringUtils.equals(result, DiscoveryConstant.OK)) {
                    result = RestUtil.getCause(restTemplate);
                }
            } catch (Exception e) {
                result = e.getMessage();
            }

            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setUrl(url);
            resultEntity.setResult(result);

            resultEntityList.add(resultEntity);
        }

        String info = getInfo();
        LOG.info(info + " results=\n{}", resultEntityList);

        return ResponseEntity.ok().body(resultEntityList);
    }

    protected String getInvokeType() {
        return async ? DiscoveryConstant.ASYNC : DiscoveryConstant.SYNC;
    }

    protected void checkDiscoveryControlPermission(ServiceInstance instance) {
        Map<String, String> metadata = instance.getMetadata();

        String discoveryControlEnabled = metadata.get(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED);
        if (StringUtils.isEmpty(discoveryControlEnabled)) {
            throw new IllegalArgumentException("No metadata for key=" + DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED);
        }

        if (!Boolean.valueOf(discoveryControlEnabled)) {
            throw new IllegalArgumentException("Discovery control is disabled");
        }
    }

    protected void checkConfigRestControlPermission(ServiceInstance instance) {
        Map<String, String> metadata = instance.getMetadata();

        String configRestControlEnabled = metadata.get(DiscoveryConstant.SPRING_APPLICATION_CONFIG_REST_CONTROL_ENABLED);
        if (StringUtils.isEmpty(configRestControlEnabled)) {
            throw new IllegalArgumentException("No metadata for key=" + DiscoveryConstant.SPRING_APPLICATION_CONFIG_REST_CONTROL_ENABLED);
        }

        if (!Boolean.valueOf(configRestControlEnabled)) {
            throw new IllegalArgumentException("Config rest control is disabled");
        }
    }

    protected abstract String getInfo();

    protected abstract String getSuffixPath();

    protected abstract String doRest(String url);

    protected abstract void checkPermission(ServiceInstance instance) throws Exception;
}