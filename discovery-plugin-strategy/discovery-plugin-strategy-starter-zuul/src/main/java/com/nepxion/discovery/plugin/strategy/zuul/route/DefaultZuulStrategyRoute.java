package com.nepxion.discovery.plugin.strategy.zuul.route;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ning Zhang
 * @version 1.0
 */

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.nepxion.discovery.common.entity.ZuulRouteEntity;

public class DefaultZuulStrategyRoute extends SimpleRouteLocator implements ZuulStrategyRoute, RefreshableRouteLocator, ApplicationEventPublisherAware {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultZuulStrategyRoute.class);

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
    public void update(List<ZuulRouteEntity> zuulRouteEntityList) {
        LOG.info("Updated Zuul strategy routes={}", zuulRouteEntityList);

        Map<String, ZuulProperties.ZuulRoute> newRouteMap = zuulRouteEntityList.stream().collect(Collectors.toMap(ZuulRouteEntity::getRouteId, this::convertRoute));
        Map<String, ZuulProperties.ZuulRoute> currentRouteMap = locateRoutes();

        boolean isChanged = false;

        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : newRouteMap.entrySet()) {
            String routeId = entry.getKey();
            ZuulRoute route = entry.getValue();
            if (!currentRouteMap.containsKey(routeId)) {
                add(route);

                isChanged = true;
            }

            if (currentRouteMap.containsKey(routeId)) {
                ZuulProperties.ZuulRoute currentRoute = currentRouteMap.get(routeId);
                if (!currentRoute.equals(route)) {
                    modify(route);

                    isChanged = true;
                }
            }
        }

        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : currentRouteMap.entrySet()) {
            String routeId = entry.getKey();
            ZuulRoute route = entry.getValue();
            if (!newRouteMap.containsKey(routeId)) {
                delete(route);

                isChanged = true;
            }
        }

        if (isChanged) {
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

    private ZuulProperties.ZuulRoute convertRoute(ZuulRouteEntity zuulRouteEntity) {
        ZuulProperties.ZuulRoute route = new ZuulProperties.ZuulRoute();
        route.setId(zuulRouteEntity.getRouteId());
        route.setServiceId(zuulRouteEntity.getServiceName());
        route.setPath(zuulRouteEntity.getPath());
        route.setUrl(zuulRouteEntity.getUrl());
        route.setStripPrefix(zuulRouteEntity.isStripPrefix());
        route.setRetryable(zuulRouteEntity.getRetryable());
        route.setSensitiveHeaders(zuulRouteEntity.getSensitiveHeaders());

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