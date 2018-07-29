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

call mvn clean deploy -DskipTests -e -P release -pl discovery-plugin-starter-eureka,discovery-plugin-starter-consul,discovery-plugin-starter-zookeeper,discovery-console-starter,discovery-plugin-config-center-extension-nacos,discovery-console-extension-nacos,discovery-plugin-config-center-extension-redis,discovery-console-extension-redis,discovery-plugin-strategy-extension-service,discovery-plugin-strategy-extension-zuul,discovery-plugin-strategy-extension-gateway -am

pause