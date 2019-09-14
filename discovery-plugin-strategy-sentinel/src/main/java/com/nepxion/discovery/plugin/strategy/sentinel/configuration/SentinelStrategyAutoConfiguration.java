package com.nepxion.discovery.plugin.strategy.sentinel.configuration;

/*
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Weihua
 * @version 1.0
 */

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.constant.SentinelConstant;
import com.nepxion.discovery.plugin.strategy.sentinel.loader.DefaultLocalRuleLoader;
import com.nepxion.discovery.plugin.strategy.sentinel.loader.RuleLoader;
import com.nepxion.discovery.plugin.strategy.sentinel.parser.SentinelRequestOriginParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = StrategyConstant.SPRING_APPLICATION_STRATEGY_SENTINEL_ENABLED)
public class SentinelStrategyAutoConfiguration {

  /** 流控规则文件路径 */
  @Value("${" + SentinelConstant.SENTINEL_FLOW_PATH + ":file:" + SentinelConstant.SENTINEL_FLOW_KEY + ".json}")
  private String flowPath;
  /** 降级规则文件路径 */
  @Value("${" + SentinelConstant.SENTINEL_SYSTEM_PATH + ":file:" + SentinelConstant.SENTINEL_SYSTEM_KEY + ".json}")
  private String degradePath;
  /** 授权规则文件路径 */
  @Value("${" + SentinelConstant.SENTINEL_AUTH_PATH + ":file:" + SentinelConstant.SENTINEL_AUTH_KEY + ".json}")
  private String authorityPath;
  /** 系统规则文件路径 */
  @Value("${" + SentinelConstant.SENTINEL_DEGRADE_PATH + ":file:" + SentinelConstant.SENTINEL_DEGRADE_KEY + ".json}")
  private String systemPath;
  /** 热点参数流控规则文件路径 */
  @Value("${" + SentinelConstant.SENTINEL_PARAM_PATH + ":file:" + SentinelConstant.SENTINEL_PARAM_KEY + ".json}")
  private String paramPath;

  @Bean
  public CommonFilter commonFilter(){
    return new CommonFilter();
  }

  @Bean
  public SentinelResourceAspect sentinelResourceAspect(){
    return new SentinelResourceAspect();
  }

  @Bean
  public RequestOriginParser requestOriginParser(){
    RequestOriginParser originParser = new SentinelRequestOriginParser();
    WebCallbackManager.setRequestOriginParser(originParser);
    return originParser;
  }

  @Bean
  @ConditionalOnMissingBean(value = RuleLoader.class)
  public RuleLoader ruleLoader () {
    return new DefaultLocalRuleLoader(flowPath, systemPath, authorityPath, degradePath, paramPath);
  }

}
