package com.nepxion.discovery.common.expression;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.HashMap;
import java.util.Map;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.TypeComparator;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class DiscoveryExpressionResolver {
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    public static boolean eval(String expression, String key, Map<String, String> map, TypeComparator typeComparator) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setTypeComparator(typeComparator);
        if (map != null) {
            context.setVariable(key, map);
        } else {
            context.setVariable(key, new HashMap<String, String>());
        }

        return eval(expression, context);
    }

    public static boolean eval(String expression, StandardEvaluationContext context) {
        try {
            Boolean result = EXPRESSION_PARSER.parseExpression(expression).getValue(context, Boolean.class);

            return result != null ? result.booleanValue() : false;
        } catch (Exception e) {
            return false;
        }
    }
}