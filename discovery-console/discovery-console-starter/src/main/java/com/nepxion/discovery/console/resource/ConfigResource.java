package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import com.nepxion.discovery.common.entity.ConfigType;
import com.nepxion.discovery.common.entity.FormatType;
import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.entity.RuleEntity;

public interface ConfigResource {
    ConfigType getConfigType();

    boolean updateRemoteConfig(String group, String serviceId, String config) throws Exception;

    boolean updateRemoteConfig(String group, String serviceId, String config, FormatType formatType) throws Exception;

    boolean updateRemoteRuleEntity(String group, String serviceId, RuleEntity ruleEntity) throws Exception;

    boolean clearRemoteConfig(String group, String serviceId) throws Exception;

    String getRemoteConfig(String group, String serviceId) throws Exception;

    RuleEntity getRemoteRuleEntity(String group, String serviceId) throws Exception;

    List<ResultEntity> updateConfig(String serviceId, String config, boolean async);

    List<ResultEntity> updateRuleEntity(String serviceId, RuleEntity ruleEntity, boolean async);

    List<ResultEntity> clearConfig(String serviceId, boolean async);

    List<ResultEntity> viewConfig(String serviceId);

    RuleEntity toRuleEntity(String config);

    String fromRuleEntity(RuleEntity ruleEntity);

    RuleEntity parse(String config);

    String deparse(RuleEntity ruleEntity);
}