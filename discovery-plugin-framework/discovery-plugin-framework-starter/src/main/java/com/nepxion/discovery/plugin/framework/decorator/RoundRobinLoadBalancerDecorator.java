package com.nepxion.discovery.plugin.framework.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */


import reactor.core.publisher.Mono;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;

public class RoundRobinLoadBalancerDecorator extends RoundRobinLoadBalancer {
    public RoundRobinLoadBalancerDecorator(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        super(serviceInstanceListSupplierProvider, serviceId);
    }

    public RoundRobinLoadBalancerDecorator(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId, int seedPosition) {
        super(serviceInstanceListSupplierProvider, serviceId, seedPosition);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        
        
        
        return super.choose(request);
    }
}