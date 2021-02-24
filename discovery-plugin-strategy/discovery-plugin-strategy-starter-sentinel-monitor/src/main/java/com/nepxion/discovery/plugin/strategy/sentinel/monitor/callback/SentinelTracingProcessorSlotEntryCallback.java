package com.nepxion.discovery.plugin.strategy.sentinel.monitor.callback;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zhang shun
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.node.DefaultNode;
import com.alibaba.csp.sentinel.slotchain.ProcessorSlotEntryCallback;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.strategy.sentinel.monitor.constant.SentinelStrategyMonitorConstant;

public abstract class SentinelTracingProcessorSlotEntryCallback<S> implements ProcessorSlotEntryCallback<DefaultNode> {
    @Override
    public void onPass(Context context, ResourceWrapper resourceWrapper, DefaultNode param, int count, Object... args) throws Exception {

    }

    @Override
    public void onBlocked(BlockException e, Context context, ResourceWrapper resourceWrapper, DefaultNode param, int count, Object... args) {
        S span = buildSpan();

        ApplicationContext applicationContext = PluginContextAware.getStaticApplicationContext();
        PluginAdapter pluginAdapter = applicationContext.getBean(PluginAdapter.class);

        Environment environment = PluginContextAware.getStaticEnvironment();
        Boolean tracerSentinelRuleOutputEnabled = environment.getProperty(SentinelStrategyMonitorConstant.SPRING_APPLICATION_STRATEGY_TRACER_SENTINEL_RULE_OUTPUT_ENABLED, Boolean.class, Boolean.TRUE);
        Boolean tracerSentinelArgsOutputEnabled = environment.getProperty(SentinelStrategyMonitorConstant.SPRING_APPLICATION_STRATEGY_TRACER_SENTINEL_ARGS_OUTPUT_ENABLED, Boolean.class, Boolean.FALSE);
      
        outputSpan(span, DiscoveryConstant.SPAN_TAG_PLUGIN_NAME, context.getName());
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_GROUP, pluginAdapter.getGroup());
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_TYPE, pluginAdapter.getServiceType());
        String serviceAppId = pluginAdapter.getServiceAppId();
        if (StringUtils.isNotEmpty(serviceAppId)) {
            outputSpan(span, DiscoveryConstant.N_D_SERVICE_APP_ID, serviceAppId);
        }
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_ID, pluginAdapter.getServiceId());
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_ADDRESS, pluginAdapter.getHost() + ":" + pluginAdapter.getPort());
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_VERSION, pluginAdapter.getVersion());
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_REGION, pluginAdapter.getRegion());
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_ENVIRONMENT, pluginAdapter.getEnvironment());
        outputSpan(span, DiscoveryConstant.N_D_SERVICE_ZONE, pluginAdapter.getZone());

        outputSpan(span, SentinelStrategyMonitorConstant.ORIGIN, context.getOrigin());
        outputSpan(span, SentinelStrategyMonitorConstant.ASYNC, String.valueOf(context.isAsync()));
        outputSpan(span, SentinelStrategyMonitorConstant.RESOURCE_NAME, resourceWrapper.getName());
        outputSpan(span, SentinelStrategyMonitorConstant.RESOURCE_SHOW_NAME, resourceWrapper.getShowName());
        outputSpan(span, SentinelStrategyMonitorConstant.RESOURCE_TYPE, String.valueOf(resourceWrapper.getResourceType()));
        outputSpan(span, SentinelStrategyMonitorConstant.ENTRY_TYPE, resourceWrapper.getEntryType().toString());
        outputSpan(span, SentinelStrategyMonitorConstant.RULE_LIMIT_APP, e.getRuleLimitApp());
        if (tracerSentinelRuleOutputEnabled) {
            outputSpan(span, SentinelStrategyMonitorConstant.RULE, e.getRule().toString());
        }
        outputSpan(span, SentinelStrategyMonitorConstant.CAUSE, e.getClass().getName());
        outputSpan(span, SentinelStrategyMonitorConstant.BLOCK_EXCEPTION, e.getMessage());
        outputSpan(span, SentinelStrategyMonitorConstant.COUNT, String.valueOf(count));
        if (tracerSentinelArgsOutputEnabled) {
            outputSpan(span, SentinelStrategyMonitorConstant.ARGS, JSON.toJSONString(args));
        }

        finishSpan(span);
    }

    protected abstract S buildSpan();

    protected abstract void outputSpan(S span, String key, String value);

    protected abstract void finishSpan(S span);
}