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
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.entity.StrategyBlacklistEntity;
import com.nepxion.discovery.common.exception.DiscoveryException;
import com.nepxion.discovery.common.util.StringUtil;
import com.nepxion.discovery.console.delegate.ConsoleResourceDelegateImpl;

public class BlacklistResourceImpl extends ConsoleResourceDelegateImpl implements BlacklistResource {
    @Autowired
    private ServiceResource serviceResource;

    @Override
    public String addBlacklist(String group, String targetServiceId, String targetHost, int targetPort) {
        return addBlacklist(group, null, targetServiceId, targetHost, targetPort);
    }

    @Override
    public String addBlacklist(String group, String targetServiceId, String targetServiceUUId) {
        return addBlacklist(group, null, targetServiceId, targetServiceUUId);
    }

    @Override
    public boolean deleteBlacklist(String group, String targetServiceId, String targetServiceUUId) {
        return deleteBlacklist(group, null, targetServiceId, targetServiceUUId);
    }

    @Override
    public boolean clearBlacklist(String group) {
        return clearBlacklist(group, null);
    }

    @Override
    public String addBlacklist(String group, String serviceId, String targetServiceId, String targetHost, int targetPort) {
        InstanceEntity instanceEntity = getInstanceEntity(targetServiceId, targetHost, targetPort);
        if (instanceEntity == null) {
            throw new DiscoveryException("Not found the instance with serviceId=" + targetServiceId + " host=" + targetHost + ", port=" + targetPort);
        }

        String targetServiceUUId = instanceEntity.getServiceUUId();
        if (StringUtils.isEmpty(targetServiceUUId)) {
            throw new DiscoveryException("Not found UUID in the instance with serviceId=" + targetServiceId + " host=" + targetHost + ", port=" + targetPort);
        }

        return addBlacklist(group, serviceId, targetServiceId, targetServiceUUId);
    }

    @Override
    public String addBlacklist(String group, String serviceId, String targetServiceId, String targetServiceUUId) {
        RuleEntity ruleEntity = getRemoteRuleEntity(group, serviceId);

        addBlacklistId(ruleEntity, targetServiceId, targetServiceUUId);

        updateRemoteRuleEntity(group, serviceId, ruleEntity);

        return targetServiceUUId;
    }

    @Override
    public boolean deleteBlacklist(String group, String serviceId, String targetServiceId, String targetServiceUUId) {
        RuleEntity ruleEntity = getRemoteRuleEntity(group, serviceId);

        deleteBlacklistId(ruleEntity, targetServiceId, targetServiceUUId);

        return updateRemoteRuleEntity(group, serviceId, ruleEntity);
    }

    @Override
    public boolean clearBlacklist(String group, String serviceId) {
        RuleEntity ruleEntity = getRemoteRuleEntity(group, serviceId);

        clearBlacklistId(ruleEntity);

        return updateRemoteRuleEntity(group, serviceId, ruleEntity);
    }

    private void addBlacklistId(RuleEntity ruleEntity, String serviceId, String serviceUUId) {
        StrategyBlacklistEntity strategyBlacklistEntity = ruleEntity.getStrategyBlacklistEntity();
        if (strategyBlacklistEntity != null) {
            String idValue = strategyBlacklistEntity.getIdValue();
            if (StringUtils.isNotEmpty(idValue)) {
                String addIdValue = addBlacklistId(idValue, serviceId, serviceUUId);
                strategyBlacklistEntity.setIdValue(addIdValue);
            } else {
                String addIdValue = addBlacklistId((String) null, serviceId, serviceUUId);
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
            } else {
                throw new DiscoveryException("Not found UUId=" + serviceUUId + " with serviceId=" + serviceId + " in blacklist");
            }
        } else {
            throw new DiscoveryException("Not found UUId=" + serviceUUId + " with serviceId=" + serviceId + " in blacklist");
        }
    }

    // 删除旧的UUId到下线黑名单idValue中，并产生新的idValue格式返回
    // idValue的格式为{"discovery-guide-service-a":"20210601-222214-909-1146-372-698", "discovery-guide-service-b":"20210601-222623-277-4978-633-279"}
    private String deleteBlacklistId(String idValue, String serviceId, String serviceUUId) {
        Map<String, List<String>> idMap = StringUtil.splitToComplexMap(idValue);
        if (MapUtils.isNotEmpty(idMap)) {
            List<String> idList = idMap.get(serviceId);
            if (CollectionUtils.isEmpty(idList)) {
                throw new DiscoveryException("Not found UUId=" + serviceUUId + " with serviceId=" + serviceId + " in blacklist");
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
                throw new DiscoveryException("Not found UUId=" + serviceUUId + " with serviceId=" + serviceId + " in blacklist");
            }

            return StringUtil.convertToComplexString(idMap);
        }

        return null;
    }

    private void clearBlacklistId(RuleEntity ruleEntity) {
        StrategyBlacklistEntity strategyBlacklistEntity = ruleEntity.getStrategyBlacklistEntity();
        if (strategyBlacklistEntity != null) {
            String idValue = strategyBlacklistEntity.getIdValue();
            if (StringUtils.isNotEmpty(idValue)) {
                strategyBlacklistEntity.setIdValue(null);
            } else {
                throw new DiscoveryException("No UUId blacklist found");
            }
        } else {
            throw new DiscoveryException("No UUId blacklist found");
        }
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
}