package com.nepxion.discovery.plugin.strategy.zuul.route;

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
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.nepxion.discovery.plugin.strategy.zuul.entity.ZuulStrategyRouteEntity;

public class DefaultZuulStrategyRoute extends SimpleRouteLocator implements ZuulStrategyRoute, RefreshableRouteLocator, ApplicationEventPublisherAware {
    private ZuulProperties zuulProperties;
    private ApplicationEventPublisher applicationEventPublisher;

    public DefaultZuulStrategyRoute(String servletPath, ZuulProperties zuulProperties) {
        super(servletPath, zuulProperties);

        this.zuulProperties = zuulProperties;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void update(List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList) {
        Map<String, ZuulProperties.ZuulRoute> newRouteMap = zuulStrategyRouteEntityList.stream().collect(Collectors.toMap(ZuulStrategyRouteEntity::getRouteId, this::convertRoute));
        Map<String, ZuulProperties.ZuulRoute> currentRouteMap = locateRoutes();

        List<ZuulProperties.ZuulRoute> insertRoute = new ArrayList<>(newRouteMap.size());
        List<ZuulProperties.ZuulRoute> updateRoute = new ArrayList<>(newRouteMap.size());
        List<ZuulProperties.ZuulRoute> deleteRoute = new ArrayList<>(newRouteMap.size());

        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : newRouteMap.entrySet()) {
            String routeId = entry.getKey();
            ZuulRoute route = entry.getValue();
            if (!currentRouteMap.containsKey(routeId)) {
                insertRoute.add(route);
            }
        }

        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : newRouteMap.entrySet()) {
            String routeId = entry.getKey();
            if (currentRouteMap.containsKey(routeId)) {
                ZuulProperties.ZuulRoute currentRoute = currentRouteMap.get(routeId);
                ZuulProperties.ZuulRoute newRoute = entry.getValue();
                if (!currentRoute.equals(newRoute)) {
                    updateRoute.add(newRoute);
                }
            }
        }

        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : currentRouteMap.entrySet()) {
            String routeId = entry.getKey();
            ZuulRoute route = entry.getValue();
            if (!newRouteMap.containsKey(routeId)) {
                deleteRoute.add(route);
            }
        }

        for (ZuulProperties.ZuulRoute route : insertRoute) {
            add(route);
        }

        for (ZuulProperties.ZuulRoute route : updateRoute) {
            modify(route);
        }

        for (ZuulProperties.ZuulRoute route : deleteRoute) {
            delete(route);
        }

        if (!insertRoute.isEmpty() || !updateRoute.isEmpty() || !deleteRoute.isEmpty()) {
            applicationEventPublisher.publishEvent(new RoutesRefreshedEvent(this));
        }
    }

    @Override
    public List<String> view() {
        return super.locateRoutes().values().stream().map(ZuulProperties.ZuulRoute::toString).collect(Collectors.toList());
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        Map<String, ZuulProperties.ZuulRoute> locateRouteMap = super.locateRoutes();
        Map<String, ZuulProperties.ZuulRoute> routeMap = new LinkedHashMap<>((int) (0.75 / locateRouteMap.size()));

        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : locateRouteMap.entrySet()) {
            String routeId = entry.getKey();
            ZuulRoute route = entry.getValue();
            
            routeMap.put(routeId, route);
        }

        return routeMap;
    }

    private ZuulProperties.ZuulRoute convertRoute(ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        ZuulProperties.ZuulRoute route = new ZuulProperties.ZuulRoute();
        route.setId(zuulStrategyRouteEntity.getRouteId());
        route.setServiceId(zuulStrategyRouteEntity.getServiceName());
        route.setPath(zuulStrategyRouteEntity.getPath());
        route.setUrl(zuulStrategyRouteEntity.getUrl());
        route.setStripPrefix(zuulStrategyRouteEntity.isStripPrefix());
        route.setRetryable(zuulStrategyRouteEntity.getRetryable());
        route.setSensitiveHeaders(zuulStrategyRouteEntity.getSensitiveHeaders());

        return route;
    }

    private void add(ZuulProperties.ZuulRoute route) {
        zuulProperties.getRoutes().put(route.getId(), route);
    }

    private void modify(ZuulProperties.ZuulRoute route) {
        zuulProperties.getRoutes().put(route.getId(), route);
    }

    private void delete(ZuulProperties.ZuulRoute route) {
        zuulProperties.getRoutes().remove(route.getId());
    }
}