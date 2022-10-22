package com.nepxion.discovery.plugin.admincenter.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.plugin.admincenter.resource.GitResource;

@RestController
@RequestMapping(path = "/git")
public class GitEndpoint {
    @Autowired
    private GitResource gitResource;

    @RequestMapping(path = "/map", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> map() {
        return doMap();
    }

    @RequestMapping(path = "/text", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> text() {
        return doText();
    }

    private ResponseEntity<?> doMap() {
        try {
            Map<String, String> map = gitResource.map();

            return ResponseUtil.getSuccessResponse(map);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doText() {
        try {
            String text = gitResource.text();

            return ResponseUtil.getSuccessResponse(text);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}