package com.nepxion.discovery.plugin.example.service.sentinel;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.stereotype.Component;

import com.nepxion.discovery.plugin.example.service.feign.BFeign;

@Component
public class MyBFeignFallbackHandler implements BFeign {
    @Override
    public String invoke(String value) {
        return value + " -> B Feign client sentinel fallback";
    }
}