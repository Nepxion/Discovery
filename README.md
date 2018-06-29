# Nepxion Discovery
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/Nepxion/Discovery/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.nepxion/discovery.svg?label=maven%20central)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.nepxion%22%20AND%20discovery)
[![Javadocs](http://www.javadoc.io/badge/com.nepxion/discovery-plugin.svg)](http://www.javadoc.io/doc/com.nepxion/discovery-plugin)
[![Build Status](https://travis-ci.org/Nepxion/Discovery.svg?branch=master)](https://travis-ci.org/Nepxion/Discovery)

Nepxion Discovery是一款对Spring Cloud Discovery的服务注册增强插件，支持Eureka、Consul和Zookeeper。现在Spring Cloud服务可以方便引入该插件，不需要对业务代码做任何修改，只需要修改规则（XML）即可

## 简介
支持如下功能

    1. 实现服务注册层面的控制，基于黑/白名单的IP地址过滤机制禁止对相应的微服务进行注册
    2. 实现服务发现层面的控制，基于黑/白名单的IP地址过滤机制禁止对相应的微服务被发现；通过对消费端和提供端可访问版本对应关系的配置，进行多版本灰度访问控制
    3. 实现通过下面两种推送方式，动态改变“服务发现层面的控制”
    4. 实现通过XML进行规则定义
    5. 实现通过事件总线机制（EventBus）异步对接远程配置中心，接受远程配置中心主动推送规则信息
    6. 实现通过事件总线机制（EventBus）异步接受Rest主动推送规则信息
    7. 实现通过Listener机制便于使用者扩展更多过滤条件，也可以利用Listener实现服务注册发现核心事件的监听

## 场景

    1. 黑/白名单的IP地址注册的过滤
       开发环境的本地服务（例如IP地址为172.16.0.8）不希望被注册到测试环境的服务注册发现中心，那么可以在配置中心维护一个黑/白名单的IP地址过滤（支持全局和局部的过滤）的规则
       我们可以通过提供一份黑/白名单达到该效果
    2. 黑/白名单的IP地址发现的过滤
       开发环境的本地服务（例如IP地址为172.16.0.8）已经注册到测试环境的服务注册发现中心，那么可以在配置中心维护一个黑/白名单的IP地址过滤（支持全局和局部的过滤）的规则，该本地服务不会被其他测试环境的服务所调用
       我们可以通过推送一份黑/白名单达到该效果
    3. 多版本灰度访问控制
       A服务调用B服务，而B服务有两个实例（B1、B2），虽然三者相同的服务名，但功能上有差异，需求是在某个时刻，A服务只能调用B1，禁止调用B2。在此场景下，我们在application.properties里为B1维护一个版本为1.0，为B2维护一个版本为1.1
       我们可以通过推送A服务调用某个版本的B服务对应关系的配置，达到某种意义上的灰度控制，切换版本的时候，我们只需要再次推送即可

## 依赖
```xml
选择相应的插件引入
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-starter-eureka</artifactId>
    <version>${discovery.plugin.version}</version>
</dependency>

<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-starter-consul</artifactId>
    <version>${discovery.plugin.version}</version>
</dependency>

<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-starter-zookeeper</artifactId>
    <version>${discovery.plugin.version}</version>
</dependency>
```

## 规则配置
### 规则示例（请不要被吓到，我只是把注释写的很详细而已，里面配置没几行）
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <register>
        <!-- 服务注册的黑/白名单注册过滤，只在服务启动的时候生效。白名单表示只允许指定IP地址前缀注册，黑名单表示不允许指定IP地址前缀注册。每个服务只能同时开启要么白名单，要么黑名单 -->
        <!-- filter-type，可选值blacklist/whitelist，表示白名单或者黑名单 -->
        <!-- service-name，表示服务名 -->
        <!-- filter-value，表示黑/白名单的IP地址列表。IP地址一般用前缀来表示，如果多个用“;”分隔，不允许出现空格 -->
        <!-- 表示下面所有服务，不允许10.10和11.11为前缀的IP地址注册（全局过滤） -->
        <blacklist filter-value="10.10;11.11">
            <!-- 表示下面服务，不允许172.16和10.10和11.11为前缀的IP地址注册 -->
            <service service-name="discovery-springcloud-example-a" filter-value="172.16"/>
        </blacklist>

        <!-- <whitelist filter-value="">
            <service service-name="" filter-value=""/>
        </whitelist>  -->
    </register>

    <discovery>
        <!-- 服务发现的黑/白名单发现过滤，使用方式跟“服务注册的黑/白名单过滤”一致 -->
        <!-- 表示下面所有服务，不允许10.10和11.11为前缀的IP地址被发现（全局过滤） -->
        <blacklist filter-value="10.10;11.11">
            <!-- 表示下面服务，不允许172.16和10.10和11.11为前缀的IP地址被发现 -->
            <service service-name="discovery-springcloud-example-b" filter-value="172.16"/>
        </blacklist>

        <!-- 服务发现的多版本灰度访问控制 -->
        <!-- service-name，表示服务名 -->
        <!-- version-value，表示可供访问的版本，如果多个用“;”分隔，不允许出现空格 -->
        <version>
            <!-- 表示消费端服务a的1.0，允许访问提供端服务b的1.0和1.1版本 -->
            <service consumer-service-name="discovery-springcloud-example-a" provider-service-name="discovery-springcloud-example-b" consumer-version-value="1.0" provider-version-value="1.0;1.1"/>
            <!-- 表示消费端服务b的1.0，允许访问提供端服务c的1.0和1.1版本 -->
            <service consumer-service-name="discovery-springcloud-example-b" provider-service-name="discovery-springcloud-example-c" consumer-version-value="1.0" provider-version-value="1.0;1.1"/>
            <!-- 表示消费端服务b的1.1，允许访问提供端服务c的1.2版本 -->
            <service consumer-service-name="discovery-springcloud-example-b" provider-service-name="discovery-springcloud-example-c" consumer-version-value="1.1" provider-version-value="1.2"/>
        </version>
    </discovery>
</rule>
```

### 多版本灰度规则策略
```xml
版本策略介绍
1. 标准配置，举例如下
   <service consumer-service-name="a" provider-service-name="b" consumer-version-value="1.0" provider-version-value="1.0,1.1"/> 表示消费端1.0版本，允许访问提供端1.0和1.1版本
2. 版本值不配置，举例如下
   <service consumer-service-name="a" provider-service-name="b" provider-version-value="1.0,1.1"/> 表示消费端任何版本，允许访问提供端1.0和1.1版本
   <service consumer-service-name="a" provider-service-name="b" consumer-version-value="1.0"/> 表示消费端1.0版本，允许访问提供端任何版本
   <service consumer-service-name="a" provider-service-name="b"/> 表示消费端任何版本，允许访问提供端任何版本
3. 版本值空字符串，举例如下
   <service consumer-service-name="a" provider-service-name="b" consumer-version-value="" provider-version-value="1.0,1.1"/> 表示消费端任何版本，允许访问提供端1.0和1.1版本
   <service consumer-service-name="a" provider-service-name="b" consumer-version-value="1.0" provider-version-value=""/> 表示消费端1.0版本，允许访问提供端任何版本
   <service consumer-service-name="a" provider-service-name="b" consumer-version-value="" provider-version-value=""/> 表示消费端任何版本，允许访问提供端任何版本
4. 版本对应关系未定义，默认消费端任何版本，允许访问提供端任何版本
特殊情况处理，在使用上需要极力避免该情况发生
1. 消费端的application.properties未定义版本号，则该消费端可以访问提供端任何版本
2. 提供端的application.properties未定义版本号，当消费端在xml里不做任何版本配置，才可以访问该提供端
```

不同的服务注册发现组件对应的版本配置值
```xml
eureka.instance.metadataMap.version=1.0

# 奇葩的Consul配置（参考https://springcloud.cc/spring-cloud-consul.html - 元数据和Consul标签）
spring.cloud.consul.discovery.tags=version=1.0

spring.cloud.zookeeper.discovery.metadata.version=1.0
```

## 配置中心
### 跟远程配置中心整合
使用者可以跟携程Apollo，百度DisConf等远程配置中心整合，实现规则读取和订阅
```xml
1. 主动从本地或远程配置中心获取规则
2. 订阅远程配置中心的规则更新
```
继承ConfigAdapter.java
```java
public class DiscoveryConfigAdapter extends ConfigAdapter {
    // 从本地获取规则
    // 通过application.properties里的spring.application.discovery.remote.config.enabled=true，来决定主动从本地，还是远程配置中心获取规则
    @Override
    protected String getLocalContextPath() {
        // 规则文件放在resources目录下
        return "classpath:rule.xml";

        // 规则文件放在工程根目录下
        // return "file:rule.xml";
    }

    // 从远程配置中心获取规则
    @Override
    public InputStream getRemoteInputStream() {
        InputStream inputStream = ...;

        return inputStream;
    }

    // 订阅远程配置中心的规则更新（推送策略自己决定，可以所有服务都只对应一个规则信息，也可以根据服务名获取对应的规则信息）
    @PostConstruct
    public void publish() {
        InputStream inputStream = ...;
        publish(inputStream);
    }
}
```

## 管理中心
### 单独推送规则信息
使用者可以通过Rest方式主动向一个微服务推送规则信息，但该方式只能每次推送到一个微服务上（注意：端口号为management.port的值）
```java
Java:
@RequestMapping(path = "config", method = RequestMethod.POST)
public Object config(@RequestBody String config)

Url:
http://IP:[management.port]/admin/config
```

### 查看当前生效的规则信息
使用者可以通过Rest方式主动请求某个微服务当前生效的规则（注意：端口号为management.port的值）
```java
Java:
@RequestMapping(path = "view", method = RequestMethod.GET)
public String view()

Url:
http://IP:[management.port]/admin/view
```

## 路由中心
### 获取本地节点可访问其他节点（根据服务名）的实例列表
```java
Java:
@RequestMapping(path = "/instances/{serviceId}", method = RequestMethod.GET)
public List<ServiceInstance> instances(@PathVariable(value = "serviceId") String serviceId)

Url:
http://IP:[server.port]/instances/{serviceId}
```

### 获取本地节点的路由信息（只显示当前节点的简单信息，不包含下级路由）
```java
Java:
@RequestMapping(path = "/info", method = RequestMethod.GET)
public RouterEntity info()

Url:
http://IP:[server.port]/info
```

### 获取本地节点可访问其他节点（根据服务名）的路由信息列表
```java
Java:
@RequestMapping(path = "/route/{routeServiceId}", method = RequestMethod.GET)
public List<RouterEntity> route(@PathVariable(value = "routeServiceId") String routeServiceId)

Url:
http://IP:[server.port]/route/{routeServiceId}
```

### 获取指定节点（根据IP和端口）可访问其他节点（根据服务名）的路由信息列表
```java
Java:
@RequestMapping(path = "/route/{routeServiceId}/{routeHost}/{routePort}", method = RequestMethod.GET)
public List<RouterEntity> route(@PathVariable(value = "routeServiceId") String routeServiceId, @PathVariable(value = "routeHost") String routeHost, @PathVariable(value = "routePort") int routePort)

Url:
http://IP:[server.port]/route/{routeServiceId}/{routeHost}/{routePort}
```

### 获取全路径的路由信息（serviceIds按调用服务名的前后次序排列，起始节点的服务名不能加上去。如果多个用“;”分隔，不允许出现空格）
```java
Java:
@RequestMapping(path = "/routeAll", method = RequestMethod.POST)
public RouterEntity routeAll(@RequestBody String serviceIds)

Url:
http://IP:[server.port]/routeAll
```

## 扩展和自定义更多规则或者监听
使用者可以继承如下类
```xml
AbstractRegisterListener，实现服务注册的扩展和监听
AbstractDiscoveryListener，实现服务发现的扩展和监听
```

## Spring Cloud引入Consul的坑
```xml
spring-cloud-consul的2.0.0.RELEASE（目前最新的稳定版）支持consul-api-1.2.2版本，它不兼容Consul的1.0.0以上的服务器，原因是服务的deregister在consul-api-1.2.2中是执行GET方法，而Consul的1.0.0以上的服务器对应的是PUT方法
解决办法，二选一
1. 选用1.0.0以下的服务器，从https://releases.hashicorp.com/consul/0.9.3/获取
2. 或者，spring-cloud-consul中consul-api-1.2.2.jar替换到最新的版本
```

## 示例
### 场景描述
本例将模拟一个较为复杂的场景，如图1
```xml
1. 调用关系服务A->服务B->服务C
2. 服务A一个实例，服务B两个实例，服务C三个实例
3. 规则为服务A只能只能调用服务B的1.0和1.1版本，服务B的1.0版本只能调用服务C的1.0和1.1版本，服务B的1.1版本只能调用服务C的1.2版本
4. 当一切就绪后，动态切换规则，改变调用的版本对应关系
```

图1
![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Version.jpg)

上述服务分别见discovery-springcloud-example-xx字样的3个工程，对应的版本，端口号如下表

| 服务 | 服务端口 | 管理端口 | 版本 |
| --- | --- | --- | --- |
| A | 1100 | 8100 | 1.0 |
| B1 | 1200 | 8200 | 1.0 |
| B2 | 1201 | 8201 | 1.1 |
| C1 | 1300 | 无 | 1.0 |
| C2 | 1301 | 无 | 1.1 |
| C3 | 1302 | 无 | 1.2 |

```xml
把discovery-springcloud-example-eureka，并在3个discovery-springcloud-example-xx工程中application.properties的Eureka地址替换掉本地地址
```

### 运行效果
黑/白名单的IP地址注册的过滤
```xml
1. 首先example-a或example-b在rule.xml把本地IP地址写入
2. 启动Application
3. 抛出禁止注册的异常，即本机不会注册到服务注册发现中心
```

黑/白名单的IP地址发现的过滤，多版本灰度访问控制（单个微服务需要推送多次，如果是远程配置中心，则推送一次够了）
```xml
1. 启动3个工程共6个Application
2. 通过Postman或者浏览器，执行GET  http://localhost:1100/instances/discovery-springcloud-example-b，查看当前A服务可访问B服务的列表
3. 通过Postman或者浏览器，执行GET  http://localhost:1200/instances/discovery-springcloud-example-c，查看当前B1服务可访问C服务的列表
4. 通过Postman或者浏览器，执行GET  http://localhost:1201/instances/discovery-springcloud-example-c，查看当前B2服务可访问C服务的列表
5. 通过Postman或者浏览器，执行POST http://localhost:1100/routeAll/，填入discovery-springcloud-example-b;discovery-springcloud-example-c，可以看到路由全路径，如图2结果
6. 通过Postman或者浏览器，执行POST http://localhost:8200/admin/config，发送新的规则XML，那么在B1服务上将会运行新的规则，再运行上述步骤，查看服务列表
7. 通过Postman或者浏览器，执行POST http://localhost:8201/admin/config，发送同样的规则XML，那么在B1服务上将会运行新的规则，再运行上述步骤，查看服务列表
8. 通过Postman或者浏览器，执行GET  http://localhost:8200/admin/view，查看当前在B1服务已经生效的规则
9. 通过Postman或者浏览器，执行GET  http://localhost:8201/admin/view，查看当前在B2服务已经生效的规则
10.再执行步骤5，可以看到路由全路径将发生变化
```
图2结果
```xml
{
    "serviceId": "discovery-springcloud-example-b",
    "version": "1.0",
    "host": "192.168.0.107",
    "port": 1200,
    "nexts": [
        {
            "serviceId": "discovery-springcloud-example-b",
            "version": "1.0",
            "host": "localhost",
            "port": 1200,
            "nexts": [
                {
                    "serviceId": "discovery-springcloud-example-c",
                    "version": "1.0",
                    "host": "localhost",
                    "port": 1300,
                    "nexts": []
                },
                {
                    "serviceId": "discovery-springcloud-example-c",
                    "version": "1.1",
                    "host": "localhost",
                    "port": 1301,
                    "nexts": []
                }
            ]
        },
        {
            "serviceId": "discovery-springcloud-example-b",
            "version": "1.1",
            "host": "localhost",
            "port": 1201,
            "nexts": [
                {
                    "serviceId": "discovery-springcloud-example-c",
                    "version": "1.2",
                    "host": "192.168.0.107",
                    "port": 1302,
                    "nexts": []
                }
            ]
        }
    ]
}
```

图2

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Result.jpg)