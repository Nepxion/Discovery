package com.nepxion.discovery.plugin.example.service.hystrix;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nepxion.discovery.plugin.strategy.service.context.RestStrategyContext;

@Service
public class HystrixService {
    private static final Logger LOG = LoggerFactory.getLogger(HystrixService.class);

    // Hystrix测试
    // @HystrixCommand(fallbackMethod = "invokeFallback", commandProperties = { @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD") })
    public String invokeHystrix(String value) {
        LOG.info("---------- 熔断方法里获取上下文 RestStrategyContext.getCurrentContext().getRequestAttributes()：{}", RestStrategyContext.getCurrentContext().getRequestAttributes());
        LOG.info("---------- 熔断方法里获取Token：{}", ((ServletRequestAttributes) RestStrategyContext.getCurrentContext().getRequestAttributes()).getRequest().getHeader("token"));

        return "Invoke Hystrix";
    }

    public String invokeFallback(String value, Throwable e) {
        LOG.info("---------- 快速失败方法里获取上下文 RestStrategyContext.getCurrentContext().getRequestAttributes()：{}", RestStrategyContext.getCurrentContext().getRequestAttributes());

        if (e != null) {
            LOG.error("Fallback error", e);
        }

        return "Fallback by Hystrix";
    }
}