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

import org.apache.commons.lang3.StringUtils;
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

import com.nepxion.discovery.plugin.strategy.gateway.constant.GatewayStrategyConstant;
import com.nepxion.discovery.plugin.strategy.gateway.entity.GatewayStrategyRouteEntity;

public class DefaultGatewayStrategyRoute implements GatewayStrategyRoute, ApplicationEventPublisherAware {
    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    @Autowired
    private GatewayProperties gatewayProperties;

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void update(List<GatewayStrategyRouteEntity> gatewayStrategyRouteEntityList) {
        Map<String, RouteDefinition> newRouteDefinitionMap = gatewayStrategyRouteEntityList.stream().collect(Collectors.toMap(GatewayStrategyRouteEntity::getRouteId, this::convertRoute));
        Map<String, RouteDefinition> currentRouteDefinitionMap = locateRoutes();

        List<RouteDefinition> insertRouteDefinition = new ArrayList<>(newRouteDefinitionMap.size());
        List<RouteDefinition> updateRouteDefinition = new ArrayList<>(newRouteDefinitionMap.size());
        List<RouteDefinition> deleteRouteDefinition = new ArrayList<>(newRouteDefinitionMap.size());

        for (Map.Entry<String, RouteDefinition> entry : newRouteDefinitionMap.entrySet()) {
            String routeId = entry.getKey();
            RouteDefinition routeDefinition = entry.getValue();
            if (!currentRouteDefinitionMap.containsKey(routeId)) {
                insertRouteDefinition.add(routeDefinition);
            }
        }

        for (Map.Entry<String, RouteDefinition> entry : newRouteDefinitionMap.entrySet()) {
            String routeId = entry.getKey();
            if (currentRouteDefinitionMap.containsKey(routeId)) {
                RouteDefinition currentRouteDefinition = currentRouteDefinitionMap.get(routeId);
                RouteDefinition newRouteDefinition = entry.getValue();
                if (!currentRouteDefinition.equals(newRouteDefinition)) {
                    updateRouteDefinition.add(newRouteDefinition);
                }
            }
        }

        for (Map.Entry<String, RouteDefinition> entry : currentRouteDefinitionMap.entrySet()) {
            String routeId = entry.getKey();
            RouteDefinition routeDefinition = entry.getValue();
            if (!newRouteDefinitionMap.containsKey(routeId)) {
                deleteRouteDefinition.add(routeDefinition);
            }
        }

        for (RouteDefinition routeDefinition : insertRouteDefinition) {
            add(routeDefinition);
        }

        for (RouteDefinition routeDefinition : updateRouteDefinition) {
            modify(routeDefinition);
        }

        for (RouteDefinition routeDefinition : deleteRouteDefinition) {
            delete(routeDefinition);
        }

        if (!insertRouteDefinition.isEmpty() || !updateRouteDefinition.isEmpty() || !deleteRouteDefinition.isEmpty()) {
            applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
        }
    }

    @Override
    public List<String> view() {
        return locateRoutes().values().stream().map(RouteDefinition::toString).collect(Collectors.toList());
    }

    private Map<String, RouteDefinition> locateRoutes() {
        Map<String, RouteDefinition> routeDefinitionMap = new HashMap<>();
        Flux<RouteDefinition> routeDefinitions = routeDefinitionLocator.getRouteDefinitions();
        routeDefinitions.subscribe(routeDefinition -> routeDefinitionMap.put(routeDefinition.getId(), routeDefinition));

        return routeDefinitionMap;
    }

    private RouteDefinition convertRoute(GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(gatewayStrategyRouteEntity.getRouteId());

        Map<String, Object> metadata = new HashMap<>();
        metadata.put(GatewayStrategyConstant.SERVICE_NAME, gatewayStrategyRouteEntity.getServiceName());
        metadata.put(GatewayStrategyConstant.ROUTE_PATH, getRoutePath(gatewayStrategyRouteEntity));
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

    private String getRoutePath(GatewayStrategyRouteEntity gatewayStrategyRouteEntity) {
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

    private void add(RouteDefinition routeDefinition) {
        Disposable disposable = null;
        try {
            disposable = routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        } finally {
            if (disposable != null) {
                disposable.dispose();
            }
        }
    }

    private void modify(RouteDefinition routeDefinition) {
        delete(routeDefinition);
        add(routeDefinition);
    }

    private void delete(RouteDefinition routeDefinition) {
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