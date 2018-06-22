# Nepxion Discovery
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/Nepxion/Discovery/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.nepxion/discovery.svg?label=maven%20central)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.nepxion%22%20AND%20discovery)
[![Javadocs](http://www.javadoc.io/badge/com.nepxion/discovery-plugin.svg)](http://www.javadoc.io/doc/com.nepxion/discovery-plugin)
[![Build Status](https://travis-ci.org/Nepxion/Discovery.svg?branch=master)](https://travis-ci.org/Nepxion/Discovery)

Nepxion Discovery是一款对Spring Cloud Discovery的服务注册增强插件，目前暂时只支持Eureka。现在Spring Cloud服务可以方便引入该插件，不需要对业务代码做任何修改，只需要修改配置即可

## 简介
支持如下功能

    1. 实现采用黑/白名单的IP地址过滤机制实现对客户端的禁止注册
    2. 实现通过多版本配置实现灰度访问控制
    3. 实现通过远程配置中心控制黑/白名单和灰度版本，实现动态通知	
    4. 实现通过事件总线机制异步对接远程配置中心，提供使用者实现和扩展
    5. 实现支持本地配置和远程配置的可选

## 依赖
```xml
<dependency>
  <groupId>com.nepxion</groupId>
  <artifactId>discovery-plugin-starter</artifactId>
  <version>${discovery.plugin.version}</version>
</dependency>
```

## 示例
### 场景示例

    1. 黑/白名单的IP地址过滤
       开发环境的本地服务（例如IP地址为172.16.0.8）不小心注册到测试环境的服务注册发现中心，会导致调用出现问题，那么可以在配置中心维护一个黑/白名单的IP地址过滤（支持全局和局部的过滤）
       我们可以在远程配置中心配置对该服务名所对应的IP地址列表，包含前缀172.16，当是黑名单的时候，表示包含在IP地址列表里的所有服务都禁止注册到服务注册发现中心；当是白名单的时候，表示包含在IP地址列表里的所有服务都允许注册到服务注册发现中心
    2. 多版本配置实现灰度访问控制
       A服务调用B服务，而B服务有两个实例（B1和B2），虽然B1和B2是相同的服务名，但功能上有差异，需求是在某个时刻，A服务只能调用B1，禁止调用B2。在此场景下，我们在application.properties里为B1维护一个版本为1.0，为B2维护一个版本为1.1
       我们可以在远程配置中心配置对于A服务调用某个版本的B服务，达到某种意义上的灰度控制，切换版本的时候，我们只需要改相关的远程配置中心的配置即可

### 配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<discovery>
    <!-- 服务注册的黑/白名单过滤。白名单表示只允许指定IP地址前缀注册，黑名单表示不允许指定IP地址前缀注册。每个服务只能同时开启要么白名单，要么黑名单 -->
    <!-- filter-type，可选值BLACKLIST/WHITELIST，表示白名单或者黑名单 -->	
    <!-- service-name，表示服务名 -->
    <!-- filter-value，表示黑/白名单的IP地址列表。IP地址一般用前缀来表示，如果多个用“;”分隔 -->
    <!-- 表示下面所有服务，不允许10.10和11.11为前缀的IP地址注册（全局过滤） -->
    <filter filter-type="BLACKLIST" filter-value="10.10;11.11">
        <!-- 表示下面服务，不允许172.16和10.10和11.11为前缀的IP地址注册 -->
        <service service-name="discovery-springcloud-example-a" filter-value="172.16"/>
    </filter>

    <!-- 服务注册下，服务多版本调用的控制 -->
    <!-- service-name，表示服务名 -->
    <!-- version-value，表示可供访问的版本，如果多个用“;”分隔 -->
    <version>
        <!-- 表示消费端服务a，允许访问提供端服务b的1.0版本-->
        <consumer service-name="discovery-springcloud-example-a">
            <provider service-name="discovery-springcloud-example-b" version-value="1.0"/>
        </consumer>
    </version>
</discovery>
```

### 代码示例
#### B服务实现
B服务的两个实例B1和B2采用标准的Spring Cloud入口，参考discovery-springcloud-example-b1和discovery-springcloud-example-b2工程
唯一需要做的是在applicaiton.properties维护版本号，如下
```xml
eureka.instance.metadataMap.version=1.0
```

#### A服务实现
A服务需要引入discovery-plugin-starter，参考discovery-springcloud-example-a工程

application.properties
```xml
# Spring cloud config
spring.application.name=discovery-springcloud-example-a
server.port=4321
eureka.client.serviceUrl.defaultZone=http://10.0.75.1:9528/eureka/
eureka.instance.metadataMap.version=1.0

# Multi-versions control
spring.application.discovery.version.enabled=true
# Get remote config
spring.application.discovery.remote.config.enabled=true
```
因为本中间件不跟任何远程配置中心系统绑定（需要使用者自行实现跟远程配置中心对接），故通过定时方式模拟获取远程配置内容的更新推送，参考DiscoveryPluginFileLoader.java和DiscoveryPluginConfigSimulator.java

#### 黑/白名单的IP地址过滤运行效果
启动DiscoveryPluginApplication.java的时候，如果IP地址被过滤，那么程序将抛出无法注册到服务注册发现中心的异常，并终止程序

#### 多版本配置实现灰度访问控制运行效果
先运行DiscoveryPluginApplicationB1.java和DiscoveryPluginApplicationB2.java，再运行DiscoveryPluginApplication.java，通过Postman访问
```xml
http://localhost:4321/instances
```
你可以看到通过A服务去获取B服务的被过滤的实例列表，虽然A服务定时器会更新不不同的配置，获取到的实例列表也随着变更
