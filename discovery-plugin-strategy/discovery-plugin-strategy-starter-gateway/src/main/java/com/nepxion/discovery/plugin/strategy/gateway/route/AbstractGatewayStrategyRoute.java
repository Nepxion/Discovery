package com.nepxion.discovery.plugin.strategy.gateway.route;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ning Zhang
 * @author Haojun Ren
 * @version 1.0
 */

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.framework.event.PluginPublisher;
import com.nepxion.discovery.plugin.strategy.gateway.entity.GatewayStrategyRouteEntity;
import com.nepxion.discovery.plugin.strategy.gateway.event.GatewayStrategyRouteAddedEvent;
import com.nepxion.discovery.plugin.strategy.gateway.event.GatewayStrategyRouteDeletedEvent;
import com.nepxion.discovery.plugin.strategy.gateway.event.GatewayStrategyRouteModifiedEvent;
import com.nepxion.discovery.plugin.strategy.gateway.event.GatewayStrategyRouteUpdatedAllEvent;

public abstract class AbstractGatewayStrategyRoute implements GatewayStrategyRoute, ApplicationEventPublisherAware {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractGatewayStrategyRoute.class);

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    @Autowired
    private GatewayProperties gatewayProperties;

    @Autowired
    private PluginPublisher pluginPublisher;

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public synchronized void add(GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        if (gatewayStrategyRouteEntity == null) {
            throw new DiscoveryException("Gateway dynamic route is null");
        }

        Map<String, RouteDefinition> routeDefinitionMap = locateRoutes();
        String routeId = gatewayStrategyRouteEntity.getId();
        if (routeDefinitionMap.containsKey(routeId)) {
            throw new DiscoveryException("Gateway dynamic route for routeId=[" + routeId + "] is duplicated");
        }

        RouteDefinition routeDefinition = convertRoute(gatewayStrategyRouteEntity);
        addRoute(routeDefinition);

        LOG.info("Added Gateway dynamic route={}", routeDefinition);

        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));

        pluginPublisher.asyncPublish(new GatewayStrategyRouteAddedEvent(gatewayStrategyRouteEntity));
    }

    @Override
    public synchronized void modify(GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        if (gatewayStrategyRouteEntity == null) {
            throw new DiscoveryException("Gateway dynamic route is null");
        }

        Map<String, RouteDefinition> routeDefinitionMap = locateRoutes();
        String routeId = gatewayStrategyRouteEntity.getId();

        if (!routeDefinitionMap.containsKey(routeId)) {
            throw new DiscoveryException("Gateway dynamic route for routeId=[" + routeId + "] isn't found");
        }

        RouteDefinition routeDefinition = convertRoute(gatewayStrategyRouteEntity);
        modifyRoute(routeDefinition);

        LOG.info("Modified Gateway dynamic route={}", routeDefinition);

        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));

        pluginPublisher.asyncPublish(new GatewayStrategyRouteModifiedEvent(gatewayStrategyRouteEntity));
    }

    @Override
    public synchronized void delete(String routeId) {
        if (StringUtils.isEmpty(routeId)) {
            throw new DiscoveryException("RouteId is empty");
        }

        Map<String, RouteDefinition> routeDefinitionMap = locateRoutes();
        RouteDefinition routeDefinition = routeDefinitionMap.get(routeId);
        if (routeDefinition == null) {
            throw new DiscoveryException("Gateway dynamic route for routeId=[" + routeId + "] isn't found");
        }

        deleteRoute(routeDefinition);

        LOG.info("Deleted Gateway dynamic route for routeId={}", routeId);

        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));

        pluginPublisher.asyncPublish(new GatewayStrategyRouteDeletedEvent(routeId));
    }

    @Override
    public synchronized void updateAll(List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList) {
        if (gatewayStrategyRouteEntityList == null) {
            throw new DiscoveryException("Gateway dynamic routes are null");
        }

        boolean isIdDuplicated = isIdDuplicated(gatewayStrategyRouteEntityList);
        if (isIdDuplicated) {
            throw new DiscoveryException("Gateway dynamic routes have duplicated routeIds");
        }

        Map<String, RouteDefinition> dynamicRouteDefinitionMap = gatewayStrategyRouteEntityList.stream().collect(Collectors.toMap(GatewayStrategyRouteEntity::getId, this::convertRoute));
        Map<String, RouteDefinition> currentRouteDefinitionMap = locateRoutes();

        List<RouteDefinition> addRouteDefinitionList = new ArrayList<RouteDefinition>(dynamicRouteDefinitionMap.size());
        List<RouteDefinition> modifyRouteDefinitionList = new ArrayList<RouteDefinition>(dynamicRouteDefinitionMap.size());
        List<RouteDefinition> deleteRouteDefinitionList = new ArrayList<RouteDefinition>(dynamicRouteDefinitionMap.size());

        for (Map.Entry<String, RouteDefinition> entry : dynamicRouteDefinitionMap.entrySet()) {
            String routeId = entry.getKey();
            RouteDefinition routeDefinition = entry.getValue();
            if (!currentRouteDefinitionMap.containsKey(routeId)) {
                addRouteDefinitionList.add(routeDefinition);
            }
        }

        for (Map.Entry<String, RouteDefinition> entry : dynamicRouteDefinitionMap.entrySet()) {
            String routeId = entry.getKey();
            RouteDefinition routeDefinition = entry.getValue();
            if (currentRouteDefinitionMap.containsKey(routeId)) {
                RouteDefinition currentRouteDefinition = currentRouteDefinitionMap.get(routeId);
                if (!currentRouteDefinition.equals(routeDefinition)) {
                    modifyRouteDefinitionList.add(routeDefinition);
                }
            }
        }

        for (Map.Entry<String, RouteDefinition> entry : currentRouteDefinitionMap.entrySet()) {
            String routeId = entry.getKey();
            RouteDefinition routeDefinition = entry.getValue();
            if (!dynamicRouteDefinitionMap.containsKey(routeId)) {
                deleteRouteDefinitionList.add(routeDefinition);
            }
        }

        for (RouteDefinition routeDefinition : addRouteDefinitionList) {
            addRoute(routeDefinition);
        }

        for (RouteDefinition routeDefinition : modifyRouteDefinitionList) {
            modifyRoute(routeDefinition);
        }

        for (RouteDefinition routeDefinition : deleteRouteDefinitionList) {
            deleteRoute(routeDefinition);
        }

        LOG.info("--- Gateway Dynamic Routes Update Information ----");
        LOG.info("Total count={}", gatewayStrategyRouteEntityList.size());
        LOG.info("Added count={}", addRouteDefinitionList.size());
        LOG.info("Modified count={}", modifyRouteDefinitionList.size());
        LOG.info("Deleted count={}", deleteRouteDefinitionList.size());
        LOG.info("--------------------------------------------------");

        if (addRouteDefinitionList.isEmpty() && modifyRouteDefinitionList.isEmpty() && deleteRouteDefinitionList.isEmpty()) {
            return;
        }

        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));

        pluginPublisher.asyncPublish(new GatewayStrategyRouteUpdatedAllEvent(gatewayStrategyRouteEntityList));
    }

    @Override
    public synchronized void updateAll(String gatewayStrategyRouteConfig) {
        if (StringUtils.isEmpty(gatewayStrategyRouteConfig)) {
            LOG.info("Gateway dynamic route config is empty");

            return;
        }

        List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList = JsonUtil.fromJson(gatewayStrategyRouteConfig, new TypeReference<List<GatewayStrategyRouteEntity>>() {
        });

        updateAll(gatewayStrategyRouteEntityList);
    }

    @Override
    public GatewayStrategyRouteEntity view(String routeId) {
        if (StringUtils.isEmpty(routeId)) {
            throw new DiscoveryException("RouteId is empty");
        }

        RouteDefinition routeDefinition = locateRoutes().get(routeId);
        if (routeDefinition == null) {
            throw new DiscoveryException("Gateway dynamic route for routeId=[" + routeId + "] isn't found");
        }

        return convertRoute(routeDefinition);
    }

    @Override
    public List<GatewayStrategyRouteEntity> viewAll() {
        List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList = new ArrayList<GatewayStrategyRouteEntity>();

        Map<String, RouteDefinition> routeDefinitionMap = locateRoutes();
        for (Map.Entry<String, RouteDefinition> entry : routeDefinitionMap.entrySet()) {
            RouteDefinition routeDefinition = entry.getValue();
            GatewayStrategyRouteEntity gatewayStrategyRouteEntity = convertRoute(routeDefinition);
            gatewayStrategyRouteEntityList.add(gatewayStrategyRouteEntity);
        }

        return gatewayStrategyRouteEntityList;
    }

    public Map<String, RouteDefinition> locateRoutes() {
        Map<String, RouteDefinition> routeDefinitionMap = new HashMap<String, RouteDefinition>();
        Flux<RouteDefinition> routeDefinitions = routeDefinitionLocator.getRouteDefinitions();
        routeDefinitions.subscribe(routeDefinition -> routeDefinitionMap.put(routeDefinition.getId(), routeDefinition));

        return routeDefinitionMap;
    }

    private boolean isIdDuplicated(List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList) {
        Set<GatewayStrategyRouteEntity> gatewayStrategyRouteEntitySet = new TreeSet<GatewayStrategyRouteEntity>(new Comparator<GatewayStrategyRouteEntity>() {
            public int compare(GatewayStrategyRouteEntity gatewayStrategyRouteEntity1, GatewayStrategyRouteEntity gatewayStrategyRouteEntity2) {
                return gatewayStrategyRouteEntity1.getId().compareTo(gatewayStrategyRouteEntity2.getId());
            }
        });
        gatewayStrategyRouteEntitySet.addAll(gatewayStrategyRouteEntityList);

        return gatewayStrategyRouteEntitySet.size() < gatewayStrategyRouteEntityList.size();
    }

    public RouteDefinition convertRoute(GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(gatewayStrategyRouteEntity.getId());
        routeDefinition.setUri(convertURI(gatewayStrategyRouteEntity.getUri()));

        List<String> predicateList = gatewayStrategyRouteEntity.getPredicates();
        List<PredicateDefinition> predicateDefinitionList = new ArrayList<PredicateDefinition>(predicateList.size());
        for (String predicate : predicateList) {
            predicateDefinitionList.add(new PredicateDefinition(predicate));
        }
        routeDefinition.setPredicates(predicateDefinitionList);

        List<String> filterList = gatewayStrategyRouteEntity.getFilters();
        List<FilterDefinition> filterDefinitionList = new ArrayList<FilterDefinition>(gatewayStrategyRouteEntity.getFilters().size());
        for (String filter : filterList) {
            filterDefinitionList.add(new FilterDefinition(filter));
        }
        routeDefinition.setFilters(filterDefinitionList);

        routeDefinition.setOrder(gatewayStrategyRouteEntity.getOrder());
        routeDefinition.setMetadata(gatewayStrategyRouteEntity.getMetadata());

        return routeDefinition;
    }

    public GatewayStrategyRouteEntity convertRoute(RouteDefinition routeDefinition) {
        GatewayStrategyRouteEntity gatewayStrategyRouteEntity = new GatewayStrategyRouteEntity();
        gatewayStrategyRouteEntity.setId(routeDefinition.getId());
        gatewayStrategyRouteEntity.setUri(routeDefinition.getUri().toString());
        gatewayStrategyRouteEntity.setPredicates(convertPredicates(routeDefinition.getPredicates()));
        gatewayStrategyRouteEntity.setFilters(convertFilters(routeDefinition.getFilters()));
        gatewayStrategyRouteEntity.setOrder(routeDefinition.getOrder());
        gatewayStrategyRouteEntity.setMetadata(routeDefinition.getMetadata());

        return gatewayStrategyRouteEntity;
    }

    public List<String> convertPredicates(List<PredicateDefinition> predicateDefinitionList) {
        List<String> predicateList = new ArrayList<String>();
        for (PredicateDefinition predicateDefinition : predicateDefinitionList) {
            String name = predicateDefinition.getName();
            Map<String, String> args = predicateDefinition.getArgs();
            predicateList.add(String.format("%s=%s", name, StringUtils.join(args.values(), ",")));
        }

        return predicateList;
    }

    public List<String> convertFilters(List<FilterDefinition> filterDefinitionList) {
        List<String> filterList = new ArrayList<String>();
        for (FilterDefinition filterDefinition : filterDefinitionList) {
            String name = filterDefinition.getName();
            Map<String, String> args = filterDefinition.getArgs();
            filterList.add(String.format("%s=%s", name, StringUtils.join(args.values(), ",")));
        }

        return filterList;
    }

    public URI convertURI(String value) {
        URI uri;
        if (value.toLowerCase().startsWith("http") || value.toLowerCase().startsWith("https")) {
            uri = UriComponentsBuilder.fromHttpUrl(value).build().toUri();
        } else {
            uri = URI.create(value);
        }

        return uri;
    }

    public void addRoute(RouteDefinition routeDefinition) {
        Disposable disposable = null;
        try {
            disposable = routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        } finally {
            if (disposable != null) {
                disposable.dispose();
            }
        }
    }

    public void modifyRoute(RouteDefinition routeDefinition) {
        deleteRoute(routeDefinition);
        addRoute(routeDefinition);
    }

    public void deleteRoute(RouteDefinition routeDefinition) {
        Disposable disposable = null;
        try {
            disposable = routeDefinitionWriter
                    .delete(Mono.just(routeDefinition.getId()))
                    .onErrorResume(new Function<Throwable, Mono<? extends Void>>() {
                        @Override
                        public Mono<? extends Void> apply(Throwable throwable) {
                            if (throwable instanceof NotFoundException) {
                                gatewayProperties.getRoutes().removeIf(new Predicate<RouteDefinition>() {
                                    @Override
                                    public boolean test(RouteDefinition routeCandidate) {
                                        return routeCandidate.getId().equals(routeDefinition.getId());
                                    }
                                });

                                return Mono.empty();
                            }

                            return Mono.error(throwable);
                        }
                    }).subscribe();
        } finally {
            if (disposable != null) {
                disposable.dispose();
            }
        }
    }

    public void clearRoutes() {
        Map<String, RouteDefinition> routeDefinitionMap = locateRoutes();
        for (Map.Entry<String, RouteDefinition> entry : routeDefinitionMap.entrySet()) {
            RouteDefinition routeDefinition = entry.getValue();
            deleteRoute(routeDefinition);
        }
    }
}