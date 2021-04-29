package com.nepxion.discovery.plugin.strategy.gateway.adapter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Ning Zhang
 * @version 1.0
 */

import com.nepxion.discovery.common.entity.DynamicRouteEntity;
import com.nepxion.discovery.plugin.framework.adapter.DynamicRouteAdapter;
import com.nepxion.discovery.plugin.strategy.gateway.constant.GatewayStrategyConstant;
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

public class GatewayStrategyDynamicRouteAdapter implements DynamicRouteAdapter, ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;
    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;
    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
    @Autowired
    private GatewayProperties gatewayProperties;

    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    public void update(final List<DynamicRouteEntity> dynamicRouteEntityList) {
        final Map<String, RouteDefinition> routeDefinitionMap = dynamicRouteEntityList.stream().collect(Collectors.toMap(DynamicRouteEntity::getRouteId, this::toRouteDefinition));
        final Map<String, RouteDefinition> currentRouteMap = this.listRouteDefinition();
        final List<RouteDefinition> insertRouteDefinition = new ArrayList<>(routeDefinitionMap.size());
        final List<RouteDefinition> updateRouteDefinition = new ArrayList<>(routeDefinitionMap.size());
        final List<RouteDefinition> deleteRouteDefinition = new ArrayList<>(routeDefinitionMap.size());

        for (final Map.Entry<String, RouteDefinition> pair : routeDefinitionMap.entrySet()) {
            if (!currentRouteMap.containsKey(pair.getKey())) {
                insertRouteDefinition.add(pair.getValue());
            }
        }

        for (final Map.Entry<String, RouteDefinition> pair : routeDefinitionMap.entrySet()) {
            if (currentRouteMap.containsKey(pair.getKey())) {
                final RouteDefinition currentRouteDefinition = currentRouteMap.get(pair.getKey());
                final RouteDefinition newRouteDefinition = pair.getValue();
                if (!currentRouteDefinition.equals(newRouteDefinition)) {
                    updateRouteDefinition.add(newRouteDefinition);
                }
            }
        }

        for (final Map.Entry<String, RouteDefinition> pair : currentRouteMap.entrySet()) {
            if (!routeDefinitionMap.containsKey(pair.getKey())) {
                deleteRouteDefinition.add(pair.getValue());
            }
        }

        for (final RouteDefinition routeDefinition : insertRouteDefinition) {
            this.add(routeDefinition);
        }
        for (final RouteDefinition routeDefinition : updateRouteDefinition) {
            this.modify(routeDefinition);
        }
        for (final RouteDefinition routeDefinition : deleteRouteDefinition) {
            this.delete(routeDefinition);
        }

        if (!insertRouteDefinition.isEmpty() || !updateRouteDefinition.isEmpty() || !deleteRouteDefinition.isEmpty()) {
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
        }
    }

    @Override
    public List<String> view() {
        return listRouteDefinition().values().stream().map(RouteDefinition::toString).collect(Collectors.toList());
    }

    private Map<String, RouteDefinition> listRouteDefinition() {
        final Map<String, RouteDefinition> result = new HashMap<>();
        final Flux<RouteDefinition> routeDefinitions = this.routeDefinitionLocator.getRouteDefinitions();
        routeDefinitions.subscribe(routeDefinition -> result.put(routeDefinition.getId(), routeDefinition));
        return result;
    }

    private void add(final RouteDefinition routeDefinition) {
        Disposable disposable = null;
        try {
            disposable = this.routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        } finally {
            if (null != disposable) {
                disposable.dispose();
            }
        }
    }

    private void modify(final RouteDefinition routeDefinition) {
        this.delete(routeDefinition);
        this.add(routeDefinition);
    }

    private void delete(final RouteDefinition routeDefinition) {
        Disposable disposable = null;
        try {
            disposable = this.routeDefinitionWriter
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
        final RouteDefinition result = new RouteDefinition();
        result.setId(dynamicRouteEntity.getRouteId());
        result.setOrder(dynamicRouteEntity.getOrderNum());
        result.setMetadata(new HashMap<>());
        result.getMetadata().put(GatewayStrategyConstant.SERVICE_NAME, dynamicRouteEntity.getServiceName());
        result.getMetadata().put(GatewayStrategyConstant.ROUTE_PATH, getRoutePath(dynamicRouteEntity));

        final String strUri = dynamicRouteEntity.getUri();
        URI uri;
        if (strUri.startsWith("http")) {
            uri = UriComponentsBuilder.fromHttpUrl(strUri).build().toUri();
        } else {
            uri = URI.create(strUri);
        }
        result.setUri(uri);

        final List<PredicateDefinition> predicateDefinitionList = new ArrayList<>();
        for (final String text : dynamicRouteEntity.getPredicates().split(";")) {
            predicateDefinitionList.add(new PredicateDefinition(text));
        }
        result.setPredicates(predicateDefinitionList);

        final List<FilterDefinition> filterDefinitionList = new ArrayList<>();
        for (final String text : dynamicRouteEntity.getFilters().split(";")) {
            filterDefinitionList.add(new FilterDefinition(text));
        }
        result.setFilters(filterDefinitionList);
        return result;
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