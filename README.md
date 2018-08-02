# Nepxion Discovery
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/Nepxion/Discovery/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.nepxion/discovery.svg?label=maven%20central)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.nepxion%22%20AND%20discovery)
[![Javadocs](http://www.javadoc.io/badge/com.nepxion/discovery.svg)](http://www.javadoc.io/doc/com.nepxion/discovery)
[![Build Status](https://travis-ci.org/Nepxion/Discovery.svg?branch=master)](https://travis-ci.org/Nepxion/Discovery)

Nepxion Discovery是一款对Spring Cloud的服务注册发现的增强中间件，其功能包括多版本灰度发布，黑/白名单的IP地址过滤，限制注册等，支持Eureka、Consul和Zookeeper，支持Spring Cloud Api Gateway（F版）、Zuul网关和微服务的灰度发布，支持用户自定义和编程灰度路由策略，支持Nacos和Redis为远程配置中心，支持Spring Cloud E版和F版。现有的Spring Cloud微服务可以方便引入该插件，代码零侵入

使用者只需要做如下简单的事情：
- 引入相关Plugin Starter依赖到pom.xml
- 必须为微服务定义一个版本号（version），必须为微服务自定义一个便于为微服务归类的Key，例如组名（group）或者应用名（application）。两者定义在application.properties或者yaml的metadata里，便于远程配置中心推送和灰度界面分析
- 使用者只需要关注相关规则推送。可以采用如下方式之一
  - 通过远程配置中心推送规则
  - 通过控制台界面推送规则
  - 通过客户端工具（例如Postman）推送

## 请联系我
- 请加微信群或者微信

![Alt text](https://github.com/Nepxion/Docs/blob/master/zxing-doc/微信群-1.jpg) ![Alt text](https://github.com/Nepxion/Docs/blob/master/zxing-doc/微信-1.jpg)

## 快速开始
- 图形化演示操作
  - 请访问[http://www.iqiyi.com/w_19rzwzovrl.html](http://www.iqiyi.com/w_19rzwzovrl.html)，视频清晰度改成720P，然后最大化播放
  - 请访问[https://pan.baidu.com/s/1eq_N56VbgSCaTXYQ5aKqiA](https://pan.baidu.com/s/1eq_N56VbgSCaTXYQ5aKqiA)，获取更清晰的视频，注意一定要下载下来看，不要在线看，否则也不清晰
![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Console1.jpg)
![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Console2.jpg)  
- 更多教程和示例查看最下面的“示例演示”

## 痛点
现有Spring Cloud的痛点
- 如果你是运维负责人，是否会经常发现，你掌管的测试环境中的服务注册中心，被一些不负责的开发人员把他本地开发环境注册上来，造成测试人员测试失败。你希望可以把本地开发环境注册给屏蔽掉，不让注册
- 如果你是运维负责人，生产环境的某个微服务集群下的某个实例，暂时出了问题，但又不希望它下线。你希望可以把该实例给屏蔽掉，暂时不让它被调用
- 如果你是业务负责人，鉴于业务服务的快速迭代性，微服务集群下的实例发布不同的版本。你希望根据版本管理策略进行路由，提供给下游微服务区别调用，达到多版本灰度访问控制
- 如果你是业务负责人，希望灰度发布功能可以基于业务场景特色定制
- 如果你是测试负责人，希望对微服务做A/B测试，那么通过动态改变版本达到该目的

## 简介
- 实现对基于Spring Cloud的微服务和Spring Cloud Api Gateway（F版）和Zuul网关的支持
  - 具有极大的灵活性 - 支持在任何环节做过滤控制和版本灰度发布
  - 具有极小的限制性 - 只要开启了服务注册发现，程序入口加了@EnableDiscoveryClient
  - 具有极强的可用性 - 当远程配置中心全部挂了，可以通过Rest方式进行灰度发布
- 实现服务注册层面的控制
  - 基于黑/白名单的IP地址过滤机制禁止对相应的微服务进行注册
  - 基于最大注册数的限制微服务注册。一旦微服务集群下注册的实例数目已经达到上限，将禁止后续的微服务进行注册
- 实现服务发现层面的控制
  - 基于黑/白名单的IP地址过滤机制禁止对相应的微服务被发现
  - 基于版本配对，通过对消费端和提供端可访问版本对应关系的配置，在服务发现和负载均衡层面，进行多版本访问控制
- 实现灰度发布
  - 通过规则改变，实现灰度发布
  - 通过版本切换，实现灰度发布
- 实现通过XML或者Json进行上述规则的定义
- 实现通过事件总线机制（EventBus）的功能，实现发布/订阅功能
  - 对接远程配置中心，集成Nacos和Redis，异步接受远程配置中心主动推送规则信息，动态改变微服务的规则
  - 结合Spring Boot Actuator，异步接受Rest主动推送规则信息，动态改变微服务的规则
  - 结合Spring Boot Actuator，动态改变微服务的版本
  - 在服务注册层面的控制中，一旦禁止注册的条件触发，主动推送异步事件，以便使用者订阅
- 实现通过Listener机制进行扩展
  - 使用者可以对服务注册发现核心事件进行监听
- 实现通过扩展，用户自定义和编程灰度路由策略
  - 使用者可以实现跟业务有关的路由策略，根据业务参数的不同，负载均衡到不同的服务器
- 实现支持Spring Boot Actuator和Swagger集成
- 实现支持未来扩展更多的服务注册中心
- 实现独立控制台微服务，支持对规则和版本集中管理、推送、更改和删除
- 实现基于独立控制台微服务的图形化的灰度发布功能（运行discovery-console-desktop下的ConsoleLauncher）

## 名词解释
- IP地址，即根据微服务上报的它所在机器的IP地址。本系统内部强制以IP地址上报，禁止HostName上报，杜绝Spring Cloud应用在Docker或者Kubernetes部署时候出现问题
- 本地版本，即初始化读取本地配置文件获取的版本，也可以是第一次读取远程配置中心获取的版本。本地版本和初始版本是同一个概念
- 动态版本，即灰度发布时的版本。动态版本和灰度版本是同一个概念
- 本地规则，即初始化读取本地配置文件获取的规则，也可以是第一次读取远程配置中心获取的规则。本地规则和初始规则是同一个概念
- 动态规则，即灰度发布时的规则。动态规则和灰度规则是同一个概念
- 事件总线，即基于Google Guava的EventBus构建的组件。在使用上，通过事件总线推送动态版本和动态规则的时候，前者只支持异步，后者支持异步和同步
- 远程配置中心，即可以存储规则配置XML格式的配置中心，可以包括不限于Nacos，Redis，Apollo，DisConf，Spring Cloud Config
- 配置（Config）和规则（Rule），在本系统中属于同一个概念，例如更新配置，即更新规则，例如远程配置中心存储的配置，即规则XML

## 场景
- 黑/白名单的IP地址注册的过滤
  - 开发环境的本地微服务（例如IP地址为172.16.0.8）不希望被注册到测试环境的服务注册发现中心，那么可以在配置中心维护一个黑/白名单的IP地址过滤（支持全局和局部的过滤）的规则
  - 我们可以通过提供一份黑/白名单达到该效果
- 最大注册数的限制的过滤
  - 当某个微服务注册数目已经达到上限（例如10个），那么后面起来的微服务，将再也不能注册上去
- 黑/白名单的IP地址发现的过滤
  - 开发环境的本地微服务（例如IP地址为172.16.0.8）已经注册到测试环境的服务注册发现中心，那么可以在配置中心维护一个黑/白名单的IP地址过滤（支持全局和局部的过滤）的规则，该本地微服务不会被其他测试环境的微服务所调用
  - 我们可以通过推送一份黑/白名单达到该效果
- 多版本灰度访问控制
  - A服务调用B服务，而B服务有两个实例（B1、B2），虽然三者相同的服务名，但功能上有差异，需求是在某个时刻，A服务只能调用B1，禁止调用B2。在此场景下，我们在application.properties里为B1维护一个版本为1.0，为B2维护一个版本为1.1
  - 我们可以通过推送A服务调用某个版本的B服务对应关系的配置，达到某种意义上的灰度控制，切换版本的时候，我们只需要再次推送即可
- 动态改变微服务版本
  - 在A/B测试中，通过动态改变版本，不重启微服务，达到访问版本的路径改变
- 用户自定义和编程灰度路由策略，可以通过非常简单编程达到如下效果
  - 我们可以在网关上根据不同的Token查询到不同的用户，把请求路由到指定的服务器
  - 我们可以在服务上根据不同的业务参数，例如手机号或者身份证号，把请求路由到指定的服务器

## 架构
简单描述一下，本系统的核心模块“基于版本控制的灰度发布”，从网关（Zuul）开始的灰度发布操作过程
- 灰度发布前
  - 假设当前生产环境，调用路径为网关(V1.0)->服务A(V1.0)->服务B(V1.0)
  - 运维将发布新的生产环境，部署新服务集群，服务A(V1.1)，服务B(V1.1)
  - 由于网关(1.0)并未指向服务A(V1.1)，服务B(V1.1)，所以它们是不能被调用的  
- 灰度发布中
  - 新增用作灰度发布的网关(V1.1)，指向服务A(V1.1)->服务B(V1.1)
  - 灰度网关(V1.1)发布到服务注册发现中心，但禁止被服务发现，网关外的调用进来无法负载均衡到网关(V1.1)上
  - 在灰度网关(V1.1)->服务A(V1.1)->服务B(V1.1)这条调用路径做灰度测试
  - 灰度测试成功后，把网关(V1.0)指向服务A(V1.1)->服务B(V1.1)
- 灰度发布后  
  - 下线服务A(V1.0)，服务B(V1.0)，灰度成功
  - 灰度网关(V1.1)可以不用下线，留作下次版本上线再次灰度发布

架构图

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Architecture.jpg)

## 兼容
版本兼容情况
- Spring Cloud F版，请采用4.x.x版本，具体代码参考master分支
- Spring Cloud E版，请采用3.x.x版本，具体代码参考Edgware分支
- 4.x.x版本由于Swagger和Spring Boot 2.x.x版本的Actuator用法有冲突，故暂时不支持Endpoint功能，其他功能和3.x.x版本一致

中间件兼容情况
- Consul
  - Consul服务器版本不限制，推荐用最新版本，从[https://releases.hashicorp.com/consul/](https://releases.hashicorp.com/consul/)获取
- Zookeeper
  - Spring Cloud F版，必须采用Zookeeper服务器的3.5.x服务器版本（或者更高），从[http://zookeeper.apache.org/releases.html#download](http://zookeeper.apache.org/releases.html#download)获取
  - Spring Cloud E版，Zookeeper服务器版本不限制
- Eureka
  - 跟Spring Cloud版本保持一致，自行搭建服务器
- Nacos
  - Nacos服务器版本，推荐用最新版本，从[https://pan.baidu.com/s/1FsPzIK8lQ8VSNucI57H67A](https://pan.baidu.com/s/1FsPzIK8lQ8VSNucI57H67A)获取
- Redis
  - Redis服务器版本，推荐用最新版本，从[https://redis.io/](https://redis.io/)获取

## 依赖
| Spring Cloud版本 | Nepxion Discovery版本 |
| --- | --- |
| Finchley | 4.2.8 |
| Edgware | 3.5.8 |

```xml
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery</artifactId>
    <version>${discovery.plugin.version}</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

> 下面标注[必须引入]是一定要引入的包，标注[选择引入]是可以选择一个引入，或者不引入

微服务端引入
```xml
[必须引入] 三个服务注册发现的中间件的增强插件，请任选一个引入
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-starter-eureka</artifactId>
</dependency>

<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-starter-consul</artifactId>
</dependency>

<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-starter-zookeeper</artifactId>
</dependency>

[选择引入] 两个远程配置中心的中间件的扩展插件，如需要，请任选一个引入
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-config-center-extension-nacos</artifactId>
</dependency>

<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-config-center-extension-nacos</artifactId>
</dependency>

[选择引入] 用户自定义和编程灰度路由，如需要，请引入
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-strategy-extension-service</artifactId>
</dependency>
```

网关Zuul端引入
```xml
[选择引入] 用户自定义和编程灰度路由，如需要，请引入
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-strategy-extension-zuul</artifactId>
</dependency>
```

网关Spring Cloud Api Gateway（F版）端引入
```xml
[选择引入] 用户自定义和编程灰度路由，如需要，请引入
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-strategy-extension-gatewway</artifactId>
</dependency>
```

独立控制台引入
```xml
[必须引入]
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-console-starter</artifactId>
</dependency>

[选择引入] 两个远程配置中心的中间件的扩展插件，如需要，请任选一个引入
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-console-extension-nacos</artifactId>
</dependency>

<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-console-extension-redis</artifactId>
</dependency>
```

>特别注意：中间件的引入一定要在所有层面保持一致，绝不允许出现类似如下情况，这也是常识
- 例如，网关用Eureka做服务注册发现，微服务用Consul做服务注册发现
- 例如，控制台用Nacos做远程配置中心，微服务用Redis做远程配置中心

## 工程

| 工程名 | 描述 |
| --- | --- | 
| discovery-common-nacos | 封装Nacos通用操作逻辑 |
| discovery-common-redis | 封装Redis通用操作逻辑 |
| discovery-plugin-framework | 核心框架 |
| discovery-plugin-framework-eureka | 核心框架服务注册发现的Eureka实现 |
| discovery-plugin-framework-consul | 核心框架服务注册发现的Consul实现 |
| discovery-plugin-framework-zookeeper | 核心框架服务注册发现的Zookeeper实现 |
| discovery-plugin-config-center | 配置中心实现 |
| discovery-plugin-config-center-extension-nacos | 配置中心的Nacos扩展 |
| discovery-plugin-config-center-extension-redis | 配置中心的Redis扩展 |
| discovery-plugin-admin-center | 管理中心实现 |
| discovery-plugin-starter-eureka | Eureka Starter |
| discovery-plugin-starter-consul | Consul Starter |
| discovery-plugin-starter-zookeeper | Zookeeper Starter |
| discovery-plugin-strategy | 用户自定义和编程灰度路由策略 |
| discovery-plugin-strategy-extension-service | 基于服务的用户自定义和编程灰度路由策略扩展 |
| discovery-plugin-strategy-extension-zuul | 基于Zuul的用户自定义和编程灰度路由策略扩展 |
| discovery-plugin-strategy-extension-gateway | 基于Spring Cloud Api Gateway（F版）的用户自定义和编程灰度路由策略扩展 |
| discovery-console | 独立控制台，提供给UI |
| discovery-console-extension-nacos | 独立控制台的Nacos扩展 |
| discovery-console-extension-redis | 独立控制台的Redis扩展 |
| discovery-console-starter | Console Starter |
| discovery-console-desktop | 图形化灰度发布等桌面程序 |
| discovery-springcloud-example-console | 独立控制台示例 |
| discovery-springcloud-example-eureka | Eureka服务器示例 |
| discovery-springcloud-example-service | 用于灰度发布的微服务示例 |
| discovery-springcloud-example-zuul | 用于灰度发布的Zuul示例 |
| discovery-springcloud-example-gateway | 用于灰度发布的Spring Cloud Api Gateway（F版）示例 |

## 规则和策略
### 规则示例
请不要被吓到，我只是把注释写的很详细而已，里面配置没几行，下面的内容也可以通过Json来描述，这里不做描述，见discovery-springcloud-example-service下的rule.json
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <!-- 如果不想开启相关功能，只需要把相关节点删除即可，例如不想要黑名单功能，把blacklist节点删除 -->
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

        <!-- 服务注册的数目限制注册过滤，只在服务启动的时候生效。当某个服务的实例注册达到指定数目时候，更多的实例将无法注册 -->
        <!-- service-name，表示服务名 -->
        <!-- filter-value，表示最大实例注册数 -->
        <!-- 表示下面所有服务，最大实例注册数为10000（全局配置） -->
        <count filter-value="10000">
            <!-- 表示下面服务，最大实例注册数为5000，全局配置值10000将不起作用，以局部配置值为准 -->
            <service service-name="discovery-springcloud-example-a" filter-value="5000"/>
        </count>
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

### 动态改变规则策略
微服务启动的时候，由于规则（例如：rule.xml）已经配置在本地，使用者希望改变一下规则，而不重启微服务，达到规则的改变 
- 规则分为本地规则和动态规则
- 本地规则是通过在本地规则（例如：rule.xml）文件定义的，也可以从远程配置中心获取，在微服务启动的时候读取
- 动态规则是通过POST方式动态设置，或者由远程配置中心推送设置
- 规则初始化的时候，如果接入了远程配置中心，先读取远程规则，如果不存在，再读取本地规则文件
- 多规则灰度获取规则的时候，先获取动态规则，如果不存在，再获取本地规则
- 规则可以持久化到远程配置中心，一旦微服务死掉后，再次启动，仍旧可以拿到灰度规则，所以动态改变规则策略属于永久灰度发布手段

### 动态改变版本策略
微服务启动的时候，由于版本已经写死在application.properties里，使用者希望改变一下版本，而不重启微服务，达到访问版本的路径改变 
- 版本分为本地版本和动态版本
- 本地版本是通过在application.properties里配置的，在微服务启动的时候读取
- 动态版本是通过POST方式动态设置
- 多版本灰度获取版本值的时候，先获取动态版本，如果不存在，再获取本地版本
- 版本不会持久化到远程配置中心，一旦微服务死掉后，再次启动，拿到的还是本地版本，所以动态改变版本策略属于临时灰度发布手段

### 黑/白名单的IP地址注册的过滤策略
微服务启动的时候，禁止指定的IP地址注册到服务注册发现中心。支持黑/白名单，白名单表示只允许指定IP地址前缀注册，黑名单表示不允许指定IP地址前缀注册
- 全局过滤，指注册到服务注册发现中心的所有微服务，只有IP地址包含在全局过滤字段的前缀中，都允许注册（对于白名单而言），或者不允许注册（对于黑名单而言）
- 局部过滤，指专门针对某个微服务而言，那么真正的过滤条件是全局过滤+局部过滤结合在一起

### 最大注册数的限制的过滤策略
微服务启动的时候，一旦微服务集群下注册的实例数目已经达到上限（可配置），将禁止后续的微服务进行注册
- 全局配置值，只下面配置所有的微服务集群，最多能注册多少个
- 局部配置值，指专门针对某个微服务而言，那么该值如存在，全局配置值失效

### 黑/白名单的IP地址发现的过滤策略
微服务启动的时候，禁止指定的IP地址被服务发现。它使用的方式和“黑/白名单的IP地址注册的过滤”一致

### 用户自定义和编程灰度路由策略
使用者可以实现跟业务有关的路由策略，根据业务参数的不同，负载均衡到不同的服务器
- 基于服务的编程灰度路由，继承DiscoveryEnabledAdapter，通过ServiceStrategyContext获取业务上下文参数，进行路由自定义，用法参考discovery-springcloud-example-service下MyDiscoveryEnabledAdapter
- 基于Zuul的编程灰度路由，继承DiscoveryEnabledAdapter，通过Zuul自带的RequestContext获取业务上下文参数，进行路由自定义，用法参考discovery-springcloud-example-zuul下MyDiscoveryEnabledAdapter
- 基于Spring Cloud Api Gateway的编程灰度路由，继承DiscoveryEnabledAdapter，通过GatewayStrategyContext获取业务上下文参数，进行路由自定义，用法参考discovery-springcloud-example-gateway下MyDiscoveryEnabledAdapter

## 用户自定义监听
使用者可以继承如下类
- AbstractRegisterListener，实现服务注册的监听，用法参考discovery-springcloud-example-service下MyRegisterListener
- AbstractDiscoveryListener，实现服务发现的监听，用法参考discovery-springcloud-example-service下MyDiscoveryListener。注意，在Consul下，同时会触发service和management两个实例的事件，需要区别判断，如下图
- AbstractLoadBalanceListener，实现负载均衡的监听，用法参考discovery-springcloud-example-service下MyLoadBalanceListener

集成了健康检查的Consul控制台
![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Consul.jpg)

### 版本属性字段定义策略
不同的服务注册发现组件对应的版本配置值
```xml
# Eureka config
eureka.instance.metadataMap.version=1.0
eureka.instance.metadataMap.group=xxx-service-group

# 奇葩的Consul配置（参考https://springcloud.cc/spring-cloud-consul.html - 元数据和Consul标签）
# Consul config（多个值用“,”分隔，例如version=1.0,value=abc）
spring.cloud.consul.discovery.tags=version=1.0,group=xxx-service-group

# Zookeeper config
spring.cloud.zookeeper.discovery.metadata.version=1.0
spring.cloud.zookeeper.discovery.metadata.group=xxx-service-group
```

### 功能开关策略
请注意，如下很多配置项，如果使用者不想做特色化的处理，为避免繁琐，可以零配置（除了最底下，但一般也不会被用到）
```xml
# Admin config
# 关闭访问Rest接口时候的权限验证
management.security.enabled=false

# Plugin config
# 开启和关闭服务注册层面的控制。一旦关闭，服务注册的黑/白名单过滤功能将失效，最大注册数的限制过滤功能将失效。缺失则默认为true
# spring.application.register.control.enabled=true
# 开启和关闭服务发现层面的控制。一旦关闭，服务多版本调用的控制功能将失效，动态屏蔽指定IP地址的服务实例被发现的功能将失效。缺失则默认为true
# spring.application.discovery.control.enabled=true
# 开启和关闭通过Rest方式对规则配置的控制和推送。一旦关闭，只能通过远程配置中心来控制和推送。缺失则默认为true
# spring.application.config.rest.control.enabled=true
# 规则文件的格式，支持xml和json。缺失则默认为xml
# spring.application.config.format=xml
# spring.application.config.format=json
# 本地规则文件的路径，支持两种方式：classpath:rule.xml（rule.json） - 规则文件放在resources目录下，便于打包进jar；file:rule.xml（rule.json） - 规则文件放在工程根目录下，放置在外部便于修改。缺失则默认为不装载本地规则
# spring.application.config.path=classpath:rule.xml
# spring.application.config.path=classpath:rule.json
# 为微服务归类的Key，一般通过group字段来归类，例如eureka.instance.metadataMap.group=xxx-group或者eureka.instance.metadataMap.application=xxx-application。缺失则默认为group
# spring.application.group.key=group
# spring.application.group.key=application
# 内置Rest调用路径的前缀，当配置了server.context-path或者server.servlet.context-path时候，需要同步配置下面的值，务必保持一致
# spring.application.context-path=/nepxion

# Plugin strategy config
# 开启和关闭用户自定义和编程灰度路由策略的控制，例如用户根据业务参数的不同，负载均衡到不同的服务器。缺失则默认为true
# spring.application.strategy.control.enabled=true
# 用户自定义和编程灰度路由策略的时候，需要指定对业务Controller类的扫描路径，以便传递上下文对象。该项配置只对服务有效，对网关无效
spring.application.strategy.scan.packages=com.nepxion.discovery.plugin.example.service.feign
```

## 配置中心
### 跟远程配置中心集成
- 默认集成
  - 本系统跟Nacos集成，如何安装使用，请参考[https://github.com/alibaba/nacos](https://github.com/alibaba/nacos)
  - 本系统跟Redis集成
- 扩展集成
  - 使用者也可以跟携程Apollo，百度DisConf等远程配置中心集成
  - 参考三个跟Nacos或者Redis有关的工程

## 管理中心
> PORT端口号为server.port或者management.port都可以（management.port开放只支持3.x.x版本）
### 配置接口
### 版本接口
### 路由接口
参考Swagger界面，如下图

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Swagger1.jpg)

## 独立控制台
为UI提供相关接口，包括
- 一系列批量功能
- 跟Nacos和Redis集成，实现配置拉去、推送和清除

> PORT端口号为server.port或者management.port都可以（（注意：管理端口不支持F版）
### 控制台接口
参考Swagger界面，如下图

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Swagger2.jpg)

## 示例演示
### 场景描述
本例将模拟一个较为复杂的场景，如下图

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Version.jpg)

- 系统部署情况：
  - 网关Zuul集群部署了1个
  - 微服务集群部署了3个，分别是A服务集群、B服务集群、C服务集群，分别对应的实例数为2、2、3
- 微服务集群的调用关系为网关Zuul->服务A->服务B->服务C
- 系统调用关系
  - 网关Zuul的1.0版本只能调用服务A的1.0版本，网关Zuul的1.1版本只能调用服务A的1.1版本
  - 服务A的1.0版本只能调用服务B的1.0版本，服务A的1.1版本只能调用服务B的1.1版本
  - 服务B的1.0版本只能调用服务C的1.0和1.1版本，服务B的1.1版本只能调用服务C的1.2版本

用规则来表述上述关系
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <discovery>
        <version>
            <!-- 表示网关z的1.0，允许访问提供端服务a的1.0版本 -->
            <service consumer-service-name="discovery-springcloud-example-zuul" provider-service-name="discovery-springcloud-example-a" consumer-version-value="1.0" provider-version-value="1.0"/>
            <!-- 表示网关z的1.1，允许访问提供端服务a的1.1版本 -->
            <service consumer-service-name="discovery-springcloud-example-zuul" provider-service-name="discovery-springcloud-example-a" consumer-version-value="1.1" provider-version-value="1.1"/>
            <!-- 表示消费端服务a的1.0，允许访问提供端服务b的1.0版本 -->
            <service consumer-service-name="discovery-springcloud-example-a" provider-service-name="discovery-springcloud-example-b" consumer-version-value="1.0" provider-version-value="1.0"/>
            <!-- 表示消费端服务a的1.1，允许访问提供端服务b的1.1版本 -->
            <service consumer-service-name="discovery-springcloud-example-a" provider-service-name="discovery-springcloud-example-b" consumer-version-value="1.1" provider-version-value="1.1"/>
            <!-- 表示消费端服务b的1.0，允许访问提供端服务c的1.0和1.1版本 -->
            <service consumer-service-name="discovery-springcloud-example-b" provider-service-name="discovery-springcloud-example-c" consumer-version-value="1.0" provider-version-value="1.0;1.1"/>
            <!-- 表示消费端服务b的1.1，允许访问提供端服务c的1.2版本 -->
            <service consumer-service-name="discovery-springcloud-example-b" provider-service-name="discovery-springcloud-example-c" consumer-version-value="1.1" provider-version-value="1.2"/>
        </version>
    </discovery>
</rule>
```

上述微服务分别见discovery-springcloud-example-service、discovery-springcloud-example-zuul和discovery-springcloud-example-gateway三个工程。相应的服务名、端口和版本见下表

| 微服务 | 服务端口 | 管理端口 | 版本 |
| --- | --- | --- | --- |
| A1 | 1100 | 5100 | 1.0 |
| A2 | 1101 | 5101 | 1.1 |
| B1 | 1200 | 5200 | 1.0 |
| B2 | 1201 | 5201 | 1.1 |
| C1 | 1300 | 5300 | 1.0 |
| C2 | 1301 | 5301 | 1.1 |
| C3 | 1302 | 5302 | 1.2 |
| Zuul | 1400 | 5400 | 1.0 |
| Gateway | 1500 | 5500 | 1.0 |

独立控制台见discovery-springcloud-example-console，对应的版本和端口号如下表

| 服务端口 | 管理端口 |
| --- | --- |
| 2222 | 3333 |

### 开始演示
- 启动服务注册发现中心，默认是Eureka。可供选择的有Eureka，Zuul，Zookeeper。Eureka，请启动discovery-springcloud-example-eureka下的应用，后两者自行安装服务器
- 根据上面选择的服务注册发现中心，对示例下的discovery-springcloud-example-service/pom.xml进行组件切换
```xml
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-starter-eureka</artifactId>
    <!-- <artifactId>discovery-plugin-starter-consul</artifactId> -->
    <!-- <artifactId>discovery-plugin-starter-zookeeper</artifactId> -->
    <version>${discovery.plugin.version}</version>
</dependency>
```
- 根据上面选择的服务注册发现中心，对控制台下的discovery-springcloud-example-console/pom.xml进行组件切换切换
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    <!-- <artifactId>spring-cloud-starter-consul-discovery</artifactId> -->
    <!-- <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId> -->
</dependency>
```

### 服务注册过滤的操作演示
黑/白名单的IP地址注册的过滤
- 在rule.xml把本地IP地址写入到相应地方
- 启动DiscoveryApplicationA1.java
- 抛出禁止注册的异常，即本地服务受限于黑名单的IP地址列表，不会注册到服务注册发现中心；白名单操作也是如此，不过逻辑刚好相反

最大注册数的限制的过滤
- 在rule.xml修改最大注册数为0
- 启动DiscoveryApplicationA1.java
- 抛出禁止注册的异常，即本地服务受限于最大注册数，不会注册到服务注册发现中心

黑/白名单的IP地址发现的过滤
- 在rule.xml把本地IP地址写入到相应地方
- 启动DiscoveryApplicationA1.java和DiscoveryApplicationB1.java、DiscoveryApplicationB2.java
- 你会发现A服务无法获取B服务的任何实例，即B服务受限于黑名单的IP地址列表，不会被A服务的发现；白名单操作也是如此，不过逻辑刚好相反

### 服务发现和负载均衡控制的操作演示
#### 基于图形化方式的多版本灰度访问控制
- 请访问[http://www.iqiyi.com/w_19s07thtsh.html](http://www.iqiyi.com/w_19s07thtsh.html)，视频清晰度改成720P，然后最大化播放
- 请访问[https://pan.baidu.com/s/1eq_N56VbgSCaTXYQ5aKqiA](https://pan.baidu.com/s/1eq_N56VbgSCaTXYQ5aKqiA)，获取更清晰的视频，注意一定要下载下来看，不要在线看，否则也不清晰

#### 基于Rest方式的多版本灰度访问控制
基于服务的操作过程和效果
- 启动discovery-springcloud-example-service下7个DiscoveryApplication，无先后顺序，等待全部启动完毕
- 下面URL的端口号，可以是服务端口号，也可以是管理端口号（注意：管理端口不支持F版）
- 通过版本切换，达到灰度访问控制，针对A服务
  - 1.1 通过Postman或者浏览器，执行POST [http://localhost:1100/routes](http://localhost:1100/routes)，填入discovery-springcloud-example-b;discovery-springcloud-example-c，查看路由路径，如图1，可以看到符合预期的调用路径
  - 1.2 通过Postman或者浏览器，执行POST [http://localhost:1100/version/update](http://localhost:1100/version/update)，填入1.1，动态把服务A的版本从1.0切换到1.1
  - 1.3 通过Postman或者浏览器，再执行第一步操作，如图2，可以看到符合预期的调用路径，通过版本切换，灰度访问控制成功
- 通过规则改变，达到灰度访问控制，针对B服务
  - 2.1 通过Postman或者浏览器，执行POST [http://localhost:1200/config/update-sync](http://localhost:1200/config/update-sync)，发送新的规则XML（内容见下面）
  - 2.2 通过Postman或者浏览器，执行POST [http://localhost:1201/config/update-sync](http://localhost:1201/config/update-sync)，发送新的规则XML（内容见下面）
  - 2.3 上述操作也可以通过独立控制台，进行批量更新，见图5。操作的逻辑：B服务的所有版本都只能访问C服务3.0版本，而本例中C服务3.0版本是不存在的，意味着这么做B服务不能访问C服务
  - 2.4 重复1.1步骤，发现调用路径只有A服务->B服务，如图3，通过规则改变，灰度访问控制成功
- 负载均衡的灰度测试
  - 3.1 通过Postman或者浏览器，执行POST [http://localhost:1100/invoke](http://localhost:1100/invoke)，这是example内置的访问路径示例（通过Feign实现）
  - 3.2 重复“通过版本切换，达到灰度访问控制”或者“通过规则改变，达到灰度访问控制”操作，查看Ribbon负载均衡的灰度结果，如图4
- 上述操作，都是单次操作，如需要批量操作，可通过“独立控制台”接口，它集成批量操作和推送到远程配置中心的功能，可以取代上面的某些调用方式
- 其它更多操作，请参考“配置中心”、“管理中心”和“独立控制台”

新XML规则
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <discovery>
        <version>
            <service consumer-service-name="discovery-springcloud-example-b" provider-service-name="discovery-springcloud-example-c" consumer-version-value="" provider-version-value="3.0"/>
        </version>
    </discovery>
</rule>
```

图1

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Result1.jpg)

图2

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Result2.jpg)

图3

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Result3.jpg)

图4

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Result4.jpg)

图5

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Result5.jpg)

基于网关的操作过程和效果
- 在上面基础上，启动discovery-springcloud-example-zuul下DiscoveryApplicationZuul或者启动discovery-springcloud-example-gateway下DiscoveryApplicationGateway
- 因为Zuul和Spring Cloud Api Gateway是一种特殊的微服务，也遵循Spring Cloud体系的服务注册发现和负载均衡机制，所以所有操作过程跟上面完全一致

图6

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Result6.jpg)

图7

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Result7.jpg)

### 用户自定义和编程灰度路由的操作演示
- 在网关层（以Zuul为例），编程灰度路由策略，如下代码，表示请求的Header中的token包含'abc'，在负载均衡层面，对应的服务示例不会被负载均衡到
```java
public class MyDiscoveryEnabledAdapter implements DiscoveryEnabledAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(MyDiscoveryEnabledAdapter.class);

    @Override
    public boolean apply(Server server, Map<String, String> metadata) {
        RequestContext context = RequestContext.getCurrentContext();
        String token = context.getRequest().getHeader("token");
        // String value = context.getRequest().getParameter("value");

        String serviceId = server.getMetaInfo().getAppName().toLowerCase();

        LOG.info("Zuul端负载均衡用户定制触发：serviceId={}, host={}, metadata={}, context={}", serviceId, server.toString(), metadata, context);

        String filterToken = "abc";
        if (StringUtils.isNotEmpty(token) && token.contains(filterToken)) {
            LOG.info("过滤条件：当Token含有'{}'的时候，不能被Ribbon负载均衡到", filterToken);

            return false;
        }

        return true;
    }
}
```

图8

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Result8.jpg)

- 在服务层，编程灰度路由策略，如下代码，因为示例中只有一个方法 String invoke(String value)，表示当服务名为discovery-springcloud-example-c，同时版本为1.0，同时参数value中包含'abc'，三个条件同时满足的情况下，在负载均衡层面，对应的服务示例不会被负载均衡到
```java
public class MyDiscoveryEnabledAdapter implements DiscoveryEnabledAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(MyDiscoveryEnabledAdapter.class);

    @SuppressWarnings("unchecked")
    @Override
    public boolean apply(Server server, Map<String, String> metadata) {
        ServiceStrategyContext context = ServiceStrategyContext.getCurrentContext();
        Map<String, Object> attributes = context.getAttributes();

        String serviceId = server.getMetaInfo().getAppName().toLowerCase();
        String version = metadata.get(PluginConstant.VERSION);

        LOG.info("Serivice端负载均衡用户定制触发：serviceId={}, host={}, metadata={}, context={}", serviceId, server.toString(), metadata, context);

        String filterServiceId = "discovery-springcloud-example-c";
        String filterVersion = "1.0";
        String filterBusinessValue = "abc";
        if (StringUtils.equals(serviceId, filterServiceId) && StringUtils.equals(version, filterVersion)) {
            if (attributes.containsKey(ServiceStrategyConstant.PARAMETER_MAP)) {
                Map<String, Object> parameterMap = (Map<String, Object>) attributes.get(ServiceStrategyConstant.PARAMETER_MAP);
                String value = parameterMap.get("value").toString();
                if (StringUtils.isNotEmpty(value) && value.contains(filterBusinessValue)) {
                    LOG.info("过滤条件：当serviceId={} && version={} && 业务参数含有'{}'的时候，不能被Ribbon负载均衡到", filterServiceId, filterVersion, filterBusinessValue);

                    return false;
                }
            }
        }

        return true;
    }
}
```

图9

![Alt text](https://github.com/Nepxion/Docs/blob/master/discovery-plugin-doc/Result9.jpg)