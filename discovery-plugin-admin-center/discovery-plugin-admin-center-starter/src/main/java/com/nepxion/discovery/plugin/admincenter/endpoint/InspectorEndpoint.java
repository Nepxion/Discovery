package com.nepxion.discovery.plugin.admincenter.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.InspectorEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.UrlUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextHolder;

@RestController
@RequestMapping(path = "/inspector")
@Api(tags = { "全链路侦测接口" })
public class InspectorEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(InspectorEndpoint.class);

    @Autowired
    private RestTemplate pluginRestTemplate;

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired(required = false)
    private PluginContextHolder pluginContextHolder;

    @RequestMapping(path = "/inspect", method = RequestMethod.POST)
    @ApiOperation(value = "侦测全链路路由", notes = "", response = InspectorEntity.class, httpMethod = "POST")
    @ResponseBody
    public InspectorEntity inspect(@RequestBody @ApiParam(value = "侦测对象", required = true) InspectorEntity inspectorEntity) {
        List<String> serviceIdList = inspectorEntity.getServiceIdList();
        String result = inspectorEntity.getResult();

        // 第一个侦测节点，不会产生侦测信息，需要通过Header方式埋入 
        if (StringUtils.isEmpty(result) && pluginContextHolder != null) {
            result = pluginContextHolder.getContext(DiscoveryConstant.INSPECTOR_ENDPOINT_HEADER);
        }

        String pluginInfo = pluginAdapter.getPluginInfo(result);
        inspectorEntity.setResult(pluginInfo);

        if (CollectionUtils.isNotEmpty(serviceIdList)) {
            // 获取侦测列表中的第一个服务，作为下一个侦测中继节点
            String nextServiceId = serviceIdList.get(0);

            // 删除侦测列表中的第一个服务
            serviceIdList.remove(0);

            String url = null;
            try {
                String contextPath = getContextPath(nextServiceId);

                url = "http://" + nextServiceId + contextPath + DiscoveryConstant.INSPECTOR_ENDPOINT_URL;

                // 直调方式需要走负载均衡模式下的RestTemplate
                return pluginRestTemplate.postForEntity(url, inspectorEntity, InspectorEntity.class).getBody();
            } catch (Exception e) {
                String exceptionMessage = "Failed to inspect, current serviceId=" + pluginAdapter.getServiceId() + ", next serviceId=" + nextServiceId + ", url=" + url;

                LOG.error(exceptionMessage, e);

                throw new DiscoveryException(exceptionMessage, e);
            }
        } else {
            return inspectorEntity;
        }
    }

    private String getContextPath(String serviceId) {
        ServiceInstance instance = getInstance(serviceId);

        String contextPath = pluginAdapter.getInstanceContextPath(instance);

        return UrlUtil.formatContextPath(contextPath);
    }

    private ServiceInstance getInstance(String serviceId) {
        List<ServiceInstance> instances = null;

        try {
            instances = discoveryClient.getInstances(serviceId);
        } catch (Exception e) {
            throw new DiscoveryException("Failed to get instance list, serviceId=" + serviceId, e);
        }

        if (CollectionUtils.isEmpty(instances)) {
            throw new DiscoveryException("Instance list is empty, serviceId=" + serviceId);
        }

        return instances.get(0);
    }
}