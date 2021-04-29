package com.nepxion.discovery.plugin.strategy.gateway.adapter;

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

import com.nepxion.discovery.common.entity.DynamicRouteEntity;
import com.nepxion.discovery.plugin.strategy.adapter.StrategyDynamicRouteAdapter;
import com.nepxion.discovery.plugin.strategy.gateway.constant.GatewayStrategyConstant;

public class GatewayStrategyDynamicRouteAdapter implements StrategyDynamicRouteAdapter, ApplicationEventPublisherAware {
    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    @Autowired
    private GatewayProperties gatewayProperties;

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void update(final List<DynamicRouteEntity> dynamicRouteEntityList) {
        final Map<String, RouteDefinition> dynamicRouteDefinitionMap = dynamicRouteEntityList.stream().collect(Collectors.toMap(DynamicRouteEntity::getRouteId, this::toRouteDefinition));
        final Map<String, RouteDefinition> currentRouteDefinitionMap = locateRoutes();

        final List<RouteDefinition> insertRouteDefinition = new ArrayList<>(dynamicRouteDefinitionMap.size());
        final List<RouteDefinition> updateRouteDefinition = new ArrayList<>(dynamicRouteDefinitionMap.size());
        final List<RouteDefinition> deleteRouteDefinition = new ArrayList<>(dynamicRouteDefinitionMap.size());

        for (final Map.Entry<String, RouteDefinition> pair : dynamicRouteDefinitionMap.entrySet()) {
            if (!currentRouteDefinitionMap.containsKey(pair.getKey())) {
                insertRouteDefinition.add(pair.getValue());
            }
        }

        for (final Map.Entry<String, RouteDefinition> pair : dynamicRouteDefinitionMap.entrySet()) {
            if (currentRouteDefinitionMap.containsKey(pair.getKey())) {
                final RouteDefinition currentRouteDefinition = currentRouteDefinitionMap.get(pair.getKey());
                final RouteDefinition newRouteDefinition = pair.getValue();
                if (!currentRouteDefinition.equals(newRouteDefinition)) {
                    updateRouteDefinition.add(newRouteDefinition);
                }
            }
        }

        for (final Map.Entry<String, RouteDefinition> pair : currentRouteDefinitionMap.entrySet()) {
            if (!dynamicRouteDefinitionMap.containsKey(pair.getKey())) {
                deleteRouteDefinition.add(pair.getValue());
            }
        }

        for (final RouteDefinition routeDefinition : insertRouteDefinition) {
            add(routeDefinition);
        }

        for (final RouteDefinition routeDefinition : updateRouteDefinition) {
            modify(routeDefinition);
        }

        for (final RouteDefinition routeDefinition : deleteRouteDefinition) {
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
        final Map<String, RouteDefinition> result = new HashMap<>();
        final Flux<RouteDefinition> routeDefinitions = routeDefinitionLocator.getRouteDefinitions();
        routeDefinitions.subscribe(routeDefinition -> result.put(routeDefinition.getId(), routeDefinition));

        return result;
    }

    private void add(final RouteDefinition routeDefinition) {
        Disposable disposable = null;
        try {
            disposable = routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        } finally {
            if (null != disposable) {
                disposable.dispose();
            }
        }
    }

    private void modify(final RouteDefinition routeDefinition) {
        delete(routeDefinition);
        add(routeDefinition);
    }

    private void delete(final RouteDefinition routeDefinition) {
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
            if (null != disposable) {
                disposable.dispose();
            }
        }
    }

    private RouteDefinition toRouteDefinition(final DynamicRouteEntity dynamicRouteEntity) {
        final RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(dynamicRouteEntity.getRouteId());
        routeDefinition.setOrder(dynamicRouteEntity.getOrderNum());
        routeDefinition.setMetadata(new HashMap<>());
        routeDefinition.getMetadata().put(GatewayStrategyConstant.SERVICE_NAME, dynamicRouteEntity.getServiceName());
        routeDefinition.getMetadata().put(GatewayStrategyConstant.ROUTE_PATH, getRoutePath(dynamicRouteEntity));

        final String value = dynamicRouteEntity.getUri();
        URI uri;
        if (value.startsWith("http")) {
            uri = UriComponentsBuilder.fromHttpUrl(value).build().toUri();
        } else {
            uri = URI.create(value);
        }
        routeDefinition.setUri(uri);

        final List<PredicateDefinition> predicateDefinitionList = new ArrayList<>();
        for (final String text : dynamicRouteEntity.getPredicates().split(";")) {
            predicateDefinitionList.add(new PredicateDefinition(text));
        }
        routeDefinition.setPredicates(predicateDefinitionList);

        final List<FilterDefinition> filterDefinitionList = new ArrayList<>();
        for (final String text : dynamicRouteEntity.getFilters().split(";")) {
            filterDefinitionList.add(new FilterDefinition(text));
        }
        routeDefinition.setFilters(filterDefinitionList);

        return routeDefinition;
    }

    private static String getRoutePath(final DynamicRouteEntity dynamicRouteEntity) {
        if (dynamicRouteEntity.getFilters() == null || dynamicRouteEntity.getPredicates() == null) {
            return dynamicRouteEntity.getServiceName();
        }

        final String filters = dynamicRouteEntity.getFilters().toLowerCase();
        String predicates = dynamicRouteEntity.getPredicates().toLowerCase();
        Integer stripPrefix = null;

        if (!StringUtils.isEmpty(filters) && filters.contains("stripprefix=")) {
            stripPrefix = Integer.parseInt(StringUtils.removeStart(filters, "stripprefix="));
        }

        if (!StringUtils.isEmpty(predicates) && predicates.contains("path=")) {
            predicates = StringUtils.removeStart(predicates, "path=");
        }

        if (null != stripPrefix) {
            int start = predicates.indexOf("/");
            if (start < 0) {
                return predicates;
            }
            int end = start;
            int fromStart;
            for (int i = 0; i < stripPrefix; i++) {
                fromStart = end;
                int endTemp = predicates.indexOf("/", fromStart + 1);
                if (endTemp > -1) {
                    end = endTemp;
                }
            }

            return predicates.substring(start, end + 1);
        }

        return dynamicRouteEntity.getServiceName();
    }
}