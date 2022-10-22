@echo on
@echo =============================================================
@echo $                                                           $
@echo $                     Nepxion Discovery                     $
@echo $                                                           $
@echo $                                                           $
@echo $                                                           $
@echo $  Nepxion Studio All Right Reserved                        $
@echo $  Copyright (C) 2017-2050                                  $
@echo $                                                           $
@echo =============================================================
@echo.
@echo off

@title Nepxion Discovery
@color 0a

call mvn clean deploy -DskipTests -e -P release -pl discovery-plugin-register-center/discovery-plugin-register-center-starter-eureka,discovery-plugin-register-center/discovery-plugin-register-center-starter-consul,discovery-plugin-register-center/discovery-plugin-register-center-starter-zookeeper,discovery-plugin-register-center/discovery-plugin-register-center-starter-nacos,discovery-plugin-config-center/discovery-plugin-config-center-starter-apollo,discovery-plugin-config-center/discovery-plugin-config-center-starter-nacos,discovery-plugin-config-center/discovery-plugin-config-center-starter-redis,discovery-plugin-config-center/discovery-plugin-config-center-starter-zookeeper,discovery-plugin-config-center/discovery-plugin-config-center-starter-consul,discovery-plugin-config-center/discovery-plugin-config-center-starter-etcd,discovery-console/discovery-console-starter-apollo,discovery-console/discovery-console-starter-nacos,discovery-console/discovery-console-starter-redis,discovery-console/discovery-console-starter-zookeeper,discovery-console/discovery-console-starter-consul,discovery-console/discovery-console-starter-etcd,discovery-plugin-admin-center/discovery-plugin-admin-center-starter,discovery-plugin-admin-center/discovery-plugin-admin-center-starter-swagger,discovery-plugin-strategy/discovery-plugin-strategy-starter-service,discovery-plugin-strategy/discovery-plugin-strategy-starter-zuul,discovery-plugin-strategy/discovery-plugin-strategy-starter-gateway,discovery-plugin-strategy/discovery-plugin-strategy-starter-hystrix,discovery-plugin-strategy/discovery-plugin-strategy-starter-opentelemetry,discovery-plugin-strategy/discovery-plugin-strategy-starter-opentracing,discovery-plugin-strategy/discovery-plugin-strategy-starter-skywalking,discovery-plugin-strategy/discovery-plugin-strategy-starter-sentinel-datasource,discovery-plugin-strategy/discovery-plugin-strategy-starter-sentinel-limiter,discovery-plugin-strategy/discovery-plugin-strategy-starter-sentinel-opentelemetry,discovery-plugin-strategy/discovery-plugin-strategy-starter-sentinel-opentracing,discovery-plugin-strategy/discovery-plugin-strategy-starter-sentinel-skywalking,discovery-plugin-strategy/discovery-plugin-strategy-starter-sentinel-micrometer,discovery-plugin-test/discovery-plugin-test-starter-automation -am

pause