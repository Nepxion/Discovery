package com.nepxion.discovery.plugin.strategy.extractor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Xun Zhong
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.nepxion.discovery.common.util.StringUtil;

public class StrategyPackagesExtractor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {
    private ApplicationContext applicationContext;

    private List<String> basePackagesList;
    private String basePackages;

    private Set<String> scanningPackagesSet;
    private String scanningPackages;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        basePackagesList = AutoConfigurationPackages.get(applicationContext);
        if (CollectionUtils.isNotEmpty(basePackagesList)) {
            basePackages = StringUtil.convertToString(basePackagesList);
        }

        ScanningPackagesExtractor.Extractor extractor = new ScanningPackagesExtractor.Extractor();
        scanningPackagesSet = extractor.extractScanningPackages(registry);

        if (CollectionUtils.isNotEmpty(scanningPackagesSet)) {
            scanningPackages = StringUtil.convertToString(new ArrayList<>(scanningPackagesSet));
        }
    }

    public List<String> getBasePackagesList() {
        return basePackagesList;
    }

    public String getBasePackages() {
        return basePackages;
    }

    public Set<String> getScanningPackagesSet() {
        return scanningPackagesSet;
    }

    public String getScanningPackages() {
        return scanningPackages;
    }

    private static class ScanningPackagesExtractor extends ConfigurationWarningsApplicationContextInitializer {
        static class Extractor extends ComponentScanPackageCheck {
            public Set<String> extractScanningPackages(BeanDefinitionRegistry registry) {
                return getComponentScanningPackages(registry);
            }
        }
    }
}