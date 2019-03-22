package com.nepxion.discovery.plugin.example.service.feign;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nepxion.discovery.plugin.example.service.sentinel.MyBFeignFallbackHandler;

@FeignClient(value = "discovery-springcloud-example-b", fallback = MyBFeignFallbackHandler.class)
// Context-patch一旦被设置，在Feign也要带上context-path，外部Postman调用网关或者服务路径也要带context-path
// @FeignClient(value = "discovery-springcloud-example-b", path = "/nepxion")
// @FeignClient(value = "discovery-springcloud-example-b/nepxion")
public interface BFeign {
    @RequestMapping(path = "/invoke", method = RequestMethod.POST)
    String invoke(@RequestBody String value);
}