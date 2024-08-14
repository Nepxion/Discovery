package com.nepxion.discovery.plugin.strategy.context;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import com.nepxion.discovery.common.util.EnvironmentUtil;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;

public class StrategyEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (EnvironmentUtil.isStandardEnvironment(environment)) {
            processStrategyDebug();
        }
    }

    private void processStrategyDebug() {
        String strategyDebug = System.getProperty(StrategyConstant.STRATEGY_DEBUG);
        if (StringUtils.isNotEmpty(strategyDebug)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            stringBuilder.append("==========================================================================================\n");
            stringBuilder.append("$                                                                                        $\n");
            stringBuilder.append("$                               Strategy Debug Information                               $\n");
            stringBuilder.append("$                                                                                        $\n");

            strategyDebug = strategyDebug.trim().toLowerCase();
            if (strategyDebug.matches(Boolean.TRUE + "|" + Boolean.FALSE)) {
                System.setProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_MONITOR_ENABLED, strategyDebug);
                System.setProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_REST_INTERCEPT_DEBUG_ENABLED, strategyDebug);
                System.setProperty(StrategyConstant.SPRING_APPLICATION_STRATEGY_LOGGER_DEBUG_ENABLED, strategyDebug);

                if (StringUtils.equals(strategyDebug, Boolean.TRUE.toString())) {
                    stringBuilder.append("$  Debug for strategy context transmission is enabled                                    $\n");
                    stringBuilder.append("$  Following strategy Http data will display on console for troubleshooting :            $\n");
                    stringBuilder.append("$  1. n-d-*                                                                              $\n");
                    stringBuilder.append("$  2. " + StrategyConstant.SPRING_APPLICATION_STRATEGY_CONTEXT_REQUEST_HEADERS + "                                $\n");
                    stringBuilder.append("$  3. " + StrategyConstant.SPRING_APPLICATION_STRATEGY_BUSINESS_REQUEST_HEADERS + "                               $\n");
                } else {
                    stringBuilder.append("$  Debug for strategy context transmission is disabled                                   $\n");
                }
            } else {
                stringBuilder.append("$  Debug for strategy context transmission isn't enabled                                 $\n");
                stringBuilder.append("$  The value of '" + StrategyConstant.STRATEGY_DEBUG + "' must be a boolean type                                  $\n");
            }

            stringBuilder.append("$                                                                                        $\n");
            stringBuilder.append("==========================================================================================\n");
            System.out.println(stringBuilder.toString());
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}