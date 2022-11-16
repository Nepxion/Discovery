package com.nepxion.discovery.plugin.strategy.injector;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.nepxion.discovery.common.entity.HeadersInjectorEntity;
import com.nepxion.discovery.common.entity.HeadersInjectorType;

public class StrategyHeadersResolver {
    public static List<String> getInjectedHeaders(List<StrategyHeadersInjector> strategyHeadersInjectorList, HeadersInjectorType headersInjectorType) {
        List<String> headerList = null;
        if (CollectionUtils.isNotEmpty(strategyHeadersInjectorList)) {
            headerList = new ArrayList<String>();
            for (StrategyHeadersInjector strategyHeadersInjector : strategyHeadersInjectorList) {
                List<HeadersInjectorEntity> headersInjectorEntityList = strategyHeadersInjector.getHeadersInjectorEntityList();
                if (CollectionUtils.isNotEmpty(headersInjectorEntityList)) {
                    for (HeadersInjectorEntity headersInjectorEntity : headersInjectorEntityList) {
                        HeadersInjectorType injectorType = headersInjectorEntity.getHeadersInjectorType();
                        List<String> headers = headersInjectorEntity.getHeaders();
                        if (injectorType == headersInjectorType || injectorType == HeadersInjectorType.ALL) {
                            if (CollectionUtils.isNotEmpty(headers)) {
                                for (String header : headers) {
                                    if (!headerList.contains(header)) {
                                        headerList.add(header);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return headerList;
    }
}