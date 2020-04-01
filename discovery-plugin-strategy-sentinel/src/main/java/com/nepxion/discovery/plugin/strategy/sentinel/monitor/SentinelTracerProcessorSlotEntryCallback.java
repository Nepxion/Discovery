package com.nepxion.discovery.plugin.strategy.sentinel.monitor;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zhang shun
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.node.DefaultNode;
import com.alibaba.csp.sentinel.slotchain.ProcessorSlotEntryCallback;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.framework.context.PluginContextAware;
import com.nepxion.discovery.plugin.strategy.sentinel.constant.SentinelStrategyConstant;

public abstract class SentinelTracerProcessorSlotEntryCallback<S> implements ProcessorSlotEntryCallback<DefaultNode> {
    private Boolean tracerSentinelRuleOutputEnabled = Boolean.valueOf(System.getProperty(SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_SENTINEL_RULE_OUTPUT_ENABLED, "true"));
    private Boolean tracerSentinelArgsOutputEnabled = Boolean.valueOf(System.getProperty(SentinelStrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_SENTINEL_ARGS_OUTPUT_ENABLED, "false"));

    @Override
    public void onPass(Context context, ResourceWrapper resourceWrapper, DefaultNode param, int count, Object... args) throws Exception {

    }

    @Override
    public void onBlocked(BlockException e, Context context, ResourceWrapper resourceWrapper, DefaultNode param, int count, Object... args) {
        S span = buildSpan();

        PluginAdapter pluginAdapter = PluginContextAware.getStaticApplicationContext().getBean(PluginAdapter.class);

        outputSpan(span, DiscoveryConstant.TAG_COMPONENT_NAME, context.getName());
        outputSpan(span, DiscoveryConstant.PLUGIN, DiscoveryConstant.PLUGIN_VALUE);
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

        outputSpan(span, SentinelStrategyConstant.ORIGIN, context.getOrigin());
        outputSpan(span, SentinelStrategyConstant.ASYNC, String.valueOf(context.isAsync()));
        outputSpan(span, SentinelStrategyConstant.RESOURCE_NAME, resourceWrapper.getName());
        outputSpan(span, SentinelStrategyConstant.RESOURCE_SHOW_NAME, resourceWrapper.getShowName());
        outputSpan(span, SentinelStrategyConstant.RESOURCE_TYPE, String.valueOf(resourceWrapper.getResourceType()));
        outputSpan(span, SentinelStrategyConstant.ENTRY_TYPE, resourceWrapper.getEntryType().toString());
        outputSpan(span, SentinelStrategyConstant.RULE_LIMIT_APP, e.getRuleLimitApp());
        if (tracerSentinelRuleOutputEnabled) {
            outputSpan(span, SentinelStrategyConstant.RULE, e.getRule().toString());
        }
        outputSpan(span, SentinelStrategyConstant.CAUSE, e.getClass().getName());
        outputSpan(span, SentinelStrategyConstant.BLOCK_EXCEPTION, e.getMessage());
        outputSpan(span, SentinelStrategyConstant.COUNT, String.valueOf(count));
        if (tracerSentinelArgsOutputEnabled) {
            outputSpan(span, SentinelStrategyConstant.ARGS, JSON.toJSONString(args));
        }

        finishSpan(span);
    }

    protected abstract S buildSpan();

    protected abstract void outputSpan(S span, String key, String value);

    protected abstract void finishSpan(S span);
}