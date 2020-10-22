package com.nepxion.discovery.plugin.strategy.condition;

import com.nepxion.discovery.common.entity.StrategyConditionEntity;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyConditionConfigWrapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * condition的header断言
 *
 * @author zlliu
 * @date 2020/10/22 22:16
 */
public class BodyStrategyConditionPredicate extends AbstractStrategyConditionPredicate {

    @Autowired
    private StrategyContextHolder strategyContextHolder;

    @Override
    public boolean apply(@Nullable StrategyConditionEntity strategyConditionEntity, Map<String, String> headerMap) {
        Map<String, Object> bodyMap = strategyContextHolder.getBody();

        //  配置了多个断言的，且没有传body的逻辑？
        boolean postOrPut = HttpMethod.POST.toString().equals(strategyContextHolder.getMethod())
                || HttpMethod.PUT.toString().equals(strategyContextHolder.getMethod());
        //  非POST请求
        if (!postOrPut && MapUtils.isEmpty(bodyMap)) {
            return true;
        }
        //  POST请求，body为空或者不满足json格式
        else if (postOrPut && MapUtils.isEmpty(bodyMap)) {
            return false;
        }
        //  POST请求，没有配置body策略
        else if (postOrPut && StringUtils.isBlank(strategyConditionEntity.getBody())) {
            return true;
        }
        Map<String, String> configParams = new HashMap<>();
        StrategyConditionConfigWrapper.parseBodyConfig(configParams, strategyConditionEntity.getBody());

        for (Map.Entry<String, String> entry : configParams.entrySet()) {
            if (!Objects.equals(entry.getValue(), bodyMap.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }
}
