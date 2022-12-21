package com.nepxion.discovery.plugin.admincenter.endpoint;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.common.util.ResponseUtil;
import com.nepxion.discovery.plugin.admincenter.resource.StrategyResource;

@RestController
@RequestMapping(path = "/strategy")
public class StrategyEndpoint {
    @Autowired
    private StrategyResource strategyResource;

    @RequestMapping(path = "/validate-expression", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> validateExpression(@RequestParam String expression, @RequestParam(defaultValue = "", required = false) String validation) {
        return doValidateExpression(expression, validation);
    }

    @RequestMapping(path = "/validate-route", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> validateRoute(@RequestParam String routeType, @RequestParam(defaultValue = "", required = false) String validation) {
        return doValidateRoute(routeType, validation);
    }

    private ResponseEntity<?> doValidateExpression(String expression, String validation) {
        try {
            boolean result = strategyResource.validateExpression(expression, validation);

            return ResponseUtil.getSuccessResponse(result);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }

    private ResponseEntity<?> doValidateRoute(String routeType, String validation) {
        try {
            String route = strategyResource.validateRoute(routeType, validation);

            return ResponseUtil.getSuccessResponse(route);
        } catch (Exception e) {
            return ResponseUtil.getFailureResponse(e);
        }
    }
}