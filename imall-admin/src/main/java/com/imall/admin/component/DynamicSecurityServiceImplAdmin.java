package com.imall.admin.component;

import com.imall.admin.service.UmsResourceService;
import com.imall.mbg.domain.UmsResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * admin模块权限信息
 *
 * @author zhangpengjun
 * @date 2022/9/5
 */
@Service
public class DynamicSecurityServiceImplAdmin implements DynamicSecurityService {

    @Autowired
    private UmsResourceService resourceService;

    @Override
    public Map<String, ConfigAttribute> loadDataSource() {
        Map<String, ConfigAttribute> map = new HashMap<>();
        List<UmsResourceEntity> resourceList = resourceService.list();
        resourceList.forEach(resource -> map.put(resource.getUrl(), new SecurityConfig("Permission:" + resource.getUrl())));
        return map;
    }

}
