package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.springframework.expression.TypeComparator;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.expression.DiscoveryExpressionResolver;
import com.nepxion.discovery.common.expression.DiscoveryTypeComparor;
import com.nepxion.discovery.common.util.StringUtil;

public class StrategyResourceImpl implements StrategyResource {
    private TypeComparator typeComparator = new DiscoveryTypeComparor();

    @Override
    public boolean validateExpression(String expression, String validation) {
        Map<String, String> map = null;
        try {
            map = StringUtil.splitToMap(validation);
        } catch (Exception e) {
            throw new DiscoveryException("Invalid format for validation input");
        }

        return DiscoveryExpressionResolver.eval(expression, DiscoveryConstant.EXPRESSION_PREFIX, map, typeComparator);
    }
}