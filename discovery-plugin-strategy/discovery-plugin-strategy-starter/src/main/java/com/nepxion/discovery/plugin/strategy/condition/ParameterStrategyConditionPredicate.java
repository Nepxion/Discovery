package com.nepxion.discovery.plugin.strategy.condition;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.StrategyConditionEntity;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * condition的header断言
 *
 * @author zlliu
 * @date 2020/10/22 22:16
 */
public class ParameterStrategyConditionPredicate extends AbstractStrategyConditionPredicate {
    @Autowired
    private StrategyContextHolder strategyContextHolder;

    @Override
    public boolean apply(@Nullable StrategyConditionEntity strategyConditionEntity, Map<String, String> headerMap) {
        Map<String, Object> params = strategyContextHolder.getParam();

        //  配置了多个断言的，且没有传param的逻辑？
        if (!HttpMethod.GET.toString().equals(strategyContextHolder.getMethod()) && MapUtils.isEmpty(params)) {
            return true;
        } else if (HttpMethod.GET.toString().equals(strategyContextHolder.getMethod()) && MapUtils.isEmpty(params)) {
            return false;
        }
        Map<String, Object> configParams = new LinkedHashMap<>();
        parseConfig(configParams, strategyConditionEntity.getParam());

        // 遍历配置的param
        for (Map.Entry<String, Object> entry : configParams.entrySet()) {
            Object values = params.get(entry.getKey());
            if (!ObjectUtils.equals(entry.getValue(), values)) {
                return false;
            }
        }
        return true;
    }

    private static void parseConfig(Map<String, Object> paramMap, String parameterConfig) {
        List<String> configList = StringUtil.splitToList(parameterConfig, DiscoveryConstant.SEPARATE);
        if (CollectionUtils.isEmpty(configList)) {
            return;
        }
        for (String config : configList) {
            String[] valueArray = StringUtils.split(config, DiscoveryConstant.EQUALS);
            String key = valueArray[0].trim();
            String value = null;
            try {
                value = String.valueOf(valueArray[1].trim());
                paramMap.put(key, value);
            } catch (Exception e) {
            }
        }
    }
}
