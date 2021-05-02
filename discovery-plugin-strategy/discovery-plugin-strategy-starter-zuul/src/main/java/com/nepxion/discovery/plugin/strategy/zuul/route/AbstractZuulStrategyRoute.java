package com.nepxion.discovery.plugin.strategy.zuul.route;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ning Zhang
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.strategy.zuul.entity.ZuulStrategyRouteEntity;

// Zuul的存储结构
// zuulProperties.getRoutes()返回值的Key为routeId
// locateRoutes()返回值的Key为path
public abstract class AbstractZuulStrategyRoute extends SimpleRouteLocator implements ZuulStrategyRoute, RefreshableRouteLocator, ApplicationEventPublisherAware {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractZuulStrategyRoute.class);

    private ZuulProperties zuulProperties;

    private ApplicationEventPublisher applicationEventPublisher;

    public AbstractZuulStrategyRoute(String servletPath, ZuulProperties zuulProperties) {
        super(servletPath, zuulProperties);

        this.zuulProperties = zuulProperties;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void add(ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        if (zuulStrategyRouteEntity == null) {
            throw new DiscoveryException("Zuul dynamic route is null");
        }

        Map<String, ZuulProperties.ZuulRoute> idRouteMap = zuulProperties.getRoutes();
        String routeId = zuulStrategyRouteEntity.getId();
        if (idRouteMap.containsKey(routeId)) {
            throw new DiscoveryException("Zuul dynamic route for routeId=[" + routeId + "] is duplicated");
        }

        Map<String, ZuulProperties.ZuulRoute> pathRouteMap = locateRoutes();
        String path = zuulStrategyRouteEntity.getPath();
        if (pathRouteMap.containsKey(path)) {
            throw new DiscoveryException("Zuul dynamic route for path=[" + path + "] is duplicated");
        }

        ZuulProperties.ZuulRoute route = convertRoute(zuulStrategyRouteEntity);
        addRoute(route);

        LOG.info("Added Zuul dynamic route={}", route);

        applicationEventPublisher.publishEvent(new RoutesRefreshedEvent(this));
    }

    @Override
    public void modify(ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        if (zuulStrategyRouteEntity == null) {
            throw new DiscoveryException("Zuul dynamic route is null");
        }

        String routeId = zuulStrategyRouteEntity.getId();
        ZuulProperties.ZuulRoute route = getRoute(routeId);
        if (route == null) {
            throw new DiscoveryException("Zuul dynamic route for routeId=[" + routeId + "] isn't found");
        }

        route = convertRoute(zuulStrategyRouteEntity);
        modifyRoute(route);

        LOG.info("Modified Zuul dynamic route={}", route);

        applicationEventPublisher.publishEvent(new RoutesRefreshedEvent(this));
    }

    @Override
    public void delete(String routeId) {
        if (StringUtils.isEmpty(routeId)) {
            throw new DiscoveryException("RouteId is empty");
        }

        ZuulProperties.ZuulRoute route = getRoute(routeId);
        if (route == null) {
            throw new DiscoveryException("Zuul dynamic route for routeId=[" + routeId + "] isn't found");
        }

        deleteRoute(route);

        LOG.info("Deleted Zuul dynamic route for routeId={}", routeId);

        applicationEventPublisher.publishEvent(new RoutesRefreshedEvent(this));
    }

    @Override
    public void updateAll(List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList) {
        if (zuulStrategyRouteEntityList == null) {
            throw new DiscoveryException("Zuul dynamic routes are null");
        }

        clearRoutes();

        for (ZuulStrategyRouteEntity zuulStrategyRouteEntity : zuulStrategyRouteEntityList) {
            ZuulProperties.ZuulRoute route = convertRoute(zuulStrategyRouteEntity);
            addRoute(route);
        }

        LOG.info("Updated Zuul dynamic routes count={}", zuulStrategyRouteEntityList.size());

        applicationEventPublisher.publishEvent(new RoutesRefreshedEvent(this));
    }

    @Override
    public void updateAll(String zuulStrategyRouteConfig) {
        if (StringUtils.isEmpty(zuulStrategyRouteConfig)) {
            LOG.info("Zuul dynamic route config is empty");

            return;
        }

        List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList = JsonUtil.fromJson(zuulStrategyRouteConfig, new TypeReference<List<ZuulStrategyRouteEntity>>() {
        });

        updateAll(zuulStrategyRouteEntityList);
    }

    @Override
    public ZuulStrategyRouteEntity view(String routeId) {
        if (StringUtils.isEmpty(routeId)) {
            throw new DiscoveryException("RouteId is empty");
        }

        ZuulProperties.ZuulRoute route = getRoute(routeId);
        if (route == null) {
            throw new DiscoveryException("Zuul dynamic route for routeId=[" + routeId + "] isn't found");
        }

        return convertRoute(route);
    }

    @Override
    public List<ZuulStrategyRouteEntity> viewAll() {
        List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList = new ArrayList<ZuulStrategyRouteEntity>();

        Map<String, ZuulProperties.ZuulRoute> routeMap = locateRoutes();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routeMap.entrySet()) {
            ZuulProperties.ZuulRoute route = entry.getValue();
            ZuulStrategyRouteEntity zuulStrategyRouteEntity = convertRoute(route);
            zuulStrategyRouteEntityList.add(zuulStrategyRouteEntity);
        }

        return zuulStrategyRouteEntityList;
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    private ZuulProperties.ZuulRoute convertRoute(ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        ZuulProperties.ZuulRoute route = new ZuulProperties.ZuulRoute();
        route.setId(StringUtils.isNotBlank(zuulStrategyRouteEntity.getId()) ? zuulStrategyRouteEntity.getId() : zuulStrategyRouteEntity.getServiceId());
        route.setServiceId(zuulStrategyRouteEntity.getServiceId());
        route.setPath(zuulStrategyRouteEntity.getPath());
        route.setUrl(zuulStrategyRouteEntity.getUrl());
        route.setStripPrefix(zuulStrategyRouteEntity.isStripPrefix());
        route.setRetryable(zuulStrategyRouteEntity.getRetryable());
        route.setSensitiveHeaders(zuulStrategyRouteEntity.getSensitiveHeaders());
        route.setCustomSensitiveHeaders(zuulStrategyRouteEntity.getSensitiveHeaders() != null && !zuulStrategyRouteEntity.getSensitiveHeaders().isEmpty());

        return route;
    }

    private ZuulStrategyRouteEntity convertRoute(ZuulProperties.ZuulRoute route) {
        ZuulStrategyRouteEntity zuulStrategyRouteEntity = new ZuulStrategyRouteEntity();
        zuulStrategyRouteEntity.setId(route.getId());
        zuulStrategyRouteEntity.setServiceId(route.getServiceId());
        zuulStrategyRouteEntity.setPath(route.getPath());
        zuulStrategyRouteEntity.setUrl(route.getUrl());
        zuulStrategyRouteEntity.setStripPrefix(route.isStripPrefix());
        zuulStrategyRouteEntity.setRetryable(route.getRetryable());
        zuulStrategyRouteEntity.setSensitiveHeaders(route.getSensitiveHeaders());
        zuulStrategyRouteEntity.setCustomSensitiveHeaders(route.isCustomSensitiveHeaders());

        return zuulStrategyRouteEntity;
    }

    private ZuulProperties.ZuulRoute getRoute(String routeId) {
        Map<String, ZuulProperties.ZuulRoute> routeMap = locateRoutes();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routeMap.entrySet()) {
            ZuulProperties.ZuulRoute route = entry.getValue();
            if (StringUtils.equals(routeId, route.getId())) {
                return route;
            }
        }

        return null;
    }

    private void addRoute(ZuulProperties.ZuulRoute route) {
        zuulProperties.getRoutes().put(route.getId(), route);
    }

    private void modifyRoute(ZuulProperties.ZuulRoute route) {
        zuulProperties.getRoutes().put(route.getId(), route);
    }

    private void deleteRoute(ZuulProperties.ZuulRoute route) {
        zuulProperties.getRoutes().remove(route.getId());
    }

    private void clearRoutes() {
        zuulProperties.getRoutes().clear();
    }
}