package com.nepxion.discovery.plugin.strategy.sentinel.loader;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.nepxion.discovery.common.util.IOUtil;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Weihua
 * @since 5.3.9
 */
public class DefaultLocalRuleLoader implements RuleLoader {

    private String flowPath;
    private String systemPath;
    private String authPath;
    private String degradePath;
    private String paramPath;

    public DefaultLocalRuleLoader(String flowPath, String systemPath, String authPath, String degradePath, String paramPath) {
        this.flowPath = flowPath;
        this.systemPath = systemPath;
        this.authPath = authPath;
        this.degradePath = degradePath;
        this.paramPath = paramPath;
    }

    /**
     * 默认的规则加载器只需要读本地文件，不需要使用任何监听器在运行时修改规则，
     * 如修改本地文件的规则文件，则需要重新启动，一般不会使用在生产环境
     *
     * @throws IOException 读取本地文件异常
     */
    public void load() throws IOException {
        FlowRuleManager.loadRules(getRuleJson(flowPath));
        SystemRuleManager.loadRules(getRuleJson(systemPath));
        AuthorityRuleManager.loadRules(getRuleJson(authPath));
        DegradeRuleManager.loadRules(getRuleJson(degradePath));
        ParamFlowRuleManager.loadRules(getRuleJson(paramPath));
    }

    private static <T> T getRuleJson(String path) throws IOException {
        InputStream is = IOUtil.getInputStream(path);
        String json = IOUtils.toString(is, "UTF-8");
        return JSON.parseObject(json, new TypeReference<>());
    }
}
