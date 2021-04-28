package com.nepxion.discovery.plugin.strategy.zuul.adapter;

import com.google.common.collect.Sets;
import com.nepxion.discovery.common.entity.RouteEntity;
import com.nepxion.discovery.plugin.framework.adapter.DynamicRouteAdapter;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Ning Zhang
 * @version 1.0
 */
public class ZuulDynamicRouteAdapter extends SimpleRouteLocator implements DynamicRouteAdapter, RefreshableRouteLocator, ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;
    private ZuulProperties zuulProperties;

    public ZuulDynamicRouteAdapter(final String servletPath,
                                   final ZuulProperties properties) {
        super(servletPath, properties);
        zuulProperties = properties;
    }

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        publisher = applicationEventPublisher;
    }

    @Override
    public void update(final List<RouteEntity> newRouteList) {
        final Map<String, ZuulProperties.ZuulRoute> newRouteMap = newRouteList.stream().collect(Collectors.toMap(RouteEntity::getUri, this::toZuulRoute));
        final Map<String, ZuulProperties.ZuulRoute> currentRouteMap = super.locateRoutes();
        final List<ZuulProperties.ZuulRoute> insertRouteDefinition = new ArrayList<>(newRouteMap.size());
        final List<ZuulProperties.ZuulRoute> updateRouteDefinition = new ArrayList<>(newRouteMap.size());
        final List<ZuulProperties.ZuulRoute> deleteRouteDefinition = new ArrayList<>(newRouteMap.size());

        for (final Map.Entry<String, ZuulProperties.ZuulRoute> pair : newRouteMap.entrySet()) {
            if (!currentRouteMap.containsKey(pair.getKey())) {
                insertRouteDefinition.add(pair.getValue());
            }
        }

        for (final Map.Entry<String, ZuulProperties.ZuulRoute> pair : newRouteMap.entrySet()) {
            if (currentRouteMap.containsKey(pair.getKey())) {
                final ZuulProperties.ZuulRoute currentRouteDefinition = currentRouteMap.get(pair.getKey());
                final ZuulProperties.ZuulRoute newRouteDefinition = pair.getValue();
                if (!currentRouteDefinition.equals(newRouteDefinition)) {
                    updateRouteDefinition.add(newRouteDefinition);
                }
            }
        }

        for (final Map.Entry<String, ZuulProperties.ZuulRoute> pair : currentRouteMap.entrySet()) {
            if (!newRouteMap.containsKey(pair.getKey())) {
                deleteRouteDefinition.add(pair.getValue());
            }
        }

        for (final ZuulProperties.ZuulRoute zuulRoute : insertRouteDefinition) {
            this.add(zuulRoute);
        }
        for (final ZuulProperties.ZuulRoute zuulRoute : updateRouteDefinition) {
            this.modify(zuulRoute);
        }
        for (final ZuulProperties.ZuulRoute zuulRoute : deleteRouteDefinition) {
            this.delete(zuulRoute);
        }

        if (!insertRouteDefinition.isEmpty() || !updateRouteDefinition.isEmpty() || !deleteRouteDefinition.isEmpty()) {
            publisher.publishEvent(new RoutesRefreshedEvent(this));
        }
    }

    @Override
    public List<String> view() {
        return super.locateRoutes().values().stream().map(ZuulProperties.ZuulRoute::toString).collect(Collectors.toList());
    }

    @Override
    public void refresh() {
        super.doRefresh();
    }

    private ZuulProperties.ZuulRoute toZuulRoute(final RouteEntity routeEntity) {
        if (routeEntity == null) {
            return null;
        }

        final ZuulProperties.ZuulRoute result = new ZuulProperties.ZuulRoute();
        result.setId(routeEntity.getRouteId());
        result.setPath(routeEntity.getUri());
        result.setServiceId(routeEntity.getServiceName());
        result.setUrl(null);
        result.setStripPrefix(true);
        result.setRetryable(null);
        result.setSensitiveHeaders(Sets.newHashSet());
        result.setCustomSensitiveHeaders(false);
        return result;
    }

    private void add(final ZuulProperties.ZuulRoute zuulRoute) {
        zuulProperties.getRoutes().put(zuulRoute.getId(), zuulRoute);
    }

    private void modify(final ZuulProperties.ZuulRoute zuulRoute) {
        zuulProperties.getRoutes().put(zuulRoute.getId(), zuulRoute);
    }

    private void delete(final ZuulProperties.ZuulRoute zuulRoute) {
        zuulProperties.getRoutes().remove(zuulRoute.getId());
    }
}