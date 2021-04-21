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
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import com.nepxion.discovery.common.util.StringUtil;

public class StrategyPackagesExtractor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(StrategyPackagesExtractor.class);

    private ApplicationContext applicationContext;

    private List<String> basePackagesList;
    private String basePackages;

    private Set<String> scanningPackagesSet;
    private String scanningPackages;

    private List<String> allPackagesList = new ArrayList<String>();
    private String allPackages;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        try {
            basePackagesList = AutoConfigurationPackages.get(applicationContext);
            if (CollectionUtils.isNotEmpty(basePackagesList)) {
                basePackages = StringUtil.convertToString(basePackagesList);

                for (String value : basePackagesList) {
                    if (!allPackagesList.contains(value)) {
                        allPackagesList.add(value);
                    }
                }
            }

            scanningPackagesSet = getComponentScanningPackages(registry, basePackagesList);
            if (CollectionUtils.isNotEmpty(scanningPackagesSet)) {
                List<String> scanningPackagesList = new ArrayList<String>(scanningPackagesSet);
                scanningPackages = StringUtil.convertToString(scanningPackagesList);

                for (String value : scanningPackagesList) {
                    if (!allPackagesList.contains(value)) {
                        allPackagesList.add(value);
                    }
                }
            }

            if (CollectionUtils.isNotEmpty(allPackagesList)) {
                allPackages = StringUtil.convertToString(allPackagesList);
            }
        } catch (Exception e) {
            LOG.warn("Get base and scanning packages failed, skip it...");
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

    public List<String> getAllPackagesList() {
        return allPackagesList;
    }

    public String getAllPackages() {
        return allPackages;
    }

    protected Set<String> getComponentScanningPackages(BeanDefinitionRegistry registry, List<String> basePackages) {
        Set<String> packages = new LinkedHashSet<>();
        String[] names = registry.getBeanDefinitionNames();
        for (String name : names) {
            BeanDefinition definition = registry.getBeanDefinition(name);
            String beanClassName = definition.getBeanClassName();
            if (definition instanceof AnnotatedBeanDefinition && beanClassName != null) {
                String beanPackage = ClassUtils.getPackageName(beanClassName);
                for (String basePackage : basePackages) {
                    if (beanPackage.equals(basePackage) || beanPackage.startsWith(basePackage + '.')) {
                        AnnotatedBeanDefinition annotatedDefinition = (AnnotatedBeanDefinition) definition;
                        addComponentScanningPackages(packages, annotatedDefinition.getMetadata());
                        break;
                    }
                }
            }
        }
        return packages;
    }

    private void addComponentScanningPackages(Set<String> packages, AnnotationMetadata metadata) {
        AnnotationAttributes attributes = AnnotationAttributes
                .fromMap(metadata.getAnnotationAttributes(ComponentScan.class.getName(), true));
        if (attributes != null) {
            addPackages(packages, attributes.getStringArray("value"));
            addPackages(packages, attributes.getStringArray("basePackages"));
            addClasses(packages, attributes.getStringArray("basePackageClasses"));
            if (packages.isEmpty()) {
                packages.add(ClassUtils.getPackageName(metadata.getClassName()));
            }
        }
    }

    private void addPackages(Set<String> packages, String[] values) {
        if (values != null) {
            Collections.addAll(packages, values);
        }
    }

    private void addClasses(Set<String> packages, String[] values) {
        if (values != null) {
            for (String value : values) {
                packages.add(ClassUtils.getPackageName(value));
            }
        }
    }
}