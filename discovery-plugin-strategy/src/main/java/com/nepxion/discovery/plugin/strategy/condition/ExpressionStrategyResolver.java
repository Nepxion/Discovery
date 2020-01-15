package com.nepxion.discovery.plugin.strategy.condition;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.TypeComparator;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ExpressionStrategyResolver {
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    public static boolean eval(String expression, String key, Map<String, String> map, TypeComparator typeComparator) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setTypeComparator(typeComparator);
        context.setVariable(key, map);

        return eval(expression, context);
    }

    public static boolean eval(String expression, Map<String, Map<String, String>> map, TypeComparator typeComparator) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setTypeComparator(typeComparator);
        for (Map.Entry<String, Map<String, String>> entry : map.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }

        return eval(expression, context);
    }

    public static boolean eval(String expression, StandardEvaluationContext context) {
        Boolean result = EXPRESSION_PARSER.parseExpression(expression).getValue(context, Boolean.class);

        return result != null ? result.booleanValue() : false;
    }
}