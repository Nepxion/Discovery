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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.ZuulStrategyRouteEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.framework.event.PluginPublisher;
import com.nepxion.discovery.plugin.strategy.zuul.event.ZuulStrategyRouteAddedEvent;
import com.nepxion.discovery.plugin.strategy.zuul.event.ZuulStrategyRouteDeletedEvent;
import com.nepxion.discovery.plugin.strategy.zuul.event.ZuulStrategyRouteModifiedEvent;
import com.nepxion.discovery.plugin.strategy.zuul.event.ZuulStrategyRouteUpdatedAllEvent;

// Zuul的存储结构
// zuulProperties.getRoutes()返回值的Key为routeId
// locateRoutes()返回值的Key为path
public abstract class AbstractZuulStrategyRoute extends SimpleRouteLocator implements ZuulStrategyRoute, RefreshableRouteLocator, ApplicationEventPublisherAware {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractZuulStrategyRoute.class);

    @Autowired
    private PluginPublisher pluginPublisher;

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
    public synchronized void add(ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        if (zuulStrategyRouteEntity == null) {
            throw new DiscoveryException("Zuul dynamic route is null");
        }

        Map<String, ZuulProperties.ZuulRoute> idRouteMap = zuulProperties.getRoutes();
        String routeId = zuulStrategyRouteEntity.getId();
        if (idRouteMap.containsKey(routeId)) {
            throw new DiscoveryException("Zuul dynamic route for routeId=[" + routeId + "] is duplicated");
        }

        /*Map<String, ZuulProperties.ZuulRoute> pathRouteMap = locateRoutes();
        String path = zuulStrategyRouteEntity.getPath();
        if (pathRouteMap.containsKey(path)) {
            throw new DiscoveryException("Zuul dynamic route for path=[" + path + "] is duplicated");
        }*/

        ZuulProperties.ZuulRoute route = convertRoute(zuulStrategyRouteEntity);
        addRoute(route);

        LOG.info("Added Zuul dynamic route={}", route);

        applicationEventPublisher.publishEvent(new RoutesRefreshedEvent(this));

        pluginPublisher.asyncPublish(new ZuulStrategyRouteAddedEvent(zuulStrategyRouteEntity));
    }

    @Override
    public synchronized void modify(ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
        if (zuulStrategyRouteEntity == null) {
            throw new DiscoveryException("Zuul dynamic route is null");
        }

        Map<String, ZuulProperties.ZuulRoute> idRouteMap = zuulProperties.getRoutes();
        String routeId = zuulStrategyRouteEntity.getId();
        if (!idRouteMap.containsKey(routeId)) {
            throw new DiscoveryException("Zuul dynamic route for routeId=[" + routeId + "] isn't found");
        }

        /*Map<String, ZuulProperties.ZuulRoute> pathRouteMap = locateRoutes();
        String path = zuulStrategyRouteEntity.getPath();
        if (pathRouteMap.containsKey(path)) {
            throw new DiscoveryException("Zuul dynamic route for path=[" + path + "] is duplicated");
        }*/

        ZuulProperties.ZuulRoute route = convertRoute(zuulStrategyRouteEntity);
        modifyRoute(route);

        LOG.info("Modified Zuul dynamic route={}", route);

        applicationEventPublisher.publishEvent(new RoutesRefreshedEvent(this));

        pluginPublisher.asyncPublish(new ZuulStrategyRouteModifiedEvent(zuulStrategyRouteEntity));
    }

    @Override
    public synchronized void delete(String routeId) {
        if (StringUtils.isEmpty(routeId)) {
            throw new DiscoveryException("RouteId is empty");
        }

        Map<String, ZuulProperties.ZuulRoute> idRouteMap = zuulProperties.getRoutes();
        if (!idRouteMap.containsKey(routeId)) {
            throw new DiscoveryException("Zuul dynamic route for routeId=[" + routeId + "] isn't found");
        }

        ZuulProperties.ZuulRoute route = idRouteMap.get(routeId);
        deleteRoute(route);

        LOG.info("Deleted Zuul dynamic route for routeId={}", routeId);

        applicationEventPublisher.publishEvent(new RoutesRefreshedEvent(this));

        pluginPublisher.asyncPublish(new ZuulStrategyRouteDeletedEvent(routeId));
    }

    @Override
    public synchronized void updateAll(List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList) {
        if (zuulStrategyRouteEntityList == null) {
            throw new DiscoveryException("Zuul dynamic routes are null");
        }

        boolean isIdDuplicated = isIdDuplicated(zuulStrategyRouteEntityList);
        if (isIdDuplicated) {
            throw new DiscoveryException("Zuul dynamic routes have duplicated routeIds");
        }

        /*boolean isPathDuplicated = isPathDuplicated(zuulStrategyRouteEntityList);
        if (isPathDuplicated) {
            throw new DiscoveryException("Zuul dynamic routes have duplicated paths");
        }*/

        Map<String, ZuulProperties.ZuulRoute> dynamicRouteMap = zuulStrategyRouteEntityList.stream().collect(Collectors.toMap(ZuulStrategyRouteEntity::getId, this::convertRoute));
        Map<String, ZuulProperties.ZuulRoute> currentRouteMap = zuulProperties.getRoutes();

        List<ZuulProperties.ZuulRoute> addRouteList = new ArrayList<ZuulProperties.ZuulRoute>(dynamicRouteMap.size());
        List<ZuulProperties.ZuulRoute> modifyRouteList = new ArrayList<ZuulProperties.ZuulRoute>(dynamicRouteMap.size());
        List<ZuulProperties.ZuulRoute> deleteRouteList = new ArrayList<ZuulProperties.ZuulRoute>(dynamicRouteMap.size());

        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : dynamicRouteMap.entrySet()) {
            String routeId = entry.getKey();
            ZuulProperties.ZuulRoute route = entry.getValue();
            if (!currentRouteMap.containsKey(routeId)) {
                addRouteList.add(route);
            }
        }

        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : dynamicRouteMap.entrySet()) {
            String routeId = entry.getKey();
            ZuulProperties.ZuulRoute route = entry.getValue();
            if (currentRouteMap.containsKey(routeId)) {
                ZuulProperties.ZuulRoute currentRoute = currentRouteMap.get(routeId);
                if (!currentRoute.equals(route)) {
                    modifyRouteList.add(route);
                }
            }
        }

        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : currentRouteMap.entrySet()) {
            String routeId = entry.getKey();
            ZuulProperties.ZuulRoute route = entry.getValue();
            if (!dynamicRouteMap.containsKey(routeId)) {
                deleteRouteList.add(route);
            }
        }

        for (ZuulProperties.ZuulRoute zuulRoute : addRouteList) {
            addRoute(zuulRoute);
        }

        for (ZuulProperties.ZuulRoute zuulRoute : modifyRouteList) {
            modifyRoute(zuulRoute);
        }

        for (ZuulProperties.ZuulRoute zuulRoute : deleteRouteList) {
            deleteRoute(zuulRoute);
        }

        LOG.info("----- Zuul Dynamic Routes Update Information -----");
        LOG.info("Total count={}", zuulStrategyRouteEntityList.size());
        LOG.info("Added count={}", addRouteList.size());
        LOG.info("Modified count={}", modifyRouteList.size());
        LOG.info("Deleted count={}", deleteRouteList.size());
        LOG.info("--------------------------------------------------"); 

        if (addRouteList.isEmpty() && modifyRouteList.isEmpty() && deleteRouteList.isEmpty()) {
            return;
        }

        applicationEventPublisher.publishEvent(new RoutesRefreshedEvent(this));

        pluginPublisher.asyncPublish(new ZuulStrategyRouteUpdatedAllEvent(zuulStrategyRouteEntityList));
    }

    @Override
    public synchronized void updateAll(String zuulStrategyRouteConfig) {
        if (StringUtils.isBlank(zuulStrategyRouteConfig)) {
            zuulStrategyRouteConfig = DiscoveryConstant.EMPTY_JSON_RULE_MULTIPLE;
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

        Map<String, ZuulProperties.ZuulRoute> idRouteMap = zuulProperties.getRoutes();
        if (!idRouteMap.containsKey(routeId)) {
            throw new DiscoveryException("Zuul dynamic route for routeId=[" + routeId + "] isn't found");
        }

        ZuulProperties.ZuulRoute route = idRouteMap.get(routeId);

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

    public boolean isIdDuplicated(List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList) {
        Set<ZuulStrategyRouteEntity> zuulStrategyRouteEntitySet = new TreeSet<ZuulStrategyRouteEntity>(new Comparator<ZuulStrategyRouteEntity>() {
            public int compare(ZuulStrategyRouteEntity zuulStrategyRouteEntity1, ZuulStrategyRouteEntity zuulStrategyRouteEntity2) {
                return zuulStrategyRouteEntity1.getId().compareTo(zuulStrategyRouteEntity2.getId());
            }
        });
        zuulStrategyRouteEntitySet.addAll(zuulStrategyRouteEntityList);

        return zuulStrategyRouteEntitySet.size() < zuulStrategyRouteEntityList.size();
    }

    public boolean isPathDuplicated(List<ZuulStrategyRouteEntity> zuulStrategyRouteEntityList) {
        Set<ZuulStrategyRouteEntity> zuulStrategyRouteEntitySet = new TreeSet<ZuulStrategyRouteEntity>(new Comparator<ZuulStrategyRouteEntity>() {
            public int compare(ZuulStrategyRouteEntity zuulStrategyRouteEntity1, ZuulStrategyRouteEntity zuulStrategyRouteEntity2) {
                return zuulStrategyRouteEntity1.getPath().compareTo(zuulStrategyRouteEntity2.getPath());
            }
        });
        zuulStrategyRouteEntitySet.addAll(zuulStrategyRouteEntityList);

        return zuulStrategyRouteEntitySet.size() < zuulStrategyRouteEntityList.size();
    }

    public ZuulProperties.ZuulRoute convertRoute(ZuulStrategyRouteEntity zuulStrategyRouteEntity) {
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

    public ZuulStrategyRouteEntity convertRoute(ZuulProperties.ZuulRoute route) {
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

    public void addRoute(ZuulProperties.ZuulRoute route) {
        zuulProperties.getRoutes().put(route.getId(), route);
    }

    public void modifyRoute(ZuulProperties.ZuulRoute route) {
        zuulProperties.getRoutes().put(route.getId(), route);
    }

    public void deleteRoute(ZuulProperties.ZuulRoute route) {
        zuulProperties.getRoutes().remove(route.getId());
    }

    public void clearRoutes() {
        zuulProperties.getRoutes().clear();
    }
}