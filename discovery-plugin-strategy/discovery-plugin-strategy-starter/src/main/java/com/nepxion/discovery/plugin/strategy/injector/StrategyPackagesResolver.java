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

import com.nepxion.discovery.common.entity.PackagesInjectorEntity;
import com.nepxion.discovery.common.entity.PackagesInjectorType;

public class StrategyPackagesResolver {
    public static List<String> getInjectedPackages(List<StrategyPackagesInjector> strategyPackagesInjectorList, PackagesInjectorType packagesInjectorType) {
        List<String> packageList = null;
        if (CollectionUtils.isNotEmpty(strategyPackagesInjectorList)) {
            packageList = new ArrayList<String>();
            for (StrategyPackagesInjector strategyPackagesInjector : strategyPackagesInjectorList) {
                List<PackagesInjectorEntity> packagesInjectorEntityList = strategyPackagesInjector.getPackagesInjectorEntityList();
                if (CollectionUtils.isNotEmpty(packagesInjectorEntityList)) {
                    for (PackagesInjectorEntity packagesInjectorEntity : packagesInjectorEntityList) {
                        PackagesInjectorType injectorType = packagesInjectorEntity.getPackagesInjectorType();
                        List<String> packages = packagesInjectorEntity.getPackages();
                        if (injectorType == packagesInjectorType || injectorType == PackagesInjectorType.ALL) {
                            if (CollectionUtils.isNotEmpty(packages)) {
                                for (String pkg : packages) {
                                    if (!packageList.contains(pkg)) {
                                        packageList.add(pkg);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return packageList;
    }
}