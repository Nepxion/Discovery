package com.nepxion.discovery.plugin.strategy.wrapper;


import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * condition的param或body节点解析
 *
 * @author zlliu
 * @date 2020/10/22 18:16
 */
public class StrategyConditionConfigWrapper {

    /**
     * param节点解析
     *
     * @param paramMap
     * @param parameterConfig
     */
    public static void parseConfig(MultiValueMap<String, String> paramMap, String parameterConfig) {
        List<String> configList = StringUtil.splitToList(parameterConfig, DiscoveryConstant.SEPARATE);
        if (CollectionUtils.isEmpty(configList)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        for (String config : configList) {
            String[] valueArray = StringUtils.split(config, DiscoveryConstant.EQUALS);
            String key = valueArray[0].trim();
            String value = null;
            try {
                value = String.valueOf(valueArray[1].trim());
                map.put(key, value);
            } catch (Exception e) {
            }
        }
        paramMap.setAll(map);
    }

    /**
     * body 节点解析
     *
     * @param paramMap
     * @param parameterConfig
     */
    public static void parseBodyConfig(Map<String, String> paramMap, String parameterConfig) {
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
