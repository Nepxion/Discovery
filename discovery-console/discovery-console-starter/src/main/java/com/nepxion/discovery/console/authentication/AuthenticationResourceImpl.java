package com.nepxion.discovery.console.authentication;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.nepxion.discovery.common.entity.UserEntity;

public class AuthenticationResourceImpl implements AuthenticationResource {
    @Autowired
    private Environment environment;

    @Override
    public boolean authenticate(UserEntity userEntity) throws Exception {
        String userId = userEntity.getUserId().trim();
        String password = userEntity.getPassword().trim();

        String passwordValue = environment.getProperty(userId);
        if (StringUtils.isNotEmpty(passwordValue)) {
            return StringUtils.equals(password, passwordValue);
        } else {
            throw new IllegalArgumentException("Not exists for [" + userId + "]");
        }
    }
}