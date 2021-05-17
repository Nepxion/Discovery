package com.nepxion.discovery.console.resource;

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

import com.nepxion.discovery.common.entity.AuthenticationEntity;
import com.nepxion.discovery.common.entity.UserEntity;

public class AuthenticationResourceImpl implements AuthenticationResource {
    @Autowired
    private Environment environment;

    @Override
    public AuthenticationEntity authenticate(UserEntity userEntity) {
        AuthenticationEntity authenticationEntity = new AuthenticationEntity();

        String userId = userEntity.getUserId().trim();
        String password = userEntity.getPassword().trim();

        String passwordValue = environment.getProperty(userId);
        if (StringUtils.isNotEmpty(passwordValue)) {
            if (StringUtils.equals(password, passwordValue)) {
                authenticationEntity.setPassed(true);
            } else {
                authenticationEntity.setError("Password is mismatched");
            }
        } else {
            authenticationEntity.setError("Account doesn't exist");
        }

        return authenticationEntity;
    }
}