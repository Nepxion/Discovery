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

call mvn clean deploy -DskipTests -e -P release -pl discovery-plugin-register-center/discovery-plugin-register-center-starter-eureka,discovery-plugin-register-center/discovery-plugin-register-center-starter-consul,discovery-plugin-register-center/discovery-plugin-register-center-starter-zookeeper,discovery-plugin-register-center/discovery-plugin-register-center-starter-nacos,discovery-plugin-config-center/discovery-plugin-config-center-starter-apollo,discovery-plugin-config-center/discovery-plugin-config-center-starter-nacos,discovery-plugin-config-center/discovery-plugin-config-center-starter-redis,discovery-console/discovery-console-starter-apollo,discovery-console/discovery-console-starter-nacos,discovery-console/discovery-console-starter-redis,discovery-plugin-admin-center/discovery-plugin-admin-center-starter,discovery-plugin-strategy/discovery-plugin-strategy-starter-service,discovery-plugin-strategy/discovery-plugin-strategy-starter-zuul,discovery-plugin-strategy/discovery-plugin-strategy-starter-gateway,discovery-plugin-strategy/discovery-plugin-strategy-starter-hystrix,discovery-plugin-strategy/discovery-plugin-strategy-starter-opentelemetry,discovery-plugin-strategy/discovery-plugin-strategy-starter-opentracing,discovery-plugin-strategy/discovery-plugin-strategy-starter-skywalking,discovery-plugin-strategy/discovery-plugin-strategy-starter-sentinel-local,discovery-plugin-strategy/discovery-plugin-strategy-starter-sentinel-apollo,discovery-plugin-strategy/discovery-plugin-strategy-starter-sentinel-nacos,discovery-plugin-strategy/discovery-plugin-strategy-starter-sentinel-opentelemetry,discovery-plugin-strategy/discovery-plugin-strategy-starter-sentinel-opentracing,discovery-plugin-strategy/discovery-plugin-strategy-starter-sentinel-skywalking,discovery-plugin-strategy/discovery-plugin-strategy-starter-service-sentinel,discovery-plugin-test/discovery-plugin-test-starter-automation -am

pause