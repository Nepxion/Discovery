[^_^]: ![](http://nepxion.gitee.io/docs/discovery-doc/Cover.jpg)

![](http://nepxion.gitee.io/docs/discovery-doc/Banner.png)

# Discovery【探索】微服务企业级解决方案
[![Total lines](https://tokei.rs/b1/github/Nepxion/Discovery?category=lines)](https://tokei.rs/b1/github/Nepxion/Discovery?category=lines)  [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/Nepxion/Discovery/blob/master/LICENSE)  [![Maven Central](https://img.shields.io/maven-central/v/com.nepxion/discovery.svg?label=maven%20central)](https://search.maven.org/artifact/com.nepxion/discovery)  [![Javadocs](http://www.javadoc.io/badge/com.nepxion/discovery-plugin-framework-starter.svg)](http://www.javadoc.io/doc/com.nepxion/discovery-plugin-framework-starter)  [![Build Status](https://travis-ci.org/Nepxion/Discovery.svg?branch=master)](https://travis-ci.org/Nepxion/Discovery)  [![Codacy Badge](https://api.codacy.com/project/badge/Grade/8e39a24e1be740c58b83fb81763ba317)](https://www.codacy.com/project/HaojunRen/Discovery/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Nepxion/Discovery&amp;utm_campaign=Badge_Grade_Dashboard)  [![Stars](https://img.shields.io/github/stars/Nepxion/Discovery.svg?label=Stars&tyle=flat&logo=GitHub)](https://github.com/Nepxion/Discovery/stargazers)  [![Stars](https://gitee.com/Nepxion/Discovery/badge/star.svg)](https://gitee.com/nepxion/Discovery/stargazers)

[![Spring Boot](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-dependencies.svg?label=Spring%20Boot&logo=Spring)](https://search.maven.org/artifact/org.springframework.boot/spring-boot-dependencies)  [![Spring Cloud](https://img.shields.io/maven-central/v/org.springframework.cloud/spring-cloud-dependencies.svg?label=Spring%20Cloud&logo=Spring)](https://search.maven.org/artifact/org.springframework.cloud/spring-cloud-dependencies)  [![Spring Cloud Alibaba](https://img.shields.io/maven-central/v/com.alibaba.cloud/spring-cloud-alibaba-dependencies.svg?label=Spring%20Cloud%20Alibaba&logo=Spring)](https://search.maven.org/artifact/com.alibaba.cloud/spring-cloud-alibaba-dependencies)  [![Nepxion Discovery](https://img.shields.io/maven-central/v/com.nepxion/discovery.svg?label=Nepxion%20Discovery&logo=Anaconda)](https://search.maven.org/artifact/com.nepxion/discovery)

[![Discovery DOC PPT](https://img.shields.io/badge/Discovery%20Doc-PPT-brightgreen?logo=Microsoft%20PowerPoint)](http://nepxion.gitee.io/docs/link-doc/discovery-ppt.html)  [![Discovery DOC PDF](https://img.shields.io/badge/Discovery%20Doc-PDF-brightgreen?logo=Adobe%20Acrobat%20Reader)](http://nepxion.gitee.io/docs/link-doc/discovery-pdf.html)  [![Discovery DOC HTML](https://img.shields.io/badge/Discovery%20Doc-HTML-brightgreen?logo=Microsoft%20Edge)](http://nepxion.gitee.io/docs/link-doc/discovery-html.html)

![](http://nepxion.gitee.io/docs/icon-doc/star3.png) 如果您觉得本框架具有一定的参考价值和借鉴意义，请帮忙在页面右上角 [**Star**]

![](http://nepxion.gitee.io/docs/icon-doc/star1.png) 首席作者简介
- Nepxion开源社区创始人
- 2020年阿里巴巴中国云原生峰会出品人
- Nacos Group Member、Spring Cloud Alibaba Member
- Spring Cloud Alibaba、Nacos、Sentinel、OpenTracing Committer & Contributor

![](http://nepxion.gitee.io/docs/discovery-doc/Logo64.png) Discovery【探索】微服务企业级解决方案

① Discovery【探索】微服务企业级解决方案文档
- [Discovery【探索】微服务企业级解决方案(WIKI版)](http://nepxion.com/discovery)
- [Discovery【探索】微服务企业级解决方案(PPT版)](http://nepxion.gitee.io/docs/link-doc/discovery-ppt.html)
- [Discovery【探索】微服务企业级解决方案(PDF版)](http://nepxion.gitee.io/docs/link-doc/discovery-pdf.html)
- [Discovery【探索】微服务企业级解决方案(HTML版)](http://nepxion.gitee.io/docs/link-doc/discovery-html.html)

② Discovery【探索】微服务企业级解决方案源码。请访问Gitee镜像获得最佳体验
- [源码Gitee同步镜像](https://gitee.com/Nepxion/Discovery)
- [源码Github原镜像](https://github.com/Nepxion/Discovery)

③ Discovery【探索】微服务企业级解决方案指南示例源码。请访问Gitee镜像获得最佳体验
- [指南Gitee同步镜像](https://gitee.com/Nepxion/DiscoveryGuide)
- [指南Github原镜像](https://github.com/Nepxion/DiscoveryGuide)

④ Discovery【探索】微服务框架指南示例说明
- 对于入门级玩家，参考[6.x.x指南示例极简版](https://github.com/Nepxion/DiscoveryGuide/tree/6.x.x-simple)，分支为6.x.x-simple。涉及到指南篇里的蓝绿灰度发布的基本功能，![](http://nepxion.gitee.io/docs/icon-doc/information_message.png) 参考[新手快速入门](https://gitee.com/nepxion/DiscoveryGuide/blob/6.x.x-simple/GUIDE.md)
- 对于熟练级玩家，参考[6.x.x指南示例精进版](https://github.com/Nepxion/DiscoveryGuide/tree/6.x.x)，分支为6.x.x。除上述《极简版》功能外，涉及到指南篇里的绝大多数高级功能
- 对于骨灰级玩家，参考[6.x.x指南示例高级版](https://github.com/Nepxion/DiscoveryGuide/tree/6.x.x-complex)，分支为6.x.x-complex。除上述《精进版》功能外，涉及到指南篇里的ActiveMQ、MongoDB、RabbitMQ、Redis、RocketMQ、MySQL等高级调用链和蓝绿灰度调用链的整合
- 上述指南实例分支是针对Spring Cloud旧版本。对于Spring Cloud 202x版本，参考[202x版指南示例精进版](https://github.com/Nepxion/DiscoveryGuide/tree/master)，分支为master

![](http://nepxion.gitee.io/docs/polaris-doc/Logo64.png) Polaris【北极星】企业级云原生微服务框架

① Polaris【北极星】企业级云原生微服务框架文档
- [Polaris【北极星】企业级云原生微服务框架(WIKI版)](http://nepxion.com/polaris)
- [Polaris【北极星】企业级云原生微服务框架(PDF版)](http://nepxion.gitee.io/docs/link-doc/polaris-pdf.html)
- [Polaris【北极星】企业级云原生微服务框架(HTML版)](http://nepxion.gitee.io/docs/link-doc/polaris-html.html)

② Polaris【北极星】企业级云原生微服务框架源码。请访问Gitee镜像获得最佳体验
- [源码Gitee同步镜像](https://gitee.com/polaris-paas/polaris-sdk)
- [源码Github原镜像](https://github.com/polaris-paas/polaris-sdk)

③ Polaris【北极星】企业级云原生微服务框架指南示例源码。请访问Gitee镜像获得最佳体验
- [指南Gitee同步镜像](https://gitee.com/polaris-paas/polaris-guide)
- [指南Github原镜像](https://github.com/polaris-paas/polaris-guide)

![](http://nepxion.gitee.io/docs/icon-doc/Logo64.png) Discovery【探索】和Polaris【北极星】架构体系

① Discovery【探索】和Polaris【北极星】联合架构图

![](http://nepxion.gitee.io/docs/polaris-doc/Architecture.jpg)

② Discovery【探索】和Polaris【北极星】联合拓扑图

![](http://nepxion.gitee.io/docs/polaris-doc/Topology.jpg)

③ Polaris【北极星】分层架构图

![](http://nepxion.gitee.io/docs/polaris-doc/Layer.jpg)

④ Discovery【探索】实施方案图

![](http://nepxion.gitee.io/docs/polaris-doc/All.jpg)

⑤ Discovery【探索】域网关实施图

![](http://nepxion.gitee.io/docs/polaris-doc/DomainEnable.jpg)

⑥ Discovery【探索】非域网关实施图

![](http://nepxion.gitee.io/docs/polaris-doc/DomainDisable.jpg)

⑦ Discovery【探索】全局订阅实施图

![](http://nepxion.gitee.io/docs/polaris-doc/GlobalSub.jpg)

## 简介
Discovery【探索】微服务框架，基于Spring Cloud & Spring Cloud Alibaba，Discovery服务注册发现、Ribbon负载均衡、Feign和RestTemplate调用、Spring Cloud Gateway和Zuul过滤等组件全方位增强的企业级微服务开源解决方案，更贴近企业级需求，更具有企业级的插件引入、开箱即用特征

① 微服务框架支持的基本功能，如下
- 支持阿里巴巴Spring Cloud Alibaba中间件生态圈
- 支持阿里巴巴Nacos、Eureka、Consul和Zookeeper四个服务注册发现中心
- 支持阿里巴巴Nacos、携程Apollo、Redis、Zookeeper、Consul和Etcd六个远程配置中心
- 支持阿里巴巴Sentinel、Hystrix和Resilience4J三个熔断限流降级权限中间件
- 支持OpenTracing和OpenTelemetry规范下的调用链中间件，Jaeger、SkyWalking和Zipkin等
- 支持Prometheus Micrometer和Spring Boot Admin两个指标中间件
- 支持Java Agent解决异步跨线程ThreadLocal上下文传递
- 支持Spring Spel解决蓝绿灰度参数的驱动逻辑
- 支持Spring Matcher解决元数据匹配的通配逻辑
- 支持Spring Cloud Gateway、Zuul网关和微服务三大模块的蓝绿灰度发布等一系列功能
- 支持和兼容Spring Cloud Edgware版、Finchley版、Greenwich版、Hoxton版和202x版

![](http://nepxion.gitee.io/docs/discovery-doc/Diagram.jpg)

② 微服务框架支持的应用功能，如下
- 全链路蓝绿灰度发布
    - 全链路版本、区域、 IP地址和端口匹配蓝绿发布
    - 全链路版本、区域、 IP地址和端口权重灰度发布
    - 全链路蓝 | 绿 | 兜底、蓝 | 兜底的蓝绿路由类型
    - 全链路稳定、灰度的灰度路由类型
    - 全链路网关、服务端到端混合蓝绿灰度发布
    - 全链路域网关、非域网关部署
    - 全链路条件驱动、非条件驱动  
    - 全链路前端触发后端蓝绿灰度发布
    - 全局订阅式蓝绿灰度发布
    - 全链路自定义网关、服务的过滤器、负载均衡策略类触发蓝绿灰度发布
    - 全链路动态变更元数据的蓝绿灰度发布
    - 全链路Header、Parameter、Cookie、域名、RPC Method等参数化规则策略驱动
    - 全链路本地和远程、局部和全局无参数化规则策略驱动
    - 全链路条件表达式、通配表达式支持
    - 全链路内置Header，支持定时Job的服务调用蓝绿灰度发布
- 全链路蓝绿灰度发布编排建模和流量侦测
    - 全链路蓝绿发布编排建模
    - 全链路灰度发布编排建模
    - 全链路蓝绿发布流量侦测
    - 全链路灰度发布流量侦测
    - 全链路蓝绿灰度发布混合流量侦测
- 全链路蓝绿灰度发布容灾
    - 发布失败下的版本故障转移
    - 并行发布下的版本偏好
- 服务下线场景下全链路蓝绿灰度发布，实时性的流量绝对无损
    - 全局唯一ID屏蔽
    - IP地址和端口屏蔽
- 异步场景下全链路蓝绿灰度发布
    - 异步跨线程Agent插件
    - Hystrix线程池隔离插件
- 全链路数据库和消息队列蓝绿发布
    - 基于多Datasource的数据库蓝绿发布
    - 基于多Queue的消息队列蓝绿发布
- 全链路规则策略推送
    - 基于远程配置中心的规则策略订阅推送
    - 基于Swagger和Rest的规则策略推送
    - 基于图形化桌面端和Web端的规则策略推送
- 全链路环境隔离和路由
    - 全链路环境隔离
    - 全链路环境路由
- 全链路可用区亲和性隔离和路由
    - 全链路可用区亲和性隔离
    - 全链路可用区亲和性路由
- 全链路服务隔离和准入
    - 消费端服务隔离
    - 提供端服务隔离
    - 注册发现隔离和准入
- 全链路服务限流熔断降级权限
    - Sentinel基于服务名的防护
    - Sentinel基于组的防护
    - Sentinel基于版本的防护
    - Sentinel基于区域的防护
    - Sentinel基于环境的防护
    - Sentinel基于可用区的防护
    - Sentinel基于IP地址和端口的防护
    - Sentinel自定义Header、Parameter、Cookie的防护
    - Sentinel自定义业务参数的防护
    - Sentinel自定义组合式的防护
- 全链路监控
    - 蓝绿灰度埋点和熔断埋点的调用链监控
    - 蓝绿灰度埋点和熔断埋点的日志监控
- 全链路服务侧注解
- 全链路服务侧API权限
- 元数据流量染色
    - Git插件自动化的元数据流量染色
    - 服务名前缀的元数据流量染色
    - 运维平台参数化的元数据流量染色
    - 注册中心动态化的元数据流量染色
    - 用户自定义的元数据流量染色
- 多活、多云、多机房流量切换
- Docker容器化和Kubernetes平台无缝支持部署
- 自动化测试、压力测试

③ 微服务框架易用性表现，如下
- 引入相关依赖到pom.xml
- 元数据Metadata流量染色。5大元数据根据不同的使用场景按需设置
    - 定义所属组名 - metadata.group，也可以通过服务名前缀来自动产生服务组名
    - 定义版本号 - metadata.version，也可以通过Git插件方式自动产生版本号
    - 定义所属区域名 - metadata.region
    - 定义所属环境 - metadata.env
    - 定义所属可用区 - metadata.zone
- 执行采用【约定大于配置】的准则，使用者根据不同的使用场景开启和关闭相关功能项或者属性值，达到最佳配置
- 规则策略文件设置和推送，或者通过业务Header、Parameter、Cookie触发，并通过Json格式的Header路由策略全链路传递

④ 微服务框架版本兼容列表，如下

![](http://nepxion.gitee.io/docs/icon-doc/tip.png) 提醒：版本号右边， `↑` 表示>=该版本号， `↓` 表示<=该版本号

| 框架版本 | 框架分支 | 框架状态 | Spring Cloud版本 | Spring Boot版本 | Spring Cloud Alibaba版本 |
| --- | --- | --- | --- | --- | --- |
| 7.x.x | master | ![](http://nepxion.gitee.io/docs/icon-doc/confirm_24.png) | 202x.x.x | 2.4.1 `↑` | N/A |
| 6.x.x | 6.x.x | ![](http://nepxion.gitee.io/docs/icon-doc/confirm_24.png) | Hoxton.SR5 `↑`<br>Hoxton<br>Greenwich<br>Finchley | 2.3.x.RELEASE<br>2.2.x.RELEASE<br>2.1.x.RELEASE<br>2.0.x.RELEASE | 2.2.x.RELEASE<br>2.2.x.RELEASE<br>2.1.x.RELEASE<br>2.0.x.RELEASE |
| ~~5.x.x~~ | ~~5.x.x~~ | ![](http://nepxion.gitee.io/docs/icon-doc/delete_24.png) | Greenwich | 2.1.x.RELEASE | 2.1.x.RELEASE |
| ~~4.x.x~~ | ~~4.x.x~~ | ![](http://nepxion.gitee.io/docs/icon-doc/delete_24.png) | Finchley | 2.0.x.RELEASE | 2.0.x.RELEASE |
| 3.x.x | 3.x.x | ![](http://nepxion.gitee.io/docs/icon-doc/arrow_up_24.png) | Edgware | 1.5.x.RELEASE | 1.5.x.RELEASE |
| ~~2.x.x~~ | ~~2.x.x~~ | ![](http://nepxion.gitee.io/docs/icon-doc/delete_24.png) | Dalston | 1.x.x.RELEASE | 1.5.x.RELEASE |
| ~~1.x.x~~ | ~~1.x.x~~ | ![](http://nepxion.gitee.io/docs/icon-doc/delete_24.png) | Camden | 1.x.x.RELEASE | 1.5.x.RELEASE |

![](http://nepxion.gitee.io/docs/icon-doc/confirm_24.png) 表示维护中 | ![](http://nepxion.gitee.io/docs/icon-doc/arrow_up_24.png) 表示不维护，但可用，强烈建议升级 | ![](http://nepxion.gitee.io/docs/icon-doc/delete_24.png) 表示不维护，不可用，已废弃

- 7.x.x版本（适用于202x.x.x）将继续维护
- 6.x.x版本（同时适用于Finchley、Greenwich和Hoxton）将继续维护
- 5.x.x版本（适用于Greenwich）已废弃
- 4.x.x版本（适用于Finchley）已废弃
- 3.x.x版本（适用于Edgware）不维护，但可用，强烈建议升级
- 2.x.x版本（适用于Dalston）已废弃
- 1.x.x版本（适用于Camden）已废弃

⑤ 相关中间件版本列表，如下

| 组件类型 | 组件版本 |
| --- | --- |
| 基础组件 | [![Guava](https://img.shields.io/maven-central/v/com.google.guava/guava.svg?label=Guava)](https://search.maven.org/artifact/com.google.guava/guava)<br>[![Caffeine](https://img.shields.io/maven-central/v/com.github.ben-manes.caffeine/caffeine.svg?label=Caffeine)](https://search.maven.org/artifact/com.github.ben-manes.caffeine/caffeine)<br>[![Dom4J](https://img.shields.io/maven-central/v/org.dom4j/dom4j.svg?label=Dom4J)](https://search.maven.org/artifact/org.dom4j/dom4j)<br>[![Swagger](https://img.shields.io/maven-central/v/io.springfox/springfox-swagger2?label=Swagger)](https://search.maven.org/artifact/io.springfox/springfox-swagger2) |
| 注册配置组件 | [![Apollo](https://img.shields.io/maven-central/v/com.ctrip.framework.apollo/apollo-client.svg?label=Apollo)](https://search.maven.org/artifact/com.ctrip.framework.apollo/apollo-client)<br>[![Zookeeper Curator](https://img.shields.io/maven-central/v/org.apache.curator/curator-framework.svg?label=Zookeeper%20Curator)](https://search.maven.org/artifact/org.apache.curator/curator-framework)<br>[![Consul](https://img.shields.io/maven-central/v/com.ecwid.consul/consul-api.svg?label=Consul)](https://search.maven.org/artifact/com.ecwid.consul/consul-api)<br>[![JEtcd](https://img.shields.io/maven-central/v/io.etcd/jetcd-core.svg?label=JEtcd)](https://search.maven.org/artifact/io.etcd/jetcd-core)<br>[![Nacos](https://img.shields.io/maven-central/v/com.alibaba.nacos/nacos-client.svg?label=Nacos)](https://search.maven.org/artifact/com.alibaba.nacos/nacos-client)<br>[![Eureka](https://img.shields.io/maven-central/v/com.netflix.eureka/eureka-client.svg?label=Eureka)](https://search.maven.org/artifact/com.netflix.eureka/eureka-client)<br>[![Redis](https://img.shields.io/maven-central/v/org.springframework.data/spring-data-redis.svg?label=Redis)](https://search.maven.org/artifact/org.springframework.data/spring-data-redis) |
| 防护组件 | [![Sentinel](https://img.shields.io/maven-central/v/com.alibaba.csp/sentinel-core.svg?label=Sentinel)](https://search.maven.org/artifact/com.alibaba.csp/sentinel-core)<br>[![Hystrix](https://img.shields.io/maven-central/v/com.netflix.hystrix/hystrix-core.svg?label=Hystrix)](https://search.maven.org/artifact/com.netflix.hystrix/hystrix-core) |
| 监控组件 | [![OpenTelemetry](https://img.shields.io/maven-central/v/io.opentelemetry/opentelemetry-api.svg?label=OpenTelemetry)](https://search.maven.org/artifact/io.opentelemetry/opentelemetry-api)<br>[![OpenTracing](https://img.shields.io/maven-central/v/io.opentracing/opentracing-api.svg?label=OpenTracing)](https://search.maven.org/artifact/io.opentracing/opentracing-api)<br>[![OpenTracing%20Sping%20Cloud](https://img.shields.io/maven-central/v/io.opentracing.contrib/opentracing-spring-cloud-starter.svg?label=OpenTracing%20Sping%20Cloud)](https://search.maven.org/artifact/io.opentracing.contrib/opentracing-spring-cloud-starter)<br>[![OpenTracing%20Jaeger](https://img.shields.io/maven-central/v/io.opentracing.contrib/opentracing-spring-jaeger-starter.svg?label=OpenTracing%20Jaeger)](https://search.maven.org/artifact/io.opentracing.contrib/opentracing-spring-jaeger-starter)<br>[![OpenTracing%20Concurrent](https://img.shields.io/maven-central/v/io.opentracing.contrib/opentracing-concurrent.svg?label=OpenTracing%20Concurrent)](https://search.maven.org/artifact/io.opentracing.contrib/opentracing-concurrent)<br>[![SkyWalking](https://img.shields.io/maven-central/v/org.apache.skywalking/apm-toolkit-opentracing.svg?label=SkyWalking)](https://search.maven.org/artifact/org.apache.skywalking/apm-toolkit-opentracing)<br>[![Spring Boot](https://img.shields.io/maven-central/v/de.codecentric/spring-boot-admin-dependencies.svg?label=Spring%20Boot%20Admin)](https://search.maven.org/artifact/de.codecentric/spring-boot-admin-dependencies) |
| Spring组件 | [![Alibaba Spring](https://img.shields.io/maven-central/v/com.alibaba.spring/spring-context-support.svg?label=Alibaba%20Spring)](https://search.maven.org/artifact/com.alibaba.spring/spring-context-support)<br>[![Spring Cloud](https://img.shields.io/maven-central/v/org.springframework.cloud/spring-cloud-dependencies.svg?label=Spring%20Cloud)](https://search.maven.org/artifact/org.springframework.cloud/spring-cloud-dependencies)<br>[![Spring Cloud Alibaba](https://img.shields.io/maven-central/v/com.alibaba.cloud/spring-cloud-alibaba-dependencies.svg?label=Spring%20Cloud%20Alibaba)](https://search.maven.org/artifact/com.alibaba.cloud/spring-cloud-alibaba-dependencies)<br>[![Spring Boot](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-dependencies.svg?label=Spring%20Boot)](https://search.maven.org/artifact/org.springframework.boot/spring-boot-dependencies) |

⑥ 相关仓库代码行数列表，如下

| 仓库 | 代码行数 |
| --- | --- |
| Discovery | [![Total lines](https://tokei.rs/b1/github/Nepxion/Discovery?category=lines)](https://tokei.rs/b1/github/Nepxion/Discovery?category=lines) |
| DiscoveryGuide | [![Total lines](https://tokei.rs/b1/github/Nepxion/DiscoveryGuide?category=lines)](https://tokei.rs/b1/github/Nepxion/DiscoveryGuide?category=lines) |
| DiscoveryAgent | [![Total lines](https://tokei.rs/b1/github/Nepxion/DiscoveryAgent?category=lines)](https://tokei.rs/b1/github/Nepxion/DiscoveryAgent?category=lines) |
| DiscoveryContrib | [![Total lines](https://tokei.rs/b1/github/Nepxion/DiscoveryContrib?category=lines)](https://tokei.rs/b1/github/Nepxion/DiscoveryContrib?category=lines) |
| DiscoveryPlatform | [![Total lines](https://tokei.rs/b1/github/Nepxion/DiscoveryPlatform?category=lines)](https://tokei.rs/b1/github/Nepxion/DiscoveryPlatform?category=lines) |
| DiscoveryUI | [![Total lines](https://tokei.rs/b1/github/Nepxion/DiscoveryUI?category=lines)](https://tokei.rs/b1/github/Nepxion/DiscoveryUI?category=lines) |

## 鸣谢
![](http://nepxion.gitee.io/docs/icon-doc/star4.png) 郑重致谢
- 感谢阿里巴巴中间件Nacos、Sentinel和Spring Cloud Alibaba团队，尤其是Nacos负责人@彦林、@于怀，Sentinel负责人@宿何、@子衿，Spring Cloud Alibaba负责人@小马哥、@洛夜、@亦盏的技术支持
- 感谢携程Apollo团队，尤其是@宋顺的技术支持
- 感谢所有Committers和Contributors
- 感谢所有帮忙分析和定位问题的同学
- 感谢所有提出宝贵建议和意见的同学
- 感谢阿里巴巴中间件Nacos和Spring Cloud Alibaba团队，纳入本框架为相关开源项目

<img src="http://nepxion.gitee.io/docs/discovery-doc/AwardNacos1.jpg" alt="Nacos" width="50%"><img src="http://nepxion.gitee.io/docs/discovery-doc/AwardSCA1.jpg" alt="Spring Cloud Alibaba" width="50%">

- 感谢支持和使用本框架的公司和企业。不完全统计，目前社区开源项目（包括本框架以及关联框架或组件）已经被如下公司使用或者调研

<table>
  <tbody>
      <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/华为.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/腾讯.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/京东.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/顺丰.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/中国移动.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/平安银行.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/平安科技.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/平安一账通.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/招商银行.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/民生银行.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/浦发银行信用卡.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/三峡银行.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/亿联银行.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/中国人寿.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/太平洋保险.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/中国太平.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/众安保险.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/珍保.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/国家电网.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/东方航空.png"></td>
   </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/恒大.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/碧桂园.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/华住会.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/东风汽车.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/南瑞.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/宇信.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/蔷薇.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/掌门.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/跟谁学.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/瑞幸.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/城家.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/海尔.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/三七互娱.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/诺亚财富.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/快盈.png"></td>
     </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/喜马拉雅.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/微鲸.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/东华软件.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/捷顺.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/御家汇.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/融都.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/天阙.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/惠借.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/新云网.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/毅德零空.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/软通动力.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/冰鉴.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/轻舟.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/数梦工场.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/星艺装饰.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/青客.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/顶昂.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/卖客星球.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/思必驰.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/弘人.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/依威能源.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/伯乔.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/创软.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/颐尔信.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/炫贵.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/明略.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/必胜道.png">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/中交兴路.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/太谷电力.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/小电.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/学海.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/资云同商.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/巨玩.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/吾享.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/风影.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/云帐房.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/壹站.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/蓝蜂.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/智慧校园.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/睿住.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/天音.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/药链.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/琢创.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/悟空丰运.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/思派.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/手心美业.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/神州商龙.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/润民.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/鑫安利中.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/橙单.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/万达信息.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/百世快递.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/贝壳找房.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/KK直播.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/雪球科技.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/中商惠民.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/果果乐学.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/林氏木业.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/兰亮.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/吹星屯.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/诺基亚.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/中科云谷.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/希捷速必达.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/趣淘鲸.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/创迹.png"></td>
    </tr>
    <tr align="center">
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/联想.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/物易云通.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/翡翠东方.png"></td>
      <td width="20%"><img style="max-height:75%;max-width:75%;" src="http://nepxion.gitee.io/docs/logo-doc/爱纷美.png"></td>
      <td width="20%"></td>
    </tr>	
  </tbody>
</table>

![](http://nepxion.gitee.io/docs/icon-doc/edit_32.png) 为提供更好的专业级服务，请更多已经使用本框架的公司和企业联系我，并希望在[Github Issues](https://github.com/Nepxion/Discovery/issues/56)上登记

![](http://nepxion.gitee.io/docs/icon-doc/chart_bar_32.png) 某大型银行信用卡新核心系统在生产环境接入Nepxion Discovery框架的服务实例数（包括异地双活，同城双活，多机房全部汇总）将近10000个

![](http://nepxion.gitee.io/docs/icon-doc/chart_bar_32.png) 某大型互联网教育公司在生产环境接入Nepxion Discovery框架的服务实例数截至到2021年2月已达到2600多个，基本接入完毕

<img src="http://nepxion.gitee.io/docs/discovery-doc/Result.jpg"/>

## 目录
- [简介](#简介)
- [鸣谢](#鸣谢)
- [请联系我](#请联系我)
- [相关链接](#相关链接)
    - [源码主页](#源码主页)
    - [发布主页](#发布主页)
    - [指南主页](#指南主页)
    - [文档主页](#文档主页)
    - [贡献主页](#贡献主页)	
- [工程架构](#工程架构)
    - [工程清单](#工程清单)
    - [架构核心](#架构核心)
    - [依赖引入](#依赖引入)	
- [准备工作](#准备工作)
    - [环境搭建](#环境搭建)
    - [启动服务](#启动服务)
    - [环境验证](#环境验证)
- [蓝绿灰度发布概念](#蓝绿灰度发布概念)
    - [蓝绿发布](#蓝绿发布)
    - [灰度发布](#灰度发布)
    - [滚动发布](#滚动发布)
- [全链路蓝绿灰度发布](#全链路蓝绿灰度发布)
    - [全链路蓝绿发布](#全链路蓝绿发布)
        - [全链路版本匹配蓝绿发布](#全链路版本匹配蓝绿发布)
        - [全链路区域匹配蓝绿发布](#全链路区域匹配蓝绿发布)
        - [全链路IP地址和端口匹配蓝绿发布](#全链路IP地址和端口匹配蓝绿发布)
    - [全链路条件蓝绿发布](#全链路条件蓝绿发布)
        - [全链路版本条件匹配蓝绿发布](#全链路版本条件匹配蓝绿发布)
        - [全链路区域条件匹配蓝绿发布](#全链路区域条件匹配蓝绿发布)
        - [全链路IP地址和端口条件匹配蓝绿发布](#全链路IP地址和端口条件匹配蓝绿发布)	
    - [全链路灰度发布](#全链路灰度发布)
        - [全链路版本权重灰度发布](#全链路版本权重灰度发布)
        - [全链路区域权重灰度发布](#全链路区域权重灰度发布)
    - [全链路条件灰度发布](#全链路条件灰度发布)
        - [全链路版本条件权重灰度发布](#全链路版本条件权重灰度发布)
        - [全链路区域条件权重灰度发布](#全链路区域条件权重灰度发布)
        - [全链路IP地址和端口权重条件灰度发布](#全链路IP地址和端口条件权重灰度发布)
    - [全链路端到端混合实施蓝绿灰度发布](#全链路端到端混合实施蓝绿灰度发布)
        - [全链路端到端实施蓝绿灰度发布](#全链路端到端实施蓝绿灰度发布)
        - [全链路混合实施蓝绿灰度发布](#全链路混合实施蓝绿灰度发布)
    - [全链路域网关和非域网关部署](#全链路域网关和非域网关部署)
        - [全链路域网关部署](#全链路域网关部署)
        - [全链路非域网关部署](#全链路非域网关部署)
    - [全链路前端触发后端蓝绿灰度发布](#全链路前端触发后端蓝绿灰度发布)
        - [全链路驱动方式](#全链路驱动方式)
        - [全链路参数策略](#全链路参数策略)
    - [全局订阅式蓝绿灰度发布](#全局订阅式蓝绿灰度发布)
    - [全链路自定义蓝绿灰度发布](#全链路自定义蓝绿灰度发布)
        - [全链路自定义过滤器触发蓝绿灰度发布](#全链路自定义过滤器触发蓝绿灰度发布)
        - [全链路自定义负载均衡策略类触发蓝绿灰度发布](#全链路自定义负载均衡策略类触发蓝绿灰度发布)
    - [全链路动态变更元数据的蓝绿灰度发布](#全链路动态变更元数据的蓝绿灰度发布)
- [全链路蓝绿灰度发布编排建模和流量侦测](#全链路蓝绿灰度发布编排建模和流量侦测)
    - [全链路编排建模](#全链路编排建模)
        - [全链路蓝绿发布编排建模](#全链路蓝绿发布编排建模)
        - [全链路灰度发布编排建模](#全链路灰度发布编排建模)
    - [全链路流量侦测](#全链路流量侦测)
        - [全链路蓝绿发布流量侦测](#全链路蓝绿发布流量侦测)
        - [全链路灰度发布流量侦测](#全链路灰度发布流量侦测)
        - [全链路蓝绿灰度发布混合流量侦测](#全链路蓝绿灰度发布混合流量侦测)
- [全链路蓝绿灰度发布容灾](#全链路蓝绿灰度发布容灾)
    - [发布失败下的版本故障转移](#发布失败下的版本故障转移)
    - [并行发布下的版本偏好](#并行发布下的版本偏好)
- [服务下线场景下全链路蓝绿灰度发布](#服务下线场景下全链路蓝绿灰度发布)
    - [全局唯一ID屏蔽](#全局唯一ID屏蔽)
    - [IP地址和端口屏蔽](#IP地址和端口屏蔽)
- [异步场景下全链路蓝绿灰度发布](#异步场景下全链路蓝绿灰度发布)
    - [异步场景下DiscoveryAgent解决方案](#异步场景下DiscoveryAgent解决方案)
        - [异步跨线程DiscoveryAgent获取](#异步跨线程DiscoveryAgent获取)
        - [异步跨线程DiscoveryAgent使用](#异步跨线程DiscoveryAgent使用)
        - [异步跨线程DiscoveryAgent扩展](#异步跨线程DiscoveryAgent扩展)
    - [异步场景下Hystrix线程池隔离解决方案](#异步场景下Hystrix线程池隔离解决方案)
- [全链路数据库和消息队列蓝绿发布](#全链路数据库和消息队列蓝绿发布)
- [规则策略定义](#规则策略定义)
    - [规则策略格式定义](#规则策略格式定义)
    - [规则策略内容定义](#规则策略内容定义)
    - [规则策略示例](#规则策略示例)
- [规则策略推送](#规则策略推送)
    - [基于远程配置中心的规则策略订阅推送](#基于远程配置中心的规则策略订阅推送)
    - [基于Swagger和Rest的规则策略推送](#基于Swagger和Rest的规则策略推送)
    - [基于图形化桌面端和Web端的规则策略推送](#基于图形化桌面端和Web端的规则策略推送)
- [全链路环境隔离和路由](#全链路环境隔离和路由)
    - [全链路环境隔离](#全链路环境隔离)
    - [全链路环境路由](#全链路环境路由)
- [全链路可用区亲和性隔离和路由](#全链路可用区亲和性隔离和路由)
    - [全链路可用区亲和性隔离](#全链路可用区亲和性隔离)
    - [全链路可用区亲和性路由](#全链路可用区亲和性路由)
- [全链路服务隔离和准入](#全链路服务隔离和准入)
    - [消费端服务隔离](#消费端服务隔离)
        - [基于组负载均衡隔离](#基于组负载均衡隔离)
    - [提供端服务隔离](#提供端服务隔离)
        - [基于组Header传值策略隔离](#基于组Header传值策略隔离)
    - [注册发现隔离和准入](#注册发现隔离和准入)
        - [基于IP地址黑白名单注册准入](#基于IP地址黑白名单注册准入)
        - [基于最大注册数限制注册准入](#基于最大注册数限制注册准入)
        - [基于IP地址黑白名单发现准入](#基于IP地址黑白名单发现准入)
        - [自定义注册发现准入](#自定义注册发现准入)
- [全链路服务限流熔断降级权限](#全链路服务限流熔断降级权限)
    - [原生Sentinel注解](#原生Sentinel注解)
    - [原生Sentinel规则](#原生Sentinel规则)
        - [流控规则](#流控规则)
        - [降级规则](#降级规则)
        - [授权规则](#授权规则)
        - [系统规则](#系统规则)
        - [热点参数流控规则](#热点参数流控规则)	
    - [基于Sentinel-LimitApp扩展的防护](#基于Sentinel-LimitApp扩展的防护)
        - [基于服务名的防护](#基于服务名的防护)
        - [基于组的防护](#基于组的防护)
        - [基于版本的防护](#基于版本的防护)
        - [基于区域的防护](#基于区域的防护)
        - [基于环境的防护](#基于环境的防护)
        - [基于可用区的防护](#基于可用区的防护)
        - [基于IP地址和端口的防护](#基于IP地址和端口的防护)	
        - [自定义组合式的防护](#自定义组合式的防护)
- [全链路监控](#全链路监控)
    - [全链路调用链监控](#全链路调用链监控)
        - [蓝绿灰度埋点调用链监控](#蓝绿灰度埋点调用链监控)
        - [蓝绿灰度埋点Debug辅助监控](#蓝绿灰度埋点Debug辅助监控)
        - [Sentinel熔断埋点调用链监控](#Sentinel熔断埋点调用链监控)
        - [自定义埋点调用链监控](#自定义埋点调用链监控)
    - [全链路日志监控](#全链路日志监控)
        - [蓝绿灰度埋点日志监控](#蓝绿灰度埋点日志监控)
    - [全链路指标监控](#全链路指标监控)
        - [Prometheus监控](#Prometheus监控)
        - [Grafana监控](#Grafana监控)
        - [Spring-Boot-Admin监控](#Spring-Boot-Admin监控)
- [全链路服务侧注解](#全链路服务侧注解)
- [全链路服务侧API权限](#全链路服务侧API权限)
- [元数据流量染色](#元数据流量染色)
    - [基于Git插件自动创建版本号](#基于Git插件自动创建版本号)
    - [基于服务名前缀自动创建组名](#基于服务名前缀自动创建组名)
    - [基于运维平台运行参数自动创建版本号](#基于运维平台运行参数自动创建版本号)
    - [基于用户自定义创建版本号](#基于用户自定义创建版本号)
- [配置文件](#配置文件)
    - [流量染色配置](#流量染色配置)
    - [中间件属性配置](#中间件属性配置)
    - [功能开关配置](#功能开关配置)
    - [内置文件配置](#内置文件配置)
- [Docker容器化和Kubernetes平台支持](#Docker容器化和Kubernetes平台支持)
    - [Docker容器化](#Docker容器化)
    - [Kubernetes平台支持](#Kubernetes平台支持)
- [自动化测试](#自动化测试)
    - [架构设计](#架构设计)
    - [启动控制台](#启动控制台)
    - [配置文件](#配置文件)
    - [测试用例](#测试用例)
        - [测试包引入](#测试包引入)
        - [测试入口程序](#测试入口程序)
        - [普通调用测试](#普通调用测试)
        - [蓝绿灰度调用测试](#蓝绿灰度调用测试)
        - [扩展调用测试](#扩展调用测试)
    - [测试报告](#测试报告)
- [压力测试](#压力测试)
    - [测试环境](#测试环境)
    - [测试介绍](#测试介绍)
    - [测试步骤](#测试步骤)
- [附录](#附录)
    - [中间件服务器下载地址](#中间件服务器下载地址)	
- [Star走势图](#Star走势图)

## 请联系我
微信、钉钉、公众号和文档

![](http://nepxion.gitee.io/docs/zxing-doc/微信-1.jpg)![](http://nepxion.gitee.io/docs/zxing-doc/钉钉-1.jpg)![](http://nepxion.gitee.io/docs/zxing-doc/公众号-1.jpg)![](http://nepxion.gitee.io/docs/zxing-doc/文档-1.jpg)

## 相关链接

### 源码主页
[Discovery源码主页](https://github.com/Nepxion/Discovery)

[Polaris源码主页](https://github.com/Nepxion/Polaris)

### 发布主页
[DiscoveryAgent](https://github.com/Nepxion/DiscoveryAgent/releases)

[DiscoveryDesktop](https://github.com/Nepxion/DiscoveryUI/releases)

### 指南主页
[Discovery指南主页](https://github.com/Nepxion/DiscoveryGuide)

[Polaris指南主页](https://github.com/Nepxion/PolarisGuide)

### 文档主页
[文档主页](https://gitee.com/Nepxion/Docs/tree/master/web-doc)

### 贡献主页

- [Nepxion Discovery - Spring Cloud灰度发布神器](https://carlzone.blog.csdn.net/article/details/109787397)
- [Nepxion Discovery - 灰度发布初体验](https://carlzone.blog.csdn.net/article/details/109839808)
- [Nepxion Discovery - 项目结构简介](https://carlzone.blog.csdn.net/article/details/110183368)
- [Nepxion Discovery - Spring Cloud 服务注册抽象](https://carlzone.blog.csdn.net/article/details/110428485)
- [Nepxion Discovery - 服务注册发现增强](https://carlzone.blog.csdn.net/article/details/110505550)
- [Nepxion Discovery - Spring Cloud 负载均衡处理](https://carlzone.blog.csdn.net/article/details/110678839)
- [Nepxion Discovery - 对 Spring Cloud 负载均衡扩展支持服务灰度发布](https://carlzone.blog.csdn.net/article/details/110625764)
- [Nepxion Discovery - 配置中心支持灰度配置](https://carlzone.blog.csdn.net/article/details/110913399)
- [Nepxion Discovery - 服务灰度发布参数的支持](https://carlzone.blog.csdn.net/article/details/110946620)
- [Nepxion Discovery - 对网关和微服务三大模式的支持](https://blog.csdn.net/u012410733/article/details/111026618)
- [Nepxion Discovery - 全链路界面操作蓝绿灰度发布](https://blog.csdn.net/u012410733/article/details/111304744)
- [Nepxion Discovery - Discovery Agent 解决异步场景线程 ThreadLocal 上下文丢失问题](https://blog.csdn.net/u012410733/article/details/111405081)
- [Nepxion Discovery - 全链路调用链监控](https://carlzone.blog.csdn.net/article/details/111806972)

## 工程架构

### 工程清单
① Discovery工程清单

| 工程名 | 描述 |
| --- | --- |
| <img src="http://nepxion.gitee.io/docs/icon-doc/direction_south.png"> discovery-commons | 通用模块目录 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-common | 通用模块 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-common-apollo | 封装Apollo通用配置操作逻辑 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-common-nacos | 封装Nacos通用配置操作逻辑 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-common-redis | 封装Redis通用配置操作逻辑 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-common-zookeeper | 封装Zookeeper通用配置操作逻辑 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-common-consul | 封装Consul通用配置操作逻辑 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-common-etcd | 封装Etcd通用配置操作逻辑 |
| <img src="http://nepxion.gitee.io/docs/icon-doc/direction_south.png"> discovery-plugin-framework | 基本框架目录 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-framework-starter| 基本框架的Starter |
| <img src="http://nepxion.gitee.io/docs/icon-doc/direction_south.png"> discovery-plugin-register-center | 注册中心目录 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-register-center-starter | 注册中心的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-register-center-starter-eureka | 注册中心的Eureka Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-register-center-starter-consul | 注册中心的Consul Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-register-center-starter-zookeeper | 注册中心的Zookeeper Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-register-center-starter-nacos | 注册中心的Nacos Starter |
| <img src="http://nepxion.gitee.io/docs/icon-doc/direction_south.png"> discovery-plugin-config-center | 配置中心目录 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-config-center-starter | 配置中心的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-config-center-starter-apollo | 配置中心的Apollo Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-config-center-starter-nacos | 配置中心的Nacos Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-config-center-starter-redis | 配置中心的Redis Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-config-center-starter-zookeeper | 配置中心的Zookeeper Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-config-center-starter-consul | 配置中心的Consul Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-config-center-starter-etcd | 配置中心的Etcd Starter |
| <img src="http://nepxion.gitee.io/docs/icon-doc/direction_south.png"> discovery-plugin-admin-center | 管理中心目录 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-admin-center-starter | 管理中心的Starter |
| <img src="http://nepxion.gitee.io/docs/icon-doc/direction_south.png"> discovery-plugin-strategy | 路由策略目录 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter | 路由策略的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-service | 路由策略在微服务端的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-service-sentinel | 路由策略在微服务端的Sentinel Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-zuul | 路由策略在Zuul网关端的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-gateway | 路由策略在Spring Cloud Gateway网关端的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-hystrix | 路由策略下，Hystrix做线程模式的服务隔离必须引入插件的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-opentelemetry | 路由策略的OpenTelemetry调用链的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-opentracing | 路由策略的OpenTracing调用链的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-skywalking | 路由策略的SkyWalking调用链的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-sentinel | 路由策略的Sentinel Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-sentinel-local | 路由策略的Sentinel Local配置订阅的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-sentinel-apollo | 路由策略的Sentinel Apollo配置订阅的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-sentinel-nacos | 路由策略的Sentinel Nacos配置订阅的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-sentinel-monitor | 路由策略的Sentinel监控抽象的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-sentinel-opentelemetry | 路由策略的Sentinel OpenTelemetry调用链的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-sentinel-opentracing | 路由策略的Sentinel OpenTracing调用链的Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-strategy-starter-sentinel-skywalking | 路由策略的Sentinel SkyWalking调用链的Starter |
| <img src="http://nepxion.gitee.io/docs/icon-doc/direction_south.png"> discovery-plugin-test | 测试模块目录 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-plugin-test-starter | 自动化测试的Starter |
| <img src="http://nepxion.gitee.io/docs/icon-doc/direction_south.png"> discovery-console | 控制平台目录 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-console-starter | 控制平台的starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-console-starter-apollo | 控制平台的Apollo Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-console-starter-nacos | 控制平台的Nacos Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-console-starter-redis | 控制平台的Redis Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-console-starter-zookeeper | 控制平台的Zookeeper Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-console-starter-consul | 控制平台的Consul Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-console-starter-etcd | 控制平台的Etcd Starter |
| <img src="http://nepxion.gitee.io/docs/icon-doc/direction_south.png"> discovery-springcloud-examples | 示例目录 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-springcloud-example-admin | Spring Boot Admin服务台示例 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-springcloud-example-console | 控制平台示例 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-springcloud-example-eureka | Eureka服务器示例 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-springcloud-example-service | 微服务示例 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-springcloud-example-zuul | Zuul网关示例 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-springcloud-example-gateway | Spring Cloud Gateway网关示例 |

② DiscoveryAgent工程清单

| 工程名 | 描述 |
| --- | --- |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-agent-starter | 异步跨线程Agent Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-agent-starter-plugin-strategy | 路由策略的异步跨线程Agent Plugin Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-agent-starter-plugin-mdc | MDC日志的异步跨线程Agent Plugin Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-agent-example | 异步跨线程示例 |

③ DiscoveryContrib工程清单

| 工程名 | 描述 |
| --- | --- |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-contrib-plugin-starter | 第三方非微服务范畴中间件的蓝绿灰度发布Contrib Plugin Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-contrib-plugin-starter-rocketmq | RocketMQ的蓝绿灰度发布Contrib Plugin Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-contrib-plugin-starter-shardingsphere | ShardingSphere日志的蓝绿灰度发布Contrib Plugin Starter |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> discovery-contrib-example | 第三方非微服务范畴中间件的蓝绿灰度发布示例 |

④ DiscoveryPlatform工程清单

| 工程名 | 描述 |
| --- | --- |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> platform | Nepxion Discovery 服务治理平台后端控制台 |

⑤ DiscoveryUI工程清单

| 工程名 | 描述 |
| --- | --- |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> desktop | Nepxion Discovery 服务治理平台前端桌面版 |
| &nbsp;&nbsp;<img src="http://nepxion.gitee.io/docs/icon-doc/direction_west.png"> web | Nepxion Discovery 服务治理平台前端Web版 |

### 架构核心
- 服务治理架构图

![](http://nepxion.gitee.io/docs/discovery-doc/Govern.jpg)

- 模块结构图

![](http://nepxion.gitee.io/docs/discovery-doc/Module.jpg)

### 依赖引入

① 服务注册发现依赖引入

服务注册发现中间件的四个插件，必须引入其中一个
```xml
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-register-center-starter-nacos</artifactId>
    <artifactId>discovery-plugin-register-center-starter-eureka</artifactId>
    <artifactId>discovery-plugin-register-center-starter-consul</artifactId>
    <artifactId>discovery-plugin-register-center-starter-zookeeper</artifactId>
    <version>${discovery.version}</version>
</dependency>
```

② 配置中心依赖引入

配置中心中间件的六个插件，选择引入其中一个
```xml
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-config-center-starter-apollo</artifactId>
    <artifactId>discovery-plugin-config-center-starter-nacos</artifactId>
    <artifactId>discovery-plugin-config-center-starter-redis</artifactId>
    <artifactId>discovery-plugin-config-center-starter-zookeeper</artifactId>
    <artifactId>discovery-plugin-config-center-starter-consul</artifactId>
    <artifactId>discovery-plugin-config-center-starter-etcd</artifactId>
    <version>${discovery.version}</version>
</dependency>
```

③ 管理中心依赖引入

选择引入
```xml
<dependency>
    <groupId>${project.groupId}</groupId>
    <artifactId>discovery-plugin-admin-center-starter</artifactId>
    <version>${discovery.version}</version>
</dependency>
```

④ 路由策略依赖引入

微服务端、网关Zuul端和网关Spring Cloud Gateway端三个路由策略插件，选择引入其中一个
```xml
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-strategy-starter-service</artifactId>
    <artifactId>discovery-plugin-strategy-starter-zuul</artifactId>
    <artifactId>discovery-plugin-strategy-starter-gateway</artifactId>
    <version>${discovery.version}</version>
</dependency>
```

⑤ 防护插件依赖引入

- Sentinel防护插件。只适用于微服务端

```xml
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-strategy-starter-service-sentinel</artifactId>
    <version>${discovery.version}</version>
</dependency>
```

- Sentinel防护的数据源插件，选择引入其中一个

```xml
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-strategy-sentinel-starter-nacos</artifactId>
    <artifactId>discovery-plugin-strategy-sentinel-starter-apollo</artifactId>
    <artifactId>discovery-plugin-strategy-sentinel-starter-local</artifactId>
    <version>${discovery.version}</version>
</dependency>
```

- Hystrix防护插件。Hystrix线程池隔离模式下必须引入该插件

```xml
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-strategy-starter-hystrix</artifactId>
    <version>${discovery.version}</version>
</dependency>
```

⑥ 控制台依赖引入

控制台对于配置中心中间件的四个插件，选择引入其中一个
```xml
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-console-starter-apollo</artifactId>
    <artifactId>discovery-console-starter-nacos</artifactId>
    <artifactId>discovery-console-starter-redis</artifactId>
    <artifactId>discovery-console-starter-zookeeper</artifactId>
    <version>${discovery.version}</version>
</dependency>
```

⑦ 调用链插件依赖引入

支持微服务端、网关Zuul端和网关Spring Cloud Gateway端，选择引入其中一个

![](http://nepxion.gitee.io/docs/icon-doc/warning.png) 需要注意，该模块支持F版或更高版本
```xml
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-strategy-sentinel-starter-opentelemetry</artifactId>
    <artifactId>discovery-plugin-strategy-sentinel-starter-opentracing</artifactId>
    <artifactId>discovery-plugin-strategy-sentinel-starter-skywalking</artifactId>
    <version>${discovery.version}</version>
</dependency>
```

⑧ 自动化测试插件依赖引入
```xml
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-test-starter</artifactId>
    <version>${discovery.version}</version>
</dependency>
```

⑨ 异步跨线程Agent引入
```
-javaagent:/discovery-agent/discovery-agent-starter-${discovery.agent.version}.jar -Dthread.scan.packages=com.abc;com.xyz
```

## 准备工作
为了更好的阐述框架的各项功能，本文围绕指南示例展开，请使用者先进行下面的准备工作。指南示例以Nacos为服务注册中心和配置中心展开介绍，使用者可自行换成其它服务注册中心和配置中心

### 环境搭建
① 下载代码，Git clone [https://github.com/Nepxion/DiscoveryGuide.git](https://github.com/Nepxion/DiscoveryGuide.git)

② 代码导入IDE

③ 下载Nacos服务器

- 从[https://github.com/alibaba/nacos/releases](https://github.com/alibaba/nacos/releases)获取nacos-server-x.x.x.zip，并解压

④ 启动Nacos服务器

- Windows环境下，运行bin目录下的startup.cmd
- Linux环境下，运行bin目录下的startup.sh

### 启动服务 
- 在IDE中，启动四个应用服务和两个网关服务，控制平台服务和监控平台服务可选，如下

| 类名 | 微服务 | 服务端口 | 版本 | 区域 | 环境 | 可用区 |
| --- | --- | --- | --- | --- | -- | -- | 
| DiscoveryGuideServiceA1.java | A1 | 3001 | 1.0 | dev | env1 | zone1 |
| DiscoveryGuideServiceA2.java | A2 | 3002 | 1.1 | qa | common | zone2 |
| DiscoveryGuideServiceB1.java | B1 | 4001 | 1.0 | qa | env1 | zone1 |
| DiscoveryGuideServiceB2.java | B2 | 4002 | 1.1 | dev | common | zone2 |
| DiscoveryGuideGateway.java | Gateway | 5001 | 1.0 | 无 | 无 | 无 |
| DiscoveryGuideZuul.java | Zuul | 5002 | 1.0 | 无 | 无 | 无 |
| DiscoveryGuideConsole.java | Console | 6001 | 1.0 | 无 | 无 | 无 |
| DiscoveryGuideAdmin.java | Admin | 6002 | 1.0 | 无 | 无 | 无 |

- 部署拓扑图

![](http://nepxion.gitee.io/docs/discovery-doc/BasicTopology.jpg)

全链路路径， 如下
```
API网关 -> 服务A（两个实例） -> 服务B（两个实例）
```

### 环境验证
通过Postman工具验证

- 导入Postman的测试脚本postman.json（位于根目录下）

- 在Postman中执行目录结构下〔Nepxion〕->〔Discovery指南网关接口〕->〔Gateway网关调用示例〕，调用地址为[http://localhost:5001/discovery-guide-service-a/invoke/gateway](http://localhost:5001/discovery-guide-service-a/invoke/gateway)，相关的Header值已经预设，供开发者修改。执行通过Spring Cloud Gateway网关发起的调用，结果为如下格式

```
gateway 
-> [ID=discovery-guide-service-a][T=service][P=Nacos][H=192.168.0.107:3001][V=1.0][R=dev][E=env1][Z=zone1][G=discovery-guide-group][TID=48682.7508.15870951148324081][SID=49570.77.15870951148480000] 
-> [ID=discovery-guide-service-b][T=service][P=Nacos][H=192.168.0.107:4001][V=1.0][R=qa][E=env1][Z=zone2][G=discovery-guide-group][TID=48682.7508.15870951148324081][SID=49571.85.15870951189970000]
```

- 在Postman中执行目录结构下〔Nepxion〕->〔Discovery指南网关接口〕->〔Zuul网关调用示例〕，调用地址为[http://localhost:5002/discovery-guide-service-a/invoke/zuul](http://localhost:5002/discovery-guide-service-a/invoke/zuul)，相关的Header值已经预设，供开发者修改。执行通过Zuul网关发起的调用，结果为如下格式

```
zuul 
-> [ID=discovery-guide-service-a][T=service][P=Nacos][H=192.168.0.107:3001][V=1.0][R=dev][E=env1][Z=zone1][G=discovery-guide-group][TID=48682.7508.15870951148324081][SID=49570.77.15870951148480000] 
-> [ID=discovery-guide-service-b][T=service][P=Nacos][H=192.168.0.107:4001][V=1.0][R=qa][E=env1][Z=zone2][G=discovery-guide-group][TID=48682.7508.15870951148324081][SID=49571.85.15870951189970000]
```

- 在Postman中多种同步和异步的调用方式，异步方式需要增加DiscoveryAgent，才能保证蓝绿发布路由调用的成功

![](http://nepxion.gitee.io/docs/icon-doc/information.png) 特别提醒

> 对于Spring Cloud 202x版，由于它采用的负载均衡Spring Cloud LoadBalancer是基于异步的WebFlux，所以必须加上DiscoveryAgent，如下方式

> -javaagent:C:/opt/discovery-agent/discovery-agent-starter-${discovery.agent.version}.jar -Dthread.scan.packages=reactor.core.publisher

| URL | 调用方式 |
| --- | --- |
| /invoke/ | 同步调用 |
| /invoke-async/ | @Async注解方式的异步调用 |
| /invoke-thread/ | 单线程方式的异步调用 |
| /invoke-threadpool/ | 线程池方式的异步调用 |

- 上述步骤在下面每次更改规则策略的时候执行，并验证结果和规则策略的期望值是否相同

## 蓝绿灰度发布概念

### 蓝绿发布

蓝绿发布 Blue-Green Deployment

① 概念

不停机旧版本，部署新版本，通过用户标记将流量在新版本和老版本切换，属无损发布

② 优点

新版本升级和老版本回滚迅速。用户可以灵活控制流量走向

③ 缺点

成本较高，需要部署两套环境（蓝/绿）。新版本出现问题，切换不及时，会造成大面积故障

![](http://nepxion.gitee.io/docs/discovery-doc/BlueGreenConcept.jpg)

### 灰度发布

灰度发布 Gray Release（又名金丝雀发布 Canary Release）

① 概念

不停机旧版本，部署新版本，低比例流量（例如：5%）切换到新版本，高比例流量（例如：95%）走旧版本，通过监控观察无问题，逐步扩大范围，最终把所有流量都迁移到新版本上，下线旧版本。属无损发布

② 优点

灵活简单，不需要用户标记驱动。安全性高，新版本如果出现问题，只会发生在低比例的流量上

③ 缺点

流量配比递增的配置修改，带来额外的操作成本。用户覆盖狭窄，低比例流量未必能发现所有问题

![](http://nepxion.gitee.io/docs/discovery-doc/GrayConcept.jpg)

### 滚动发布

滚动发布 Rolling Release

① 概念

每次只升级一个或多个服务，升级完成监控观察，不断执行这个过程，直到集群中的全部旧版本升级到新版本。停止旧版本的过程中，无法精确计算旧版本是否已经完成它正在执行的工作，需要靠业务自身去判断。属有损发布

② 优点

出现问题影响范围很小，只会发生在若干台正在滚动发布的服务上

③ 缺点

发布和回滚需要较长的时间周期。按批次停止旧版本，启动新版本，由于旧版本不保留，一旦全部升级完毕后才发现问题，则无法快速回滚，必须重新降级部署

![](http://nepxion.gitee.io/docs/discovery-doc/RollingConcept.jpg)

## 全链路蓝绿灰度发布

### 全链路蓝绿发布
> 经典场景：当调用请求从网关或者服务发起的时候，通过Header | Parameter | Cookie一种或者几种参数进行驱动，在路由过滤中，根据这些参数，选择在配置中心配置的蓝路由 | 绿路由 | 兜底路由的规则策略（Json格式），并把命中的规则策略转化为策略路由Header（n-d-开头），实现全链路传递。每个端到端服务接收到策略路由Header后，执行负载均衡时，该Header跟注册中心的对应元数据进行相关比较，不符合条件的实例进行过滤，从而实现全链路蓝绿发布

> 实施概要：只涉及当前正在发布的服务，例如，对于 〔网关〕->〔A服务〕->〔B服务〕->〔C服务〕->〔D服务〕调用链来说，如果当前只是B服务和C服务正在实施发布，那么，只需要把B服务和C服务配置到规则策略中，其它则不需要配置。发布结束后，即B服务和C服务的所有实例都完全一致，例如，版本号都只有唯一一个，那么清除掉在配置中心配置的规则策略即可，从而进行下一轮全链路蓝绿发布

![](http://nepxion.gitee.io/docs/discovery-doc/BlueGreen.jpg)

![](http://nepxion.gitee.io/docs/icon-doc/tip.png) 小贴士

n-d-的含义：n为Nepxion首字母，d为Discovery首字母

#### 全链路版本匹配蓝绿发布
增加Spring Cloud Gateway的版本匹配蓝绿发布策略，Group为discovery-guide-group，Data Id为discovery-guide-gateway，策略内容如下，实现从Spring Cloud Gateway发起的调用全链路都走版本为1.0的服务
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version>1.0</version>
    </strategy>
</rule>
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide2-1.jpg)

如果希望每个服务的版本分别指定，那么策略内容如下，实现从Spring Cloud Gateway发起的调用走1.0版本的a服务，走1.1版本的b服务
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version>{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.1"}</version>
    </strategy>
</rule>
```

当所有服务都选同一版本的时候，下面第1条和第2条是等效的
```
1. <version>1.0</version>
2. <version>{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.0"}</version>
```

如果上述表达式还未满足需求，也可以采用通配表达式方式（具体详细用法，参考Spring AntPathMatcher），通过Spring Matcher的通配表达式，支持多个通配*、单个通配?等全部标准表达式用法
```
* - 表示调用范围为所有版本
1.* - 表示调用范围为1开头的所有版本
```

例如
```
"discovery-guide-service-b":"1.*;1.2.?"
```
表示discovery-guide-service-b服务的版本调用范围是1开头的所有版本，或者是1.2开头的所有版本（末尾必须是1个字符），多个用分号隔开

![](http://nepxion.gitee.io/docs/icon-doc/tip.png) 提醒：非条件驱动下的全链路蓝绿发布跟Header驱动下的全链路蓝绿发布等效，例如
```
n-d-version=1.0
n-d-version={"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.0"}
```

版本匹配蓝绿发布架构图

![](http://nepxion.gitee.io/docs/discovery-doc/RouteVersion.jpg)

#### 全链路区域匹配蓝绿发布
增加Zuul的区域匹配蓝绿发布策略，Group为discovery-guide-group，Data Id为discovery-guide-zuul，策略内容如下，实现从Zuul发起的调用全链路都走区域为dev的服务
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <region>dev</region>
    </strategy>
</rule>
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide2-3.jpg)

如果希望每个服务的版本分别指定，那么策略内容如下，实现从Zuul发起的调用走dev区域的a服务，走qa区域的b服务
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version>{"discovery-guide-service-a":"dev", "discovery-guide-service-b":"qa"}</version>
    </strategy>
</rule>
```

当所有服务都选同一区域的时候，下面第1条和第2条是等效的
```
1. <region>dev</region>
2. <region>{"discovery-guide-service-a":"dev", "discovery-guide-service-b":"dev"}</region>
```

如果上述表达式还未满足需求，也可以采用通配表达式方式（具体详细用法，参考Spring AntPathMatcher），通过Spring Matcher的通配表达式，支持多个通配*、单个通配?等全部标准表达式用法
```
* - 表示调用范围为所有区域
d* - 表示调用范围为d开头的所有区域
```

例如
```
"discovery-guide-service-b":"d*;q?"
```
表示discovery-guide-service-b服务的区域调用范围是d开头的所有区域，或者是q开头的所有区域（末尾必须是1个字符），多个用分号隔开

![](http://nepxion.gitee.io/docs/icon-doc/tip.png) 提醒：非条件驱动下的全链路蓝绿发布跟Header驱动下的全链路蓝绿发布等效，例如
```
n-d-region=dev
n-d-region={"discovery-guide-service-a":"dev", "discovery-guide-service-b":"dev"}
```

区域匹配蓝绿发布架构图

![](http://nepxion.gitee.io/docs/discovery-doc/RouteRegion.jpg)

#### 全链路IP地址和端口匹配蓝绿发布
增加Zuul的IP地址和端口匹配蓝绿发布策略，Group为discovery-guide-group，Data Id为discovery-guide-zuul，策略内容如下，实现从Zuul发起的调用走指定IP地址和端口，或者指定IP地址，或者指定端口（下面策略以端口为例）的服务
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <!-- <address>127.0.0.1:3001</address> -->
        <!-- <address>127.0.0.1</address> -->
        <address>3001</address>
    </strategy>
</rule>
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide2-5.jpg)

如果希望每个服务的IP地址或者端口分别指定，那么策略内容如下，实现从Zuul发起的调用走3001端口的a服务，走4001端口的b服务
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <address>{"discovery-guide-service-a":"3001", "discovery-guide-service-b":"4001"}</address>
    </strategy>
</rule>
```

当所有服务都选同一端口的时候，下面第1条和第2条是等效的
```
1. <address>3001</address>
2. <address>{"discovery-guide-service-a":"3001", "discovery-guide-service-b":"3001"}</address>
```

如果上述表达式还未满足需求，也可以采用通配表达式方式（具体详细用法，参考Spring AntPathMatcher），通过Spring Matcher的通配表达式，支持多个通配*、单个通配?等全部标准表达式用法
```
* - 表示调用范围为所有端口
3* - 表示调用范围为3开头的所有端口
```

例如
```
"discovery-guide-service-b":"3*;400?"
```
表示discovery-guide-service-b服务的端口调用范围是3开头的所有端口，或者是4开头的所有端口（末尾必须是1个字符），多个用分号隔开

![](http://nepxion.gitee.io/docs/icon-doc/tip.png) 提醒：非条件驱动下的全链路蓝绿发布跟Header驱动下的全链路蓝绿发布等效，例如
```
n-d-address=3001
n-d-address={"discovery-guide-service-a":"3001", "discovery-guide-service-b":"3001"}
```

IP地址和端口匹配蓝绿发布架构图

![](http://nepxion.gitee.io/docs/discovery-doc/RouteAddress.jpg)

### 全链路条件蓝绿发布

#### 全链路版本条件匹配蓝绿发布
通过Header、Parameter、Cookie驱动参数和条件表达式结合，把业务定义的这三个驱动参数转化成全链路传递的策略路由Header，执行基于版本匹配的蓝、绿、兜底三条路由驱动，实现全链路版本条件匹配蓝绿发布

![](http://nepxion.gitee.io/docs/icon-doc/information.png) 驱动参数

① Header、Parameter、Cookie参数传递。对于要驱动发布的参数，例如，业务参数user，可以选择Header、Parameter、Cookie其中任意一个传递，都是等效的

② Header、Parameter、Cookie参数优先级。对于要驱动发布的参数，例如，业务参数user，如果在这三者中都存在，且值不相同，那么取值优先级Header > Parameter > Cookie

③ Header、Parameter、Cookie参数混合。对于要驱动发布的参数，如果不止一个，例如，业务参数user、age、address，可以全部是Header或者Parameter或者Cookie，也可以是这三者混合传递：user通过Header传递，age通过Parameter传递，address通过Cookie传递

![](http://nepxion.gitee.io/docs/icon-doc/information.png) 条件表达式

① Spring Spel的条件表达式，支持等于=、不等于!=、大于>、小于<、与&&、或||、匹配matches，以及加减乘除取模等全部标准表达式用法

![](http://nepxion.gitee.io/docs/icon-doc/tip.png) 小贴士

通过Spring Spel的matches条件表达式

- 可通过如下表达式，判断入参是否为`邮件格式`

```
[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}
```

- 可通过如下表达式，判断入参是否为`三个字母，结尾等于2`

```
[a-z]{3}2
```

② Spring Spel的条件表达式，整合驱动参数

例如，驱动参数分别为a、b、c，驱动条件为a等于1，b小于等于2，c不等于3，那么表达式可以写为

`#`H['a'] == '1' && `#`H['b'] <= '2' && `#`H['c'] != '3'

其中，`#`H['a']，Spring Spel表达式用来表述驱动参数a的专有格式

![](http://nepxion.gitee.io/docs/icon-doc/tip.png) 小贴士

H的含义：H为Http首字母，即取值Http类型的参数，包括Header、Parameter、Cookie

③ Spring Spel的逻辑表达，需要注意

- 任何值都大于null。当某个参数未传值，但又指定了该参数大于的表达逻辑，那么表达式结果为false。例如，#H['a'] > '2'，但a未传递进来，a即null，则null > 2，表达式结果为false
- null满足不等于。当某个参数未传值，但又指定了该该参数不等于的表达逻辑，那么表达式结果为true。例如，#H['a'] != '2'，但a未传递进来，a即null，则null != 2，表达式结果为true

④ Spring Spel的符号转义，对XML格式的规则策略文件，保存在配置中心的时候，需要对表达式中的特殊符号进行转义

| 符号 | 转义符 | 含义 | 备注 |
| --- | --- | --- | --- |
| `&` | `&amp;` | 和符号 | 必须转义 |
| `<` | `&lt;` | 小于号 | 必须转义 |
| `"` | `&quot;` | 双引号 | 必须转义 |
| `>` | `&gt;` | 大于号 | |
| `'` | `&apos;` | 单引号 | |

对于上面示例，表达式必须改成如下

`#`H['a'] == '1' `&amp;&amp;` `#`H['b'] `&lt;`= '2' `&amp;&amp;` `#`H['c'] != '3'

![](http://nepxion.gitee.io/docs/icon-doc/information.png) 规则策略配置

增加Spring Cloud Gateway的版本条件匹配蓝绿发布策略，Group为discovery-guide-group，Data Id为discovery-guide-gateway，策略内容如下
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <!-- 基于Http Header传递的策略路由，全局缺省路由（第三优先级） -->
    <strategy>
        <version>{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.0"}</version>
    </strategy>
    
    <!-- 基于Http Header传递的定制化策略路由，支持蓝绿部署和灰度发布两种模式。如果都不命中，则执行上面的全局缺省路由 -->
    <strategy-customization>
        <!-- 全链路蓝绿部署：条件命中的匹配方式（第一优先级），支持版本匹配、区域匹配、IP地址和端口匹配、版本权重匹配、区域权重匹配 -->
        <!-- Expression节点不允许缺失 -->
        <conditions type="blue-green">
            <condition id="blue-condition" expression="#H['a'] == '1'" version-id="blue-version-route"/>
            <condition id="green-condition" expression="#H['a'] == '1' &amp;&amp; #H['b'] == '2'" version-id="green-version-route"/>
        </conditions>

        <routes>
            <route id="blue-version-route" type="version">{"discovery-guide-service-a":"1.1", "discovery-guide-service-b":"1.1"}</route>	
            <route id="green-version-route" type="version">{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.0"}</route>
        </routes>
    </strategy-customization>
</rule>
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide2-8.jpg)

![](http://nepxion.gitee.io/docs/icon-doc/information.png) 规则策略解释

![](http://nepxion.gitee.io/docs/icon-doc/tip.png) 特别提醒

> 为准确体现相关变量（例如上文中的 **a** ）支持Header、Parameter、Cookie中的任意一个，在6.6.0版本之前，表达式格式为 **header="#H['a'] == '1'"** ，从6.6.0版本开始，表达式格式改为 **expression="#H['a'] == '1'"** ，但也兼容 **header="#H['a'] == '1'"** ，即从6.6.0版本开始，用 **expression=""** 和 **header=""** 都支持

① 当外部调用带有的Header/Parameter/Cookies中的值a=1同时b=2，执行绿路由

`<condition>`节点中 **expression="#H['a'] == '1' &amp;&amp; #H['b'] == '2'"** 对应的 **version-id="green-version-route"** ，找到下面`<route>`节点中 **id="green-version-route" type="version"** 的那项，那么路由即为
```
{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.0"}
```

② 当外部调用带有的Header/Parameter/Cookies中的值a=1，执行蓝路由

`<condition>`节点中 **expression="#H['a'] == '1'"** 对应的 **version-id="blue-version-route"** ，找到下面`<route>`节点中 **id="blue-version-route" type="version"** 的那项，那么路由即为
```
{"discovery-guide-service-a":"1.1", "discovery-guide-service-b":"1.1"}
```

③ 当外部调用带有的Header/Parameter/Cookies中的值都不命中，或者未传值，执行兜底路由

- 执行`<strategy>`节点中的全局缺省路由，那么路由即为

```
{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.0"}
```

- 如果全局缺省路由未配置，则执行Spring Cloud Ribbon轮询策略
   
④ 假如不愿意从网关外部传入Header/Parameter/Cookies，那么支持策略下内置Header来决策蓝绿发布，可以代替外部传入Header/Parameter/Cookies，参考如下配置
```xml
<headers>
   <header key="a" value="1"/>
</headers>
```
内置Header一般使用场景为定时Job的服务定时去调用其它服务，希望实施蓝绿灰度发布。当服务侧配置了内置Header，而网关也传递给对应Header给该服务，通过开关来决定，网关传递的Header为优先还是服务侧内置的Header优先

![](http://nepxion.gitee.io/docs/icon-doc/warning.png) 需要注意，Spring Cloud Gateway在Finchley版不支持该方式

⑤ 路由类型支持如下

- 蓝 | 绿 | 兜底，即上述提到的路由场景
- 蓝 | 兜底，即绿路由缺省，那么兜底路由则为绿路由，逻辑更加简单的路由场景
- 如果蓝路由和路由都缺省，那就只有兜底路由（全局缺省路由），即为[全链路版本匹配蓝绿发布](#全链路版本匹配蓝绿发布)的路由场景

⑥ 策略总共支持5种，可以单独一项使用，也可以多项叠加使用

- version 版本
- region 区域
- address IP地址和端口
- version-weight 版本权重
- region-weight 区域权重

⑦ 策略支持Spring Spel的条件表达式方式

⑧ 策略支持Spring Matcher的通配方式

![](http://nepxion.gitee.io/docs/icon-doc/information.png) 上述方式，可以通过[全链路蓝绿发布编排建模](#全链路蓝绿发布编排建模)方式执行，并通过[全链路蓝绿发布流量侦测](#全链路蓝绿发布流量侦测)进行验证

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop10.jpg)

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop15.jpg)

#### 全链路区域条件匹配蓝绿发布
参考[全链路版本条件匹配蓝绿发布](#全链路版本条件匹配蓝绿发布)

用法相似，只需要把规则策略中
- 属性`version-id`替换成`region-id`
- 属性`type="version"`替换成`type="region"`
- 节点`route`对应的Json中版本替换成区域

#### 全链路IP地址和端口条件匹配蓝绿发布
参考[全链路版本条件匹配蓝绿发布](#全链路版本条件匹配蓝绿发布)

用法相似，只需要把规则策略中
- 属性`version-id`替换成`address-id`
- 属性`type="version"`替换成`type="address"`
- 节点`route`对应的Json中版本替换成IP地址和端口

### 全链路灰度发布
> 经典场景：当调用请求从网关或者服务发起的时候，在路由过滤中，根据在配置中心配置的随机权重值，执行权重算法，选择灰度路由 | 稳定路由的规则策略（Json格式），并把命中的规则策略转化为策略路由Header（n-d-开头），实现全链路传递。每个端到端服务接收到策略路由Header后，执行负载均衡时，该Header跟注册中心的对应元数据进行相关比较，不符合条件的实例进行过滤，从而实现全链路灰度发布

> 实施概要：只涉及当前正在发布的服务，例如，对于 〔网关〕->〔A服务〕->〔B服务〕->〔C服务〕->〔D服务〕调用链来说，如果当前只是B服务和C服务正在实施发布，那么，只需要把B服务和C服务配置到规则策略中，其它则不需要配置。发布结束后，即B服务和C服务的所有实例都完全一致，例如，版本号都只有唯一一个，那么清除掉在配置中心配置的规则策略即可，从而进行下一轮全链路灰度发布

![](http://nepxion.gitee.io/docs/discovery-doc/Gray.jpg)

#### 全链路版本权重灰度发布
增加Spring Cloud Gateway的版本权重灰度发布策略，Group为discovery-guide-group，Data Id为discovery-guide-gateway，策略内容如下，实现从Spring Cloud Gateway发起的调用全链路1.0版本流量权重为90%，1.1版本流量权重为10%
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version-weight>1.0=90;1.1=10</version-weight>
    </strategy>
</rule>
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide2-2.jpg)

如果希望每个服务的版本权重分别指定，那么策略内容如下，实现从Spring Cloud Gateway发起的调用a服务1.0版本流量权重为90%，1.1版本流量权重为10%，b服务1.0版本流量权重为80%，1.1版本流量权重为20%
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version-weight>{"discovery-guide-service-a":"1.0=90;1.1=10", "discovery-guide-service-b":"1.0=80;1.1=20"}</version-weight>
    </strategy>
</rule>
```

当所有服务都选相同版本流量权重分配的时候，下面第1条和第2条是等效的
```
1. <version-weight>1.0=90;1.1=10</version-weight>
2. <version-weight>{"discovery-guide-service-a":"1.0=90;1.1=10", "discovery-guide-service-b":"1.0=90;1.1=10"}</version-weight>
```

![](http://nepxion.gitee.io/docs/icon-doc/tip.png) 提醒：非条件驱动下的全链路灰度发布跟Header驱动下的全链路灰度发布等效，例如
```
n-d-version-weight=1.0=90;1.1=10
n-d-version-weight={"discovery-guide-service-a":"1.0=90;1.1=10", "discovery-guide-service-b":"1.0=90;1.1=10"}
```

#### 全链路区域权重灰度发布
增加Zuul的区域权重灰度发布策略，Group为discovery-guide-group，Data Id为discovery-guide-zuul，策略内容如下，实现从Zuul发起的调用全链路dev区域流量权重为85%，qa区域流量权重为15%
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <region-weight>dev=85;qa=15</region-weight>
    </strategy>
</rule>
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide2-4.jpg)

如果希望每个服务的区域权重分别指定，那么策略内容如下，实现从Zuul发起的调用a服务dev区域流量权重为85%，qa区域流量权重为15%，b服务dev区域流量权重为75%，qa区域流量权重为25%
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <region-weight>{"discovery-guide-service-a":"dev=85;qa=15", "discovery-guide-service-b":"dev=75;qa=25"}</region-weight>
    </strategy>
</rule>
```

当所有服务都选相同区域流量权重分配的时候，下面第1条和第2条是等效的
```
1. <region-weight>dev=85;qa=15</region-weight>
2. <region-weight>{"discovery-guide-service-a":"dev=85;qa=15", "discovery-guide-service-b":"dev=85;qa=15"}</region-weight>
```

![](http://nepxion.gitee.io/docs/icon-doc/tip.png) 提醒：非条件驱动下的全链路灰度发布跟Header驱动下的全链路灰度发布等效，例如
```
n-d-region-weight=dev=85;qa=15
n-d-region-weight={"discovery-guide-service-a":"dev=85;qa=15", "discovery-guide-service-b":"dev=85;qa=15"}
```

### 全链路条件灰度发布

#### 全链路版本条件权重灰度发布
![](http://nepxion.gitee.io/docs/icon-doc/information.png) 规则策略配置

增加Zuul的版本条件权重灰度发布策略，Group为discovery-guide-group，Data Id为discovery-guide-zuul，策略内容如下
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <!-- 基于Http Header传递的策略路由，全局缺省路由（第三优先级） -->
    <strategy>
        <version>{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.0"}</version>
    </strategy>

    <!-- 基于Http Header传递的定制化策略路由，支持蓝绿部署和灰度发布两种模式。如果都不命中，则执行上面的全局缺省路由 -->
    <strategy-customization>
        <!-- 全链路灰度发布：条件命中的随机权重（第二优先级），支持版本匹配、区域匹配、IP地址和端口匹配 -->
        <!-- Expression节点允许缺失，当含Expression和未含Expression的配置并存时，以未含Expression的配置为优先 -->
        <conditions type="gray">
            <!-- <condition id="gray-condition" expression="#H['a'] == '1'" version-id="gray-version-route=10;stable-version-route=90"/> -->
            <!-- <condition id="gray-condition" expression="#H['a'] == '1' &amp;&amp; #H['b'] == '2'" version-id="gray-version-route=85;stable-version-route=15"/> -->
            <condition id="gray-condition" version-id="gray-version-route=95;stable-version-route=5"/>
        </conditions>

        <routes>
            <route id="gray-version-route" type="version">{"discovery-guide-service-a":"1.1", "discovery-guide-service-b":"1.1"}</route>
            <route id="stable-version-route" type="version">{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.0"}</route>
        </routes>
    </strategy-customization>
</rule>
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide2-9.jpg)

![](http://nepxion.gitee.io/docs/icon-doc/information.png) 规则策略解释

网关随机权重调用服务，服务链路按照版本匹配方式调用

① 稳定版本路由和灰度版本路由流量权重分配

- 稳定版本路由：a服务1.0版本向网关提供90%的流量，a服务1.0版本只能访问b服务1.0版本
- 灰度版本路由：a服务1.1版本向网关提供10%的流量，a服务1.1版本只能访问b服务1.1版本

② gray-version-route链路配比10%的流量，stable-version-route链路配比90%的流量

③ 策略总共支持3种，可以单独一项使用，也可以多项叠加使用

- version 版本
- region 区域
- address IP地址和端口

![](http://nepxion.gitee.io/docs/icon-doc/tip.png) 提醒：条件权重灰度发布支持参数驱动，但建议使用无参方式，同时兜底路由（全局缺省路由）也不需要

![](http://nepxion.gitee.io/docs/icon-doc/information.png) 上述方式，可以通过[全链路灰度发布编排建模](#全链路灰度发布编排建模)方式执行，并通过[全链路灰度发布流量侦测](#全链路灰度发布流量侦测)进行验证

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop14.jpg)

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop17.jpg)

#### 全链路区域条件权重灰度发布
参考[全链路版本条件权重灰度发布](#全链路版本条件权重灰度发布)

用法相似，只需要把规则策略中
- 属性`version-id`替换成`region-id`
- 属性`type="version"`替换成`type="region"`
- 节点`route`对应的Json中版本替换成区域

#### 全链路IP地址和端口条件权重灰度发布
参考[全链路版本条件权重灰度发布](#全链路版本条件权重灰度发布)

用法相似，只需要把规则策略中
- 属性`version-id`替换成`address-id`
- 属性`type="version"`替换成`type="address"`
- 节点`route`对应的Json中版本替换成IP地址和端口

### 全链路端到端混合实施蓝绿灰度发布

#### 全链路端到端实施蓝绿灰度发布
前端 -> 网关 -> 服务全链路调用中，可以实施端到端蓝绿灰度发布

① 前端 -> 网关并行实施蓝绿灰度发布

当外界传值Header的时候，网关也设置并传递同名的Header，需要决定哪个Header传递到后边的服务去。需要通过如下开关做控制
```
# 当外界传值Header的时候，网关也设置并传递同名的Header，需要决定哪个Header传递到后边的服务去。如果下面开关为true，以网关设置为优先，否则以外界传值为优先。缺失则默认为true
spring.application.strategy.gateway.header.priority=false
# 当以网关设置为优先的时候，网关未配置Header，而外界配置了Header，仍旧忽略外界的Header。缺失则默认为true
spring.application.strategy.gateway.original.header.ignored=true

# 当外界传值Header的时候，网关也设置并传递同名的Header，需要决定哪个Header传递到后边的服务去。如果下面开关为true，以网关设置为优先，否则以外界传值为优先。缺失则默认为true
spring.application.strategy.zuul.header.priority=false
# 当以网关设置为优先的时候，网关未配置Header，而外界配置了Header，仍旧忽略外界的Header。缺失则默认为true
spring.application.strategy.zuul.original.header.ignored=true
```

② 网关 -> 服务并行实施蓝绿灰度发布

当网关传值Header的时候，服务也设置并传递同名的Header，需要决定哪个Header传递到后边的服务去。需要通过如下开关做控制
```
# 当外界传值Header的时候，服务也设置并传递同名的Header，需要决定哪个Header传递到后边的服务去，该开关依赖前置过滤器的开关。如果下面开关为true，以服务设置为优先，否则以外界传值为优先。缺失则默认为true
# spring.application.strategy.service.header.priority=true
```

#### 全链路混合实施蓝绿灰度发布
网关 -> 服务全链路调用中，可以混合实施蓝绿灰度发布

① 网关上实施蓝绿发布，服务上实施灰度发布

② 网关上实施灰度发布，服务上实施蓝绿发布

上述两个发布场景，可以独立实施，互不影响，前提条件，需要控制服务上`header.priority`的开关

### 全链路域网关和非域网关部署

#### 全链路域网关部署
A部门服务访问B部门服务必须通过B部门网关

该部署模式下，本部门服务的蓝绿灰度发布只由本部门的网关来实施，其它部门无权对本部门服务实施蓝绿灰度发布，前提条件，需要控制网关上`header.priority`的开关

#### 全链路非域网关部署
A部门服务直接访问B部门服务

该部署模式下，会发生本部门服务的蓝绿灰度发布会由其它部门的网关或者服务来触发，当本部门服务和其它部门服务在同一时刻实施蓝绿灰度发布的时候，会产生混乱。解决方案，参考[并行发布下的版本偏好](#并行发布下的版本偏好)

### 全链路前端触发后端蓝绿灰度发布

前端可以直接触发后端蓝绿灰度发布，前提条件，需要控制网关和服务上`header.priority`的开关

#### 全链路驱动方式
- 版本匹配策略，Header格式如下任选一个

```
1. n-d-version=1.0
2. n-d-version={"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.0"}
```

- 版本权重策略，Header格式如下任选一个

```
1. n-d-version-weight=1.0=90;1.1=10
2. n-d-version-weight={"discovery-guide-service-a":"1.0=90;1.1=10", "discovery-guide-service-b":"1.0=90;1.1=10"}
```

- 区域匹配策略，Header格式如下任选一个

```
1. n-d-region=qa
2. n-d-region={"discovery-guide-service-a":"qa", "discovery-guide-service-b":"qa"}
```

- 区域权重策略，Header格式如下任选一个

```
1. n-d-region-weight=dev=99;qa=1
2. n-d-region-weight={"discovery-guide-service-a":"dev=99;qa=1", "discovery-guide-service-b":"dev=99;qa=1"}
```

- IP地址和端口匹配策略，Header格式如下任选一个

```
1. n-d-address={"discovery-guide-service-a":"127.0.0.1:3001", "discovery-guide-service-b":"127.0.0.1:4002"}
2. n-d-address={"discovery-guide-service-a":"127.0.0.1", "discovery-guide-service-b":"127.0.0.1"}
3. n-d-address={"discovery-guide-service-a":"3001", "discovery-guide-service-b":"4002"}
```

- 环境隔离下动态环境匹配策略

```
1. n-d-env=env1
```

- 服务下线实时性的流量绝对无损，全局唯一ID屏蔽策略

```
1. n-d-id-blacklist=e92edde5-0153-4ec8-9cbb-b4d3f415aa33;af043384-c8a5-451e-88f4-457914e8e3bc
```

- 服务下线实时性的流量绝对无损，IP地址和端口屏蔽策略

```
1. n-d-address-blacklist=192.168.43.101:1201;192.168.*.102;1301
```

![](http://nepxion.gitee.io/docs/icon-doc/tip.png) 全链路前端触发后端蓝绿灰度发布全景功能

![](http://nepxion.gitee.io/docs/discovery-doc/Introduction.jpg)

#### 全链路参数策略
① Header参数策略

基于标准Http传值方式

框架会默认把相关的Header，进行全链路传递，可以通过如下配置进行。除此之外，凡是以n-d-开头的任何Header，框架都会默认全链路传递
```
# 启动和关闭路由策略的时候，对REST方式的调用拦截。缺失则默认为true
spring.application.strategy.rest.intercept.enabled=true
# 启动和关闭Header传递的Debug日志打印，注意：每调用一次都会打印一次，会对性能有所影响，建议压测环境和生产环境关闭。缺失则默认为false
spring.application.strategy.rest.intercept.debug.enabled=true
# 路由策略的时候，对REST方式调用拦截的时候（支持Feign或者RestTemplate调用），希望把来自外部自定义的Header参数（用于框架内置上下文Header，例如：trace-id, span-id等）传递到服务里，那么配置如下值。如果多个用“;”分隔，不允许出现空格
spring.application.strategy.context.request.headers=trace-id;span-id
# 路由策略的时候，对REST方式调用拦截的时候（支持Feign或者RestTemplate调用），希望把来自外部自定义的Header参数（用于业务系统自定义Header，例如：mobile）传递到服务里，那么配置如下值。如果多个用“;”分隔，不允许出现空格
spring.application.strategy.business.request.headers=user;mobile;location
```

② Parameter参数策略

基于标准Http传值方式

[http://localhost:5001/discovery-guide-service-a/invoke/gateway?a=1](http://localhost:5001/discovery-guide-service-a/invoke/gateway?a=1)

[http://localhost:5001/discovery-guide-service-a/invoke/gateway?a=2](http://localhost:5001/discovery-guide-service-a/invoke/gateway?a=2)

③ Cookie参数策略
基于标准Http传值方式

④ 域名参数策略
基于取值域名前缀等方式，即可实现既定功能

本地测试，为验证结果，请事先在hosts文件中配置如下
```
127.0.0.1 common.nepxion.com
127.0.0.1 env1.nepxion.com
127.0.0.1 env2.nepxion.com
```

- 根据env1.nepxion.com域名路由到env1环境

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide2-15.jpg)

- 根据common.nepxion.com域名路由到common环境

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide2-16.jpg)

参考[全链路自定义过滤器触发蓝绿灰度发布](#全链路自定义过滤器触发蓝绿灰度发布)示例，以根据域名全链路环境隔离为例，根据域名前缀中的环境名路由到相应的全链路环境中

⑤ RPC-Method参数策略

基于取值RPC调用中的方法入参等方式，即可实现既定功能，该方式只适用于服务侧

参考[全链路自定义负载均衡策略类触发蓝绿灰度发布](#全链路自定义负载均衡策略类触发蓝绿灰度发布)示例

### 全局订阅式蓝绿灰度发布
如果使用者不希望通过全链路传递Header实现蓝绿灰度发布，框架提供另外一种规避Header传递的方式，即全局订阅式蓝绿灰度发布，也能达到Header传递一样的效果。以全链路版本匹配蓝绿发布为例

增加版本匹配的蓝绿发布策略，Group为discovery-guide-group，Data Id为discovery-guide-group（全局发布，两者都是组名），策略内容如下，实现a服务走1.0版本，b服务走1.1版本
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version>{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.1"}</version>
    </strategy>
</rule>
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide2-10.jpg)

如果采用上述方式，可以考虑关闭下面的开关
```
# 启动和关闭核心策略Header传递，缺失则默认为true。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能。核心策略Header，包含如下
# 1. n-d-version
# 2. n-d-region
# 3. n-d-address
# 4. n-d-version-weight
# 5. n-d-region-weight
# 6. n-d-id-blacklist
# 7. n-d-address-blacklist
# 8. n-d-env (不属于蓝绿灰度范畴的Header，只要外部传入就会全程传递)
spring.application.strategy.gateway.core.header.transmission.enabled=true
spring.application.strategy.zuul.core.header.transmission.enabled=true
spring.application.strategy.feign.core.header.transmission.enabled=true
spring.application.strategy.rest.template.core.header.transmission.enabled=true
```

### 全链路自定义蓝绿灰度发布

#### 全链路自定义过滤器触发蓝绿灰度发布
下面代码既适用于Zuul和Spring Cloud Gateway网关，也适用于微服务。继承DefaultGatewayStrategyRouteFilter、DefaultZuulStrategyRouteFilter和DefaultServiceStrategyRouteFilter，覆盖掉如下方法中的一个或者多个，通过@Bean方式覆盖框架内置的过滤类
```java
public String getRouteVersion();

public String getRouteRegion();

public String getRouteEnvironment();

public String getRouteAddress();

public String getRouteVersionWeight();

public String getRouteRegionWeight();

public String getRouteIdBlacklist();

public String getRouteAddressBlacklist();
```

GatewayStrategyRouteFilter示例
```java
// 适用于A/B Testing或者更根据某业务参数决定蓝绿灰度路由路径。可以结合配置中心分别配置A/B两条路径，可以动态改变并通知
// 当Header中传来的用户为张三，执行一条路由路径；为李四，执行另一条路由路径
public class MyGatewayStrategyRouteFilter extends DefaultGatewayStrategyRouteFilter {
    private static final Logger LOG = LoggerFactory.getLogger(MyGatewayStrategyRouteFilter.class);

    private static final String DEFAULT_A_ROUTE_VERSION = "{\"discovery-guide-service-a\":\"1.0\", \"discovery-guide-service-b\":\"1.1\"}";
    private static final String DEFAULT_B_ROUTE_VERSION = "{\"discovery-guide-service-a\":\"1.1\", \"discovery-guide-service-b\":\"1.0\"}";
    private static final String DEFAULT_A_ROUTE_REGION = "{\"discovery-guide-service-a\":\"dev\", \"discovery-guide-service-b\":\"qa\"}";
    private static final String DEFAULT_B_ROUTE_REGION = "{\"discovery-guide-service-a\":\"qa\", \"discovery-guide-service-b\":\"dev\"}";
    private static final String DEFAULT_A_ROUTE_ADDRESS = "{\"discovery-guide-service-a\":\"3001\", \"discovery-guide-service-b\":\"4002\"}";
    private static final String DEFAULT_B_ROUTE_ADDRESS = "{\"discovery-guide-service-a\":\"3002\", \"discovery-guide-service-b\":\"4001\"}";

    @Value("${a.route.version:" + DEFAULT_A_ROUTE_VERSION + "}")
    private String aRouteVersion;

    @Value("${b.route.version:" + DEFAULT_B_ROUTE_VERSION + "}")
    private String bRouteVersion;

    @Value("${a.route.region:" + DEFAULT_A_ROUTE_REGION + "}")
    private String aRouteRegion;

    @Value("${b.route.region:" + DEFAULT_B_ROUTE_REGION + "}")
    private String bRouteRegion;

    @Value("${a.route.address:" + DEFAULT_A_ROUTE_ADDRESS + "}")
    private String aRouteAddress;

    @Value("${b.route.address:" + DEFAULT_B_ROUTE_ADDRESS + "}")
    private String bRouteAddress;

    // 自定义根据Header全链路版本匹配路由
    @Override
    public String getRouteVersion() {
        String user = strategyContextHolder.getHeader("user");

        LOG.info("自定义根据Header全链路版本匹配路由, Header user={}", user);

        if (StringUtils.equals(user, "zhangsan")) {
            LOG.info("执行全链路版本匹配路由={}", aRouteVersion);

            return aRouteVersion;
        } else if (StringUtils.equals(user, "lisi")) {
            LOG.info("执行全链路版本匹配路由={}", bRouteVersion);

            return bRouteVersion;
        }

        return super.getRouteVersion();
    }

    // 自定义根据Parameter全链路区域匹配路由
    @Override
    public String getRouteRegion() {
        String user = strategyContextHolder.getParameter("user");

        LOG.info("自定义根据Parameter全链路区域匹配路由, Parameter user={}", user);

        if (StringUtils.equals(user, "zhangsan")) {
            LOG.info("执行全链路区域匹配路由={}", aRouteRegion);

            return aRouteRegion;
        } else if (StringUtils.equals(user, "lisi")) {
            LOG.info("执行全链路区域匹配路由={}", bRouteRegion);

            return bRouteRegion;
        }

        return super.getRouteRegion();
    }

    // 自定义根据Cookie全链路IP地址和端口匹配路由
    @Override
    public String getRouteAddress() {
        String user = strategyContextHolder.getCookie("user");

        LOG.info("自定义根据Cookie全链路IP地址和端口匹配路由, Cookie user={}", user);

        if (StringUtils.equals(user, "zhangsan")) {
            LOG.info("执行全链路IP地址和端口匹配路由={}", aRouteAddress);

            return aRouteAddress;
        } else if (StringUtils.equals(user, "lisi")) {
            LOG.info("执行全链路IP地址和端口匹配路由={}", bRouteAddress);

            return bRouteAddress;
        }

        return super.getRouteAddress();
    }

    @Autowired
    private GatewayStrategyContextHolder gatewayStrategyContextHolder;

    // 自定义根据域名全链路环境隔离
    @Override
    public String getRouteEnvironment() {
        String host = gatewayStrategyContextHolder.getURI().getHost();
        if (host.contains("nepxion.com")) {
            LOG.info("自定义根据域名全链路环境隔离, URL={}", host);

            String environment = host.substring(0, host.indexOf("."));

            LOG.info("执行全链路环境隔离={}", environment);

            return environment;
        }

        return super.getRouteEnvironment();
    }

    // 自定义全链路版本权重路由
    /*@Override
    public String getRouteVersion() {
        LOG.info("自定义全链路版本权重路由");

        List<Pair<String, Double>> weightList = new ArrayList<Pair<String, Double>>();
        weightList.add(new ImmutablePair<String, Double>(aRouteVersion, 30D));
        weightList.add(new ImmutablePair<String, Double>(bRouteVersion, 70D));
        MapWeightRandom<String, Double> weightRandom = new MapWeightRandom<String, Double>(weightList);

        return weightRandom.random();
    }*/
}
```
在配置类里@Bean方式进行过滤类创建，覆盖框架内置的过滤类
```java
@Bean
public GatewayStrategyRouteFilter gatewayStrategyRouteFilter() {
    return new MyGatewayStrategyRouteFilter();
}
```

ZuulStrategyRouteFilter示例
```java
// 适用于A/B Testing或者更根据某业务参数决定蓝绿灰度路由路径。可以结合配置中心分别配置A/B两条路径，可以动态改变并通知
// 当Header中传来的用户为张三，执行一条路由路径；为李四，执行另一条路由路径
public class MyZuulStrategyRouteFilter extends DefaultZuulStrategyRouteFilter {
    private static final Logger LOG = LoggerFactory.getLogger(MyZuulStrategyRouteFilter.class);

    private static final String DEFAULT_A_ROUTE_VERSION = "{\"discovery-guide-service-a\":\"1.0\", \"discovery-guide-service-b\":\"1.1\"}";
    private static final String DEFAULT_B_ROUTE_VERSION = "{\"discovery-guide-service-a\":\"1.1\", \"discovery-guide-service-b\":\"1.0\"}";
    private static final String DEFAULT_A_ROUTE_REGION = "{\"discovery-guide-service-a\":\"dev\", \"discovery-guide-service-b\":\"qa\"}";
    private static final String DEFAULT_B_ROUTE_REGION = "{\"discovery-guide-service-a\":\"qa\", \"discovery-guide-service-b\":\"dev\"}";
    private static final String DEFAULT_A_ROUTE_ADDRESS = "{\"discovery-guide-service-a\":\"3001\", \"discovery-guide-service-b\":\"4002\"}";
    private static final String DEFAULT_B_ROUTE_ADDRESS = "{\"discovery-guide-service-a\":\"3002\", \"discovery-guide-service-b\":\"4001\"}";

    @Value("${a.route.version:" + DEFAULT_A_ROUTE_VERSION + "}")
    private String aRouteVersion;

    @Value("${b.route.version:" + DEFAULT_B_ROUTE_VERSION + "}")
    private String bRouteVersion;

    @Value("${a.route.region:" + DEFAULT_A_ROUTE_REGION + "}")
    private String aRouteRegion;

    @Value("${b.route.region:" + DEFAULT_B_ROUTE_REGION + "}")
    private String bRouteRegion;

    @Value("${a.route.address:" + DEFAULT_A_ROUTE_ADDRESS + "}")
    private String aRouteAddress;

    @Value("${b.route.address:" + DEFAULT_B_ROUTE_ADDRESS + "}")
    private String bRouteAddress;

    // 自定义根据Header全链路版本匹配路由
    @Override
    public String getRouteVersion() {
        String user = strategyContextHolder.getHeader("user");

        LOG.info("自定义根据Header全链路版本匹配路由, Header user={}", user);

        if (StringUtils.equals(user, "zhangsan")) {
            LOG.info("执行全链路版本匹配路由={}", aRouteVersion);

            return aRouteVersion;
        } else if (StringUtils.equals(user, "lisi")) {
            LOG.info("执行全链路版本匹配路由={}", bRouteVersion);

            return bRouteVersion;
        }

        return super.getRouteVersion();
    }

    // 自定义根据Parameter全链路区域匹配路由
    @Override
    public String getRouteRegion() {
        String user = strategyContextHolder.getParameter("user");

        LOG.info("自定义根据Parameter全链路区域匹配路由, Parameter user={}", user);

        if (StringUtils.equals(user, "zhangsan")) {
            LOG.info("执行全链路区域匹配路由={}", aRouteRegion);

            return aRouteRegion;
        } else if (StringUtils.equals(user, "lisi")) {
            LOG.info("执行全链路区域匹配路由={}", bRouteRegion);

            return bRouteRegion;
        }

        return super.getRouteRegion();
    }

    // 自定义根据Cookie全链路IP地址和端口匹配路由
    @Override
    public String getRouteAddress() {
        String user = strategyContextHolder.getCookie("user");

        LOG.info("自定义根据Cookie全链路IP地址和端口匹配路由, Cookie user={}", user);

        if (StringUtils.equals(user, "zhangsan")) {
            LOG.info("执行全链路IP地址和端口匹配路由={}", aRouteAddress);

            return aRouteAddress;
        } else if (StringUtils.equals(user, "lisi")) {
            LOG.info("执行全链路IP地址和端口匹配路由={}", bRouteAddress);

            return bRouteAddress;
        }

        return super.getRouteEnvironment();
    }

    @Autowired
    private ZuulStrategyContextHolder zuulStrategyContextHolder;

    // 自定义根据域名全链路环境隔离
    @Override
    public String getRouteEnvironment() {
        String requestURL = zuulStrategyContextHolder.getRequestURL();
        if (requestURL.contains("nepxion.com")) {
            LOG.info("自定义根据域名全链路环境隔离, URL={}", requestURL);

            String host = requestURL.substring("http://".length(), requestURL.length());
            String environment = host.substring(0, host.indexOf("."));

            LOG.info("执行全链路环境隔离={}", environment);

            return environment;
        }

        return super.getRouteEnvironment();
    }

    // 自定义全链路版本权重路由
    /*@Override
    public String getRouteVersion() {
        LOG.info("自定义全链路版本权重路由");

        List<Pair<String, Double>> weightList = new ArrayList<Pair<String, Double>>();
        weightList.add(new ImmutablePair<String, Double>(aRouteVersion, 30D));
        weightList.add(new ImmutablePair<String, Double>(bRouteVersion, 70D));
        MapWeightRandom<String, Double> weightRandom = new MapWeightRandom<String, Double>(weightList);

        return weightRandom.random();
    }*/
}
```
在配置类里@Bean方式进行过滤类创建，覆盖框架内置的过滤类
```java
@Bean
public ZuulStrategyRouteFilter zuulStrategyRouteFilter() {
    return new MyZuulStrategyRouteFilter();
}
```

ServiceStrategyRouteFilter示例
```java
// 适用于A/B Testing或者更根据某业务参数决定蓝绿灰度路由路径。可以结合配置中心分别配置A/B两条路径，可以动态改变并通知
// 当Header中传来的用户为张三，执行一条路由路径；为李四，执行另一条路由路径
public class MyServiceStrategyRouteFilter extends DefaultServiceStrategyRouteFilter {
    private static final Logger LOG = LoggerFactory.getLogger(MyServiceStrategyRouteFilter.class);

    private static final String DEFAULT_A_ROUTE_VERSION = "{\"discovery-guide-service-a\":\"1.0\", \"discovery-guide-service-b\":\"1.1\"}";
    private static final String DEFAULT_B_ROUTE_VERSION = "{\"discovery-guide-service-a\":\"1.1\", \"discovery-guide-service-b\":\"1.0\"}";
    private static final String DEFAULT_A_ROUTE_REGION = "{\"discovery-guide-service-a\":\"dev\", \"discovery-guide-service-b\":\"qa\"}";
    private static final String DEFAULT_B_ROUTE_REGION = "{\"discovery-guide-service-a\":\"qa\", \"discovery-guide-service-b\":\"dev\"}";
    private static final String DEFAULT_A_ROUTE_ADDRESS = "{\"discovery-guide-service-a\":\"3001\", \"discovery-guide-service-b\":\"4002\"}";
    private static final String DEFAULT_B_ROUTE_ADDRESS = "{\"discovery-guide-service-a\":\"3002\", \"discovery-guide-service-b\":\"4001\"}";

    @Value("${a.route.version:" + DEFAULT_A_ROUTE_VERSION + "}")
    private String aRouteVersion;

    @Value("${b.route.version:" + DEFAULT_B_ROUTE_VERSION + "}")
    private String bRouteVersion;

    @Value("${a.route.region:" + DEFAULT_A_ROUTE_REGION + "}")
    private String aRouteRegion;

    @Value("${b.route.region:" + DEFAULT_B_ROUTE_REGION + "}")
    private String bRouteRegion;

    @Value("${a.route.address:" + DEFAULT_A_ROUTE_ADDRESS + "}")
    private String aRouteAddress;

    @Value("${b.route.address:" + DEFAULT_B_ROUTE_ADDRESS + "}")
    private String bRouteAddress;

    // 自定义根据Header全链路版本匹配路由
    @Override
    public String getRouteVersion() {
        String user = strategyContextHolder.getHeader("user");

        LOG.info("自定义根据Header全链路版本匹配路由, Header user={}", user);

        if (StringUtils.equals(user, "zhangsan")) {
            LOG.info("执行全链路版本匹配路由={}", aRouteVersion);

            return aRouteVersion;
        } else if (StringUtils.equals(user, "lisi")) {
            LOG.info("执行全链路版本匹配路由={}", bRouteVersion);

            return bRouteVersion;
        }

        return super.getRouteVersion();
    }

    // 自定义根据Parameter全链路区域匹配路由
    @Override
    public String getRouteRegion() {
        String user = strategyContextHolder.getParameter("user");

        LOG.info("自定义根据Parameter全链路区域匹配路由, Parameter user={}", user);

        if (StringUtils.equals(user, "zhangsan")) {
            LOG.info("执行全链路区域匹配路由={}", aRouteRegion);

            return aRouteRegion;
        } else if (StringUtils.equals(user, "lisi")) {
            LOG.info("执行全链路区域匹配路由={}", bRouteRegion);

            return bRouteRegion;
        }

        return super.getRouteRegion();
    }

    // 自定义根据Cookie全链路IP地址和端口匹配路由
    @Override
    public String getRouteAddress() {
        String user = strategyContextHolder.getCookie("user");

        LOG.info("自定义根据Cookie全链路IP地址和端口匹配路由, Cookie user={}", user);

        if (StringUtils.equals(user, "zhangsan")) {
            LOG.info("执行全链路IP地址和端口匹配路由={}", aRouteAddress);

            return aRouteAddress;
        } else if (StringUtils.equals(user, "lisi")) {
            LOG.info("执行全链路IP地址和端口匹配路由={}", bRouteAddress);

            return bRouteAddress;
        }

        return super.getRouteEnvironment();
    }

    @Autowired
    private ServiceStrategyContextHolder serviceStrategyContextHolder;

    // 自定义根据域名全链路环境隔离
    @Override
    public String getRouteEnvironment() {
        String requestURL = serviceStrategyContextHolder.getRequestURL();
        if (requestURL.contains("nepxion.com")) {
            LOG.info("自定义根据域名全链路环境隔离, URL={}", requestURL);

            String host = requestURL.substring("http://".length(), requestURL.length());
            String environment = host.substring(0, host.indexOf("."));

            LOG.info("执行全链路环境隔离={}", environment);

            return environment;
        }

        return super.getRouteEnvironment();
    }

    // 自定义全链路版本权重路由
    /*@Override
    public String getRouteVersion() {
        LOG.info("自定义全链路版本权重路由");

        List<Pair<String, Double>> weightList = new ArrayList<Pair<String, Double>>();
        weightList.add(new ImmutablePair<String, Double>(aRouteVersion, 30D));
        weightList.add(new ImmutablePair<String, Double>(bRouteVersion, 70D));
        MapWeightRandom<String, Double> weightRandom = new MapWeightRandom<String, Double>(weightList);

        return weightRandom.random();
    }*/
}
```
在配置类里@Bean方式进行过滤类创建，覆盖框架内置的过滤类
```java
@Bean
public ServiceStrategyRouteFilter serviceStrategyRouteFilter() {
    return new MyServiceStrategyRouteFilter();
}
```

#### 全链路自定义负载均衡策略类触发蓝绿灰度发布
下面代码既适用于Zuul和Spring Cloud Gateway网关，也适用于微服务。继承DefaultDiscoveryEnabledStrategy，可以有多个，通过@Bean方式注入
```java
// 实现了组合策略，版本路由策略+区域路由策略+IP地址和端口路由策略+自定义策略
public class MyDiscoveryEnabledStrategy extends DefaultDiscoveryEnabledStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(MyDiscoveryEnabledStrategy.class);

    // 对REST调用传来的Header参数（例如：mobile）做策略
    @Override
    public boolean apply(Server server) {
        String mobile = strategyContextHolder.getHeader("mobile");
        String serviceId = pluginAdapter.getServerServiceId(server);
        String version = pluginAdapter.getServerVersion(server);
        String region = pluginAdapter.getServerRegion(server);
        String environment = pluginAdapter.getServerEnvironment(server);
        String address = server.getHost() + ":" + server.getPort();

        LOG.info("负载均衡用户定制触发：mobile={}, serviceId={}, version={}, region={}, env={}, address={}", mobile, serviceId, version, region, environment, address);

        if (StringUtils.isNotEmpty(mobile)) {
            // 手机号以移动138开头，路由到1.0版本的服务上
            if (mobile.startsWith("138") && StringUtils.equals(version, "1.0")) {
                return true;
                // 手机号以联通133开头，路由到2.0版本的服务上
            } else if (mobile.startsWith("133") && StringUtils.equals(version, "1.1")) {
                return true;
            } else {
                // 其它情况，直接拒绝请求
                return false;
            }
        }

        return true;
    }
}
```
在配置类里@Bean方式进行策略类创建
```java
@Bean
public DiscoveryEnabledStrategy discoveryEnabledStrategy() {
    return new MyDiscoveryEnabledStrategy();
}
```
服务除了支持网关那种基于Rest参数的方式之外，还支持基于Rpc方法参数的方式，它包括接口名、方法名、参数名或参数值等多种形式
```java
// 实现了组合策略，版本路由策略+区域路由策略+IP地址和端口路由策略+自定义策略
public class MyDiscoveryEnabledStrategy implements DiscoveryEnabledStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(MyDiscoveryEnabledStrategy.class);

    @Autowired
    private PluginAdapter pluginAdapter;

    @Autowired
    private ServiceStrategyContextHolder serviceStrategyContextHolder;

    @Override
    public boolean apply(Server server) {
        boolean enabled = applyFromHeader(server);
        if (!enabled) {
            return false;
        }

        return applyFromMethod(server);
    }

    // 根据REST调用传来的Header参数（例如：mobile），选取执行调用请求的服务实例
    private boolean applyFromHeader(Server server) {
        String mobile = serviceStrategyContextHolder.getHeader("mobile");
        String serviceId = pluginAdapter.getServerServiceId(server);
        String version = pluginAdapter.getServerVersion(server);
        String region = pluginAdapter.getServerRegion(server);
        String environment = pluginAdapter.getServerEnvironment(server);
        String address = server.getHost() + ":" + server.getPort();

        LOG.info("负载均衡用户定制触发：mobile={}, serviceId={}, version={}, region={}, env={}, address={}", mobile, serviceId, version, region, environment, address);

        if (StringUtils.isNotEmpty(mobile)) {
            // 手机号以移动138开头，路由到1.0版本的服务上
            if (mobile.startsWith("138") && StringUtils.equals(version, "1.0")) {
                return true;
                // 手机号以联通133开头，路由到2.0版本的服务上
            } else if (mobile.startsWith("133") && StringUtils.equals(version, "1.1")) {
                return true;
            } else {
                // 其它情况，直接拒绝请求
                return false;
            }
        }

        return true;
    }

    // 根据RPC调用传来的方法参数（例如接口名、方法名、参数名或参数值等），选取执行调用请求的服务实例
    // 本示例只作用在discovery-guide-service-a服务上
    @SuppressWarnings("unchecked")
    private boolean applyFromMethod(Server server) {
        Map<String, Object> attributes = serviceStrategyContextHolder.getRpcAttributes();
        String serviceId = pluginAdapter.getServerServiceId(server);
        String version = pluginAdapter.getServerVersion(server);
        String region = pluginAdapter.getServerRegion(server);
        String environment = pluginAdapter.getServerEnvironment(server);
        String address = server.getHost() + ":" + server.getPort();

        LOG.info("负载均衡用户定制触发：attributes={}, serviceId={}, version={}, region={}, env={}, address={}", attributes, serviceId, version, region, environment, address);

        if (attributes.containsKey(DiscoveryConstant.PARAMETER_MAP)) {
            Map<String, Object> parameterMap = (Map<String, Object>) attributes.get(DiscoveryConstant.PARAMETER_MAP);
            String value = parameterMap.get("value").toString();
            if (StringUtils.isNotEmpty(value)) {
                // 输入值包含dev，路由到dev区域的服务上
                if (value.contains("dev") && StringUtils.equals(region, "dev")) {
                    return true;
                    // 输入值包含qa，路由到qa区域的服务上
                } else if (value.contains("qa") && StringUtils.equals(region, "qa")) {
                    return true;
                } else {
                    // 其它情况，直接通过请求
                    return true;
                }
            }
        }

        return true;
    }
}
```
需要通过如下开关开启该功能
```
# 启动和关闭路由策略的时候，对RPC方式的调用拦截。缺失则默认为false
spring.application.strategy.rpc.intercept.enabled=true
```

### 全链路动态变更元数据的蓝绿灰度发布
利用注册中心的Open API接口动态变更服务实例的元数据，达到稳定版本和灰度版本流量灰度控制的目的

以Nacos注册中心的版本匹配路由为例

老的稳定版本的服务实例配置版本元数据，如下
```
spring.cloud.nacos.discovery.metadata.version=stable
```
新的稳定版本的服务实例配置版本元数据，如下
```
spring.cloud.nacos.discovery.metadata.version=gray
```

路由策略，如下

表示所有的服务流量走灰度版本
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version>gray</version>
    </strategy>
</rule>
```
表示a服务流量走灰度版本，b服务流量走稳定版本
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version>{"discovery-guide-service-a":"gray", "discovery-guide-service-b":"stable"}</version>
    </strategy>
</rule>
```
也可以通过全链路传递Header方式实现
```
n-d-version=gray
n-d-version={"discovery-guide-service-a":"gray", "discovery-guide-service-b":"stable"}
```

新上线的服务实例版本为gray，即默认是灰度版本。等灰度成功后，通过注册中心的Open API接口变更服务版本为stable，或者在注册中心界面手工修改

- Nacos Open API变更元数据

```
curl -X PUT 'http://ip:port/nacos/v1/ns/instance?serviceName={appId}&ip={ip}&port={port}&metadata={"version", "stable"}'
```
Nacos Open API使用手册，参考[https://nacos.io/zh-cn/docs/open-api.html](https://nacos.io/zh-cn/docs/open-api.html)

- Eureka Open API变更元数据

```
curl -X PUT 'http://ip:port/eureka/apps/{appId}/{instanceId}/metadata?version=stable'
```

- Consul Open API变更元数据

自行研究

- Zookeeper Open API变更元数据

自行研究

![](http://nepxion.gitee.io/docs/icon-doc/warning.png) 需要注意

① 并非所有的注册中心都支持动态元数据变更方式，需要使用者自行研究

② 动态元数据变更方式利用第三方注册中心的Open API达到最终目的，其可能具有一定的延迟性，不如本框架那样具有蓝绿灰度发布实时生效的特征，但比本框架动态变更蓝绿灰度发布简单了一些

③ 动态元数据变更方式只是让新的元数据驻留在内存里，并不持久化。当服务重启后，服务的元数据仍旧会以初始值为准

## 全链路蓝绿灰度发布编排建模和流量侦测

① 获取图形化桌面端

桌面端获取方式有两种方式
- 通过[https://github.com/Nepxion/DiscoveryUI/releases](https://github.com/Nepxion/DiscoveryUI/releases)下载最新版本的discovery-desktop-release
- 编译[https://github.com/Nepxion/DiscoveryUI](https://github.com/Nepxion/DiscoveryUI)下的desktop，在target目录下产生discovery-desktop-release

② 启动控制台
- 通过[https://github.com/Nepxion/DiscoveryPlatform](https://github.com/Nepxion/DiscoveryPlatform)下载最新版本的控制台
- 导入IDE或者编译成Spring Boot程序运行
- 运行之前，先修改src/main/resources/bootstrap.properties的相关配置，包括注册中心和配置中心的地址等

③ 启动图形化桌面端
- 修改config/console.properties中的url，指向控制台的地址
- 在Windows操作系统下，运行startup.bat，在Mac或者Linux操作系统下，运行startup.sh

④ 登录图形化桌面端

登录认证，用户名和密码为admin/admin或者nepxion/nepxion。控制台支持简单的认证，用户名和密码配置在上述控制台的bootstrap.properties中，使用者可以自己扩展AuthenticationResource并注入，实现更专业的认证功能

 ![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop8.jpg)

### 全链路编排建模

全链路编排建模工具，只提供最经典和最常用的蓝绿灰度发布场景功能，并不覆盖框架所有的功能

#### 全链路蓝绿发布编排建模

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop9.jpg)

① 导航栏上选择〔全链路服务蓝绿发布〕

② 〔全链路服务蓝绿发布〕界面的工具栏上，点击【新建】按钮，弹出【新建配置】对话框。确认下面选项后，点击【确定】按钮后，进行全链路蓝绿发布编排建模

- 〔订阅参数〕项。选择〔局部订阅〕或者〔全局订阅〕，通过下拉菜单〔订阅组名〕和〔订阅服务名〕，〔订阅服务名〕可以选择网关（以网关为发布入口）或者服务（以服务为发布入口）。如果是〔全局订阅〕，则不需要选择〔订阅服务名〕
- 〔部署参数〕项。选择〔域网关模式〕（发布界面上提供只属于〔订阅组〕下的服务列表）或者〔非域网模式〕（发布界面上提供所有服务列表）
- 〔发布策略〕项。选择〔版本策略〕或者〔区域策略〕
- 〔路由类型〕项。选择〔蓝 | 绿 | 兜底〕或者〔蓝 | 兜底〕

根据[全链路版本条件匹配蓝绿发布](#全链路版本条件匹配蓝绿发布)示例中的场景

③ 在〔蓝绿条件〕中，分别输入〔蓝条件〕和〔绿条件〕

- 〔蓝条件〕输入a==1
- 〔绿条件〕输入a==1&&b==2

使用者可以通过〔条件校验〕来判断条件是否正确。例如，在〔绿条件〕区的校验文本框里，输入a=1，执行校验，将提示〔校验结果:false〕，输入a=1;b=2，将提示〔校验结果:true〕

④ 在〔蓝绿编排〕中，分别选择如下服务以及其版本，并点击【添加】按钮，把路由链路添加到拓扑图上

- 服务discovery-guide-service-a，〔蓝版本〕=1.1，〔绿版本〕=1.0，〔兜底版本〕=1.0
- 服务discovery-guide-service-b，〔蓝版本〕=1.1，〔绿版本〕=1.0，〔兜底版本〕=1.0

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop10.jpg)

⑤ 如果希望内置Header参数，可以〔蓝绿参数〕的文本框中输入

⑥ 全链路编排建模完毕，点击工具栏上【保存】按钮进行保存，也可以先点击【预览】按钮，在弹出的【预览配置】对话框中，确认规则策略无误后再保存。使用者可以访问Nacos界面查看相关的规则策略是否已经存在

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop11.jpg)

⑦ 对于已经存在的策略配置，可以通过点击工具栏上【打开】按钮，在弹出的【打开配置】对话框中，根据上述逻辑相似，确定〔订阅参数〕项后，选择〔打开远程配置〕（载入Nacos上对应的规则策略）或者〔打开本地配置〕（载入本地硬盘上规则策略文件rule.xml）

⑧ 对于已经存在的策略配置，如果想重置清除掉，点击工具栏上【重置】按钮进行重置清除

#### 全链路灰度发布编排建模

① 导航栏上选择〔全链路服务灰度发布〕

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop13.jpg)

根据[全链路版本条件权重灰度发布](#全链路版本条件权重灰度发布)示例中的场景

② 在〔灰度条件〕中，〔灰度条件〕（灰度流量占比）选择95%，〔稳定条件〕（稳定流量占比）会自动切换成5%

其它步骤跟[全链路蓝绿发布编排建模](#全链路蓝绿发布编排建模)相似，但比其简单

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop14.jpg)

### 全链路流量侦测

#### 全链路蓝绿发布流量侦测

① 导航栏上选择〔全链路服务流量侦测〕

② 在〔侦测入口〕中，操作如下

- 〔类型〕项。选择〔网关〕或者〔服务〕，本示例的规则策略是配置在网关上，所以选择〔网关〕
- 〔协议〕项。选择〔http://〕或者〔https://〕，视网关或者服务暴露出来的协议类型而定，本示例暴露出来的是http协议，所以选择〔http://〕
- 〔服务〕项。选择一个网关名或者服务名，下拉菜单列表随着〔类型〕项的改变而改变，蓝绿发布规则策略是配置在discovery-guide-gateway上，所以选择它
- 〔实例〕项。选择一个网关实例或者服务实例的IP地址和端口，下拉菜单列表随着〔服务〕的改变而改变

③ 在〔侦测参数〕中，操作如下

添加〔Header〕项和〔Parameter〕项，也可以〔Cookie〕项，使用者可以任意选择2个

- 〔Header〕项。输入a=1
- 〔Parameter〕项。输入b=2

④ 在〔侦测链路〕中，操作如下

- 增加服务discovery-guide-service-a
- 增加服务discovery-guide-service-b

⑤ 在〔侦测执行〕中，操作如下

- 〔维护〕项。选择〔版本〕、〔区域〕、〔环境〕、〔可用区〕、〔地址〕或者〔组〕，维护表示在拓扑图上聚合调用场景的维度，本示例的规则策略是是基于版本维度进行发布，所以选择〔版本〕
- 〔次数〕项。选择执行侦测的次数，基于网关和服务的性能压力，使用者需要酌情考虑调用次数
- 〔次数〕项。选择执行侦测的同一时刻线程并发数，并发数是对于图形化桌面端而言的
- 〔成功〕项。用来显示侦测成功的百分比
- 〔失败〕项。用来显示侦测失败的百分比
- 〔耗时〕项。用来显示侦测执行的消耗时间

⑥ 点击工具栏上【开始】按钮开始侦测，在侦测执行过程中，可以点击工具栏上【停止】按钮停止侦测

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop15.jpg)

从上述截图中，可以看到

- 在条件a==1&&b==2的〔绿条件〕下，执行〔网关〕->〔a服务1.0版本〕->〔b服务1.0版本〕的〔绿路由〕

⑦ 点击工具栏上【查看】按钮查看拓扑图上所有节点配置的规则策略，包括局部配置和全局配置

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop16.jpg)

⑧ 支持直接n-d-version策略路由Header驱动的蓝绿发布流量侦测

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop7.jpg)

#### 全链路灰度发布流量侦测

① 导航栏上选择〔全链路服务流量侦测〕

② 在〔侦测入口〕中，操作如下

- 〔服务〕项。灰度发布规则策略是配置在discovery-guide-zuul上，所以选择它

③ 在〔侦测参数〕中，不需要输入任何值

④ 在〔侦测执行〕中，〔次数〕项的值越大，灰度权重百分比越准确

其它步骤跟[全链路蓝绿发布流量侦测](#全链路蓝绿发布流量侦测)相似，但比其简单

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop17.jpg)

从上述截图中，可以看到

- 执行〔网关〕->〔a服务1.1版本〕->〔b服务1.1版本〕的〔灰度路由〕权重百分比95%左右
- 执行〔网关〕->〔a服务1.0版本〕->〔b服务1.0版本〕的〔稳定路由〕权重百分比5%左右

#### 全链路蓝绿灰度发布混合流量侦测

① 全链路蓝绿发布 + 灰度发布混合模式下流量侦测

在网关上配置了蓝绿发布规则策略，在a服务上配置了灰度发布规则策略

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop5.jpg)

② 全链路灰度发布 + 蓝绿发布混合模式下流量侦测

在网关上配置了灰度发布规则策略，在a服务上配置了蓝绿发布规则策略

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryDesktop6.jpg)

附录：全链路流量侦测接口

通过discovery-plugin-admin-center-starter内置基于LoadBalanced RestTemplate的接口方法，实现全链路侦测，用于查看全链路中调用的各个服务的版本、区域、环境、可用区、IP地址和端口等是否符合和满足蓝绿灰度条件。使用方式，如下

① 请求方式
```
POST
```
② 请求路径

网关为入口，路径为
```
http://[网关IP:PORT]/[A服务名]/inspector/inspect
```
服务为入口，路径为
```
http://[A服务IP:PORT]/inspector/inspect
```
③ 请求内容，服务名列表不分前后次序
```
{"serviceIdList":["B服务名", "C服务名", ....]}
```

## 全链路蓝绿灰度发布容灾

### 发布失败下的版本故障转移
版本故障转移，即无法找到相应版本的服务实例，路由到老的稳定版本的实例。其作用是防止蓝绿灰度版本发布人为设置错误，或者对应的版本实例发生灾难性的全部下线，导致流量有损

故障转移方式，对版本号进行排序，此解决方案的前置条件是版本号必须是规律的有次序，例如，以时间戳的方式。如果所有服务实例的版本号未设置，那么将转移到未设置版本号的实例上

需要通过如下开关开启该功能
```
# 启动和关闭版本故障转移。缺失则默认为false
spring.application.strategy.version.failover.enabled=true
```

### 并行发布下的版本偏好
版本偏好，即非蓝绿灰度发布场景下，路由到老的稳定版本的实例。其作用是防止多个网关上并行实施蓝绿灰度版本发布产生混乱，对处于非蓝绿灰度状态的服务，调用它的时候，只取它的老的稳定版本的实例；蓝绿灰度状态的服务，还是根据传递的Header版本号进行匹配

偏好方式，对版本号进行排序，此解决方案的前置条件是版本号必须是规律的有次序，例如，以时间戳的方式。如果所有服务实例的版本号未设置，那么将转移到未设置版本号的实例上

需要通过如下开关开启该功能
```
# 启动和关闭版本偏好。缺失则默认为false
spring.application.strategy.version.prefer.enabled=true
```

## 服务下线场景下全链路蓝绿灰度发布
服务下线场景下，由于Ribbon负载均衡组件存在着缓存机制，当被提供端服务实例已经下线，而消费端服务实例还暂时缓存着它，直到下个心跳周期才会把已下线的服务实例剔除，在此期间，如果发生调用，会造成流量有损

框架提供流量的实时性绝对无损策略。采用下线之前，把服务实例添加到屏蔽名单中，负载均衡不会去寻址该服务实例。下线之后，清除该名单。实现该方式，需要通过DevOps调用配置中心的Open API推送或者在配置中心界面手工修改，通过全局订阅方式实现，Group为discovery-guide-group，Data Id为discovery-guide-group（全局发布，两者都是组名）

### 全局唯一ID屏蔽
① 远程配置方式

全局唯一ID对应于元数据spring.application.uuid字段，框架会自动把该ID注册到注册中心，不需要用户自己配置，此用法适用于Docker和Kubernetes上IP地址不确定的场景。策略内容如下，采用如下两种方式之一均可
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy-blacklist>
        <!-- 单个ID形式。如果多个用“;”分隔，不允许出现空格 -->
        <id value="e92edde5-0153-4ec8-9cbb-b4d3f415aa33;af043384-c8a5-451e-88f4-457914e8e3bc"/>

        <!-- 多个ID节点形式 -->
        <!-- <id value="e92edde5-0153-4ec8-9cbb-b4d3f415aa33"/>
        <id value="af043384-c8a5-451e-88f4-457914e8e3bc"/> -->
    </strategy-blacklist>
</rule>
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide2-11.jpg)

② 全链路Header传递方式

```
n-d-id-blacklist=e92edde5-0153-4ec8-9cbb-b4d3f415aa33;af043384-c8a5-451e-88f4-457914e8e3bc
```

### IP地址和端口屏蔽
① 远程配置方式

通过IP地址或者端口或者IP地址+端口进行屏蔽，支持通配表达式方式，此用法适用于IP地址确定的场景。策略内容如下，采用如下两种方式之一均可
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy-blacklist>
        <!-- 单个Address形式。如果多个用“;”分隔，不允许出现空格 -->
        <address value="192.168.43.101:1201;192.168.*.102;1301"/>

        <!-- 多个Address节点形式 -->
        <!-- <address value="192.168.43.101:1201"/>
        <address value="192.168.*.102"/>
        <address value="1301"/> -->
    </strategy-blacklist>
</rule>
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide2-12.jpg)

② 全链路Header传递方式

```
n-d-address-blacklist=192.168.43.101:1201;192.168.*.102;1301
```

## 异步场景下全链路蓝绿灰度发布

### 异步场景下DiscoveryAgent解决方案
全链路策略路由Header和调用链Span在Hystrix线程池隔离模式下或者线程、线程池、@Async注解等异步调用Feign或者RestTemplate时，通过线程上下文切换会存在丢失Header的问题，通过下述步骤解决，同时适用于网关端和服务端。该方案可以替代Hystrix线程池隔离模式下的解决方案，也适用于其它有相同使用场景的基础框架和业务场景，例如：Dubbo

ThreadLocal的作用是提供线程内的局部变量，在多线程环境下访问时能保证各个线程内的ThreadLocal变量各自独立。在异步场景下，由于出现线程切换的问题，例如主线程切换到子线程，会导致线程ThreadLocal上下文丢失。DiscoveryAgent通过Java Agent方式解决这些痛点

涵盖所有Java框架的异步场景，解决如下7个异步场景下丢失线程ThreadLocal上下文的问题
- WebFlux Reactor
- `@`Async
- Hystrix Thread Pool Isolation
- Runnable
- Callable
- Single Thread
- Thread Pool
- SLF4J MDC

#### 异步跨线程DiscoveryAgent获取
插件获取方式有两种方式
- 通过[https://github.com/Nepxion/DiscoveryAgent/releases](https://github.com/Nepxion/DiscoveryAgent/releases)下载最新版本的Discovery Agent
- 编译[https://github.com/Nepxion/DiscoveryAgent](https://github.com/Nepxion/DiscoveryAgent)产生discovery-agent目录

#### 异步跨线程DiscoveryAgent使用
- discovery-agent-starter-`$`{discovery.version}.jar为Agent引导启动程序，JVM启动时进行加载；discovery-agent/plugin目录包含discovery-agent-starter-plugin-strategy-`$`{discovery.version}.jar为Nepxion Discovery自带的实现方案，业务系统可以自定义plugin，解决业务自己定义的上下文跨线程传递
- 通过如下-javaagent启动，基本格式，如下

```
-javaagent:/discovery-agent/discovery-agent-starter-${discovery.agent.version}.jar -Dthread.scan.packages=com.abc;com.xyz
```

例如

```
-javaagent:C:/opt/discovery-agent/discovery-agent-starter-${discovery.agent.version}.jar -Dthread.scan.packages=reactor.core.publisher;org.springframework.aop.interceptor;com.netflix.hystrix;com.nepxion.discovery.guide.service.feign
```

参数说明
- /discovery-agent：Agent所在的目录，需要对应到实际的目录上
- `-D`thread.scan.packages：Runnable，Callable对象所在的扫描目录，该目录下的Runnable，Callable对象都会被装饰。该目录最好精细和准确，这样可以减少被装饰的对象数，提高性能，目录如果有多个，用“;”分隔。参数定义为
    - WebFlux Reactor异步场景下的扫描目录对应为reactor.core.publisher，可以解决基于Reactor的Spring Cloud LoadBalancer异步负载均衡下的上下文传递
    - `@`Async场景下的扫描目录对应为org.springframework.aop.interceptor
    - Hystrix线程池隔离场景下的扫描目录对应为com.netflix.hystrix
    - 线程，线程池的扫描目录对应为自定义Runnable，Callable对象所在类的目录
    - 上述扫描路径如果含有“;”，可能会在某些操作系统中无法被识别，请用`""`进行引入，例如，-Dthread.scan.packages="com.abc;com.xyz"
- `-D`thread.gateway.enabled：Spring Cloud Gateway端策略Header输出到异步子线程。默认开启
- `-D`thread.zuul.enabled：Zuul端策略Header输出到异步子线程。默认开启
- `-D`thread.service.enabled：服务端策略Header输出到异步子线程。默认开启
- `-D`thread.mdc.enabled：SLF4J MDC日志输出到异步子线程。默认开启
- `-D`thread.request.decorator.enabled：异步调用场景下在服务端的Request请求的装饰，当主线程先于子线程执行完的时候，Request会被Destory，导致Header仍旧拿不到，开启装饰，就可以确保拿到。默认为开启，根据实践经验，大多数场景下，需要开启该开关

![](http://nepxion.gitee.io/docs/icon-doc/tip.png) 提醒：对于扫描目录，请务必根据实际场景做配置，例如，不存在WebFlux Reactor的异步场景，就不必把reactor.core.publisher配置到扫描目录中

#### 异步跨线程DiscoveryAgent扩展
- 根据规范开发一个插件，插件提供了钩子函数，在某个类被加载的时候，可以注册一个事件到线程上下文切换事件当中，实现业务自定义ThreadLocal的跨线程传递
- plugin目录为放置需要在线程切换时进行ThreadLocal传递的自定义插件。业务自定义插件开发完后，放入到plugin目录下即可

具体步骤介绍，如下

① SDK侧工作

- 新建ThreadLocal上下文类

```java
public class MyContext {
    private static final ThreadLocal<MyContext> THREAD_LOCAL = new ThreadLocal<MyContext>() {
        @Override
        protected MyContext initialValue() {
            return new MyContext();
        }
    };

    public static MyContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public static void clearCurrentContext() {
        THREAD_LOCAL.remove();
    }

    private Map<String, String> attributes = new HashMap<>();

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
```

② Agent侧工作

- 新建一个模块，引入如下依赖

```xml
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-agent-starter</artifactId>
    <version>${discovery.agent.version}</version>
    <scope>provided</scope>
</dependency>
```

- 新建一个ThreadLocalHook类继承AbstractThreadLocalHook

```java
public class MyContextHook extends AbstractThreadLocalHook {
    @Override
    public Object create() {
        // 从主线程的ThreadLocal里获取并返回上下文对象
        return MyContext.getCurrentContext().getAttributes();
    }

    @Override
    public void before(Object object) {
        // 把create方法里获取到的上下文对象放置到子线程的ThreadLocal里
        if (object instanceof Map) {
            MyContext.getCurrentContext().setAttributes((Map<String, String>) object);
        }
    }

    @Override
    public void after() {
        // 线程结束，销毁上下文对象
        MyContext.clearCurrentContext();
    }
}
```

- 新建一个Plugin类继承AbstractPlugin

```java
public class MyContextPlugin extends AbstractPlugin {
    private Boolean threadMyPluginEnabled = Boolean.valueOf(System.getProperty("thread.myplugin.enabled", "false"));

    @Override
    protected String getMatcherClassName() {
        // 返回存储ThreadLocal对象的类名，由于插件是可以插拔的，所以必须是字符串形式，不允许是显式引入类
        return "com.nepxion.discovery.example.sdk.MyContext";
    }

    @Override
    protected String getHookClassName() {
        // 返回ThreadLocalHook类名
        return MyContextHook.class.getName();
    }

    @Override
    protected boolean isEnabled() {
        // 通过外部-Dthread.myplugin.enabled=true/false的运行参数来控制当前Plugin是否生效。该方法在父类中定义的返回值为true，即缺省为生效
        return threadMyPluginEnabled;
    }
}
```

- 定义SPI扩展，在src/main/resources/META-INF/services目录下定义SPI文件

名称为固定如下格式
```
com.nepxion.discovery.agent.plugin.Plugin
```
内容为Plugin类的全路径
```
com.nepxion.discovery.example.agent.MyContextPlugin
```

- 执行Maven编译，把编译后的包放在discovery-agent/plugin目录下

- 给服务增加启动参数并启动，如下

```
-javaagent:C:/opt/discovery-agent/discovery-agent-starter-${discovery.agent.version}.jar -Dthread.scan.packages=com.nepxion.discovery.example.application -Dthread.myplugin.enabled=true
```

③ Application侧工作

- 执行MyApplication，它模拟在主线程ThreadLocal放入Map数据，子线程通过DiscoveryAgent获取到该Map数据，并打印出来

```java
@SpringBootApplication
@RestController
public class MyApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MyApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);

        invoke();
    }

    public static void invoke() {
        RestTemplate restTemplate = new RestTemplate();

        for (int i = 1; i <= 10; i++) {
            restTemplate.getForEntity("http://localhost:8080/index/" + i, String.class).getBody();
        }
    }

    @GetMapping("/index/{value}")
    public String index(@PathVariable(value = "value") String value) throws InterruptedException {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put(value, "MyContext");

        MyContext.getCurrentContext().setAttributes(attributes);

        LOG.info("【主】线程ThreadLocal：{}", MyContext.getCurrentContext().getAttributes());

        new Thread(new Runnable() {
            @Override
            public void run() {
                LOG.info("【子】线程ThreadLocal：{}", MyContext.getCurrentContext().getAttributes());

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                LOG.info("Sleep 5秒之后，【子】线程ThreadLocal：{} ", MyContext.getCurrentContext().getAttributes());
            }
        }).start();

        return "";
    }
}
```

输出结果，如下
```
2020-11-09 00:08:14.330  INFO 16588 --- [nio-8080-exec-1] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{1=MyContext}
2020-11-09 00:08:14.381  INFO 16588 --- [       Thread-4] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{1=MyContext}
2020-11-09 00:08:14.402  INFO 16588 --- [nio-8080-exec-2] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{2=MyContext}
2020-11-09 00:08:14.403  INFO 16588 --- [       Thread-5] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{2=MyContext}
2020-11-09 00:08:14.405  INFO 16588 --- [nio-8080-exec-3] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{3=MyContext}
2020-11-09 00:08:14.406  INFO 16588 --- [       Thread-6] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{3=MyContext}
2020-11-09 00:08:14.414  INFO 16588 --- [nio-8080-exec-4] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{4=MyContext}
2020-11-09 00:08:14.414  INFO 16588 --- [       Thread-7] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{4=MyContext}
2020-11-09 00:08:14.417  INFO 16588 --- [nio-8080-exec-5] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{5=MyContext}
2020-11-09 00:08:14.418  INFO 16588 --- [       Thread-8] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{5=MyContext}
2020-11-09 00:08:14.421  INFO 16588 --- [nio-8080-exec-6] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{6=MyContext}
2020-11-09 00:08:14.422  INFO 16588 --- [       Thread-9] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{6=MyContext}
2020-11-09 00:08:14.424  INFO 16588 --- [nio-8080-exec-7] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{7=MyContext}
2020-11-09 00:08:14.425  INFO 16588 --- [      Thread-10] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{7=MyContext}
2020-11-09 00:08:14.427  INFO 16588 --- [nio-8080-exec-8] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{8=MyContext}
2020-11-09 00:08:14.428  INFO 16588 --- [      Thread-11] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{8=MyContext}
2020-11-09 00:08:14.430  INFO 16588 --- [nio-8080-exec-9] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{9=MyContext}
2020-11-09 00:08:14.431  INFO 16588 --- [      Thread-12] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{9=MyContext}
2020-11-09 00:08:14.433  INFO 16588 --- [io-8080-exec-10] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{10=MyContext}
2020-11-09 00:08:14.434  INFO 16588 --- [      Thread-13] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{10=MyContext}
2020-11-09 00:08:19.382  INFO 16588 --- [       Thread-4] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{1=MyContext} 
2020-11-09 00:08:19.404  INFO 16588 --- [       Thread-5] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{2=MyContext} 
2020-11-09 00:08:19.406  INFO 16588 --- [       Thread-6] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{3=MyContext} 
2020-11-09 00:08:19.416  INFO 16588 --- [       Thread-7] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{4=MyContext} 
2020-11-09 00:08:19.418  INFO 16588 --- [       Thread-8] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{5=MyContext} 
2020-11-09 00:08:19.422  INFO 16588 --- [       Thread-9] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{6=MyContext} 
2020-11-09 00:08:19.425  INFO 16588 --- [      Thread-10] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{7=MyContext} 
2020-11-09 00:08:19.428  INFO 16588 --- [      Thread-11] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{8=MyContext} 
2020-11-09 00:08:19.432  INFO 16588 --- [      Thread-12] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{9=MyContext} 
2020-11-09 00:08:19.434  INFO 16588 --- [      Thread-13] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{10=MyContext} 
```

如果不加异步Agent，则输出结果，如下，可以发现在子线程中ThreadLocal上下文全部都丢失
```
2020-11-09 00:01:40.133  INFO 16692 --- [nio-8080-exec-1] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{1=MyContext}
2020-11-09 00:01:40.135  INFO 16692 --- [       Thread-8] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{}
2020-11-09 00:01:40.158  INFO 16692 --- [nio-8080-exec-2] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{2=MyContext}
2020-11-09 00:01:40.159  INFO 16692 --- [       Thread-9] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{}
2020-11-09 00:01:40.162  INFO 16692 --- [nio-8080-exec-3] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{3=MyContext}
2020-11-09 00:01:40.163  INFO 16692 --- [      Thread-10] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{}
2020-11-09 00:01:40.170  INFO 16692 --- [nio-8080-exec-5] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{4=MyContext}
2020-11-09 00:01:40.170  INFO 16692 --- [      Thread-11] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{}
2020-11-09 00:01:40.173  INFO 16692 --- [nio-8080-exec-4] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{5=MyContext}
2020-11-09 00:01:40.174  INFO 16692 --- [      Thread-12] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{}
2020-11-09 00:01:40.176  INFO 16692 --- [nio-8080-exec-6] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{6=MyContext}
2020-11-09 00:01:40.177  INFO 16692 --- [      Thread-13] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{}
2020-11-09 00:01:40.179  INFO 16692 --- [nio-8080-exec-8] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{7=MyContext}
2020-11-09 00:01:40.180  INFO 16692 --- [      Thread-14] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{}
2020-11-09 00:01:40.182  INFO 16692 --- [nio-8080-exec-7] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{8=MyContext}
2020-11-09 00:01:40.182  INFO 16692 --- [      Thread-15] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{}
2020-11-09 00:01:40.185  INFO 16692 --- [nio-8080-exec-9] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{9=MyContext}
2020-11-09 00:01:40.186  INFO 16692 --- [      Thread-16] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{}
2020-11-09 00:01:40.188  INFO 16692 --- [io-8080-exec-10] c.n.d.example.application.MyApplication  : 【主】线程ThreadLocal：{10=MyContext}
2020-11-09 00:01:40.189  INFO 16692 --- [      Thread-17] c.n.d.example.application.MyApplication  : 【子】线程ThreadLocal：{}
2020-11-09 00:01:45.136  INFO 16692 --- [       Thread-8] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{} 
2020-11-09 00:01:45.160  INFO 16692 --- [       Thread-9] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{} 
2020-11-09 00:01:45.163  INFO 16692 --- [      Thread-10] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{} 
2020-11-09 00:01:45.171  INFO 16692 --- [      Thread-11] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{} 
2020-11-09 00:01:45.174  INFO 16692 --- [      Thread-12] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{} 
2020-11-09 00:01:45.177  INFO 16692 --- [      Thread-13] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{} 
2020-11-09 00:01:45.181  INFO 16692 --- [      Thread-14] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{} 
2020-11-09 00:01:45.183  INFO 16692 --- [      Thread-15] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{} 
2020-11-09 00:01:45.187  INFO 16692 --- [      Thread-16] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{} 
2020-11-09 00:01:45.190  INFO 16692 --- [      Thread-17] c.n.d.example.application.MyApplication  : Sleep 5秒之后，【子】线程ThreadLocal：{} 
```

完整示例，请参考[https://github.com/Nepxion/DiscoveryAgent/tree/master/discovery-agent-example](https://github.com/Nepxion/DiscoveryAgent/tree/master/discovery-agent-example)。上述自定义插件的方式，即可解决使用者在线程切换时丢失ThreadLocal上下文的问题

### 异步场景下Hystrix线程池隔离解决方案
全链路策略路由Header和调用链Span在Hystrix线程池隔离模式（信号量模式不需要引入）下传递时，通过线程上下文切换会存在丢失Header的问题，通过下述步骤解决，同时适用于网关端和服务端

① Pom引入
```xml
<!-- 当服务用Hystrix做线程隔离的时候，才需要导入下面的包 -->
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-strategy-starter-hystrix</artifactId>
    <version>${discovery.version}</version>
</dependency>
```

② 配置开启
```
# 开启服务端实现Hystrix线程隔离模式做服务隔离时，必须把spring.application.strategy.hystrix.threadlocal.supported设置为true，同时要引入discovery-plugin-strategy-starter-hystrix包，否则线程切换时会发生ThreadLocal上下文对象丢失。缺失则默认为false
spring.application.strategy.hystrix.threadlocal.supported=true
```

该方案也可以通过[异步场景下DiscoveryAgent解决方案](#异步场景下DiscoveryAgent解决方案)解决

## 全链路数据库和消息队列蓝绿发布
通过订阅相关参数的变化，实现参数化蓝绿发布，可用于如下场景

① 基于多Datasource的数据库蓝绿发布

② 基于多Queue的消息队列蓝绿发布

增加参数化蓝绿发布规则，Group为discovery-guide-group，Data Id为discovery-guide-group（全局发布，两者都是组名），规则内容如下，实现功能

① 服务a在版本为1.0的时候，数据库的数据源指向db1；服务a在版本为1.1的时候，数据库的数据源指向db2

② 服务b在区域为dev的时候，消息队列指向queue1；服务b在区域为qa的时候，消息队列指向queue2

③ 服务c在环境为env1的时候，数据库的数据源指向db1；服务c在环境为env2的时候，数据库的数据源指向db2

④ 服务d在可用区为zone1的时候，消息队列指向queue1；服务d在可用区为zone2的时候，消息队列指向queue2

⑤ 服务c在IP地址和端口为192.168.43.101:1201的时候，数据库的数据源指向db1；服务c在IP地址和端口为192.168.43.102:1201的时候，数据库的数据源指向db2

```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <parameter>
        <service service-name="discovery-guide-service-a" tag-key="version" tag-value="1.0" key="ShardingSphere" value="db1"/>
        <service service-name="discovery-guide-service-a" tag-key="version" tag-value="1.1" key="ShardingSphere" value="db2"/>
        <service service-name="discovery-guide-service-b" tag-key="region" tag-value="dev" key="RocketMQ" value="queue1"/>
        <service service-name="discovery-guide-service-b" tag-key="region" tag-value="qa" key="RocketMQ" value="queue2"/>
        <service service-name="discovery-guide-service-c" tag-key="env" tag-value="env1" key="ShardingSphere" value="db1"/>
        <service service-name="discovery-guide-service-c" tag-key="env" tag-value="env2" key="ShardingSphere" value="db2"/>
        <service service-name="discovery-guide-service-d" tag-key="zone" tag-value="zone1" key="RocketMQ" value="queue1"/>
        <service service-name="discovery-guide-service-d" tag-key="zone" tag-value="zone2" key="RocketMQ" value="queue2"/>
        <service service-name="discovery-guide-service-e" tag-key="address" tag-value="192.168.43.101:1201" key="ShardingSphere" value="db1"/>
        <service service-name="discovery-guide-service-e" tag-key="address" tag-value="192.168.43.102:1201" key="ShardingSphere" value="db2"/>
    </parameter>
</rule>
```
通过事件总线方式，对参数改变动态实现监听，并在此类里自行对接相关的数据库和消息队列中间件的切换和驱动
```java
@EventBus
public class MySubscriber {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Subscribe
    public void onParameterChanged(ParameterChangedEvent parameterChangedEvent) {
        ParameterEntity parameterEntity = parameterChangedEvent.getParameterEntity();
        String serviceId = pluginAdapter.getServiceId();
        List<ParameterServiceEntity> parameterServiceEntityList = null;
        if (parameterEntity != null) {
            Map<String, List<ParameterServiceEntity>> parameterServiceMap = parameterEntity.getParameterServiceMap();
            parameterServiceEntityList = parameterServiceMap.get(serviceId);
        }
        // parameterServiceEntityList为动态参数列表
    }
}
```
使用者可以通过如下开关，决定在服务启动过程中，读到参数配置的时候，是否要发送一个事件触发数据库和消息队列中间件的切换
```
# 启动和关闭在服务启动的时候参数订阅事件发送。缺失则默认为true
spring.application.parameter.event.onstart.enabled=true
```
参考[https://github.com/Nepxion/DiscoveryContrib](https://github.com/Nepxion/DiscoveryContrib)里的实现方式

## 规则策略定义

### 规则策略格式定义
![](http://nepxion.gitee.io/docs/icon-doc/warning.png) 需要注意，服务名大小写规则
- 在配置文件（application.properties、application.yaml等）里，定义服务名（spring.application.name）不区分大小写
- 在规则文件（XML、Json）里，引用的服务名必须小写
- 在Nacos、Apollo、Redis等远程配置中心的Key，包含的服务名必须小写

### 规则策略内容定义
规则策略的格式是XML或者Json，存储于本地文件或者远程配置中心，可以通过远程配置中心修改的方式达到规则策略动态化。其核心代码参考discovery-plugin-framework以及它的扩展、discovery-plugin-config-center以及它的扩展和discovery-plugin-admin-center等

### 规则策略示例
XML最全的示例如下，Json示例见源码discovery-springcloud-example-service工程下的rule.json

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

        <!-- 服务发现的多版本灰度匹配控制 -->
        <!-- service-name，表示服务名 -->
        <!-- version-value，表示可供访问的版本，如果多个用“;”分隔，不允许出现空格 -->
        <!-- 版本策略介绍 -->
        <!-- 1. 标准配置，举例如下 -->
        <!--    <service consumer-service-name="a" provider-service-name="b" consumer-version-value="1.0" provider-version-value="1.0;1.1"/> 表示消费端1.0版本，允许访问提供端1.0和1.1版本 -->
        <!-- 2. 版本值不配置，举例如下 -->
        <!--    <service consumer-service-name="a" provider-service-name="b" provider-version-value="1.0;1.1"/> 表示消费端任何版本，允许访问提供端1.0和1.1版本 -->
        <!--    <service consumer-service-name="a" provider-service-name="b" consumer-version-value="1.0"/> 表示消费端1.0版本，允许访问提供端任何版本 -->
        <!--    <service consumer-service-name="a" provider-service-name="b"/> 表示消费端任何版本，允许访问提供端任何版本 -->
        <!-- 3. 版本值空字符串，举例如下 -->
        <!--    <service consumer-service-name="a" provider-service-name="b" consumer-version-value="" provider-version-value="1.0;1.1"/> 表示消费端任何版本，允许访问提供端1.0和1.1版本 -->
        <!--    <service consumer-service-name="a" provider-service-name="b" consumer-version-value="1.0" provider-version-value=""/> 表示消费端1.0版本，允许访问提供端任何版本 -->
        <!--    <service consumer-service-name="a" provider-service-name="b" consumer-version-value="" provider-version-value=""/> 表示消费端任何版本，允许访问提供端任何版本 -->
        <!-- 4. 版本对应关系未定义，默认消费端任何版本，允许访问提供端任何版本 -->
        <!-- 特殊情况处理，在使用上需要极力避免该情况发生 -->
        <!-- 1. 消费端的application.properties未定义版本号，则该消费端可以访问提供端任何版本 -->
        <!-- 2. 提供端的application.properties未定义版本号，当消费端在xml里不做任何版本配置，才可以访问该提供端 -->
        <version>
            <!-- 表示网关g的1.0，允许访问提供端服务a的1.0版本 -->
            <service consumer-service-name="discovery-springcloud-example-gateway" provider-service-name="discovery-springcloud-example-a" consumer-version-value="1.0" provider-version-value="1.0"/>
            <!-- 表示网关g的1.1，允许访问提供端服务a的1.1版本 -->
            <service consumer-service-name="discovery-springcloud-example-gateway" provider-service-name="discovery-springcloud-example-a" consumer-version-value="1.1" provider-version-value="1.1"/>
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

        <!-- 服务发现的多区域灰度匹配控制 -->
        <!-- service-name，表示服务名 -->
        <!-- region-value，表示可供访问的区域，如果多个用“;”分隔，不允许出现空格 -->
        <!-- 区域策略介绍 -->
        <!-- 1. 标准配置，举例如下 -->
        <!--    <service consumer-service-name="a" provider-service-name="b" consumer-region-value="dev" provider-region-value="dev"/> 表示dev区域的消费端，允许访问dev区域的提供端 -->
        <!-- 2. 区域值不配置，举例如下 -->
        <!--    <service consumer-service-name="a" provider-service-name="b" provider-region-value="dev;qa"/> 表示任何区域的消费端，允许访问dev区域和qa区域的提供端 -->
        <!--    <service consumer-service-name="a" provider-service-name="b" consumer-region-value="dev"/> 表示dev区域的消费端，允许访问任何区域的提供端 -->
        <!--    <service consumer-service-name="a" provider-service-name="b"/> 表示任何区域的消费端，允许访问任何区域的提供端 -->
        <!-- 3. 区域值空字符串，举例如下 -->
        <!--    <service consumer-service-name="a" provider-service-name="b" consumer-region-value="" provider-region-value="dev;qa"/> 表示任何区域的消费端，允许访问dev区域和qa区域的提供端 -->
        <!--    <service consumer-service-name="a" provider-service-name="b" consumer-region-value="dev" provider-region-value=""/> 表示dev区域的消费端，允许访问任何区域的提供端 -->
        <!--    <service consumer-service-name="a" provider-service-name="b" consumer-region-value="" provider-region-value=""/> 表示任何区域的消费端，允许访问任何区域的提供端 -->
        <!-- 4. 区域对应关系未定义，默认表示任何区域的消费端，允许访问任何区域的提供端 -->
        <!-- 特殊情况处理，在使用上需要极力避免该情况发生 -->
        <!-- 1. 消费端的application.properties未定义区域值，则该消费端可以访问任何区域的提供端 -->
        <!-- 2. 提供端的application.properties未定义区域值，当消费端在xml里不做任何区域配置，才可以访问该提供端 -->
        <region>
            <!-- 表示dev区域的消费端服务a，允许访问dev区域的提供端服务b -->
            <service consumer-service-name="discovery-springcloud-example-a" provider-service-name="discovery-springcloud-example-b" consumer-region-value="dev" provider-region-value="dev"/>
            <!-- 表示qa区域的消费端服务a，允许访问qa区域的提供端服务b -->
            <service consumer-service-name="discovery-springcloud-example-a" provider-service-name="discovery-springcloud-example-b" consumer-region-value="qa" provider-region-value="qa"/>
            <!-- 表示dev区域的消费端服务b，允许访问dev区域的提供端服务c -->
            <service consumer-service-name="discovery-springcloud-example-b" provider-service-name="discovery-springcloud-example-c" consumer-region-value="dev" provider-region-value="dev"/>
            <!-- 表示qa区域的消费端服务b，允许访问qa区域的提供端服务c -->
            <service consumer-service-name="discovery-springcloud-example-b" provider-service-name="discovery-springcloud-example-c" consumer-region-value="qa" provider-region-value="qa"/>
        </region>

        <!-- 服务发现的多版本或者多区域的灰度权重控制 -->
        <!-- service-name，表示服务名 -->
        <!-- weight-value，表示版本对应的权重值，格式为"版本/区域值=权重值"，如果多个用“;”分隔，不允许出现空格 -->
        <!-- 版本权重策略介绍 -->
        <!-- 1. 标准配置，举例如下 -->
        <!--    <service consumer-service-name="a" provider-service-name="b" provider-weight-value="1.0=90;1.1=10"/> 表示消费端访问提供端的时候，提供端的1.0版本提供90%的权重流量，1.1版本提供10%的权重流量 -->
        <!--    <service provider-service-name="b" provider-weight-value="1.0=90;1.1=10"/> 表示所有消费端访问提供端的时候，提供端的1.0版本提供90%的权重流量，1.1版本提供10%的权重流量 -->
        <!-- 2. 局部配置，即指定consumer-service-name，专门为该消费端配置权重。全局配置，即不指定consumer-service-name，为所有消费端配置相同情形的权重。当局部配置和全局配置同时存在的时候，以局部配置优先 -->
        <!-- 3. 尽量为线上所有版本都赋予权重值 -->
        <!-- 全局版本权重策略介绍 -->
        <!-- 1. 标准配置，举例如下 -->
        <!--    <version provider-weight-value="1.0=85;1.1=15"/> 表示版本为1.0的服务提供85%的权重流量，版本为1.1的服务提供15%的权重流量 -->
        <!-- 2. 全局版本权重可以切换整条调用链的权重配比 -->
        <!-- 3. 尽量为线上所有版本都赋予权重值 -->

        <!-- 区域权重策略介绍 -->
        <!-- 1. 标准配置，举例如下 -->
        <!--    <service consumer-service-name="a" provider-service-name="b" provider-weight-value="dev=85;qa=15"/> 表示消费端访问提供端的时候，区域为dev的服务提供85%的权重流量，区域为qa的服务提供15%的权重流量 -->
        <!--    <service provider-service-name="b" provider-weight-value="dev=85;qa=15"/> 表示所有消费端访问提供端的时候，区域为dev的服务提供85%的权重流量，区域为qa的服务提供15%的权重流量 -->
        <!-- 2. 局部配置，即指定consumer-service-name，专门为该消费端配置权重。全局配置，即不指定consumer-service-name，为所有消费端配置相同情形的权重。当局部配置和全局配置同时存在的时候，以局部配置优先 -->
        <!-- 3. 尽量为线上所有版本都赋予权重值 -->
        <!-- 全局区域权重策略介绍 -->
        <!-- 1. 标准配置，举例如下 -->
        <!--    <region provider-weight-value="dev=85;qa=15"/> 表示区域为dev的服务提供85%的权重流量，区域为qa的服务提供15%的权重流量 -->
        <!-- 2. 全局区域权重可以切换整条调用链的权重配比 -->
        <!-- 3. 尽量为线上所有区域都赋予权重值 -->
        <weight>
            <!-- 权重流量配置有如下六种方式，优先级分别是由高到底，即先从第一种方式取权重流量值，取不到则到第二种方式取值，以此类推，最后仍取不到则忽略。使用者按照实际情况，选择一种即可 -->
            <!-- 表示消费端服务b访问提供端服务c的时候，提供端服务c的1.0版本提供90%的权重流量，1.1版本提供10%的权重流量 -->
            <service consumer-service-name="discovery-springcloud-example-b" provider-service-name="discovery-springcloud-example-c" provider-weight-value="1.0=90;1.1=10" type="version"/>
            <!-- 表示所有消费端服务访问提供端服务c的时候，提供端服务c的1.0版本提供90%的权重流量，1.1版本提供10%的权重流量 -->
            <service provider-service-name="discovery-springcloud-example-c" provider-weight-value="1.0=90;1.1=10" type="version"/>
            <!-- 表示所有版本为1.0的服务提供90%的权重流量，版本为1.1的服务提供10%的权重流量 -->
            <version provider-weight-value="1.0=90;1.1=10"/>

            <!-- 表示消费端服务b访问提供端服务c的时候，提供端服务c的dev区域提供85%的权重流量，qa区域提供15%的权重流量 -->
            <service consumer-service-name="discovery-springcloud-example-b" provider-service-name="discovery-springcloud-example-c" provider-weight-value="dev=85;qa=15" type="region"/>
            <!-- 表示所有消费端服务访问提供端服务c的时候，提供端服务c的dev区域提供85%的权重流量，qa区域提供15%的权重流量 -->
            <service provider-service-name="discovery-springcloud-example-c" provider-weight-value="dev=85;qa=15" type="region"/>
            <!-- 表示所有区域为dev的服务提供85%的权重流量，区域为qa的服务提供15%的权重流量 -->
            <region provider-weight-value="dev=85;qa=15"/>
        </weight>
    </discovery>

    <!-- 基于Http Header传递的策略路由，全局缺省路由（第三优先级） -->
    <strategy>
        <!-- 版本路由 -->
        <version>{"discovery-springcloud-example-a":"1.0", "discovery-springcloud-example-b":"1.0", "discovery-springcloud-example-c":"1.0;1.2"}</version>
        <!-- <version>1.0</version> -->
        <!-- 区域路由 -->
        <region>{"discovery-springcloud-example-a":"qa;dev", "discovery-springcloud-example-b":"dev", "discovery-springcloud-example-c":"qa"}</region>
        <!-- <region>dev</region> -->
        <!-- IP地址和端口路由 -->
        <address>{"discovery-springcloud-example-a":"192.168.43.101:1100", "discovery-springcloud-example-b":"192.168.43.101:1201", "discovery-springcloud-example-c":"192.168.43.101:1300"}</address>
        <!-- 权重流量配置有如下四种方式，优先级分别是由高到底，即先从第一种方式取权重流量值，取不到则到第二种方式取值，以此类推，最后仍取不到则忽略。使用者按照实际情况，选择一种即可 -->
        <!-- 版本权重路由 -->
        <version-weight>{"discovery-springcloud-example-a":"1.0=90;1.1=10", "discovery-springcloud-example-b":"1.0=90;1.1=10", "discovery-springcloud-example-c":"1.0=90;1.1=10"}</version-weight>
        <!-- <version-weight>1.0=90;1.1=10</version-weight> -->
        <!-- 区域权重路由 -->
        <region-weight>{"discovery-springcloud-example-a":"dev=85;qa=15", "discovery-springcloud-example-b":"dev=85;qa=15", "discovery-springcloud-example-c":"dev=85;qa=15"}</region-weight>
        <!-- <region-weight>dev=85;qa=15</region-weight> -->
    </strategy>

    <!-- 基于Http Header传递的定制化策略路由，支持蓝绿部署和灰度发布两种模式。如果都不命中，则执行上面的全局缺省路由 -->
    <strategy-customization>
        <!-- Spel表达式在XML中的转义符：-->
        <!-- 和符号 & 转义为 &amp; 必须转义 -->
        <!-- 小于号 < 转义为 &lt; 必须转义 -->
        <!-- 双引号 " 转义为 &quot; 必须转义 -->
        <!-- 大于号 > 转义为 &gt; -->
        <!-- 单引号 ' 转义为 &apos; -->

        <!-- 全链路蓝绿部署：条件命中的匹配方式（第一优先级），支持版本匹配、区域匹配、IP地址和端口匹配、版本权重匹配、区域权重匹配 -->
        <!-- Expression节点不允许缺失 -->
        <conditions type="blue-green">
            <condition id="1" expression="#H['a'] == '1' &amp;&amp; #H['b'] == '2'" version-id="a-1" region-id="b-1" address-id="c-1" version-weight-id="d-1" region-weight-id="e-1"/>
            <condition id="2" expression="#H['c'] == '3'" version-id="a-2" region-id="b-2" address-id="c-2" version-weight-id="d-2" region-weight-id="e-2"/>
        </conditions>

        <!-- 全链路灰度发布：条件命中的随机权重（第二优先级），支持版本匹配、区域匹配、IP地址和端口匹配 -->
        <!-- Expression节点允许缺失，当含Expression和未含Expression的配置并存时，以未含Expression的配置为优先 -->
        <conditions type="gray">
            <condition id="1" expression="#H['a'] == '1' &amp;&amp; #H['b'] == '2'" version-id="a-1=10;a-2=90" region-id="b-1=20;b-2=80" address-id="c-1=30;c-2=70"/>
            <condition id="2" expression="#H['c'] == '3'" version-id="a-1=90;a-2=10" region-id="b-1=80;b-2=20" address-id="c-1=70;c-2=30"/>
            <condition id="3" version-id="a-1=5;a-2=95" region-id="b-1=5;b-2=95" address-id="c-1=5;c-2=95"/>
        </conditions>

        <routes>
            <route id="a-1" type="version">{"discovery-springcloud-example-a":"1.0", "discovery-springcloud-example-b":"1.0", "discovery-springcloud-example-c":"1.0;1.2"}</route>
            <route id="a-2" type="version">{"discovery-springcloud-example-a":"1.1", "discovery-springcloud-example-b":"1.1", "discovery-springcloud-example-c":"1.2"}</route>
            <route id="b-1" type="region">{"discovery-springcloud-example-a":"qa;dev", "discovery-springcloud-example-b":"dev", "discovery-springcloud-example-c":"qa"}</route>
            <route id="b-2" type="region">{"discovery-springcloud-example-a":"qa", "discovery-springcloud-example-b":"qa", "discovery-springcloud-example-c":"qa"}</route>
            <route id="c-1" type="address">{"discovery-springcloud-example-a":"192.168.43.101:1100", "discovery-springcloud-example-b":"192.168.43.101:1201", "discovery-springcloud-example-c":"192.168.43.101:1300"}</route>
            <route id="c-2" type="address">{"discovery-springcloud-example-a":"192.168.43.101:1101", "discovery-springcloud-example-b":"192.168.43.101:1201", "discovery-springcloud-example-c":"192.168.43.101:1301"}</route>
            <route id="d-1" type="version-weight">{"discovery-springcloud-example-a":"1.0=90;1.1=10", "discovery-springcloud-example-b":"1.0=90;1.1=10", "discovery-springcloud-example-c":"1.0=90;1.1=10"}</route>
            <route id="d-2" type="version-weight">{"discovery-springcloud-example-a":"1.0=10;1.1=90", "discovery-springcloud-example-b":"1.0=10;1.1=90", "discovery-springcloud-example-c":"1.0=10;1.1=90"}</route>
            <route id="e-1" type="region-weight">{"discovery-springcloud-example-a":"dev=85;qa=15", "discovery-springcloud-example-b":"dev=85;qa=15", "discovery-springcloud-example-c":"dev=85;qa=15"}</route>
            <route id="e-2" type="region-weight">{"discovery-springcloud-example-a":"dev=15;qa=85", "discovery-springcloud-example-b":"dev=15;qa=85", "discovery-springcloud-example-c":"dev=15;qa=85"}</route>
        </routes>

        <!-- 策略中配置条件表达式中的Header来决策蓝绿和灰度，可以代替外部传入Header -->
        <headers>
            <header key="a" value="1"/>
        </headers>
    </strategy-customization>

    <!-- 策略路由上服务屏蔽黑名单。一般适用于服务下线场景，流量实现实时性的绝对无损：下线之前，把服务实例添加到下面屏蔽名单中，负载均衡不会去寻址该服务实例。下线之后，清除该名单。该配置运行在全局订阅模式下 -->
    <strategy-blacklist>
        <!-- 通过全局唯一ID进行屏蔽，ID对应于元数据spring.application.uuid字段，适用于Docker和K8s上IP地址不确定的场景 -->
        <!-- 单个ID形式。如果多个用“;”分隔，不允许出现空格 -->
        <id value="e92edde5-0153-4ec8-9cbb-b4d3f415aa33;af043384-c8a5-451e-88f4-457914e8e3bc"/>

        <!-- 多个ID节点形式 -->
        <!-- <id value="e92edde5-0153-4ec8-9cbb-b4d3f415aa33"/>
        <id value="af043384-c8a5-451e-88f4-457914e8e3bc"/> -->

        <!-- 通过IP地址或者端口或者IP地址+端口进行屏蔽。适用于IP地址确定的场景 -->
        <!-- 单个Address形式。如果多个用“;”分隔，不允许出现空格 -->
        <address value="192.168.43.101:1201;192.168.*.102;1301"/>

        <!-- 多个Address节点形式 -->
        <!-- <address value="192.168.43.101:1201"/>
        <address value="192.168.*.102"/>
        <address value="1301"/> -->
    </strategy-blacklist>

    <!-- 参数控制，由远程推送参数的改变，实现一些特色化的蓝绿发布，例如，基于数据库和消息队列的蓝绿发布 -->
    <parameter>
        <!-- 服务a在版本为1.0的时候，数据库的数据源指向db1；服务a在版本为1.1的时候，数据库的数据源指向db2 -->
        <!-- 服务b在区域为dev的时候，消息队列指向queue1；服务b在区域为dev的时候，消息队列指向queue2 -->
        <!-- 服务c在环境为env1的时候，数据库的数据源指向db1；服务c在环境为env2的时候，数据库的数据源指向db2 -->
        <!-- 服务d在可用区为zone1的时候，消息队列指向queue1；服务d在可用区为zone2的时候，消息队列指向queue2 -->
        <!-- 服务c在IP地址和端口为192.168.43.101:1201的时候，数据库的数据源指向db1；服务c在IP地址和端口为192.168.43.102:1201的时候，数据库的数据源指向db2 -->
        <service service-name="discovery-springcloud-example-a" tag-key="version" tag-value="1.0" key="ShardingSphere" value="db1"/>
        <service service-name="discovery-springcloud-example-a" tag-key="version" tag-value="1.1" key="ShardingSphere" value="db2"/>
        <service service-name="discovery-springcloud-example-b" tag-key="region" tag-value="dev" key="RocketMQ" value="queue1"/>
        <service service-name="discovery-springcloud-example-b" tag-key="region" tag-value="qa" key="RocketMQ" value="queue2"/>
        <service service-name="discovery-springcloud-example-c" tag-key="env" tag-value="env1" key="ShardingSphere" value="db1"/>
        <service service-name="discovery-springcloud-example-c" tag-key="env" tag-value="env2" key="ShardingSphere" value="db2"/>
        <service service-name="discovery-springcloud-example-d" tag-key="zone" tag-value="zone1" key="RocketMQ" value="queue1"/>
        <service service-name="discovery-springcloud-example-d" tag-key="zone" tag-value="zone2" key="RocketMQ" value="queue2"/>
        <service service-name="discovery-springcloud-example-e" tag-key="address" tag-value="192.168.43.101:1201" key="ShardingSphere" value="db1"/>
        <service service-name="discovery-springcloud-example-e" tag-key="address" tag-value="192.168.43.102:1201" key="ShardingSphere" value="db2"/>
    </parameter>
</rule>
```

## 规则策略推送

### 基于远程配置中心的规则策略订阅推送
Apollo订阅推送界面

![](http://nepxion.gitee.io/docs/discovery-doc/Apollo1.jpg)

① 参考Apollo官方文档[https://github.com/ctripcorp/apollo](https://github.com/ctripcorp/apollo)相关文档，搭建Apollo环境，以及熟悉相关的基本操作

② 根据上图，做如下步骤操作

- 设置页面中AppId和配置文件里面app.id一致
- 设置页面中Namespace和配置文件里面apollo.plugin.namespace一致，如果配置文件里不设置，那么页面默认采用内置的application
- 在页面中添加配置
    - 局部配置方式：一个服务集群（eureka.instance.metadataMap.group和spring.application.name都相同的服务）对应一个配置文件，通过group+serviceId方式添加，Key为group-serviceId，Value为Xml或者Json格式的规则策略内容。group取值于配置文件里的eureka.instance.metadataMap.group配置项，serviceId取值于spring.application.name配置项目
    - 全局配置方式：一组服务集群（eureka.instance.metadataMap.group相同，但spring.application.name可以不相同的服务）对应一个配置文件，通过group方式添加，Key为group-group，Value为Xml或者Json格式的规则内容。group取值于配置文件里的eureka.instance.metadataMap.group配置项
- 其他更多参数，例如evn, cluster等，请自行参考Apollo官方文档，保持一致

③ 需要注意

- 局部配置方式建议使用Apollo的私有（private）配置方式，全局配置方式必须采用Apollo的共享（public）配置方式
- 如果业务配置和蓝绿灰度配置在同一个namespace里且namespace只有一个，蓝绿灰度配置可以通过apollo.bootstrap.namespaces或者apollo.plugin.namespace来指定（如果namespace为application则都不需要配置）
- 如果业务配置和蓝绿灰度配置不在同一个namespace里或者业务配置横跨几个namespace，蓝绿灰度配置必须通过apollo.plugin.namespace来指定唯一的namespace

Nacos订阅推送界面

![](http://nepxion.gitee.io/docs/discovery-doc/Nacos2.jpg)
- 参考Nacos官方文档[https://github.com/alibaba/nacos](https://github.com/alibaba/nacos)相关文档，搭建Nacos环境，以及熟悉相关的基本操作

- 添加配置步骤跟Apollo配置界面中的【在页面中添加配置】操作项相似

Redis订阅推送界面

![](http://nepxion.gitee.io/docs/discovery-doc/Redis.jpg)

Consul订阅推送界面

![](http://nepxion.gitee.io/docs/discovery-doc/Consul.jpg)

Zookeeper订阅推送界面

略

Etcd订阅推送界面

略

### 基于Swagger和Rest的规则策略推送
服务侧单个推送界面

![](http://nepxion.gitee.io/docs/discovery-doc/Swagger1.jpg)

控制平台批量推送界面

![](http://nepxion.gitee.io/docs/discovery-doc/Swagger2.jpg)

### 基于图形化桌面端和Web端的规则策略推送

参考[全链路蓝绿灰度发布编排建模和流量侦测](#全链路蓝绿灰度发布编排建模和流量侦测)

## 全链路环境隔离和路由
基于服务实例的元数据Metadata的env参数和全链路传递的环境Header值进行对比实现隔离，当从网关传递来的环境Header（n-d-env）值和提供端实例的元数据Metadata环境配置值相等才能调用。环境隔离下，调用端实例找不到符合条件的提供端实例，把流量路由到一个通用或者备份环境

![](http://nepxion.gitee.io/docs/discovery-doc/IsolationEnvironment.jpg)

### 全链路环境隔离
在网关或者服务端，配置环境元数据，在同一套环境下，env值必须是一样的，这样才能达到在同一个注册中心下，环境隔离的目的
```
spring.cloud.nacos.discovery.metadata.env=env1
```

### 全链路环境路由
在环境隔离执行的时候，如果无法找到对应的环境，则会路由到一个通用或者备份环境，默认为env为common的环境，可以通过如下参数进行更改
```
# 流量路由到指定的环境下。不允许为保留值default，缺失则默认为common
spring.application.strategy.environment.route=common
```

![](http://nepxion.gitee.io/docs/icon-doc/warning.png) 需要注意

- 如果存在环境，优先寻址环境的服务实例
- 如果不存在环境，则寻址Common环境的服务实例（未设置元数据Metadata的env参数的服务实例也归为Common环境）
- 如果Common环境也不存在，则调用失败
- 如果没有传递环境Header（n-d-env）值，则执行Spring Cloud Ribbon轮询策略
- 环境隔离和路由适用于测试环境，性能压测等场景

## 全链路可用区亲和性隔离和路由

![](http://nepxion.gitee.io/docs/discovery-doc/IsolationZone.jpg)

### 全链路可用区亲和性隔离
基于调用端实例和提供端实例的元数据Metadata的zone配置值进行对比实现隔离
```
spring.cloud.nacos.discovery.metadata.zone=zone
```
通过如下开关进行开启和关闭
```
# 启动和关闭可用区亲和性，即同一个可用区的服务才能调用，同一个可用区的条件是调用端实例和提供端实例的元数据Metadata的zone配置值必须相等。缺失则默认为false
spring.application.strategy.zone.affinity.enabled=false
```

### 全链路可用区亲和性路由
在可用区亲和性隔离执行的时候，调用端实例找不到同一可用区的提供端实例，把流量路由到其它可用区或者不归属任何可用区

通过如下开关进行开启和关闭
```
# 启动和关闭可用区亲和性失败后的路由，即调用端实例没有找到同一个可用区的提供端实例的时候，当开关打开，可路由到其它可用区或者不归属任何可用区，当开关关闭，则直接调用失败。缺失则默认为true
spring.application.strategy.zone.route.enabled=true
```

![](http://nepxion.gitee.io/docs/icon-doc/warning.png) 需要注意

- 不归属任何可用区，含义是服务实例未设置任何zone元数据值。可用区亲和性路由功能，是为了尽量保证流量不损失
- 如果采用Eureka注册中心，Ribbon本身就具有可用区亲和性功能，跟本框架类似。如果使用者采用了Eureka注册中心下的Ribbon可用区亲和性功能，请关闭本框架提供的相似功能，以免冲突
- 本框架提供的可用区亲和性功能适用于一切注册中心

## 全链路服务隔离和准入

### 消费端服务隔离

#### 基于组负载均衡隔离
元数据中的Group在一定意义上代表着系统ID或者系统逻辑分组，基于Group策略意味着只有同一个系统中的服务才能调用

基于Group是否相同的策略，即消费端拿到的提供端列表，两者的Group必须相同。只需要在网关或者服务端，开启如下配置即可
```
# 启动和关闭消费端的服务隔离（基于Group是否相同的策略）。缺失则默认为false
spring.application.strategy.consumer.isolation.enabled=true
```
通过修改discovery-guide-service-b的Group名为其它名称，执行Postman调用，将发现从discovery-guide-service-a无法拿到discovery-guide-service-b的任何实例。意味着在discovery-guide-service-a消费端进行了隔离

### 提供端服务隔离

#### 基于组Header传值策略隔离
元数据中的Group在一定意义上代表着系统ID或者系统逻辑分组，基于Group策略意味着只有同一个系统中的服务才能调用

基于Group是否相同的策略，即服务端被消费端调用，两者的Group必须相同，否则拒绝调用，异构系统可以通过Header方式传递n-d-service-group值进行匹配。只需要在服务端（不适用网关），开启如下配置即可
```
# 启动和关闭提供端的服务隔离（基于Group是否相同的策略）。缺失则默认为false
spring.application.strategy.provider.isolation.enabled=true

# 路由策略的时候，需要指定对业务RestController类的扫描路径。此项配置作用于RPC方式的调用拦截和消费端的服务隔离两项工作
spring.application.strategy.scan.packages=com.nepxion.discovery.guide.service.feign
```

在Postman调用，执行[http://localhost:4001/invoke/abc](http://localhost:4001/invoke/abc)，去调用discovery-guide-service-b服务，将出现如下异常。意味着在discovery-guide-service-b提供端进行了隔离
```
Reject to invoke because of isolation with different service group
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide6-1.jpg)

如果加上n-d-service-group=discovery-guide-group的Header，那么两者保持Group相同，则调用通过。这是解决异构系统调用微服务被隔离的一种手段

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide6-2.jpg)

### 注册发现隔离和准入

#### 基于IP地址黑白名单注册准入
微服务启动的时候，禁止指定的IP地址注册到注册中心。支持黑/白名单，白名单表示只允许指定IP地址前缀注册，黑名单表示不允许指定IP地址前缀注册
- 全局过滤，指注册到服务注册发现中心的所有微服务，只有IP地址包含在全局过滤字段的前缀中，都允许注册（对于白名单而言），或者不允许注册（对于黑名单而言）
- 局部过滤，指专门针对某个微服务而言，那么真正的过滤条件是全局过滤 + 局部过滤结合在一起

#### 基于最大注册数限制注册准入
微服务启动的时候，一旦微服务集群下注册的实例数目已经达到上限（可配置），将禁止后续的微服务进行注册
- 全局配置值，只下面配置所有的微服务集群，最多能注册多少个
- 局部配置值，指专门针对某个微服务而言，如果该值如存在，全局配置值失效

#### 基于IP地址黑白名单发现准入
微服务启动的时候，禁止指定的IP地址被服务发现。它使用的方式和[基于IP地址黑白名单注册准入](#基于IP地址黑白名单注册准入)一致

#### 自定义注册发现准入
- 集成AbstractRegisterListener，实现自定义禁止注册
- 集成AbstractDiscoveryListener，实现自定义禁止被发现。需要注意，在Consul下，同时会触发service和management两个实例的事件，需要区别判断
- 集成AbstractLoadBalanceListener，实现自定义禁止被负载均衡

## 全链路服务限流熔断降级权限

集成Sentinel熔断隔离限流降级平台

![](http://nepxion.gitee.io/docs/discovery-doc/Sentinel1.jpg)
![](http://nepxion.gitee.io/docs/discovery-doc/Sentinel2.jpg)
![](http://nepxion.gitee.io/docs/discovery-doc/Sentinel3.jpg)
![](http://nepxion.gitee.io/docs/discovery-doc/Sentinel4.jpg)

通过集成Sentinel，在服务端实现该功能

![](http://nepxion.gitee.io/docs/icon-doc/information.png) 由于该块功能早于Spring Cloud Alibaba Sentinel而产生，下述功能可以通过Spring Cloud Alibaba Sentinel功能来实现

封装NacosDataSource和ApolloDataSource，支持Nacos和Apollo两个远程配置中心，零代码实现Sentinel功能。更多的远程配置中心，请参照Sentinel官方的DataSource并自行集成

- Nacos的Key格式

```
Group为元数据中配置的[组名]，Data Id为[服务名]-[规则类型]
```

- Apollo的Key格式

```
[组名]-[服务名]-[规则类型]
```

支持远程配置中心和本地规则文件的读取逻辑，即优先读取远程配置，如果不存在或者规则错误，则读取本地规则文件。动态实现远程配置中心对于规则的热刷新

支持如下开关开启该动能，默认是关闭的
```
# 启动和关闭Sentinel限流降级熔断权限等原生功能的数据来源扩展。缺失则默认为false
spring.application.strategy.sentinel.enabled=true
```

### 原生Sentinel注解
参照下面代码，为接口方法增加@SentinelResource注解，value为sentinel-resource，blockHandler和fallback是防护其作用后需要执行的方法

```java
@RestController
@ConditionalOnProperty(name = DiscoveryConstant.SPRING_APPLICATION_NAME, havingValue = "discovery-guide-service-b")
public class BFeignImpl extends AbstractFeignImpl implements BFeign {
    private static final Logger LOG = LoggerFactory.getLogger(BFeignImpl.class);

    @Override
    @SentinelResource(value = "sentinel-resource", blockHandler = "handleBlock", fallback = "handleFallback")
    public String invoke(@PathVariable(value = "value") String value) {
        value = doInvoke(value);

        LOG.info("调用路径：{}", value);

        return value;
    }

    public String handleBlock(String value, BlockException e) {
        return value + "-> B server sentinel block, cause=" + e.getClass().getName() + ", rule=" + e.getRule() + ", limitApp=" + e.getRuleLimitApp();
    }

    public String handleFallback(String value) {
        return value + "-> B server sentinel fallback";
    }
}
```

### 原生Sentinel规则
原生Sentinel规则的用法，请参照Sentinel官方文档

#### 流控规则
增加服务discovery-guide-service-b的规则，Group为discovery-guide-group，Data Id为discovery-guide-service-b-sentinel-flow，规则内容如下
```
[
    {
        "resource": "sentinel-resource",
        "limitApp": "default",
        "grade": 1,
        "count": 1,
        "strategy": 0,
        "refResource": null,
        "controlBehavior": 0,
        "warmUpPeriodSec": 10,
        "maxQueueingTimeMs": 500,
        "clusterMode": false,
        "clusterConfig": null
    }
]
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide7-1.jpg)

#### 降级规则
增加服务discovery-guide-service-b的规则，Group为discovery-guide-group，Data Id为discovery-guide-service-b-sentinel-degrade，规则内容如下
```
[
    {
        "resource": "sentinel-resource",
        "limitApp": "default",
        "count": 2,
        "timeWindow": 10,
        "grade": 0,
        "passCount": 0
    }
]
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide7-2.jpg)

#### 授权规则
增加服务discovery-guide-service-b的规则，Group为discovery-guide-group，Data Id为discovery-guide-service-b-sentinel-authority，规则内容如下
```
[
    {
        "resource": "sentinel-resource",
        "limitApp": "discovery-guide-service-a",
        "strategy": 0
    }
]
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide7-3.jpg)

#### 系统规则
增加服务discovery-guide-service-b的规则，Group为discovery-guide-group，Data Id为discovery-guide-service-b-sentinel-system，规则内容如下
```
[
    {
        "resource": null,
        "limitApp": null,
        "highestSystemLoad": -1.0,
        "highestCpuUsage": -1.0,
        "qps": 200.0,
        "avgRt": -1,
        "maxThread": -1
    }
]
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide7-4.jpg)

#### 热点参数流控规则
增加服务discovery-guide-service-b的规则，Group为discovery-guide-group，Data Id为discovery-guide-service-b-sentinel-param-flow，规则内容如下
```
[
    {
        "resource": "sentinel-resource",
        "limitApp": "default",
        "grade": 1,
        "paramIdx": 0,
        "count": 1,
        "controlBehavior": 0,
        "maxQueueingTimeMs": 0,
        "burstCount": 0,
        "durationInSec": 1,
        "paramFlowItemList": [],
        "clusterMode": false
    }
]
```
![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide7-5.jpg)

### 基于Sentinel-LimitApp扩展的防护
该功能对于上面5种规则都有效，这里以授权规则展开阐述

授权规则中，limitApp，如果有多个，可以通过“,”分隔。"strategy": 0 表示白名单，"strategy": 1 表示黑名单

支持如下开关开启该动能，默认是关闭的
```
# 启动和关闭Sentinel LimitApp限流等功能。缺失则默认为false
spring.application.strategy.service.sentinel.limit.app.enabled=true
```

#### 基于服务名的防护
修改配置项Sentinel Request Origin Key为服务名Header，修改授权规则中limitApp为对应的服务名，可实现基于服务名的防护

配置项，该配置项默认为n-d-service-id，可以不配置
```
spring.application.strategy.service.sentinel.request.origin.key=n-d-service-id
```

增加服务discovery-guide-service-b的规则，Group为discovery-guide-group，Data Id为discovery-guide-service-b-sentinel-authority，规则内容如下，表示所有discovery-guide-service-a服务允许访问discovery-guide-service-b服务
```
[
    {
        "resource": "sentinel-resource",
        "limitApp": "discovery-guide-service-a",
        "strategy": 0
    }
]
```

#### 基于组的防护
修改配置项Sentinel Request Origin Key为组Header，修改授权规则中limitApp为对应的组名，可实现基于组的防护

配置项
```
spring.application.strategy.service.sentinel.request.origin.key=n-d-service-group
```

增加服务discovery-guide-service-b的规则，Group为discovery-guide-group，Data Id为discovery-guide-service-b-sentinel-authority，规则内容如下，表示隶属my-group组的所有服务都允许访问服务discovery-guide-service-b
```
[
    {
        "resource": "sentinel-resource",
        "limitApp": "my-group",
        "strategy": 0
    }
]
```

#### 基于版本的防护
修改配置项Sentinel Request Origin Key为版本Header，修改授权规则中limitApp为对应的版本，可实现基于版本的防护机制

配置项
```
spring.application.strategy.service.sentinel.request.origin.key=n-d-service-version
```

增加服务discovery-guide-service-b的规则，Group为discovery-guide-group，Data Id为discovery-guide-service-b-sentinel-authority，规则内容如下，表示版本为1.0的所有服务都允许访问服务discovery-guide-service-b
```
[
    {
        "resource": "sentinel-resource",
        "limitApp": "1.0",
        "strategy": 0
    }
]
```

#### 基于区域的防护
修改配置项Sentinel Request Origin Key为区域Header，修改授权规则中limitApp为对应的区域，可实现基于区域的防护

配置项
```
spring.application.strategy.service.sentinel.request.origin.key=n-d-service-region
```

增加服务discovery-guide-service-b的规则，Group为discovery-guide-group，Data Id为discovery-guide-service-b-sentinel-authority，规则内容如下，表示区域为dev的所有服务都允许访问服务discovery-guide-service-b
```
[
    {
        "resource": "sentinel-resource",
        "limitApp": "dev",
        "strategy": 0
    }
]
```

#### 基于环境的防护
修改配置项Sentinel Request Origin Key为环境Header，修改授权规则中limitApp为对应的环境，可实现基于环境的防护

配置项
```
spring.application.strategy.service.sentinel.request.origin.key=n-d-service-env
```

增加服务discovery-guide-service-b的规则，Group为discovery-guide-group，Data Id为discovery-guide-service-b-sentinel-authority，规则内容如下，表示环境为env1的所有服务都允许访问服务discovery-guide-service-b
```
[
    {
        "resource": "sentinel-resource",
        "limitApp": "env1",
        "strategy": 0
    }
]
```

#### 基于可用区的防护
修改配置项Sentinel Request Origin Key为可用区Header，修改授权规则中limitApp为对应的可用区，可实现基于可用区的防护

配置项
```
spring.application.strategy.service.sentinel.request.origin.key=n-d-service-zone
```

增加服务discovery-guide-service-b的规则，Group为discovery-guide-group，Data Id为discovery-guide-service-b-sentinel-authority，规则内容如下，表示可用区为zone1的所有服务都允许访问服务discovery-guide-service-b
```
[
    {
        "resource": "sentinel-resource",
        "limitApp": "zone1",
        "strategy": 0
    }
]
```

#### 基于IP地址和端口的防护
修改配置项Sentinel Request Origin Key为IP地址和端口Header，修改授权规则中limitApp为对应的区域值，可实现基于IP地址和端口的防护

配置项
```
spring.application.strategy.service.sentinel.request.origin.key=n-d-service-address
```

增加服务discovery-guide-service-b的规则，Group为discovery-guide-group，Data Id为discovery-guide-service-b-sentinel-authority，规则内容如下，表示地址和端口为192.168.0.88:8081和192.168.0.88:8082的服务都允许访问服务discovery-guide-service-b
```
[
    {
        "resource": "sentinel-resource",
        "limitApp": "192.168.0.88:8081,192.168.0.88:8082",
        "strategy": 0
    }
]
```

#### 自定义组合式的防护
通过适配类实现自定义组合式的防护，支持自定义Header、Parameter、Cookie参数的防护，自定义业务参数的防护，以及自定义前两者组合式的防护
```java
// 自定义版本号+地域名，实现组合式熔断
public class MyServiceSentinelRequestOriginAdapter extends DefaultServiceSentinelRequestOriginAdapter {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        String version = request.getHeader(DiscoveryConstant.N_D_SERVICE_VERSION);
        String location = request.getHeader("location");

        return version + "&" + location;
    }
}
```
在配置类里@Bean方式进行适配类创建
```java
@Bean
public ServiceSentinelRequestOriginAdapter ServiceSentinelRequestOriginAdapter() {
    return new MyServiceSentinelRequestOriginAdapter();
}
```

增加服务discovery-guide-service-b的规则，Group为discovery-guide-group，Data Id为discovery-guide-service-b-sentinel-authority，规则内容如下，表示版本为1.0且传入Header的location=shanghai，同时满足这两个条件下的所有服务都允许访问服务discovery-guide-service-b
```
[
    {
        "resource": "sentinel-resource",
        "limitApp": "1.0&shanghai",
        "strategy": 0
    }
]
```

运行效果

- 当传递的Header中location=shanghai，当全链路调用中，API网关负载均衡discovery-guide-service-a服务到1.0版本后再去调用discovery-guide-service-b服务，最终调用成功

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide7-6.jpg)

- 当传递的Header中location=beijing，不满足条件，最终调用在discovery-guide-service-b服务端被拒绝掉

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide7-7.jpg)

- 当传递的Header中location=shanghai，满足条件之一，当全链路调用中，API网关负载均衡discovery-guide-service-a服务到1.1版本后再去调用discovery-guide-service-b服务，不满足version=1.0的条件，最终调用在discovery-guide-service-b服务端被拒绝掉

![](http://nepxion.gitee.io/docs/discovery-doc/DiscoveryGuide7-8.jpg)

## 全链路监控

### 全链路调用链监控

#### 蓝绿灰度埋点调用链监控
① 内置蓝绿灰度埋点

内置蓝绿灰度埋点，包括如下
```
1. n-d-service-group - 服务所属组或者应用
2. n-d-service-type - 服务类型，分为网关端 | 服务端 | 控制台端 | 测试端，使用者只需要关注前两个即可
3. n-d-service-id - 服务ID
4. n-d-service-address - 服务地址，包括Host和Port
5. n-d-service-version - 服务版本
6. n-d-service-region - 服务所属区域
7. n-d-service-env - 服务所属环境
8. n-d-version - 版本路由值
9. n-d-region - 区域路由值
10. n-d-env - 环境路由值
11. n-d-address - 地址路由值
12. n-d-version-weight - 版本权重路由值
13. n-d-region-weight - 区域权重路由值
14. n-d-id-blacklist - 全局唯一ID屏蔽值
15. n-d-address-blacklist - IP地址和端口屏蔽值
```
- n-d-service开头的埋点代表是服务自身的属性
- n-d-开头的埋点是蓝绿灰度传递的策略路由值

② 外置自定义埋点

用户可以自定义外置埋点
- 自定义要传递的调用链参数，例如：traceId，spanId等
- 自定义要传递的业务参数，例如：mobile，user等

③ 跟调用链中间件集成

- 集成OpenTracing + Jaeger蓝绿灰度全链路监控

![](http://nepxion.gitee.io/docs/discovery-doc/Jaeger2.jpg)
![](http://nepxion.gitee.io/docs/discovery-doc/Jaeger3.jpg)
![](http://nepxion.gitee.io/docs/discovery-doc/JaegerPremium1.jpg)

- 集成OpenTracing + SkyWalking蓝绿灰度全链路监控

![](http://nepxion.gitee.io/docs/discovery-doc/SkyWalking1.jpg)
![](http://nepxion.gitee.io/docs/discovery-doc/SkyWalking2.jpg)

#### 蓝绿灰度埋点Debug辅助监控
Debug辅助监控只是通过普通的System.out.println方式输出，便于开发人员在IDE上调试，在生产环境下不建议开启

对于Debug辅助监控功能的开启和关闭，需要通过如下开关做控制
```
# 启动和关闭Header传递的Debug日志打印，注意：每调用一次都会打印一次，会对性能有所影响，建议压测环境和生产环境关闭。缺失则默认为false
spring.application.strategy.rest.intercept.debug.enabled=true
# 启动和关闭Debug日志打印，注意：每调用一次都会打印一次，会对性能有所影响，建议压测环境和生产环境关闭。缺失则默认为false
spring.application.strategy.logger.debug.enabled=true
```

① 网关端和服务端自身蓝绿灰度埋点Debug辅助监控
```
------------------ Logger Debug ------------------
trace-id=dade3982ae65e9e1
span-id=997e31021e9fce20
n-d-service-group=discovery-guide-group
n-d-service-type=service
n-d-service-id=discovery-guide-service-a
n-d-service-address=172.27.208.1:3001
n-d-service-version=1.0
n-d-service-region=dev
n-d-service-env=env1
n-d-service-zone=zone1
n-d-version={"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.0"}
mobile=13812345678
user=
--------------------------------------------------
```

② 服务端Feign或者RestTemplate拦截输入的蓝绿灰度埋点Debug辅助监控
```
------- Feign Intercept Input Header Information -------
n-d-service-group=discovery-guide-group
n-d-version={"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.0"}
n-d-service-type=gateway
n-d-service-id=discovery-guide-zuul
n-d-service-env=default
mobile=13812345678
n-d-service-region=default
n-d-service-zone=default
n-d-service-address=172.27.208.1:5002
n-d-service-version=1.0
--------------------------------------------------
```

③ 服务端Feign或者RestTemplate拦截输出的蓝绿灰度埋点Debug辅助监控
```
------- Feign Intercept Output Header Information ------
mobile=[13812345678]
n-d-service-address=[172.27.208.1:3001]
n-d-service-env=[env1]
n-d-service-group=[discovery-guide-group]
n-d-service-id=[discovery-guide-service-a]
n-d-service-region=[dev]
n-d-service-type=[service]
n-d-service-version=[1.0]
n-d-service-zone=[zone1]
n-d-version=[{"discovery-guide-service-a":"1.0", "discovery-guide-service-b":"1.0"}]
--------------------------------------------------
```

#### Sentinel熔断埋点调用链监控

- 集成OpenTracing + Jaeger + Sentinel限流熔断降级权限埋点全链路监控

![](http://nepxion.gitee.io/docs/discovery-doc/Jaeger6.jpg)

- 集成OpenTracing + SkyWalking + Sentinel限流熔断降级权限埋点全链路监控

![](http://nepxion.gitee.io/docs/discovery-doc/SkyWalking3.jpg)
![](http://nepxion.gitee.io/docs/discovery-doc/SkyWalking4.jpg)

#### 自定义埋点调用链监控
① 自定义调用链上下文参数的创建，继承DefaultStrategyTracerAdapter

```java
// 自定义调用链上下文参数的创建
// 对于getTraceId和getSpanId方法，在OpenTracing等调用链中间件引入的情况下，由调用链中间件决定，在这里定义不会起作用；在OpenTracing等调用链中间件未引入的情况下，在这里定义才有效，下面代码中表示从Http Header中获取，并全链路传递
// 对于getCustomizationMap方法，表示输出到调用链中的定制化业务参数，可以同时输出到日志和OpenTracing等调用链中间件，下面代码中表示从Http Header中获取，并全链路传递
public class MyStrategyTracerAdapter extends DefaultStrategyTracerAdapter {
    @Override
    public String getTraceId() {
        return StringUtils.isNotEmpty(strategyContextHolder.getHeader(DiscoveryConstant.TRACE_ID)) ? strategyContextHolder.getHeader(DiscoveryConstant.TRACE_ID) : StringUtils.EMPTY;
    }

    @Override
    public String getSpanId() {
        return StringUtils.isNotEmpty(strategyContextHolder.getHeader(DiscoveryConstant.SPAN_ID)) ? strategyContextHolder.getHeader(DiscoveryConstant.SPAN_ID) : StringUtils.EMPTY;
    }

    @Override
    public Map<String, String> getCustomizationMap() {
        Map<String, String> customizationMap = new HashMap<String, String>();
        customizationMap.put("mobile", StringUtils.isNotEmpty(strategyContextHolder.getHeader("mobile")) ? strategyContextHolder.getHeader("mobile") : StringUtils.EMPTY);
        customizationMap.put("user", StringUtils.isNotEmpty(strategyContextHolder.getHeader("user")) ? strategyContextHolder.getHeader("user") : StringUtils.EMPTY);

        return customizationMap;
    }
}
```
在配置类里@Bean方式进行调用链类创建，覆盖框架内置的调用链适配器
```java
@Bean
public StrategyTracerAdapter strategyTracerAdapter() {
    return new MyStrategyTracerAdapter();
}
```

② 自定义类方法上入参和出参输出到调用链，继承ServiceStrategyMonitorAdapter
```java
// 自定义类方法上入参和出参输出到调用链
// parameterMap格式：
// key为入参名
// value为入参值
public class MyServiceStrategyMonitorAdapter implements ServiceStrategyMonitorAdapter {
    @Override
    public Map<String, String> getCustomizationMap(ServiceStrategyMonitorInterceptor interceptor, MethodInvocation invocation, Map<String, Object> parameterMap, Object returnValue) {
        Map<String, String> customizationMap = new HashMap<String, String>();
        customizationMap.put(DiscoveryConstant.PARAMETER, parameterMap.toString());
        customizationMap.put(DiscoveryConstant.RETURN, returnValue != null ? returnValue.toString() : null);

        return customizationMap;
    }
}
```
在配置类里@Bean方式进行创建
```java
@Bean
public ServiceStrategyMonitorAdapter serviceStrategyMonitorAdapter() {
    return new MyServiceStrategyMonitorAdapter();
}
```

③ 业务方法上获取TraceId和SpanId
```java
public class MyClass {
    @Autowired
    private StrategyMonitorContext strategyMonitorContext;

    public void doXXX() {
        String traceId = strategyMonitorContext.getTraceId();
        String spanId = strategyMonitorContext.getSpanId();
        ...
    }
}
```

对于全链路监控功能的开启和关闭，需要通过如下开关做控制
```
# 启动和关闭监控，一旦关闭，调用链和日志输出都将关闭。缺失则默认为false
spring.application.strategy.monitor.enabled=true
# 启动和关闭日志输出。缺失则默认为false
spring.application.strategy.logger.enabled=true
# 日志输出中，是否显示MDC前面的Key。缺失则默认为true
spring.application.strategy.logger.mdc.key.shown=true
# 启动和关闭Debug日志打印，注意：每调用一次都会打印一次，会对性能有所影响，建议压测环境和生产环境关闭。缺失则默认为false
spring.application.strategy.logger.debug.enabled=true
# 启动和关闭调用链输出。缺失则默认为false
spring.application.strategy.tracer.enabled=true
# 启动和关闭调用链的蓝绿灰度信息以独立的Span节点输出，如果关闭，则蓝绿灰度信息输出到原生的Span节点中（SkyWalking不支持原生模式）。缺失则默认为true
spring.application.strategy.tracer.separate.span.enabled=true
# 启动和关闭调用链的蓝绿灰度规则策略信息输出。缺失则默认为true
spring.application.strategy.tracer.rule.output.enabled=true
# 启动和关闭调用链的异常信息是否以详细格式输出。缺失则默认为false
spring.application.strategy.tracer.exception.detail.output.enabled=true
# 启动和关闭类方法上入参和出参输出到调用链。缺失则默认为false
spring.application.strategy.tracer.method.context.output.enabled=true
# 显示在调用链界面上蓝绿灰度Span的名称，建议改成具有公司特色的框架产品名称。缺失则默认为NEPXION
spring.application.strategy.tracer.span.value=NEPXION
# 显示在调用链界面上蓝绿灰度Span Tag的插件名称，建议改成具有公司特色的框架产品的描述。缺失则默认为Nepxion Discovery
spring.application.strategy.tracer.span.tag.plugin.value=Nepxion Discovery
# 启动和关闭Sentinel调用链上规则在Span上的输出，注意：原生的Sentinel不是Spring技术栈，下面参数必须通过-D方式或者System.setProperty方式等设置进去。缺失则默认为true
spring.application.strategy.tracer.sentinel.rule.output.enabled=true
# 启动和关闭Sentinel调用链上方法入参在Span上的输出，注意：原生的Sentinel不是Spring技术栈，下面参数必须通过-D方式或者System.setProperty方式等设置进去。缺失则默认为false
spring.application.strategy.tracer.sentinel.args.output.enabled=true
```

![](http://nepxion.gitee.io/docs/icon-doc/warning.png) 需要注意，OpenTracing对Finchley版的Spring Cloud Gateway的reactor-core包存在版本兼容性问题，如果使用者希望Finchley版的Spring Cloud Gateway上使用OpenTracing，需要做如下改造
```xml
<dependency>
    <groupId>com.nepxion</groupId>
    <artifactId>discovery-plugin-strategy-starter-gateway</artifactId>
    <version>${discovery.version}</version>
    <exclusions>
        <exclusion>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>io.projectreactor</groupId>
    <artifactId>reactor-core</artifactId>
    <version>3.2.3.RELEASE</version>
</dependency>
```
上述方式也适用于其它引入了低版本reactor-core包版本兼容性的场景

### 全链路日志监控

#### 蓝绿灰度埋点日志监控

蓝绿灰度埋点日志输出，需要使用者配置logback.xml或者log4j.xml日志格式，参考如下
```xml
<!-- Logback configuration. See http://logback.qos.ch/manual/index.html -->
<configuration scan="true" scanPeriod="10 seconds">
    <!-- Simple file output -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- encoder defaults to ch.qos.logback.classic.encoder.PatternLayoutEncoder -->
        <encoder>
            <pattern>discovery %date %level [%thread] [%X{trace-id}] [%X{span-id}] [%X{n-d-service-group}] [%X{n-d-service-type}] [%X{n-d-service-app-id}] [%X{n-d-service-id}] [%X{n-d-service-address}] [%X{n-d-service-version}] [%X{n-d-service-region}] [%X{n-d-service-env}] [%X{n-d-service-zone}] [%X{mobile}] [%X{user}] %logger{10} [%file:%line] - %msg%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <fileNamePattern>log/discovery-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <maxFileSize>50MB</maxFileSize>
        </rollingPolicy>
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>INFO</level>
        </filter>
        <!-- Safely log to the same file from multiple JVMs. Degrades performance! -->
        <prudent>true</prudent>
    </appender>

    <appender name="FILE_ASYNC" class="ch.qos.logback.classic.AsyncAppender">
        <discardingThreshold>0</discardingThreshold>
        <queueSize>512</queueSize>
        <appender-ref ref="FILE" />
    </appender>

    <!-- Console output -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <!-- encoder defaults to ch.qos.logback.classic.encoder.PatternLayoutEncoder -->
        <encoder>
            <pattern>discovery %date %level [%thread] [%X{trace-id}] [%X{span-id}] [%X{n-d-service-group}] [%X{n-d-service-type}] [%X{n-d-service-app-id}] [%X{n-d-service-id}] [%X{n-d-service-address}] [%X{n-d-service-version}] [%X{n-d-service-region}] [%X{n-d-service-env}] [%X{n-d-service-zone}] [%X{mobile}] [%X{user}] %logger{10} [%file:%line] - %msg%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <!-- Only log level WARN and above -->
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>INFO</level>
        </filter>
    </appender>

    <!-- For loggers in the these namespaces, log at all levels. -->
    <logger name="pedestal" level="ALL" />
    <logger name="hammock-cafe" level="ALL" />
    <logger name="user" level="ALL" />

    <root level="INFO">
        <!-- <appender-ref ref="FILE_ASYNC" /> -->
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```

![](http://nepxion.gitee.io/docs/discovery-doc/Tracer.jpg)

### 全链路指标监控

#### Prometheus监控
![](http://nepxion.gitee.io/docs/discovery-doc/Prometheus.jpg)

#### Grafana监控
![](http://nepxion.gitee.io/docs/discovery-doc/Grafana.jpg)

#### Spring-Boot-Admin监控
![](http://nepxion.gitee.io/docs/discovery-doc/Admin1.jpg)
![](http://nepxion.gitee.io/docs/discovery-doc/Admin4.jpg)
![](http://nepxion.gitee.io/docs/discovery-doc/Admin5.jpg)
![](http://nepxion.gitee.io/docs/discovery-doc/Admin7.jpg)

## 全链路服务侧注解
服务侧对于RPC方式的调用拦截、消费端的服务隔离和调用链三项功能，默认映射到RestController类（含有@RestController注解），并配合如下的扫描路径才能工作
```
# 路由策略的时候，需要指定对业务RestController类的扫描路径。此项配置作用于RPC方式的调用拦截、消费端的服务隔离和调用链三项功能
spring.application.strategy.scan.packages=com.nepxion.discovery.guide.service.feign
```
当使用者不希望只局限于RestController类（含有@RestController注解）方式，而要求在任何类中实现上述功能，那么框架提供@ServiceStrategy注解，使用者把它加在类头部即可，可以达到和@RestController注解同样的效果

## 全链路服务侧API权限
服务侧对于RPC方式的调用，可以加入API权限控制，通过在接口或者类名上加@Permission注解，或者在接口或者类的方法名上加@Permission注解，实现API权限控制。如果两者都加，以前者为优先
- 实现权限自动扫描入库
- 实现提供显式基于注解的权限验证，参数通过注解传递；实现提供基于Rest请求的权限验证，参数通过Header传递
- 实现提供入库方法和权限判断方法的扩展，这两者需要自行实现

请参考[权限代码](https://github.com/Nepxion/DiscoveryGuide/blob/master/discovery-guide-service/src/main/java/com/nepxion/discovery/guide/service/permission)

## 元数据流量染色

### 基于Git插件自动创建版本号
通过集成插件git-commit-id-plugin，通过产生git信息文件的方式，获取git.commit.id（最后一次代码的提交ID）或者git.build.version（对应到Maven工程的版本）来自动创建版本号，这样就可以避免使用者手工维护版本号。当两者都启用的时候，Git插件方式的版本号优先级要高于手工配置的版本号

- 增加Git编译插件

需要在4个工程下的pom.xml里增加git-commit-id-plugin

默认配置
```xml
<plugin>
    <groupId>pl.project13.maven</groupId>
    <artifactId>git-commit-id-plugin</artifactId>
    <executions>
        <execution>
            <goals>
                <goal>revision</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <!-- 必须配置，并指定为true -->
        <generateGitPropertiesFile>true</generateGitPropertiesFile>
        <!-- 指定日期格式 -->
        <dateFormat>yyyyMMdd</dateFormat>
        <!-- <dateFormat>yyyy-MM-dd-HH:mm:ss</dateFormat> -->
    </configuration>
</plugin>
```

特色配置
```xml
<plugin>
    <groupId>pl.project13.maven</groupId>
    <artifactId>git-commit-id-plugin</artifactId>
    <executions>
        <execution>
            <goals>
                <goal>revision</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <!-- 指定git信息文件是否生成。缺失则默认为false -->
        <generateGitPropertiesFile>true</generateGitPropertiesFile>
        <!-- 指定.git文件夹路径。缺失则默认为${project.basedir}/.git -->
        <dotGitDirectory>${project.basedir}/.git</dotGitDirectory>
        <!-- 指定git信息文件的输出路径 -->
        <generateGitPropertiesFilename>${project.build.outputDirectory}/git.json</generateGitPropertiesFilename>
        <!-- <generateGitPropertiesFilename>${project.basedir}/git.json</generateGitPropertiesFilename> -->
        <!-- 指定git信息文件的输出格式。缺失则默认为properties -->
        <format>json</format>
        <!-- 指定当.git文件夹未找到时，构建是否失败。若设置true，则构建失败，若设置false，则跳过执行该构建步骤。缺失则默认为true -->
        <failOnNoGitDirectory>true</failOnNoGitDirectory>
        <!-- 指定当项目打包类型为pom时，是否取消构建。缺失则默认为true -->
        <skipPoms>false</skipPoms>
        <!-- 指定构建过程中，是否打印详细信息。缺失则默认为false -->
        <verbose>false</verbose>
        <dateFormat>yyyyMMdd</dateFormat>
        <!-- <dateFormat>yyyy-MM-dd-HH:mm:ss</dateFormat> -->
    </configuration>
</plugin>
```

更多的配置方式，参考[https://github.com/git-commit-id/maven-git-commit-id-plugin/blob/master/maven/docs/using-the-plugin.md](https://github.com/git-commit-id/maven-git-commit-id-plugin/blob/master/maven/docs/using-the-plugin.md)

可能需要增加下面的配置，保证Git相关文件被打包进去
```xml
<resources>
    <resource>
        <directory>src/main/java</directory>
        <includes>
            <include>**/*.xml</include>
            <include>**/*.json</include>
            <include>**/*.properties</include>
        </includes>
    </resource>
    <resource>
        <directory>src/main/resources</directory>
        <includes>
            <include>**/*.xml</include>
            <include>**/*.json</include>
            <include>**/*.properties</include>
        </includes>
    </resource>
</resources>
```

- 增加配置项

```
# 开启和关闭使用Git信息中的字段单个或者多个组合来作为服务版本号。缺失则默认为false
spring.application.git.generator.enabled=true
# 插件git-commit-id-plugin产生git信息文件的输出路径，支持properties和json两种格式，支持classpath:xxx和file:xxx两种路径，这些需要和插件里的配置保持一致。缺失则默认为classpath:git.properties
spring.application.git.generator.path=classpath:git.properties
# spring.application.git.generator.path=classpath:git.json
# 使用Git信息中的字段单个或者多个组合来作为服务版本号。缺失则默认为{git.commit.time}-{git.total.commit.count}
spring.application.git.version.key={git.commit.id.abbrev}-{git.commit.time}
# spring.application.git.version.key={git.build.version}-{git.commit.time}
```

下面是可供选择的Git字段，比较实际意义的字段为git.commit.id，git.commit.id.abbrev，git.build.version，git.total.commit.count
```
git.branch=master
git.build.host=Nepxion
git.build.time=2019-10-21-10\:07\:41
git.build.user.email=1394997@qq.com
git.build.user.name=Nepxion
git.build.version=1.0.0
git.closest.tag.commit.count=
git.closest.tag.name=
git.commit.id=04d7e45b11b975db37bdcdbc5a97c02e9d80e5fa
git.commit.id.abbrev=04d7e45
git.commit.id.describe=04d7e45-dirty
git.commit.id.describe-short=04d7e45-dirty
git.commit.message.full=\u4FEE\u6539\u914D\u7F6E
git.commit.message.short=\u4FEE\u6539\u914D\u7F6E
git.commit.time=2019-10-21T09\:09\:25+0800
git.commit.user.email=1394997@qq.com
git.commit.user.name=Nepxion
git.dirty=true
git.local.branch.ahead=0
git.local.branch.behind=0
git.remote.origin.url=https\://github.com/Nepxion/DiscoveryGuide.git
git.tags=
git.total.commit.count=765
```

![](http://nepxion.gitee.io/docs/icon-doc/warning.png) 需要注意，一般情况下，上述两个地方的配置都同时保持默认即可。对于一些特殊的用法，两个地方的配置项用法必须保持一致，例如
```
# 输出到工程根目录下
<generateGitPropertiesFilename>${project.basedir}/git.json</generateGitPropertiesFilename>
# 输出成json格式
<format>json</format>
```
下面配置项必须上面两个配置项的操作逻辑相同
```
# 输出到工程根目录下的json格式文件
spring.application.git.generator.path=file:git.json
```

内置基于Swagger的Rest接口，可以供外部查询当前服务的Git信息

### 基于服务名前缀自动创建组名
通过指定长度截断或者标志截断服务名的前缀来自动创建组名，这样就可以避免使用者手工维护组名。当两者都启用的时候，截断方式的组名优先级要高于手工配置的组名

- 增加配置项

```
# 开启和关闭使用服务名前缀来作为服务组名。缺失则默认为false
spring.application.group.generator.enabled=true
# 服务名前缀的截断长度，必须大于0
spring.application.group.generator.length=15
# 服务名前缀的截断标志。当截断长度配置了，则取截断长度方式，否则取截断标志方式
spring.application.group.generator.character=-
```

### 基于运维平台运行参数自动创建版本号
运维平台在启动微服务的时候，可以通过参数方式初始化元数据，框架会自动把它注册到远程注册中心。有如下两种方式
- 通过VM arguments来传递，它的用法是前面加-D。支持上述所有的注册组件，它的限制是变量前面必须要加metadata.，推荐使用该方式。例如：-Dmetadata.version=1.0
- 通过Program arguments来传递，它的用法是前面加--。支持Eureka、Zookeeper和Nacos的增量覆盖，Consul由于使用了全量覆盖的tag方式，不适用改变单个元数据的方式。例如：--spring.cloud.nacos.discovery.metadata.version=1.0
- 两种方式尽量避免同时用

### 基于用户自定义创建版本号
参考[流量染色配置](#流量染色配置)

## 配置文件

### 流量染色配置
```
# Eureka config for discovery
eureka.instance.metadataMap.group=xxx-service-group
eureka.instance.metadataMap.version=1.0
eureka.instance.metadataMap.region=dev
eureka.instance.metadataMap.env=env1
eureka.instance.metadataMap.zone=zone1

# Consul config for discovery
# Spring Cloud 2020之前版本的配置方式
# 参考https://springcloud.cc/spring-cloud-consul.html - 元数据和Consul标签
spring.cloud.consul.discovery.tags=group=xxx-service-group,version=1.0,region=dev,env=env1,zone=zone1

# Spring Cloud 2020之后版本的配置方式
spring.cloud.consul.discovery.metadata.group=xxx-service-group
spring.cloud.consul.discovery.metadata.version=1.0
spring.cloud.consul.discovery.metadata.region=dev
spring.cloud.consul.discovery.metadata.env=env1
spring.cloud.consul.discovery.metadata.zone=zone1

# Zookeeper config for discovery
spring.cloud.zookeeper.discovery.metadata.group=xxx-service-group
spring.cloud.zookeeper.discovery.metadata.version=1.0
spring.cloud.zookeeper.discovery.metadata.region=dev
spring.cloud.zookeeper.discovery.metadata.env=env1
spring.cloud.zookeeper.discovery.metadata.zone=zone1

# Nacos config for discovery
spring.cloud.nacos.discovery.metadata.group=xxx-service-group
spring.cloud.nacos.discovery.metadata.version=1.0
spring.cloud.nacos.discovery.metadata.region=dev
spring.cloud.nacos.discovery.metadata.env=env1
spring.cloud.nacos.discovery.metadata.zone=zone1
```

### 中间件属性配置
① 注册中心配置

- Nacos注册中心配置

```
# Nacos config for discovery
spring.cloud.nacos.discovery.server-addr=localhost:8848
# spring.cloud.nacos.discovery.namespace=discovery
```

- Eureka注册中心配置

```
# Eureka config for discovery
eureka.client.serviceUrl.defaultZone=http://localhost:9528/eureka/
eureka.instance.preferIpAddress=true
```

- Consul注册中心配置

```
# Consul config for discovery
spring.cloud.consul.host=localhost
spring.cloud.consul.port=8500
spring.cloud.consul.discovery.preferIpAddress=true
```

- Zookeeper注册中心配置

```
# Zookeeper config for discovery
spring.cloud.zookeeper.connectString=localhost:2181
spring.cloud.zookeeper.discovery.instancePort=${server.port}
spring.cloud.zookeeper.discovery.root=/spring-cloud
spring.cloud.zookeeper.discovery.preferIpAddress=true
```

② 配置中心配置

蓝绿灰度发布专用配置

- Apollo配置中心配置

```
# Apollo config for rule
app.id=discovery
apollo.meta=http://localhost:8080
# apollo.plugin.namespace=application
```

- Nacos配置中心配置

```
# Nacos config for rule
nacos.server-addr=localhost:8848
# nacos.access-key=
# nacos.secret-key=
# nacos.plugin.namespace=application
# nacos.plugin.cluster-name=
# nacos.plugin.context-path=
# nacos.plugin.config-long-poll-timeout=
# nacos.plugin.config-retry-time=
# nacos.plugin.max-retry=
# nacos.plugin.endpoint=
# nacos.plugin.endpoint-port=
# nacos.plugin.is-use-endpoint-parsing-rule=
# nacos.plugin.is-use-cloud-namespace-parsing=
# nacos.plugin.encode=
# nacos.plugin.naming-load-cache-at-start=
# nacos.plugin.naming-client-beat-thread-count=
# nacos.plugin.naming-polling-thread-count=
# nacos.plugin.naming-request-domain-max-retry-count=
# nacos.plugin.naming-push-empty-protection=
# nacos.plugin.ram-role-name=
# nacos.plugin.timout=
```

- Redis配置中心配置

```
# Redis config for rule
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=
spring.redis.database=0
```

- Zookeeper配置中心配置

```
# Zookeeper config for rule
zookeeper.connect-string=localhost:2181
zookeeper.retry-count=3
zookeeper.sleep-time=3000
```

- Consul配置中心配置

```
# Consul config for rule
consul.host=localhost
consul.port=8500
consul.timeout=1
consul.token=
```

- Etcd配置中心配置

```
# Etcd config for rule
etcd.server.addr=http://localhost:2379
etcd.username=
etcd.password=
```

③ 监控中心配置

- OpenTracing + Jaeger监控中心配置

```
# OpenTracing config for jaeger
opentracing.jaeger.enabled=true
opentracing.jaeger.http-sender.url=http://localhost:14268/api/traces
```

- SkyWalking监控中心配置

```
-javaagent:C:/opt/skywalking-agent/skywalking-agent.jar -Dskywalking.agent.service_name=discovery-guide-service-a
```

- Spring Boot Admin监控中心配置

```
# Spring boot admin config
spring.boot.admin.client.instance.prefer-ip=true
spring.boot.admin.client.url=http://localhost:9728
```

④ 异步跨线程Agent配置

```
-javaagent:C:/opt/discovery-agent/discovery-agent-starter-${discovery.agent.version}.jar -Dthread.scan.packages=reactor.core.publisher;org.springframework.aop.interceptor;com.netflix.hystrix;com.nepxion.discovery.guide.service.feign
```

### 功能开关配置
① 服务端配置
```
# Plugin core config
# 开启和关闭服务注册层面的控制。一旦关闭，服务注册的黑/白名单过滤功能将失效，最大注册数的限制过滤功能将失效。缺失则默认为true
spring.application.register.control.enabled=true
# 开启和关闭服务发现层面的控制。一旦关闭，服务多版本调用的控制功能将失效，动态屏蔽指定IP地址的服务实例被发现的功能将失效。缺失则默认为true
spring.application.discovery.control.enabled=true
# 开启和关闭通过Rest方式对规则配置的控制和推送。一旦关闭，只能通过远程配置中心来控制和推送。缺失则默认为true
spring.application.config.rest.control.enabled=true
# 规则文件的格式，支持xml和json。缺失则默认为xml
spring.application.config.format=xml
# spring.application.config.format=json
# 本地规则文件的路径，支持两种方式：classpath:rule.xml（rule.json） - 规则文件放在resources目录下，便于打包进jar；file:rule.xml（rule.json） - 规则文件放在工程根目录下，放置在外部便于修改。缺失则默认为不装载本地规则
spring.application.config.path=classpath:rule.xml
# spring.application.config.path=classpath:rule.json
# 为微服务归类的Key，一般通过group字段来归类，例如eureka.instance.metadataMap.group=xxx-group或者eureka.instance.metadataMap.application=xxx-application。缺失则默认为group
spring.application.group.key=group
# spring.application.group.key=application
# 业务系统希望大多数时候Spring、SpringBoot或者SpringCloud的基本配置、调优参数（非业务系统配置参数），不配置在业务端，集成到基础框架里。但特殊情况下，业务系统有时候也希望能把基础框架里配置的参数给覆盖掉，用他们自己的配置
# 对于此类型的配置需求，可以配置在下面的配置文件里。该文件一般放在resource目录下。缺失则默认为spring-application-default.properties
spring.application.default.properties.path=spring-application-default.properties
# 负载均衡下，消费端尝试获取对应提供端初始服务实例列表为空的时候，进行重试。缺失则默认为false
spring.application.no.servers.retry.enabled=false
# 负载均衡下，消费端尝试获取对应提供端初始服务实例列表为空的时候，进行重试的次数。缺失则默认为5
spring.application.no.servers.retry.times=5
# 负载均衡下，消费端尝试获取对应提供端初始服务实例列表为空的时候，进行重试的时间间隔。缺失则默认为2000
spring.application.no.servers.retry.await.time=2000
# 负载均衡下，消费端尝试获取对应提供端服务实例列表为空的时候，通过日志方式通知。缺失则默认为false
spring.application.no.servers.notify.enabled=false

# Plugin strategy config
# 开启和关闭路由策略的控制。一旦关闭，路由策略功能将失效。缺失则默认为true
spring.application.strategy.control.enabled=true
# 开启和关闭Ribbon默认的ZoneAvoidanceRule负载均衡策略。一旦关闭，则使用RoundRobin简单轮询负载均衡策略。缺失则默认为true
spring.application.strategy.zone.avoidance.rule.enabled=true
# 启动和关闭路由策略的时候，对REST方式的调用拦截。缺失则默认为true
spring.application.strategy.rest.intercept.enabled=true
# 启动和关闭路由策略的时候，对REST方式在异步调用场景下在服务端的Request请求的装饰，当主线程先于子线程执行完的时候，Request会被Destory，导致Header仍旧拿不到，开启装饰，就可以确保拿到。缺失则默认为true
spring.application.strategy.rest.request.decorator.enabled=true
# 启动和关闭Header传递的Debug日志打印，注意：每调用一次都会打印一次，会对性能有所影响，建议压测环境和生产环境关闭。缺失则默认为false
spring.application.strategy.rest.intercept.debug.enabled=true
# 当外界传值Header的时候，服务也设置并传递同名的Header，需要决定哪个Header传递到后边的服务去，该开关依赖前置过滤器的开关。如果下面开关为true，以服务设置为优先，否则以外界传值为优先。缺失则默认为true
spring.application.strategy.service.header.priority=true
# 启动和关闭Feign上核心策略Header传递，缺失则默认为true。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能。核心策略Header，包含如下
# 1. n-d-version
# 2. n-d-region
# 3. n-d-address
# 4. n-d-version-weight
# 5. n-d-region-weight
# 6. n-d-id-blacklist
# 7. n-d-address-blacklist
# 8. n-d-env (不属于蓝绿灰度范畴的Header，只要外部传入就会全程传递)
spring.application.strategy.feign.core.header.transmission.enabled=true
# 启动和关闭RestTemplate上核心策略Header传递，缺失则默认为true。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能。核心策略Header，包含如下
# 1. n-d-version
# 2. n-d-region
# 3. n-d-address
# 4. n-d-version-weight
# 5. n-d-region-weight
# 6. n-d-id-blacklist
# 7. n-d-address-blacklist
# 8. n-d-env (不属于蓝绿灰度范畴的Header，只要外部传入就会全程传递)
spring.application.strategy.rest.template.core.header.transmission.enabled=true
# 路由策略的时候，对REST方式调用拦截的时候（支持Feign或者RestTemplate调用），希望把来自外部自定义的Header参数（用于框架内置上下文Header，例如：trace-id, span-id等）传递到服务里，那么配置如下值。如果多个用“;”分隔，不允许出现空格
spring.application.strategy.context.request.headers=trace-id;span-id
# 路由策略的时候，对REST方式调用拦截的时候（支持Feign或者RestTemplate调用），希望把来自外部自定义的Header参数（用于业务系统自定义Header，例如：mobile）传递到服务里，那么配置如下值。如果多个用“;”分隔，不允许出现空格
spring.application.strategy.business.request.headers=token
# 路由策略的时候，执行请求过滤，对指定包含的URI字段进行排除。缺失则默认为/actuator/，如果多个用“;”分隔，不允许出现空格
spring.application.strategy.uri.filter.exclusion=/actuator/
# 启动和关闭路由策略的时候，对RPC方式的调用拦截。缺失则默认为false
spring.application.strategy.rpc.intercept.enabled=true
# 路由策略的时候，需要指定对业务RestController类的扫描路径。此项配置作用于RPC方式的调用拦截、消费端的服务隔离和调用链三项功能
spring.application.strategy.scan.packages=com.nepxion.discovery.plugin.example.service.feign
# 启动和关闭消费端的服务隔离（基于Group是否相同的策略）。缺失则默认为false
spring.application.strategy.consumer.isolation.enabled=true
# 启动和关闭提供端的服务隔离（基于Group是否相同的策略）。缺失则默认为false
spring.application.strategy.provider.isolation.enabled=true

# 启动和关闭监控，一旦关闭，调用链和日志输出都将关闭。缺失则默认为false
spring.application.strategy.monitor.enabled=true
# 启动和关闭日志输出。缺失则默认为false
spring.application.strategy.logger.enabled=true
# 日志输出中，是否显示MDC前面的Key。缺失则默认为true
spring.application.strategy.logger.mdc.key.shown=true
# 启动和关闭Debug日志打印，注意：每调用一次都会打印一次，会对性能有所影响，建议压测环境和生产环境关闭。缺失则默认为false
spring.application.strategy.logger.debug.enabled=true
# 启动和关闭调用链输出。缺失则默认为false
spring.application.strategy.tracer.enabled=true
# 启动和关闭调用链的蓝绿灰度信息以独立的Span节点输出，如果关闭，则蓝绿灰度信息输出到原生的Span节点中（SkyWalking不支持原生模式）。缺失则默认为true
spring.application.strategy.tracer.separate.span.enabled=true
# 启动和关闭调用链的蓝绿灰度规则策略信息输出。缺失则默认为true
spring.application.strategy.tracer.rule.output.enabled=true
# 启动和关闭调用链的异常信息是否以详细格式输出。缺失则默认为false
spring.application.strategy.tracer.exception.detail.output.enabled=true
# 启动和关闭类方法上入参和出参输出到调用链。缺失则默认为false
spring.application.strategy.tracer.method.context.output.enabled=true
# 显示在调用链界面上蓝绿灰度Span的名称，建议改成具有公司特色的框架产品名称。缺失则默认为NEPXION
spring.application.strategy.tracer.span.value=NEPXION
# 显示在调用链界面上蓝绿灰度Span Tag的插件名称，建议改成具有公司特色的框架产品的描述。缺失则默认为Nepxion Discovery
spring.application.strategy.tracer.span.tag.plugin.value=Nepxion Discovery
# 启动和关闭Sentinel调用链上规则在Span上的输出，注意：原生的Sentinel不是Spring技术栈，下面参数必须通过-D方式或者System.setProperty方式等设置进去。缺失则默认为true
spring.application.strategy.tracer.sentinel.rule.output.enabled=true
# 启动和关闭Sentinel调用链上方法入参在Span上的输出，注意：原生的Sentinel不是Spring技术栈，下面参数必须通过-D方式或者System.setProperty方式等设置进去。缺失则默认为false
spring.application.strategy.tracer.sentinel.args.output.enabled=true

# 开启服务端实现Hystrix线程隔离模式做服务隔离时，必须把spring.application.strategy.hystrix.threadlocal.supported设置为true，同时要引入discovery-plugin-strategy-starter-hystrix包，否则线程切换时会发生ThreadLocal上下文对象丢失。缺失则默认为false
spring.application.strategy.hystrix.threadlocal.supported=true

# 启动和关闭Sentinel限流降级熔断权限等原生功能的数据来源扩展。缺失则默认为false
spring.application.strategy.sentinel.enabled=true
# 流控规则文件路径。缺失则默认为classpath:sentinel-flow.json
spring.application.strategy.sentinel.flow.path=classpath:sentinel-flow.json
# 降级规则文件路径。缺失则默认为classpath:sentinel-degrade.json
spring.application.strategy.sentinel.degrade.path=classpath:sentinel-degrade.json
# 授权规则文件路径。缺失则默认为classpath:sentinel-authority.json
spring.application.strategy.sentinel.authority.path=classpath:sentinel-authority.json
# 系统规则文件路径。缺失则默认为classpath:sentinel-system.json
spring.application.strategy.sentinel.system.path=classpath:sentinel-system.json
# 热点参数流控规则文件路径。缺失则默认为classpath:sentinel-param-flow.json
spring.application.strategy.sentinel.param.flow.path=classpath:sentinel-param-flow.json
# 服务端执行规则时候，以Http请求中的Header值作为关键Key。缺失则默认为n-d-service-id，即以服务名作为关键Key
spring.application.strategy.service.sentinel.request.origin.key=n-d-service-id
# 启动和关闭Sentinel LimitApp限流等功能。缺失则默认为false
spring.application.strategy.service.sentinel.limit.app.enabled=true

# 流量路由到指定的环境下。不允许为保留值default，缺失则默认为common
spring.application.strategy.environment.route=common

# 启动和关闭可用区亲和性，即同一个可用区的服务才能调用，同一个可用区的条件是调用端实例和提供端实例的元数据Metadata的zone配置值必须相等。缺失则默认为false
spring.application.strategy.zone.affinity.enabled=true
# 启动和关闭可用区亲和性失败后的路由，即调用端实例没有找到同一个可用区的提供端实例的时候，当开关打开，可路由到其它可用区或者不归属任何可用区，当开关关闭，则直接调用失败。缺失则默认为true
spring.application.strategy.zone.route.enabled=true

# 版本故障转移，即无法找到相应版本的服务实例，路由到老的稳定版本的实例。其作用是防止蓝绿灰度版本发布人为设置错误，或者对应的版本实例发生灾难性的全部下线，导致流量有损
# 启动和关闭版本故障转移。缺失则默认为false
spring.application.strategy.version.failover.enabled=true
# 版本偏好，即非蓝绿灰度发布场景下，路由到老的稳定版本的实例。其作用是防止多个网关上并行实施蓝绿灰度版本发布产生混乱，对处于非蓝绿灰度状态的服务，调用它的时候，只取它的老的稳定版本的实例；蓝绿灰度状态的服务，还是根据传递的Header版本号进行匹配
# 启动和关闭版本偏好。缺失则默认为false
spring.application.strategy.version.prefer.enabled=true

# 启动和关闭在服务启动的时候参数订阅事件发送。缺失则默认为true
spring.application.parameter.event.onstart.enabled=true

# 开启和关闭使用服务名前缀来作为服务组名。缺失则默认为false
spring.application.group.generator.enabled=true
# 服务名前缀的截断长度，必须大于0
spring.application.group.generator.length=15
# 服务名前缀的截断标志。当截断长度配置了，则取截断长度方式，否则取截断标志方式
spring.application.group.generator.character=-

# 开启和关闭使用Git信息中的字段单个或者多个组合来作为服务版本号。缺失则默认为false
spring.application.git.generator.enabled=true
# 插件git-commit-id-plugin产生git信息文件的输出路径，支持properties和json两种格式，支持classpath:xxx和file:xxx两种路径，这些需要和插件里的配置保持一致。缺失则默认为classpath:git.properties
spring.application.git.generator.path=classpath:git.properties
# spring.application.git.generator.path=classpath:git.json
# 使用Git信息中的字段单个或者多个组合来作为服务版本号。缺失则默认为{git.commit.time}-{git.total.commit.count}
spring.application.git.version.key={git.commit.id.abbrev}-{git.commit.time}
# spring.application.git.version.key={git.build.version}-{git.commit.time}
```

② Spring Cloud Gateway端配置
```
# Plugin core config
# 开启和关闭服务注册层面的控制。一旦关闭，服务注册的黑/白名单过滤功能将失效，最大注册数的限制过滤功能将失效。缺失则默认为true
spring.application.register.control.enabled=true
# 开启和关闭服务发现层面的控制。一旦关闭，服务多版本调用的控制功能将失效，动态屏蔽指定IP地址的服务实例被发现的功能将失效。缺失则默认为true
spring.application.discovery.control.enabled=true
# 开启和关闭通过Rest方式对规则配置的控制和推送。一旦关闭，只能通过远程配置中心来控制和推送。缺失则默认为true
spring.application.config.rest.control.enabled=true
# 规则文件的格式，支持xml和json。缺失则默认为xml
spring.application.config.format=xml
# spring.application.config.format=json
# 本地规则文件的路径，支持两种方式：classpath:rule.xml（rule.json） - 规则文件放在resources目录下，便于打包进jar；file:rule.xml（rule.json） - 规则文件放在工程根目录下，放置在外部便于修改。缺失则默认为不装载本地规则
spring.application.config.path=classpath:rule.xml
# spring.application.config.path=classpath:rule.json
# 为微服务归类的Key，一般通过group字段来归类，例如eureka.instance.metadataMap.group=xxx-group或者eureka.instance.metadataMap.application=xxx-application。缺失则默认为group
spring.application.group.key=group
# spring.application.group.key=application
# 业务系统希望大多数时候Spring、SpringBoot或者SpringCloud的基本配置、调优参数（非业务系统配置参数），不配置在业务端，集成到基础框架里。但特殊情况下，业务系统有时候也希望能把基础框架里配置的参数给覆盖掉，用他们自己的配置
# 对于此类型的配置需求，可以配置在下面的配置文件里。该文件一般放在resource目录下。缺失则默认为spring-application-default.properties
spring.application.default.properties.path=spring-application-default.properties
# 负载均衡下，消费端尝试获取对应提供端初始服务实例列表为空的时候，进行重试。缺失则默认为false
spring.application.no.servers.retry.enabled=false
# 负载均衡下，消费端尝试获取对应提供端初始服务实例列表为空的时候，进行重试的次数。缺失则默认为5
spring.application.no.servers.retry.times=5
# 负载均衡下，消费端尝试获取对应提供端初始服务实例列表为空的时候，进行重试的时间间隔。缺失则默认为2000
spring.application.no.servers.retry.await.time=2000
# 负载均衡下，消费端尝试获取对应提供端服务实例列表为空的时候，通过日志方式通知。缺失则默认为false
spring.application.no.servers.notify.enabled=false

# Plugin strategy config
# 开启和关闭路由策略的控制。一旦关闭，路由策略功能将失效。缺失则默认为true
spring.application.strategy.control.enabled=true
# 开启和关闭Ribbon默认的ZoneAvoidanceRule负载均衡策略。一旦关闭，则使用RoundRobin简单轮询负载均衡策略。缺失则默认为true
spring.application.strategy.zone.avoidance.rule.enabled=true
# 路由策略过滤器的执行顺序（Order）。缺失则默认为9000
spring.application.strategy.gateway.route.filter.order=9000
# 当外界传值Header的时候，网关也设置并传递同名的Header，需要决定哪个Header传递到后边的服务去。如果下面开关为true，以网关设置为优先，否则以外界传值为优先。缺失则默认为true
spring.application.strategy.gateway.header.priority=false
# 当以网关设置为优先的时候，网关未配置Header，而外界配置了Header，仍旧忽略外界的Header。缺失则默认为true
spring.application.strategy.gateway.original.header.ignored=true
# 启动和关闭网关上核心策略Header传递，缺失则默认为true。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能。核心策略Header，包含如下
# 1. n-d-version
# 2. n-d-region
# 3. n-d-address
# 4. n-d-version-weight
# 5. n-d-region-weight
# 6. n-d-id-blacklist
# 7. n-d-address-blacklist
# 8. n-d-env (不属于蓝绿灰度范畴的Header，只要外部传入就会全程传递)
spring.application.strategy.gateway.core.header.transmission.enabled=true
# 启动和关闭消费端的服务隔离（基于Group是否相同的策略）。缺失则默认为false
spring.application.strategy.consumer.isolation.enabled=true

# 启动和关闭监控，一旦关闭，调用链和日志输出都将关闭。缺失则默认为false
spring.application.strategy.monitor.enabled=true
# 启动和关闭日志输出。缺失则默认为false
spring.application.strategy.logger.enabled=true
# 日志输出中，是否显示MDC前面的Key。缺失则默认为true
spring.application.strategy.logger.mdc.key.shown=true
# 启动和关闭Debug日志打印，注意：每调用一次都会打印一次，会对性能有所影响，建议压测环境和生产环境关闭。缺失则默认为false
spring.application.strategy.logger.debug.enabled=true
# 启动和关闭调用链输出。缺失则默认为false
spring.application.strategy.tracer.enabled=true
# 启动和关闭调用链的蓝绿灰度信息以独立的Span节点输出，如果关闭，则蓝绿灰度信息输出到原生的Span节点中（SkyWalking不支持原生模式）。缺失则默认为true
spring.application.strategy.tracer.separate.span.enabled=true
# 启动和关闭调用链的蓝绿灰度规则策略信息输出。缺失则默认为true
spring.application.strategy.tracer.rule.output.enabled=true
# 启动和关闭调用链的异常信息是否以详细格式输出。缺失则默认为false
spring.application.strategy.tracer.exception.detail.output.enabled=true
# 显示在调用链界面上蓝绿灰度Span的名称，建议改成具有公司特色的框架产品名称。缺失则默认为NEPXION
spring.application.strategy.tracer.span.value=NEPXION
# 显示在调用链界面上蓝绿灰度Span Tag的插件名称，建议改成具有公司特色的框架产品的描述。缺失则默认为Nepxion Discovery
spring.application.strategy.tracer.span.tag.plugin.value=Nepxion Discovery
# 启动和关闭Sentinel调用链上规则在Span上的输出，注意：原生的Sentinel不是Spring技术栈，下面参数必须通过-D方式或者System.setProperty方式等设置进去。缺失则默认为true
spring.application.strategy.tracer.sentinel.rule.output.enabled=true
# 启动和关闭Sentinel调用链上方法入参在Span上的输出，注意：原生的Sentinel不是Spring技术栈，下面参数必须通过-D方式或者System.setProperty方式等设置进去。缺失则默认为false
spring.application.strategy.tracer.sentinel.args.output.enabled=true

# 开启Spring Cloud Gateway网关上实现Hystrix线程隔离模式做服务隔离时，必须把spring.application.strategy.hystrix.threadlocal.supported设置为true，同时要引入discovery-plugin-strategy-starter-hystrix包，否则线程切换时会发生ThreadLocal上下文对象丢失。缺失则默认为false
spring.application.strategy.hystrix.threadlocal.supported=true

# 流量路由到指定的环境下。不允许为保留值default，缺失则默认为common
spring.application.strategy.environment.route=common

# 启动和关闭可用区亲和性，即同一个可用区的服务才能调用，同一个可用区的条件是调用端实例和提供端实例的元数据Metadata的zone配置值必须相等。缺失则默认为false
spring.application.strategy.zone.affinity.enabled=true
# 启动和关闭可用区亲和性失败后的路由，即调用端实例没有找到同一个可用区的提供端实例的时候，当开关打开，可路由到其它可用区或者不归属任何可用区，当开关关闭，则直接调用失败。缺失则默认为true
spring.application.strategy.zone.route.enabled=true

# 版本故障转移，即无法找到相应版本的服务实例，路由到老的稳定版本的实例。其作用是防止蓝绿灰度版本发布人为设置错误，或者对应的版本实例发生灾难性的全部下线，导致流量有损
# 启动和关闭版本故障转移。缺失则默认为false
spring.application.strategy.version.failover.enabled=true
# 版本偏好，即非蓝绿灰度发布场景下，路由到老的稳定版本的实例。其作用是防止多个网关上并行实施蓝绿灰度版本发布产生混乱，对处于非蓝绿灰度状态的服务，调用它的时候，只取它的老的稳定版本的实例；蓝绿灰度状态的服务，还是根据传递的Header版本号进行匹配
# 启动和关闭版本偏好。缺失则默认为false
spring.application.strategy.version.prefer.enabled=true

# 启动和关闭在服务启动的时候参数订阅事件发送。缺失则默认为true
spring.application.parameter.event.onstart.enabled=true

# 开启和关闭使用服务名前缀来作为服务组名。缺失则默认为false
spring.application.group.generator.enabled=true
# 服务名前缀的截断长度，必须大于0
spring.application.group.generator.length=15
# 服务名前缀的截断标志。当截断长度配置了，则取截断长度方式，否则取截断标志方式
spring.application.group.generator.character=-

# 开启和关闭使用Git信息中的字段单个或者多个组合来作为服务版本号。缺失则默认为false
spring.application.git.generator.enabled=true
# 插件git-commit-id-plugin产生git信息文件的输出路径，支持properties和json两种格式，支持classpath:xxx和file:xxx两种路径，这些需要和插件里的配置保持一致。缺失则默认为classpath:git.properties
spring.application.git.generator.path=classpath:git.properties
# spring.application.git.generator.path=classpath:git.json
# 使用Git信息中的字段单个或者多个组合来作为服务版本号。缺失则默认为{git.commit.time}-{git.total.commit.count}
spring.application.git.version.key={git.commit.id.abbrev}-{git.commit.time}
# spring.application.git.version.key={git.build.version}-{git.commit.time}

# 下面配置只适用于网关里直接进行Feign或者RestTemplate调用场景
# 启动和关闭路由策略的时候，对REST方式的调用拦截。缺失则默认为true
spring.application.strategy.rest.intercept.enabled=true
# 启动和关闭Header传递的Debug日志打印，注意：每调用一次都会打印一次，会对性能有所影响，建议压测环境和生产环境关闭。缺失则默认为false
spring.application.strategy.rest.intercept.debug.enabled=true
# 启动和关闭Feign上核心策略Header传递，缺失则默认为true。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能。核心策略Header，包含如下
# 1. n-d-version
# 2. n-d-region
# 3. n-d-address
# 4. n-d-version-weight
# 5. n-d-region-weight
# 6. n-d-id-blacklist
# 7. n-d-address-blacklist
# 8. n-d-env (不属于蓝绿灰度范畴的Header，只要外部传入就会全程传递)
# spring.application.strategy.feign.core.header.transmission.enabled=false
# 启动和关闭RestTemplate上核心策略Header传递，缺失则默认为true。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能。核心策略Header，包含如下
# 1. n-d-version
# 2. n-d-region
# 3. n-d-address
# 4. n-d-version-weight
# 5. n-d-region-weight
# 6. n-d-id-blacklist
# 7. n-d-address-blacklist
# 8. n-d-env (不属于蓝绿灰度范畴的Header，只要外部传入就会全程传递)
# spring.application.strategy.rest.template.core.header.transmission.enabled=false
# 路由策略的时候，对REST方式调用拦截的时候（支持Feign或者RestTemplate调用），希望把来自外部自定义的Header参数（用于框架内置上下文Header，例如：trace-id, span-id等）传递到服务里，那么配置如下值。如果多个用“;”分隔，不允许出现空格
spring.application.strategy.context.request.headers=trace-id;span-id
# 路由策略的时候，对REST方式调用拦截的时候（支持Feign或者RestTemplate调用），希望把来自外部自定义的Header参数（用于业务系统自定义Header，例如：mobile）传递到服务里，那么配置如下值。如果多个用“;”分隔，不允许出现空格
spring.application.strategy.business.request.headers=user;mobile;location
```

③ Zuul端配置
```
# Plugin core config
# 开启和关闭服务注册层面的控制。一旦关闭，服务注册的黑/白名单过滤功能将失效，最大注册数的限制过滤功能将失效。缺失则默认为true
spring.application.register.control.enabled=true
# 开启和关闭服务发现层面的控制。一旦关闭，服务多版本调用的控制功能将失效，动态屏蔽指定IP地址的服务实例被发现的功能将失效。缺失则默认为true
spring.application.discovery.control.enabled=true
# 开启和关闭通过Rest方式对规则配置的控制和推送。一旦关闭，只能通过远程配置中心来控制和推送。缺失则默认为true
spring.application.config.rest.control.enabled=true
# 规则文件的格式，支持xml和json。缺失则默认为xml
spring.application.config.format=xml
# spring.application.config.format=json
# 本地规则文件的路径，支持两种方式：classpath:rule.xml（rule.json） - 规则文件放在resources目录下，便于打包进jar；file:rule.xml（rule.json） - 规则文件放在工程根目录下，放置在外部便于修改。缺失则默认为不装载本地规则
spring.application.config.path=classpath:rule.xml
# spring.application.config.path=classpath:rule.json
# 为微服务归类的Key，一般通过group字段来归类，例如eureka.instance.metadataMap.group=xxx-group或者eureka.instance.metadataMap.application=xxx-application。缺失则默认为group
spring.application.group.key=group
# spring.application.group.key=application
# 业务系统希望大多数时候Spring、SpringBoot或者SpringCloud的基本配置、调优参数（非业务系统配置参数），不配置在业务端，集成到基础框架里。但特殊情况下，业务系统有时候也希望能把基础框架里配置的参数给覆盖掉，用他们自己的配置
# 对于此类型的配置需求，可以配置在下面的配置文件里。该文件一般放在resource目录下。缺失则默认为spring-application-default.properties
spring.application.default.properties.path=spring-application-default.properties
# 负载均衡下，消费端尝试获取对应提供端初始服务实例列表为空的时候，进行重试。缺失则默认为false
spring.application.no.servers.retry.enabled=false
# 负载均衡下，消费端尝试获取对应提供端初始服务实例列表为空的时候，进行重试的次数。缺失则默认为5
spring.application.no.servers.retry.times=5
# 负载均衡下，消费端尝试获取对应提供端初始服务实例列表为空的时候，进行重试的时间间隔。缺失则默认为2000
spring.application.no.servers.retry.await.time=2000
# 负载均衡下，消费端尝试获取对应提供端服务实例列表为空的时候，通过日志方式通知。缺失则默认为false
spring.application.no.servers.notify.enabled=false

# Plugin strategy config
# 开启和关闭路由策略的控制。一旦关闭，路由策略功能将失效。缺失则默认为true
spring.application.strategy.control.enabled=true
# 开启和关闭Ribbon默认的ZoneAvoidanceRule负载均衡策略。一旦关闭，则使用RoundRobin简单轮询负载均衡策略。缺失则默认为true
spring.application.strategy.zone.avoidance.rule.enabled=true
# 路由策略过滤器的执行顺序（Order）。缺失则默认为0
spring.application.strategy.zuul.route.filter.order=0
# 当外界传值Header的时候，网关也设置并传递同名的Header，需要决定哪个Header传递到后边的服务去。如果下面开关为true，以网关设置为优先，否则以外界传值为优先。缺失则默认为true
spring.application.strategy.zuul.header.priority=false
# 当以网关设置为优先的时候，网关未配置Header，而外界配置了Header，仍旧忽略外界的Header。缺失则默认为true
spring.application.strategy.zuul.original.header.ignored=true
# 启动和关闭网关上核心策略Header传递，缺失则默认为true。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能。核心策略Header，包含如下
# 1. n-d-version
# 2. n-d-region
# 3. n-d-address
# 4. n-d-version-weight
# 5. n-d-region-weight
# 6. n-d-id-blacklist
# 7. n-d-address-blacklist
# 8. n-d-env (不属于蓝绿灰度范畴的Header，只要外部传入就会全程传递)
spring.application.strategy.zuul.core.header.transmission.enabled=true
# 启动和关闭消费端的服务隔离（基于Group是否相同的策略）。缺失则默认为false
spring.application.strategy.consumer.isolation.enabled=true

# 启动和关闭监控，一旦关闭，调用链和日志输出都将关闭。缺失则默认为false
spring.application.strategy.monitor.enabled=true
# 启动和关闭日志输出。缺失则默认为false
spring.application.strategy.logger.enabled=true
# 日志输出中，是否显示MDC前面的Key。缺失则默认为true
spring.application.strategy.logger.mdc.key.shown=true
# 启动和关闭Debug日志打印，注意：每调用一次都会打印一次，会对性能有所影响，建议压测环境和生产环境关闭。缺失则默认为false
spring.application.strategy.logger.debug.enabled=true
# 启动和关闭调用链输出。缺失则默认为false
spring.application.strategy.tracer.enabled=true
# 启动和关闭调用链的蓝绿灰度信息以独立的Span节点输出，如果关闭，则蓝绿灰度信息输出到原生的Span节点中（SkyWalking不支持原生模式）。缺失则默认为true
spring.application.strategy.tracer.separate.span.enabled=true
# 启动和关闭调用链的蓝绿灰度规则策略信息输出。缺失则默认为true
spring.application.strategy.tracer.rule.output.enabled=true
# 启动和关闭调用链的异常信息是否以详细格式输出。缺失则默认为false
spring.application.strategy.tracer.exception.detail.output.enabled=true
# 显示在调用链界面上蓝绿灰度Span的名称，建议改成具有公司特色的框架产品名称。缺失则默认为NEPXION
spring.application.strategy.tracer.span.value=NEPXION
# 显示在调用链界面上蓝绿灰度Span Tag的插件名称，建议改成具有公司特色的框架产品的描述。缺失则默认为Nepxion Discovery
spring.application.strategy.tracer.span.tag.plugin.value=Nepxion Discovery
# 启动和关闭Sentinel调用链上规则在Span上的输出，注意：原生的Sentinel不是Spring技术栈，下面参数必须通过-D方式或者System.setProperty方式等设置进去。缺失则默认为true
spring.application.strategy.tracer.sentinel.rule.output.enabled=true
# 启动和关闭Sentinel调用链上方法入参在Span上的输出，注意：原生的Sentinel不是Spring技术栈，下面参数必须通过-D方式或者System.setProperty方式等设置进去。缺失则默认为false
spring.application.strategy.tracer.sentinel.args.output.enabled=true

# 开启Zuul网关上实现Hystrix线程隔离模式做服务隔离时，必须把spring.application.strategy.hystrix.threadlocal.supported设置为true，同时要引入discovery-plugin-strategy-starter-hystrix包，否则线程切换时会发生ThreadLocal上下文对象丢失。缺失则默认为false
spring.application.strategy.hystrix.threadlocal.supported=true

# 流量路由到指定的环境下。不允许为保留值default，缺失则默认为common
spring.application.strategy.environment.route=common

# 启动和关闭可用区亲和性，即同一个可用区的服务才能调用，同一个可用区的条件是调用端实例和提供端实例的元数据Metadata的zone配置值必须相等。缺失则默认为false
spring.application.strategy.zone.affinity.enabled=true
# 启动和关闭可用区亲和性失败后的路由，即调用端实例没有找到同一个可用区的提供端实例的时候，当开关打开，可路由到其它可用区或者不归属任何可用区，当开关关闭，则直接调用失败。缺失则默认为true
spring.application.strategy.zone.route.enabled=true

# 版本故障转移，即无法找到相应版本的服务实例，路由到老的稳定版本的实例。其作用是防止蓝绿灰度版本发布人为设置错误，或者对应的版本实例发生灾难性的全部下线，导致流量有损
# 启动和关闭版本故障转移。缺失则默认为false
spring.application.strategy.version.failover.enabled=true
# 版本偏好，即非蓝绿灰度发布场景下，路由到老的稳定版本的实例。其作用是防止多个网关上并行实施蓝绿灰度版本发布产生混乱，对处于非蓝绿灰度状态的服务，调用它的时候，只取它的老的稳定版本的实例；蓝绿灰度状态的服务，还是根据传递的Header版本号进行匹配
# 启动和关闭版本偏好。缺失则默认为false
spring.application.strategy.version.prefer.enabled=true

# 启动和关闭在服务启动的时候参数订阅事件发送。缺失则默认为true
spring.application.parameter.event.onstart.enabled=true

# 开启和关闭使用服务名前缀来作为服务组名。缺失则默认为false
spring.application.group.generator.enabled=true
# 服务名前缀的截断长度，必须大于0
spring.application.group.generator.length=15
# 服务名前缀的截断标志。当截断长度配置了，则取截断长度方式，否则取截断标志方式
spring.application.group.generator.character=-

# 开启和关闭使用Git信息中的字段单个或者多个组合来作为服务版本号。缺失则默认为false
spring.application.git.generator.enabled=true
# 插件git-commit-id-plugin产生git信息文件的输出路径，支持properties和json两种格式，支持classpath:xxx和file:xxx两种路径，这些需要和插件里的配置保持一致。缺失则默认为classpath:git.properties
spring.application.git.generator.path=classpath:git.properties
# spring.application.git.generator.path=classpath:git.json
# 使用Git信息中的字段单个或者多个组合来作为服务版本号。缺失则默认为{git.commit.time}-{git.total.commit.count}
# spring.application.git.version.key={git.commit.id.abbrev}-{git.commit.time}
# spring.application.git.version.key={git.build.version}-{git.commit.time}

# 下面配置只适用于网关里直接进行Feign或者RestTemplate调用场景
# 启动和关闭路由策略的时候，对REST方式的调用拦截。缺失则默认为true
spring.application.strategy.rest.intercept.enabled=true
# 启动和关闭Header传递的Debug日志打印，注意：每调用一次都会打印一次，会对性能有所影响，建议压测环境和生产环境关闭。缺失则默认为false
spring.application.strategy.rest.intercept.debug.enabled=true
# 启动和关闭Feign上核心策略Header传递，缺失则默认为true。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能。核心策略Header，包含如下
# 1. n-d-version
# 2. n-d-region
# 3. n-d-address
# 4. n-d-version-weight
# 5. n-d-region-weight
# 6. n-d-id-blacklist
# 7. n-d-address-blacklist
# 8. n-d-env (不属于蓝绿灰度范畴的Header，只要外部传入就会全程传递)
# spring.application.strategy.feign.core.header.transmission.enabled=false
# 启动和关闭RestTemplate上核心策略Header传递，缺失则默认为true。当全局订阅启动时，可以关闭核心策略Header传递，这样可以节省传递数据的大小，一定程度上可以提升性能。核心策略Header，包含如下
# 1. n-d-version
# 2. n-d-region
# 3. n-d-address
# 4. n-d-version-weight
# 5. n-d-region-weight
# 6. n-d-id-blacklist
# 7. n-d-address-blacklist
# 8. n-d-env (不属于蓝绿灰度范畴的Header，只要外部传入就会全程传递)
# spring.application.strategy.rest.template.core.header.transmission.enabled=false
# 路由策略的时候，对REST方式调用拦截的时候（支持Feign或者RestTemplate调用），希望把来自外部自定义的Header参数（用于框架内置上下文Header，例如：trace-id, span-id等）传递到服务里，那么配置如下值。如果多个用“;”分隔，不允许出现空格
spring.application.strategy.context.request.headers=trace-id;span-id
# 路由策略的时候，对REST方式调用拦截的时候（支持Feign或者RestTemplate调用），希望把来自外部自定义的Header参数（用于业务系统自定义Header，例如：mobile）传递到服务里，那么配置如下值。如果多个用“;”分隔，不允许出现空格
spring.application.strategy.business.request.headers=user;mobile;location
```

### 内置文件配置
框架提供内置文件方式的配置spring-application-default.properties。如果使用者希望对框架做封装，并提供相应的默认配置，可以在src/main/resources目录下放置spring-application-default.properties

![](http://nepxion.gitee.io/docs/icon-doc/warning.png) 需要注意，该文件在整个服务目录和包中只能出现一次

## Docker容器化和Kubernetes平台支持

### Docker容器化
![](http://nepxion.gitee.io/docs/icon-doc/information.png) Spring 2.3.x支持Docker分层部署，步骤也更简单，请参考Polaris【北极星】企业级云原生微服务框架里的介绍

① 搭建Windows10操作系统或者Linux操作系统下的Docker环境

- Windows10环境下，具体步骤参考[Docker安装步骤](https://github.com/Nepxion/Thunder/blob/master/thunder-spring-boot-docker-example/README.md)
- Linux环境请自行研究

② 需要在4个工程下的pom.xml里增加spring-boot-maven-plugin和docker-maven-plugin
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <executable>true</executable>
        <mainClass>com.nepxion.discovery.guide.gateway.DiscoveryGuideGateway</mainClass>
        <layout>JAR</layout>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>repackage</goal>
            </goals>
            <configuration>
                <attach>false</attach>
            </configuration>
        </execution>
    </executions>
</plugin>
<plugin>
    <groupId>com.spotify</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <configuration>
        <imageName>${ImageName}</imageName>
        <baseImage>openjdk:8-jre-alpine</baseImage>
        <entryPoint>["java", "-jar", "/${project.build.finalName}.jar"]</entryPoint>
        <exposes>
            <expose>${ExposePort}</expose>
        </exposes>
        <resources>
            <resource>
                <targetPath>/</targetPath>
                <directory>${project.build.directory}</directory>
                <include>${project.build.finalName}.jar</include>
            </resource>
        </resources>
    </configuration>
</plugin>
```
③ 拷贝discovery-guide-docker目录下的所有脚本文件到根目录下

④ 所有脚本文件下的MIDDLEWARE_HOST=10.0.75.1改成使用者本地物理IP地址（Docker是不能去连接容器外地址为localhost的中间件服务器）

⑤ 全自动部署和运行Docker化的服务。在根目录下

- 一键运行install-docker-gateway.bat或者.sh，把Spring Cloud Gateway网关全自动部署且运行起来
- 一键运行install-docker-zuul.bat或者.sh，把Zuul网关全自动部署且运行起来
- 一键运行install-docker-service-xx.bat或者.sh，把微服务全自动部署且运行起来。需要注意，必须依次运行，即等上一个部署完毕后才能执行下一个
- 一键运行install-docker-console.bat或者.sh，把控制平台全自动部署且运行起来
- 一键运行install-docker-admin.bat或者.sh，把监控平台全自动部署且运行起来	

上述步骤为演示步骤，和DevOps平台结合在一起，更为完美

⑥ Docker运行效果

- Docker Desktop

![](http://nepxion.gitee.io/docs/discovery-doc/Docker.jpg)

- Docker Windows

![](http://nepxion.gitee.io/docs/polaris-doc/DockerWindows.jpg)

- Docker Linux

![](http://nepxion.gitee.io/docs/polaris-doc/DockerLinux.jpg)

### Kubernetes平台支持
请自行研究

## 自动化测试
自动化测试，基于Spring Boot/Spring Cloud的自动化测试框架，包括普通调用测试、蓝绿灰度调用测试和扩展调用测试（例如：支持阿里巴巴的Sentinel，FF4j的功能开关等）。通过注解形式，跟Spring Boot内置的测试机制集成，使用简单方便。该自动化测试框架的现实意义，可以把服务注册发现中心、远程配置中心、负载均衡、蓝绿灰度发布、熔断降级限流、功能开关、Feign或者RestTemplate调用等中间件或者组件，一条龙组合起来进行自动化测试

自动化测试代码参考[指南示例自动化测试](https://github.com/Nepxion/DiscoveryGuide/tree/master/discovery-guide-test-automation)

### 架构设计
通过Matrix Aop框架，实现TestAutoScanProxy和TestInterceptor拦截测试用例，实现配置内容的自动化推送

### 启动控制台
运行[指南示例](https://github.com/Nepxion/DiscoveryGuide)下的DiscoveryGuideConsole.java控制台服务，它是连接服务注册发现中心、远程配置中心和服务的纽带，自动化测试利用控制台实现配置的自动更新和清除

### 配置文件
```
# 自动化测试框架内置配置
# 测试用例类的扫描路径
spring.application.test.scan.packages=com.nepxion.discovery.guide.test
# 测试用例的配置内容推送时，是否打印配置日志。缺失则默认为true
spring.application.test.config.print.enabled=true
# 测试用例的配置内容推送后，等待生效的时间。推送远程配置中心后，再通知各服务更新自身的配置缓存，需要一定的时间，缺失则默认为3000
spring.application.test.config.operation.await.time=5000
# 测试用例的配置内容推送的控制台地址。控制台是连接服务注册发现中心、远程配置中心和服务的纽带
spring.application.test.console.url=http://localhost:6001/

# 业务测试配置
# Spring Cloud Gateway网关配置
gateway.group=discovery-guide-group
gateway.service.id=discovery-guide-gateway
gateway.test.url=http://localhost:5001/discovery-guide-service-a/invoke/gateway

# Zuul网关配置
zuul.group=discovery-guide-group
zuul.service.id=discovery-guide-zuul
zuul.test.url=http://localhost:5002/discovery-guide-service-a/invoke/zuul

# 每个测试用例执行循环次数
testcase.loop.times=1

# 测试用例的灰度权重测试开关。由于权重测试需要大量采样调用，会造成整个自动化测试时间很长，可以通过下面开关开启和关闭。缺失则默认为true
gray.weight.testcases.enabled=true
# 测试用例的灰度权重采样总数。采样总数越大，灰度权重准确率越高，但耗费时间越长
gray.weight.testcase.sample.count=1500
# 测试用例的灰度权重准确率偏离值。采样总数越大，灰度权重准确率偏离值越小
gray.weight.testcase.result.offset=5
```

### 测试用例

![](http://nepxion.gitee.io/docs/icon-doc/warning.png) 需要注意，当使用Eureka注册中心的时候，因为Spring Cloud内嵌了Eureka可用区亲和性功能，会自动开启该策略，则导致某些自动化测试用例失败。需要把所有服务实例的元数据zone值改成相同或者也可以把该行元数据删除，然后进行自动化测试

#### 测试包引入
```xml
<dependencies>
    <dependency>
        <groupId>com.nepxion</groupId>
        <artifactId>discovery-plugin-test-starter</artifactId>
        <version>${discovery.version}</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <compilerArgs>
                    <arg>-parameters</arg>
                </compilerArgs>
                <encoding>${project.build.sourceEncoding}</encoding>
                <source>${java.version}</source>
                <target>${java.version}</target>
            </configuration>
        </plugin>
    </plugins>
</build>
```

![](http://nepxion.gitee.io/docs/icon-doc/warning.png) 需要注意，对于带有注解@DTestConfig的测试用例，要用到Spring的Spel语法格式（即group = "#group", serviceId = "#serviceId"），需要引入Java8的带"-parameters"编译方式，见上面的<compilerArgs>参数设置

在IDE环境里需要设置"-parameters"的Compiler Argument

- Eclipse加"-parameters"参数：https://www.concretepage.com/java/jdk-8/java-8-reflection-access-to-parameter-names-of-method-and-constructor-with-maven-gradle-and-eclipse-using-parameters-compiler-argument
- Idea加"-parameters"参数：http://blog.csdn.net/royal_lr/article/details/52279993

#### 测试入口程序
结合Spring Boot Junit，TestApplication.class为测试框架内置应用启动程序，DiscoveryGuideTestConfiguration用于初始化所有测试用例类。在测试方法上面加入JUnit的@Test注解
```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestApplication.class, DiscoveryGuideTestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiscoveryGuideTest {
    @Autowired
    private DiscoveryGuideTestCases discoveryGuideTestCases;

    private static long startTime;

    @BeforeClass
    public static void beforeTest() {
        startTime = System.currentTimeMillis();
    }

    @AfterClass
    public static void afterTest() {
        LOG.info("* Finished automation test in {} seconds", (System.currentTimeMillis() - startTime) / 1000);
    }

    @Test
    public void testNoGray() throws Exception {
        discoveryGuideTestCases.testNoGray(gatewayTestUrl);
        discoveryGuideTestCases.testNoGray(zuulTestUrl);
    }

    @Test
    public void testVersionStrategyGray() throws Exception {
        discoveryGuideTestCases.testVersionStrategyGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        discoveryGuideTestCases.testVersionStrategyGray1(zuulGroup, zuulServiceId, zuulTestUrl);
    }
}
```

```java
@Configuration
public class DiscoveryGuideTestConfiguration {
    @Bean
    public DiscoveryGuideTestCases discoveryGuideTestCases() {
        return new DiscoveryGuideTestCases();
    }
}
```

#### 普通调用测试
在测试方法上面增加注解@DTest，通过断言Assert来判断测试结果。注解@DTest内容如下
```java
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DTest {

}
```

代码如下
```java
public class DiscoveryGuideTestCases {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @DTest
    public void testNoGray(String testUrl) {
        int noRepeatCount = 0;
        List<String> resultList = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            if (!resultList.contains(result)) {
                noRepeatCount++;
            }
            resultList.add(result);
        }

        Assert.assertEquals(noRepeatCount, 4);
    }
}
```

#### 蓝绿灰度调用测试
在测试方法上面增加注解@DTestConfig，通过断言Assert来判断测试结果。注解DTestConfig注解内容如下
```java
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DTestConfig {
    // 组名
    String group();

    // 服务名
    String serviceId();

    // 组名-服务名组合键值的前缀
    String prefix() default StringUtils.EMPTY;

    // 组名-服务名组合键值的后缀
    String suffix() default StringUtils.EMPTY;

    // 执行配置的文件路径。测试用例运行前，会把该文件里的内容推送到远程配置中心或者服务
    String executePath();

    // 重置配置的文件路径。测试用例运行后，会把该文件里的内容推送到远程配置中心或者服务。该文件内容是最初的默认配置
    // 如果该注解属性为空，则直接删除从配置中心删除组名-服务名组合键值
    String resetPath() default StringUtils.EMPTY;
}
```

代码如下

```java
public class DiscoveryGuideTestCases {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-version-1.xml", resetPath = "gray-default.xml")
    public void testVersionStrategyGray(String group, String serviceId, String testUrl) {
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf("[V=1.0]");
            int lastIndex = result.lastIndexOf("[V=1.0]");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }
    }
}
```

蓝绿灰度配置文件gray-strategy-version-1.xml的内容如下
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version>1.0</version>
    </strategy>
</rule>
```

蓝绿灰度配置文件gray-default.xml的内容如下
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>

</rule>
```

#### 扩展调用测试
除了支持蓝绿灰度自动化测试外，使用者可扩展出以远程配置中心内容做变更的自动化测试。以阿里巴巴的Sentinel的权限功能为例子，参考PolarisGuide，测试实现方式如下

① 远程配置中心约定

- Nacos的Key格式

```
Group为DEFAULT_GROUP，Data ID为sentinel-authority-${spring.application.name}。每个服务都专享自己的Sentinel规则
```

- Apollo的Key格式

```
namespace为application，Key为sentinel-authority。每个服务都专享自己的Sentinel规则
```

② 执行测试用例前，把执行限流降级熔断等逻辑的内容（executePath = "sentinel-authority-2.json"）推送到远程配置中心

③ 执行测试用例，通过断言Assert来判断测试结果

④ 执行测试用例后，把修改过的内容（resetPath = "sentinel-authority-1.json"）复原，再推送一次到远程配置中心

```java
public class PolarisTestCases {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @DTestConfig(group = "DEFAULT_GROUP", serviceId = "sentinel-authority-polaris-service-b", executePath = "sentinel-authority-2.json", resetPath = "sentinel-authority-1.json")
    public void testSentinelAuthority1(String testUrl) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.postForEntity(testUrl, "gateway", String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            if (result.contains("AuthorityRule")) {
                count++;
            }
        }

        Assert.assertEquals(count, 4);
    }
}
```

### 测试报告
- 路由策略测试报告样例

```
---------- Run automation testcase :: testNoGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testEnabledStrategyGray1() ----------
Header : [mobile:"138"]
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testVersionStrategyGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testRegionStrategyGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testVersionWeightStrategyGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : 1.0 version weight=90%, 1.1 version weight=10%
B service desired : 1.0 version weight=20%, 1.1 version weight=80%
Result : A service 1.0 version weight=89.6%
Result : A service 1.1 version weight=10.4%
Result : B service 1.0 version weight=20.1333%
Result : B service 1.1 version weight=79.8667%
* Passed
---------- Run automation testcase :: testRegionWeightStrategyGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : dev region weight=85%, qa region weight=15%
B service desired : dev region weight=85%, qa region weight=15%
Result : A service dev region weight=83.7667%
Result : A service qa region weight=16.2333%
Result : B service dev region weight=86.2%
Result : B service qa region weight=13.8%
* Passed
```

- 路由规则测试报告样例

```
---------- Run automation testcase :: testStrategyCustomizationGray() ----------
Header : [a:"1", b:"2"]
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testVersionRuleGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testRegionRuleGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testVersionWeightRuleGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : 1.0 version weight=75%, 1.1 version weight=25%
B service desired : 1.0 version weight=35%, 1.1 version weight=65%
Result : A service 1.0 version weight=75.2667%
Result : A service 1.1 version weight=24.7333%
Result : B service 1.0 version weight=35.1667%
Result : B service 1.1 version weight=64.8333%
* Passed
---------- Run automation testcase :: testRegionWeightRuleGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : dev region weight=95%, qa region weight=5%
B service desired : dev region weight=95%, qa region weight=5%
Result : A service dev region weight=94.9333%
Result : A service qa region weight=5.0667%
Result : B service dev region weight=95.0667%
Result : B service qa region weight=4.9333%
* Passed
---------- Run automation testcase :: testVersionCompositeRuleGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : 1.0 version weight=40%, 1.1 version weight=60%
Route desired : A Service 1.0 version -> B Service 1.0 version, A Service 1.1 version -> B Service 1.1 version
Result : A service 1.0 version weight=39.8333%
A service 1.1 version weight=60.1667%
* Passed
```

## 压力测试
压力测试，基于WRK的异步压力测试框架，能用很少的线程压测出很大的并发量，使用简单方便

### 测试环境
① 准备两台机器部署Spring Cloud应用

② 准备一台机器部署网关（Spring Cloud或者Zuul）

③ 准备一台机器部署压测工具

| 服务 | 配置 | 数目 |
| --- | --- | --- |
| Spring Cloud Gateway | 16C 32G | 1 |
| Zuul 1.x | 16C 32G | 1 |
| Service | 4C 8G | 2 |

④ 优化方式

- Spring Cloud Gateway，不需要优化
- Zuul 1.x，优化如下

```
zuul.host.max-per-route-connections=1000
zuul.host.max-total-connections=1000
zuul.semaphore.max-semaphores=5000
```

### 测试介绍
- 使用WRK脚本进行性能测试，WRK脚本参考post.lua（位于discovery-guide-test-automation目录下），不带参数运行
- 使用WRK详细说明参考[https://github.com/wg/wrk](https://github.com/wg/wrk)

### 测试步骤
- 登录到WRK的机器，进入WRK目录
- 运行命令 wrk -t64 -c2000 -d30s -H "id: 123" -H "token: abc" --timeout=2s --latency --script=post.lua http://localhost:5001/discovery-guide-service-a/invoke/gateway

```
使用方法: wrk <选项> <被测HTTP服务的URL>
  Options:
    -c, --connections 跟服务器建立并保持的TCP连接数量
    -d, --duration    压测时间。例如：2s，2m，2h
    -t, --threads     使用多少个线程进行压测
    -s, --script      指定Lua脚本路径
    -H, --header      为每一个HTTP请求添加HTTP头。例如：-H "id: 123" -H "token: abc"，冒号后面要带空格
        --latency     在压测结束后，打印延迟统计信息
        --timeout     超时时间
```

- 等待结果，Requests/sec 表示每秒处理的请求数

基于WRK极限压测，报告如下

| 服务 | 性质 | 线程数 | 连接数 | 每秒最大请求数 | 资源耗费 |
| --- | --- | --- | --- | --- | --- |
| Spring Cloud Gateway为起始的调用链 | 原生框架 | 5000 | 20000 | 28100左右 | CPU占用率42% |
| Spring Cloud Gateway为起始的调用链 | 本框架 | 5000 | 20000 | 27800左右 | CPU占用率42.3% |
| Zuul 1.x为起始的调用链 | 原生框架 | 5000 | 20000 | 24050左右 | CPU占用率56% |
| Zuul 1.x为起始的调用链 | 本框架 | 5000 | 20000 | 23500左右 | CPU占用率56.5% |

## 附录

### 中间件服务器下载地址
![](http://nepxion.gitee.io/docs/icon-doc/information_message.png) 注册中心

① Nacos

- Nacos服务器版本，推荐用最新版本，从[https://github.com/alibaba/nacos/releases](https://github.com/alibaba/nacos/releases)获取
- 功能界面主页，[http://localhost:8848/nacos/index.html](http://localhost:8848/nacos/index.html)

② Consul

- Consul服务器版本不限制，推荐用最新版本，从[https://releases.hashicorp.com/consul/](https://releases.hashicorp.com/consul/)获取
- 功能界面主页，[http://localhost:8500](http://localhost:8500)

③ Eureka

- 跟Spring Cloud版本保持一致，自行搭建服务器
- 功能界面主页，[http://localhost:9528](http://localhost:9528)

④ Zookeeper

- Spring Cloud F版或以上，必须采用Zookeeper服务器的3.5.x服务器版本（或者更高），从[http://zookeeper.apache.org/releases.html#download](http://zookeeper.apache.org/releases.html#download)获取
- Spring Cloud E版，Zookeeper服务器版本不限制

![](http://nepxion.gitee.io/docs/icon-doc/information_message.png) 配置中心

① Nacos

- Nacos服务器版本，推荐用最新版本，从[https://github.com/alibaba/nacos/releases](https://github.com/alibaba/nacos/releases)获取
- 功能界面主页，[http://localhost:8848/nacos/index.html](http://localhost:8848/nacos/index.html)

② Apollo

- Apollo服务器版本，推荐用最新版本，从[https://github.com/ctripcorp/apollo/releases](https://github.com/ctripcorp/apollo/releases)获取
- 功能界面主页，[http://localhost:8088](http://localhost:8088)

③ Redis

- Redis服务器版本，推荐用最新版本，从[https://redis.io](https://redis.io)获取

④ Etcd

- Etcd服务器版本，推荐用最新版本，从[https://github.com/etcd-io/etcd/releases](https://github.com/etcd-io/etcd/releases)获取

![](http://nepxion.gitee.io/docs/icon-doc/information_message.png) 限流熔断

① Sentinel

- Sentinel服务器版本，推荐用最新版本，从[https://github.com/alibaba/Sentinel/releases](https://github.com/alibaba/Sentinel/releases)获取
- 功能界面主页，[http://localhost:8075/#/dashboard](http://localhost:8075/#/dashboard)

![](http://nepxion.gitee.io/docs/icon-doc/information_message.png) 调用链监控

① Jaeger

- Jaeger服务器版本，推荐用最新版本，从[https://github.com/jaegertracing/jaeger/releases](https://github.com/jaegertracing/jaeger/releases)获取
- 功能界面主页，[http://localhost:16686](http://localhost:16686)

② SkyWalking

- SkyWalking服务器版本，推荐用最新版本，从[http://skywalking.apache.org/downloads](http://skywalking.apache.org/downloads)获取
- 功能界面主页，[http://127.0.0.1:8080/](http://127.0.0.1:8080/)

③ Zipkin

- Zipkin服务器版本，推荐用最新版本，从[https://search.maven.org/remote_content?g=io.zipkin&a=zipkin-server&v=LATEST&c=exec](https://search.maven.org/remote_content?g=io.zipkin&a=zipkin-server&v=LATEST&c=exec)获取
- 功能界面主页，[http://localhost:9411/zipkin](http://localhost:9411/zipkin)

![](http://nepxion.gitee.io/docs/icon-doc/information_message.png) 指标监控

① Prometheus

- Prometheus服务器版本，推荐用最新版本，从[https://github.com/prometheus/prometheus/releases](https://github.com/prometheus/prometheus/releases)获取
- 功能界面主页，[http://localhost:9090](http://localhost:9090)

② Grafana

- Grafana服务器版本，推荐用最新版本，从[https://grafana.com/grafana/download?platform=windows](https://grafana.com/grafana/download?platform=windows)获取
- 功能界面主页，[http://localhost:3000](http://localhost:3000)

③ Spring Boot Admin

- 跟Spring Boot版本保持一致，自行搭建服务器。从[https://github.com/codecentric/spring-boot-admin](https://github.com/codecentric/spring-boot-admin)获取
- 功能界面主页，[http://localhost:6002](http://localhost:6002)

## Star走势图
[![Stargazers over time](https://starchart.cc/Nepxion/Discovery.svg)](https://starchart.cc/Nepxion/Discovery)