package com.nepxion.discovery.plugin.admincenter.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.plugin.admincenter.resource.ConfigResource;

@RestController
@RequestMapping(path = "/config")
public class ConfigEndpoint {
    @Autowired
    private ConfigResource configResource;

    @RequestMapping(path = "/config-type", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> configType() {
        return doConfigType();
    }

    @RequestMapping(path = "/update-async", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateAsync(@RequestBody String config) {
        return doUpdate(config, true);
    }

    @RequestMapping(path = "/update-sync", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateSync(@RequestBody String config) {
        return doUpdate(config, false);
    }

    @RequestMapping(path = "/clear-async", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> clearAsync() {
        return doClear(true);
    }

    @RequestMapping(path = "/clear-sync", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> clearSync() {
        return doClear(false);
    }

    @RequestMapping(path = "/view", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> view() {
        return doView();
    }

    private ResponseEntity<?> doConfigType() {
        try {
            String configType = configResource.getConfigType().toString();

            return ResponseUtil.getSuccessResponse(configType);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doUpdate(String config, boolean async) {
        try {
            configResource.update(config, async);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doClear(boolean async) {
        try {
            configResource.clear(async);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doView() {
        try {
            List<String> ruleList = configResource.view();

            return ResponseUtil.getSuccessResponse(ruleList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}