package com.nepxion.discovery.plugin.strategy.service.sentinel.loader;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Weihua Wang
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nepxion.discovery.common.util.JsonUtil;
import com.nepxion.discovery.plugin.framework.util.FileContextUtil;
import com.nepxion.discovery.plugin.strategy.service.sentinel.constant.SentinelStrategyConstant;

public class SentinelRuleLoaderUtil {
    public static String getRuleText(ApplicationContext applicationContext, String path) {
        String text = FileContextUtil.getText(applicationContext, path);
        if (StringUtils.isEmpty(text)) {
            text = SentinelStrategyConstant.SENTINEL_EMPTY_RULE;
        }

        return text;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getRuleList(ApplicationContext applicationContext, String path) {
        String text = getRuleText(applicationContext, path);

        return (T) JsonUtil.fromJson(text, new TypeReference<List<T>>() {
        });
    }
}