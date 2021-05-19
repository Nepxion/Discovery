package com.nepxion.discovery.plugin.example.service.impl;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.Subscribe;
import com.nepxion.discovery.common.entity.ParameterEntity;
import com.nepxion.discovery.common.entity.ParameterServiceEntity;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.event.ParameterChangedEvent;
import com.nepxion.discovery.plugin.framework.event.RegisterFailureEvent;
import com.nepxion.discovery.plugin.framework.event.RuleClearedEvent;
import com.nepxion.discovery.plugin.framework.event.RuleFailureEvent;
import com.nepxion.discovery.plugin.framework.event.RuleUpdatedEvent;
import com.nepxion.discovery.plugin.strategy.event.StrategyAlarmEvent;
import com.nepxion.eventbus.annotation.EventBus;

@EventBus
public class MySubscriber {
    @Autowired
    private PluginAdapter pluginAdapter;

    @Subscribe
    public void onRuleUpdated(RuleUpdatedEvent ruleUpdatedEvent) {
        System.out.println("========== 规则执行更新, rule=" + ruleUpdatedEvent.getRule());
    }

    @Subscribe
    public void onRuleCleared(RuleClearedEvent ruleClearedEvent) {
        System.out.println("========== 规则执行清空");
    }

    @Subscribe
    public void onRuleRuleFailure(RuleFailureEvent ruleFailureEvent) {
        System.out.println("========== 规则更新失败, rule=" + ruleFailureEvent.getRule() + ", exception=" + ruleFailureEvent.getException());
    }

    @Subscribe
    public void onParameterChanged(ParameterChangedEvent parameterChangedEvent) {
        ParameterEntity parameterEntity = parameterChangedEvent.getParameterEntity();
        String serviceId = pluginAdapter.getServiceId();
        List<ParameterServiceEntity> parameterServiceEntityList = null;
        if (parameterEntity != null) {
            Map<String, List<ParameterServiceEntity>> parameterServiceMap = parameterEntity.getParameterServiceMap();
            parameterServiceEntityList = parameterServiceMap.get(serviceId);
        }
        System.out.println("========== 获取动态参数, serviceId=" + serviceId + ", parameterServiceEntityList=" + parameterServiceEntityList);
    }

    @Subscribe
    public void onRegisterFailure(RegisterFailureEvent registerFailureEvent) {
        System.out.println("========== 注册失败, eventType=" + registerFailureEvent.getEventType() + ", eventDescription=" + registerFailureEvent.getEventDescription() + ", serviceId=" + registerFailureEvent.getServiceId() + ", host=" + registerFailureEvent.getHost() + ", port=" + registerFailureEvent.getPort());
    }

    @Subscribe
    public void onAlarm(StrategyAlarmEvent strategyAlarmEvent) {
        System.out.println("========== 告警类型=" + strategyAlarmEvent.getAlarmType());
        System.out.println("========== 告警内容=" + strategyAlarmEvent.getAlarmMap());
    }
}