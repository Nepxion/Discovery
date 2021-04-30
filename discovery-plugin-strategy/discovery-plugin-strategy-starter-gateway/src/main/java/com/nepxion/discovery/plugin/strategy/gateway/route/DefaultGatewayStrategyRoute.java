package com.nepxion.discovery.plugin.strategy.gateway.route;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Ning Zhang
 * @version 1.0
 */

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

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

import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.strategy.gateway.entity.GatewayStrategyRouteEntity;

public class DefaultGatewayStrategyRoute implements GatewayStrategyRoute, ApplicationEventPublisherAware {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultGatewayStrategyRoute.class);

    public static final String SERVICE_NAME = "service_name";
    public static final String ROUTE_PATH = "route_path";

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    @Autowired
    private GatewayProperties gatewayProperties;

    @Autowired(required = false)
    private GatewayStrategyRouteAdapter gatewayStrategyRouteAdapter;

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostConstruct
    public void retrieve() {
        if (gatewayStrategyRouteAdapter != null) {
            try {
                List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList = gatewayStrategyRouteAdapter.retrieve();

                update(gatewayStrategyRouteEntityList);
            } catch (Exception e) {
                LOG.warn("Spring Cloud Gateway dynamic routes can't be null");
            }
        }
    }

    @Override
    public void update(List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList) {
        if (gatewayStrategyRouteEntityList == null) {
            throw new DiscoveryException("Spring Cloud Gateway dynamic routes are null");
        }

        Map<String, RouteDefinition> newRouteDefinitionMap = gatewayStrategyRouteEntityList.stream().collect(Collectors.toMap(GatewayStrategyRouteEntity::getRouteId, this::convert));

        LOG.info("Updated Spring Cloud Gateway dynamic routes={}", newRouteDefinitionMap);

        Map<String, RouteDefinition> currentRouteDefinitionMap = locate();

        boolean isChanged = false;

        for (Map.Entry<String, RouteDefinition> entry : newRouteDefinitionMap.entrySet()) {
            String routeId = entry.getKey();
            RouteDefinition routeDefinition = entry.getValue();
            if (!currentRouteDefinitionMap.containsKey(routeId)) {
                addRoute(routeDefinition);

                isChanged = true;
            }

            if (currentRouteDefinitionMap.containsKey(routeId)) {
                RouteDefinition currentRouteDefinition = currentRouteDefinitionMap.get(routeId);
                if (!currentRouteDefinition.equals(routeDefinition)) {
                    modifyRoute(routeDefinition);

                    isChanged = true;
                }
            }
        }

        for (Map.Entry<String, RouteDefinition> entry : currentRouteDefinitionMap.entrySet()) {
            String routeId = entry.getKey();
            RouteDefinition routeDefinition = entry.getValue();
            if (!newRouteDefinitionMap.containsKey(routeId)) {
                deleteRoute(routeDefinition);

                isChanged = true;
            }
        }

        if (isChanged) {
            applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
        }
    }

    @Override
    public List<String> view() {
        return locate().values().stream().map(RouteDefinition::toString).collect(Collectors.toList());
    }

    private Map<String, RouteDefinition> locate() {
        Map<String, RouteDefinition> routeDefinitionMap = new HashMap<>();
        Flux<RouteDefinition> routeDefinitions = routeDefinitionLocator.getRouteDefinitions();
        routeDefinitions.subscribe(routeDefinition -> routeDefinitionMap.put(routeDefinition.getId(), routeDefinition));

        return routeDefinitionMap;
    }

    private RouteDefinition convert(GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(gatewayStrategyRouteEntity.getRouteId());

        Map<String, Object> metadata = new HashMap<>();
        metadata.put(SERVICE_NAME, gatewayStrategyRouteEntity.getServiceName());
        metadata.put(ROUTE_PATH, getPath(gatewayStrategyRouteEntity));
        routeDefinition.setMetadata(metadata);

        String value = gatewayStrategyRouteEntity.getUri();
        URI uri;
        if (value.startsWith("http")) {
            uri = UriComponentsBuilder.fromHttpUrl(value).build().toUri();
        } else {
            uri = URI.create(value);
        }
        routeDefinition.setUri(uri);

        List<PredicateDefinition> predicateDefinitionList = new ArrayList<>();
        String[] predicateArray = gatewayStrategyRouteEntity.getPredicates().split(";");
        for (String predicate : predicateArray) {
            predicateDefinitionList.add(new PredicateDefinition(predicate));
        }
        routeDefinition.setPredicates(predicateDefinitionList);

        List<FilterDefinition> filterDefinitionList = new ArrayList<>();
        String[] filterArray = gatewayStrategyRouteEntity.getFilters().split(";");
        for (String filter : filterArray) {
            filterDefinitionList.add(new FilterDefinition(filter));
        }
        routeDefinition.setFilters(filterDefinitionList);

        routeDefinition.setOrder(gatewayStrategyRouteEntity.getOrder());

        return routeDefinition;
    }

    private String getPath(GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        String filters = gatewayStrategyRouteEntity.getFilters();
        String predicates = gatewayStrategyRouteEntity.getPredicates();

        if (filters == null || predicates == null) {
            return gatewayStrategyRouteEntity.getServiceName();
        }

        filters = filters.toLowerCase();

        Integer stripPrefix = null;
        if (StringUtils.isNotEmpty(filters) && filters.contains("stripprefix=")) {
            stripPrefix = Integer.parseInt(StringUtils.removeStart(filters, "stripprefix="));
        }

        if (stripPrefix == null) {
            return gatewayStrategyRouteEntity.getServiceName();
        }

        predicates = predicates.toLowerCase();
        if (StringUtils.isNotEmpty(predicates) && predicates.contains("path=")) {
            predicates = StringUtils.removeStart(predicates, "path=");
        }

        int start = predicates.indexOf("/");
        if (start < 0) {
            return predicates;
        }

        int end = start;
        int from;
        for (int i = 0; i < stripPrefix; i++) {
            from = end;
            int temp = predicates.indexOf("/", from + 1);
            if (temp > -1) {
                end = temp;
            }
        }

        return predicates.substring(start, end + 1);
    }

    private void addRoute(RouteDefinition routeDefinition) {
        Disposable disposable = null;
        try {
            disposable = routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        } finally {
            if (disposable != null) {
                disposable.dispose();
            }
        }
    }

    private void modifyRoute(RouteDefinition routeDefinition) {
        deleteRoute(routeDefinition);
        addRoute(routeDefinition);
    }

    private void deleteRoute(RouteDefinition routeDefinition) {
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
}