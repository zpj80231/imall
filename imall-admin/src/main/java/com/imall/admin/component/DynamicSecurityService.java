package com.imall.admin.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 加载资源所需权限
 *
 * @author zhangpengjun
 * @date 2022/9/5
 */
public interface DynamicSecurityService {

    /**
     * 加载指定资源的所需权限
     */
    Map<String, ConfigAttribute> loadDataSource();

}
