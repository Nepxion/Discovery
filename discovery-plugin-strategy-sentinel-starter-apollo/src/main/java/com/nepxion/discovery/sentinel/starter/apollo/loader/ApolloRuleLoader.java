package com.nepxion.discovery.sentinel.starter.apollo.loader;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.nepxion.discovery.common.util.IOUtil;
import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.nepxion.discovery.plugin.strategy.sentinel.loader.RuleLoader;
import com.nepxion.discovery.sentinel.starter.apollo.ApolloDataSource;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Weihua
 * @since 5.3.9
 */
public class ApolloRuleLoader implements RuleLoader {

    private String namespace;
    private PluginAdapter pluginAdapter;
    private String flowPath;
    private String systemPath;
    private String authPath;
    private String degradePath;
    private String paramPath;


    public ApolloRuleLoader(String namespace, PluginAdapter pluginAdapter, String flowPath, String systemPath,
                            String authPath, String degradePath, String paramPath) {
        this.namespace = namespace;
        this.pluginAdapter = pluginAdapter;
        this.flowPath = flowPath;
        this.systemPath = systemPath;
        this.authPath = authPath;
        this.degradePath = degradePath;
        this.paramPath = paramPath;
    }

    @Override
    public void load() throws IOException {
        String ruleJson = getRuleJson(flowPath);
        String flowRuleKey = pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-sentinel-flow";
        ReadableDataSource<String, List<FlowRule>> flowDataSource = new ApolloDataSource<>(namespace, flowRuleKey, ruleJson);
        FlowRuleManager.register2Property(flowDataSource.getProperty());

        String systemJson = getRuleJson(systemPath);
        String systemRuleKey = pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-sentinel-system";
        ReadableDataSource<String, List<SystemRule>> systemDataSource = new ApolloDataSource<>(namespace, systemRuleKey, systemJson);
        SystemRuleManager.register2Property(systemDataSource.getProperty());

        String authJson = getRuleJson(authPath);
        String authRuleKey = pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-sentinel-authority";
        ReadableDataSource<String, List<AuthorityRule>> authorityDataSource = new ApolloDataSource<>(namespace, authRuleKey, authJson);
        AuthorityRuleManager.register2Property(authorityDataSource.getProperty());

        String degradeJson = getRuleJson(degradePath);
        String degradeRuleKey = pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-sentinel-degrade";
        ReadableDataSource<String, List<DegradeRule>> degradeDataSource = new ApolloDataSource<>(namespace, degradeRuleKey, degradeJson);
        DegradeRuleManager.register2Property(degradeDataSource.getProperty());

        String paramJson = getRuleJson(paramPath);
        String paramRuleKey = pluginAdapter.getGroup() + "-" + pluginAdapter.getServiceId() + "-sentinel-param";
        ReadableDataSource<String, List<ParamFlowRule>> paramDataSource = new ApolloDataSource<>(namespace, paramRuleKey, paramJson);
        ParamFlowRuleManager.register2Property(paramDataSource.getProperty());
    }

    private static String getRuleJson(String path) throws IOException {
        InputStream is = IOUtil.getInputStream(path);
        return IOUtils.toString(is, "UTF-8");
    }
}
