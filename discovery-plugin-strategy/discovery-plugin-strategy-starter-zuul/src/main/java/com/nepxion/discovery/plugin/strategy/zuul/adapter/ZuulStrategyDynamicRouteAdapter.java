package com.nepxion.discovery.plugin.strategy.zuul.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Ning Zhang
 * @version 1.0
 */

import com.google.common.collect.Sets;
import com.nepxion.discovery.common.entity.DynamicRouteEntity;
import com.nepxion.discovery.plugin.framework.adapter.DynamicRouteAdapter;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ZuulStrategyDynamicRouteAdapter extends SimpleRouteLocator implements DynamicRouteAdapter, RefreshableRouteLocator, ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;
    private final ZuulProperties zuulProperties;

    public ZuulStrategyDynamicRouteAdapter(final String servletPath,
                                           final ZuulProperties properties) {
        super(servletPath, properties);
        this.zuulProperties = properties;
    }

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    public void update(final List<DynamicRouteEntity> dynamicRouteEntityList) {
        final Map<String, ZuulProperties.ZuulRoute> zuulRouteMap = dynamicRouteEntityList.stream().collect(Collectors.toMap(DynamicRouteEntity::getRouteId, this::toZuulRoute));
        final Map<String, ZuulProperties.ZuulRoute> currentRouteMap = this.locateRoutes();
        final List<ZuulProperties.ZuulRoute> insertRouteDefinition = new ArrayList<>(zuulRouteMap.size());
        final List<ZuulProperties.ZuulRoute> updateRouteDefinition = new ArrayList<>(zuulRouteMap.size());
        final List<ZuulProperties.ZuulRoute> deleteRouteDefinition = new ArrayList<>(zuulRouteMap.size());

        for (final Map.Entry<String, ZuulProperties.ZuulRoute> pair : zuulRouteMap.entrySet()) {
            if (!currentRouteMap.containsKey(pair.getKey())) {
                insertRouteDefinition.add(pair.getValue());
            }
        }

        for (final Map.Entry<String, ZuulProperties.ZuulRoute> pair : zuulRouteMap.entrySet()) {
            if (currentRouteMap.containsKey(pair.getKey())) {
                final ZuulProperties.ZuulRoute currentRouteDefinition = currentRouteMap.get(pair.getKey());
                final ZuulProperties.ZuulRoute newRouteDefinition = pair.getValue();
                if (!currentRouteDefinition.equals(newRouteDefinition)) {
                    updateRouteDefinition.add(newRouteDefinition);
                }
            }
        }

        for (final Map.Entry<String, ZuulProperties.ZuulRoute> pair : currentRouteMap.entrySet()) {
            if (!zuulRouteMap.containsKey(pair.getKey())) {
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
            this.publisher.publishEvent(new RoutesRefreshedEvent(this));
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

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        final Map<String, ZuulProperties.ZuulRoute> zuulRouteMap = super.locateRoutes();
        final Map<String, ZuulProperties.ZuulRoute> result = new LinkedHashMap<>((int) (0.75 / zuulRouteMap.size()));
        for (final Map.Entry<String, ZuulProperties.ZuulRoute> pair : zuulRouteMap.entrySet()) {
            result.put(pair.getValue().getId(), pair.getValue());
        }
        return result;
    }

    private ZuulProperties.ZuulRoute toZuulRoute(final DynamicRouteEntity dynamicRouteEntity) {
        final ZuulProperties.ZuulRoute result = new ZuulProperties.ZuulRoute();
        result.setId(dynamicRouteEntity.getRouteId());
        result.setPath(dynamicRouteEntity.getUri());
        result.setServiceId(dynamicRouteEntity.getServiceName());
        result.setUrl(null);
        result.setStripPrefix(true);
        result.setRetryable(null);
        result.setSensitiveHeaders(Sets.newHashSet());
        result.setCustomSensitiveHeaders(false);
        return result;
    }

    private void add(final ZuulProperties.ZuulRoute zuulRoute) {
        this.zuulProperties.getRoutes().put(zuulRoute.getId(), zuulRoute);
    }

    private void modify(final ZuulProperties.ZuulRoute zuulRoute) {
        this.zuulProperties.getRoutes().put(zuulRoute.getId(), zuulRoute);
    }

    private void delete(final ZuulProperties.ZuulRoute zuulRoute) {
        this.zuulProperties.getRoutes().remove(zuulRoute.getId());
    }
}