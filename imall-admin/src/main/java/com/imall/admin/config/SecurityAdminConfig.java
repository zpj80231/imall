package com.imall.admin.config;

import com.imall.admin.component.DynamicSecurityService;
import com.imall.admin.component.DynamicSecurityServiceImplAdmin;
import com.imall.admin.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author zhangpengjun
 * @date 2022/8/24
 */
@Configuration
public class SecurityAdminConfig {

    @Autowired
    private UmsAdminService umsAdminService;
    @Autowired
    private DynamicSecurityServiceImplAdmin dynamicSecurityServiceImplAdmin;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> umsAdminService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return dynamicSecurityServiceImplAdmin;
    }
}
