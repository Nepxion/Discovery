package com.nepxion.discovery.plugin.strategy.service.decorator;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author liquanjin
 * @version 1.0
 */

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServiceStrategyRequestDecoratorFactory {
    public static RequestAttributes decorateRequestAttributes(RequestAttributes requestAttributes) {
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        return new ServletRequestAttributes(new ServiceStrategyRequestDecorator(request));
    }
}