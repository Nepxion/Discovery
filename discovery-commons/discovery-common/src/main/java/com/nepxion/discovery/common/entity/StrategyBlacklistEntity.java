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
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.nepxion.discovery.common.util.StringUtil;

public class StrategyBlacklistEntity implements Serializable {
    private static final long serialVersionUID = -5342596042988530380L;

    private List<String> idList;
    private List<String> addressList;

    private String ids;
    private String addresses;

    public List<String> getIdList() {
        if (idList == null) {
            return null;
        }

        return Collections.unmodifiableList(idList);
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
        this.ids = StringUtil.convertToString(idList);
    }

    public String toIds() {
        return ids;
    }

    public List<String> getAddressList() {
        if (addressList == null) {
            return null;
        }

        return Collections.unmodifiableList(addressList);
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
        this.addresses = StringUtil.convertToString(addressList);
    }

    public String toAddresses() {
        return addresses;
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