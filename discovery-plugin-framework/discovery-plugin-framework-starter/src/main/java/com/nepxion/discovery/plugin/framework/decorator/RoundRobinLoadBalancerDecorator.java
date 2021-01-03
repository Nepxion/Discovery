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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;

import com.nepxion.discovery.common.entity.WeightFilterEntity;
import com.nepxion.discovery.plugin.framework.loadbalance.DiscoveryEnabledLoadBalance;
import com.nepxion.discovery.plugin.framework.loadbalance.weight.RuleMapWeightRandomLoadBalance;
import com.nepxion.discovery.plugin.framework.loadbalance.weight.StrategyMapWeightRandomLoadBalance;

/**
 * A Round-Robin-based implementation of {@link ReactorServiceInstanceLoadBalancer}.
 *
 * @author Spencer Gibb
 * @author Olga Maciaszek-Sharma
 */
public class RoundRobinLoadBalancerDecorator implements ReactorServiceInstanceLoadBalancer {

    private static final Log log = LogFactory.getLog(RoundRobinLoadBalancer.class);

    final AtomicInteger position;

    final String serviceId;

    ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    @Autowired
    private StrategyMapWeightRandomLoadBalance strategyMapWeightRandomLoadBalance;

    @Autowired
    private RuleMapWeightRandomLoadBalance ruleMapWeightRandomLoadBalance;

    @Autowired
    private DiscoveryEnabledLoadBalance discoveryEnabledLoadBalance;

    /**
     * @param serviceInstanceListSupplierProvider a provider of
     * {@link ServiceInstanceListSupplier} that will be used to get available instances
     * @param serviceId id of the service for which to choose an instance
     */
    public RoundRobinLoadBalancerDecorator(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
            String serviceId) {
        this(serviceInstanceListSupplierProvider, serviceId, new Random().nextInt(1000));
    }

    /**
     * @param serviceInstanceListSupplierProvider a provider of
     *        {@link ServiceInstanceListSupplier} that will be used to get available instances
     * @param serviceId id of the service for which to choose an instance
     * @param seedPosition Round Robin element position marker
     */
    public RoundRobinLoadBalancerDecorator(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
            String serviceId, int seedPosition) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.position = new AtomicInteger(seedPosition);
    }

    @SuppressWarnings("rawtypes")
    @Override
    // see original
    // https://github.com/Netflix/ocelli/blob/master/ocelli-core/
    // src/main/java/netflix/ocelli/loadbalancer/RoundRobinLoadBalancer.java
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next()
                .map(serviceInstances -> processInstanceResponse(supplier, serviceInstances));
    }

    private Response<ServiceInstance> processInstanceResponse(ServiceInstanceListSupplier supplier,
            List<ServiceInstance> serviceInstances) {
        Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances);
        if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
            ((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
        }
        return serviceInstanceResponse;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + serviceId);
            }
            return new EmptyResponse();
        }

        boolean isTriggered = false;

        WeightFilterEntity strategyWeightFilterEntity = strategyMapWeightRandomLoadBalance.getT();
        if (strategyWeightFilterEntity != null && strategyWeightFilterEntity.hasWeight()) {
            isTriggered = true;

            boolean isWeightChecked = strategyMapWeightRandomLoadBalance.checkWeight(instances, strategyWeightFilterEntity);
            if (isWeightChecked) {
                try {
                    return new DefaultResponse(strategyMapWeightRandomLoadBalance.choose(instances, strategyWeightFilterEntity));
                } catch (Exception e) {
                    return processDiscoveryEnabledLoadBalance(instances);
                }
            } else {
                return processDiscoveryEnabledLoadBalance(instances);
            }
        }

        if (!isTriggered) {
            WeightFilterEntity ruleWeightFilterEntity = ruleMapWeightRandomLoadBalance.getT();
            if (ruleWeightFilterEntity != null && ruleWeightFilterEntity.hasWeight()) {
                boolean isWeightChecked = ruleMapWeightRandomLoadBalance.checkWeight(instances, ruleWeightFilterEntity);
                if (isWeightChecked) {
                    try {
                        return new DefaultResponse(ruleMapWeightRandomLoadBalance.choose(instances, ruleWeightFilterEntity));
                    } catch (Exception e) {
                        return processDiscoveryEnabledLoadBalance(instances);
                    }
                } else {
                    return processDiscoveryEnabledLoadBalance(instances);
                }
            }
        }

        return processDiscoveryEnabledLoadBalance(instances);
    }

    private Response<ServiceInstance> processDiscoveryEnabledLoadBalance(List<ServiceInstance> instances) {
        List<ServiceInstance> roundRobinInstances = new ArrayList<ServiceInstance>();
        for (ServiceInstance instance : instances) {
            boolean enabled = discoveryEnabledLoadBalance.apply(instance);
            if (enabled) {
                roundRobinInstances.add(instance);
            }
        }

        if (roundRobinInstances.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No servers available for service: " + serviceId);
            }
            return new EmptyResponse();
        }

        return processRoundRobinLoadBalance(roundRobinInstances);
    }

    private Response<ServiceInstance> processRoundRobinLoadBalance(List<ServiceInstance> instances) {
        // TODO: enforce order?
        int pos = Math.abs(this.position.incrementAndGet());

        ServiceInstance instance = instances.get(pos % instances.size());

        return new DefaultResponse(instance);
    }
}