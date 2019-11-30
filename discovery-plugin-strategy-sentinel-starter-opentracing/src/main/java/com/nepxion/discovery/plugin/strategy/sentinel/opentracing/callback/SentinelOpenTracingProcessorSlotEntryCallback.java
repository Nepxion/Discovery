package com.nepxion.discovery.plugin.strategy.sentinel.opentracing.callback;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author zhang shun
 * @version 1.0
 */

import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.node.DefaultNode;
import com.alibaba.csp.sentinel.slotchain.ProcessorSlotEntryCallback;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.nepxion.discovery.plugin.strategy.sentinel.opentracing.constant.SentinelOpenTracingConstant;

public class SentinelOpenTracingProcessorSlotEntryCallback implements ProcessorSlotEntryCallback<DefaultNode> {
    @Override
    public void onPass(Context context, ResourceWrapper resourceWrapper, DefaultNode param, int count, Object... args) throws Exception {

    }

    @Override
    public void onBlocked(BlockException e, Context context, ResourceWrapper resourceWrapper, DefaultNode param, int count, Object... args) {
        Span span = GlobalTracer.get().buildSpan(SentinelOpenTracingConstant.SPAN_NAME).start();
        span.setTag(SentinelOpenTracingConstant.CONTEXT_NAME, context.getName());
        span.setTag(SentinelOpenTracingConstant.ORIGIN, context.getOrigin());
        span.setTag(SentinelOpenTracingConstant.ASYNC, context.isAsync());
        span.setTag(SentinelOpenTracingConstant.RESOURCE_NAME, resourceWrapper.getName());
        span.setTag(SentinelOpenTracingConstant.RESOURCE_SHOW_NAME, resourceWrapper.getShowName());
        span.setTag(SentinelOpenTracingConstant.RESOURCE_TYPE, resourceWrapper.getResourceType());
        span.setTag(SentinelOpenTracingConstant.ENTRY_TYPE, resourceWrapper.getEntryType().toString());
        span.setTag(SentinelOpenTracingConstant.RULE_LIMIT_APP, e.getRuleLimitApp());
        span.setTag(SentinelOpenTracingConstant.RULE, e.getRule().toString());
        span.setTag(SentinelOpenTracingConstant.CAUSE, e.getClass().getName());
        span.setTag(SentinelOpenTracingConstant.BLOCK_EXCEPTION, e.getMessage());
        span.setTag(SentinelOpenTracingConstant.COUNT, count);
        span.setTag(SentinelOpenTracingConstant.ARGS, JSON.toJSONString(args));
        span.finish();
    }
}