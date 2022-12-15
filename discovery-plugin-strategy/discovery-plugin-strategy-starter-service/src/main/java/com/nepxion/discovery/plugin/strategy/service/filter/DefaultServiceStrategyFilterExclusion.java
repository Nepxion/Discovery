package com.nepxion.discovery.plugin.strategy.service.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.strategy.service.constant.ServiceStrategyConstant;

public class DefaultServiceStrategyFilterExclusion implements ServiceStrategyFilterExclusion {
    @Value("${" + ServiceStrategyConstant.SPRING_APPLICATION_STRATEGY_URI_FILTER_EXCLUSION + ":/actuator/}")
    protected String uriFilterExclusion;

    protected List<String> uriFilterExclusionList = new ArrayList<String>();

    @PostConstruct
    public void initialize() {
        if (StringUtils.isNotEmpty(uriFilterExclusion)) {
            uriFilterExclusionList = StringUtil.splitToList(uriFilterExclusion);
        }
    }

    @Override
    public boolean isExclusion(HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();

        for (String uriFilterExclusionValue : uriFilterExclusionList) {
            if (requestURI.contains(uriFilterExclusionValue)) {
                return true;
            }
        }

        return false;
    }
}