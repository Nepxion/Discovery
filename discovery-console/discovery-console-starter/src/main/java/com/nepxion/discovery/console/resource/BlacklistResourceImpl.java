package com.nepxion.discovery.console.resource;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nepxion.discovery.common.entity.InstanceEntity;
import com.nepxion.discovery.common.entity.InstanceEntityWrapper;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyBlacklistEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.StringUtil;

public class BlacklistResourceImpl implements BlacklistResource {
    @Autowired
    private ServiceResource serviceResource;

    @Autowired
    private ConfigResource configResource;

    @Override
    public String addBlacklist(String serviceId, String host, int port) {
        String group = getGroup(serviceId);

        return addBlacklist(group, serviceId, host, port);
    }

    @Override
    public String addBlacklist(String group, String serviceId, String host, int port) {
        InstanceEntity instanceEntity = getInstanceEntity(serviceId, host, port);
        if (instanceEntity == null) {
            throw new DiscoveryException("Not found the instance with serviceId=" + serviceId + " host=" + host + ", port=" + port);
        }

        String serviceUUId = instanceEntity.getServiceUUId();
        if (StringUtils.isEmpty(serviceUUId)) {
            throw new DiscoveryException("Not found UUID in the instance with serviceId=" + serviceId + " host=" + host + ", port=" + port);
        }

        RuleEntity ruleEntity = null;
        try {
            ruleEntity = configResource.getRemoteRuleEntity(group, group);
        } catch (Exception e) {
            throw new DiscoveryException("Get remote RuleEntity failed, group=" + group, e);
        }

        addBlacklistId(ruleEntity, serviceId, serviceUUId);

        try {
            configResource.updateRemoteRuleEntity(group, group, ruleEntity);
        } catch (Exception e) {
            throw new DiscoveryException("Update remote RuleEntity failed, group=" + group, e);
        }

        return serviceUUId;
    }

    @Override
    public boolean deleteBlacklist(String serviceId, String serviceUUId) {
        String group = getGroup(serviceId);

        return deleteBlacklist(group, serviceId, serviceUUId);
    }

    @Override
    public boolean deleteBlacklist(String group, String serviceId, String serviceUUId) {
        RuleEntity ruleEntity = null;
        try {
            ruleEntity = configResource.getRemoteRuleEntity(group, group);
        } catch (Exception e) {
            throw new DiscoveryException("Get remote RuleEntity failed, group=" + group, e);
        }

        deleteBlacklistId(ruleEntity, serviceId, serviceUUId);

        try {
            return configResource.updateRemoteRuleEntity(group, group, ruleEntity);
        } catch (Exception e) {
            throw new DiscoveryException("Update remote RuleEntity failed, group=" + group, e);
        }
    }

    public void addBlacklistId(RuleEntity ruleEntity, String serviceId, String serviceUUId) {
        StrategyBlacklistEntity strategyBlacklistEntity = ruleEntity.getStrategyBlacklistEntity();
        if (strategyBlacklistEntity != null) {
            String idValue = strategyBlacklistEntity.getIdValue();
            if (StringUtils.isNotEmpty(idValue)) {
                String addIdValue = addBlacklistId(idValue, serviceId, serviceUUId);
                strategyBlacklistEntity.setIdValue(addIdValue);
            }
        } else {
            strategyBlacklistEntity = new StrategyBlacklistEntity();
            ruleEntity.setStrategyBlacklistEntity(strategyBlacklistEntity);

            String addIdValue = addBlacklistId((String) null, serviceId, serviceUUId);
            strategyBlacklistEntity.setIdValue(addIdValue);
        }
    }

    // 添加新的UUId到下线黑名单idValue中，并产生新的idValue格式返回
    // idValue的格式为{"discovery-guide-service-a":"20210601-222214-909-1146-372-698", "discovery-guide-service-b":"20210601-222623-277-4978-633-279"}
    private String addBlacklistId(String idValue, String serviceId, String serviceUUId) {
        Map<String, List<String>> idMap = StringUtil.splitToComplexMap(idValue);
        if (MapUtils.isNotEmpty(idMap)) {
            if (idMap.containsKey(serviceId)) {
                List<String> idList = idMap.get(serviceId);
                if (!idList.contains(serviceUUId)) {
                    List<String> newIdList = new ArrayList<String>();
                    newIdList.addAll(idList);
                    newIdList.add(serviceUUId);

                    idMap.put(serviceId, newIdList);
                }
            } else {
                List<String> idList = new ArrayList<String>();
                idList.add(serviceUUId);

                idMap.put(serviceId, idList);
            }
        } else {
            idMap = new LinkedHashMap<String, List<String>>();

            List<String> idList = new ArrayList<String>();
            idList.add(serviceUUId);

            idMap.put(serviceId, idList);
        }

        return StringUtil.convertToComplexString(idMap);
    }

    private void deleteBlacklistId(RuleEntity ruleEntity, String serviceId, String serviceUUId) {
        StrategyBlacklistEntity strategyBlacklistEntity = ruleEntity.getStrategyBlacklistEntity();
        if (strategyBlacklistEntity != null) {
            String idValue = strategyBlacklistEntity.getIdValue();
            if (StringUtils.isNotEmpty(idValue)) {
                String deletedIdValue = deleteBlacklistId(idValue, serviceId, serviceUUId);
                strategyBlacklistEntity.setIdValue(deletedIdValue);
            }
        }
    }

    // 删除旧的UUId到下线黑名单idValue中，并产生新的idValue格式返回
    // idValue的格式为{"discovery-guide-service-a":"20210601-222214-909-1146-372-698", "discovery-guide-service-b":"20210601-222623-277-4978-633-279"}
    private String deleteBlacklistId(String idValue, String serviceId, String serviceUUId) {
        Map<String, List<String>> idMap = StringUtil.splitToComplexMap(idValue);
        if (MapUtils.isNotEmpty(idMap)) {
            List<String> idList = idMap.get(serviceId);
            if (CollectionUtils.isEmpty(idList)) {
                throw new DiscoveryException("Not found UUID in the instance with serviceId=" + serviceId);
            }

            if (idList.contains(serviceUUId)) {
                if (idList.size() == 1) {
                    idMap.remove(serviceId);
                } else {
                    List<String> newIdList = new ArrayList<String>();
                    newIdList.addAll(idList);
                    newIdList.remove(serviceUUId);

                    idMap.put(serviceId, newIdList);
                }
            } else {
                throw new DiscoveryException("Not found UUID=" + serviceUUId + " with serviceId=" + serviceId + " in blacklist");
            }

            return StringUtil.convertToComplexString(idMap);
        }

        return null;
    }

    private InstanceEntity getInstanceEntity(String serviceId, String host, int port) {
        List<InstanceEntity> instanceEntityList = serviceResource.getInstanceList(serviceId);
        for (InstanceEntity instanceEntity : instanceEntityList) {
            if (StringUtils.equals(instanceEntity.getHost(), host) && instanceEntity.getPort() == port) {
                return instanceEntity;
            }
        }

        return null;
    }

    private String getGroup(String serviceId) {
        List<InstanceEntity> instanceEntityList = serviceResource.getInstanceList(serviceId);
        InstanceEntity instanceEntity = instanceEntityList.get(0);

        return InstanceEntityWrapper.getGroup(instanceEntity);
    }
}