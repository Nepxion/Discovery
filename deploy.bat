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

call mvn clean deploy -DskipTests -e -P release -pl discovery-plugin-starter-eureka,discovery-plugin-starter-consul,discovery-plugin-starter-zookeeper,discovery-plugin-config-center-starter-nacos,discovery-console-starter-nacos,discovery-plugin-config-center-starter-redis,discovery-console-starter-redis,discovery-plugin-strategy-starter-service,discovery-plugin-strategy-starter-zuul -am

pause