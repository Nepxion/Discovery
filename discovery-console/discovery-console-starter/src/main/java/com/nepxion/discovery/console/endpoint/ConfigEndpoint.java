package com.nepxion.discovery.console.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.entity.FormatType;
import com.nepxion.discovery.common.entity.ResultEntity;
import com.nepxion.discovery.common.entity.RuleEntity;
import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.console.resource.ConfigResource;

@RestController
@RequestMapping(path = "/config")
@Api(tags = { "配置接口" })
public class ConfigEndpoint {
    @Autowired
    private ConfigResource configResource;

    @RequestMapping(path = "/config-type", method = RequestMethod.GET)
    @ApiOperation(value = "获取配置中心类型", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> configType() {
        return doConfigType();
    }

    @RequestMapping(path = "/remote/update/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "更新规则配置到远程配置中心", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> remoteConfigUpdate(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置内容", required = true) String config) {
        return doRemoteConfigUpdate(group, serviceId, config);
    }

    @RequestMapping(path = "/remote/update/{group}/{serviceId}/{formatType}", method = RequestMethod.POST)
    @ApiOperation(value = "更新规则配置到远程配置中心", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> remoteConfigUpdate(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId, @PathVariable(value = "formatType") @ApiParam(value = "配置类型（Nacos专用）。取值： xml | json | yaml | properties | html | text", defaultValue = "text", required = true) String formatType, @RequestBody @ApiParam(value = "规则配置内容", required = true) String config) {
        return doRemoteConfigUpdate(group, serviceId, config, formatType);
    }

    @RequestMapping(path = "/remote/update-rule-entity/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "更新规则配置对象到远程配置中心", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> remoteRuleEntityUpdate(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置对象", required = true) RuleEntity ruleEntity) {
        return doRemoteRuleEntityUpdate(group, serviceId, ruleEntity);
    }

    @RequestMapping(path = "/remote/clear/{group}/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "清除规则配置到远程配置中心", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> remoteConfigClear(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId) {
        return doRemoteConfigClear(group, serviceId);
    }

    @RequestMapping(path = "/remote/view/{group}/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "查看远程配置中心的规则配置", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> remoteConfigView(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId) {
        return doRemoteConfigView(group, serviceId);
    }

    @RequestMapping(path = "/remote/view-rule-entity/{group}/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "查看远程配置中心的规则配置对象", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> remoteRuleEntityView(@PathVariable(value = "group") @ApiParam(value = "组名", required = true) String group, @PathVariable(value = "serviceId") @ApiParam(value = "服务名。当全局推送模式下，服务名必须由组名来代替", required = true) String serviceId) {
        return doRemoteRuleEntityView(group, serviceId);
    }

    @RequestMapping(path = "/update-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步更新规则配置", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> configUpdateAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置内容", required = true) String config) {
        return doConfigUpdate(serviceId, config, true);
    }

    @RequestMapping(path = "/update-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步更新规则配置", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> configUpdateSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId, @RequestBody @ApiParam(value = "规则配置内容", required = true) String config) {
        return doConfigUpdate(serviceId, config, false);
    }

    @RequestMapping(path = "/clear-async/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量异步清除规则配置", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> configClearAsync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doConfigClear(serviceId, true);
    }

    @RequestMapping(path = "/clear-sync/{serviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "批量同步清除规则配置", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> configClearSync(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doConfigClear(serviceId, false);
    }

    @RequestMapping(path = "/view/{serviceId}", method = RequestMethod.GET)
    @ApiOperation(value = "批量查看规则配置", notes = "", response = ResponseEntity.class, httpMethod = "GET")
    @ResponseBody
    public ResponseEntity<?> configView(@PathVariable(value = "serviceId") @ApiParam(value = "服务名", required = true) String serviceId) {
        return doConfigView(serviceId);
    }

    @RequestMapping(path = "/parse", method = RequestMethod.POST)
    @ApiOperation(value = "解析规则配置内容成对象", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> configParse(@RequestBody @ApiParam(value = "规则配置内容", required = true) String config) {
        return doConfigParse(config);
    }

    @RequestMapping(path = "/deparse", method = RequestMethod.POST)
    @ApiOperation(value = "反解析规则配置对象成内容", notes = "", response = ResponseEntity.class, httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<?> configDeparse(@RequestBody @ApiParam(value = "规则配置对象，RuleEntity格式", required = true) RuleEntity ruleEntity) {
        return doConfigDeparse(ruleEntity);
    }

    private ResponseEntity<?> doConfigType() {
        try {
            String configType = configResource.getConfigType().toString();

            return ResponseUtil.getSuccessResponse(configType);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRemoteConfigUpdate(String group, String serviceId, String config) {
        try {
            boolean result = configResource.updateRemoteConfig(group, serviceId, config);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRemoteConfigUpdate(String group, String serviceId, String config, String formatType) {
        try {
            boolean result = configResource.updateRemoteConfig(group, serviceId, config, FormatType.fromString(formatType));

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRemoteRuleEntityUpdate(String group, String serviceId, RuleEntity ruleEntity) {
        try {
            boolean result = configResource.updateRemoteRuleEntity(group, serviceId, ruleEntity);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRemoteConfigClear(String group, String serviceId) {
        try {
            boolean result = configResource.clearRemoteConfig(group, serviceId);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRemoteConfigView(String group, String serviceId) {
        try {
            String config = configResource.getRemoteConfig(group, serviceId);

            return ResponseUtil.getSuccessResponse(config);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doRemoteRuleEntityView(String group, String serviceId) {
        try {
            RuleEntity ruleEntity = configResource.getRemoteRuleEntity(group, serviceId);

            return ResponseUtil.getSuccessResponse(ruleEntity);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doConfigUpdate(String serviceId, String config, boolean async) {
        try {
            List<ResultEntity> resultEntityList = configResource.updateConfig(serviceId, config, async);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doConfigClear(String serviceId, boolean async) {
        try {
            List<ResultEntity> resultEntityList = configResource.clearConfig(serviceId, async);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doConfigView(String serviceId) {
        try {
            List<ResultEntity> resultEntityList = configResource.viewConfig(serviceId);

            return ResponseUtil.getSuccessResponse(resultEntityList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doConfigParse(String config) {
        try {
            RuleEntity ruleEntity = configResource.toRuleEntity(config);

            return ResponseUtil.getSuccessResponse(ruleEntity);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doConfigDeparse(RuleEntity ruleEntity) {
        try {
            String config = configResource.fromRuleEntity(ruleEntity);

            return ResponseUtil.getSuccessResponse(config);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}