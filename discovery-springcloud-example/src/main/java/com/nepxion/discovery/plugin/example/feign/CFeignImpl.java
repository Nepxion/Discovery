package com.nepxion.discovery.plugin.example.feign;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nepxion.discovery.plugin.framework.constant.PluginConstant;

@RestController
@ConditionalOnProperty(name = PluginConstant.SPRING_APPLICATION_NAME, havingValue = "discovery-springcloud-example-c")
public class CFeignImpl extends AbstractFeignImpl implements CFeign {
    @Override
    public String invoke(@RequestBody String value) {
        value = doInvoke(value);

        System.out.println("调用路径：" + value);

        return value;
    }
}