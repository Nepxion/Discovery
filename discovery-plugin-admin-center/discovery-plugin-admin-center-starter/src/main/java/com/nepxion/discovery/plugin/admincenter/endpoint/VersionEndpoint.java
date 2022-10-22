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
import com.nepxion.discovery.plugin.admincenter.resource.VersionResource;

@RestController
@RequestMapping(path = "/version")
public class VersionEndpoint {
    @Autowired
    private VersionResource versionResource;

    @RequestMapping(path = "/update-async", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateAsync(@RequestBody String version) {
        return doUpdate(version, true);
    }

    @RequestMapping(path = "/update-sync", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateSync(@RequestBody String version) {
        return doUpdate(version, false);
    }

    @RequestMapping(path = "/clear-async", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> clearAsync(@RequestBody(required = false) String version) {
        return doClear(version, true);
    }

    @RequestMapping(path = "/clear-sync", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> clearSync(@RequestBody(required = false) String version) {
        return doClear(version, false);
    }

    @RequestMapping(path = "/view", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> view() {
        return doView();
    }

    private ResponseEntity<?> doUpdate(String version, boolean async) {
        try {
            versionResource.update(version, async);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doClear(String version, boolean async) {
        try {
            versionResource.clear(version, async);

            return ResponseUtil.getSuccessResponse(true);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doView() {
        try {
            List<String> versionList = versionResource.view();

            return ResponseUtil.getSuccessResponse(versionList);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}