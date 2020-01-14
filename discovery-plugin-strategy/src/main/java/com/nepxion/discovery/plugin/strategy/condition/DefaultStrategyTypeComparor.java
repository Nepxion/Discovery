package com.nepxion.discovery.plugin.strategy.condition;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.math.BigDecimal;

import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.support.StandardTypeComparator;
import org.springframework.lang.Nullable;

public class DefaultStrategyTypeComparor extends StandardTypeComparator {
    @Override
    public int compare(@Nullable Object left, @Nullable Object right) throws SpelEvaluationException {
        if (left == null) {
            return 0;
        }

        try {
            BigDecimal leftValue = new BigDecimal(left.toString());
            BigDecimal rightValue = new BigDecimal(right.toString());

            return super.compare(leftValue, rightValue);
        } catch (Exception e) {

        }

        return super.compare(left, right);
    }
}