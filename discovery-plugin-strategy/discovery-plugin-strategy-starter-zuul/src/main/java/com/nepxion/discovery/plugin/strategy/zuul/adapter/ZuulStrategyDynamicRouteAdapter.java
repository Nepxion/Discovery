package com.nepxion.discovery.plugin.strategy.zuul.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ning Zhang
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.google.common.collect.Sets;
import com.nepxion.discovery.common.entity.DynamicRouteEntity;
import com.nepxion.discovery.plugin.strategy.adapter.StrategyDynamicRouteAdapter;

public class ZuulStrategyDynamicRouteAdapter extends SimpleRouteLocator implements StrategyDynamicRouteAdapter, RefreshableRouteLocator, ApplicationEventPublisherAware {
    private final ZuulProperties zuulProperties;
    private ApplicationEventPublisher applicationEventPublisher;

    public ZuulStrategyDynamicRouteAdapter(final String servletPath, final ZuulProperties zuulProperties) {
        super(servletPath, zuulProperties);

        this.zuulProperties = zuulProperties;
    }

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void update(final List<DynamicRouteEntity> dynamicRouteEntityList) {
        final Map<String, ZuulProperties.ZuulRoute> dynamicRouteDefinitionMap = dynamicRouteEntityList.stream().collect(Collectors.toMap(DynamicRouteEntity::getRouteId, this::toZuulRoute));
        final Map<String, ZuulProperties.ZuulRoute> currentRouteDefinitionMap = locateRoutes();

        final List<ZuulProperties.ZuulRoute> insertRouteDefinition = new ArrayList<>(dynamicRouteDefinitionMap.size());
        final List<ZuulProperties.ZuulRoute> updateRouteDefinition = new ArrayList<>(dynamicRouteDefinitionMap.size());
        final List<ZuulProperties.ZuulRoute> deleteRouteDefinition = new ArrayList<>(dynamicRouteDefinitionMap.size());

        for (final Map.Entry<String, ZuulProperties.ZuulRoute> pair : dynamicRouteDefinitionMap.entrySet()) {
            if (!currentRouteDefinitionMap.containsKey(pair.getKey())) {
                insertRouteDefinition.add(pair.getValue());
            }
        }

        for (final Map.Entry<String, ZuulProperties.ZuulRoute> pair : dynamicRouteDefinitionMap.entrySet()) {
            if (currentRouteDefinitionMap.containsKey(pair.getKey())) {
                final ZuulProperties.ZuulRoute currentRouteDefinition = currentRouteDefinitionMap.get(pair.getKey());
                final ZuulProperties.ZuulRoute newRouteDefinition = pair.getValue();
                if (!currentRouteDefinition.equals(newRouteDefinition)) {
                    updateRouteDefinition.add(newRouteDefinition);
                }
            }
        }

        for (final Map.Entry<String, ZuulProperties.ZuulRoute> pair : currentRouteDefinitionMap.entrySet()) {
            if (!dynamicRouteDefinitionMap.containsKey(pair.getKey())) {
                deleteRouteDefinition.add(pair.getValue());
            }
        }

        for (final ZuulProperties.ZuulRoute routeDefinition : insertRouteDefinition) {
            add(routeDefinition);
        }

        for (final ZuulProperties.ZuulRoute routeDefinition : updateRouteDefinition) {
            modify(routeDefinition);
        }

        for (final ZuulProperties.ZuulRoute routeDefinition : deleteRouteDefinition) {
            delete(routeDefinition);
        }

        if (!insertRouteDefinition.isEmpty() || !updateRouteDefinition.isEmpty() || !deleteRouteDefinition.isEmpty()) {
            applicationEventPublisher.publishEvent(new RoutesRefreshedEvent(this));
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
        final ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
        zuulRoute.setId(dynamicRouteEntity.getRouteId());
        zuulRoute.setPath(dynamicRouteEntity.getUri());
        zuulRoute.setServiceId(dynamicRouteEntity.getServiceName());
        zuulRoute.setUrl(null);
        zuulRoute.setStripPrefix(true);
        zuulRoute.setRetryable(null);
        zuulRoute.setSensitiveHeaders(Sets.newHashSet());
        zuulRoute.setCustomSensitiveHeaders(false);

        return zuulRoute;
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