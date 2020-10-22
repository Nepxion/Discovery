package com.nepxion.discovery.plugin.strategy.condition;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.StrategyConditionEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.plugin.strategy.context.StrategyContextHolder;
import com.nepxion.discovery.plugin.strategy.wrapper.StrategyConditionConfigWrapper;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * condition的header断言
 *
 * @author zlliu
 * @date 2020/10/22 22:16
 */
public class HeaderStrategyConditionPredicate extends AbstractStrategyConditionPredicate {
    private Pattern pattern = Pattern.compile(DiscoveryConstant.EXPRESSION_REGEX);

    @Autowired
    private StrategyContextHolder strategyContextHolder;

    @Override
    public boolean apply(StrategyConditionEntity strategyConditionEntity, Map<String, String> headerMap) {
        if (headerMap == null) {
            throw new DiscoveryException("Header map can't be null");
        }
        String conditionHeader = strategyConditionEntity.getConditionHeader();
        if (StringUtils.isBlank(conditionHeader)) {
            return true;
        }
        Matcher matcher = pattern.matcher(conditionHeader);
        if (matcher.find()) {
            return ExpressionStrategyResolver.eval(conditionHeader,
                    DiscoveryConstant.EXPRESSION_PREFIX, headerMap, new DefaultStrategyTypeComparor());
        } else {
            MultiValueMap<String, String> configParams = new LinkedMultiValueMap<>();
            StrategyConditionConfigWrapper.parseConfig(configParams, conditionHeader);

            if (MapUtils.isEmpty(headerMap)) {
                headerMap = strategyContextHolder.getHeaders();
            }
            MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
            header.setAll(headerMap);
            for (Map.Entry<String, List<String>> entry : configParams.entrySet()) {
                List<String> values = header.get(entry.getKey());
                if (!ListUtils.isEqualList(entry.getValue(), values)) {
                    return false;
                }
            }
            return true;
        }
    }

//    public static void main(String[] args) {
//        Matcher matcher = new HeaderStrategyConditionPredicate().pattern.matcher("#H['a'] == '1'");
//        System.out.println(matcher.find());
//        System.out.println(matcher.matches());
//    }
}
