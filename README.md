# Nepxion Discovery
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/Nepxion/Discovery/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.nepxion/discovery.svg?label=maven%20central)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.nepxion%22%20AND%20discovery)
[![Javadocs](http://www.javadoc.io/badge/com.nepxion/discovery-plugin.svg)](http://www.javadoc.io/doc/com.nepxion/discovery-plugin)
[![Build Status](https://travis-ci.org/Nepxion/Discovery.svg?branch=master)](https://travis-ci.org/Nepxion/Discovery)

Nepxion Discovery是一款对Spring Cloud Discovery的服务注册增强插件，目前暂时只支持Eureka。现在Spring Cloud服务可以方便引入该插件，不需要对业务代码做任何修改，只需要修改规则（XML）即可

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
       A服务调用B服务，而B服务有两个实例（B1、B2和B3），虽然三者相同的服务名，但功能上有差异，需求是在某个时刻，A服务只能调用B1，禁止调用B2和B3。在此场景下，我们在application.properties里为B1维护一个版本为1.0，为B2维护一个版本为1.1，以此类推
       我们可以通过推送A服务调用某个版本的B服务对应关系的配置，达到某种意义上的灰度控制，切换版本的时候，我们只需要再次推送即可

## 依赖
```xml
<dependency>
  <groupId>com.nepxion</groupId>
  <artifactId>discovery-plugin-starter</artifactId>
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
        </version>
    </discovery>
</rule>
```

### 多版本灰度规则策略
```xml
规则策略介绍
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
1. 消费端的application.properties未定义版本号（即eureka.instance.metadataMap.version不存在），则该消费端可以访问提供端任何版本
2. 提供端的application.properties未定义版本号（即eureka.instance.metadataMap.version不存在），当消费端在xml里不做任何版本配置，才可以访问该提供端
```

## 跟远程配置中心整合
使用者可以跟携程Apollo，百度DisConf等远程配置中心整合
```xml
1. 主动从本地或远程配置中心获取规则
2. 订阅远程配置中心的规则更新
```
继承ConfigAdapter.java
```java
public class DiscoveryConfigAdapter extends ConfigAdapter {
    // 通过application.properties里的spring.application.discovery.remote.config.enabled=true，来决定主动从本地，还是远程配置中心获取规则
    // 从本地获取规则
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

    // 订阅远程配置中心的规则更新
    @PostConstruct
    public void publish() {
       InputStream inputStream = ...;

       publish(inputStream);
    }
}
```

实现接收远程配置中心推送过来的规则更新
```java
public class DiscoveryConfigSubscriber {
    @Autowired
    private ConfigPublisher configPublisher;

    public void subscribe() {
        // 订阅远程推送内容，再通过内置的发布订阅机制异步推送给缓存
        configPublisher.publish(inputStream);
    }
}
```

## 不整合远程配置中心
使用者可以通过Rest方式主动向一个微服务推送规则信息，但该方式只能每次推送到一个微服务上
```xml
利用Post执行http://IP:PORT/admin/config，发送的内容即规则XML
```

## 查看当前生效的规则
使用者可以通过Rest方式主动请求某个微服务当前生效的规则
```xml
利用Get执行http://IP:PORT/admin/view
```

## 扩展过滤条件监听
使用者可以继承如下类
```xml
AbstractRegisterListener，实现服务注册的扩展和监听
AbstractDiscoveryListener，实现服务发现的扩展和监听
```

## 示例
### B服务实现
B服务的两个实例B1、B2和B3采用标准的Spring Cloud入口，参考discovery-springcloud-example-b1、discovery-springcloud-example-b2和discovery-springcloud-example-b3工程

配置applicaiton.properties
```xml
eureka.instance.metadataMap.version=[version]
```

### A服务实现
A服务需要引入discovery-plugin-starter，参考discovery-springcloud-example-a工程

配置application.properties
```xml
# Spring cloud config
spring.application.name=discovery-springcloud-example-a
server.port=4321
eureka.client.serviceUrl.defaultZone=http://10.0.75.1:9528/eureka/
eureka.instance.preferIpAddress=true
eureka.instance.metadataMap.version=1.0

# Plugin config
# 开启和关闭服务注册层面的控制。一旦关闭，服务注册的黑/白名单过滤功能将失效。缺失则默认为true
spring.application.register.control.enabled=true
# 开启和关闭服务发现层面的控制。一旦关闭，服务多版本调用的控制功能将失效，动态屏蔽指定IP地址的服务实例被发现的功能将失效。缺失则默认为true
spring.application.discovery.control.enabled=true
# 开启和关闭远程配置中心规则规则文件读取。一旦关闭，默认读取本地规则规则文件（例如：rule.xml）。缺失则默认为true
spring.application.discovery.remote.config.enabled=true

management.security.enabled=false
```

模拟实现跟远程配置中心整合
```xml
参考DiscoveryConfigAdapter.java
```

### 运行效果
黑/白名单的IP地址注册的过滤
```xml
1. 首先在rule1.xml把本地IP地址写入
2. 启动discovery-springcloud-example-a/DiscoveryApplication.java
3. 抛出禁止注册的异常，本机不会注册到服务注册发现中心
```

黑/白名单的IP地址发现的过滤，多版本灰度访问控制
```xml
1. 运行discovery-springcloud-example-b1、discovery-springcloud-example-b2和discovery-springcloud-example-b3下的DiscoveryApplication.java，
2. 运行discovery-springcloud-example-a/DiscoveryApplication.java
3. 通过Postman或者浏览器，执行GET  http://localhost:4321/instances，看到当前时刻，A服务可访问B服务的列表，如图1
4. 通过Postman或者浏览器，执行POST http://localhost:5432/admin/config，发送的内容即规则XML，更改相关规则，如图2
5. 通过Postman或者浏览器，执行GET  http://localhost:4321/admin/view，看到当前时刻，在A服务已经生效的规则，如图3
```
图1

![Alt text](https://github.com/Nepxion/Discovery/blob/master/discovery-plugin-doc/Postman1.jpg)

图2

![Alt text](https://github.com/Nepxion/Discovery/blob/master/discovery-plugin-doc/Postman2.jpg)

图3

![Alt text](https://github.com/Nepxion/Discovery/blob/master/discovery-plugin-doc/Postman3.jpg)

## 鸣谢
感谢Spring Cloud中国社区刘石明提供支持
