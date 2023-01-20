package com.nepxion.discovery.plugin.admincenter.endpoint;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.entity.GatewayType;
import com.nepxion.discovery.common.entity.InstanceEntity;
import com.nepxion.discovery.common.entity.ServiceType;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.plugin.admincenter.resource.ServiceResource;

@RestController
@RequestMapping(path = "/service")
public class ServiceEndpoint {
    @Autowired
    private ServiceResource serviceResource;

    @RequestMapping(path = "/discovery-type", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> discoveryType() {
        return doDiscoveryType();
    }

    @RequestMapping(path = "/groups", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> groups() {
        return doGroups();
    }

    @RequestMapping(path = "/group/{serviceId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> group(@PathVariable(value = "serviceId") String serviceId) {
        return doGroup(serviceId);
    }

    @RequestMapping(path = "/services", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> services() {
        return doServices();
    }

    @RequestMapping(path = "/service-list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> serviceList(@RequestBody List<String> serviceTypes) {
        return doServiceList(serviceTypes);
    }

    @RequestMapping(path = "/service-list/{group}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> serviceList(@PathVariable(value = "group") String group, @RequestBody List<String> serviceTypes) {
        return doServiceList(group, serviceTypes);
    }

    @RequestMapping(path = "/gateways", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> gateways() {
        return doGateways();
    }

    @RequestMapping(path = "/gateway-list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> gatewayList(@RequestBody List<String> gatewayTypes) {
        return doGatewayList(gatewayTypes);
    }

    @RequestMapping(path = "/instances/{serviceId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> instances(@PathVariable(value = "serviceId") String serviceId) {
        return doInstances(serviceId);
    }

    @RequestMapping(path = "/instance-list/{serviceId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> instanceList(@PathVariable(value = "serviceId") String serviceId) {
        return doInstanceList(serviceId);
    }

    @RequestMapping(path = "/instance-map", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> instanceMap(@RequestBody List<String> groups) {
        return doInstanceMap(groups);
    }

    private ResponseEntity<?> doDiscoveryType() {
        try {
            String discoveryType = serviceResource.getDiscoveryType().toString();

            return ResponseUtil.getSuccessResponse(discoveryType);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doGroups() {
        try {
            List<String> groups = serviceResource.getGroups();

            return ResponseUtil.getSuccessResponse(groups);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doGroup(String serviceId) {
        try {
            String group = serviceResource.getGroup(serviceId);

            return ResponseUtil.getSuccessResponse(group);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doServices() {
        try {
            List<String> services = serviceResource.getServices();

            return ResponseUtil.getSuccessResponse(services);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doServiceList(List<String> serviceTypes) {
        try {
            List<ServiceType> types = new ArrayList<ServiceType>();
            for (String serviceType : serviceTypes) {
                types.add(ServiceType.fromString(serviceType));
            }

            List<String> services = serviceResource.getServiceList(types);

            return ResponseUtil.getSuccessResponse(services);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doServiceList(String group, List<String> serviceTypes) {
        try {
            List<ServiceType> types = new ArrayList<ServiceType>();
            for (String serviceType : serviceTypes) {
                types.add(ServiceType.fromString(serviceType));
            }

            List<String> services = serviceResource.getServiceList(group, types);

            return ResponseUtil.getSuccessResponse(services);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doGateways() {
        try {
            List<String> gateways = serviceResource.getGateways();

            return ResponseUtil.getSuccessResponse(gateways);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doGatewayList(List<String> gatewayTypes) {
        try {
            List<GatewayType> types = new ArrayList<GatewayType>();
            for (String gatewayType : gatewayTypes) {
                types.add(GatewayType.fromString(gatewayType));
            }

            List<String> gatewayList = serviceResource.getGatewayList(types);

            return ResponseUtil.getSuccessResponse(gatewayList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doInstances(String serviceId) {
        try {
            List<ServiceInstance> instances = serviceResource.getInstances(serviceId);

            return ResponseUtil.getSuccessResponse(instances);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doInstanceList(String serviceId) {
        try {
            List<InstanceEntity> instanceList = serviceResource.getInstanceList(serviceId);

            return ResponseUtil.getSuccessResponse(instanceList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doInstanceMap(List<String> groups) {
        try {
            Map<String, List<InstanceEntity>> instanceMaps = serviceResource.getInstanceMap(groups);

            return ResponseUtil.getSuccessResponse(instanceMaps);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}