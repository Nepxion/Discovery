![](http://nepxion.gitee.io/discovery/docs/discovery-doc/Banner.png)

# Discovery【探索】云原生微服务解决方案
![Total visits](https://visitor-badge.laobi.icu/badge?page_id=Nepxion&title=total%20visits)  [![Total lines](https://tokei.rs/b1/github/Nepxion/Discovery?category=lines)](https://tokei.rs/b1/github/Nepxion/Discovery?category=lines)  [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?label=license)](https://github.com/Nepxion/Discovery/blob/6.x.x/LICENSE)  [![Maven Central](https://img.shields.io/maven-central/v/com.nepxion/discovery.svg?label=maven)](https://search.maven.org/artifact/com.nepxion/discovery)  [![Javadocs](http://www.javadoc.io/badge/com.nepxion/discovery-plugin-framework-starter.svg)](http://www.javadoc.io/doc/com.nepxion/discovery-plugin-framework-starter)  [![Build Status](https://travis-ci.org/Nepxion/Discovery.svg?branch=6.x.x)](https://travis-ci.org/Nepxion/Discovery)  [![Codacy Badge](https://app.codacy.com/project/badge/Grade/5c42eb719ef64def9cad773abd877e8b)](https://www.codacy.com/gh/Nepxion/Discovery/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Nepxion/Discovery&amp;utm_campaign=Badge_Grade)  [![Stars](https://img.shields.io/github/stars/Nepxion/Discovery.svg?label=Stars&tyle=flat&logo=GitHub)](https://github.com/Nepxion/Discovery/stargazers)  [![Stars](https://gitee.com/Nepxion/Discovery/badge/star.svg?theme=gvp)](https://gitee.com/Nepxion/Discovery/stargazers)

[![Spring Boot](https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-dependencies.svg?label=Spring%20Boot&logo=Spring)](https://search.maven.org/artifact/org.springframework.boot/spring-boot-dependencies)  [![Spring Cloud](https://img.shields.io/maven-central/v/org.springframework.cloud/spring-cloud-dependencies.svg?label=Spring%20Cloud&logo=Spring)](https://search.maven.org/artifact/org.springframework.cloud/spring-cloud-dependencies)  [![Spring Cloud Alibaba](https://img.shields.io/maven-central/v/com.alibaba.cloud/spring-cloud-alibaba-dependencies.svg?label=Spring%20Cloud%20Alibaba&logo=Spring)](https://search.maven.org/artifact/com.alibaba.cloud/spring-cloud-alibaba-dependencies)  [![Nepxion Discovery](https://img.shields.io/maven-central/v/com.nepxion/discovery.svg?label=Nepxion%20Discovery&logo=Anaconda)](https://search.maven.org/artifact/com.nepxion/discovery)

[![Discovery PPT](https://img.shields.io/badge/Discovery%20-PPT-brightgreen?logo=Microsoft%20PowerPoint)](http://nepxion.gitee.io/discovery/docs/link-doc/discovery-ppt.html)  [![Discovery WIKI](https://img.shields.io/badge/Discovery%20-WIKI-brightgreen?logo=Microsoft%20Edge)](http://nepxion.gitee.io/discovery/)  [![Discovery Platform WIKI](https://img.shields.io/badge/Discovery%20Platform%20-WIKI-brightgreen?logo=Microsoft%20Edge)](http://nepxion.gitee.io/discoveryplatform)  [![Polaris WIKI](https://img.shields.io/badge/Polaris%20-WIKI-brightgreen?logo=Microsoft%20Edge)](http://polaris-paas.gitee.io/polaris-sdk)

<a href="https://github.com/Nepxion" tppabs="#" target="_blank"><img width="25" height="25" src="http://nepxion.gitee.io/discovery/docs/icon-doc/github.png"></a>&nbsp;  <a href="https://gitee.com/Nepxion" tppabs="#" target="_blank"><img width="25" height="25" src="http://nepxion.gitee.io/discovery/docs/icon-doc/gitee.png"></a>&nbsp;  <a href="https://search.maven.org/search?q=g:com.nepxion" tppabs="#" target="_blank"><img width="25" height="25" src="http://nepxion.gitee.io/discovery/docs/icon-doc/maven.png"></a>&nbsp;  <a href="http://nepxion.gitee.io/discovery/docs/contact-doc/wechat.jpg" tppabs="#" target="_blank"><img width="25" height="25" src="http://nepxion.gitee.io/discovery/docs/icon-doc/wechat.png"></a>&nbsp;  <a href="http://nepxion.gitee.io/discovery/docs/contact-doc/dingding.jpg" tppabs="#" target="_blank"><img width="25" height="25" src="http://nepxion.gitee.io/discovery/docs/icon-doc/dingding.png"></a>&nbsp;  <a href="http://nepxion.gitee.io/discovery/docs/contact-doc/gongzhonghao.jpg" tppabs="#" target="_blank"><img width="25" height="25" src="http://nepxion.gitee.io/discovery/docs/icon-doc/gongzhonghao.png"></a>&nbsp;  <a href="mailto:1394997@qq.com" tppabs="#"><img width="25" height="25" src="http://nepxion.gitee.io/discovery/docs/icon-doc/email.png"></a>

如果您觉得本框架具有一定的参考价值和借鉴意义，请帮忙在页面右上角 [**Star**]

## 简介

### 作者简介
- Nepxion开源社区创始人
- 2020年阿里巴巴中国云原生峰会出品人
- 2020年被Nacos和Spring Cloud Alibaba纳入相关开源项目
- 2021年阿里巴巴技术峰会上海站演讲嘉宾
- 2021年荣获陆奇博士主持的奇绩资本，进行风险投资的关注和调研
- 2021年入选Gitee最有价值开源项目
- 阿里巴巴官方书籍《Nacos架构与原理》作者之一
- Nacos Group Member、Spring Cloud Alibaba Member
- Spring Cloud Alibaba、Nacos、Sentinel、OpenTracing Committer & Contributor

<img src="http://nepxion.gitee.io/discovery/docs/discovery-doc/CertificateGVP.jpg" width="43%"><img src="http://nepxion.gitee.io/discovery/docs/discovery-doc/AwardNacos1.jpg" width="28%"><img src="http://nepxion.gitee.io/discovery/docs/discovery-doc/AwardSCA1.jpg" width="28%">

### 商业合作
① Discovery系列

| 框架名称 | 框架版本 | 支持Spring Cloud版本 | 使用许可 |
| --- | --- | --- | --- |
| Discovery | 1.x.x ~ 6.x.x | Camden ~ Hoxton | 开源，永久免费 |
| DiscoveryX | 7.x.x | 2020 | 闭源，商业许可 |
| DiscoveryX | 8.x.x | 2021 | 闭源，商业许可 |

② Polaris系列

Polaris为Discovery高级定制版，特色功能

- 基于Nepxion Discovery集成定制
- 多云、多活、多机房流量调配
- 跨云动态域名、跨环境适配
- DCN、DSU、SET单元化部署
- 组件灵活装配、配置对外屏蔽
- 极简低代码PaaS平台

| 框架名称 | 框架版本 | 支持Discovery版本 | 支持Spring Cloud版本 | 使用许可 |
| --- | --- | --- | --- | --- |
| Polaris | 1.x.x | 6.x.x | Finchley ~ Hoxton | 闭源，商业许可 |
| Polaris | 2.x.x | 7.x.x | 2020 | 闭源，商业许可 |
| Polaris | 3.x.x | 8.x.x | 2021 | 闭源，商业许可 |

有商业版需求的企业和用户，请添加微信1394997，联系作者，洽谈合作事宜

### 入门资料
![](http://nepxion.gitee.io/discovery/docs/discovery-doc/Logo64.png) Discovery【探索】企业级云原生微服务开源解决方案

① 快速入门
- [快速入门Github版](https://github.com/Nepxion/Discovery/wiki)
- [快速入门Gitee版](https://gitee.com/Nepxion/Discovery/wikis/pages)

② 解决方案
- [解决方案WIKI版](http://nepxion.com/discovery)
- [解决方案PPT版](http://nepxion.gitee.io/discovery/docs/link-doc/discovery-ppt.html)

③ 平台界面
- [平台界面WIKI版](http://nepxion.com/discovery-platform)

④ 框架源码
- [框架源码Github版](https://github.com/Nepxion/Discovery)
- [框架源码Gitee版](https://gitee.com/Nepxion/Discovery)

⑤ 指南示例源码
- [指南示例源码Github版](https://github.com/Nepxion/DiscoveryGuide)
- [指南示例源码Gitee版](https://gitee.com/Nepxion/DiscoveryGuide)

⑥ 指南示例说明
- 对于入门级玩家，参考[6.x.x指南示例极简版](https://github.com/Nepxion/DiscoveryGuide/tree/6.x.x-simple)，分支为6.x.x-simple
- 对于熟练级玩家，参考[6.x.x指南示例精进版](https://github.com/Nepxion/DiscoveryGuide/tree/6.x.x)，分支为6.x.x。除上述《极简版》功能外，涉及到指南篇里的绝大多数高级功能
- 对于骨灰级玩家，参考[6.x.x指南示例高级版](https://github.com/Nepxion/DiscoveryGuide/tree/6.x.x-complex)，分支为6.x.x-complex。除上述《精进版》功能外，涉及到指南篇里的ActiveMQ、MongoDB、RabbitMQ、Redis、RocketMQ、MySQL等高级调用链和蓝绿灰度调用链的整合
- 上述指南实例分支是针对Spring Cloud Finchley ~ Hoxton版本。对于Spring Cloud 202x版本，参考[7.x.x ~ 8.x.x指南示例精进版](https://github.com/Nepxion/DiscoveryGuide/tree/master)，分支为master

![](http://nepxion.gitee.io/discovery/docs/polaris-doc/Logo64.png) Polaris【北极星】企业级云原生微服务商业解决方案

① 解决方案
- [解决方案WIKI版](http://nepxion.com/polaris)

② 框架源码
- [框架源码Github版](https://github.com/polaris-paas/polaris-sdk)
- [框架源码Gitee版](https://gitee.com/polaris-paas/polaris-sdk)

③ 指南示例源码
- [指南示例源码Github版](https://github.com/polaris-paas/polaris-guide)
- [指南示例源码Gitee版](https://gitee.com/polaris-paas/polaris-guide)

④ 指南示例说明
- Spring Cloud Finchley ~ Hoxton版本，参考[1.x.x指南示例](https://github.com/polaris-paas/polaris-guide/tree/1.x.x)，分支为1.x.x
- Spring Cloud 202x版本，参考[2.x.x ~ 3.x.x指南示例](https://github.com/polaris-paas/polaris-guide/tree/master)，分支为master

## 请联系我
微信、钉钉、公众号和文档

![](http://nepxion.gitee.io/discovery/docs/contact-doc/wechat-1.jpg)![](http://nepxion.gitee.io/discovery/docs/contact-doc/dingding-1.jpg)![](http://nepxion.gitee.io/discovery/docs/contact-doc/gongzhonghao-1.jpg)![](http://nepxion.gitee.io/discovery/docs/contact-doc/document-1.jpg)

## Star走势图
[![Stargazers over time](https://starchart.cc/Nepxion/Discovery.svg)](https://starchart.cc/Nepxion/Discovery)