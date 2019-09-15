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

import com.nepxion.discovery.plugin.example.service.feign.CFeign;

@Component
public class MyCFeignFallbackHandler implements CFeign {
    @Override
    public String invoke(String value) {
        return "C Feign client sentinel fallback, value=" + value;
    }
}