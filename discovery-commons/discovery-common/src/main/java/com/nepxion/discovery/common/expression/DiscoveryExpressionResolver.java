package com.nepxion.discovery.common.expression;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.TypeComparator;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.nepxion.discovery.common.constant.DiscoveryConstant;

public class DiscoveryExpressionResolver {
    public static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

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

    public static final Pattern EXPRESSION_PATTERN = Pattern.compile(DiscoveryConstant.EXPRESSION_REGEX);

    public static List<String> extractList(String expression) {
        List<String> list = new ArrayList<String>();

        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
        while (matcher.find()) {
            String group = matcher.group();
            String name = StringUtils.substringBetween(group, DiscoveryConstant.EXPRESSION_SUB_PREFIX, DiscoveryConstant.EXPRESSION_SUB_SUFFIX).trim();

            list.add(name);
        }

        return list;
    }

    public static final Pattern EXPRESSION_SINGLE_QUOTES_PATTERN = Pattern.compile(DiscoveryConstant.EXPRESSION_SINGLE_QUOTES_REGEX);

    public static Map<String, String> extractMap(String expression) {
        Map<String, String> map = new LinkedHashMap<String, String>();

        Matcher matcher = EXPRESSION_SINGLE_QUOTES_PATTERN.matcher(expression);
        int index = 0;
        String key = null;
        while (matcher.find()) {
            String group = matcher.group();
            if (group.startsWith(DiscoveryConstant.EXPRESSION_SINGLE_QUOTES) && group.endsWith(DiscoveryConstant.EXPRESSION_SINGLE_QUOTES)) {
                String name = StringUtils.substringBetween(group, DiscoveryConstant.EXPRESSION_SINGLE_QUOTES, DiscoveryConstant.EXPRESSION_SINGLE_QUOTES).trim();
                if (index % 2 == 0) {
                    // 偶数个元素为Key
                    key = name;
                } else if (index % 2 == 1) {
                    // 奇数个元素为Value
                    map.put(key, name);
                }

                index++;
            }
        }

        return map;
    }
}