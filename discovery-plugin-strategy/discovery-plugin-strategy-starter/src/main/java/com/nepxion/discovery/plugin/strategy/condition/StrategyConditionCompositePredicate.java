package com.nepxion.discovery.plugin.strategy.condition;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.nepxion.discovery.common.entity.StrategyConditionEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 复合断言
 * 参考：
 *      {@link com.netflix.loadbalancer.CompositePredicate}
 * @author zlliu
 * @date 2020/10/22 22:16
 */
public class StrategyConditionCompositePredicate extends AbstractStrategyConditionPredicate {
    private AbstractStrategyConditionPredicate delegate;

    @Override
    public boolean apply(StrategyConditionEntity input, Map<String, String> headerMap) {
        return this.delegate.apply(input, headerMap);
    }

    public static class Builder {
        private StrategyConditionCompositePredicate toBuild = new StrategyConditionCompositePredicate();

        public Builder(AbstractStrategyConditionPredicate primaryPredicate) {
            this.toBuild.delegate = primaryPredicate;
        }

        public Builder(AbstractStrategyConditionPredicate... primaryPredicates) {
            AndPredicate chain = new AndPredicate(Lists.newArrayList(primaryPredicates));
            this.toBuild.delegate = new AbstractStrategyConditionPredicate() {
                @Override
                public boolean apply(StrategyConditionEntity strategyConditionEntity, Map<String, String> headerMap) {
                    return chain.apply(strategyConditionEntity, headerMap);
                }
            };
        }

        static <T> List<T> defensiveCopy(Iterable<T> iterable) {
            ArrayList<T> list = new ArrayList();
            Iterator var2 = iterable.iterator();
            while(var2.hasNext()) {
                T element = (T) var2.next();
                list.add(Preconditions.checkNotNull(element));
            }
            return list;
        }

        private static class AndPredicate<T> extends AbstractStrategyConditionPredicate implements Serializable {
            private final List<? extends AbstractStrategyConditionPredicate> components;

            private AndPredicate(List<? extends AbstractStrategyConditionPredicate> components) {
                this.components = components;
            }

            @Override
            public boolean apply(StrategyConditionEntity input, Map<String, String> headerMap) {
                for(int i = 0; i < this.components.size(); ++i) {
                    if (!(this.components.get(i)).apply(input, headerMap)) {
                        return false;
                    }
                }
                return true;
            }
        }

        public StrategyConditionCompositePredicate build() {
            return this.toBuild;
        }
    }
}
