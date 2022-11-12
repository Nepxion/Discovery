package com.nepxion.discovery.common.entity;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PackagesInjectorEntity implements Serializable {
    private static final long serialVersionUID = 5730588860616523887L;

    private PackagesInjectorType packagesInjectorType;
    private List<String> packages;

    public PackagesInjectorEntity() {

    }

    public PackagesInjectorEntity(PackagesInjectorType packagesInjectorType, List<String> packages) {
        this.packagesInjectorType = packagesInjectorType;
        this.packages = packages;
    }

    public PackagesInjectorType getPackagesInjectorType() {
        return packagesInjectorType;
    }

    public void setPackagesInjectorType(PackagesInjectorType packagesInjectorType) {
        this.packagesInjectorType = packagesInjectorType;
    }

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}