package com.nepxion.discovery.common.entity;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import org.junit.Test;
import java.util.HashMap;

import static org.junit.Assert.*;
import static com.nepxion.discovery.common.entity.InstanceEntityWrapper.*;

public class InstanceEntityWrapperTest {

    private final InstanceEntity instanceEntity = new InstanceEntity();

    @Test
    public void testGetContextPath() {
        HashMap<String, String> hashMap = new HashMap<>();
        instanceEntity.setMetadata(hashMap);
        assertNull(getContextPath(instanceEntity));
    }

    @Test
    public void testGetGroup() {
        HashMap<String, String> hashMap = new HashMap<>();
        instanceEntity.setMetadata(hashMap);
        assertEquals("", getGroup(instanceEntity));

        hashMap.put(DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY, " ");
        assertEquals("", getGroup(instanceEntity));

        hashMap.put(DiscoveryConstant.SPRING_APPLICATION_GROUP_KEY, "spring.application.group.key");
        assertEquals("spring.application.group.key", getGroup(instanceEntity));
    }

    @Test
    public void testGetPlugin() {
        HashMap<String, String> hashMap = new HashMap<>();
        instanceEntity.setMetadata(hashMap);
        assertEquals("", getPlugin(instanceEntity));

        hashMap.put(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_PLUGIN, "spring.application.discovery.plugin");
        assertEquals("spring.application.discovery.plugin", getPlugin(instanceEntity));
    }

    @Test
    public void testIsRegisterControlEnabled() {
        HashMap<String, String> hashMap = new HashMap<>();
        instanceEntity.setMetadata(hashMap);
        assertTrue(isRegisterControlEnabled(instanceEntity));

        String value = "spring.application.register.control.enabled";
        hashMap.put(DiscoveryConstant.SPRING_APPLICATION_REGISTER_CONTROL_ENABLED, value);
        assertFalse(isRegisterControlEnabled(instanceEntity));
    }

    @Test
    public void testIsDiscoveryControlEnabled() {
        HashMap<String, String> hashMap = new HashMap<>();
        instanceEntity.setMetadata(hashMap);
        assertTrue(isDiscoveryControlEnabled(instanceEntity));

        String value = "spring.application.discovery.control.enabled";
        hashMap.put(DiscoveryConstant.SPRING_APPLICATION_DISCOVERY_CONTROL_ENABLED, value);
        assertFalse(isDiscoveryControlEnabled(instanceEntity));
    }

    @Test
    public void testIsConfigRestControlEnabled() {
        HashMap<String, String> hashMap = new HashMap<>();
        instanceEntity.setMetadata(hashMap);
        assertTrue(isConfigRestControlEnabled(instanceEntity));

        String value = "spring.application.config.rest.control.enabled";
        hashMap.put(DiscoveryConstant.SPRING_APPLICATION_CONFIG_REST_CONTROL_ENABLED, value);
        assertFalse(isConfigRestControlEnabled(instanceEntity));
    }
}
