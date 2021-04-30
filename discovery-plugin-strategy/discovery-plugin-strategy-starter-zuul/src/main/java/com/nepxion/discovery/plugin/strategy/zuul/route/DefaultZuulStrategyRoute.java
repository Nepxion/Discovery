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
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.util.CollectionUtils;

import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.strategy.zuul.entity.ZuulStrategyRouteEntity;

// Zuul的存储结构
// zuulProperties.getRoutes()返回值的Key为routeId
// locateRoutes()返回值的Key为path
public class DefaultZuulStrategyRoute extends SimpleRouteLocator implements ZuulStrategyRoute, RefreshableRouteLocator, ApplicationEventPublisherAware {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultZuulStrategyRoute.class);

    @Autowired(required = false)
    private ZuulStrategyRouteAdapter zuulStrategyRouteAdapter;

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

    @PostConstruct
    public void retrieve() {
        if (zuulStrategyRouteAdapter == null) {
            return;
        }

        Map<String, ZuulStrategyRouteEntity> newRouteMap = zuulStrategyRouteAdapter.retrieve();
        if (MapUtils.isEmpty(newRouteMap)) {
            throw new DiscoveryException("Zuul dynamic routes are empty");
        }

        Map<String, ZuulProperties.ZuulRoute> routeMap = locateRoutes();

        for (Map.Entry<String, ZuulStrategyRouteEntity> entry : newRouteMap.entrySet()) {
            String path = entry.getKey();
            ZuulStrategyRouteEntity zuulStrategyRouteEntity = entry.getValue();

            // 如果从数据库等持久化的地方获取到的动态路由配置和配置文件里的静态路由配置存在重复的情况，则静态路由会被覆盖掉
            if (routeMap.containsKey(path)) {
                deleteRoute(routeMap.get(path));
            }

            ZuulProperties.ZuulRoute route = convert(zuulStrategyRouteEntity);
            addRoute(route);
        }

        LOG.info("Retrieved Zuul dynamic routes count={}", newRouteMap.size());

        applicationEventPublisher.publishEvent(new RoutesRefreshedEvent(this));
    }

    @Override
    public void add(ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        if (zuulStrategyRouteEntity == null) {
            throw new DiscoveryException("Zuul dynamic route is null");
        }

        Map<String, ZuulProperties.ZuulRoute> routeMap = locateRoutes();
        String path = zuulStrategyRouteEntity.getPath();
        if (routeMap.containsKey(path)) {
            throw new DiscoveryException("Zuul dynamic route for path=[" + path + "] exists");
        }

        ZuulProperties.ZuulRoute route = convert(zuulStrategyRouteEntity);
        addRoute(route);

        LOG.info("Added Zuul dynamic route={}", route);

        applicationEventPublisher.publishEvent(new RoutesRefreshedEvent(this));
    }

    @Override
    public void modify(List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList) {
        if (CollectionUtils.isEmpty(zuulStrategyRouteEntityList)) {
            throw new DiscoveryException("Zuul dynamic routes are empty");
        }

        if (zuulStrategyRouteEntityList.size() != 2) {
            throw new DiscoveryException("Zuul dynamic routes size must be two");
        }

        Map<String, ZuulProperties.ZuulRoute> routeMap = locateRoutes();
        String path = zuulStrategyRouteEntityList.get(0).getPath();
        if (!routeMap.containsKey(path)) {
            throw new DiscoveryException("Zuul dynamic route for path=[" + path + "] not exists");
        }

        deleteRoute(routeMap.get(path));

        ZuulProperties.ZuulRoute route = convert(zuulStrategyRouteEntityList.get(1));
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
            throw new DiscoveryException("Zuul dynamic route for routeId=[" + routeId + "] not exists");
        }

        deleteRoute(route);

        LOG.info("Deleted Zuul dynamic route for routeId={}", routeId);

        applicationEventPublisher.publishEvent(new RoutesRefreshedEvent(this));
    }

    @Override
    public ZuulStrategyRouteEntity view(String routeId) {
        if (StringUtils.isEmpty(routeId)) {
            throw new DiscoveryException("RouteId is empty");
        }

        ZuulProperties.ZuulRoute route = getRoute(routeId);
        if (route == null) {
            return null;
        }

        return convert(route);
    }

    @Override
    public List<ZuulStrategyRouteEntity> viewAll() {
        List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList = new ArrayList<ZuulStrategyRouteEntity>();

        Map<String, ZuulProperties.ZuulRoute> routeMap = locateRoutes();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routeMap.entrySet()) {
            ZuulProperties.ZuulRoute route = entry.getValue();
            ZuulStrategyRouteEntity zuulStrategyRouteEntity = convert(route);
            zuulStrategyRouteEntityList.add(zuulStrategyRouteEntity);
        }

        return zuulStrategyRouteEntityList;
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    private ZuulProperties.ZuulRoute convert(ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        ZuulProperties.ZuulRoute route = new ZuulProperties.ZuulRoute();
        route.setId(StringUtils.isNotBlank(zuulStrategyRouteEntity.getRouteId()) ? zuulStrategyRouteEntity.getRouteId() : zuulStrategyRouteEntity.getServiceName());
        route.setServiceId(zuulStrategyRouteEntity.getServiceName());
        route.setPath(zuulStrategyRouteEntity.getPath());
        route.setUrl(zuulStrategyRouteEntity.getUrl());
        route.setStripPrefix(zuulStrategyRouteEntity.isStripPrefix());
        route.setRetryable(zuulStrategyRouteEntity.getRetryable());
        route.setSensitiveHeaders(zuulStrategyRouteEntity.getSensitiveHeaders());
        route.setCustomSensitiveHeaders(zuulStrategyRouteEntity.getSensitiveHeaders() != null && !zuulStrategyRouteEntity.getSensitiveHeaders().isEmpty());

        return route;
    }

    private ZuulStrategyRouteEntity convert(ZuulProperties.ZuulRoute route) {
        ZuulStrategyRouteEntity zuulStrategyRouteEntity = new ZuulStrategyRouteEntity();
        zuulStrategyRouteEntity.setRouteId(route.getId());
        zuulStrategyRouteEntity.setServiceName(route.getServiceId());
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
}